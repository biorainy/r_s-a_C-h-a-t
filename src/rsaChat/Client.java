package rsaChat;


import java.net.*;
import java.io.*;

public class Client {



    /**
     * JavaProgrammingForums.com
     */
    public static void main(String[] args) throws Exception {
	// Port to monitor
	final int myPort = 1071;
	//Client client = new Client();
	InetAddress serverIPAddr = InetAddress.getLocalHost();
	Socket csock = new Socket(serverIPAddr,myPort);
	DataOutputStream out = new DataOutputStream(csock.getOutputStream());
	
	SendMsg send = new SendMsg(out);
	send.start();
	
	BufferedReader in = new BufferedReader(new InputStreamReader(csock.getInputStream()));
	getRequest(in);
	
	

    }
    
    static String getRequest(BufferedReader in) throws Exception {
	String s = null;
	while ((s = in.readLine()) != null) {
	    System.out.println("got: " + s);
	}
	return s;
    }

}
