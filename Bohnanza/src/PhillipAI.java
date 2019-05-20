import java.util.ArrayList;

public class PhillipAI {

	/*
	 * The idea for this AI is you trade so that your hand is ready-to-plant for
	 * next turn This can be achieved by: -trading away cards in your current hand
	 * so that the first 2 cards in your hand can be put into your fields -get cards
	 * from trades so that your field(s) are set up for your hand next turn
	 */

	private ArrayList<Card> tempOffer = new ArrayList<Card>();
	private ArrayList<Card> cOffer = new ArrayList<Card>();
	private int[] pubProb = { 20, 18, 16, 14, 12, 10, 8, 6 };// hard coded from csv at start

	public int plS(Player p) {// plant second

		// check hand size
		if (p.getHand().size() == 0)
			return 1;

		// check empty
		for (int i = 0; i < p.getField().size(); i++)
			if (p.getField().get(i).size() == 0)
				return 0;

		// check match
		for (int i = 0; i < p.getField().size(); i++)
			if (p.getHand().get(0) == p.getField().get(i).get(0))
				return 0;

		// no match
		return 1;

	}

	// player says what they want to give away
	public void createOffer(Player p) {

		if (p.getTrading().size() > 0)
			tempOffer.addAll(p.getTrading());

		p.getTrading().clear();

		ArrayList<Card> tempHand = (ArrayList<Card>) p.getHand().clone();

		int value = 0;

		while (value < 2 && tempHand.size() <= 3) {

			// try to set up hand with trade for next turn b/c you want the first 2 cards in
			// hand to be playable next turn
			for (int i = 0; i < p.getField().size(); i++)
				for (int x = 0; x < 2; x++)// 2 b/c you want first 2 cards to be playable
					if (p.getField().get(i).size() > 0 && p.getHand().size() >= 2 + x && tempHand.size() < 3) {// if
																												// there
																												// are
																												// cards
																												// in
																												// field
						if (tempHand.get(x) != p.getField().get(i).get(0)
								&& tempHand.get(x + 1) == p.getField().get(i).get(0)) {
							tempOffer.add(tempHand.get(x));
							tempHand.remove(x);
						}
					}

			value++;

		} // end while

	}

	// next player makes an offer for the cards being given away
	public void counterOffer(Player p, Player o) {

		ArrayList<Card> tempHand = (ArrayList<Card>) p.getHand().clone();

		int value = 0;

		while (value < 2 || tempHand.size() <= 3) {

			// try to set up hand with trade for next turn b/c you want the first 2 cards in
			// hand to be playable next turn
			for (int i = 0; i < p.getField().size(); i++)
				for (int x = 0; x < 2; x++)// 2 b/c you want first 2 cards to be playable
					if (p.getField().get(i).size() > 0 && p.getHand().size() >= 2 + x && tempHand.size() < 3) {// if
																												// there
																												// are
																												// cards
																												// in
																												// field
						if (tempHand.get(x) != p.getField().get(i).get(0)
								&& tempHand.get(x + 1) == p.getField().get(i).get(0)) {
							tempOffer.add(tempHand.get(x));
							tempHand.remove(x);
						}
					}
			value++;

		} // end while

		// if you have empty fields, make offer more valuable to other player by
		// offering cards in their field
		for (int f = 0; f < o.getField().size(); f++)
			if (o.getField().get(f).size() == 0)
				for (int i = 0; i < tempHand.size(); i++)
					if (o.getField().get(f).size() > 0)
						if (tempHand.get(i) == o.getField().get(f).get(0)) {
							cOffer.add((tempHand.get(i)));
							tempHand.remove(i);
							break;
						}

	}

	// evaluates if cards are worth it and if it's worth to trade
	public boolean checkDealC(Player p) {// counteroffer

		// fields are empty and cards are in your hand
		for (int f = 0; f < p.getField().size(); f++)// temp
			for (int i = 0; i < p.getHand().size(); i++)
				for (int k = 0; k < tempOffer.size(); k++)
					if (p.getHand().get(i) == tempOffer.get(k))
						return true;

		int val = 0;
		// accept if cards in opposing offer are in your fields
		for (int f = 0; f < p.getField().size(); f++)
			for (int i = 0; i < tempOffer.size(); i++)
				if (p.getField().get(f).size() > 0)
					if (tempOffer.get(i) == p.getField().get(f).get(0))
						val++;

		// field is developed enough and cards offer has cards in your first 2 slots in
		// your hand
		for (int h = 0; h < 2; h++)
			for (int i = 0; i < tempOffer.size(); i++)
				if (p.getHand().get(h) == tempOffer.get(i))
					val++;

		if (val >= tempOffer.size() - 1 && val >= 2)
			return true;

		// deal not worth
		return false;
	}

	// evaluates if cards are worth it and if it's worth to trade
	public boolean checkDealO(Player p) {// offer

		// fields are empty and cards are in your hand
		if (p.getField().get(0).size() == 0 && p.getField().get(1).size() == 0)
			for (int i = 0; i < p.getHand().size(); i++)
				for (int k = 0; k < cOffer.size(); k++)
					if (p.getHand().get(i) == cOffer.get(k))
						return true;

		int val = 0;
		// accept if cards in opposing offer are in your fields
		for (int f = 0; f < p.getField().size(); f++)
			for (int i = 0; i < cOffer.size(); i++)
				if (p.getField().get(f).size() > 0)
					if (cOffer.get(i) == p.getField().get(f).get(0))
						val++;

		// field is developed enough and cards offer has cards in your first 2 slots in
		// your hand
		for (int h = 0; h < 2; h++)
			for (int i = 0; i < cOffer.size(); i++)
				if (p.getHand().get(h) == cOffer.get(i))
					val++;

		if (val >= 2)
			return true;

		// deal not worth
		return false;

	}

	public ArrayList<Card> getTempOffer() {
		return tempOffer;
	}

	public void setTempOffer(ArrayList<Card> tempOffer) {
		this.tempOffer = tempOffer;
	}

	public ArrayList<Card> getcOffer() {
		return cOffer;
	}

	public void setcOffer(ArrayList<Card> cOffer) {
		this.cOffer = cOffer;
	}

}
