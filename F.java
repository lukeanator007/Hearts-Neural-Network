
public class F {

	
	
	
	public static void log(Object obj) 
	{
		if(obj!=null) {
			System.out.println(obj.toString());
		}
		else 
		{
			System.out.println("null");
		}
			
	}
	
	public static void logArray(Object[] array) 
	{
		log("Array Start");
		for(int i=0;i<array.length;i++) 
		{
			log(array[i]);
		}
		log("Array End");
	}

	public static void logArray(int[] array) {
		log("Array Start");
		for(int i=0;i<array.length;i++) 
		{
			log(array[i]);
		}
		log("Array End");
		
	}
}
