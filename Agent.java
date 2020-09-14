import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class Agent {

	public int[] positions = new int[4];
	public int age=0;
	
	
	
	
	//weights and biases
	//we use 100, 100, 52 for our neurons for choosing to play a card
	float[] bias1=new float[100];
	float[] bias2=new float[100];
	float[] bias3=new float[52];
	
	float[][] heartWeight=new float[100][52];
	float[][] diamondWeight=new float[100][52];
	float[][] spadeWeight=new float[100][52];
	float[][] clubWeight=new float[100][52];
	float[][] normalisedValueWeight=new float[100][52];
	float[][] inHandWeight=new float[100][52];
	float[][] isLegalWeight=new float[100][52];
	float[][][] playedRoundWeight=new float[100][52][12];
	
	float[][][] playedByAgentNWeight=new float[4][100][52];
	float[][] recievedWeight=new float[100][52];
	float[][] gaveAwayWeight=new float[100][52];
	
	

	float[][] scoresWeight=new float[100][4];
	
	float[] leadSuit0Weight=new float[100];
	float[] leadSuitHWeight=new float[100];
	float[] leadSuitDWeight=new float[100];
	float[] leadSuitSWeight=new float[100];
	float[] leadSuitCWeight=new float[100];
	
	//choose 26 weights and biases

	
	float[][] scoresWeight26=new float[10][4];
	
	float[] choose26Bias=new float[10];
	
	float[][] choose26SecondWeight=new float[10][2];
	float[] choose26SecondBias =new float[2];
	
	
	
	
	float[][] secondNeuronWeight = new float[100][100];
	
	float[][] thirdNeuronWeight = new float[52][100];
	
	
	float[] firstNeurons =new float[100];
	float[] secondNeurons =new float[100];
	
	
	private Random ran;
	private double mean;
	private double var;
	
	
	
	private float[][] passCardWeights = new float[52][100];
	private float[][] passScoreWeights = new float[4][100];
	private float[] passbias1=new float[100];
	
	private float[][] passSecondWeights = new float[100][52];

	private float[] passbias2=new float[52];
	
	
	/**
	 * randomly initalise all weights and biases
	 */
	public Agent(double mean, double var) 
	{
		this.ran=new Random();
		this.var=var;
		this.mean=mean;
		
		for(int i=0;i<100;i++) 
		{
			
			for(int j=0;j<52;j++) 
			{
				this.heartWeight[i][j]=nextRan();
				this.clubWeight[i][j]=nextRan();
				this.spadeWeight[i][j]=nextRan();
				this.diamondWeight[i][j]=nextRan();
				this.normalisedValueWeight[i][j]=nextRan();
				this.inHandWeight[i][j]=nextRan();
				this.isLegalWeight[i][j]=nextRan();
				this.thirdNeuronWeight[j][i]=nextRan();
				this.passCardWeights[j][i]=nextRan();
				this.passSecondWeights[i][j]=nextRan();
				this.recievedWeight[i][j]=nextRan();
				this.gaveAwayWeight[i][j]=nextRan();
				for(int k=0;k<4;k++) 
				{

					this.playedByAgentNWeight[k][i][j]=nextRan();
				}
				for(int k=0;k<12;k++) 
				{
					this.playedRoundWeight[i][j][k]=nextRan();
				}
			}
			
			for(int k=0;k<4;k++) 
			{
				this.scoresWeight[i][k]=nextRan();
			}
			this.leadSuit0Weight[i]=nextRan();
			this.leadSuitHWeight[i]=nextRan();
			this.leadSuitDWeight[i]=nextRan();
			this.leadSuitCWeight[i]=nextRan();
			this.leadSuitSWeight[i]=nextRan();
			for( int j=0;j<100;j++) 
			{
				this.secondNeuronWeight[i][j]=nextRan();
			}
			
			this.bias1[i]=3.0F*nextRan();
			this.bias2[i]=3.0F*nextRan();
			this.passbias1[i]=3.0F*nextRan();
			
		}
		
		for(int i=0;i<52;i++) 
		{

			this.bias3[i]=3.0F*nextRan();
			this.passbias2[i]=3.0F*nextRan();
		}
		
		for(int i=0;i<10;i++) 
		{
			for(int j=0;j<4;j++) 
			{
				this.scoresWeight26[i][j]=nextRan();
			}

			this.choose26Bias[i]=nextRan();
			
			this.choose26SecondWeight[i][0]=nextRan();
			this.choose26SecondWeight[i][1]=nextRan();
		}
		this.choose26SecondBias[0]=nextRan();
		this.choose26SecondBias[1]=nextRan();
		
		
		
	}
	
	public Agent(double mean, double var, Agent agent) 
	{
		this.ran=new Random();
		this.var=var;
		this.mean=mean;
		
		for(int i=0;i<100;i++) 
		{
			
			for(int j=0;j<52;j++) 
			{
				this.heartWeight[i][j]=agent.heartWeight[i][j]+nextRan();
				this.clubWeight[i][j]=agent.clubWeight[i][j]+nextRan();
				this.spadeWeight[i][j]=agent.spadeWeight[i][j]+nextRan();
				this.diamondWeight[i][j]=agent.diamondWeight[i][j]+nextRan();
				this.normalisedValueWeight[i][j]=agent.normalisedValueWeight[i][j]+nextRan();
				this.inHandWeight[i][j]=agent.inHandWeight[i][j]+nextRan();
				this.isLegalWeight[i][j]=agent.isLegalWeight[i][j]+nextRan();
				this.recievedWeight[i][j]=agent.recievedWeight[i][j]+nextRan();
				this.gaveAwayWeight[i][j]=agent.gaveAwayWeight[i][j]+nextRan();


				for(int k=0;k<4;k++) 
				{

					this.playedByAgentNWeight[k][i][j]=agent.playedByAgentNWeight[k][i][j]+nextRan();
				}
				this.thirdNeuronWeight[j][i]=agent.thirdNeuronWeight[j][i]+nextRan();

				this.passCardWeights[j][i]=agent.passCardWeights[j][i]+nextRan();
				this.passSecondWeights[i][j]=agent.passSecondWeights[i][j]+nextRan();

				for(int k=0;k<12;k++) 
				{
					this.playedRoundWeight[i][j][k]=agent.playedRoundWeight[i][j][k]+nextRan();
				}
			}
			
			
			for(int k=0;k<4;k++) 
			{
				this.scoresWeight[i][k]=agent.scoresWeight[i][k]+=nextRan();
			}
			this.leadSuit0Weight[i]=agent.leadSuit0Weight[i]+nextRan();
			this.leadSuitHWeight[i]=agent.leadSuitHWeight[i]+nextRan();
			this.leadSuitDWeight[i]=agent.leadSuitDWeight[i]+nextRan();
			this.leadSuitCWeight[i]=agent.leadSuitCWeight[i]+nextRan();
			this.leadSuitSWeight[i]=agent.leadSuitSWeight[i]+nextRan();

			for( int j=0;j<100;j++) 
			{
				this.secondNeuronWeight[i][j]=agent.secondNeuronWeight[i][j]+nextRan();
			}
			
			this.bias1[i]=agent.bias1[i]+3.0F*nextRan();
			this.bias2[i]=agent.bias2[i]+3.0F*nextRan();
			this.passbias1[i]=agent.passbias1[i]+3.0F*nextRan();
		}
		
		for(int i=0;i<52;i++) 
		{

			this.bias3[i]=agent.bias3[i]+3.0F*nextRan();
			this.passbias2[i]=agent.passbias2[i]+3.0F*nextRan();
		}
		
		for(int i=0;i<10;i++) 
		{
			for(int j=0;j<4;j++) 
			{
				this.scoresWeight26[i][j]=agent.scoresWeight26[i][j]+nextRan();
			}

			this.choose26Bias[i]=nextRan();
			
			this.choose26SecondWeight[i][0]=nextRan();
			this.choose26SecondWeight[i][1]=nextRan();
		}
		this.choose26SecondBias[0]=nextRan();
		this.choose26SecondBias[1]=nextRan();
		
		
		
	}
	
	public Agent(String fileName)  
	{
		
		
		try {
			
			FileReader fr=new FileReader(fileName);
			BufferedReader br=new BufferedReader(fr);
			this.age=Integer.parseInt(br.readLine());
			br.readLine();//clearing the position line
			
			this.bias1=loadArray(br);
			this.bias2=loadArray(br);
			this.bias3=loadArray(br);
			this.choose26Bias=loadArray(br);
			this.choose26SecondBias=loadArray(br);
			this.leadSuit0Weight=loadArray(br);
			this.leadSuitCWeight=loadArray(br);
			this.leadSuitDWeight=loadArray(br);
			this.leadSuitHWeight=loadArray(br);
			this.leadSuitSWeight=loadArray(br);
			this.passbias1=loadArray(br);
			this.passbias2=loadArray(br);
			loadArrayArray(br,this.choose26SecondWeight);
			loadArrayArray(br,this.clubWeight);
			loadArrayArray(br,this.diamondWeight);
			loadArrayArray(br,this.gaveAwayWeight);
			loadArrayArray(br,this.heartWeight);
			loadArrayArray(br,this.inHandWeight);
			loadArrayArray(br,this.isLegalWeight);
			loadArrayArray(br,this.normalisedValueWeight);
			loadArrayArray(br,this.passCardWeights);
			loadArrayArray(br,this.passScoreWeights);
			loadArrayArray(br,this.passSecondWeights);
			loadArrayArray(br,this.recievedWeight);
			loadArrayArray(br,this.secondNeuronWeight);
			loadArrayArray(br,this.spadeWeight);
			loadArrayArray(br,this.thirdNeuronWeight);
			loadArrayArray(br,this.scoresWeight);
			loadArrayArray(br,this.scoresWeight26);

			for(int i=0;i<this.playedByAgentNWeight.length;i++) 
			{
				loadArrayArray(br,this.playedByAgentNWeight[i]);
			}
			for(int i=0;i<this.playedRoundWeight.length;i++) 
			{
				loadArrayArray(br,this.playedRoundWeight[i]);
			}
			
			
			
			
			
			
			br.close();
			fr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private float[] loadArray(BufferedReader br) throws IOException 
	{
		String line=br.readLine();
		
		String[] split=line.split(" ");
		float[] ans=new float[split.length];
		
		for(int i=0;i<split.length-1;i++) 
		{
			ans[i]=Float.parseFloat(split[i]);
		}
		
		return ans;
	}
	
	private void loadArrayArray(BufferedReader br, float[][] in) throws IOException 
	{
		
		
		
		for(int i=0;i<in.length;i++) 
		{
			String line=br.readLine();
			String[] split=line.split(" ");
			for(int j=0;j<in[0].length;j++) 
			{
				in[i][j]=Float.parseFloat(split[j]);
			}
			
		}
		
		
	}
	
	
	public Agent() {
		this.positions[0]=-1;
	}

	private float nextRan() 
	{
		return (float) (this.var*this.ran.nextGaussian()+this.mean);
	}
	
	
	
	public boolean saveToFile(int generation, int position) 
	{
		
		PrintWriter writer;
		try {
			writer = new PrintWriter("agent "+position+" generation "+generation+".txt", "UTF-8");
			writer.println(this.age);
			writer.println(this.positions[0]);
			
			saveArray(this.bias1,writer);
			saveArray(this.bias2,writer);
			saveArray(this.bias3,writer);
			saveArray(this.choose26Bias,writer);
			saveArray(this.choose26SecondBias,writer);
			saveArray(this.leadSuit0Weight,writer);
			saveArray(this.leadSuitCWeight,writer);
			saveArray(this.leadSuitDWeight,writer);
			saveArray(this.leadSuitHWeight,writer);
			saveArray(this.leadSuitSWeight,writer);
			saveArray(this.passbias1,writer);
			saveArray(this.passbias2,writer);
			saveArrayArray(this.choose26SecondWeight ,writer);
			saveArrayArray(this.clubWeight ,writer);
			saveArrayArray(this.diamondWeight ,writer);
			saveArrayArray(this.gaveAwayWeight ,writer);
			saveArrayArray(this.heartWeight ,writer);
			saveArrayArray(this.inHandWeight ,writer);
			saveArrayArray(this.isLegalWeight ,writer);
			saveArrayArray(this.normalisedValueWeight ,writer);
			saveArrayArray(this.passCardWeights ,writer);
			saveArrayArray(this.passScoreWeights ,writer);
			saveArrayArray(this.passSecondWeights ,writer);
			saveArrayArray(this.recievedWeight ,writer);
			saveArrayArray(this.secondNeuronWeight ,writer);
			saveArrayArray(this.spadeWeight ,writer);
			saveArrayArray(this.thirdNeuronWeight ,writer);
			saveArrayArray(this.scoresWeight, writer);
			saveArrayArray(this.scoresWeight26, writer);
			
			
			for(int i=0;i<this.playedByAgentNWeight.length;i++) 
			{
				saveArrayArray(this.playedByAgentNWeight[i] ,writer);
			}
			for(int i=0;i<this.playedRoundWeight.length;i++) 
			{
				saveArrayArray(this.playedRoundWeight[i],writer);
			}
			
			
			
			
			
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
		
		
		
		
		
		
		
		return true;
	}
	
	
	private void saveArrayArray(float[][] arr, PrintWriter writer) 
	{
		for(int i=0;i<arr.length;i++) 
		{
			for(int j=0;j<arr[0].length;j++) 
			{
				writer.print(arr[i][j]+" ");
			}
			writer.println();
		}
		
	}
	
	private void saveArray(float[] arr, PrintWriter writer) 
	{
		for(int i=0;i<arr.length;i++) 
		{
			writer.print(arr[i]+" ");
		}
		writer.println();
	}
	
	
	
	private float leadSuitValue(char leadSuit, int i) 
	{
		//as which suit is lead will be 1 or 0 we can simplify the values by simply using a switch statement, removing
		//the need for additional operations
		switch(leadSuit) 
		{
		case 'h': return this.leadSuitHWeight[i];
		case 'd': return this.leadSuitDWeight[i];
		case 'c': return this.leadSuitCWeight[i];
		case 's': return this.leadSuitSWeight[i];
		case '0': return this.leadSuit0Weight[i];
		
		}
		
		
		
		return -1.0F;
	}
	
	
	public boolean choose26(int[] scores, int agentNo) 
	{
		float[] neurons=new float[10];
		for(int i=0;i<10;i++) 
		{
			neurons[i]=this.choose26Bias[i];
			for(int j=0;j<4;j++) 
			{
				neurons[i]+=scores[(j+agentNo)%4]*this.scoresWeight26[i][j]; 
			}
			
			neurons[i]=(float) sigmoid(neurons[i]);
		}
		
		float[] neurons2=new float[2];
		for(int i=0;i<2;i++) 
		{
			for(int j=0;j<10;j++) 
			{
				neurons2[i]+=neurons[j]*this.choose26SecondWeight[j][i];
			}
			neurons2[i]+=this.choose26SecondBias[i];
		}
		
		
		return neurons[1]>neurons2[0];
	}
	
	public SmartCard[] passCards(Deck deck, int[] score, int agentNo) 
	{
		float[] neurons1=new float[100];
		for(int i=0;i<52;i++) 
		{
			if(deck.getCard(i).isInHand(agentNo)==1.0F) 
			{
				for(int j=0;j<100;j++) 
				{
					neurons1[j]+=this.passCardWeights[i][j];
				}
			}
		}
		for(int i=0;i<100;i++) 
		{
			neurons1[i]+=this.passbias1[i];
			for(int  j=0;j<4;j++) 
			{
				neurons1[i]+=this.passScoreWeights[j][i]*score[(j+agentNo)%4]/52.0F;
			}
			
			neurons1[i]=(float) sigmoid(neurons1[i]);
		}
		
		
		
		
		float[] neurons2=new float[13];
		int[] map=new int[13];
		int index=0;
		
		for(int i=0;i<52;i++) 
		{
			if(deck.getCard(i).isInHand(agentNo)==1.0F) 
			{
				neurons2[index]+=this.passbias2[i];
				map[index]=i;
				for(int j=0;j<100;j++) 
				{
					neurons2[index]+=this.passSecondWeights[j][i];
				}
				index++;
			}
		}
		
		
		
		SmartCard[] ans=new SmartCard[3];
		
		for(int i=0;i<3;i++) 
		{
			float max=-Float.MAX_VALUE;
			int pos=-1;
			for(int j=0;j<13;j++) 
			{
				if(neurons2[j]>max) 
				{
					max=neurons2[j];
					pos=j;
				}
			}
			ans[i]=deck.getCard(map[pos]);
			neurons2[pos]=-Float.MAX_VALUE;
		}
		
		
		
		
		
		
		
		return ans;
	}
	
	
	public SmartCard pickCard(Deck deck, char leadSuit, int[] score, int agentNo)
	{
		for(int i=0;i<100;i++) 
		{
			float total=0.0F;
			for(int j=0;j<52;j++) 
			{
				total+=deck.getCard(j).normalisedValue*this.normalisedValueWeight[i][j]+deck.getCard(j).isClub*this.clubWeight[i][j]
						+deck.getCard(j).isSpade*this.spadeWeight[i][j]+deck.getCard(j).isDiamond*this.diamondWeight[i][j]
								+deck.getCard(j).isHeart*this.heartWeight[i][j]+inHandWeight[i][j]*deck.getCard(j).isInHand(agentNo)
								+isLegalWeight[i][j]*deck.getCard(j).isLegalToPlay(leadSuit, deck, agentNo)
								+this.gaveAwayWeight[i][j]*deck.getCard(j).gaveAway(agentNo)
								+this.recievedWeight[i][j]*deck.getCard(j).recieved(agentNo);
				if(deck.getCard(j).playedByAgent!=-1) 
				{
					total+=this.playedByAgentNWeight[(deck.getCard(j).playedByAgent+agentNo)%4][i][j];
				}
				
				
				for(int k=0;k<12;k++) 
				{

					total+=playedRoundWeight[i][j][k]*deck.getCard(j).playedRound[k];
				}
			}
			
			total+=bias1[i]+leadSuitValue(leadSuit, i);
			
			for(int j=0;j<4;j++) 
			{
				total+=this.scoresWeight[i][j]*(score[(j+agentNo)%4]/52.0F);
			}
			
			firstNeurons[i]=(float) sigmoid(total);
			
		}
		
		
		for(int i=0;i<100;i++) 
		{
			float total=0.0F;
			for(int j=0;j<100;j++) 
			{
				total+=secondNeuronWeight[i][j];
			}
			total+=bias2[i];
			secondNeurons[i]=(float) sigmoid(total);
				
		}
		

		float currentMax=-Float.MAX_VALUE;
		int cardIndex=-1;
		for(int i=0;i<52;i++) 
		{
			float total=0.0F;
			if (deck.getCard(i).isLegalToPlay(leadSuit, deck, agentNo)==1.0F) 
			{
				for(int j=0;j<100;j++) 
				{
					total+=thirdNeuronWeight[i][j];
				}
				total+=bias2[i];
				if(total>currentMax) 
				{
					cardIndex=i;
					currentMax=total;
				}
			}
		}
		
		
		
		
		
		return deck.getCard(cardIndex);
	}
	

	
	/**
	 * * @param x
	 * @return sigmoid of x: 1/(1+e^(-x))
	 */
	public static double sigmoid(double x) 
	{
		return (1 / (1 + Math.exp(-x)));
	}
	
	
	
	
	
	
	
	
}







