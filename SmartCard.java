
public class SmartCard extends Card {

	public int inHand=-1; //the agent whos hand this card is in
	int gaveAway=-1;
	int recieved=-1;
	float isSpade= 0.0F;
	float isHeart= 0.0F;
	float isDiamond= 0.0F;
	float isClub= 0.0F;
	float normalisedValue= 0.0F;
	float[] playedRound= new float[13];
	
	int playedByAgent=-1;
	
	
	
	
	
	public SmartCard(int value, char suit) 
	{
		super(value, suit);
		switch(suit) 
		{
		case 'd':
			this.isDiamond=1.0F;
			break;
		case 'h':
			this.isHeart=1.0F;
			break;
		case 'c':
			this.isClub=1.0F;
			break;
		case 's':
			this.isSpade=1.0F;
			break;
		}
		this.normalisedValue=value/14.0F;
		
	}
	
	public SmartCard(Card card) 
	{
		super.suit=card.suit;
		super.value=card.value;
		switch(suit) 
		{
		case 'd':
			this.isDiamond=1.0F;
			break;
		case 'h':
			this.isHeart=1.0F;
			break;
		case 'c':
			this.isClub=1.0F;
			break;
		case 's':
			this.isSpade=1.0F;
			break;
		}
		this.normalisedValue=value/14.0F;
	}
	
	
	
	public void reset() 
	{
		this.gaveAway=-1;
		this.playedByAgent=-1;
		
		for(int i=0;i<12;i++) 
		{
			this.playedRound[i]=0.0F;
		}
		this.recieved=-1;
	}
	
	public float gaveAway(int agentNumber) 
	{
		return  agentNumber==this.gaveAway ? 1.0F : 0.0F ; 
	}
	
	public float recieved(int agentNumber) 
	{
		return  agentNumber==this.recieved ? 1.0F : 0.0F ; 
	}

	
	
	
	
	
	
	/**
	 * 
	 * @param agentNumber
	 * the number of the agent to check against
	 * @return 
	 * true if this card is in the given agents hand
	 */
	public float isInHand(int agentNumber) 
	{
		if(this.inHand==agentNumber) return 1.0F;
		return 0.0F;
	}
	
	/**
	 * 
	 * @param leadSuit
	 * suit that was lead, '0' if this agent has the lead
	 * @param deck
	 * the deck this agent is playing with, using SmartCards, to determine what cards are in the agent's hand
	 * @param agentNumber
	 * the number of this agent, used to find what cards are in the agent's hand
	 * @return
	 * 1.0F if the card is legal to play, 0.0F otherwise
	 */
	float isLegalToPlay(char leadSuit, Deck deck, int agentNumber) 
	{
		if(this.inHand!=agentNumber) 
		{
			return 0.0F;
		}
		
		if (leadSuit=='0') 
		{
			return 1.0F;
		}
		if (this.suit==leadSuit) 
		{
			return 1.0F;
		}
			
		for (int i=0;i<52; i++) 
		{
			if(deck.getCard(i).inHand==agentNumber&&deck.getCard(i).suit==leadSuit) 
			{
				return 0.0F;
			}
		}
		
		return 1.0F;
	}
	
	
	
	
}
