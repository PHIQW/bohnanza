import java.util.ArrayList;
import java.util.Arrays;

public class Player{

	private String name;

	private  ArrayList<Card> hand = new ArrayList<Card>();

	private int gold = 0;

	private  ArrayList<ArrayList<Card>> field = new ArrayList<ArrayList<Card>>();

	private ArrayList<Card> trading = new ArrayList<Card>();

	private ArrayList<Card> removed = new ArrayList<Card>();
	
	//player can't directly access deck, so this would be put into discard pile by game class
	private  ArrayList<Card> tempDiscard = new ArrayList<Card>();

	public Player (String name){

		this.name = name;

		ArrayList<Card> temp = new ArrayList<Card>();
		ArrayList<Card> temp1 = new ArrayList<Card>();

		//create 2 fields
		field.add(temp);
		field.add(temp1);

	}

	public ArrayList<Card> getTempDiscard() {
		ArrayList<Card> k = (ArrayList<Card>) tempDiscard.clone();
		tempDiscard.clear();
		return k;
	}

	public String getName() {
		return name;
	}

	public void removeCard(int n) {

		hand.remove(0);
	}

	public void fieldTest() {

		for (int i = 0 ; i < hand.size(); i++)
			field.get(0).add(hand.get(i));

	}

	public  ArrayList<ArrayList<Card>> getField() {
		return field;
	}

	public  int returnGold() {
		return gold;
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	@Override
	public String toString() {
		return "Player [name=" + name + "]";
	}

	public void draw( Card [] cards){

		System.out.println(getName());

		for (int i = 0; i < cards.length; i++)
			hand.add(cards[i]);
	}

	public  void harvest(int n) {

		System.out.println(field.get(n).size());
		gold += getGold(field.get(n).size(), n);
	}

	public void getTrade(Card[] cards) {

		for (int  i = 0 ; i < cards.length; i ++)
			trading.add(cards[i]);

		for (int  x = 0; x < trading.size(); x++)
			if (canPlant(trading.get(x)) == true) {
				plant(trading.get(x), false);
				trading.remove(x);
			}
		

	}

	public ArrayList<Card> getTrading() {
		return trading;
	}

	public void setTrading(ArrayList<Card> trading) {
		this.trading = trading;
	}

	public boolean canPlant(Card c) {

		for (int i = 0; i < field.size(); i++)
			if (field.get(i).isEmpty() == true || c.getName() == field.get(i).get(0).getName())
				return true;

		return false;
	}

	public void plant(Card c, boolean m) {

		boolean did = false;
		int u;

		//check all fields until u can plant something
		for (int i = 0; i < field.size(); i++)
			if(field.get(i).isEmpty() == true || c.getName() == field.get(i).get(0).getName()) {
				field.get(i).add(c);
				did = true;
				return;
			}
		
		//ai decide which field to plant if there are 2
		if(field.get(0).size()>0 && field.get(1).size()>0 && did==false){
			int f = AI.decField(this);
			harvest(f);
			field.get(f).add(c);
			did = true;
		}
		
		//if it couldn't plant, and m==true(you HAVE to plant)
		//determine field to harvest, and plant again
		if (m == true && did == false) {

			if(field.get(0).size() == 1 && field.get(1).size() > 1)
				u = 1;
			else 
				u = 0;

			harvest(u);

			//recursion
			plant(c, false);

		}
	}



	public Card getTophand() {

		Card temp = hand.get(0);

		hand.remove(0);

		return temp;
	}

	public Card getTop (){

		Card i = hand.get(0);

		hand.remove(0);

		return i;

	}

	public int getGold(int n, int h) {

		System.out.println("harvest");

		//take gold values of first bean
		int [] harvest = field.get(h).get(0).getHarvest();

		//Start at the back of gold values
		for (int  i = harvest.length - 1; i >= 0; i-- ){

			//if u have a bigger number
			if (n >= harvest[i] && harvest[i] > 0) {

				System.out.println(harvest[i]);

				//remove number of beans
				for (int j = 0; j < harvest[i]; j++){
					removed.add(field.get(h).get(0));
					field.get(h).remove(0);
				}
				//discard the rest
				tempDiscard.addAll(field.get(h));//put this in discard afterwards
				field.get(h).clear();

				//return gold integer
				return i+1;
			}else if(harvest[i] == -2 && n >= harvest[i-1]){//special case for garden bean
				
				for (int j = 0; j < harvest[i]; j++)
					field.get(h).remove(0);

				//discard the rest
				tempDiscard.addAll(field.get(h));//put this in discard afterwards
				field.get(h).clear();
				
				return 3;
			}
		}
		//return nothing for insufficient beans
		tempDiscard.addAll(field.get(h));//put this in discard
		field.get(h).clear();
		return 0;
	}

}
