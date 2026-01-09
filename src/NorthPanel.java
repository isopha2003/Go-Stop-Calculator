import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NorthPanel extends JPanel {
	private WestPanel westPanel;
	private CenterPanel centerPanel;
	
	private Color textColor = Color.black;
	private Color redColor = new Color(174, 10, 28);
	private Color greenColor = new Color(43, 102, 34);
	
	private JLabel nameLabel = new JLabel("이름");
	private JTextField nameTF = new JTextField(10);
	private JButton addBtn = new JButton("추가");
	
	private int totalPlayer = 0;
	private Player players[] = new Player[5];
	
	
	private JLabel pricePerPointLabel = new JLabel("점당");
	private JTextField pricePerPointTF = new JTextField(10);
	private JButton saveBtn = new JButton("저장");
	
	private int pricePerPoint = 0;
	
	public NorthPanel(WestPanel westPanel, CenterPanel centerPanel) {
		this.westPanel = westPanel;
		this.centerPanel = centerPanel;
		
		setLayout(new FlowLayout());
		setBackground(greenColor);
		
		nameLabel.setForeground(textColor);
		add(nameLabel);
		
		nameTF.setForeground(textColor);
		nameTF.setBackground(redColor);
		nameTF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (nameTF.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "이름을 입력하세요.", "확인", JOptionPane.ERROR_MESSAGE);
				}
				else if (totalPlayer >= 5) {
					JOptionPane.showMessageDialog(null,  "5명까지 참가할 수 있습니다.", "확인", 
							JOptionPane.ERROR_MESSAGE);
					nameTF.setText("");
					westPanel.setPlayer(players, totalPlayer);
					centerPanel.setPlayer(players, totalPlayer);
				}
				else {
					String playerName = nameTF.getText();
					players[totalPlayer] = new Player(playerName, totalPlayer);
					totalPlayer++;
					nameTF.setText("");
					westPanel.setPlayer(players, totalPlayer);
					centerPanel.setPlayer(players, totalPlayer);
				}
			}
		});
		add(nameTF);
		
		addBtn.setForeground(Color.BLACK);
		addBtn.setBackground(redColor);
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (nameTF.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "이름을 입력하세요.", "확인", JOptionPane.ERROR_MESSAGE);
				}
				else if(totalPlayer >= 5) {
					JOptionPane.showMessageDialog(null,  "5명까지 참가할 수 있습니다.", "확인", 
							JOptionPane.ERROR_MESSAGE);
					nameTF.setText("");
					westPanel.setPlayer(players, totalPlayer);
					centerPanel.setPlayer(players, totalPlayer);
				}
				else {
					String playerName = nameTF.getText();
					players[totalPlayer] = new Player(playerName, totalPlayer);
					totalPlayer++;
					nameTF.setText("");
					westPanel.setPlayer(players, totalPlayer);
					centerPanel.setPlayer(players, totalPlayer);
				}
			}
		});
		add(addBtn);
		
		pricePerPointLabel.setForeground(textColor);
		add(pricePerPointLabel);
		
		pricePerPointTF.setForeground(textColor);
		pricePerPointTF.setBackground(redColor);
		pricePerPointTF.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pricePerPoint = Integer.parseInt(pricePerPointTF.getText());
				westPanel.setPricePerPoint(pricePerPoint);
				pricePerPointTF.setText("");
				JOptionPane.showMessageDialog(null,  "점당 점수가 저장되었습니다: " + pricePerPoint, "확인", JOptionPane.ERROR_MESSAGE);
			}
		});
		add(pricePerPointTF);
		
		saveBtn.setForeground(Color.BLACK);
		saveBtn.setBackground(redColor);
		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pricePerPoint = Integer.parseInt(pricePerPointTF.getText());
				westPanel.setPricePerPoint(pricePerPoint);
				pricePerPointTF.setText("");
				JOptionPane.showMessageDialog(null,  "점당 점수가 저장되었습니다: " + pricePerPoint, "확인", JOptionPane.ERROR_MESSAGE);
			}
		});
		add(saveBtn);
	}
	public int getPricePerPoint() {
		return pricePerPoint;
	}
}
