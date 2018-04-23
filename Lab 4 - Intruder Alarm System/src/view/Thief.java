package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Thief extends ImageView {
	
	private static final double STEP = 3;
	private static final double THIEF_SIZE = 64;
	private static final int THIEF_GIFS = 4;
	private double velX, velY;
	private StackPane stack;

	public Thief(StackPane stack) {
		this.stack = stack;
		newVel();
		
		Image icon = new Image("/assets/thief" + (int)(1 + Math.floor(Math.random() * THIEF_GIFS)) + ".gif");
		this.setImage(icon);
		this.setFitHeight(THIEF_SIZE);
		this.setPreserveRatio(true);
		this.setSmooth(true);
		this.setCache(true);
		
		DropShadow shadow = new DropShadow();
		shadow.setOffsetY(0.0f);
		shadow.setOffsetX(0.0f);
		shadow.setColor(Color.BLACK);
		this.setEffect(shadow);
		
		Timeline movement = new Timeline(new KeyFrame(Duration.millis(33), ae -> {
			this.move();
		}));
		movement.setCycleCount(Timeline.INDEFINITE);
		movement.play();
		
		Timeline velocityChange = new Timeline(new KeyFrame(Duration.seconds((int)(7 + Math.floor(Math.random() * 5))), ae -> {
			newVel();
		}));
		velocityChange.setCycleCount(Timeline.INDEFINITE);
		velocityChange.play();
	}

	private void newVel() {
		velX = 2+ STEP * Math.random();
		velY = 1+ STEP * Math.random();
		this.setScaleX(this.velX > 0 ? 1 : -1);
	}
	
	public void move() {
		
		double maxX = stack.getWidth()/2;
		double maxY = stack.getHeight()/2;
		
		
		if (this.getTranslateX() > maxX - THIEF_SIZE / 2 || this.getTranslateX() < -maxX) {
			this.velX *= -1;
			this.setScaleX(this.velX > 0 ? 1 : -1);
		}

		if (this.getTranslateY() > maxY - THIEF_SIZE / 2 || this.getTranslateY() < -maxY) {
			this.velY *= -1;
		}
		
		this.setTranslateX(this.getTranslateX() + this.velX);
		this.setTranslateY(this.getTranslateY() + this.velY);
	}

	public boolean isInRoom(RoomPane room) {
		
		double maxX = stack.getWidth()/2;
		double maxY = stack.getHeight()/2;

		double x = this.getTranslateX() + maxX;
		double y = this.getTranslateY() + maxY;
		
		double roomX = room.getLayoutX();
		double roomW = room.getWidth();
		double roomY = room.getLayoutY();
		double roomH = room.getHeight();
		
		boolean detected = (x > roomX && x < roomX + roomW && y > roomY && y < roomY + roomH);
		
//		System.out.println("Room: " + roomX + ", " + roomY + ", " + roomW + ", " + roomH);
//		System.out.println("Thief: " + x + ", " + y);
//		System.out.println("Detected: " + detected);
		
		return detected;
	}
}
