import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class CenterPanel extends JPanel {
	private Font mainFont = new Font("맑은 고딕", Font.BOLD, 20);
	private Font subFont = new Font("맑은 고딕", Font.BOLD, 15);
	private Color redColor = new Color(174, 10, 28);
	private Color greenColor = new Color(43, 102, 34);
	private Player players[];
	public CenterPanel() {
		setBackground(greenColor);
	}
	public void setPlayer(Player players[], int totalPlayer) {
		this.players = players;
		this.removeAll();
		this.setLayout(new GridLayout(3, 1, 10, 10));
		add(new FirstLinePanel(players, totalPlayer));
		add(new SecondLinePanel(players, totalPlayer));
		
		revalidate();
		repaint();
	}
	class FirstLinePanel extends JPanel {
		public FirstLinePanel(Player players[], int totalPlayer) {
			this.setBackground(greenColor);
			setLayout(new GridLayout(1, totalPlayer, 10, 10));
			for (int i = 0; i < totalPlayer; i++) {
				JLabel label = new JLabel(players[i].getPlayerName());
				label.setFont(mainFont);
				label.setForeground(Color.BLACK);
				label.setBackground(redColor);
				label.setHorizontalAlignment(JLabel.CENTER);
				add(label);
			}
		}
	}
	class SecondLinePanel extends JPanel {
		public SecondLinePanel(Player players[], int totalPlayer) {
			setBackground(greenColor);
			setLayout(new GridLayout(1, totalPlayer - 1, 10, 10));
			for (int i = 0; i < totalPlayer; i++) {
				JPanel infoPanel = new JPanel();
				infoPanel.setLayout(new GridLayout(3, 2, 10, 10));
				
				if (totalPlayer >= 2) {
					infoPanel.setBackground(redColor);
					infoPanel.setBorder(new LineBorder(Color.BLACK, 3, true));
				}
				else {
					infoPanel.setBackground(greenColor);
				}
				for (int j = 0; j < totalPlayer; j++) {
					if (i == j) {
						continue;
					}
					else {						
						JLabel nameLabel = new JLabel(players[j].getPlayerName());
						JLabel priceLabel = new JLabel(Integer.toString(players[j].getPayment(i)) + '원');
						nameLabel.setFont(subFont);
						priceLabel.setFont(subFont);
						nameLabel.setForeground(Color.BLACK);
						nameLabel.setBackground(redColor);
						priceLabel.setForeground(Color.BLACK);
						nameLabel.setHorizontalAlignment(JLabel.CENTER);
						priceLabel.setHorizontalAlignment(JLabel.CENTER);
						infoPanel.add(nameLabel);
						infoPanel.add(priceLabel);
					}
				}
				add(infoPanel);
			}
		}
	}
}