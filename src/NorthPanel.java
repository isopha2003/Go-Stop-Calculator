import javax.swing.JPanel;
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
					calculatePanel.setPricePerPoint(pricePerGwang);
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
					calculatePanel.setPricePerPoint(priceFirstBbuck);
					JOptionPane.showMessageDialog(null,  "첫 뻑 가격이 저장되었습니다: " + priceFirstBbuck, "확인", JOptionPane.INFORMATION_MESSAGE);
				}
				break;
			}
			case "총통 가격": {
				String point = JOptionPane.showInputDialog("총통 가격을 입력하세요.");
				if (point != null) {
					priceChongTong = Integer.parseInt(point);
					calculatePanel.setPricePerPoint(priceChongTong);
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
		JMenuItem [] menuItem = new JMenuItem [4];
		String calculateTitle[] = {"광팔이(깍두기)", "결과 정산","첫 뻑", "총통"};
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
				JPanel myPanel = new JPanel();
				JComboBox<Player> sellerCB = new JComboBox<>();
				JComboBox<Player> sellerCB2 = new JComboBox<>();
				if (totalPlayer == 5) {
					myPanel.setLayout(new GridLayout(2, 3));
				}
				
				else {
					myPanel = new JPanel(new GridLayout(1, 3));
				}
			    
			    // 현재 참여 중인 플레이어들을 콤보박스에 추가
			    for (int i = 0; i < totalPlayer; i++) {
			    	if (i == kkagdugi || i == kkagdugi2) {
			    		continue;
			    	}
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
			    myPanel.add(sellerCB);
			    myPanel.add(gwangCB);
			    myPanel.add(countLabel);
			    if (totalPlayer == 5) {
			    	myPanel.add(sellerCB2);
				    myPanel.add(gwangCB2);
				    myPanel.add(countLabel2);
			    }
			    int result = JOptionPane.showConfirmDialog(null, myPanel, "광팔이", JOptionPane.OK_CANCEL_OPTION);
			    if (result == JOptionPane.OK_OPTION) {
			    	Player p = (Player)sellerCB.getSelectedItem();
			    	if (p == null) {
			    		return;
			    	}
			    	int seller = p.getIndex();
			    	int seller2 = -1;
			    	Player p2;
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
			    	}
			    	kkagdugiSetting = true;
			    	kkagdugi = seller;
			    	kkagdugi2 = seller2;
			    	calculatePanel.setKkagdugi(kkagdugi, kkagdugi2);
			    	calculatePanel.setPlayer(players, totalPlayer);
					int countGwang = gwangCB.getSelectedIndex(); // 파는 광 개수
					int price = countGwang * pricePerGwang;
					
					for (int i = 0; i < totalPlayer; i++) {
						if (i == seller || i == seller2) {
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
							if (i == seller || i == seller2) {
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
			}
		}
	}
}
