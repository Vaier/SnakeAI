import java.awt.Color
import java.awt.Dimension
import javax.swing.BorderFactory
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel

class MenuPanel(frame: JFrame): JPanel()
{
    private val windowWidth = 1000
    private val windowHeight = 1000
    var frame = frame

    init{
        background = Color.white
        isFocusable = true
        preferredSize = Dimension(windowWidth, windowHeight)

        var startButton = JButton("Start simulation")
        startButton.setBounds(300,300, 100, 100)
        startButton.background = Color.ORANGE
        startButton.border = BorderFactory.createBevelBorder(0)
        startButton.addActionListener(AIListener(this.frame))
        add(startButton)

        var settingsButton = JButton("Settings")
        settingsButton.background = Color.ORANGE
        settingsButton.border = BorderFactory.createBevelBorder(0)
        settingsButton.addActionListener(SettingsListener(this.frame))
        add(settingsButton)
    }
}