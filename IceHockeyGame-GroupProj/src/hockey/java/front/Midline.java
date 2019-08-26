package hockey.java.front;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Midline extends Pane {
	
	private PVector location;
	
	double height = BoardSettings.BOARDER_HEIGHT/2;
	double width = BoardSettings.SCENE_WIDTH;
	double startingX = 0;
	double startingY = (BoardSettings.SCENE_HEIGHT/2)-2;
	
	Rectangle rec;

	public Midline(){
		location = new PVector(startingX, startingY);
		rec = new Rectangle();
		rec.setWidth(width);
		rec.setHeight(height);
		rec.setStroke(Color.BLUE.deriveColor(1, 1, 1, 0.3));
		rec.setFill(Color.BLUE.deriveColor(1, 1, 1, 0.3));
		getChildren().add(rec);
	}
	//TODO add circle with line
	
	public void display() {
        relocate(location.x, location.y);
    }
	
	public void move(double y) {
		location.y = y;
		display();
	}

	public void reset() {
		location.y = startingY;
		display();
	}
	
	public boolean inMiddle() {
		return location.y == startingY;
	}
	
	public double getLocation() {
		return location.y;
	}
	
}
