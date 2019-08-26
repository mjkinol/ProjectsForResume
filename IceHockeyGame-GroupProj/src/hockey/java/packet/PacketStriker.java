package hockey.java.packet;

import hockey.java.front.PVector;

public class PacketStriker {
/*
	public int id;
	public PVector location;
	public PVector velocity;
	
	public PacketStriker() {
		this(-1,new PVector(0,0),new PVector(0,0));
	}
	
	public PacketStriker(int id, PVector location, PVector velocity) {
		this.id = id;
		this.location = location;
		this.velocity = velocity;
	}
*/
	public int id;
	public double locX;
	public double locY;
	public double velX;
	public double velY;
	
	public PacketStriker() {
		this(-1,0,0,0,0);
	}

	public PacketStriker(int i, double x1, double y1, double x2, double y2) {
		id = i;
		locX = x1;
		locY = y1;
		velX = x2;
		velY = y2;
	}
	
	/*
	public void print() {
		System.out.print("id ");
		if(location == null) System.out.print("null ");
		else System.out.print(location.x + "," + location.y + " ");
		if(velocity == null) System.out.print("null");
		else System.out.println(velocity.x + "," + velocity.y);
	}*/
	
	
	
	
}
