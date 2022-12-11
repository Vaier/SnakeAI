import kotlin.random.Random

//Replace existing connection with new one
public fun RandomUniformMutation(predecessor: Individual, mutationChance: Double): Individual
{
    var snake = Individual(predecessor.outputLayerConnections)

    for(i in 0..3)
    {
        for(j in 0..11)
        {
            if(Random.nextDouble(0.0, 1.0) < mutationChance)
            {
                snake.outputLayerConnections[i][j] = Random.nextDouble(-1.0, 1.0)
            }
        }
    }
    return snake
}

//Center around 0
public fun GaussianMutation(predecessor: Individual, mutationChance: Double): Individual
{
    var snake = Individual(predecessor.outputLayerConnections)

    for(i in 0..3)
    {
        for(j in 0..11)
        {
            if(Random.nextDouble(0.0, 1.0) < mutationChance)
            {
                snake.outputLayerConnections[i][j] *= Random.nextDouble(0.0, 1.0)
            }
        }
    }
    return snake
}

//Mutate towards best individual
public fun ToBestIndividualMutation(predecessor: Individual, best: Individual, mutationChance: Double): Individual
{
    var snake = Individual(predecessor.outputLayerConnections)

    for(i in 0..3)
    {
        for(j in 0..11)
        {
            if(Random.nextDouble(0.0, 1.0) < mutationChance)
            {
                snake.outputLayerConnections[i][j] =
                    (snake.outputLayerConnections[i][j] - best.outputLayerConnections[i][j]) * Random.nextDouble(0.0, 1.0)
            }
        }
    }
    return snake
}
