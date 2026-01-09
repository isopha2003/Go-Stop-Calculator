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

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

public class WestPanel extends JPanel {
	private NorthPanel northPanel;
	private CenterPanel centerPanel;
	
	private Color textColor = Color.black;
	private Color redColor = new Color(174, 10, 28);
	private Color greenColor = new Color(43, 102, 34);
	
	private Font subFont = new Font("맑은 고딕", Font.BOLD, 15);
	
	private Player players[] = null;
	private int totalPlayer = 0;
	private LeftPanel leftPanel = new LeftPanel();
	private RightPanel rightPanel = new RightPanel();
	
	private int pricePerPoint = 0;
	public WestPanel() {
		setLayout(new GridLayout(1, 2));
		add(leftPanel);
		add(rightPanel);
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
	// 승자 점수 반환
	public int getWinnerScore() {
		WinnerPanel panel = leftPanel.winnerPanel;
		WinnerScorePanel scorePanel = leftPanel.winnerScorePanel;
		int score = Integer.parseInt(panel.scoreTF.getText()); // 획득 점수
		if (panel.scoreTF.getText() == "") {
			JOptionPane.showMessageDialog(null, "점수를 입력하세요", "확인", JOptionPane.ERROR_MESSAGE);
			return -1;
		}
		int countBoom = scorePanel.boomCB.getSelectedIndex(); // 흔들기/폭탄 횟수
		int countGo = scorePanel.goCB.getSelectedIndex() + 1; // 고 횟수
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
			score += 1;
			if (i >= 3) {
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
		int loser1Score = getLoserScore(leftPanel.loserScorePanel1);
		int loser2Score = getLoserScore(leftPanel.loserScorePanel2);
		
		if(loser1Score == -1 || loser2Score == -1) {
			return;
		}
		
		int winner = leftPanel.winnerPanel.winnerCB.getSelectedIndex(); // 승자 인덱스 번호
		int loser1 = leftPanel.loserPanel1.loserCB.getSelectedIndex(); // 첫 번째 패자 인덱스 번호
		int loser2 = leftPanel.loserPanel2.loserCB.getSelectedIndex(); // 두 번째 패자 인덱스 번호
		
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
		if (leftPanel.loserScorePanel1.dokBak.isSelected()) {
			int price = (loser1Score + loser2Score) * pricePerPoint;
			players[loser1].setPayment(winner, price);
			ResultDialog resultDialog = new ResultDialog(
					players[winner].getPlayerName(), players[loser1].getPlayerName(), Integer.toString(price), totalPlayer);
			resultDialog.setVisible(true);
		}
		// 두 번째 패배자가 독박일 때
		else if (leftPanel.loserScorePanel2.dokBak.isSelected()) {
			int price = (loser1Score + loser2Score) * pricePerPoint;
			players[loser2].setPayment(winner, price);
			ResultDialog resultDialog = new ResultDialog(
					players[winner].getPlayerName(), players[loser2].getPlayerName(), Integer.toString(price), totalPlayer);
			resultDialog.setVisible(true);
		}
		else if (loser1 == -1) {
			int price = loser2Score * pricePerPoint;
			players[loser2].setPayment(winner, price);
			ResultDialog resultDialog = new ResultDialog(players[winner].getPlayerName(), players[loser2].getPlayerName(), Integer.toString(price), totalPlayer);
		}
		else if (loser2 == -1) {
			int price = loser2Score * pricePerPoint;
			players[loser1].setPayment(winner, price);
			ResultDialog resultDialog = new ResultDialog(players[winner].getPlayerName(), players[loser1].getPlayerName(), Integer.toString(price), totalPlayer);
		}
		else {
			int price1 = loser1Score * pricePerPoint;
			int price2 = loser2Score * pricePerPoint;
			
			players[loser1].setPayment(winner, price1);
			players[loser2].setPayment(winner, price2);
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
	        leftPanel.winnerPanel.winnerCB, 
	        leftPanel.loserPanel1.loserCB, 
	        leftPanel.loserPanel2.loserCB,
	        rightPanel.sellGwangPanel.sellerCB,
	        rightPanel.firstBbuckPanel.firstBbuckCB,
	        rightPanel.chongTongPanel.chongTongCB
	    };

	    for (JComboBox box : boxes) {
	        box.removeAllItems();
	        for (int i = 0; i < totalPlayer; i++) {
	            if (players[i] != null) {
	                box.addItem(players[i]);
	                box.setSelectedIndex(-1);
	            }
	        }
	    }
	}
	class LeftPanel extends JPanel {
		private WinnerPanel winnerPanel = new WinnerPanel();
		private WinnerScorePanel winnerScorePanel = new WinnerScorePanel();
		private LoserPanel loserPanel1 = new LoserPanel();
		private LoserScorePanel loserScorePanel1 = new LoserScorePanel();
		private LoserPanel loserPanel2 = new LoserPanel();
		private LoserScorePanel loserScorePanel2 = new LoserScorePanel();
		
		private JButton calculateBtn = new JButton("계산");
		public LeftPanel() {
			setLayout(new GridLayout(9, 1));
			setForeground(textColor);
			setBackground(greenColor);
			winnerPanel.setBackground(greenColor);
			add(winnerPanel);
			
			winnerScorePanel.setBackground(greenColor);
			add(winnerScorePanel);
			
			loserPanel1.setBackground(greenColor);
			add(loserPanel1);
			
			loserScorePanel1.setBackground(greenColor);
			add(loserScorePanel1);
			
			loserPanel2.setBackground(greenColor);
			add(loserPanel2);
			
			loserScorePanel2.setBackground(greenColor);
			add(loserScorePanel2);
			
			JPanel btnWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
			btnWrapper.setBackground(greenColor);
			
			calculateBtn.setForeground(Color.BLACK);
			calculateBtn.setBackground(redColor);
			calculateBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (leftPanel.winnerPanel.scoreTF.getText().equals("")) {
						JOptionPane.showMessageDialog(null,  "점수를 입력하세요", "확인", 
								JOptionPane.ERROR_MESSAGE);
					}
					else {
					calculate();
					centerPanel.setPlayer(players, totalPlayer);
					}
				}
			});
			
	        btnWrapper.add(calculateBtn);
	        add(btnWrapper);
		}
	}
	class WinnerPanel extends JPanel {
		private JLabel winnerLabel = new JLabel("승자");
		private JComboBox<Player> winnerCB = new JComboBox<Player>();
		private JTextField scoreTF = new JTextField(3);
		private JLabel scoreLabel = new JLabel("점");
		
		public WinnerPanel() {
			winnerLabel.setFont(subFont);
			winnerLabel.setForeground(textColor);
			winnerLabel.setBackground(greenColor);
			add(winnerLabel);
			
			winnerCB.setSelectedIndex(-1);
			winnerCB.setForeground(textColor);
			winnerCB.setBackground(greenColor);
			add(winnerCB);
			
			scoreTF.setForeground(textColor);
			scoreTF.setBackground(redColor);
			add(scoreTF);
			
			scoreLabel.setFont(subFont);
			scoreLabel.setForeground(textColor);
			scoreLabel.setBackground(greenColor);
			add(scoreLabel);
		}
	}
	class WinnerScorePanel extends JPanel {
		private Integer boomCount[] = {0, 1, 2};
		private JLabel boomLabel = new JLabel("흔들기(폭탄)");
		private JComboBox<Integer> boomCB = new JComboBox<Integer>(boomCount);
		
		private Integer goCount[] = {1, 2, 3, 4, 5, 6, 7};
		private JLabel goLabel = new JLabel("고");
		private JComboBox<Integer> goCB = new JComboBox<Integer>(goCount);
		
		private JCheckBox meongDdaCB = new JCheckBox("멍따");
		public WinnerScorePanel() {
			boomLabel.setFont(subFont);
			boomLabel.setForeground(textColor);
			boomLabel.setBackground(greenColor);
			add(boomLabel);
			
			boomCB.setForeground(textColor);
			boomCB.setBackground(greenColor);
			add(boomCB);
			
			goLabel.setFont(subFont);
			goLabel.setForeground(textColor);
			goLabel.setBackground(greenColor);
			add(goLabel);
			
			goCB.setSelectedIndex(-1);
			goCB.setForeground(textColor);
			goCB.setBackground(greenColor);
			add(goCB);
			
			meongDdaCB.setForeground(textColor);
			meongDdaCB.setBackground(greenColor);
			add(meongDdaCB);
		}
	}
	class LoserPanel extends JPanel {
		private JLabel loserLabel = new JLabel("패배자");
		private JComboBox<Player> loserCB = new JComboBox<Player>();
		public LoserPanel() {
			loserLabel.setFont(subFont);
			loserLabel.setForeground(textColor);
			loserLabel.setBackground(greenColor);
			add(loserLabel);
			
			loserCB.setSelectedIndex(-1);
			loserCB.setForeground(textColor);
			loserCB.setBackground(greenColor);
			add(loserCB);
		}
	}
	class LoserScorePanel extends JPanel {
		private JCheckBox gwangBak = new JCheckBox("광박");
		private JCheckBox piBak = new JCheckBox("피박");
		private JCheckBox dokBak = new JCheckBox("독박");
		public LoserScorePanel() {
			gwangBak.setForeground(textColor);
			gwangBak.setBackground(greenColor);
			add(gwangBak);
			
			piBak.setForeground(textColor);
			piBak.setBackground(greenColor);
			add(piBak);
			
			dokBak.setForeground(textColor);
			dokBak.setBackground(greenColor);
			add(dokBak);
		}
	}
	class RightPanel extends JPanel {
		private SellGwangPanel sellGwangPanel = new SellGwangPanel();
		private FirstBbuckPanel firstBbuckPanel = new FirstBbuckPanel();
		private ChongTongPanel chongTongPanel = new ChongTongPanel();
		public RightPanel() {
			setLayout(new GridLayout(3, 1));
			setBackground(greenColor);
			
			sellGwangPanel.setBackground(greenColor);
			add(sellGwangPanel);
			
			firstBbuckPanel.setBackground(greenColor);
			add(firstBbuckPanel);
			
			chongTongPanel.setBackground(greenColor);
			add(chongTongPanel);
		}
	}
	class SellGwangPanel extends JPanel {
		private JLabel sellGwangLabel = new JLabel("광팔이");
		private JComboBox<Player> sellerCB = new JComboBox<Player>();
		private Integer countGwang[] = {1, 2, 3, 4, 5, 6, 7};
		private JComboBox<Integer> gwangCB = new JComboBox<Integer>(countGwang);
		private JLabel countLabel = new JLabel("개");
		private JTextField priceTF = new JTextField(5);
		private JLabel wonLabel = new JLabel("원");
		
		private JButton calculateBtn = new JButton("계산");
		public SellGwangPanel() {
			sellGwangLabel.setFont(subFont);
			sellGwangLabel.setForeground(textColor);
			sellGwangLabel.setBackground(greenColor);
			add(sellGwangLabel);
			
			sellerCB.setSelectedIndex(-1);
			sellerCB.setForeground(textColor);
			sellerCB.setBackground(greenColor);
			add(sellerCB);
			
			gwangCB.setSelectedIndex(-1);
			gwangCB.setForeground(textColor);
			gwangCB.setBackground(greenColor);
			add(gwangCB);
			
			countLabel.setFont(subFont);
			countLabel.setForeground(textColor);
			countLabel.setBackground(greenColor);
			add(countLabel);
			
			priceTF.setForeground(textColor);
			priceTF.setBackground(redColor);
			add(priceTF);
			
			wonLabel.setFont(subFont);
			wonLabel.setForeground(textColor);
			wonLabel.setBackground(greenColor);
			add(wonLabel);
			calculateBtn.setForeground(Color.BLACK);
			calculateBtn.setBackground(redColor);
			calculateBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (priceTF.getText().equals("")) {
						JOptionPane.showMessageDialog(null,  "가격을 입력하세요", "확인", 
								JOptionPane.ERROR_MESSAGE);
					}
					else {
						int seller = sellerCB.getSelectedIndex(); // 광팔이 인덱스 번호
						int countGwang = gwangCB.getSelectedIndex() + 1; // 파는 광 개수
						int price = countGwang * Integer.parseInt(priceTF.getText());
						
						for (int i = 0; i < totalPlayer; i++) {
							if (i == seller) {
								continue;
							}
							else {
								players[i].setPayment(seller, price);
							}
						}
						centerPanel.setPlayer(players, totalPlayer);
					}
				}
			});
			add(calculateBtn);
		}
	}
	class FirstBbuckPanel extends JPanel {
		private JLabel firstBbuckLabel = new JLabel("첫뻑");
		private JComboBox<Player> firstBbuckCB = new JComboBox<Player>();
		
		private JTextField priceTF = new JTextField(5);
		private JLabel wonLabel = new JLabel("원");
		
		private JButton calculateBtn = new JButton("계산");
		public FirstBbuckPanel() {
			firstBbuckLabel.setFont(subFont);
			firstBbuckLabel.setForeground(textColor);
			firstBbuckLabel.setBackground(greenColor);
			add(firstBbuckLabel);
			
			firstBbuckCB.setSelectedIndex(-1);
			firstBbuckCB.setForeground(textColor);
			firstBbuckCB.setBackground(greenColor);
			add(firstBbuckCB);
			
			priceTF.setForeground(textColor);
			priceTF.setBackground(redColor);
			add(priceTF);
			
			wonLabel.setFont(subFont);
			wonLabel.setForeground(textColor);
			wonLabel.setBackground(greenColor);
			add(wonLabel);
			calculateBtn.setForeground(Color.BLACK);
			calculateBtn.setBackground(redColor);
			calculateBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (priceTF.getText().equals("")) {
						JOptionPane.showMessageDialog(null,  "가격을 입력하세요", "확인", 
								JOptionPane.ERROR_MESSAGE);
					}
					else {
						int firstBbuck = firstBbuckCB.getSelectedIndex(); // 광팔이 인덱스 번호
						int price = Integer.parseInt(priceTF.getText());
						
						for (int i = 0; i < totalPlayer; i++) {
							if (i == firstBbuck) {
								continue;
							}
							else {
								players[i].setPayment(firstBbuck, price);
							}
						}
						centerPanel.setPlayer(players, totalPlayer);
					}
				}
			});
			add(calculateBtn);
		}
	}
	class ChongTongPanel extends JPanel {
		private JLabel chongTongLabel = new JLabel("총통");
		private JComboBox<Player> chongTongCB = new JComboBox<Player>();
		private JTextField priceTF = new JTextField(5);
		private JLabel wonLabel = new JLabel("원");
		private JButton calculateBtn = new JButton("계산");
		public ChongTongPanel() {
			chongTongLabel.setFont(subFont);
			chongTongLabel.setForeground(textColor);
			chongTongLabel.setBackground(greenColor);
			add(chongTongLabel);
			
			chongTongCB.setSelectedIndex(-1);
			chongTongCB.setForeground(textColor);
			chongTongCB.setBackground(greenColor);
			add(chongTongCB);
			
			priceTF.setForeground(textColor);
			priceTF.setBackground(redColor);
			add(priceTF);
			
			wonLabel.setFont(subFont);
			wonLabel.setForeground(textColor);
			wonLabel.setBackground(greenColor);
			add(wonLabel);
			calculateBtn.setForeground(Color.BLACK);
			calculateBtn.setBackground(redColor);
			calculateBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (priceTF.getText().equals("")) {
						JOptionPane.showMessageDialog(null,  "가격을 입력하세요", "확인", 
								JOptionPane.ERROR_MESSAGE);
					}
					else {
						int chongTong = chongTongCB.getSelectedIndex(); // 광팔이 인덱스 번호
						int price = Integer.parseInt(priceTF.getText());
						
						for (int i = 0; i < totalPlayer; i++) {
							if (i == chongTong) {
								continue;
							}
							else {
								players[i].setPayment(chongTong, price);
							}
						}
						centerPanel.setPlayer(players, totalPlayer);
					}
				}
			});
			add(calculateBtn);
		}
	}
}