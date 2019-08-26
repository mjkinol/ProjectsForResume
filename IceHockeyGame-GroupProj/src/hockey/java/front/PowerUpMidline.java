package hockey.java.front;
import java.util.Random;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PowerUpMidline extends Pane implements PowerUp {
	
	private PVector location;
	private boolean hidden;
	
	private double width = 20;
	private double height = width;
	private double centerX = width / 2.0;
	private double centerY = height / 2.0;
	private double radius = width / 2.0;
	private Striker s1;
	private Striker s2;
	

	private Circle circle;
	
	public PowerUpMidline(Striker s1, Striker s2) {
		this.s1 = s1;
		this.s2 = s2;
		location = new PVector(-100, -100);
		
		circle = new Circle(radius);
        circle.setCenterX(radius);
        circle.setCenterY(radius);

        circle.setStroke(Color.GREEN);
        circle.setFill(Color.GREEN.deriveColor(1, 1, 1, 0.1));

        getChildren().add(circle);
        
        hide();
	}
	
	
	public void display() {
		relocate(location.x - centerX, location.y - centerY);
	}

	public void activate(Midline m, Striker s) {
		int id = s.getPlayer().getPlayerID();
		
		if (id == 1) {
			m.move((m.startingY*0.5)-15);
			s1.updateMidlineMult(0.5);
			s2.updateMidlineMult(0.5);
		}
		else {
			m.move((m.startingY*1.5)+15);
			s1.updateMidlineMult(1.5);
			s2.updateMidlineMult(1.5);
		}
		hide();
	}


	public void show(double x, double y) {
		move(x, y);
		hidden = false;
	}
	
	public PVector getLocation() {
		return location;
	}

	public double getRadius() {
		return radius;
	}
	
	public void move(double x, double y) {
		location.x = x;
		location.y = y;
		display();
	}
	
	public PVector reset() {
		Random r = new Random();
//		move(r.nextDouble() * (BoardSettings.SCENE_WIDTH - (2 * BoardSettings.BOARDER_HEIGHT)) + BoardSettings.BOARDER_HEIGHT, 
//				r.nextDouble() * (BoardSettings.SCENE_HEIGHT - (2 * BoardSettings.BOARDER_HEIGHT)) + BoardSettings.BOARDER_HEIGHT);
		double x = r.nextDouble() * (BoardSettings.SCENE_WIDTH - (2 * BoardSettings.BOARDER_HEIGHT)) + BoardSettings.BOARDER_HEIGHT;
		double y = r.nextDouble() * (BoardSettings.SCENE_HEIGHT - (2 * BoardSettings.BOARDER_HEIGHT)) + BoardSettings.BOARDER_HEIGHT;

		if(x >= BoardSettings.SCENE_WIDTH - BoardSettings.BOARDER_HEIGHT) {
			x = BoardSettings.SCENE_WIDTH - BoardSettings.BOARDER_HEIGHT-10;
		}
		if(x <= BoardSettings.BOARDER_HEIGHT) {
			x = BoardSettings.BOARDER_HEIGHT+10;
		}
		if(y >= BoardSettings.SCENE_HEIGHT - BoardSettings.BOARDER_HEIGHT){
			y = BoardSettings.SCENE_HEIGHT - BoardSettings.BOARDER_HEIGHT-10;
		}
		if(y <= BoardSettings.BOARDER_HEIGHT) {
			y = BoardSettings.BOARDER_HEIGHT+10;
		}
		move(x, y);
		hidden = false;
		return new PVector(x,y);
	}
	
	public void hide() {
		move(-100, -100);
		hidden = true;
	}
	
	public boolean hidden() {
		return hidden;
	}


	public Circle getCircle() {
		// TODO Auto-generated method stub
		return circle;
	}
	
}
