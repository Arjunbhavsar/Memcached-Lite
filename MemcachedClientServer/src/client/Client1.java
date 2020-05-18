package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client1 {
	private static DataInputStream input;
	private static DataOutputStream output;

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {

		Socket sock = new Socket("127.0.0.1", 15000);
		output = new DataOutputStream(sock.getOutputStream());
		input = new DataInputStream(sock.getInputStream());

		System.out.println("Client Address"+sock.getLocalSocketAddress());
		String sendMessage,recievMessagge;
		
		while(true) {
			StringBuilder command = new StringBuilder();
			String line= new String();
			Scanner s= new Scanner(System.in);

			if(s.hasNext()) {
				line = s.nextLine(); 
				command.append(line);
				if(line.toLowerCase().startsWith("set") && line.split(" ").length == 3) {
					command.append(" ");
					Scanner value = new Scanner(System.in);
					if(value.hasNext()) {
						command.append(value.nextLine());
					}
				}
				sendMessage = command.toString();
				output.writeUTF(sendMessage);
				output.flush();
			}
			if((recievMessagge=input.readUTF())!=null) {
				System.out.println(recievMessagge);	
			}

		}
	}


}
