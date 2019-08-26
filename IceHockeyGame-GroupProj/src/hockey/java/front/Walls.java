package hockey.java.front;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Walls extends Pane{
	private PVector location;
	Rectangle rec;
	
	public Walls(int num){
		
		if(num == 1) {
			location = new PVector(0, 0);
			rec = new Rectangle();
			rec.setWidth(BoardSettings.SCENE_WIDTH);
			rec.setHeight(BoardSettings.SCENE_HEIGHT);
			rec.setStroke(Color.BLUE);
			rec.setFill(Color.BLUE);
		}
		else {
			location = new PVector(BoardSettings.BOARDER_HEIGHT, BoardSettings.BOARDER_HEIGHT+1);
			rec = new Rectangle();
			rec.setWidth(BoardSettings.SCENE_WIDTH - (BoardSettings.BOARDER_HEIGHT*2));
			rec.setHeight(BoardSettings.SCENE_HEIGHT - (BoardSettings.BOARDER_HEIGHT*2)-2);
			rec.setStroke(Color.WHITE);
			rec.setFill(Color.WHITE);
		}
		getChildren().add(rec);
	}
	
	
	public void display() {
		relocate(location.x, location.y);
    }

}
