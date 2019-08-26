package hockey.java.front;

public class PVector {

    public double x;
    public double y;

    public PVector() {
    	this(0,0);
    }
    public PVector(double x, double y) {
        super();
        this.x = x;
        this.y = y;
    }

    public PVector(PVector v) {
    	this.copy(v);
	}
    
	public void normalize() {
        double m = mag(); 
        if (m != 0 && m != 1) { 
          div(m); 
        } 
    }

	public static PVector normalize(PVector v) {
        PVector p = new PVector(v);
		double m = p.mag(); 
        if (m != 0 && m != 1) { 
          p.div(m); 
        } 
        return p;
    }

    public void div(double value) {
        x /= value;
        y /= value;
    }

 // added for recalculate
    public static PVector div(PVector v, double value) {
    	return new PVector(v.x / value, v.y / value);
    }
    
    public void mult(double value) {
        x *= value;
        y *= value;
    }

// added for recalculate
    public static PVector mult(PVector v, double value) {
    	return new PVector(v.x * value, v.y * value);
    }
    public void add(PVector v) {
        x += v.x;
        y += v.y;
    }

 // added for recalculate
    public static PVector add(PVector a, PVector b) {
    	return new PVector(a.x+b.x,a.y+b.y);
    }
    
    public void sub(PVector v) {
        x -= v.x;
        y -= v.y;
    }
    
    public static PVector sub(PVector a, PVector b) {
    	return new PVector(a.x-b.x,a.y-b.y);
    }
    

    public void limit(float max) {
        if (mag() > max) {
            normalize();
            mult(max);
        }
    }

    public double mag() {
        return Math.sqrt(x * x + y * y);
    }

    public static PVector sub(PVector v1, PVector v2, PVector target) {
        if (target == null) {
            target = new PVector(v1.x - v2.x, v1.y - v2.y);
        } else {
            target.set(v1.x - v2.x, v1.y - v2.y);
        }
        return target;
    }
    
    public void copy(PVector v) {
    	set(v.x, v.y);
    }


    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public void print() {
    	System.out.println(this.x + " " + this.y);
    }
    
    // added by caesar
    public static PVector normal(PVector v) {
    	return new PVector(-v.y,v.x);
    }
    
    public static double dot(PVector p, PVector q) {
		return p.x * q.x + p.y * q.y;
	}
    
}