
import java.util.Scanner;

public class Human extends Agent{

	
	public Human() 
	{
		
	}
	
	
	
	
	
	@Override
	public SmartCard pickCard(Deck deck, char leadSuit, int[] score, int agentNo)
	{
		return HumanGame.getInputCard("pick the card to play");
		
	}
	
	
	@Override
	public boolean choose26(int[] scores, int agentNo) 
	{
		try 
		{
			Scanner sc= new Scanner(System.in);    
			System.out.print("enter true to increase by 26, false to reduce by 26");
			String temp=sc.nextLine();
			return Boolean.parseBoolean(temp);
		}
		catch(Exception e) 
		{
			e.printStackTrace();
			return choose26(scores, agentNo);
		}
		
		
		
	}
	
	
	
	
	
}




























































