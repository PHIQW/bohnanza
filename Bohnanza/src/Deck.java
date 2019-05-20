import java.util.ArrayList;
import java.util.Random;

public class Deck {

	private int numRe = 0;//number of times deck ran out

	//	private Card s;

	private ArrayList<Card> deck = new ArrayList<Card>();

	private ArrayList<Card> card1 = new ArrayList<Card>();

	private ArrayList<Card> discard = new ArrayList<Card>();

	public ArrayList<Card> getDeck() {
		return deck;
	}

	public void setDeck(ArrayList<Card> deck) {
		this.deck = deck;
	}

	public int getNumRe() {
		return numRe;
	}

	public Card [] draw(int n) {//n represent number of card to be drawn

		Card temp;

		Card [] draw = new Card[n];

		for (int i = 0; i < draw.length; i++)

			if (deck.isEmpty() == false) {
				temp = deck.get(0);
				deck.remove(0);
				draw[i] = temp;
			}
			else {
				i--;
				reshuffle();	//reshuffle when out of cards
			}

		if (deck.size()==0)
			reshuffle();
		
		return draw;

	}

	public void addCard(Card c){//adding a card to the deck
		deck.add(c);

	}

	public void shuffle(){

		card1 = (ArrayList<Card>) deck.clone();

		deck.clear();

		Random r = new Random();

		while(card1.isEmpty() == false) {
			int n = r.nextInt(card1.size());
			deck.add(card1.get(n));
			card1.remove(n);
		}
	}

	@Override
	public String toString() {
		return "Deck " + deck;
	}

	public void discard(ArrayList<Card> dis) {
			discard.addAll(dis);
	}

	public void discard(Card dis){
		discard.add(dis);
	}

	public ArrayList<Card> getDiscard() {
		return discard;
	}

	public void setDiscard(ArrayList<Card> discard) {
		this.discard = discard;
	}

	public void reshuffle(){//would be activated when deck runs out

		numRe++;//when the deck is reshuffled 3 times turn would finish(if in stage 2 or 3) and game would end

		deck = (ArrayList<Card>) discard.clone();
		discard.clear();

		shuffle();
	}

}
