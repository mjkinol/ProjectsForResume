import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CWClient extends Thread {
	
	private BufferedReader br;
	private PrintWriter pw;
	private boolean kill = false;
	private Lock clientLock;
	private Condition playingCondition;

	public CWClient(String hostname, int port) {
		Socket s = null;
		Scanner scan = new Scanner(System.in);
		try {
			//Socket allows bidirectional communication
			s = new Socket(hostname, port);
			this.br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			this.pw = new PrintWriter(s.getOutputStream());
			//code that follows this start runs at the same time as run()
			clientLock = new ReentrantLock();
			playingCondition = clientLock.newCondition();
			this.start();
			while(!kill) {
				String line = scan.nextLine();
				pw.println(line);
				pw.flush();
			}
			
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
			scan.close();
		} finally {
			try {
				if(s != null) s.close();
				scan.close();
			} catch (IOException ioe) {
				System.out.println("ioe closing socket: " + ioe.getMessage());
				scan.close();
			}
		}
	}
	
	public void run() {
		try {
			while(!kill) {
				String line = br.readLine();
				if(line != null) {
					if(line.equalsIgnoreCase("^^kill~`")) {
						kill = true;
						br.close();
						pw.close();
						System.exit(0);
						playingCondition.signal();
					}
					else System.out.println(line);
				}
			}
		} catch (IOException ioe) {
			System.out.println("ioe reading from server: " + ioe.getMessage());
		}
	}
	
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.println("Welcome to 201 Crossword");
		System.out.println("");
		System.out.println("Enter the server hostname:");
		String hostname = scan.nextLine();
		System.out.println("Enter the server port:");
		int port = scan.nextInt();
		System.out.println("");
		CWClient cc = new CWClient(hostname, port);
		scan.close();

	}

}
