package engine;

public class DumbTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LogicTest l = new LogicTest();
		display(l);
	}

	
	public static void display(Object o)
	{
		System.out.println(o.getClass().getSimpleName());
	}
}
