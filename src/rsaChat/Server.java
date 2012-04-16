package rsaChat;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    /**
     * JavaProgrammingForums.com
     */
    public static void main(String[] args) throws Exception {
	// Port to monitor
	final int myPort = 1071;
	ServerSocket ssock = new ServerSocket(myPort);
	System.out.println("port " + myPort + " opened");

	Socket sock = ssock.accept();
	System.out.println("Someone has made socket connection");

	ListenFor client = new ListenFor(sock);
	String s = client.getRequest();
    }
}
