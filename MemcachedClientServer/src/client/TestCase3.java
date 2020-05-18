package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TestCase3 {
	private static DataInputStream input;
	private static DataOutputStream output;

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {

		Socket sock = new Socket("127.0.0.1", 15000);
		output = new DataOutputStream(sock.getOutputStream());
		input = new DataInputStream(sock.getInputStream());

		System.out.println("Client Address"+sock.getLocalSocketAddress());
		String recievMessagge;
		
		List<String> listOfSETCommands = new ArrayList<>();
		for(int i =1000; i<1500;i++) {
			listOfSETCommands.add("set key"+i+" 20 Value"+i);
		}
		List<String> listOfGETCommands = new ArrayList<>();
		for(int i =1000; i<1500;i++) {
			listOfGETCommands.add("get key"+i);
		}
		for(String s:listOfSETCommands) {
			output.writeUTF(s);
			output.flush();
			if((recievMessagge=input.readUTF())!=null) {
				System.out.println(recievMessagge);	
			}
		}
		for(String s:listOfGETCommands) {
			output.writeUTF(s);
			output.flush();
			if((recievMessagge=input.readUTF())!=null) {
				System.out.println(recievMessagge);	
			}
		}

	}



}
