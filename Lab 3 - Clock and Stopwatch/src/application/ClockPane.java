package application;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;


public class ClockPane extends Pane {

	private static final int CLOCK_MARGIN = 16;

	private Group frame, hands;
	private Circle c1, c2;
	private Calendar cal;
	private int hour;
	private int minute;
	private double second;
	private double decs;
	private double moveEverySecond;

	/** Construct a default clock with the current time */
	public ClockPane() {
		cal = Calendar.getInstance();
		setCurrentTime();
	}

	/** Construct a clock with specified hour, minute, and second */
	public ClockPane(int hour, int minute, int second) {
		this.hour = hour;
		this.minute = minute;
		this.second = second;
	}

	/** Return hour */
	public int getHour() {
		return hour;
	}

	/** Set a new hour */
	public void setHour(int hour) {
		this.hour = hour;
		paintClock();
	}

	/** Return minute */
	public int getMinute() {
		return minute;
	}

	/** Set a new minute */
	public void setMinute(int minute) {
		this.minute = minute;
		paintClock();
	}

	/** Return second */
	public double getSecond() {
		return second;
	}

	/** Set a new second */
	public void setSecond(int second) {
		this.second = second;
		paintClock();
	}

	/* Set the current time for the clock */
	public void setCurrentTime() {
		// Construct a calendar for the current date and time
		Calendar calendar = new GregorianCalendar();

		// Set current hour, minute and second
		this.hour = calendar.get(Calendar.HOUR_OF_DAY);
		this.minute = calendar.get(Calendar.MINUTE);
		this.second = calendar.get(Calendar.SECOND);
		this.decs = (double) calendar.get(Calendar.MILLISECOND) / 1000;
		
		if (moveEverySecond == 0.1) {
			this.second = second + round(decs, 1);
		} else if (moveEverySecond == 0.5) {
			this.second = decs < .5 ? second : second + .5 ;
		}
		
		paintClock(); // Repaint the clock
	}

	/** Paint the clock */
	private void paintClock() {
		// Initialize clock parameters
		double clockRadius = Math.min(getWidth(), getHeight()) * 0.8 * 0.5;
		double centerX = getWidth() / 2;
		double centerY = getHeight() / 2;

		frame = new Group();
		hands = new Group();

		setupClockFrame(clockRadius, centerX, centerY, frame);
		setClockHands(clockRadius, centerX, centerY, hands);

		getChildren().clear();
		getChildren().addAll(frame, hands);
	}

	private void setupClockFrame(double clockRadius, double centerX, double centerY, Group frame) {
		// Draw circle
		c1 = new Circle(centerX, centerY, clockRadius);
		c2 = new Circle(centerX, centerY, clockRadius);
		;
		c1.setFill(Color.WHITE);
		c1.setStrokeWidth(10);
		c1.setStroke(Color.DARKGOLDENROD);

		c2.setFill(Color.TRANSPARENT);
		c2.setStrokeWidth(6);
		c2.setStroke(Color.GOLD);
		
		// Clock minute marks and numbers
		ArrayList<Polygon> marks = new ArrayList<>();
		ArrayList<Text> numbers = new ArrayList<>();

		double markWidth, markLength;
		Color markColor;
		for (int i = 0; i < 60; i++) {
			if (i % 5  == 0) {
				markWidth = 2;
				markLength = 10;
				markColor = Color.RED;
			} else {
				markWidth = 1;
				markLength = 5;
				markColor = Color.BLACK;
			}
			
			Polygon m = new Polygon();
			m.setFill(markColor);
			m.getPoints()
			.addAll(new Double[] { 
					centerX +markWidth, centerY - clockRadius +35, 
					centerX -markWidth, centerY - clockRadius +35, 
					centerX - markWidth/2, centerY - clockRadius +35 + markLength, 
					centerX + markWidth/2, centerY - clockRadius + 35 + markLength });
			m.getTransforms().addAll(new Rotate(i * 6, centerX, centerY));
			marks.add(m);
			
			if ( i % 5 == 0) {
				Text thour = new Text(Integer.toString( i > 0 ? i/5 : 12 ));
				thour.setFont(Font.font("Arial", FontWeight.MEDIUM, 22));
				thour.setFontSmoothingType(FontSmoothingType.GRAY);
				thour.setTextAlignment(TextAlignment.CENTER);
				thour.setX(centerX - thour.getLayoutBounds().getWidth()/2);
				thour.setY(centerY - clockRadius + 27);
				thour.getTransforms().addAll(new Rotate(i * 6, centerX, centerY));
				thour.setBlendMode(BlendMode.MULTIPLY);
				
//				double r = clockRadius - 27;
//				double cx = centerX + r * Math.sin(i * (2 * Math.PI / 60));
//				double cy = centerY - r * Math.cos(i * (2 * Math.PI / 60));
//				thour.getTransforms().addAll(new Rotate( -i * 6, cx, cy));
				
				numbers.add(thour);
				
				Text t  = new Text(Integer.toString( i ));
				t.setFont(Font.font("Arial", FontWeight.LIGHT, 14));
				t.setFontSmoothingType(FontSmoothingType.GRAY);
				t.setTextAlignment(TextAlignment.CENTER);
				t.setX(centerX - t.getLayoutBounds().getWidth()/2);
				t.setY(centerY - clockRadius + 58);
				t.getTransforms().addAll(new Rotate(i * 6, centerX, centerY));
				t.setFill(second == i ? Color.RED : Color.BLACK);
				numbers.add(t);
			} 
		}


		frame.getChildren().addAll(c1, c2);
		frame.getChildren().addAll(marks);
		frame.getChildren().addAll(numbers);
	}

