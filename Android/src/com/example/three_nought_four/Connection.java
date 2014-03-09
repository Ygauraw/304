package com.example.three_nought_four;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.json.simple.JSONObject;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.StrictMode;

/**
 * This class will use something like JSON to transfer content to another player playing via the
 * Internet.
 * 
 * @author sg3809
 * 
 */
@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class Connection {

	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;
	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

	public Connection(String string, int port) {
		StrictMode.setThreadPolicy(policy);
		System.out.println("Entered Connection constructor");
		try {
			socket = new Socket(string, port);
			System.out.println("Socket created");
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e) {
				System.out.println("IOException caught BREADER");
			}
			try {
				writer = new PrintWriter(socket.getOutputStream(), true);
			} catch (IOException e) {
				System.out.println("IOException caught PWRITER");
			}
		} catch (IOException e) {
			System.out.println("IOException caught kld");
			System.out.println(e);
		} catch (Exception f) {
			System.out.println("Caught exception kld");
			System.out.println(f);
		}

	}

	public synchronized String receiveMessage() {

		try {
			String message = reader.readLine();
			if (message != null) {
				System.out.println("Received: " + message);
				return message;
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return "";
	}

	/**
	 * Implements message passing (i.e Async send)
	 * 
	 * @param message
	 * @return
	 */
	// For now just tries to send the message.
	public synchronized void sendMessage(String message) {
		StrictMode.setThreadPolicy(policy);
		System.out.println("Sending: " + message + " to :" + socket.getInetAddress());

		// Then put the message into the output-stream
		writer.println(message);
	}

	public void close() throws IOException {
		writer.close();
		reader.close();
		socket.close();
	}

	public void sendOk() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("st", "ok");
		sendMessage(jsonObject.toString());
	}

	public Socket getSocket() {
		return socket;
	}

}
