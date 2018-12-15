import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/** Group which we can draw our figure on */
public class Canvas extends Group {

	ArrayList<Stickman> stickmanList = new ArrayList<Stickman>();
	final private int width = 850;
	final private int height = 600;

	/* Creates a new canvas */
	public Canvas() {
		getChildren().add(getRectangle());
		stickmanList.add(new Stickman(this));

	}

	/* Makes a copy of another canvas not a reference */
	public Canvas(Canvas other) {
		getChildren().add(getRectangle());
		// Copies each stick-man from other canvas over to new canvas
		for (Stickman s : other.stickmanList) {
			this.stickmanList.add(new Stickman(s, this));

		}

	}
	/* Creates a rectangle for the scene */
	public Rectangle getRectangle() {
		Rectangle rect = new Rectangle(width - 100, height, Color.WHITE);
		rect.setStroke(Color.BLACK);
		return rect;
	}

	// add new stickman to the canvas
	public void addStickman() {
		stickmanList.add(new Stickman(this));
	}
}
