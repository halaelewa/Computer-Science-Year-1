from catalogue import *


def format_number(number) -> bool:
    cnt = 0  # counter of numbers in a group
    # go on each char in number string from right to left (end to beginning)
    for i in range(len(number) - 1, -1, -1):
        if number[i].isnumeric():  # it's a digit
            cnt += 1
        else:  # not digit
            if number[i] == ',':  # this char is a comma
                if cnt == 3:
                    cnt = 0  # we have digits group of 3
                else:
                    return False  # not group of 3

        # not group of 3
        if cnt > 3: return False

    # format is valid
    return True


def processUpdates(cntryFileName, updateFileName) -> bool:
    def handle_fileNotFoundError(file):
        print(f'File {file} does not exist!')
        quit_option = input('You want to quit? (Y/N): ')  # ask for another file name
        if quit_option not in ('n', 'N'):  # the user choose to quit
            open('output.txt', 'w').write('Update Unsuccessful\n')
            return ''

        else:  # the user does not want to quit
            # prompt the user for another country file name
            if quit_option in ('n', 'N'):
                file_type = 'country' \
                    if file == cntryFileName \
                    else 'update'
                file = input(f'Enter name of file with {file_type} data: ')
                return file

    """ an infinite loop, will stop only when the user input a valid country file name or quit """
    while True:
        """ process the country file using the CountryCatalogue """
        try:
            process_country = CountryCatalogue(cntryFileName)
            break
        except FileNotFoundError:  # a file name error
            cntryFileName = handle_fileNotFoundError(cntryFileName)
            if cntryFileName == '':
                return False

    """ an infinite loop, will stop only when the user input a valid update file name or quit """
    while True:
        """ process the updates file using the CountryCatalogue """
        try:
            f = open(updateFileName, 'r')
            break
        except FileNotFoundError:  # a file name error
            f = handle_fileNotFoundError(updateFileName)
            if f == '':
                return False

    """ navigate through each line"""
    for line in f:
        """ split the line on comma and get the name of the country and its attributes """
        name, *attrs = line.split(';')  # take the first item as the name and pack the rest as attrs
        name = name.strip()  # remove any whitespace
        name = ' '.join(name.split()).title()  # format the name of the country

        attrs = [attr.strip() for attr in attrs][:3]  # [:3] to get only the first 3 attributes
        q = False  # will be used as a skip mark
        """ go through each attribute """
        for attr in attrs:
            q = False  # will be used as a skip mark
            attr = attr.split('=')  # split the attributes on '=' sign
            L, val = attr
            L, val = L.strip(), val.strip()
            """ attr is pop|area, check the validity of their values """
            if L in ('P', 'A'):
                try:  # remove all ',' from it and try to convert it to (int)
                    # an exception will be raised if there are spaces
                    int(val.replace(',', ''))
                except ValueError:  # print a message and skip this line of update
                    print(f'\n({L}={val}) in {name} updateline has incorrect format, this update has been skipped!')
                    q = True  # skip this line of update

                """ this value isn't a string of digits with commas separating groups of 3 """
                if not q and not format_number(val):
                    print(f'\n({L}={val}) in {name} updateline is invalid format, this update has been skipped!')
                    q = True  # skip this line of update

            if q:  # there is an error, so skip the line of update
                break
            if name not in [cntry.getName() for cntry in process_country.countryCat]:  # country not found, add it
                process_country.addCountry(name, '', '', '')

        # the attributes of this country are correct and valid, so make the update or add the country
        if not q:
            for attr in attrs:
                attr = attr.split('=')
                L, val = attr
                L, val = L.strip(), val.strip()
                """ add the specified attributes to their country """
                # update the attributes
                if L == 'P':
                    process_country.setPopulationOfCountry(name, val)
                elif L == 'A':
                    process_country.setAreaOfCountry(name, val)
                elif L == 'C':
                    val = ' '.join(val.split()).title()
                    process_country.setContinentOfCountry(name, val)

    # output the result in a file named output.txt
    process_country.saveCountryCatalogue('output.txt')
    return True
