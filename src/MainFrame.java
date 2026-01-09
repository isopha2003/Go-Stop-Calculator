import javax.swing.JFrame;

import java.awt.BorderLayout;

public class MainFrame extends JFrame {
	private WestPanel westPanel = new WestPanel();
	private CenterPanel centerPanel = new CenterPanel();
	private NorthPanel northPanel = new NorthPanel(westPanel, centerPanel);
	
	
	public MainFrame() {
		setTitle("고스톱 계산기");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		add(westPanel, BorderLayout.WEST);
		add(centerPanel, BorderLayout.CENTER);
		add(northPanel, BorderLayout.NORTH);
		
		westPanel.setNorthPanel(northPanel);
		westPanel.setCenterPanel(centerPanel);
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
	}
	public static void main(String[] args) {
		new MainFrame();
	}
}