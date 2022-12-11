import java.awt.event.ActionListener
import java.awt.event.ActionEvent;
import javax.swing.JFrame

class SettingsListener(frame: JFrame) : ActionListener
{
    var frame: JFrame = frame

    public override fun actionPerformed(e: ActionEvent?)
    {
        (frame as Snake).settings()
    }
}
