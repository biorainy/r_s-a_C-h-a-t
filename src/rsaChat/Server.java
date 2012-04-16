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
	ServerSocket serSock = new ServerSocket(myPort);
	System.out.println("port " + myPort + " opened");

	Socket sock = serSock.accept();
	System.out.println("Someone has made socket connection");

	ListenFor client = new ListenFor(sock);
	SendMsg send = new SendMsg(client.out);
	send.start();
	String s = client.getRequest();

    }
}
