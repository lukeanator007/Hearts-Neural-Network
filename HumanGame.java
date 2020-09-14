import java.util.Scanner;

public class HumanGame {

	static Deck deck=new Deck();
	static int[] scores =new int[4];
	
	public static SmartCard getInputCard(String msg) 
	{
		try 
		{
			Scanner sc= new Scanner(System.in);    
			System.out.print("enter card in the format \"<suit><value>\" ace has value 14\n"+msg);
			String temp=sc.nextLine();
			return deck.parseCard(temp);
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			return getInputCard(msg);
		}
		
		
		
		
	}
	
	public void PlayGame(Agent agent) 
	{
		Agent[] agents=new Agent[4];
		agents[0]=agent;
		agents[1]=new Human();
		agents[2]=new Human();
		agents[3]=new Human();
		
		while(true) 
		{
			deck.shuffle();
			//deal hands
			F.log("enter the cards in the agents hand");
			for(int i=0;i<13;i++) 
			{
			    getInputCard("").inHand=0;
			}
			
			F.log("these are the cards the agents passes");
			//pass cards
			SmartCard[] toPass=agent.passCards(deck, scores,0);
			for(int i=0;i<3;i++) 
			{
				F.log(toPass[i]);
				toPass[i].inHand=-1;
			}
			
			//recieve cards
			
			for(int i=0;i<3;i++) 
			{
				getInputCard("the card passed to the agent").inHand=0;
			}
			
			
			
			//determine lead
			Scanner sc= new Scanner(System.in);
			F.log("enter the player who starts");
			int leadAgent=sc.nextInt();
			
			char leadSuit='c';
			int[] tempScores=new int[4];
			int leadValue=2; 
			int trickWinner;
			
			SmartCard tempCard;
			for(int i=0;i<13;i++) 
			{
				if(i==0) tempCard=deck.getCard(26);
				else tempCard=agents[leadAgent].pickCard(deck, leadSuit, scores,0);
				
				
				leadSuit=tempCard.suit;
				leadValue=tempCard.value;
				trickWinner=leadAgent;
				playCard(tempCard, leadAgent, i);
				int tempScore = getPoints(tempCard);
				
				for(int j=1;j<4;j++) 
				{
					tempCard=agents[(leadAgent+j)%4].pickCard(deck, leadSuit, scores,0);
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
			boolean gameEnd=false;
			
			for(int i=0;i<4;i++) 
			{
				if(tempScores[i]==26) 
				{
					if(agents[i].choose26(scores,0)) 
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
	
	
	
	private int getPoints(SmartCard card) {
		if(card.suit=='h') return 1;
		
		if(card.value==12 && card.suit=='s') return 13;
		
		return 0;
	}

	public void playCard(SmartCard card, int agentNumber, int round) 
	{
		card.inHand=-1;
		card.playedByAgent=agentNumber;
		
		card.playedRound[round]=1.0F;
		
		F.log(card);
		
		
	}
	
}
