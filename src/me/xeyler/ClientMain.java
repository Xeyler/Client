package me.xeyler;

import java.io.*;
import java.net.*;

public class ClientMain implements Runnable {

	static Socket socket;
	static DataInputStream in;
	static DataOutputStream out;
	String login;
	boolean register;
	
	public static void main(String[] args) throws IOException {

		ChatConsole console = new ChatConsole();
		console.setup();
		
	}

	public static void startThread(String login, boolean register) {

		ClientMain main = new ClientMain();
		main.login = login;
		main.register = register;
		Thread thread = new Thread(main);
		thread.start();
		
	}
	
	public void run() {

		try{
			socket = new Socket("localhost",24353);
		} catch (IOException error) {
			try{
				InetAddress address = InetAddress.getByAddress(new byte[] { 8, 8, 8, 8 });
				address.isReachable(3000);
				ChatConsole.error("It seems that the server is offline. Please try again later.");
				return;
			} catch (IOException error2) {
				ChatConsole.error("It seems that youre offline. Connect to the internet.");
				return;
			}
		}
		try {
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
		out.writeBoolean(register);
		out.writeUTF(login);
		int code = in.readInt();
		System.out.println(code);
		if(code == 0) {
			ChatConsole.home();
			out.writeUTF("Hey, wassup?");
			while(true){
				
				ChatConsole.write(in.readUTF());
				
			}
		} else if(code == 1) {
			ChatConsole.error("You entered the wrong username and/or password...");
		} else if(code == 2){
			ChatConsole.error("You're logged in on another session.");
		} else if(code == 3) {
			ChatConsole.error("This username is already in use! Pick a different one.");
		} else {
			ChatConsole.error("Something went wrong while connecting...");
		}
		} catch(IOException error) {
			ChatConsole.error("There was an issue communicating with the server...");
		}
		
	}

}
