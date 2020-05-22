import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

// Outbound:
// 
// Login: login <...>
// 
// Public: all <...>
// 
// Private: @<user> <...>

// Inbound: 
// 
// Info: info <id> <...> 
// id = 0 => logged in
//
// Error: err <id> <...>
// id = 0 => login failed 
// id = 1 => message send failed
//
// Broadcast: broadcast <author> <timestamp> <...> 
// 
// Message: msg <author> <timestamp> <...>
// 

public class ChatClient extends Thread {
	protected int serverPort = 1234;
	protected boolean loggedIn = false;

	public static void main(String[] args) throws Exception {
		new ChatClient();
	}

	public ChatClient() throws Exception {
		Socket socket = null;
		DataInputStream in = null;
		DataOutputStream out = null;

		// connect to the chat server
		try {
			System.out.println("[system] connecting to chat server ...");
			socket = new Socket("localhost", serverPort); // create socket connection
			in = new DataInputStream(socket.getInputStream()); // create input stream for listening for incoming
																// messages
			out = new DataOutputStream(socket.getOutputStream()); // create output stream for sending messages
			System.out.println("[system] connected");

			ChatClientMessageReceiver message_receiver = new ChatClientMessageReceiver(in); // create a separate thread
																							// for listening to messages
																							// from the chat server
			message_receiver.start(); // run the new thread
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}

		// read from STDIN and send messages to the chat server
		BufferedReader std_in = new BufferedReader(new InputStreamReader(System.in));
		String userInput;
		System.out.print("Choose a cool username: ");
		while ((userInput = std_in.readLine()) != null) { // read a line from the console
			if (!loggedIn) {
				this.login(userInput, out);
			} else {
				this.sendMessage(userInput, out); // send the message to the chat server
			}
		}

		// cleanup
		in.close();
		out.close();
		std_in.close();
		socket.close();
	}

	private void sendMessage(String message, DataOutputStream out) {
		try {
			out.writeUTF(message); // send the message to the chat server
			out.flush(); // ensure the message has been sent
		} catch (IOException e) {
			System.err.println("[system] could not send message");
			e.printStackTrace(System.err);
		}
	}

	private void login(String username, DataOutputStream out) {
		sendMessage("login " + username, out);
	}

	// wait for messages from the chat server and print the out
	class ChatClientMessageReceiver extends Thread {
		private DataInputStream in;

		public ChatClientMessageReceiver(DataInputStream in) {
			this.in = in;
		}

		public void run() {
			try {
				String message;
				while ((message = this.in.readUTF()) != null) { // read new message
					String[] msg = message.split(" ");
					if (msg.length < 2) {
						System.out.println("Unknown short message from server: " + message);
					}

					if (msg[0].equals("err")) {
						if (msg[1].equals("0")) {
							System.out.print(
									"Login failed with: " + joinArr(msg, 2, msg.length) + "\nPlease try again: ");
						}
						if (msg[1].equals("1")) {
							System.out.println("Error: " + joinArr(msg, 2, msg.length));
						}

						continue;
					}

					if (msg[0].equals("info")) {
						if (msg[1].equals("0")) {
							loggedIn = true;
							System.out.println(joinArr(msg, 2, msg.length));
						}
						continue;
					}

					if (msg[0].equals("broadcast")) {
						String author = msg[1];
						String valuableInfo = joinArr(msg, 3, msg.length);

						DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
						DateFormat df2 = new SimpleDateFormat("[d.MMM HH:mm]");

						try {
							Date d = df1.parse(msg[2]);
							System.out.printf("[Broadcast]%s %s said:\n%s\n", df2.format(d), author, valuableInfo);
						} catch (Exception e) {
							System.err.println(
									"[system] there was a problem parsing message: " + joinArr(msg, 0, msg.length));
							e.printStackTrace(System.err);
						}
						continue;
					}

					if (msg[0].equals("msg")) {
						String author = msg[1];
						String valuableInfo = joinArr(msg, 3, msg.length);

						DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
						DateFormat df2 = new SimpleDateFormat("[d.MMM HH:mm]");

						try {
							Date d = df1.parse(msg[2]);
							System.out.printf("%s %s said:\n%s\n", df2.format(d), author, valuableInfo);
						} catch (Exception e) {
							System.err.println(
									"[system] there was a problem parsing message: " + joinArr(msg, 0, msg.length));
							e.printStackTrace(System.err);
						}
						continue;
					}

					System.out.println("Unknown short message from server: " + message);
				}
			} catch (Exception e) {
				System.err.println("[system] could not read message");
				e.printStackTrace(System.err);
				System.exit(1);
			}
		}

		String joinArr(String[] arr, int from, int to) {
			StringBuilder sb = new StringBuilder();
			for (int i = from; i < to; i++) {
				sb.append(arr[i]);
				sb.append(" ");
			}
			return sb.toString();
		}

	}
}