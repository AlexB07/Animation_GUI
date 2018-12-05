

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.*;
import sun.security.util.Length;
import javafx.scene.paint.Color;

/** Group which we can draw our figure on */
public class Canvas extends Group {
	/* Set up some initial positions for the parts of the stick figure */
	Point shoulder = new Point(100, 100);
	Point posterior = new Point(100, 150);
	Point leftHand = new Point(50, 100);
	Point rightHand = new Point(150, 100);
	Point head = new Point(100, 50);
	Point leftFoot = new Point(65, 200);
	Point rightFoot = new Point(135, 200);

	/* Create some lines to draw it with */
	Line leftArm = new Line();
	Line rightArm = new Line();
	Line neck = new Line();
	Line back = new Line();
	Line leftLeg = new Line();
	Line rightLeg = new Line();

	/* And a circle for the head */
	Node headCircle = new Node(25, Color.GREY, shoulder, head);

	/* And some a node to drag the left hand around with */
	Node leftHandNode = new Node(8, Color.RED, shoulder, leftHand);
	Node rightHandNode = new Node(8, Color.RED, shoulder, rightHand);
	Node leftFootNode = new Node(8, Color.RED, posterior, leftFoot);
	Node rightFootNode = new Node(8, Color.RED, posterior, rightFoot);
	Node neckNode = new Node(8, Color.RED);
	Node backNode = new Node(8, Color.RED);

	public Canvas(int width, int height) {
		intialiseNodes();
		getChildren().add(leftArm);
		getChildren().add(rightArm);
		getChildren().add(neck);
		getChildren().add(back);
		getChildren().add(leftLeg);
		getChildren().add(rightLeg);
		getChildren().add(headCircle);
		getChildren().add(leftHandNode);
		getChildren().add(rightHandNode);
		getChildren().add(leftFootNode);
		getChildren().add(rightFootNode);
		getChildren().add(neckNode);
		getChildren().add(backNode);

		pointsToShapes();

		leftHandNode.setOnMouseDragged(nodeMouseEvent);
		rightHandNode.setOnMouseDragged(nodeMouseEvent);
		leftFootNode.setOnMouseDragged(nodeMouseEvent);
		rightFootNode.setOnMouseDragged(nodeMouseEvent);
		neckNode.setOnMouseDragged(nodeMouseEvent);
		backNode.setOnMouseDragged(nodeMouseEvent);
		headCircle.setOnMouseDragged(nodeMouseEvent);
		
	}

	/** Drags object which is clicked on **/
	EventHandler<MouseEvent> nodeMouseEvent = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent e) {
			if (e.getButton() == MouseButton.PRIMARY) {
				Node temp = (Node) e.getSource();
				Point mouse = new Point(e.getX(), e.getY());
				double lengthToMouse = lengthOfLine(temp.getPointOne(), mouse);
				double lengthOfPart = lengthOfLine(temp.getPointOne(), temp.getPointTwo());
				if ((lengthToMouse >= lengthOfPart) || (lengthToMouse <= lengthOfPart))  {
					temp.setCenterX(temp.getPointOne().x + (lengthOfPart * (e.getX() - temp.getPointOne().x))/ lengthToMouse);
					temp.setCenterY(temp.getPointOne().y + (lengthOfPart * (e.getY() - temp.getPointOne().y))/ lengthToMouse);
					pointToNode();
				}
			}
		}
	};

	public boolean isDistance() {
		
		return false;
	}
	
	public double lengthOfLine(Point one, Point two) {
		double length;
		length = (Math.pow((two.x - one.x), 2) + (Math.pow((two.y - one.y), 2)));
		length = Math.sqrt(length);
		return length;
		
	}

	private void pointToNode() {
		leftHand.updatePointFromNode(leftHandNode);
		rightHand.updatePointFromNode(rightHandNode);
		leftFoot.updatePointFromNode(leftFootNode);
		rightFoot.updatePointFromNode(rightFootNode);
		posterior.updatePointFromNode(backNode);
		shoulder.updatePointFromNode(neckNode);
		head.updatePointFromNode(headCircle);

		pointsToShapes();
	}

	private void intialiseNodes() {
		leftHandNode.setCenterX(leftHand.x);
		leftHandNode.setCenterY(leftHand.y);
		rightHandNode.setCenterX(rightHand.x);
		rightHandNode.setCenterY(rightHand.y);
		leftFootNode.setCenterX(leftFoot.x);
		leftFootNode.setCenterY(leftFoot.y);
		rightFootNode.setCenterX(rightFoot.x);
		rightFootNode.setCenterY(rightFoot.y);
		backNode.setCenterX(posterior.x);
		backNode.setCenterY(posterior.y);
		neckNode.setCenterX(shoulder.x);
		neckNode.setCenterY(shoulder.y);
		headCircle.setCenterX(head.x);
		headCircle.setCenterY(head.y);
	}

	/** Helper method to set up a line using two Points */
	private void setupLine(Line line, Point start, Point end) {
		line.setStartX(start.x);
		line.setStartY(start.y);
		line.setEndX(end.x);
		line.setEndY(end.y);
	}

	/** Set up our shapes to the current positions of our points */
	private void pointsToShapes() {
		setupLine(leftArm, leftHand, shoulder);
		setupLine(rightArm, rightHand, shoulder);
		setupLine(neck, shoulder, head);
		setupLine(back, shoulder, posterior);
		setupLine(leftLeg, posterior, leftFoot);
		setupLine(rightLeg, posterior, rightFoot);

	}
}
