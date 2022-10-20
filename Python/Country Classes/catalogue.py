from country import Country


class CountryCatalogue:
    # create a list that will save all the Country instances
    countryCat = []

    def __init__(self, countryFile):

        # Open the country file and read it a line by line
        country_File = open(countryFile, encoding='utf-8', errors='ignore')
        country_File.readline()
        for line in country_File:
            # split the line by a |
            line = line.split("|")
            name, cont, pop, area = line
            # print(line)
            # exit(0)
            # add the country and delete the whitespaces
            self.addCountry(name.strip(), pop.strip(), area.strip(), cont.strip())

        # close the file
        country_File.close()

    """ setters function """
    def setPopulationOfCountry(self, name, pop):
        for cnt in self.countryCat:
            if name == cnt.getName():
                cnt.setPopulation(pop)

    def setAreaOfCountry(self, name, area):
        for cnt in self.countryCat:
            if name == cnt.getName():
                cnt.setArea(area)

    def setContinentOfCountry(self, name, cont):
        for cnt in self.countryCat:
            if name == cnt.getName():
                cnt.setContinent(cont)

    # check the country if it exists
    def findCountry(self, country) -> object:
        for cntry in self.countryCat:
            if cntry.getName() == country.getName():
                return cntry

        # if it does not exists return none
        return None

    # add the country in the countryCat
    def addCountry(self, name, pop, area, cont) -> bool:
        # create a Country object
        country = Country(name, pop, area, cont)

        # check if the the country already exists or not
        if self.findCountry(country):
            return False

        # add country to countryCat
        self.countryCat.append(country)
        return True

    # print the country instances in the countryCat
    def printCountryCatalogue(self):
        for i in self.countryCat:
            print(i.__repr__())  # make use of __repr__() from Country class

    #  save the country in a file
    def saveCountryCatalogue(self, fname):
        # sort the list according to the name (alphabetically)
        self.countryCat.sort(key=lambda x: x.getName())

        with open(fname, 'w') as f:
            f.write('Country|Continent|Population|Area\n')  # the header line
            for country in self.countryCat:
                f.write(f'{country.getName()}|{country.getContinent()}'
                        f'|{country.getPopulation()}|{country.getArea()}\n')

