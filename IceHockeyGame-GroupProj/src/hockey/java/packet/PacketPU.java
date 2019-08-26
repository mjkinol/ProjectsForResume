package hockey.java.packet;

public class PacketPU {
	public int puid;
	public double x;
	public double y;
	public int playerID;
	
	public PacketPU() {
		this(-1,-1,-1,-1);
	}


	public PacketPU(int i , int j) {
		this(i,-1,-1, j);
	}
	
	public PacketPU(int i, double a, double b) {
		this(i,a,b,-1);
	}
	
	public PacketPU(int i, double a, double b, int j) {
		puid = i;
		x = a;
		y = b;
		playerID = j;
	}


	public PacketPU(int i) {
		this(i,-1,-1,-1);
	}
}
