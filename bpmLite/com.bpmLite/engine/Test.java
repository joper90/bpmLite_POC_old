package engine;

import com.bpmlite.api.ServerCommandDocument;
import com.bpmlite.api.ServerInstruction;
import com.bpmlite.api.ServerCommandDocument.ServerCommand;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ServerCommandDocument s = ServerCommandDocument.Factory.newInstance();
		ServerCommand sc = s.addNewServerCommand();
		sc.setCaseId("TEST991");
		sc.setInstruction(ServerInstruction.NEXT_STEP);
		
		System.out.println(s);
	}

}