	private void setClockHands(double clockRadius, double centerX, double centerY, Group needles) {
		// Draw second hand
		Polygon secondHand = new Polygon();
		double secondWidth = Math.max(clockRadius * 0.01, 2);
		double sLength = clockRadius * 0.8;
		secondHand.setFill(Color.RED);
		secondHand.getPoints()
		.addAll(new Double[] { 
				centerX +secondWidth/2, centerY - sLength, 
				centerX -secondWidth/2, centerY - sLength, 
				centerX - secondWidth, centerY +28 , 
				centerX + secondWidth, centerY +28 });
		secondHand.getTransforms().addAll(new Rotate( (second) * 6, centerX, centerY));

//		new Timeline(new KeyFrame(Duration.millis(300), ae -> {
//			secondHand.getTransforms().add(new Rotate( -1, centerX, centerY));
//		})).play();
		

		 //Draw minute hand
		Polygon minuteHand = new Polygon();
		double minuteWidth = Math.max(clockRadius * 0.025, 2);
//		double mLength = clockRadius * 0.8;
		double mLength = clockRadius - 30;
		minuteHand.setStrokeWidth(1);
		minuteHand.setStroke(Color.GRAY);
		minuteHand.setFill(Color.BLACK);
		minuteHand.getPoints()
		.addAll(new Double[] { 
				centerX + 2*minuteWidth/3, centerY - mLength, 
				centerX - 2*minuteWidth/3, centerY - mLength, 
				centerX - minuteWidth, centerY +23 , 
				centerX + minuteWidth, centerY +23 });
		minuteHand.getTransforms().addAll(new Rotate(minute * 6, centerX, centerY));
		
		// Draw hour hand
		Polygon hourHand = new Polygon();
		double hourWidth = Math.max(clockRadius * 0.04, 3);
		double hLength = clockRadius * 0.5;
		hourHand.setFill(Color.BLACK);
		hourHand.getPoints()
		.addAll(new Double[] { 
				centerX + 2*hourWidth/3, centerY - hLength, 
				centerX - 2*hourWidth/3, centerY - hLength, 
				centerX - hourWidth, centerY +18 , 
				centerX + hourWidth, centerY +18 });
		hourHand.getTransforms().addAll(new Rotate( (hour%12) * 30, centerX, centerY));

		Circle dot = new Circle(centerX, centerY, 5);
		dot.setFill(Color.RED);
		
		needles.getChildren().addAll(hourHand, minuteHand, secondHand, dot);

		// Setting up needles
		for (Node n : needles.getChildren()) {
			((Shape) n).setStrokeLineCap(StrokeLineCap.ROUND);
			((Shape) n).setSmooth(true);
//			((Shape) n).setBlendMode(BlendMode.MULTIPLY);
		}
	}


	@Override
	public void setWidth(double width) {
		super.setWidth(width);
		paintClock();
	}

	@Override
	public void setHeight(double height) {
		super.setHeight(height);
		paintClock();
	}

	public String getTimeString() {
		return getHour() + ":" + String.format("%02d", getMinute()) + ":" + String.format("#00", getSecond());
	}

	public String getDateString() {
		return new SimpleDateFormat("EEEE, d MMMM yyyy").format(cal.getTime());
	}
	
	 
	
	public double getMoveEverySecond() {
		return moveEverySecond;
	}

	public void setMoveEverySecond(double moveEverySecond) {
		this.moveEverySecond = moveEverySecond;
	}

	// Helper
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
}
