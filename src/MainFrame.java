import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;

public class MainFrame extends JFrame {
	private CalculatePanel calculatePanel = new CalculatePanel();
	private CenterPanel centerPanel = new CenterPanel();
	private NorthPanel northPanel = new NorthPanel(calculatePanel, centerPanel);
	
	
	public MainFrame() {
		setTitle("고스톱 계산기");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setLayout(new BorderLayout());
		
		ImagePanel panel = new ImagePanel();
		
		panel.setLayout(new BorderLayout());
		
		calculatePanel.setOpaque(false);
	    centerPanel.setOpaque(false);
	    northPanel.setOpaque(false);
		
		// panel.add(westPanel, BorderLayout.WEST);
		panel.add(centerPanel, BorderLayout.CENTER);
		panel.add(northPanel, BorderLayout.NORTH);
		
		setContentPane(panel);
		
		calculatePanel.setNorthPanel(northPanel);
		calculatePanel.setCenterPanel(centerPanel);
		
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
	}
	class ImagePanel extends JPanel {
		private ImageIcon icon = new ImageIcon("images/backgroundImage.png");
		private Image img = icon.getImage();
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		}
	}
	
	public static void main(String[] args) {
		new MainFrame();
	}
}