package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


/**
 * 
 * @author Isaac Sanchez
 *
 */
public class Main extends Application {
	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {
		
		primaryStage.getIcons().add(new Image("/assets/clock.png" ));
		
		// Create a clock and a label
		ClockPane clock = new ClockPane();
		String timeString = clock.getTimeString();
		String dateString = clock.getDateString();
		
		
		Label lblCurrentTime = new Label(timeString);
		Label lblCurrentDate = new Label(dateString);
		VBox dateAndTime = new VBox(10);
		lblCurrentTime.setFont(new Font("Arial", 22));
		lblCurrentDate.setFont(new Font("Arial", 22));
		dateAndTime.getChildren().addAll(lblCurrentDate, lblCurrentTime);
		dateAndTime.setAlignment(Pos.BOTTOM_CENTER);
		
		ControlPane control = new ControlPane(clock);
		lblCurrentTime.textProperty().bind(control.time);

		
		// Place control, clock and label in border pane
		BorderPane pane = new BorderPane();
		pane.setStyle("-fx-base: ANTIQUEWHITE;");
		pane.setPadding(new Insets(20));
		
		pane.setTop(control);
		pane.setCenter(clock);
		pane.setBottom(dateAndTime);
		BorderPane.setAlignment(dateAndTime, Pos.BOTTOM_CENTER);

		// Create a scene and place it in the stage
		Scene scene = new Scene(pane, 420, 600);
		
		primaryStage.setMinWidth(420);
		primaryStage.setMinHeight(600);
		
		primaryStage.setTitle("Clock + Stopwatch  -  Isaac Sanchez"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}

	public static void main(String[] args) {
		launch(args);
	}

}
