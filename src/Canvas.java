import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/** Group which we can draw our figure on */
public class Canvas extends Group {

	ArrayList<Stickman> stickmanList = new ArrayList<Stickman>();

	public Canvas(int width, int height) {
		Rectangle rect = new Rectangle(width - 100, height, Color.WHITE);
		rect.setStroke(Color.BLACK);
		getChildren().add(rect);
		stickmanList.add(new Stickman(this));

	}

	public Canvas(Canvas other) {
		int width = 800;
		int height = 600;
		Rectangle rect = new Rectangle(width - 100, height, Color.WHITE);
		rect.setStroke(Color.BLACK);
		getChildren().add(rect);

		for (Stickman s : other.stickmanList) {
			this.stickmanList.add(new Stickman(s, this));

		}

	}

	public void addStickman() {
		stickmanList.add(new Stickman(this));
	}

	// public void addCanvas() {
	// Main.undoList.add(this);
	// }
}
