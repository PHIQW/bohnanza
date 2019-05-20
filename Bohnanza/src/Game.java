import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Game{

	private final int START_HAND = 5;
	private final int  END_DRAW = 3;
	private final int SHUFFLES_TO_END=3;

	Player[] p;
	private AI ai = new AI();
	private BohnanzaGUI gui;

	private ArrayList<String> rngNames = new ArrayList<String>
	(Arrays.asList("Bob", "Steve", "Skydoesminecraft", "MIckMouse",
			"Kachow","eztherebuddy", "Sample Text", "Tyron", 
			"Jameel", "Philllip", "Alac", "Abhirup"));

	private Deck deck;
	private int numP;

	private Trading trade;

	public Game (Deck d, int p) {
		deck = d;
		numP = p;
	}

	public void play(){

		Scanner input = new Scanner(System.in);
		Random rng = new Random();

		//STARTING
		//set up players
		p = new Player[numP];//array so you can't add players

		//name players
		for(int i = 0; i < numP; i++){
			int x =rng.nextInt(rngNames.size());
			p[i] = new Player(rngNames.get(x));
			rngNames.remove(x);

		}

		//add starting cards for each player
		for (int  i = 0; i < p.length; i++) {
			p[i].draw(deck.draw(START_HAND));
			System.out.println(Arrays.toString(p[i].getHand().toArray()));
		}

		//set up gui
		gui = new BohnanzaGUI(p, deck);

		//MAIN GAME
		while(deck.getNumRe() < SHUFFLES_TO_END){

			for (int  i = 0; i < p.length; i++){


				//				System.out.printf("Player:%s\t%s\n",p[i].getName(),Arrays.toString(p[i].getHand().toArray()));
				//Plant cards
				p[i].plant(p[i].getTophand(), true);

				//				for (int y = 0; y < p[i].getField().size();y++)
				//					System.out.printf("FIELD:%d\t\t%s\n",y ,Arrays.toString(p[i].getField().get(y).toArray()));
				//
				//				System.out.printf("Player:%s\t%s\n",p[i].getName(),Arrays.toString(p[i].getHand().toArray()));

				discard(p[i]);
				gui.refreshP(i, p[i], deck);

				//plant second card
				//				System.out.println("Plant another bean?(y=0,n=1)");
				if (0 == ai.plS(p[i])) {
					p[i].plant(p[i].getTophand(), false);
					//					System.out.printf("Player:%s\t%s\n",p[i].getName(),Arrays.toString(p[i].getHand().toArray()));

					//					for (int y = 0; y < p[i].getField().size();y++)
					//						System.out.printf("FIELD:%d\t\t%s\n",y ,Arrays.toString(p[i].getField().get(y).toArray()));
				}

				discard(p[i]);
				gui.refreshP(i, p[i], deck);

				//end game if deck was reshuffled 3 times
				if(deck.getNumRe() >= SHUFFLES_TO_END)
					break;

				//trade
				p[i].getTrade(deck.draw(2));

				ai.createOffer(p[i]);

				boolean traded = false;
				int j=i;

				for(int k=0;k<p.length-1;k++){

					j++;
					if(j>=p.length)
						j=0;

					//if already traded
					if(traded)
						break;



					//ai makes counter offer
					if(ai.checkDealC(p[j])){
						ai.counterOffer(p[j],p[i]);
					}else
						break;//go to next player

					//original 'player' checks offer and trades if worth
					if(ai.checkDealO(p[i])){
						System.out.println("Traded with " + p[j].getName());
						trade(i,j);
						traded = true;
					}

					if(traded == false){
						if(p[i].getTrading().size()>0)
							for(int z=0;z<p[i].getTrading().size();z++){
								p[i].plant(p[i].getTrading().get(z),true);
							}
						p[i].getTrading().clear();
					}
				}


				for(int k=0;k<p.length;k++)
					discard(p[k]);

				gui.refreshAll(p, deck);

				//end game if deck was reshuffled 3 times
				//				System.out.println(deck.getNumRe());
				if(deck.getNumRe() >= SHUFFLES_TO_END)
					break;

				//draw cards at end of turn
				while(p[i].getHand().size()<5)
					p[i].draw(deck.draw(1));

				gui.refreshP(i, p[i], deck);

			}
		}

		//ENDING GAME
		//harvest all, count coins, determine winner

		for(int i = 0; i<p.length;i++){
			for(int f = 0; f<p[i].getField().size();f++)//Harvest each field for each player
				if(p[i].getField().get(f).size()>0)
					p[i].harvest(f);

		}

		//refersh gui again
		gui.refreshAll(p,deck);



		//rank players
		p  = rankPlayers(p);

		//		gui.rank(p);

		System.out.println("\nFirst to Last");
		for(Player end:p)
			System.out.println(end.getName());

	}

	private void trade(int ori, int cnt) {

		//plant trades received for original player
		for(int i=0;i<ai.getcOffer().size();i++)
			p[ori].plant(ai.getcOffer().get(0), true);

		//remove trade offer from other player's hand
		for(int i=0;i<ai.getcOffer().size();i++)
			for(int k=0;k<p[cnt].getHand().size();k++)
				if(ai.getcOffer().get(0)==p[cnt].getHand().get(k)){
					p[cnt].getHand().remove(i);
					break;
				}
		ai.getcOffer().clear();


		//plant trades for counter offer
		for(int i=0;i<ai.getTempOffer().size();i++)
			p[cnt].plant(ai.getTempOffer().get(0), true);

		//remove trade offer from original player hand and trade area
		for(int i=0;i<ai.getTempOffer().size();i++)
			for(int k=0;k<p[ori].getTrading().size();k++)
				if(ai.getTempOffer().get(i)==p[ori].getTrading().get(k)){
					p[ori].getTrading().remove(k);
					ai.getTempOffer().remove(i);
					break;
				}

		for(int i=0;i<ai.getTempOffer().size();i++)
			for(int k=0;k<p[ori].getHand().size();k++)
				if(ai.getTempOffer().get(i)==p[ori].getHand().get(k)){
					p[ori].getHand().remove(k);
					break;
				}
		ai.getTempOffer().clear();


	}

	private void discard(Player k){
		deck.discard(k.getTempDiscard());
	}


	//first to last
	private Player[] rankPlayers(Player[] p){

		Player temp;

		for(int i = 1;i<p.length;i++)
			while(p[i].returnGold()>p[i-1].returnGold()){
				//				System.out.println("SWAP");
				temp = p[i];
				p[i]=p[i-1];
				p[i-1] = temp;
				if(i>1)
					i--;
			}

		return p;
	}

}