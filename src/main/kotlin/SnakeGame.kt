import java.awt.*
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import kotlin.math.sqrt
import javax.swing.*
import javax.swing.Timer
import kotlin.math.min
import kotlin.math.pow


class Board() : JPanel(), ActionListener {

    private var ai: neuralNetwork
    private var currentGeneration: MutableList<neuralNetwork> = mutableListOf<neuralNetwork>()
    private var currentAiNumber = 0
    private var generationNumber = 0
    private val population = 500


    private val boardWidth = 1000
    private val boardHeight = 1000
    private val dotSize = 100
    private val allDots = 100
    private val randPos = 10
    private var delay = 1

    private val x = IntArray(allDots)
    private val y = IntArray(allDots)

    private var nOfDots: Int = 0
    private var appleX: Int = 0
    private var appleY: Int = 0
    private var steps: Int = 0
    private var movesSinceApple: Int = 0

    private var direction = 2
    private var inGame = true

    private var timer: Timer? = null
    private var ball: Image? = null
    private var apple: Image? = null
    private var head: Image? = null

    init {
        background = Color.black
        isFocusable = true

        preferredSize = Dimension(boardWidth, boardHeight)

        for(i in 1..population)
            currentGeneration.add(neuralNetwork())

        ai = currentGeneration[currentAiNumber]

        loadImages()
        initGame()
    }

    private fun loadImages() {

        val iid = ImageIcon("src/main/resources/dot.png")
        ball = iid.image

        val iia = ImageIcon("src/main/resources/apple.png")
        apple = iia.image

        val iih = ImageIcon("src/main/resources/head.png")
        head = iih.image
    }

    private fun initGame() {

        nOfDots = 3
        ai = currentGeneration[currentAiNumber]
        for (z in 0 until nOfDots) {
            x[z] = 4 * dotSize - z * dotSize
            y[z] = 4 * dotSize
        }

        locateApple()

        timer = Timer(delay, this)
        timer!!.start()
    }

