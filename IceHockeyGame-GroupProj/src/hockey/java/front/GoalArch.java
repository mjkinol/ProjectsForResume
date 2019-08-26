package hockey.java.front;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GoalArch extends Pane{
	private PVector location;
	
	double radius;
	double height;
	double xstart;
	double ystart;
	Goal goal;
	
	Circle arc;
	
	public GoalArch(Goal g) {
		goal = g;
		
		radius = goal.getW()/2;
		xstart = BoardSettings.SCENE_WIDTH/2;
		if(goal.getID() == 1) {

			ystart = (BoardSettings.BOARDER_HEIGHT/2)-2.7;
			location = new PVector(xstart, ystart);
			arc = new Circle(radius);
			
			arc.setCenterX(xstart);
			arc.setCenterY(ystart);
		}
		else {
			ystart = BoardSettings.SCENE_HEIGHT;
			location = new PVector(xstart, ystart);
			arc = new Circle(radius);
			
			arc.setCenterX(xstart);
			arc.setCenterY(ystart);
		}
		
		arc.setStroke(Color.RED.deriveColor(1, 1, 1, 0.2));
		arc.setFill(Color.SKYBLUE.deriveColor(1, 1, 1, 0.4));
		
		getChildren().add(arc);
	}
	
	public void display() {
	    relocate(location.x, location.y);
	}

}
