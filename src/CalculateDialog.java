import javax.swing.JDialog;
import javax.swing.JPanel;

public class CalculateDialog extends JDialog {
	private CalculatePanel calculatePanel;
	
	public CalculateDialog(CalculatePanel calculatePanel) {
		this.calculatePanel = calculatePanel;
		calculatePanel.setCalculateDialog(this);
		setSize(600, 500);
		setLocationRelativeTo(null);
		
		add(calculatePanel);
	}
}