    public override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        doDrawing(g)
    }

    private fun doDrawing(g: Graphics) {

        if (inGame) {

            g.drawImage(apple, appleX, appleY, this)

            for (z in 0 until nOfDots) {
                if (z == 0) {
                    g.drawImage(head, x[z], y[z], this)
                } else {
                    g.drawImage(ball, x[z], y[z], this)
                }
            }

            Toolkit.getDefaultToolkit().sync()

        } else {

            gameOver(g)
        }
    }

    private fun gameOver(g: Graphics) {

        ai.fitness = steps+(2.0.pow(nOfDots - 3) + (nOfDots - 3).toDouble().pow(2.1)*500) -
                ((nOfDots - 3).toDouble().pow(1.2) * (0.25 * steps).pow(1.3))
        //ai.fitness = (-steps + (nOfDots - 3) * 100).toDouble()
        var lock = true
        if (currentAiNumber < population - 1)
        {
            if (currentAiNumber > 5)
                delay = 0
            else
                delay = 50
            currentAiNumber += 1
        }
        else {
            lock = false
            currentAiNumber = 0
            generationNumber += 1
            //if (generationNumber == 200)
            //    delay = 50
            println("NEW GENERATION!")
            println(generationNumber)
            createNewGeneration()
        }

        ai = currentGeneration[currentAiNumber]
        inGame = true
        restart()
    }

    private fun restart()
    {
        steps = 0
        inGame = true
        direction = 2
        movesSinceApple = 0
        initGame()
    }

    private fun checkApple() {

        if (x[0] == appleX && y[0] == appleY)
        {
            movesSinceApple = 0
            nOfDots++
            locateApple()
        }
    }

    private fun move()
    {
        movesSinceApple += 1
        steps += 1
        for (z in nOfDots downTo 1) {
            x[z] = x[z - 1]
            y[z] = y[z - 1]
        }

        if (direction == 1) {
            y[0] -= dotSize
        }

        if (direction == 2) {
            x[0] += dotSize
        }

        if (direction == 3) {
            y[0] += dotSize
        }

        if (direction == 4) {
            x[0] -= dotSize
        }
    }

    private fun checkCollision() {

        for (z in nOfDots downTo 1) {

            if (x[0] == x[z] && y[0] == y[z]) {
                inGame = false
            }
        }

        if (y[0] >= boardHeight) {
            inGame = false
        }

        if (y[0] < 0) {
            inGame = false
        }

        if (x[0] >= boardWidth) {
            inGame = false
        }

        if (x[0] < 0) {
            inGame = false
        }

        if (!inGame) {
            timer!!.stop()
        }
    }

    private fun locateApple() {

        var r = (Math.random() * randPos).toInt()
        appleX = r * dotSize

        r = (Math.random() * randPos).toInt()
        appleY = r * dotSize
    }

    override fun actionPerformed(e: ActionEvent) {

        if (inGame)
        {
            var directionArray = arrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
            directionArray[direction-1] = 1.0
            directionArray[tailDirection() + 4 - 1] = 1.0
            var input: Array<Double> = arrayOf(*(checkDistanceToWalls()), *(checkForApple()), *(checkForSnakeParts()))
            var newDirection = ai.getMove(input)

            if(!isDirectionOpposite(direction, newDirection))
                direction = newDirection
            if(movesSinceApple > 100)
                inGame = false

            checkCollision()
            move()
            checkApple()
        }
        repaint()
    }

    private fun isDirectionOpposite(first: Int, second: Int): Boolean {
        return ((first == 1) and (second == 3)) or ((first == 2) and (second == 4)) or
                ((first == 3) and (second == 1)) or ((first == 4) and (second == 2))
    }

    private fun tailDirection(): Int{
        if ((y[nOfDots-1] - y[nOfDots-2])/dotSize == 1) return 1
        if ((x[nOfDots-1] - x[nOfDots-2])/dotSize  == -1) return 2
        if ((y[nOfDots-1] - y[nOfDots-2])/dotSize  == -1) return 3
        return 4
    }

    private fun checkForApple(): Array<Double> {
        var sensors = arrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        if (x[0] == appleX && y[0] > appleY){
            sensors[0] = 1.0
        }

        if (appleX - x[0]  == y[0] - appleY){
            sensors[1] = 1.0
        }

        if (x[0] < appleX && y[0] == appleY){
            sensors[2] = 1.0
        }

        if (appleX - x[0]  == appleY - y[0]){
            sensors[3] = 1.0
        }

        if (x[0] == appleX && y[0] < appleY){
            sensors[4] = 1.0
        }

        if (x[0] - appleX == appleY - y[0]){
            sensors[5] = 1.0
        }

        if (x[0] > appleX && y[0] == appleY){
            sensors[6] = 1.0
        }

        if (x[0] - appleX == y[0] - appleY){
            sensors[7] = 1.0
        }

        return sensors
    }

    private fun checkForSnakeParts(): Array<Double> {
        var sensors = arrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        for (i in 1 until nOfDots) {
            if (x[0] == x[i] && y[0] > y[i]) {
                sensors[0] = 1.0
            }

            if (x[i] - x[0] == y[0] - y[i]) {
                sensors[1] = 1.0
            }

            if (x[0] < x[i] && y[0] == y[i]) {
                sensors[2] = 1.0
            }

            if (x[i] - x[0] == y[i] - y[0]) {
                sensors[3] = 1.0
            }

            if (x[0] == x[i] && y[0] < y[i]) {
                sensors[4] = 1.0
            }

            if (x[0] - x[i] == y[i] - y[0]) {
                sensors[5] = 1.0
            }

            if (x[0] > x[i] && y[0] == y[i]) {
                sensors[6] = 1.0
            }

            if (x[0] - x[i] == y[0] - y[i]) {
                sensors[7] = 1.0
            }
        }
        return sensors
    }

    private fun checkDistanceToWalls(): Array<Double>
    {
        var currentCords = currentHeadCell()
        var x = currentCords.first.toDouble()
        var y = currentCords.second.toDouble()
        return arrayOf(y, sqrt((min(y, 10 - x) * min(y, 10 - x) * 2)),
            10 - x, sqrt((min(10 - y, 10 - x) * min(10 - y, 10 - x) * 2)),
            10 - y, sqrt((min(10 - y, x) * min(10 - y, x) * 2)),
            x, sqrt((min(y, x) * min(y, x) * 2)))
    }

    private fun currentHeadCell(): Pair<Int, Int>{
        return Pair(x[0]/dotSize + 1, y[0]/dotSize + 1)
    }

    private fun createNewGeneration()
    {
        var fitnessList = mutableListOf<Pair<Double, Int>>()

        for (i in 0 until population)
        {
            fitnessList.add(Pair(currentGeneration[i].fitness, i))
        }

        var newFitnessList = fitnessList.sortedByDescending { it.first }
        for (i in 0..19)
            println(newFitnessList[i].first)
        var newGeneration = mutableListOf<neuralNetwork>()

        for (i in 0..19)
        {
            var chosenOne = currentGeneration[newFitnessList[i].second]
            newGeneration.add(chosenOne)
            newGeneration.addAll(chosenOne.createOffspring(19))
        }

        for (i in 0..100)
        {
            newGeneration.add(neuralNetwork())
        }

        currentGeneration = newGeneration


    }

}