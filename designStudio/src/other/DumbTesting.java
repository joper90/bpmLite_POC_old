package other;

public class DumbTesting {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 

		System.out.println("running");
		String test = "monkey";
		try
		{
			Integer.parseInt(test);
		}catch(Exception e)
		{
			System.out.println("got an error");
		}
		
		
	}

}
