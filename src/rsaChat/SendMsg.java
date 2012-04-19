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
	    msg = msg + "\n";
	    for (int k = 0; k < msg.length(); k++) {
		// unblock this comment block if you want to see what cipher
		// text this character is encrypted to.

		BigInteger cipher = RSA.endecrypt(
			BigInteger.valueOf((long) msg.charAt(k)), pubKey, cKey);

		/*		
		System.out.print(msg.charAt(k) + " " + (int) msg.charAt(k)
			+ " encrypt to: ");
			
		System.out.println(cipher);
		*/

		try {
		    sendMsg(cipher);
		} catch (Exception e) {
		    System.out.println("Error sending message!");
		}
	    }

	}
    }

    public void sendMsg(BigInteger cipher) throws Exception {
	out.writeBytes(cipher + "\n");
    }
}
