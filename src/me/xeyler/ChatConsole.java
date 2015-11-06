package me.xeyler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Scanner;

import javax.swing.*;

public class ChatConsole {
	
	static JPanel pane = new JPanel();
	static JFrame frame = new JFrame();
	static JButton submit = new JButton("Login");
	static JButton signup = new JButton("Sign Up");
	static JTextField usernameField = new JTextField(10);
	static JTextField passwordField = new JTextField(10);
	
	public void setup() throws IOException {

		FileReader reader = new FileReader("res/settings");
		Scanner textReader = new Scanner(reader);
		
		String[] settings = new String[3];
		int step = 0;
		
		while(textReader.hasNextLine()) {
			settings[step] = textReader.nextLine();
			step+=1;
		}
		
		reader.close();
		textReader.close();
		
		frame.setLocation(200, 100);
		frame.setSize(600, 400);
		frame.setTitle("Falco");
		
		ImageIcon img = new ImageIcon("res/chat-icon.png");
		frame.setIconImage(img.getImage());
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);

		if(settings[0] == null) {
			Login();
		} else {
			ClientMain.startThread(settings[0], false);
		}

	}
	
	public void Login() {
		
		submit.addActionListener(press);
		signup.addActionListener(press);
		
		pane.add(usernameField);
		pane.add(passwordField);
		pane.add(submit);
		pane.add(signup);
		
		frame.add(pane);
		
		pane.setVisible(true);
		
	}

	static Action press = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent event) {
		
			if(event.getSource().equals(submit)) {
				ClientMain.startThread(usernameField.getText() + ":" + passwordField.getText(), false);
			} else if(event.getSource().equals(signup)){
				ClientMain.startThread(usernameField.getText() + ":" + passwordField.getText(), true);
			}
		
		}
	};
	
	public static void write(String string) {
		
		System.out.println(string);
		
	}

	public static void home() {
		
		JPanel homePanel = new JPanel();
		JTextArea chat = new JTextArea();
		chat.setEditable(false);
		JTextField input = new JTextField(40);
		input.addActionListener(enter);
		homePanel.add(chat);
		homePanel.add(input);
		frame.removeAll();
		frame.setContentPane(homePanel);
		frame.revalidate();
		
		System.out.println("done!");
		
	}

	static Action enter = new AbstractAction() {
	    @Override
	    public void actionPerformed(ActionEvent event) {
	    	System.out.println("O_O");
	    }
	};
	
	public static void error(String string) {

		JLabel errorLabel = new JLabel(string);
		
		pane.add(errorLabel);
		pane.revalidate();
		pane.repaint();
		frame.revalidate();
		frame.repaint();
		
	}

}
