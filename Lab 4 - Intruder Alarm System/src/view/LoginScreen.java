package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LoginScreen {

	private static final int WIDTH = 500;
	private static final int HEIGHT = 280;
	private static LoginScreen instance;
	private Stage primaryStage;
	private PasswordField fldPin;
	private Button btnLogin;
	private ImageView lockIcon;
	private Text status;
	
	public LoginScreen() {
		instance = this;
		go();
	}

	public static LoginScreen getInstance() {
		if (instance == null)
			return new LoginScreen();
		return instance;
	}
	
	private void go() {

		primaryStage = new Stage();
		primaryStage.setMinWidth(WIDTH);
		primaryStage.setMinHeight(HEIGHT);
		primaryStage.getIcons().add(new Image("/assets/icon.png" ));
		double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
		double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
		primaryStage.setX(screenWidth / 2 - WIDTH / 2);
		primaryStage.setY(screenHeight / 2 - HEIGHT / 2);
		primaryStage.setResizable(false);
		
		VBox root = new VBox(10);
		root.setPadding(new Insets(20));
		root.setStyle(
				"-fx-background-image: url(" + "'/assets/background.png'" + "); -fx-background-size: cover;");
		Scene scene = new Scene(root, WIDTH, HEIGHT);

		Label lblPin = new Label("Enter Pin");
		fldPin = new PasswordField();
		lblPin.setFont(Font.font("Arial", FontWeight.BOLD, 18));
		fldPin.setMaxWidth(120);
		fldPin.setPadding(new Insets(10,20,10,20));
		fldPin.setAlignment(Pos.CENTER);
		
		Image icon = new Image("/assets/pin.png");
		lockIcon = new ImageView();
		lockIcon.setImage(icon);
		lockIcon.setFitWidth(28);
		lockIcon.setPreserveRatio(true);
		lockIcon.setSmooth(true);
		lockIcon.setCache(true);
		
		
		HBox pinTitle = new HBox(10);
		pinTitle.getChildren().addAll(lockIcon, lblPin);
		pinTitle.setAlignment(Pos.CENTER);
		
		btnLogin = new Button("Login");
		btnLogin.setPadding(new Insets(10));
		btnLogin.prefWidthProperty().bind(fldPin.widthProperty());
		btnLogin.setAlignment(Pos.BOTTOM_CENTER);
		
		status = new Text("");
		status.setTextAlignment(TextAlignment.CENTER);
		status.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
		status.setFill(Color.CORAL);
		
		root.getChildren().addAll(pinTitle, fldPin, btnLogin, status);
		root.setAlignment(Pos.CENTER);

		primaryStage.setTitle("Intruder Detection System");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
		setHandlers();
	}

	private void setHandlers() {
		btnLogin.setOnMouseClicked(e -> {
			login();
		});
		
		fldPin.setOnKeyReleased(e -> {
			KeyCode key = e.getCode();
			if (key.equals(KeyCode.ENTER)) {
				login();
			} else {
				setStatus("");
			}
		});
	}

	private void login() {
		if (fldPin.getText().equals("2018")) {
			this.getStage().close();
			MainScreen.getInstance();
		} else {
			setStatus("Incorrect PIN.\nTry again.");
		}
	}
	
	private void setStatus(String text) {
		this.status.setText(text);
	
}
private Stage getStage() { return this.primaryStage;	}
}
