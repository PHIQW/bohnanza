import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class BohnanzaGUI extends JFrame implements ActionListener {

	private final int CARD_X = 100;
	private final int CARD_Y = 125;

	private final int PLAYERP_X = 700;
	private final int PLAYERP_Y = 500;

	private final int FRAME_X = 1300;
	private final int FRAME_Y = 768;
	private final Font TITLE_FONT = new Font("Comic Sans MS", Font.BOLD, 40);
	private final Font LABEL_FONT = new Font("Comic Sans MS", Font.BOLD, 14);

	private JLabel titleLabel = new JLabel("Bohnanza");

	public JPanel guiPanel = new JPanel();
	private JLabel[] deckAndDiscard = new JLabel[2];//deck is 0, discard is 1
	private JLabel[] dLabels = new JLabel[2];//deck is 0, discard is 1
	private PlayerPanel[] playerPanel;
	BohnanzaGUIMenuBar menuBar = new BohnanzaGUIMenuBar();

	public BohnanzaGUI(Player[] p, Deck d) {

		frameSetup();
		gamePanelSetup();
		playerPanelSetup(p);
		setupLabels(p, d);

	}

	private void playerPanelSetup(Player[] p) {

		playerPanel = new PlayerPanel[p.length];

		for(int i=0;i<p.length;i++){
			playerPanel[i] = new PlayerPanel(p[i]);
			guiPanel.add(playerPanel[i]);
		}

		//setting location manually
		playerPanel[0].setLocation(0,80);
		playerPanel[1].setLocation(FRAME_X-playerPanel[1].getWidth(),80);
		playerPanel[2].setLocation(0,420);
		playerPanel[3].setLocation(FRAME_X-playerPanel[3].getWidth(),420);

	}

	public JPanel getPanel() {

		return guiPanel;

	}

	private void frameSetup() {

		ImageIcon userRatingsIcon = new ImageIcon("cards/guiicon.jpg");

		setTitle("Bohnanza");
		setIconImage(userRatingsIcon.getImage());
		setPreferredSize(new Dimension(FRAME_X, FRAME_Y));
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(guiPanel);


	}

	private void gamePanelSetup() {
		guiPanel.setBackground(new Color(158, 209, 199));
		guiPanel.setLayout(null);
		guiPanel.setPreferredSize(new Dimension(FRAME_X, FRAME_Y));
		guiPanel.setVisible(true);

	}

	private void setupLabels(Player[] p, Deck d) {

		//title
		titleLabel.setBounds(540, 0, 400, 100);
		titleLabel.setFont(TITLE_FONT);
		titleLabel.setVisible(true);
		guiPanel.add(titleLabel);

		//deck and discard
		for(int i=0;i<deckAndDiscard.length;i++){
			deckAndDiscard[i] = new JLabel();
			deckAndDiscard[i].setBounds(550+CARD_X*i, 400, CARD_X, CARD_Y);
			deckAndDiscard[i].setVisible(true);
			guiPanel.add(deckAndDiscard[i]);
		}

		//deck should have cards, discard should be empty
		deckAndDiscard[0].setIcon(new ImageIcon(new ImageIcon("cards/" + d.getDeck().get(0) + ".png").getImage().getScaledInstance(CARD_X, CARD_Y, 0)));
		deckAndDiscard[1].setIcon(new ImageIcon(new ImageIcon("cards/Back.png").getImage().getScaledInstance(CARD_X, CARD_Y, 0)));

		//dnd Labels
		for(int i=0;i<dLabels.length;i++){
			dLabels[i] = new JLabel();
			dLabels[i].setFont(LABEL_FONT);
			dLabels[i].setBounds(deckAndDiscard[i].getX(), deckAndDiscard[i].getY()+CARD_Y+20, 100, 20);//hardcoded size
			dLabels[i].setVisible(true);
			guiPanel.add(dLabels[i]);
		}
		//set text
		dLabels[0].setText("Deck:"+d.getDeck().size());
		dLabels[1].setText("Discard:"+d.getDiscard().size());


	}

	public void refreshP(int i, Player p, Deck d){

		//update hand
		for(int k=0;k<p.getHand().size();k++)
			playerPanel[i].getHand().get(k).setIcon(new ImageIcon(new ImageIcon("cards/" + p.getHand().get(k).getName() + ".png").getImage().getScaledInstance(CARD_X, CARD_Y, 0)));

		if(p.getHand().size()<playerPanel[i].getHand().size())
			for(int k = p.getHand().size();k<playerPanel[i].getHand().size();k++)
				playerPanel[i].getHand().get(k).setIcon(null);

		//update field
		for(int k=0;k<p.getField().size();k++)
			if(p.getField().get(k).size()>0){
				playerPanel[i].getField().get(k).setIcon(new ImageIcon(new ImageIcon("cards/"+ p.getField().get(k).get(0) +".png").getImage().getScaledInstance(CARD_X, CARD_Y, 0)));
				playerPanel[i].getFieldNum().get(k).setText(Integer.toString(p.getField().get(k).size()));
			}else{
				playerPanel[i].getField().get(k).setIcon(new ImageIcon(new ImageIcon("cards/Back.png").getImage().getScaledInstance(CARD_X, CARD_Y, 0)));
				playerPanel[i].getFieldNum().get(k).setText("0");
			}

		//gold, deck, and discard
		playerPanel[i].getPlayerInfo()[1].setText("Gold: " + p.returnGold());

		if(d.getDeck().size()>0)
			deckAndDiscard[0].setIcon(new ImageIcon(new ImageIcon("cards/" + d.getDeck().get(0) + ".png").getImage().getScaledInstance(CARD_X, CARD_Y, 0)));
		else
			deckAndDiscard[0].setIcon(new ImageIcon(new ImageIcon("cards/Back.png").getImage().getScaledInstance(CARD_X, CARD_Y, 0)));

		if(d.getDiscard().size()>0)
			deckAndDiscard[1].setIcon(new ImageIcon(new ImageIcon("cards/" + d.getDiscard().get(0) + ".png").getImage().getScaledInstance(CARD_X, CARD_Y, 0)));
		else
			deckAndDiscard[1].setIcon(new ImageIcon(new ImageIcon("cards/Back.png").getImage().getScaledInstance(CARD_X, CARD_Y, 0)));
	
		dLabels[0].setText(Integer.toString(d.getDeck().size()));
		dLabels[1].setText(Integer.toString(d.getDiscard().size()));
		
	}

	public void refreshAll(Player[] p, Deck d){

		for(int k=0;k<p.length;k++){
			refreshP(k,p[k],d);
		}

	}	
	

	public void actionPerformed(ActionEvent e) {

	}



}