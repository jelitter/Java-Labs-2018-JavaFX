package application;

import javafx.application.Application;
import javafx.stage.Stage;
import view.LoginScreen;
import view.MainScreen;


public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
//			LoginScreen.getInstance();
			MainScreen.getInstance();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
