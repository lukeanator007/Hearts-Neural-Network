
public class Card implements Cloneable{

	public char suit;
	public int value;
	
	
	//use 14 as value for ace
	public Card(int value, char suit) 
	{
		this.suit=suit;
		this.value=value;
	}
	
	public Card() 
	{
		
	}
	
	
	
	public String toString() 
	{
		String ans;
		switch (value) 
		{
		case 14: ans="Ace";
		break;
		case 13: ans="King";
		break;
		case 12: ans="Queen";
		break;
		case 11: ans="Jack";
		break;
		default: ans=value+"";
		}
		
		switch (suit) 
		{
		case 'h': ans+=" of Hearts";
		break;
		case 'd': ans+=" of Diamonds";
		break;
		case 'c': ans+=" of Clubs";
		break;
		case 's': ans+=" of Spades";
		break;
		}
		
		
		return ans;
		
	}
	
	public Object clone() throws CloneNotSupportedException 
	{
		return super.clone();
	}
	
	public boolean equals(Card anotherCard)
	{
		if(anotherCard==null) 
		{
			return false;
		}
		if(this.value==anotherCard.value && this.suit==anotherCard.suit) 
		{
			return true;
		}
		
		
		return false;
		
	}

	
	
	
	
}
