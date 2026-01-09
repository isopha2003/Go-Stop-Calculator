import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class CenterPanel extends JPanel {
	private Font mainFont = new Font("맑은 고딕", Font.BOLD, 20);
	private Font subFont = new Font("맑은 고딕", Font.BOLD, 15);
	// private Color redColor = new Color(174, 10, 28);
	// private Color greenColor = new Color(8, 54, 33);
	private Player players[];
	private int totalPlayer;
	
	private FirstLinePanel firstLinePanel;
	private SecondLinePanel secondLinePanel;
	
	public CenterPanel() {
		// setBackground(greenColor);
	}
	public void setPlayer(Player players[], int totalPlayer) {
		this.players = players;
		this.totalPlayer = totalPlayer;
		this.removeAll();
		this.setLayout(new GridLayout(3, 1, 10, 10));
		
		firstLinePanel = new FirstLinePanel(players, totalPlayer);
		secondLinePanel = new SecondLinePanel(players, totalPlayer);
		setPanel();
		add(firstLinePanel);
		add(secondLinePanel);
		
		revalidate();
		repaint();
	}
	public void setPanel() {
		firstLinePanel.setOpaque(false);
		secondLinePanel.setOpaque(false);
	}
	class FirstLinePanel extends JPanel {
		public FirstLinePanel(Player players[], int totalPlayer) {
			// this.setBackground(greenColor);
			setLayout(new GridLayout(1, totalPlayer, 10, 10));
			for (int i = 0; i < totalPlayer; i++) {
				JLabel label = new JLabel(players[i].getPlayerName());
				label.setFont(mainFont);
				label.setForeground(Color.BLACK);
				label.setOpaque(false);
				label.setHorizontalAlignment(JLabel.CENTER);
				// label.setBackground(redColor);
				add(label);
			}
		}
	}
	class SecondLinePanel extends JPanel {		
		public SecondLinePanel(Player players[], int totalPlayer) {
			// setBackground(greenColor);
			setLayout(new GridLayout(1, totalPlayer - 1, 10, 10));
			for (int i = 0; i < totalPlayer; i++) {
				ImagePanel infoPanel = new ImagePanel();
				infoPanel.setBorder(new EmptyBorder(60, 220 - (30 * totalPlayer), 60, 220 - (30 * totalPlayer)));
				infoPanel.setLayout(new GridLayout(totalPlayer, 2, 5, 5));
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
						// nameLabel.setBackground(redColor);
						priceLabel.setForeground(Color.BLACK);
						nameLabel.setHorizontalAlignment(JLabel.CENTER);
						priceLabel.setHorizontalAlignment(JLabel.CENTER);
						nameLabel.setOpaque(false);
						priceLabel.setOpaque(false);
						infoPanel.add(nameLabel);
						infoPanel.add(priceLabel);
					}
				}
				add(infoPanel);
			}
		}
		class ImagePanel extends JPanel {
		    private ImageIcon icon = new ImageIcon("images/theBackOfTheCard.png");
		    private Image img = icon.getImage();
		    private int w = 180, h = 400;
		    
		    public ImagePanel() {
		        setOpaque(false);
		    }

		    @Override
		    public void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        
		        // (총 너비 - 이미지 크기) / 2: 이미지 중앙 정렬 공식
		        int x = (this.getWidth() - w) / 2;
		        int y = (this.getHeight() - h) / 2; 

		        if (img != null) {
		            g.drawImage(img, x, y, w, h, this);
		        }
		    }

		    public void reSize(int w, int h) {
		        this.w = w;
		        this.h = h;
		        repaint();
		    }
		}
	}
}