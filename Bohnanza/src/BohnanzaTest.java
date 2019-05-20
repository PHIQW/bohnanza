import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;


public class BohnanzaTest {

	private static Deck d;

	public static void main(String[]args){

		d = new Deck();
		createD();
		d.shuffle();
		System.out.println(d);

		Game g = new Game(d, 4);//4 players
		g.play();
		
	}


	public static void createD(){

		try{

			Scanner input = new Scanner(new File("Cards.csv")).useDelimiter(",");
			
			while(input.hasNextLine()){
				
				input.nextLine();//jump to next line and skip headers
				int n = input.nextInt();//in csv, first column is number of that specific card in the deck
				Card c = new Card(input.next(),input.nextInt(),input.nextInt(),input.nextInt(),input.nextInt());//gets name, then 4 "harvest values"
				
				for(int i = 0; i<n;i++){

					d.addCard(c);//keep adding cards until n number of cards are in the deck

				}//end adding cards


			}//while hasNextLine

			input.close();

		}catch(FileNotFoundException error){

			System.err.println("File not found - check the file name");

		}

	}



}//end bohnanzaTest
