package rsaChat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    // Port to monitor
    final int myPort = 8811;
    // these are used for decrypt
    BigInteger pubKey;
    BigInteger cKey;
    BigInteger privateKey;

    // these are used for encrypt
    BigInteger receivedPubKey;
    BigInteger receivedCKey;

    BigInteger ourPubKey = BigInteger.valueOf(1442270057);
    BigInteger ourCKey = BigInteger.valueOf(145924174367l);
    BigInteger ourPrivateKey = BigInteger.valueOf(34553307521l);

    /**
     * JavaProgrammingForums.com
     */
    public static void main(String[] args) throws Exception {
	new Client().run();
    }

    public void run() throws Exception {
	// connect to server by the IP address
	System.out
		.println("Please enter the IP address of the server you want to connect to, or \"localhost\" if you want to connect to local host:");
	Scanner scan = new Scanner(System.in);
	String ipAddr = scan.next();

	Socket csock;
	if (ipAddr.equals("localhost")) {
	    csock = new Socket(InetAddress.getLocalHost(), myPort);
	} else {
	    csock = new Socket(ipAddr, myPort);
	}

	DataOutputStream out = new DataOutputStream(csock.getOutputStream());
	BufferedReader in = new BufferedReader(new InputStreamReader(
		csock.getInputStream()));

	// SendMsg send = new SendMsg(out, pubKey, cKey);

	// received partner's public key pair
	String keyInfo = in.readLine();
	System.out.println(keyInfo);
	int start = keyInfo.indexOf("Public Key:");
	StringBuilder pKey = new StringBuilder();
	int pubKeyStart = start + "Public Key:".length();
	while (keyInfo.charAt(pubKeyStart) != ' ') {
	    pKey.append(keyInfo.charAt(pubKeyStart));
	    pubKeyStart++;
	}
	receivedPubKey = new BigInteger(pKey.toString());

	// System.out.println("Extracted pub key is:" + pubKey);

	int startCK = keyInfo.indexOf("C_Key is:");
	StringBuilder cKeyStrBui = new StringBuilder();
	int cKeyStart = startCK + "C_Key is:".length();
	while (keyInfo.charAt(cKeyStart) != ' ') {
	    cKeyStrBui.append(keyInfo.charAt(cKeyStart));
	    cKeyStart++;
	}
	receivedCKey = new BigInteger(cKeyStrBui.toString());

	// asks the user to input public key pairs
	System.out
		.println("Please enter the public key (e, c): first e, then c");
	try {
	    pubKey = scan.nextBigInteger();
	    cKey = scan.nextBigInteger();
	} catch (Exception e) {
	    System.out.println("Input not valid. Program quit....");
	}
	out.writeBytes("Public Key:" + pubKey + " C_Key is:" + cKey + " \n");

	// ask for chat msg
	System.out.println("Please enter message to send to the server: ");

	SendMsg send = new SendMsg(out, receivedPubKey, receivedCKey);
	send.start();

	if (pubKey.equals(ourPubKey) && cKey.equals(ourCKey)) {
	    privateKey = ourPrivateKey;
	} else {
	    privateKey = BigInteger.valueOf(RSA.bruteDecrpt(pubKey.longValue(),
		    cKey.longValue()));
	}

	getRequest(in);
    }

	String quit = "";
	int count = 0;
	boolean ifQuit = false;
	boolean startCount = false;
    void getRequest(BufferedReader in) throws Exception {
	String incomingMsg;
	BigInteger cipher = null;
	while (ifQuit == false && (incomingMsg = in.readLine()) != null) {
	    cipher = new BigInteger(incomingMsg);
	    // System.out.println("Received Cipher is :" + cipher);
	    BigInteger decrpted = RSA.endecrypt(cipher, privateKey, cKey);
	    // System.out.println((char) decrpted.intValue() + " " + decrpted);
	    System.out.print((char) decrpted.intValue());
	    
	    if((((char) decrpted.intValue())+"").equals(".") ) {
//		System.out.println("heyhj");
		startCount = true;
	    }
	    if(startCount == true) {
		quit += (char) decrpted.intValue();
		count++;
	    }
	    if(count == 4) {
		if(quit.equals(".bye")) {
		    System.out.println("\nServer is quiting...");
		    ifQuit = true;
		    System.exit(0);
		}
		else {
		    quit = "";
		    count = 0;
		    startCount = false;
		}
	    }
	}
	return;
    }
}