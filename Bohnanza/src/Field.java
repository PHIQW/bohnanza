
public class Field {

	public Card bean;
	public int numBeans = 0;
	
	public void addBean(Card c){
		numBeans++;
	}
	
	public int harvest(){
		
		
		
		numBeans = 0;//reset field after harvesting
		return 0;
	}
}
