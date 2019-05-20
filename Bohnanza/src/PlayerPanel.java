import java.util.ArrayList;
import java.awt.*;

import javax.swing.*;



public class PlayerPanel extends JPanel{

	private final Font PLAYER_FONT = new Font("Comic Sans MS", Font.BOLD, 14);
	private final int CARD_X = 100;
	private final int CARD_Y = 125;
	private final int PANEL_X = 500;
	private final int PANEL_Y = 320;

	private ArrayList<JLabel> hand = new ArrayList<JLabel>();
	private ArrayList<JLabel> field = new ArrayList<JLabel>();
	private ArrayList<JLabel> fieldNum = new ArrayList<JLabel>();
	private JLabel[] playerInfo = new JLabel[2];

	public PlayerPanel(Player p){

		setUpPanel();
		setUpLabels(p.getName(), p.returnGold());
		setUpHand(p.getHand());
		setUpField(p.getField());

	}

	public void setUpPanel(){

		setLayout(null);
		setBackground(new Color(10, 100, 30));
		setSize(new Dimension(PANEL_X, PANEL_Y));//set preferred size breaks the gui
		setVisible(true);

	}

	public void setUpLabels(String n, int g){

		for(int i=0;i<playerInfo.length;i++){
			playerInfo[i] = new JLabel();
			playerInfo[i].setBounds(200*i, 0, 200, 20);
			playerInfo[i].setFont(PLAYER_FONT);
			playerInfo[i].setForeground(Color.WHITE);
			playerInfo[i].setVisible(true);
			add(playerInfo[i]);
		}

		playerInfo[0].setText(n);
		playerInfo[1].setText("Gold: " + Integer.toString(g));

	}

	public void setUpHand(ArrayList<Card> h){

		for(int k=0;k<h.size();k++){
			hand.add(new JLabel());
			hand.get(k).setBounds(CARD_X*k,playerInfo[0].getHeight(),CARD_X,CARD_Y);
			hand.get(k).setIcon(new ImageIcon(new ImageIcon("cards/" + h.get(k).getName() + ".png").getImage().getScaledInstance(CARD_X, CARD_Y, 0)));
			hand.get(k).setVisible(true);
			add(hand.get(k));

		}			

	}

	private void setUpField(ArrayList<ArrayList<Card>> f) {

		//empty at start
		for(int k=0;k<f.size();k++){
			field.add(new JLabel());
			field.get(k).setBounds(CARD_X*k,hand.get(k).getY()+CARD_Y,CARD_X,CARD_Y);
			field.get(k).setIcon(new ImageIcon(new ImageIcon("cards/Back.png").getImage().getScaledInstance(CARD_X, CARD_Y, 0)));
			field.get(k).setVisible(true);
			add(field.get(k));
		}
		
		//bean counters
		for(int k=0;k<f.size();k++){
			fieldNum.add(new JLabel());
			fieldNum.get(k).setBounds(CARD_X*k,field.get(k).getY()*2-20,20,20);
			fieldNum.get(k).setFont(PLAYER_FONT);
			fieldNum.get(k).setText(Integer.toString(f.get(k).size()));
			fieldNum.get(k).setForeground(Color.WHITE);
			fieldNum.get(k).setVisible(true);
			add(fieldNum.get(k));
		}
		
		
	}

	public ArrayList<JLabel> getHand() {
		return hand;
	}

	public void setHand(ArrayList<JLabel> hand) {
		this.hand = hand;
	}

	public ArrayList<JLabel> getField() {
		return field;
	}

	public void setField(ArrayList<JLabel> field) {
		this.field = field;
	}

	public JLabel[] getPlayerInfo() {
		return playerInfo;
	}

	public void setPlayerInfo(JLabel[] playerInfo) {
		this.playerInfo = playerInfo;
	}

	public ArrayList<JLabel> getFieldNum() {
		return fieldNum;
	}

	public void setFieldNum(ArrayList<JLabel> fieldNum) {
		this.fieldNum = fieldNum;
	}

}