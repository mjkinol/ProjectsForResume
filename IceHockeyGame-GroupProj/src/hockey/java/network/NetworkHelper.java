package hockey.java.network;

import com.esotericsoftware.kryo.Kryo;

import hockey.java.front.PVector;
import hockey.java.front.Player;
import hockey.java.front.Puck;
import hockey.java.front.Striker;
import hockey.java.packet.Constants;
import hockey.java.packet.PacketAttempt;
import hockey.java.packet.PacketMouse;
import hockey.java.packet.PacketPU;
import hockey.java.packet.PacketPuck;
import hockey.java.packet.PacketReturn;
import hockey.java.packet.PacketStats;
import hockey.java.packet.PacketStriker;

public class NetworkHelper {


	public static final String username = "root";

	public static final String password = "doghot";
	
	public static final String server_ngrok_url = "localhost";
	public static final int server_tcpPort = 23333;

//	public static final String client_ngrok_url = "localhost";
//	public static final int client_tcpPort = 23333;
	
	public static final String client_ngrok_url = "tcp://0.tcp.ngrok.io";
	public static final int client_tcpPort = 15018;
	
	
	public static void registerClasses(Kryo k) {

		// register packet. ONLY objects registered as packets can be sent
		k.register(PacketAttempt.class);
		k.register(PacketReturn.class);
		k.register(PacketStats.class);
		k.register(PacketStriker.class);
		k.register(PacketMouse.class);
		k.register(PacketPuck.class);
		k.register(PacketPU.class);
		k.register(Striker.class);
		k.register(Player.class);
		k.register(Puck.class);
		k.register(PVector.class);
		k.register(Constants.class);
		
		//k.register(com.sun.javafx.geom.RectBounds.class);
	}

}
