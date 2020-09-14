import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

public class Zero {

	
	
	public static void main(String[] args) {
		
		//main control area
		
		HumanGame game=new HumanGame();
		game.PlayGame(new Agent("agent 0 generation 350.txt"));
		
	
		
		//trainFromZero();
		//trainFromFile(350);
		
	}
	
	/**
	 * trains the agents from the stored agent files given a generation to train them from
	 * a given generation
	 * 
	 * @param currentGeneration the generation to be mutated. 
	 */
	private static void trainFromFile(int currentGeneration) 
	{
		Agent[] agents=new Agent[10];
		
		for(int i=0;i<10;i++) 
		{
			agents[i]=new Agent("agent "+i+" generation "+currentGeneration+".txt");
		}
		
		
		
		Agent[][] agentList = new Agent[5][20];
		createMutations(agents, agentList);
		train(agentList, currentGeneration+1);
		
	}
	
	/**
	 * generates completely new agents and begins training them
	 */
	private static void trainFromZero() 
	{
		Agent[][] agentList = new Agent[5][20];
		for(int i=0;i<agentList.length;i++) 
		{
			for(int j=0;j<agentList[i].length;j++) 
			{
				agentList[i][j]=new Agent(0.0, 1.0);
			}
			
		}
		train(agentList,0);
	}
	
	
	/**
	 * takes an array of winning agents and creates new agents by mutating them
	 * then adds the original agents and a few completely new ones to the agent list. 
	 * this updates agentList to contain new agents for training
	 * 
	 * @param finalWinners the agents that will be mutated
	 * @param agentList the list of agents that is modified
	 */
	private static void createMutations(Agent[] finalWinners, Agent[][] agentList) 
	{
		ArrayList<Agent> nextRoundList = new ArrayList<Agent>(); 
		for(int i=0;i<finalWinners.length;i++) 
		{
			for(int j=0;j<4;j++) 
			{
				finalWinners[i].positions[j]=0;
			}
			nextRoundList.add(finalWinners[i]);
			for(int j=0;j<8;j++) 
			{
				nextRoundList.add(new Agent(0,0.25, finalWinners[i]));
			}
			
		}
		for(int i=0;i<10;i++) 
		{
			nextRoundList.add(new Agent(0,1));
			
		}
		Collections.shuffle(nextRoundList);
		

		for(int i=0;i<agentList[0].length*agentList.length;i++) 
		{
			agentList[i/agentList[0].length][i%agentList[0].length]=nextRoundList.get(i);
		}
	}
	
	/**
	 * 
	 * @param agentList
	 * @param generation
	 */
	private static void train(Agent[][] agentList, int generation) 
	{
		
		Agent[] winningAgents=new Agent[20];
		
		
		
		while(true) 
		{
			
			F.log("starting generation "+generation);
			for(int i=0;i<agentList.length;i++) 
			{
				
				F.log("starting test "+i);
				Agent[] tempAgents=testAgents(agentList[i],4);
				for(int j=0;j<4;j++) 
				{
					winningAgents[i*4+j]=tempAgents[j];
				}
				F.log("ending test "+i );
			}
			
			for(int i=0;i<winningAgents.length;i++) 
			{
				for(int j=0;j<4;j++) 
				{
					winningAgents[i].positions[j]=0;
				}
			}
			
			Agent[] finalWinners=testAgents(winningAgents,10);
			
			

			for(int i=0;i<finalWinners.length;i++) 
			{
				finalWinners[i].saveToFile(generation, i);
				finalWinners[i].age++;
			}
			
			createMutations(finalWinners,agentList);
			
			
			generation++;
		}
	}
	
	/**
	 * 
	 * @param agentList
	 * @param numberOfWinners
	 * @return
	 */
	private static Agent[] testAgents(Agent[] agentList, int numberOfWinners) 
	{
		Agent[] agents=new Agent[4];
		HashSet<HeartsGame> games=new HashSet<HeartsGame>();
		
		for(int i=0;i<agentList.length-3;i++) 
		{
			for(int j=i+1;j<agentList.length-2;j++) 
			{
				for(int k=j+1;k<agentList.length-1;k++) 
				{
					for(int l=k+1;l<agentList.length;l++) 
					{
						boolean test=true;
						
						try 
						{
							agents[0]=agentList[i];
							agents[1]=agentList[j];
							agents[2]=agentList[k];
							agents[3]=agentList[l];
						}
						catch(Exception e) 
						{
							test=false;
						}
						
						if(test) 
						{
							games.add(new HeartsGame(agents));
							
							
						}
						
						
						
					} 
					
				}
				
			}
			
		}
		
		for(HeartsGame game:games) 
		{
			game.start();
		}
		for(HeartsGame game:games) 
		{
			try {
				game.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		
		Agent[] winners=new Agent[numberOfWinners];
		
		
		int wins=0;
		int winner;
		
		for(int i=0;i<numberOfWinners;i++) 
		{
			wins=agentList[0].positions[0];
			winner=0;
			for(int j=1;j<agentList.length;j++) 
			{
				if(agentList[j].positions[0]>wins) 
				{
					wins=agentList[j].positions[0];
					winner=j;
				}
			}
			
			winners[i]=agentList[winner];
			winners[i].positions[0]=-1;
		}
		
		
		
		
		
		
		
		return winners;
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
}


















