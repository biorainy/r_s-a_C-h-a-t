package rsaChat;

import java.io.BufferedReader;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    BigInteger ourPubKey = BigInteger.valueOf(816999931);
    BigInteger ourCKey = BigInteger.valueOf(334016195633l);
    BigInteger ourPrivateKey = BigInteger.valueOf(-12746183801l);

    // the user input pubKey and cKey, privateKey is got by brute force
    // decrption if different from our own pubkey pair
    BigInteger pubKey;
    BigInteger privateKey;
    BigInteger cKey;

    // received pubkey pair from partner
    BigInteger receivedPubKey;
    BigInteger receivedCKey;

    /**
     * JavaProgrammingForums.com
     */
    public static void main(String[] args) {
	try {
	    new Server().run();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    public void run() throws Exception {
	Scanner scan = new Scanner(System.in);
	// Port to monitor
	final int myPort = 8811;
	ServerSocket serSock = new ServerSocket(myPort);
	System.out.println("port " + myPort + " opened");

	Socket sock = serSock.accept();
	System.out.println("Someone has made socket connection");

	ListenFor listener = new ListenFor(sock);

	System.out
		.println("Please enter the public key (e, c): first e, then c");
	try {
	    pubKey = scan.nextBigInteger();
	    cKey = scan.nextBigInteger();
	} catch (Exception e) {
	    System.out.println("Input not valid. Program quit....");
	}
	listener.out.writeBytes("Public Key:" + pubKey + " C_Key is:" + cKey
		+ " \n");

	// receive pubkey pair from partner
	String keyInfo = listener.in.readLine();
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

	// ask for chat msg
	System.out.println("Please enter message to send to the client: ");

	SendMsg send = new SendMsg(listener.out, receivedPubKey, receivedCKey);

	if (pubKey.equals(ourPubKey) && cKey.equals(ourCKey)) {
	    privateKey = ourPrivateKey;
	} else {
	    privateKey = BigInteger.valueOf(RSA.bruteDecrpt(pubKey.longValue(),
		    cKey.longValue()));
	}

	send.start();
	if(ifQuit == false) getRequest(listener.in);
	else System.exit(0);
	// String s = listener.getRequest();
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
		    System.out.println("\nClient is quiting...");
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