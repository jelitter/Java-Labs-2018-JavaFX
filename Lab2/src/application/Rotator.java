package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineJoin;

public class Rotator extends Polygon {

	private static final double MAXSPEED = 2.0;
	private double angle;
	private double speed;
	public Color col;

	public Rotator() {

		this.setAngle(0.0);
		this.setSpeed(-MAXSPEED + 2 * MAXSPEED * Math.random());
		this.col = Color.color(Math.random(), Math.random(), Math.random());
		this.setFill(col);

		this.setStroke(col.darker());
		this.setStrokeWidth(10);
		this.setStrokeLineJoin(StrokeLineJoin.ROUND);
	}

	public double getAngle() { return angle;	}
	public void setAngle(double angle) { this.angle = angle;	}

	public double getSpeed() { return speed;	}
	public void setSpeed(double speed) {
		if (speed >= -MAXSPEED && speed <= MAXSPEED)
			this.speed = speed;
	}

	public void rotate() {

//		Uncomment this to make rotation speed random on each iteration
//		this.setSpeed(this.getSpeed() -1 + 2 * Math.random());
		this.setAngle(this.getAngle() + this.getSpeed());
		this.setRotate(this.getAngle());

		this.setOpacity();
//		Uncomment this to make polygons move based on their rotation
//		this.translate();  
	}

	private void setOpacity() {
		
		// Setting opacity based on angular speed
		
		//		https://stackoverflow.com/questions/345187/math-mapping-numbers
		//		If your number X falls between A and B, and you would like Y to fall between C and D, you can apply the following linear transform:
		//			Y = (X-A)/(B-A) * (D-C) + C

		
		double y, x,a,b,c,d;

		a = -MAXSPEED;
		b = MAXSPEED;
		c = 0.5;  // Min. Opacity
		d = 1;  // Max. Opacity
		x = this.getSpeed();
		y = (x-a)/(b-a) * (d-c) + c;

		this.setOpacity(y);
	}

	private void translate() {

		double x,y;

		x = this.getTranslateX() + this.getSpeed() / 3;
		y = this.getTranslateY() + this.getSpeed() / 2;
		
		x *= (Math.random() < 0.0025) ? -1: 1;
		y *= (Math.random() < 0.0025) ? -1: 1;

		this.setTranslateX(x);
		this.setTranslateY(y);
	}
}
