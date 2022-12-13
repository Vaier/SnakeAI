import kotlin.random.Random

class Individual()
{
    var outputLayerConnections: Array<DoubleArray> = Array(4) { DoubleArray(24)}
    var fitness = 0.0
    val numberOfHiddenLayers = 0

    init
    {
        generateConnections()
    }

    private fun generateConnections()
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

    constructor(outputLayer: Array<DoubleArray>) : this()
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
        return java.lang.Double.max(0.0, num)
    }

    fun getMove(input: DoubleArray): Int
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

    fun createOffspring(num: Int): MutableList<Individual>
    {
        var fam = mutableListOf<Individual>()

        for(i in 1..num)
        {
            fam.add(RandomUniformMutation(this, 0.05))
        }

        return fam
    }
}