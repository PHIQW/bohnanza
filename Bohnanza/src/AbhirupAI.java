import java.util.ArrayList;

public class AbhirupAI {

	/*
	 * This AI is essentially in "SOLO PLAYER" mode. It neglects each trade offer
	 * and doesn't make trade offers either. It tries to win on its on.
	 * 
	 */

	public int plS(Player p) { // plant second seed

		// checks hand size
		if (p.getHand().size() == 0)

			return 1;

		// if there are less than 5 cards, then plant
		for (int i = 0; i <= 5; i++) {

			return 1;

		}

		return 1;

	}

	public boolean checkDealO(Player p) { // makes offer

		// decline trade
		return false;

	}

	public boolean checkDealC(Player p) { // makes counteroffer

		// decline trade
		return false;

	}

}
