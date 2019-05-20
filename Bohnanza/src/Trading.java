import java.util.ArrayList;

public class Trading {
	
	private ArrayList<Card> trade = new ArrayList<Card>();
	
	public void clear() {
		trade.clear();
	}
	
	public Card take(int n) {
		
		return trade.get(n);
	}

	public void addCard(ArrayList<Card> trading) {
		trade.addAll(trading);
		
	}

	public ArrayList<Card> getTrade() {
		return trade;
	}

	public void setTrade(ArrayList<Card> trade) {
		this.trade = trade;
	}

}
