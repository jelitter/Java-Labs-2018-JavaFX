package application;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class ClockPane extends Pane {
	
	private static final int CLOCK_MARGIN = 16;
	
	private Circle c1, c2;
	private Calendar cal;
	private int hour;
	private int minute;
	private int second;
	
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
	public int getSecond() {
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

		paintClock(); // Repaint the clock
	}

	/** Paint the clock */
	private void paintClock() {
		// Initialize clock parameters
		double clockRadius = Math.min(getWidth(), getHeight()) * 0.8 * 0.5;
		
		double centerX = getWidth() / 2;
		double centerY = getHeight() / 2;

		Group frame = new Group();
		Group needles = new Group();
		Group numbers = new Group();

		setClockFrame(clockRadius, centerX, centerY, frame);
		setClockNumbers(clockRadius, centerX, centerY, numbers);
		setClockNeedles(clockRadius, centerX, centerY, needles);

		getChildren().clear();
		getChildren().addAll(frame, numbers, needles);
	}

	private void setClockFrame(double clockRadius, double centerX, double centerY, Group frame) {
		// Draw circle
		c1 = new Circle(centerX, centerY, clockRadius);
		c2 = new Circle(centerX, centerY, clockRadius);;
		c1.setFill(Color.WHITE);
		c1.setStrokeWidth(10);
		c1.setStroke(Color.DARKGOLDENROD);
		
		c2.setFill(Color.TRANSPARENT);
		c2.setStrokeWidth(6);
		c2.setStroke(Color.GOLD);
		
		frame.getChildren().addAll(c1, c2);
	}

	private void setClockNeedles(double clockRadius, double centerX, double centerY, Group needles) {
		// Draw second hand
		double sLength = clockRadius * 0.8;
		double secondX = centerX + sLength * Math.sin(second * (2 * Math.PI / 60));
		double secondY = centerY - sLength * Math.cos(second * (2 * Math.PI / 60));
		Line sLine = new Line(centerX, centerY, secondX, secondY);
		sLine.setStrokeWidth(2);
		sLine.setStroke(Color.RED);

		// Draw minute hand
		double mLength = clockRadius * 0.65;
		double xMinute = centerX + mLength * Math.sin(minute * (2 * Math.PI / 60));
		double minuteY = centerY - mLength * Math.cos(minute * (2 * Math.PI / 60));
		Line mLine = new Line(centerX, centerY, xMinute, minuteY);
		mLine.setStrokeWidth(4);
		mLine.setStroke(Color.BLUE);

		// Draw hour hand
		double hLength = clockRadius * 0.5;
		double hourX = centerX + hLength * Math.sin((hour % 12 + minute / 60.0) * (2 * Math.PI / 12));
		double hourY = centerY - hLength * Math.cos((hour % 12 + minute / 60.0) * (2 * Math.PI / 12));
		Line hLine = new Line(centerX, centerY, hourX, hourY);
		hLine.setStrokeWidth(6);
		hLine.setStroke(Color.GREEN);

		needles.getChildren().addAll(sLine, mLine, hLine);

		// Setting up needles
		for (Node n : needles.getChildren()) {
			((Shape) n).setStrokeLineCap(StrokeLineCap.ROUND);
			((Shape) n).setSmooth(true);
			((Shape) n).setBlendMode(BlendMode.DARKEN);
		}
	}

	private void setClockNumbers(double clockRadius, double centerX, double centerY, Group numbers) {
		Text t3 = new Text("3");
		Text t6 = new Text("6");
		Text t9 = new Text("9");
		Text t12 = new Text("12");
		
		numbers.getChildren().addAll(t12,t3,t6,t9);
		
		for (Node t : numbers.getChildren()) {
			((Text) t).setFont(Font.font("Georgia", FontWeight.BOLD, 18));
			((Text) t).setFontSmoothingType(FontSmoothingType.GRAY);
			((Text) t).setTextAlignment(TextAlignment.CENTER);
		}

		t3.setX(centerX + clockRadius - t3.getLayoutBounds().getWidth() - CLOCK_MARGIN);
		t3.setY(centerY + t3.getLayoutBounds().getHeight()/4);
		t6.setX(centerX - t6.getLayoutBounds().getWidth()/2);
		t6.setY(centerY + clockRadius - CLOCK_MARGIN);
		t9.setX(centerX - clockRadius + CLOCK_MARGIN );
		t9.setY(centerY + t9.getLayoutBounds().getHeight()/4);
		t12.setX(centerX - t12.getLayoutBounds().getWidth()/2);
		t12.setY(centerY - clockRadius + t12.getLayoutBounds().getHeight()/2 + CLOCK_MARGIN);
	}

	public void updateClockFrame() {
//		Color c = (Color) c1.getStroke();
		
		c1.setRotate(+1);
		
//		if (second < 30)
//			c1.setStroke(c.brighter());
//		else
//			c1.setStroke(c.darker());
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
		return getHour() + ":" + String.format("%02d", getMinute()) + ":" + String.format("%02d", getSecond());
	}

	public String getDateString() {
		return new SimpleDateFormat("EEEE, d MMMM yyyy").format(cal.getTime());
	}
}
