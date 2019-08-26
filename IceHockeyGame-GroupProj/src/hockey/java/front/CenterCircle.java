package hockey.java.front;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CenterCircle extends Pane{
	private PVector location;

	double width = 100;
	double height = width;
	double centerX = width / 2.0;
	double centerY = height / 2.0;
	double radius = width / 2.0;
	 
	 Circle center;
	 
	 public CenterCircle(){
		location = new PVector(BoardSettings.SCENE_WIDTH/2-radius, BoardSettings.SCENE_HEIGHT/2-radius);
		center = new Circle(radius);
		 
		center.setCenterX(radius);
		center.setCenterY(radius);
		
		center.setStroke(Color.RED.deriveColor(1, 1, 1, 0.1));
		center.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.1));
		getChildren().add(center);
		 
	 }
	 
	public void display() {
	    relocate(location.x, location.y);
	}
}
