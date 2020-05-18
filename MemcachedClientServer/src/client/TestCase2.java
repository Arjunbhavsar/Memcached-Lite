package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TestCase2 extends Thread{
	private static DataInputStream input;
	private static DataOutputStream output;

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException, InterruptedException {

		Socket sock = new Socket("127.0.0.1", 15000);
		output = new DataOutputStream(sock.getOutputStream());
		input = new DataInputStream(sock.getInputStream());

		System.out.println("Client Address"+sock.getLocalSocketAddress());
		String recievMessagge;
		List<String> listOfCommands = new ArrayList<>();
		for(int i =0; i<1000;i++) {
			listOfCommands.add("get key"+i);
		}
		for(String s:listOfCommands) {
			output.writeUTF(s);
			output.flush();
			if((recievMessagge=input.readUTF())!=null) {
				System.out.println(recievMessagge);	
			}
			Thread.sleep(10);
		}
		

	}



}
