package rsaChat;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

class ListenFor {
    Socket sock;
    BufferedReader in = null;
    DataOutputStream out = null;

    ListenFor(Socket sock) throws Exception {
	this.sock = sock;
	in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	out = new DataOutputStream(sock.getOutputStream());
    }

    String getRequest() throws Exception {
	String s = null;
	while ((s = in.readLine()) != null) {
	    System.out.println("got: " + s);
	}
	return s;
    }
}