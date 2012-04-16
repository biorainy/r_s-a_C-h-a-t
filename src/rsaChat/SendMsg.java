package rsaChat;

import java.io.DataOutputStream;
import java.util.Scanner;

public class SendMsg extends Thread {
    /**
     * @param out
     */
    public SendMsg(DataOutputStream out) {
	super();
	this.out = out;
    }

    DataOutputStream out;
    Scanner scan;

    public void run() {
	scan = new Scanner(System.in);
	while (true) {
	    // scan.nextLine();
	    String msg = scan.next();
	    System.out.println("Recived input: " + msg);
	    try {
		sendRequest(msg);
	    } catch (Exception e) {
		// TODO Auto-generated catch block
		System.out.println("Error sending message!");
	    }
	}
    }

    public void sendRequest(String msg) throws Exception {
	out.writeBytes(msg);
    }
}
