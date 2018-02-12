package application;
	
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
		    // Create a border pane 
		    BorderPane pane = new BorderPane();
		    pane.setPadding(new Insets(40));
		    
		    MyPolygon hex = new MyPolygon(6, 100.0, Color.RED);
		    MyPolygon pen = new MyPolygon(5, 100.0);
		    MyCross cross = new MyCross(300);
		    MyRectangle rectLeft = new MyRectangle(200, 125);
		    MyRectangle rectCenter = new MyRectangle(200, 200, 35);
		    
		    // Center nodes
		    BorderPane.setAlignment(hex, Pos.CENTER);
		    BorderPane.setAlignment(pen, Pos.CENTER);
		    BorderPane.setAlignment(cross, Pos.CENTER);
		    BorderPane.setAlignment(rectLeft, Pos.CENTER);
		    BorderPane.setAlignment(rectCenter, Pos.CENTER);
		    
		    // Place nodes in the pane
		    pane.setTop(hex); 		    
		    pane.setRight(cross);
		    pane.setBottom(pen);
		    pane.setLeft(rectLeft);
		    pane.setCenter(rectCenter); 
		    
		    // Create a scene and place it in the stage
		    Scene scene = new Scene(pane, 1000, 800);
		    primaryStage.setTitle("Lab 2 - Isaac Sanchez"); // Set the stage title
		    primaryStage.setScene(scene); // Place the scene in the stage

		    new AnimationTimer() {
		    	@Override
		    	public void handle(long now) {
		    		pen.rotate();
		    		hex.rotate();
		    		cross.rotate();
		    		rectLeft.rotate();
		    		rectCenter.rotate();
		    	}
		    }.start();
		    
		    
		    primaryStage.show(); // Display the stage
		  
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
