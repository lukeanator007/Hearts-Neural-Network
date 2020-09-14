

public class Deck {

	private boolean[] deckUsed=new boolean[52];
	public SmartCard[] deck;
	private int initalisePosition=0;
	private int cardsUsed=0;
	
	/**
	 * constructor
	 */
	public Deck() 
	{
		deck=new SmartCard[52];
		initaliseSuit('h');
		initaliseSuit('d');
		initaliseSuit('c');
		initaliseSuit('s');
		
	}
	
	/**
	 * called once to initalise the deck by suit
	 * @param suit
	 */
	private void initaliseSuit(char suit) 
	{
		for(int i=2;i<15;i++) 
		{
			deck[initalisePosition]=new SmartCard(i, suit);
			initalisePosition++;
		}
	}
	
	/**
	 * list each card in the initialised order and which agent is holding them
	 * or -1 if the card is not in a hand
	 * 
	 */
	public String toString() 
	{
		String ans="";
		
		for(int i=0;i<52;i++) 
		{
			SmartCard tempCard=this.getCard(i);
			ans+=tempCard.toString()+" in hand="+tempCard.inHand+"\n";
		}
		
		
		return ans;
	}
	
	/**
	 * draws a card from the deck randomly, and marks the drawn card so it
	 * cannot be drawn again until the deck is shuffled
	 * 
	 * @return the draw card
	 */
	public SmartCard drawSmartCard() 
	{
		int random=(int) Math.floor(Math.random()*(52.0-this.cardsUsed));
		for(int j=0;j<=random;j++) 
		{
			if(this.deckUsed[j]) 
			{
				random++;
			}
		}
		this.deckUsed[random]=true;
		this.cardsUsed++;
		return  deck[random];
		
	}
	
	/**
	 * reset the cards drawn
	 */
	public void shuffle() 
	{
		this.deckUsed=new boolean[52];
		this.cardsUsed=0;
	}
	
	/**
	 * checks that each card in the 52 card deck is unique
	 * used during debugging
	 * 
	 * @return true if there are no duplicate cards, and false otherwise 
	 */
	public boolean checkDeckValid() 
	{
		for(int i=0;i<52;i++) 
		{
			for (int j=0;j<52;j++) 
			{
				if(j!=i) 
				{
					if(this.deck[i].equals(this.deck[j])) 
					{
						return false;
					}
				}
			}
		}
		
		
		return true;
	}
	
	/**
	 * checks which agent is holding the 2 of clubs
	 * 
	 * @return agent number stored in the 2 of clubs
	 */
	public int find2Clubs() 
	{
		return this.deck[26].inHand; //index 26 is the 2 of clubs
	}
	
	/**
	 * lists the number of cards in each agents hand, used for debuging
	 * 
	 * @return a string repesentation of the number of cards in each agents hand
	 */
	public String cardsInHand() 
	{
		String ans="";
		for(int i=0;i<4;i++) 
		{
			int counter=0;
			for(int j=0;j<52;j++) 
			{
				if(this.getCard(j).inHand==i) counter++;
			}
			
			ans+="agent "+i+" has "+counter+" cards in their hand\n";
			
		}
		
		
		
		return ans;
	}
	
	/**
	 * gets a card by index
	 * 
	 * @param index of the card to get
	 * @return the card with the corresponding index
	 */
	public SmartCard getCard(int index) 
	{
		return deck[index];
	}
	
	/**
	 * 
	 * 
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public SmartCard parseCard(String str) throws Exception 
	{
		int index=-2;
		switch(str.charAt(0))
		{
		case 'h':
			break;
		case 'd':
			index+=13;
			break;
		case 'c':
			index+=26;
			break;
		case 's':
			index+=39;
			break;
		
		}
		int val=Integer.parseInt(str.substring(1));
		
		if(val<2||val>14) throw new Exception("value is out of range");
		
		index+=val;
		
		
		return getCard(index);
	}
	
	
	
	
	
}
	
	
	
	






