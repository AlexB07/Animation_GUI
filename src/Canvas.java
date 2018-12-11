import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/** Group which we can draw our figure on */
public class Canvas extends Group {

	public Canvas(int width, int height) {
		
		Rectangle rect = new Rectangle(width - 100, height, Color.WHITE);
		rect.setStroke(Color.BLACK);
		getChildren().add(rect);
		
		Stickman a = new Stickman(this);
	}

}
