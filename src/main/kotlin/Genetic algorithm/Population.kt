class Population(num: Int)
{
    var population: MutableList<Individual> = mutableListOf<Individual>()
    public val numberOfSpecies: Int = num

    init
    {
        for(i in 1..numberOfSpecies)
            population.add(Individual())
    }

    fun averageFitness(): Double
    {
        var sum = 0.0
        for(i in 0..numberOfSpecies)
        {
            sum += population[i].fitness
        }
        return sum/numberOfSpecies
    }

    fun bestFitness(): Double
    {
        return population[0].fitness
    }

    fun fitnessSum(): Double
    {
        var sum = 0.0
        for(i in 0..numberOfSpecies)
        {
            sum += population[i].fitness
        }
        return sum
    }

    fun fittestIndividual(numberOfIndividuals: Int): MutableList<Individual>
    {
        var bestIndividuals= mutableListOf<Individual>()
        for(i in 0 until numberOfIndividuals)
            bestIndividuals.add(population[i])
        return bestIndividuals
    }


}