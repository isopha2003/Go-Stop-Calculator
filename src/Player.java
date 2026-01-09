
public class Player {
	private String playerName;
	private int playerIndex = 0;
	private int payment[] = new int [5];
	
	public Player(String playerName, int playerIndex) {
		this.playerName = playerName;
		this.playerIndex = playerIndex;
	}
	public String getPlayerName() {
		return playerName;
	}
	@Override
	public String toString() {
		return playerName;
	}
	public int getIndex() {
		return playerIndex;
	}
	public void setPayment(int index, int price) {
		payment[index] += price;
	}
	public int getPayment(int index) {
		return payment[index];
	}
}
