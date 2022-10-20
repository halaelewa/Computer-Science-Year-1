class Country:
    def __init__(self, name, pop, area, continent):
        # create an object to use the parameter
        self._name = name
        self._pop = pop
        self._area = area
        self._continent = continent

        # create a special function to organize your output
    def __repr__(self):
        return f'{self._name} (pop: {self._pop}, area: {self._area}) in {self._continent}'

    # get ( name, pop, area, continent) from __init__ instructor
    # set (pop, area, continent)
    def getName(self):
        return self._name

    def getPopulation(self):
        return self._pop

    def setPopulation(self, pop):
        self._pop = pop

    def getArea(self):
        return self._area

    def setArea(self, area):
        self._area = area

    def getContinent(self):
        return self._continent

    def setContinent(self, continent):
        self._continent = continent

