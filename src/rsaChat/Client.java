package rsaChat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    /**
     * JavaProgrammingForums.com
     */
    public static void main(String[] args) throws Exception {
	// Port to monitor
	final int myPort = 1074;
	// Client client = new Client();
	InetAddress serverIPAddr = InetAddress.getLocalHost();

	Socket csock = new Socket(serverIPAddr, myPort);
	DataOutputStream out = new DataOutputStream(csock.getOutputStream());
	BufferedReader in = new BufferedReader(new InputStreamReader(
		csock.getInputStream()));
	System.out.println("Please enter message to send to the server: ");

	// SendMsg send = new SendMsg(out);
	// send.start();

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
