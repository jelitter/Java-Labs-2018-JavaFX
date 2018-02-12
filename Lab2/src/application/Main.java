package application;
	
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.text.Text;


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
		    primaryStage.setTitle("Lab 2 - Win 1"); // Set the stage title
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
		    
		    primaryStage.setX(150);
		    primaryStage.setY(200);
		    primaryStage.show(); // Display the stage
		    
		    createSecondaryStage();
		  
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void createSecondaryStage() {
		
		Stage secondaryStage = new Stage();
		StackPane pane = new StackPane();
		Scene secondaryScene = new Scene(pane, 600, 800);
		secondaryStage.setTitle("Lab 2 - Win 2"); // Set the stage title
		secondaryStage.setScene(secondaryScene); 
		
		
		Ellipse ellipse = new Ellipse(0,0, 100,50);
		Text text = new Text("Hello");
		
//		secondaryStage.getC
		
		
		secondaryStage.setX(1200);
		secondaryStage.setY(200);
		secondaryStage.show();
		
	}
	
}
