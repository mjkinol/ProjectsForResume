package hockey.java.packet;

public class PacketMouse {
	public int id;
	public double x;
	public double y;
	public PacketMouse() {
		this(-1,0,0);
	}
	public PacketMouse(int i, double a, double b) {
		id = i;
		x = a;
		y = b;
	}
}
