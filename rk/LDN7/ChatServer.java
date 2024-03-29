import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;

public class ChatServer {

	protected int serverPort = 1234;
	protected List<ChatServerConnector> clients = new ArrayList<ChatServerConnector>(); // list of clients
	Map<String, ChatServerConnector> clientsMap = new HashMap<String, ChatServerConnector>(); // list of clients

	public static void main(String[] args) throws Exception {
		new ChatServer();
	}

	public ChatServer() {
		ServerSocket serverSocket = null;

		// create socket
		try {
			serverSocket = new ServerSocket(this.serverPort); // create the ServerSocket
		} catch (Exception e) {
			System.err.println("[system] could not create socket on port " + this.serverPort);
			e.printStackTrace(System.err);
			System.exit(1);
		}

		// start listening for new connections
		System.out.println("[system] listening ...");
		try {
			while (true) {
				Socket newClientSocket = serverSocket.accept(); // wait for a new client connection
				ChatServerConnector conn = new ChatServerConnector(this, newClientSocket); // create a new thread for
				// communication with the
				// new client
				synchronized (this) {
					clients.add(conn); // add client to the list of clients
				}
				conn.start(); // run the new thread
			}
		} catch (Exception e) {
			System.err.println("[error] Accept failed.");
			e.printStackTrace(System.err);
			System.exit(1);
		}

		// close socket
		System.out.println("[system] closing server socket ...");
		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}

	// send a message to all clients connected to the server
	public void sendToAllClients(String message, String author) throws Exception {
		Iterator<ChatServerConnector> i = clientsMap.values().iterator();
		while (i.hasNext()) { // iterate through the client list
			Socket socket = ((ChatServerConnector) i.next()).socket;// get the socket for communicating
																	// with this client
			sendMessage(socket, "broadcast " + author + " " + message);
		}
	}

	public void sendMessage(Socket socket, String message) {
		try {
			DataOutputStream out = new DataOutputStream(socket.getOutputStream()); // create output stream for
																					// sending messages to the
																					// client
			out.writeUTF(message); // send message to the client
		} catch (Exception e) {
			System.err.println("[system] could not send message to a client");
			e.printStackTrace(System.err);
		}
	}

	public void removeClient(ChatServerConnector csc) {
		synchronized (this) {
			clients.remove(csc);
			if (csc.loggedIn) {
				clientsMap.remove(csc.name);
			}
		}
	}

	class ChatServerConnector extends Thread {
		private ChatServer server;
		private Socket socket;
		private String name;
		private boolean loggedIn = false;

		public ChatServerConnector(ChatServer server, Socket socket) {
			this.server = server;
			this.socket = socket;
		}

		public void run() {
			System.out.println("[system] connected with " + this.socket.getInetAddress().getHostName() + ":"
					+ this.socket.getPort());

			DataInputStream in;
			try {
				// create input stream for listening for incoming messages
				in = new DataInputStream(this.socket.getInputStream());
			} catch (IOException e) {
				System.err.println("[system] could not open input stream!");
				e.printStackTrace(System.err);
				this.server.removeClient(this);
				return;
			}

			while (true) { // infinite loop in which this thread waits for incoming messages and processes
							// them
				String msg_received;
				try {
					msg_received = in.readUTF(); // read the message from the client
				} catch (Exception e) {
					System.err.println("[system] there was a problem while reading message client on port "
							+ this.socket.getPort() + ", removing client");
					e.printStackTrace(System.err);
					this.server.removeClient(this);
					return;
				}

				if (msg_received.length() == 0) // invalid message
					continue;

				// print the incoming message in the console
				System.out.println("[RKchat] [" + this.socket.getPort() + "] : " + msg_received);

				String[] msg = msg_received.split(" ");

				if (msg.length < 2) {
					this.server.sendMessage(socket, "err 1 Check message structure");
					continue;
				}
				if (!loggedIn) {
					if (msg.length != 2 || !msg[0].equals("login")) {
						this.server.sendMessage(socket,
								"err 0 Error logging in. Make sure your name format is correct");
						System.out.println("Error logging in: '" + msg_received + "'");
						continue;
					}

					if (clientsMap.containsKey(msg[1])) {
						this.server.sendMessage(socket, "err 0 Username is already taken :(");
						System.out.println("Error logging in: '" + msg_received + "'");
						continue;
					}

					if (msg[1].length() < 1) {
						this.server.sendMessage(socket, "err 0 Username is too short");
						System.out.println("Error logging in: '" + msg_received + "'");
						continue;
					}

					this.name = msg[1];
					this.server.sendMessage(socket, "info 0 Logged in as " + name);
					clientsMap.put(this.name, this);
					loggedIn = true;
					continue;
				}

				if (msg[0].equals("all")) {
					try {
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
						String ts = df.format(new Date());

						// send message to all clients
						this.server.sendToAllClients(ts + " " + joinArr(msg, 1, msg.length), this.name);
					} catch (Exception e) {
						System.err.println("[system] there was a problem while sending the message to all clients");
						e.printStackTrace(System.err);
						this.server.sendMessage(socket, "err 1 Error sending message. Please try again.");
						continue;
					}
					continue;
				}

				if (msg[0].charAt(0) == '@') {
					String sendTo = msg[0].substring(1);
					if (!clientsMap.containsKey(sendTo)) {
						this.server.sendMessage(socket,
								"err 1 Requested user is not connected. Please try again later.");
						continue;
					}

					Socket recSocket = clientsMap.get(sendTo).socket;
					try {
						DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
						String ts = df.format(new Date());

						System.out.println(ts);
						this.server.sendMessage(recSocket,
								"msg " + name + " " + ts + " " + joinArr(msg, 1, msg.length));
					} catch (Exception e) {
						System.err.println("[system] there was a problem while sending the message to " + sendTo);
						e.printStackTrace(System.err);
						this.server.sendMessage(socket, "err 1 Error sending message. Please try again.");
						continue;
					}
					continue;
				}
				this.server.sendMessage(socket, "err 1 Unknown command.");
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