import java.lang.Double.max
import kotlin.random.Random

class neuralNetwork()
{
    var outputLayerConnections: Array<Array<Double>> = Array(4) { Array(24) { 0.0 } }
    var fitness = 0.0

    init
    {
        generateConnections()
    }

    fun generateConnections()
    {

        //Constructing output connections
        for(i in 0..3)
        {
            for(j in 0..23)
            {
                outputLayerConnections[i][j] = Random.nextDouble(-1.0, 1.0)
            }
        }
    }

    constructor(outputLayer: Array<Array<Double>>) : this()
    {

        //Constructing output connections
        for(i in 0..3)
        {
            for(j in 0..23)
            {
                outputLayerConnections[i][j] = outputLayer[i][j]
            }
        }
    }

    private fun sigmoid(num: Double): Double
    {
        return 1.0 / (1.0 + kotlin.math.exp(-num))
    }

    fun relu(num: Double): Double
    {
        return max(0.0, num)
    }

    fun getMove(input: Array<Double>): Int
    {
        var max = -1000.0
        var move = -1
        for(i in 1..4)
        {
            var sum = 0.0
            for(j in 0..23)
            {
                sum += input[j]*outputLayerConnections[i-1][j]
            }
            var value = sigmoid(sum)
            if(value > max){
                max = value
                move = i
            }
        }

        return move
    }

    fun createOffspring(num: Int): MutableList<neuralNetwork>
    {
        var fam = mutableListOf<neuralNetwork>()

        for(i in 1..num)
        {
            fam.add(mutate(this, 0.05))
        }

        return fam
    }

    private fun mutate(predecessor: neuralNetwork, mutationChance: Double): neuralNetwork
    {
        var snake = neuralNetwork(predecessor.outputLayerConnections)

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
}
