package server;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


public class ThreadHandler extends Thread{
	private Socket sock;
	private String clientId;
	final DataInputStream input; 
	final DataOutputStream output; 
	private static Map<String, String> map = new HashMap<String, String>();


	public ThreadHandler(Socket sock2, String count,DataInputStream input, DataOutputStream output) {
		this.output = output;
		this.input = input;
		this.sock= sock2;
		this.clientId= count;
		setMap();
		System.out.println(count);

	}
	
	
	// Reading the data from file to update the Map
	void setMap()  {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("data.csv"));
		} catch (FileNotFoundException e1) {
			System.out.println(e1);
		}
		String data= new String();
		try {
			while(null != (data=br.readLine())) {
				String[] readData = data.split(",");
				if (null != readData[0] && null != readData[1]) {
					ThreadHandler.map.put(readData[0], readData[1]);
				}
			}
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void run() {
		String inputmsg;
		while (true)  
		{ 
			try {

				inputmsg = input.readUTF();

				String[] inputCommand = inputmsg.split(" ");
				System.out.println(Arrays.toString(inputCommand));

				
				if (null!= inputCommand && inputCommand.length>=2 && null!= inputCommand[0] && null!=inputCommand[1]) {
					String command = inputCommand[0];
					String key= inputCommand[1];

					//Check for set Condition
					if("SET".equalsIgnoreCase(command) && inputCommand.length >= 4) {
						String byteSize = inputCommand[2];
						StringBuilder valueBuilder = new StringBuilder();
						String value = new String();
						
						//reading the input Command and extracting the data required
						for(int i=3;i<=inputCommand.length-1;i++) {
							valueBuilder.append(inputCommand[i]).append(" ");
						}
						value = valueBuilder.toString().substring(0, valueBuilder.toString().length()-1);

						if(null != key && null != byteSize && null != value.toString() ) {
							int valueSize = 0;
							try {
								//Handle Exception if Byte Size is not Integer
								valueSize = Integer.parseInt(byteSize) ;
							}catch(NumberFormatException e) {
								output.writeUTF("Enter The Valid Command");
							}
							//Checking if Input not exceeding Byte Size
							if(valueSize>= value.toString().length()) {
								
								//Adding Entry to map As well as CSV
								map.put(key,value.toString());
								try(Writer write = new FileWriter("data.csv")){

									for(Entry<String, String> entry:map.entrySet()) {
										write.append(entry.getKey()).append(",").append(entry.getValue()).append("\r\n");
									}
								}catch(IOException e) {
									System.out.println("File Handling Issue: "+ e);
								}
								output.writeUTF("Stored\r\n");
							}else {
								output.writeUTF("Not Stored\r\n");
							}
						}else {
							output.writeUTF("Please Enter the valid Command");
						}
						// Check for get Command
					}else if ("GET".equalsIgnoreCase(command) && inputCommand.length ==2) {
						// Update the Map
						setMap();

						if(null != key){
							// Check if we have the key to Start
							if(map.containsKey(key)) {
								output.writeUTF("Value "+key+" "+map.get(key).length()+"\r\n"+(String) map.get(key)+"\r\n"+"END\r\n");	
							}else {
								output.writeUTF("Key Not Found");
							}
						}else {
							output.writeUTF("Please Enter the valid Command");
						}
					}else {
						output.writeUTF("Please Enter the valid Command");
					}
				}else if("bye".equalsIgnoreCase(inputCommand[0])){
					this.output.close();;
					this.input.close();
					this.sock.close();
				}else{
					output.writeUTF("Please Enter the valid Command");					
				}
			} catch (IOException e) {
				try {
					this.sock.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

}
