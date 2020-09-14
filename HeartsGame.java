
public class HeartsGame extends Thread{

	
	
	Deck deck=new Deck();
	Agent[] agents;
	
	HeartsGame(Agent[] agents)
	{
		this.agents=agents;
	}
	
	HeartsGame()
	{
		
	}
	
	@Override
	public void run() 
	{
		playGame(agents);
	}
	
	/**
	 * plays hearts with 4 given agents and increments the positions array of each agent
	 * based on the position it got. If an agent gets first, positions[0] is incremented and so on
	 * 
	 * @param agents an array of 4 agents to play hearts
	 */
	public void playGame(Agent[] agents)
	{
		
		//play game loop
		int[] scores=new int[4];
		
		int tempScore=0;
		int leadValue;
		int trickWinner;
		boolean gameEnd=false;
		while(true) 
		{
			deck.shuffle();
			//deal hands
			for(int i=0;i<13;i++) 
			{
			    deck.drawSmartCard().inHand=0;
				deck.drawSmartCard().inHand=1;
				deck.drawSmartCard().inHand=2;
				deck.drawSmartCard().inHand=3;
				
			}
			
			//pass cards
			for(int i=0;i<4;i++) 
			{
				SmartCard[] toPass=agents[i].passCards(deck, scores,i);
				for(int j=0;j<3;j++) 
				{
					toPass[j].gaveAway=toPass[j].inHand;
					toPass[j].inHand=(toPass[j].inHand)%4;
					toPass[j].recieved=toPass[j].inHand;
				}
			}
			
			
			
			
			
			
			
			//determine lead
			int leadAgent=deck.find2Clubs();
			char leadSuit='c';
			int[] tempScores=new int[4];
			
			SmartCard tempCard;
			for(int i=0;i<13;i++) 
			{
				if(i==0) tempCard=deck.getCard(26);
				else tempCard=agents[leadAgent].pickCard(deck, leadSuit, scores,leadAgent);
				
				
				leadSuit=tempCard.suit;
				leadValue=tempCard.value;
				trickWinner=leadAgent;
				playCard(tempCard, leadAgent, i);
				tempScore+=getPoints(tempCard);
				
				for(int j=1;j<4;j++) 
				{
					tempCard=agents[(leadAgent+j)%4].pickCard(deck, leadSuit, scores,(leadAgent+j)%4);
					if(tempCard.suit==leadSuit&&tempCard.value>leadValue) 
					{
						trickWinner=(leadAgent+j)%4;
						leadValue=tempCard.value;
						
					}
					
					tempScore+=getPoints(tempCard);
					playCard(tempCard, (leadAgent+j)%4, i);
				}
				
				tempScores[trickWinner]+=tempScore;
				leadAgent=trickWinner;
				tempScore=0;
				
				
				leadSuit='0';
				
				
			}
			
			
			for(int i=0;i<4;i++) 
			{
				if(tempScores[i]==26) 
				{
					if(agents[i].choose26(scores,i)) 
					{
						for(int j=1;j<4;j++) 
						{
							scores[(i+j)%4]+=26;
							if(scores[(i+j)%4]>52) gameEnd=true;
						}
					}
					else 
					{
						scores[i]-=26;
					}
				}
				else 
				{
					scores[i]+=tempScores[i];
					if(scores[i]>52) gameEnd=true;
				}
			}
			
			
			for(int i=0;i<4;i++) 
			{
				tempScores[i]=0;
			}
			
			
			
			if(gameEnd)  
			{
				int placement=0;
				int placementHold=0;
				while(true) 
				{
					
					int lowest=Math.min(Math.min(scores[0], scores[1]),Math.min(scores[2], scores[3]));
					for(int i=0;i<4;i++) 
					{
						if(lowest==scores[i]) 
						{
							agents[i].positions[placement]++;
							placementHold++;
							scores[i]=100;
						}
					}
					placement=placementHold;
					if(placement>=4) break;
				}
				break;
			}
			
		}
		
		
	}
	
	
	/**
	 * gets the amount of points a card is worth
	 * 
	 * @param card
	 * @return 1 if the card is a heart, 13 if the card is the queen of spades and 0 otherwise
	 */
	public int getPoints(SmartCard card) 
	{
		
		if(card.suit=='h') return 1;
		
		if(card.value==12 && card.suit=='s') return 13;
		
		return 0;
	}
	
	/**
	 * updates the card with the relevant data when the card is played
	 * 
	 * @param card the card played
	 * @param agentNumber the agent that played the card
	 * @param round the round the card was played
	 */
	public void playCard(SmartCard card, int agentNumber, int round) 
	{
		card.inHand=-1;
		card.playedByAgent=agentNumber;
		
		card.playedRound[round]=1.0F;
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}


















