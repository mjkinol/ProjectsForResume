package hockey.java.packet;

import hockey.java.front.PVector;

public class PacketPuck {

	public PVector location;
	public PVector velocity;
	
	public double locX;
	public double locY;
	public double velX;
	public double velY;
	
	public PacketPuck() {
		this(0,0,0,0);
	}

	public PacketPuck(double x1, double y1, double x2, double y2) {
	
		locX = x1;
		locY = y1;
		velX = x2;
		velY = y2;
	}
}
