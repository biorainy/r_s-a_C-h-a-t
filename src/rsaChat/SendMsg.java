package rsaChat;

import java.io.DataOutputStream;
import java.math.BigInteger;
import java.util.Scanner;

public class SendMsg extends Thread {
    BigInteger pubKey;
    BigInteger cKey;
    DataOutputStream out;
    Scanner scan;

    /**
     * @param out
     */
    public SendMsg(DataOutputStream out, BigInteger pubKey, BigInteger cKey) {
	super();
	this.out = out;
	this.pubKey = pubKey;
	this.cKey = cKey;
    }

    public void run() {
	scan = new Scanner(System.in);
	while (true) {
	    // scan.nextLine();
	    String msg = scan.nextLine();

	    for (int k = 0; k < msg.length(); k++) {
		// System.out.println("Input length is:" + input.length());
		System.out.print(msg.charAt(k) + " " + (int) msg.charAt(k)
			+ " encrypt to: ");
		BigInteger cipher = RSA.endecrypt(
			BigInteger.valueOf((long) msg.charAt(k)), pubKey, cKey);
		System.out.println(cipher);
		try {
		    sendMsg(cipher);
		} catch (Exception e) {
		    // TODO Auto-generated catch block
		    System.out.println("Error sending message!");
		}
	    }

	}
    }

    public void sendMsg(BigInteger cipher) throws Exception {
	out.writeBytes(cipher + "\n");
    }
}
