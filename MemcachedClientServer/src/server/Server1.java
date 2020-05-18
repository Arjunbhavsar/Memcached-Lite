package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server1{

	
	static List<ThreadHandler> clientlist = new ArrayList<>();
	
	public static void main(String[] args) throws IOException {
		int count=0; 
		ServerSocket ss = new ServerSocket(15000);
		while (true) {
			try {
				System.out.println("Ready To Accept Client");
				Socket sock = ss.accept();
				System.out.println("Client Connected");
				DataInputStream input  = new DataInputStream(sock.getInputStream());
				DataOutputStream output = new DataOutputStream(sock.getOutputStream());
				
				ThreadHandler t= new ThreadHandler(sock,"Count "+count,input,output);
				clientlist.add(t);
				t.start();
				count++;
			} catch (IOException e) {
				System.out.println("Problem With Server"+e);
			}
		}
	
	}

}
