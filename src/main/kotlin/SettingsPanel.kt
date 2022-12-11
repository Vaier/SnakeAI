import java.awt.Color
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.*

class SettingsPanel(frame: JFrame, board: Board): JPanel(), ActionListener
{

    private val windowWidth = 1000
    private val windowHeight = 1000
    private val board = board
    var frame = frame

    var t: JTextField
    var l: JLabel
    var c: JCheckBox

    init
    {
        background = Color.white
        isFocusable = true

        t = JTextField(16)

        l = JLabel("                                                                Number of species                                                                ")
        l!!.font = l!!.font.deriveFont(20f)

        var b = JButton("save")
        b.addActionListener(this)



        c = JCheckBox("Show only best one", true)

        add(t)
        add(l)
        add(c)
        add(b)

        this.frame.add(this)
        this.frame.show()
    }

    override fun actionPerformed(e: ActionEvent) {
        val s = e.actionCommand
        if (s == "save")
        {
            try {
                if (t.text != "") {
                    board.numberOfSnakes = t!!.text.toInt()
                    board.showBest = c.isSelected
                }
                (frame as Snake).back()
            }
            catch (e: NumberFormatException)
            {
                l!!.text = "                                                               Please input NUMBER                                                                "
            }

        }
    }

}