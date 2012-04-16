package rsaChat;

import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    BigInteger pubKey;
    BigInteger cKey;

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
	final int myPort = 1074;
	ServerSocket serSock = new ServerSocket(myPort);
	System.out.println("port " + myPort + " opened");

	Socket sock = serSock.accept();
	System.out.println("Someone has made socket connection");

	ListenFor listener = new ListenFor(sock);

	System.out
		.println("Please enter the public key (e, c): first e, then c");
	BigInteger pubKey = scan.nextBigInteger();
	BigInteger cKey = scan.nextBigInteger();
	listener.out.writeBytes("Public Key:" + "" + "\n");
	SendMsg send = new SendMsg(listener.out, pubKey, cKey);
	send.start();
	String s = listener.getRequest();
    }
}