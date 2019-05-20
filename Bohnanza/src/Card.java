import java.util.Arrays;

public class Card {

	private String name;
	private int[] harvest;

	public Card(String s, int h1,int h2, int h3, int h4){//h stands for harvest

		name = s;
		int[] h = {h1, h2, h3, h4};
		harvest = h;

	}//end constructor

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int[] getHarvest() {
		return harvest;
	}

	public void setHarvest(int[] harvest) {
		this.harvest = harvest;
	}

	@Override
	public String toString() {
		
		return name;
//		return "Card [name=" + name + ", harvest=" + Arrays.toString(harvest) + "]";
	}

	public int harvest(int n){
		
		for (int i = harvest.length - 1; i >= 0; i--)
			if (n >= harvest[i] && harvest[i] != -1)
				return i + 1;

		
		return 0;
		
	}


}