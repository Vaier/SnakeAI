import java.awt.CardLayout
import java.awt.EventQueue
import javax.swing.JFrame
import javax.swing.JPanel

class Snake : JFrame() {

    var cardLayout = CardLayout()
    var contentPane = JPanel()
    var board = Board()
    var settings = SettingsPanel(this, board)
    var menu = MenuPanel(this)

    init
    {
        initUI()
    }

    private fun initUI()
    {
        title = "Snake"
        isResizable = false
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        contentPane = JPanel()
        contentPane.layout = cardLayout
        contentPane.add(menu)
        setContentPane(contentPane);
        pack()
        setLocationRelativeTo(null)
    }

    public fun start()
    {
        contentPane.add(board)
        cardLayout.next(contentPane)
        contentPane.remove(menu)
        board.requestFocusInWindow()
    }

    public fun settings()
    {
        contentPane.add(settings)
        cardLayout.next(contentPane)
        contentPane.remove(menu)
        board.requestFocusInWindow()
    }

    public fun back()
    {
        contentPane.add(menu)
        cardLayout.next(contentPane)
        contentPane.remove(settings)
        board.requestFocusInWindow()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {

            EventQueue.invokeLater {
                val ex = Snake()
                ex.isVisible = true
            }
        }
    }
}
