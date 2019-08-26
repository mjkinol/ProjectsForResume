package hockey.java.front;

import java.text.DecimalFormat;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Test extends Application{

    Pane playfield;
 
    Player p1;
    Player p2;
    Striker s1;
    Striker s2;
    Puck puck;
    Goal goal1, goal2;
    Walls walls1, walls2;
    GoalArch arc1, arc2;
    Midline mid;
    CenterCircle center;
    double friction;
    PowerUpMidline pu;
    PowerUpPuckSize puckPU;
    PowerUpGoalSize goalPU;

    public void displayWinner(Pane playfield, String name) {}
    
    @Override
    public void start(Stage stage) {
    	 p1 = new Player(1);
    	 p2 = new Player(2);
    	 s1 = new Striker(p1);
    	 s2 = new Striker(p2);
    	 puck = new Puck();
    	 
    	 
    	 // TESTTESTTEST
//    	 s1 = null;
//    	 s2 = null;
    	 
    	 pu = new PowerUpMidline(s1, s2);
    	 puckPU = new PowerUpPuckSize();
    	 goalPU = new PowerUpGoalSize();

    	 goal1 = new Goal(1, puck, p1);
    	 goal2 = new Goal(2, puck, p2);
    	 arc1 = new GoalArch(goal1);
    	 arc2 = new GoalArch(goal2);
    	 walls1 = new Walls(1);
    	 walls2 = new Walls(2);

    	 mid = new Midline();
    	 center = new CenterCircle();
    	 friction = .988;
    	 
    	 Text p1s = new Text(Integer.toString(p1.getScore()));
     	 p1s.setFont(Font.font ("Verdana", 50));
     	 p1s.setFill(Color.RED);
     	 p1s.setX(350);
     	 p1s.setY(400);

     	 Text p2s = new Text(Integer.toString(p2.getScore()));
    	 p2s.setFont(Font.font ("Verdana", 50));
    	 p2s.setFill(Color.RED);
    	 p2s.setX(350);
    	 p2s.setY(335);
    	 
    	 // create containers
    	 BorderPane root = new BorderPane();
         StackPane layerPane = new StackPane();
         // playfield for our strikers 
         playfield = new Pane();
         layerPane.getChildren().addAll(playfield);
         root.setCenter(layerPane);
         Scene scene = new Scene(root, BoardSettings.SCENE_WIDTH, BoardSettings.SCENE_HEIGHT);
         stage.setScene(scene);
         stage.show();
         playfield.getChildren().add(walls1);
         playfield.getChildren().add(walls2);
         playfield.getChildren().add(center);
         playfield.getChildren().add(mid);
         playfield.getChildren().add(arc1);
         playfield.getChildren().add(arc2);
         
         
         if(s1 != null) playfield.getChildren().add(s1);
         
         // ADD s2
         if(s2 != null) playfield.getChildren().add(s2);
         
         
         playfield.getChildren().add(puck);
         playfield.getChildren().add(goal1);
         playfield.getChildren().add(goal2);
         playfield.getChildren().add(p1s);
         playfield.getChildren().add(p2s);
         //display static shapes
         center.display();
         arc1.display();
         arc2.display();
         goal1.display();
         goal2.display();
         walls1.display();
         walls2.display();
         
    	 playfield.getChildren().add(pu);
    	 playfield.getChildren().add(puckPU);
    	 playfield.getChildren().add(goalPU);
         
         mid.display();
         // capture mouse position
         scene.addEventFilter(MouseEvent.ANY, e -> {
             p1.getMouse().set(e.getX(), e.getY());
             p2.getMouse().set(e.getX(), e.getY());
         });
         // process all strikers
         AnimationTimer loop = new AnimationTimer() {
        	 int time = 0;
        	 Random r = new Random();
        	 int ran = (int) (r.nextDouble() * 100);
             @Override
             public void handle(long now) {
            	 

            	 DecimalFormat form = new DecimalFormat("#.00");

            	 
//            	 if(s1 != null) System.out.println("s1 at (" + form.format(s1.getLocation().x) + "," + form.format(s1.getLocation().y) + ") vel (" + form.format(s1.getVelocity().x) + "," + form.format(s1.getVelocity().y) + ")");
//            	 if(s1 != null) System.out.println("s2 at (" + form.format(s2.getLocation().x) + "," + form.format(s2.getLocation().y) + ") vel (" + form.format(s2.getVelocity().x) + "," + form.format(s2.getVelocity().y) + ")");
//            	 System.out.println("pk at (" + form.format(puck.getLocation().x) + "," + form.format(puck.getLocation().y) + ") vel (" + form.format(puck.getVelocity().x) + "," + form.format(puck.getVelocity().y) + ")");

            	 
            	 
                 // move
            	 pu.display();
            	 puckPU.display();
            	 
            	 if(s1 != null) s1.step(p1.getMouse(), mid);
            	 if(s2 != null) s2.step(p2.getMouse(), mid);


                 if(s1 != null) s1.checkStrikerWallsMidline();
                 if(s2 != null) s2.checkStrikerWallsMidline();
                 
                 puck.checkPuckWalls(goal1, goal2);
                 
                 if(s1 != null) {
                	 if(puck.collision(s1)) {
                		 puck.recalculate(s1);
                	 }
                 }
                 if(s2 != null) {
                	 if(puck.collision(s2)) {
                		 puck.recalculate(s2);
                	 }
                 }
                 
                 //puck.collision(otherStriker);
                 puck.step(friction);
                 // update in fx scene
                 if(s1 != null) s1.display();
                 if(s2 != null) s2.display();
                 //otherStriker.display();
                 puck.display();
                 if (goal1.goalDetection(1)) {
                	 p1.score();
                	 p1s.setText(Integer.toString(p1.getScore()));
                	 stage.show();
                	 if(s1 != null) s1.reset(1);
                	 if(s2 != null) s2.reset(2);
                	 mid.reset();
                	 puck.resetSize();
                	 //otherStriker.reset(2);
                 }
                 if (goal2.goalDetection(2)) {
                	 p2.score();
                	 p2s.setText(Integer.toString(p2.getScore()));
                	 stage.show();
                	 if(s1 != null) s1.reset(1);
                	 if(s2 != null) s2.reset(2);
                	 mid.reset();
                	 puck.resetSize();
                	 //otherStriker.reset(2);
                 }
                 if (p1.getScore() == 7) {
                	 //Text text = new Text("  " + p1.getUsername() + " wins!");
                 	 //text.setFont(Font.font ("Verdana", 50));
                 	 //text.setFill(Color.RED);
                 	 //text.setY(350);
                 	 //playfield.getChildren().add(text);
                	 //display message based on winner or loser
                 	 stage.show();
                	 stop();
                 }
                 if (p2.getScore() == 7) {
                	 //Text text = new Text("  " + p2.getUsername() + " wins!");
                	 //text.setFont(Font.font ("Verdana", 50));
                 	 //text.setFill(Color.RED);
                 	 //text.setY(350);
                 	 //playfield.getChildren().add(text);
                	 //display message based on winner or loser
                 	 stage.show();
                	 stop();
                 } 
                 puck.collision(mid, pu);
                 puck.collision(puckPU);
                 puck.collision(goalPU, goal1, goal2);

                 time++;
                 
                 if (time == ran) {
                	 
                	 time = 0;
                	 int choose = new Random().nextInt() % 1;
                	 if (choose == 3) {
                		 if (pu.hidden() && mid.inMiddle()) {
                    		 pu.reset();
                    	 }
                	 }
                	 else if (choose == 1){
                		 if (puckPU.hidden() && puck.width == 30) {
                    		 puckPU.reset();
                    	 }
                	 }

                	 else {
                		 if (goalPU.hidden() && goal1.getW() == 110 && goal2.getW() == 110) {
                			 goalPU.reset();
                		 }
                	 }
                	 ran = (int)r.nextDouble() * 2500;

                 }
                 System.out.println(time + " < " + ran);
             }
         };
         loop.start();
    }

    public static void main(String[] args) {
    	launch(args);
    }
    
}