package comNet;

import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;

public class WebServer {

	public static void main(String[] args) {
		int port=1010;
		try {
			ServerSocket ss = new ServerSocket(port);
			Socket client = null;
			while (true){ 
				System.out.println("waiting for connection...");
				client = ss.accept();
				System.out.println("connection established");
				HttpRequest request = new HttpRequest(client);
				Thread thread = new Thread(request);
				thread.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

