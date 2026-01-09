import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

public class CalculatePanel extends JPanel {
	private NorthPanel northPanel;
	private CenterPanel centerPanel;
	private CalculateDialog calculateDialog;
	
	private Color textColor = Color.black;
	// private Color redColor = new Color(174, 10, 28);
	// private Color greenColor = new Color(8, 54, 33);
	
	private Font subFont = new Font("맑은 고딕", Font.BOLD, 15);
	
	private Player players[] = null;
	private int totalPlayer = 0;
	
	private int pricePerPoint = 0;

	private int kkagdugi = -1;
	private int kkagdugi2 = -1;
	private int winner = -1;
	private int loser1 = -1;
	private int loser2 = -1;
	
	private WinnerPanel winnerPanel = new WinnerPanel();
	private WinnerScorePanel winnerScorePanel = new WinnerScorePanel();
	private LoserPanel loserPanel1 = new LoserPanel();
	private LoserScorePanel loserScorePanel1 = new LoserScorePanel();
	private LoserPanel loserPanel2 = new LoserPanel();
	private LoserScorePanel loserScorePanel2 = new LoserScorePanel();
	private JPanel btnWrapper;
	
	private JButton calculateBtn = new JButton("계산");
	public void reset() {
		kkagdugi = -1;
		kkagdugi2 = -1;
		winner = -1;
		loser1 = -1;
		loser2 = -1;
		
		winnerPanel.scoreTF.setText("");
		
		winnerScorePanel.boomCB.setSelectedIndex(0);
		winnerScorePanel.goCB.setSelectedIndex(0);
		winnerScorePanel.meongDdaCB.setSelected(false);
		
		loserScorePanel1.gwangBak.setSelected(false);
		loserScorePanel1.piBak.setSelected(false);
		loserScorePanel1.dokBak.setSelected(false);
		
		loserScorePanel2.gwangBak.setSelected(false);
		loserScorePanel2.piBak.setSelected(false);
		loserScorePanel2.dokBak.setSelected(false);
		
		setPlayer(players, totalPlayer);
	}
	public CalculatePanel() {
		setLayout(new BorderLayout());
		setForeground(textColor);
		
		JPanel centerContainer = new JPanel(new GridLayout(2, 1));
		
		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout(3, 1));
		panel1.setOpaque(false);
		panel1.add(winnerPanel);
		panel1.add(winnerScorePanel);
		centerContainer.add(panel1);
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(5, 1));
		panel2.setOpaque(false);
		panel2.add(loserPanel1);
		panel2.add(loserScorePanel1);
		panel2.add(loserPanel2);
		panel2.add(loserScorePanel2);
		centerContainer.add(panel2);
		
		JPanel panel3 = new JPanel();
		panel3.setLayout(new GridLayout(1, 1));
		panel3.setOpaque(false);
		btnWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
		// btnWrapper.setBackground(greenColor);
		
		calculateBtn.setForeground(Color.BLACK);
		// calculateBtn.setBackground(redColor);
		calculateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (winnerPanel.scoreTF.getText().equals("")) {
					JOptionPane.showMessageDialog(null,  "점수를 입력하세요", "확인", 
							JOptionPane.ERROR_MESSAGE);
				}
				else {
				calculate();
				reset();
				northPanel.reset();
				centerPanel.setPlayer(players, totalPlayer);
				calculateDialog.setVisible(false);
				}
			}
		});
		
        btnWrapper.add(calculateBtn);
        
        add(centerContainer, BorderLayout.CENTER);
        add(btnWrapper, BorderLayout.SOUTH);
		setPanel();
	}
	public void setCalculateDialog(CalculateDialog calculateDialog) {
		this.calculateDialog = calculateDialog;
	}
	public void setNorthPanel(NorthPanel northPanel) {
		this.northPanel = northPanel;
		this.pricePerPoint = northPanel.getPricePerPoint();
	}
	public void setCenterPanel(CenterPanel centerPanel) {
		this.centerPanel = centerPanel;
	}
	public void setPricePerPoint(int pricePerPoint) {
		this.pricePerPoint = pricePerPoint;
	}
	public void setKkagdugi(int kkagdugi, int kkagdugi2) {
		this.kkagdugi = kkagdugi;
		this.kkagdugi2 = kkagdugi2;
	}
	// 승자 점수 반환
	public int getWinnerScore() {
		WinnerPanel panel = winnerPanel;
		WinnerScorePanel scorePanel = winnerScorePanel;
		if (panel.scoreTF.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "점수를 입력하세요", "확인", JOptionPane.ERROR_MESSAGE);
			return -1;
		}
		int score = Integer.parseInt(panel.scoreTF.getText()); // 획득 점수
		int countBoom = scorePanel.boomCB.getSelectedIndex(); // 흔들기/폭탄 횟수
		int countGo = scorePanel.goCB.getSelectedIndex(); // 고 횟수
		boolean meongDda = scorePanel.meongDdaCB.isSelected();

		// 흔들기/폭탄 점수 반영
		if (countBoom != 0) {
			if (countBoom == 1) {
				score *= 2;
			}
			else if (countBoom == 2) {
				score *= 4;
			}
		}
		// 고 횟수 점수 반영
		for (int i = 1; i <= countGo; i++) {
			if (i < 3) {
				score += 1;
			}
			else if (i >= 3) {
				score *= 2;
			}
		}
		
		if (meongDda) {
			score *= 2;
		}
		
		return score;
	}
	// 패배자 점수 반환
	public int getLoserScore(LoserScorePanel scorePanel) {
		int total = getWinnerScore();
		if (total == -1) {
			return -1;
		}
		boolean gwangBak = scorePanel.gwangBak.isSelected();
		boolean piBak = scorePanel.piBak.isSelected();
		if (gwangBak) {
			total *= 2;
		}
		if (piBak) {
			total *= 2;
		}
		
		return total;
	}
	
	public void calculate() {
		int loser1Score = getLoserScore(loserScorePanel1);
		int loser2Score = getLoserScore(loserScorePanel2);
		
		if (winner == -1) {
			JOptionPane.showMessageDialog(null, "승자를 입력하세요", "확인", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		else if (loser1 == -1 && loser2 == -1) {
			JOptionPane.showMessageDialog(null, "패자를 입력하세요", "확인", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		else if (winner == loser1 || winner == loser2) {
			JOptionPane.showMessageDialog(null, "승자와 패자가 같습니다.", "확인", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		else if (loser1 == loser2) {
			JOptionPane.showMessageDialog(null,  "두 패자가 같습니다.", "확인", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		// 첫 번째 패배자가 독박일 때
		if (loserScorePanel1.dokBak.isSelected()) {
			int price = (loser1Score + loser2Score) * pricePerPoint;
			players[loser1].setPayment(winner, price);
			players[winner].setPayment(loser1, -price);
			ResultDialog resultDialog = new ResultDialog(
					players[winner].getPlayerName(), players[loser1].getPlayerName(), Integer.toString(price), totalPlayer);
			resultDialog.setVisible(true);
		}
		// 두 번째 패배자가 독박일 때
		else if (loserScorePanel2.dokBak.isSelected()) {
			int price = (loser1Score + loser2Score) * pricePerPoint;
			players[loser2].setPayment(winner, price);
			players[winner].setPayment(loser2, -price);
			ResultDialog resultDialog = new ResultDialog(
					players[winner].getPlayerName(), players[loser2].getPlayerName(), Integer.toString(price), totalPlayer);
			resultDialog.setVisible(true);
		}
		else if (loser1 == -1) {
			int price = loser2Score * pricePerPoint;
			players[loser2].setPayment(winner, price);
			players[winner].setPayment(loser2, -price);
			ResultDialog resultDialog = new ResultDialog(players[winner].getPlayerName(), players[loser2].getPlayerName(), Integer.toString(price), totalPlayer);
			resultDialog.setVisible(true);

		}
		else if (loser2 == -1) {
			int price = loser1Score * pricePerPoint;
			players[loser1].setPayment(winner, price);
			players[winner].setPayment(loser1, -price);
			ResultDialog resultDialog = new ResultDialog(players[winner].getPlayerName(), players[loser1].getPlayerName(), Integer.toString(price), totalPlayer);
			resultDialog.setVisible(true);
		}
		else {
			int price1 = loser1Score * pricePerPoint;
			int price2 = loser2Score * pricePerPoint;
			
			players[loser1].setPayment(winner, price1);
			players[winner].setPayment(loser1, -price1);
			players[loser2].setPayment(winner, price2);
			players[winner].setPayment(loser2, -price2);
			ResultDialog resultDialog = new ResultDialog(
					players[winner].getPlayerName(), players[loser1].getPlayerName(), players[loser2].getPlayerName(), 
					Integer.toString(price1), Integer.toString(price2), totalPlayer);
			resultDialog.setVisible(true);
		}
		
	}
	public void setPlayer(Player players[], int totalPlayer) {
	    this.players = players;
	    this.totalPlayer = totalPlayer;
	    
	    JComboBox[] boxes = {
	        winnerPanel.winnerCB
	    };

	    for (JComboBox box : boxes) {
	        box.removeAllItems();
	        for (int i = 0; i < totalPlayer; i++) {
	            if (i == kkagdugi || i == kkagdugi2) {
	            	continue;
	            }
	        	if (players[i] != null) {
	                box.addItem(players[i]);
	                box.setSelectedIndex(-1);
	                box.setOpaque(false);
	            }
	        }
	    }
	}
	
	public void setPanel() {
	    this.setOpaque(false);

		// LeftPanel 내부 자식들 투명화
		winnerPanel.setOpaque(false);
		winnerScorePanel.setOpaque(false);
		loserPanel1.setOpaque(false);
		loserScorePanel1.setOpaque(false);
		loserPanel2.setOpaque(false);
		loserScorePanel2.setOpaque(false);
		btnWrapper.setOpaque(false);
	}
			
	class WinnerPanel extends JPanel {
		private JLabel winnerLabel = new JLabel("승자");
		private JComboBox<Player> winnerCB = new JComboBox<Player>();
		private JTextField scoreTF = new JTextField(3);
		private JLabel scoreLabel = new JLabel("점");
		
		public WinnerPanel() {
			winnerLabel.setFont(subFont);
			winnerLabel.setForeground(textColor);
			// winnerLabel.setBackground(greenColor);
			add(winnerLabel);
			
			winnerCB.setSelectedIndex(-1);
			winnerCB.setForeground(textColor);
			winnerCB.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
						Player p = (Player) winnerCB.getSelectedItem();
						if (p == null) {
							return;
						}
						int winnerIndex = p.getIndex();
						winner = winnerIndex;
						
						if (winner != -1) {
						for (int i = 0; i < totalPlayer; i++) {
							if (i == winner || i == kkagdugi || i == kkagdugi2) {
								continue;
							}
							else {
								loser1 = i;
								loserPanel1.loserLabel.setText("패배자: " + players[i].getPlayerName());
								break;
							}
						}
						for (int i = 0; i < totalPlayer; i++) {
							if (i == winner || i == kkagdugi || i == loser1 || i == kkagdugi2) {
								continue;
							}
							else {
								loser2 = i;
								loserPanel2.loserLabel.setText("패배자: " + players[i].getPlayerName());
								break;
							}
						}
					}
				}
			});
			add(winnerCB);
			
			scoreTF.setForeground(textColor);
			// scoreTF.setBackground(redColor);
			add(scoreTF);
			
			scoreLabel.setFont(subFont);
			scoreLabel.setForeground(textColor);
			// scoreLabel.setBackground(greenColor);
			add(scoreLabel);
		}
	}
	class WinnerScorePanel extends JPanel {
		private Integer boomCount[] = {0, 1, 2};
		private JLabel boomLabel = new JLabel("흔들기(폭탄)");
		private JComboBox<Integer> boomCB = new JComboBox<Integer>(boomCount);
		
		private Integer goCount[] = {0, 1, 2, 3, 4, 5, 6, 7};
		private JLabel goLabel = new JLabel("고");
		private JComboBox<Integer> goCB = new JComboBox<Integer>(goCount);
		
		private JCheckBox meongDdaCB = new JCheckBox("멍따");
		public WinnerScorePanel() {
			boomLabel.setFont(subFont);
			boomLabel.setForeground(textColor);
			// boomLabel.setBackground(greenColor);
			add(boomLabel);
			
			boomCB.setForeground(textColor);
			// boomCB.setBackground(greenColor);
			add(boomCB);
			
			goLabel.setFont(subFont);
			goLabel.setForeground(textColor);
			// goLabel.setBackground(greenColor);
			add(goLabel);
			
			goCB.setSelectedIndex(-1);
			goCB.setForeground(textColor);
			// goCB.setBackground(greenColor);
			add(goCB);
			
			meongDdaCB.setOpaque(false);
			meongDdaCB.setForeground(textColor);
			// meongDdaCB.setBackground(greenColor);
			add(meongDdaCB);
		}
	}
	class LoserPanel extends JPanel {
		private JLabel loserLabel = new JLabel("패배자: ");
		public LoserPanel() {
			loserLabel.setFont(subFont);
			loserLabel.setForeground(textColor);
			// loserLabel.setBackground(greenColor);
			add(loserLabel);
		}
	}
	class LoserScorePanel extends JPanel {
		private JCheckBox gwangBak = new JCheckBox("광박");
		private JCheckBox piBak = new JCheckBox("피박");
		private JCheckBox dokBak = new JCheckBox("독박");
		public LoserScorePanel() {
			gwangBak.setOpaque(false);
			gwangBak.setForeground(textColor);
			// gwangBak.setBackground(greenColor);
			add(gwangBak);
			
			piBak.setOpaque(false);
			piBak.setForeground(textColor);
			// piBak.setBackground(greenColor);
			add(piBak);
			
			dokBak.setOpaque(false);
			dokBak.setForeground(textColor);
			// dokBak.setBackground(greenColor);
			add(dokBak);
		}
	}
}