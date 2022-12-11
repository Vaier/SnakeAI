import kotlin.random.Random

fun elitismSelection(currentGeneration: MutableList<Individual>, elitePercent: Double, oldSnakesPercent: Double): MutableList<Individual>
{
    var fitnessList = mutableListOf<Pair<Double, Int>>()

    for (i in 0 until currentGeneration.count())
    {
        fitnessList.add(Pair(currentGeneration[i].fitness, i))
    }

    var newFitnessList = fitnessList.sortedByDescending { it.first }

    var eliteNumber = (currentGeneration.count() * 0.04).toInt()
    for (i in 0 until eliteNumber)
        println(newFitnessList[i].first)

    var newGeneration = mutableListOf<Individual>()
    var offspringNumber = (currentGeneration.count() * 0.8).toInt() / eliteNumber
    for (i in 0 until offspringNumber)
    {
        var chosenOne = currentGeneration[newFitnessList[i].second]
        newGeneration.add(chosenOne)
        newGeneration.addAll(chosenOne.createOffspring(19))
    }

    for (i in 0..100)
    {
        newGeneration.add(Individual())
    }

    return newGeneration
}

fun rouletteWheelSelection(currentGeneration: MutableList<Individual>, oldSnakesPercent: Double): MutableList<Individual>
{
    var numberOfNewSnakes = (currentGeneration.count() * oldSnakesPercent).toInt()
    var newGeneration = mutableListOf<Individual>()
    var fitnessSum = 0.0
    for(i in 0..currentGeneration.count())
    {
        fitnessSum += currentGeneration[i].fitness
    }
    for(i in 1..numberOfNewSnakes)
    {
        var pick = Random.nextDouble(0.0, fitnessSum)
        var current = 0.0
        for(j in 0..currentGeneration.count())
        {
            current += currentGeneration[j].fitness
            if(current > pick)
            {
                newGeneration.add(currentGeneration[j])
            }
            break
        }
    }

    var numberOfAdditionalSnakes = currentGeneration.count() - numberOfNewSnakes
    for (i in 0..numberOfAdditionalSnakes)
    {
        newGeneration.add(Individual())
    }

    return newGeneration
}

fun tournamentSelection(currentGeneration: MutableList<Individual>, endRound: Int)
{
    //Should be Tournament play off with win condition - best fitness
}