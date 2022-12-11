import kotlin.random.Random

fun elitismSelection(population: Population, elitePercent: Double, oldSnakesPercent: Double): MutableList<Individual>
{
    var fitnessList = mutableListOf<Pair<Double, Int>>()

    for (i in 0 until population.numberOfSpecies)
    {
        fitnessList.add(Pair(population.population[i].fitness, i))
    }

    var newFitnessList = fitnessList.sortedByDescending { it.first }

    var eliteNumber = (population.numberOfSpecies * elitePercent).toInt()
    for (i in 0 until eliteNumber)
        println(newFitnessList[i].first)

    var newGeneration = mutableListOf<Individual>()
    var offspringNumber = (population.numberOfSpecies * oldSnakesPercent).toInt() / eliteNumber
    for (i in 0 until offspringNumber)
    {
        var chosenOne = population.population[newFitnessList[i].second]
        newGeneration.add(chosenOne)
        newGeneration.addAll(chosenOne.createOffspring(offspringNumber))
    }

    var numberOfNewSnakes = (population.numberOfSpecies*(1-oldSnakesPercent)).toInt()
    for (i in 0..numberOfNewSnakes)
    {
        newGeneration.add(Individual())
    }

    return newGeneration
}

fun rouletteWheelSelection(population: Population, oldSnakesPercent: Double): MutableList<Individual>
{
    var numberOfNewSnakes = (population.numberOfSpecies * oldSnakesPercent).toInt()
    var newGeneration = mutableListOf<Individual>()

    for(i in 1..numberOfNewSnakes)
    {
        var pick = Random.nextDouble(0.0, population.fitnessSum())
        var current = 0.0
        for(j in 0..population.numberOfSpecies)
        {
            current += population.population[j].fitness
            if(current > pick)
            {
                newGeneration.add(population.population[j])
            }
            break
        }
    }

    var numberOfAdditionalSnakes = population.numberOfSpecies - numberOfNewSnakes
    for (i in 0..numberOfAdditionalSnakes)
    {
        newGeneration.add(Individual())
    }

    return newGeneration
}

fun tournamentSelection()
{
    //Should be Tournament play off with win condition - best fitness
}