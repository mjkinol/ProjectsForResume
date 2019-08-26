import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class CWServerThread extends Thread {
	
	private BufferedReader br;
	private PrintWriter pw;
	//private CWServer cw;
	private Socket thisSocket;
	
	public CWServerThread(Socket s, CWServer cw) {
		//this.cw = cw;
		thisSocket = s;
		try {
			this.br = new BufferedReader(new InputStreamReader(thisSocket.getInputStream()));
			this.pw = new PrintWriter(thisSocket.getOutputStream());
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}
	public synchronized void sendMessage(String message) {
		pw.println(message);
		pw.flush();
	}
	public synchronized void sendMessageNoPL(String message) {
		pw.print(message);
		pw.flush();
	}
	
	public String readMessage() {
		try {
			String words = br.readLine();
			return words;
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
			return null;
		}
	}
	
	public void run() {
		while(true) {

		}
	}

}
