package hockey.java.front;

import java.util.Random;

import javafx.scene.layout.Pane;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PowerUpGoalSize extends Pane implements PowerUp {
	private PVector location;
	private boolean hidden;
	
	double width = 20;
    double height = width;
    double centerX = width / 2.0;
    double centerY = height / 2.0;
    double radius = width / 2.0;
    
    Circle circle;
    
	public PowerUpGoalSize() {
		location = new PVector(-100, -100);

		circle = new Circle(radius);
        circle.setCenterX(radius);
        circle.setCenterY(radius);

        circle.setStroke(Color.ORANGE);
        circle.setFill(Color.ORANGE.deriveColor(1, 1, 1, 0.1));

        getChildren().add(circle);
        hide();
	}
	
	public void display() {
		relocate(location.x - centerX, location.y - centerY);
	}
	
	public void activate(Goal g) {
		//change goal size here
		g.enlarge();
		hide();
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
		double x =r.nextDouble() * (BoardSettings.SCENE_WIDTH - (2 * BoardSettings.BOARDER_HEIGHT));
		double y = r.nextDouble() * (BoardSettings.SCENE_HEIGHT - (2 * BoardSettings.BOARDER_HEIGHT));
		if(x >= BoardSettings.SCENE_WIDTH - BoardSettings.BOARDER_HEIGHT) {
			x = BoardSettings.SCENE_WIDTH - BoardSettings.BOARDER_HEIGHT-10;
		}
		else if(x <= BoardSettings.BOARDER_HEIGHT) {
			x = BoardSettings.BOARDER_HEIGHT+10;
		}
		if(y >= BoardSettings.SCENE_HEIGHT - BoardSettings.BOARDER_HEIGHT){
			y = BoardSettings.SCENE_HEIGHT - BoardSettings.BOARDER_HEIGHT-10;
		}
		else if(y <= BoardSettings.BOARDER_HEIGHT) {
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

	public void show(double x, double y) {
		move(x, y);
		hidden = false;
	}
}
