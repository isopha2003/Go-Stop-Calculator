import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NorthPanel extends JPanel {
	private CalculatePanel calculatePanel;
	private CenterPanel centerPanel;
	private CalculateDialog calculateDialog;
	
	// 광팔이
	private Integer countGwang[] = {0, 1, 2, 3, 4, 5, 6, 7};
	private JComboBox<Integer> gwangCB = new JComboBox<Integer>(countGwang);
	private JComboBox<Integer> gwangCB2 = new JComboBox<Integer>(countGwang);
	private JLabel countLabel = new JLabel("개");
	private JLabel countLabel2 = new JLabel("개");

	private String playerName;
	
	private int totalPlayer = 0;
	private Player players[] = new Player[5];
	
	private int pricePerPoint = 0;
	private int pricePerGwang = 0;
	private int priceFirstBbuck = 0;
	private int priceChongTong = 0;
	
	private int kkagdugi = -1;
	private int kkagdugi2 = -1;
	private boolean kkagdugiSetting = false;
	// private boolean kkagdugiSetting2 = false; 
	public void reset() {
		kkagdugi = -1;
		kkagdugi2 = -1;
		kkagdugiSetting = false;
		// kkagdugiSetting2 = false;
		gwangCB.setSelectedIndex(0);
		gwangCB.setSelectedIndex(0);
	}
	public NorthPanel(CalculatePanel calculatePanel, CenterPanel centerPanel) {
		this.calculatePanel = calculatePanel;
		this.centerPanel = centerPanel;
		calculateDialog = new CalculateDialog(calculatePanel);
		
		createReadyMenu();
		createCalculateMenu();
	}
	public void setKkagdugi(int kkagdugi) {
		this.kkagdugi = kkagdugi;
	}
	public int getPricePerPoint() {
		return pricePerPoint;
	}
	public void createReadyMenu() {
		JMenuBar readyMenuBar = new JMenuBar();
		ReadyMenuActionListener listener = new ReadyMenuActionListener();
		
		JMenuItem [] menuItem = new JMenuItem [5];
		JMenu readyMenu = new JMenu("게임 준비");
		String readyTitle[] = {"참가자 추가", "점 당 가격", "광팔이 가격", "첫 뻑 가격", "총통 가격"};
		for (int i = 0; i < readyTitle.length; i++) {
			menuItem[i] = new JMenuItem(readyTitle[i]);
			menuItem[i].addActionListener(listener);
			readyMenu.add(menuItem[i]);	
		}
		
		readyMenuBar.add(readyMenu);
		add(readyMenuBar);
	}
	class ReadyMenuActionListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			
			switch(cmd) {
			case "참가자 추가": {
				playerName = JOptionPane.showInputDialog("참가자 이름을 입력하세요.");
				if (playerName != null) {
					if (playerName.equals("")) {
						JOptionPane.showMessageDialog(null, "이름을 입력하세요.", "확인", JOptionPane.ERROR_MESSAGE);
					}
					else if (totalPlayer >= 5) {
						JOptionPane.showMessageDialog(null,  "5명까지 참가할 수 있습니다.", "확인", 
								JOptionPane.ERROR_MESSAGE);
					}
					else {
						players[totalPlayer] = new Player(playerName, totalPlayer);
						totalPlayer++;
						calculatePanel.setPlayer(players, totalPlayer);
						centerPanel.setPlayer(players, totalPlayer);
					}
				}
				break;
			}
			case "광팔이 가격": {
				String point = JOptionPane.showInputDialog("광 당 가격을 입력하세요.");
				if (point != null) {
					pricePerGwang = Integer.parseInt(point);
					JOptionPane.showMessageDialog(null,  "광 당 가격이 저장되었습니다: " + pricePerGwang, "확인", JOptionPane.INFORMATION_MESSAGE);
				}
				break;
			}
			case "점 당 가격": {
				String point = JOptionPane.showInputDialog("점 당 가격을 입력하세요.");
				if (point != null) {
					pricePerPoint = Integer.parseInt(point);
					calculatePanel.setPricePerPoint(pricePerPoint);
					JOptionPane.showMessageDialog(null,  "점당 가격이 저장되었습니다: " + pricePerPoint, "확인", JOptionPane.INFORMATION_MESSAGE);
				}
				break;
			}
			case "첫 뻑 가격": {
				String point = JOptionPane.showInputDialog("첫 뻑 가격을 입력하세요.");
				if (point != null) {
					priceFirstBbuck = Integer.parseInt(point);
					JOptionPane.showMessageDialog(null,  "첫 뻑 가격이 저장되었습니다: " + priceFirstBbuck, "확인", JOptionPane.INFORMATION_MESSAGE);
				}
				break;
			}
			case "총통 가격": {
				String point = JOptionPane.showInputDialog("총통 가격을 입력하세요.");
				if (point != null) {
					priceChongTong = Integer.parseInt(point);
					JOptionPane.showMessageDialog(null,  "총통 가격이 저장되었습니다: " + priceChongTong, "확인", JOptionPane.INFORMATION_MESSAGE);
				}
				break;
			}
			}
		}
	}
	public void createCalculateMenu() {
		JMenuBar calculateMenuBar = new JMenuBar();
		CalculateActionListener listener = new CalculateActionListener();
		JMenu calculateMenu = new JMenu("계산하기");
		
		calculateMenuBar.add(calculateMenu);
		JMenuItem [] menuItem = new JMenuItem [5];
		String calculateTitle[] = {"광팔이(깍두기)", "결과 정산","첫 뻑", "총통", "금액 수정"};
		for (int i = 0; i < calculateTitle.length; i++) {
			menuItem[i] = new JMenuItem (calculateTitle[i]);
			menuItem[i].addActionListener(listener);
			calculateMenu.add(menuItem[i]);
		}
		
		calculateMenuBar.add(calculateMenu);
		add(calculateMenuBar);
	}
	public void setPricePerGwang(int pricePerGwang) {
		this.pricePerGwang = pricePerGwang;
	}
	class CalculateActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			
			switch(cmd) {
			case "광팔이(깍두기)" : {
				if (pricePerGwang == 0) {
		    		JOptionPane.showMessageDialog(null, "광팔이 가격을 입력하세요", "확인", JOptionPane.ERROR_MESSAGE);
		    		return;
				}
				if (kkagdugiSetting) {
					JOptionPane.showMessageDialog(null, "이미 이번 게임의 광팔이를 입력했습니다.", "확인", JOptionPane.ERROR_MESSAGE);
					return;
				}
				JPanel myPanel = new JPanel(new GridLayout(2, 1));
				JPanel panel1 = new JPanel(new GridLayout(1, 2));
				JPanel panel2 = new JPanel();
				JComboBox<Player> exclusionPlayerCB = new JComboBox();
				JComboBox<Player> sellerCB = new JComboBox<>();
				JComboBox<Player> sellerCB2 = new JComboBox<>();
				if (totalPlayer == 5) {
					panel2.setLayout(new GridLayout(2, 4));
				}
				
				else {
					panel2 = new JPanel(new GridLayout(1, 4));
				}
			    
			    // 현재 참여 중인 플레이어들을 콤보박스에 추가
			    for (int i = 0; i < totalPlayer; i++) {
			    	if (i == kkagdugi || i == kkagdugi2) {
			    		continue;
			    	}
			    	exclusionPlayerCB.addItem(players[i]);
			    	sellerCB.addItem(players[i]);
			    }
			    if (totalPlayer == 5) {
			    	// 현재 참여 중인 플레이어들을 콤보박스에 추가
				    for (int i = 0; i < totalPlayer; i++) {
				    	if (i == kkagdugi || i == kkagdugi2) {
				    		continue;
				    	}
				    	sellerCB2.addItem(players[i]);
				    }
			    }
			    panel1.add(new JLabel("광팔이 제외"));
			    panel1.add(exclusionPlayerCB);
			    panel2.add(new JLabel("광팔이"));
			    panel2.add(sellerCB);
			    panel2.add(gwangCB);
			    panel2.add(countLabel);
			    if (totalPlayer == 5) {
			    	panel2.add(new JLabel("광팔이"));
			    	panel2.add(sellerCB2);
			    	panel2.add(gwangCB2);
			    	panel2.add(countLabel2);
			    }
			    myPanel.add(panel2);
			    myPanel.add(panel1);
			    int result = JOptionPane.showConfirmDialog(null, myPanel, "광팔이", JOptionPane.OK_CANCEL_OPTION);
			    if (result == JOptionPane.OK_OPTION) {
			    	Player eP = (Player)exclusionPlayerCB.getSelectedItem();
			    	Player p = (Player)sellerCB.getSelectedItem();
			    	if (p == null || eP == null) {
			    		return;
			    	}
			    	int exclusionPlayer = eP.getIndex();
			    	int seller = p.getIndex();
			    	int seller2 = -1;
			    	Player p2;
			    	if (exclusionPlayer == seller) {
		    			JOptionPane.showMessageDialog(null, "광팔이와 광팔이 제외 플레이어가 같습니다.", "확인", JOptionPane.ERROR_MESSAGE);
		    			return;
		    		}
			    	if (totalPlayer == 5) {
			    		p2 = (Player)sellerCB2.getSelectedItem();
			    		if (p2 == null) {
			    			return;
			    		}
			    		seller2 = p2.getIndex();
			    		if (seller == seller2) {
			    			JOptionPane.showMessageDialog(null, "두 광팔이가 같습니다.", "확인", JOptionPane.ERROR_MESSAGE);
			    			return;
			    		}
			    		else if (exclusionPlayer == seller2) {
			    			JOptionPane.showMessageDialog(null, "광팔이와 광팔이 제외 플레이어가 같습니다.", "확인", JOptionPane.ERROR_MESSAGE);
			    			return;
			    		}
			    	}
			    	kkagdugiSetting = true;
			    	kkagdugi = seller;
			    	kkagdugi2 = seller2;
			    	calculatePanel.setKkagdugi(kkagdugi, kkagdugi2);
			    	calculatePanel.setPlayer(players, totalPlayer);
					int countGwang = gwangCB.getSelectedIndex(); // 파는 광 개수
					int price = countGwang * pricePerGwang;
					
					for (int i = 0; i < totalPlayer; i++) {
						if (i == seller || i == seller2 || i == exclusionPlayer) {
							continue;
						}
						else {
							players[i].setPayment(seller, price);
							players[seller].setPayment(i, -price);
						}
					}
					
					countGwang = gwangCB2.getSelectedIndex();
					price = countGwang * pricePerGwang;
					if (totalPlayer == 5) {
						for (int i = 0; i < totalPlayer; i++) {
							if (i == seller || i == seller2 || i == exclusionPlayer) {
								continue;
							}
							else {
								players[i].setPayment(seller2, price);
								players[seller2].setPayment(i, -price);
							}
						}
					}
					centerPanel.setPlayer(players, totalPlayer);
			    }
			    break;
			}
			case "결과 정산": {
				if (kkagdugi == -1 && totalPlayer >= 4) {
		    		JOptionPane.showMessageDialog(null, "광팔이(또는 깍두기)를 입력하세요", "확인", JOptionPane.ERROR_MESSAGE);
		    		return;
				}
				calculateDialog.setVisible(true);
				break;
			}
			case "첫 뻑": {
				JPanel myPanel = new JPanel(new GridLayout(1, 3));
				JComboBox<Player> firstBbuckCB = new JComboBox<Player>();
			    
			    // 현재 참여 중인 플레이어들을 콤보박스에 추가
			    for (int i = 0; i < totalPlayer; i++) {
			    	firstBbuckCB.addItem(players[i]);
			    }
			    myPanel.add(firstBbuckCB);
			    int result = JOptionPane.showConfirmDialog(null, myPanel, "첫 뻑", JOptionPane.OK_CANCEL_OPTION);
			    if (result == JOptionPane.OK_OPTION) {
			    	int firstBbuck = firstBbuckCB.getSelectedIndex();
			    	if (priceFirstBbuck == 0) {
			    		JOptionPane.showMessageDialog(null, "첫 뻑 가격을 입력하세요", "확인", JOptionPane.ERROR_MESSAGE);
			    		return;
			    	}
			    	else if (kkagdugi == -1) {
			    		JOptionPane.showMessageDialog(null, "광팔이(또는 깍두기)를 입력하세요", "확인", JOptionPane.ERROR_MESSAGE);
			    		return;
			    	}
			    	else {
						for (int i = 0; i < totalPlayer; i++) {
							if (i == firstBbuck || i == kkagdugi || i == kkagdugi2) {
								continue;
							}
							else {
								players[i].setPayment(firstBbuck, priceFirstBbuck);
								players[firstBbuck].setPayment(i, -priceFirstBbuck);
							}
						}
						centerPanel.setPlayer(players, totalPlayer);
					    }
			    }
			    break;
			}
			case "총통": {
				JPanel myPanel = new JPanel(new GridLayout(1, 3));
				JComboBox<Player> chongTongCB = new JComboBox<Player>();
			    
			    // 현재 참여 중인 플레이어들을 콤보박스에 추가
			    for (int i = 0; i < totalPlayer; i++) {
			    	chongTongCB.addItem(players[i]);
			    }
			    myPanel.add(chongTongCB);
			    int result = JOptionPane.showConfirmDialog(null, myPanel, "총통", JOptionPane.OK_CANCEL_OPTION);
			    if (result == JOptionPane.OK_OPTION) {
			    	int chongTong = chongTongCB.getSelectedIndex();
			    	if (priceChongTong == 0) {
			    		JOptionPane.showMessageDialog(null, "총통 가격을 입력하세요", "확인", JOptionPane.ERROR_MESSAGE);
			    	}
			    	else {
			    		reset();
			    		calculatePanel.reset();
						for (int i = 0; i < totalPlayer; i++) {
							if (i == chongTong) {
								continue;
							}
							else {
								players[i].setPayment(chongTong, priceChongTong);
								players[chongTong].setPayment(i, -priceChongTong);
							}
						}
						centerPanel.setPlayer(players, totalPlayer);
					    }
			    }
			    break;
			}
			case "금액 수정": {
				JPanel myPanel = new JPanel(new GridLayout(2, 1));
				JPanel panel1 = new JPanel(new GridLayout(2, 2));
				JPanel panel2 = new JPanel(new GridLayout(1, 3));
				
				JLabel plusLabel = new JLabel("+");
				plusLabel.setHorizontalAlignment(JLabel.CENTER);
				JLabel minusLabel = new JLabel("-");
				minusLabel.setHorizontalAlignment(JLabel.CENTER);
				JComboBox<Player> player1CB = new JComboBox<Player>();
				JComboBox<Player> player2CB = new JComboBox<Player>();
				for (int i = 0; i < players.length; i++) {
					player1CB.addItem(players[i]);
					player2CB.addItem(players[i]);
				}
				JTextField wonTF = new JTextField(5);
				JLabel wonLabel = new JLabel("원");
				
				panel1.add(plusLabel);
				panel1.add(player1CB);
				panel1.add(minusLabel);
				panel1.add(player2CB);
				panel2.add(new JLabel("금액"));
				panel2.add(wonTF);
				panel2.add(wonLabel);
				
				myPanel.add(panel1);
				myPanel.add(panel2);
				int result = JOptionPane.showConfirmDialog(null, myPanel, "금액 수정", JOptionPane.OK_CANCEL_OPTION);
				
				if (result == JOptionPane.OK_OPTION) {
					Player p1 = (Player) player1CB.getSelectedItem();
					Player p2 = (Player) player2CB.getSelectedItem();
					if (p1 == null || p2 == null || wonTF == null) {
						return;
					}
					int seller =  p1.getIndex();
					int charger = p2.getIndex();
					if (seller == charger) {
						JOptionPane.showMessageDialog(null, "두 플레이어가 같습니다.", "확인", JOptionPane.ERROR_MESSAGE);
					}
					int price = Integer.parseInt(wonTF.getText());
					
					players[charger].setPayment(seller, price);
					players[seller].setPayment(charger, -price);
					centerPanel.setPlayer(players, totalPlayer);
				}
				break;
			}
			}
		}
	}
}
