// Secomd Test Commit
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ResultDialog extends JDialog {
	private Color textColor = Color.black;
	private Color redColor = new Color(174, 10, 28);
	private Color greenColor = new Color(43, 102, 34);
	
	private Font subFont = new Font("맑은 고딕", Font.BOLD, 15);
	
	private int totalPlayer = 0;
	
	private JLabel winnerLabel = new JLabel();
	private JLabel loser1Label = new JLabel();
	private JLabel loser2Label = new JLabel();
	private JLabel loser1Price = new JLabel();
	private JLabel loser2Price = new JLabel();
	
	public ResultDialog(String winner, String loser1, String loser2, String loser1Price, String loser2Price, int totalPlayer) {
		setSize(250, 250);
		setLocationRelativeTo(null);
		setLayout(new GridLayout(2, 1));
		
		this.totalPlayer = totalPlayer;
		
		this.winnerLabel.setText(winner);
		this.loser1Label.setText(loser1);
		this.loser2Label.setText(loser2);
		this.loser1Price.setText('-' + loser1Price + '원');
		this.loser1Price.setBackground(redColor);
		this.loser2Price.setText('-' + loser2Price + '원');
		JPanel firstPanel = new JPanel();
		firstPanel.setLayout(new GridLayout(1, 1));
		winnerLabel.setText(winner);
		winnerLabel.setHorizontalAlignment(JLabel.CENTER);
		firstPanel.add(winnerLabel);
		JPanel secondPanel = new JPanel();
		if (totalPlayer >= 3) {
			secondPanel.setLayout(new GridLayout(2, 2));
		}
		else {
			secondPanel.setLayout(new GridLayout(1, 2));
		}
		loser1Label.setHorizontalAlignment(JLabel.CENTER);
		this.loser1Price.setHorizontalAlignment(JLabel.CENTER);
		loser2Label.setHorizontalAlignment(JLabel.CENTER);
		this.loser2Price.setHorizontalAlignment(JLabel.CENTER);
		secondPanel.add(loser1Label);
		secondPanel.add(this.loser1Price);
		if (totalPlayer >= 3) {
			secondPanel.add(loser2Label);
			secondPanel.add(this.loser2Price);
		}
		add(firstPanel);
		add(secondPanel);
	}
	public ResultDialog(String winner, String loser, String loserPrice, int totalPlayer) {
		this.winnerLabel.setText(winner);
		this.loser1Label.setText(loser);
		this.loser1Price.setText(loserPrice);
		this.totalPlayer = totalPlayer;
		
		setSize(250, 250);
		setLocationRelativeTo(null);
		setLayout(new GridLayout(2, 1));
		
		JPanel firstPanel = new JPanel();
		firstPanel.setLayout(new GridLayout(1, 1));
		winnerLabel.setHorizontalAlignment(JLabel.CENTER);
		add(winnerLabel);
		JPanel secondPanel = new JPanel();
		secondPanel.setLayout(new GridLayout(1, 2));
		loser1Label.setHorizontalAlignment(JLabel.CENTER);
		this.loser1Price.setHorizontalAlignment(JLabel.CENTER);
		secondPanel.add(loser1Label);
		secondPanel.add(this.loser1Price);
		
		add(firstPanel);
		add(secondPanel);
	}
}
