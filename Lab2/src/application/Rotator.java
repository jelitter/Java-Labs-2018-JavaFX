package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineJoin;

public class Rotator extends Polygon {

	private final double MAXSPEED = 3.0;
	private double angle;
	private double speed;

	public Rotator() {
		this.setAngle(0.0);
		this.setSpeed(-1 + 2 * Math.random());
		this.setFill(Color.color(Math.random(), Math.random(), Math.random()));
		
		this.setStroke(Color.BLACK);
		this.setStrokeWidth(10);
		this.setStrokeLineJoin(StrokeLineJoin.ROUND);
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		if (speed >= -MAXSPEED && speed <= MAXSPEED)
			this.speed = speed;
	}

	public void rotate() {
		
		this.setSpeed(this.getSpeed() -1 + 2 * Math.random());

		this.setAngle(this.getAngle() + this.getSpeed());
		this.setRotate(this.getAngle());
	}


}
