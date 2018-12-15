import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Stickman extends Group {
	/* Group in which the stickman is added to find the bounds of each stick-man */
	private Group man = new Group();

	/* Set up some initial positions for the parts of the stick figure */
	private Point shoulder = new Point(150, 150);
	private Point posterior = new Point(150, 200);
	private Point leftHand = new Point(100, 150);
	private Point rightHand = new Point(200, 150);
	private Point head = new Point(150, 100);
	private Point leftFoot = new Point(115, 250);
	private Point rightFoot = new Point(185, 250);

	/* initial creation of line for body parts */
	private Line leftArm = new Line();
	private Line rightArm = new Line();
	private Line neck = new Line();
	private Line back = new Line();
	private Line leftLeg = new Line();
	private Line rightLeg = new Line();

	/* Line array list */
	public ArrayList<Line> lineList = new ArrayList<Line>();

	/* Point array list */
	public ArrayList<Point> pointList = new ArrayList<Point>();
	
	/* Node array list */
	public ArrayList<Node> nodeList = new ArrayList<Node>();
	
	/* And some a node to drag the left hand around with */
	private Node leftHandNode = new Node(8, Color.RED, shoulder, leftHand);
	private Node rightHandNode = new Node(8, Color.RED, shoulder, rightHand);
	private Node leftFootNode = new Node(8, Color.RED, posterior, leftFoot);
	private Node rightFootNode = new Node(8, Color.RED, posterior, rightFoot);
	private Node neckNode = new Node(8, Color.ORANGE, posterior, shoulder);
	private Node backNode = new Node(8, Color.RED, shoulder, posterior);
	private Node headNode = new Node(25, Color.GREY, shoulder, head);

	public Stickman(Canvas group) {
		initialiseStickman(group);
	}
	/*Creates a new stick-man from another stick-man and adds it to the canvas*/
	public Stickman(Stickman other, Canvas group) {
		shoulder.updatePointFromOther(other.shoulder);
		head.updatePointFromOther(other.head);
		posterior.updatePointFromOther(other.posterior);
		leftHand.updatePointFromOther(other.leftHand);
		rightHand.updatePointFromOther(other.rightHand);
		leftFoot.updatePointFromOther(other.leftFoot);
		rightFoot.updatePointFromOther(other.rightFoot);
		initialiseStickman(group);

	}
	/*Creates a orignal sitck-man*/
	public void initialiseStickman(Canvas group) {
		/* Initiate line list, node list and point list */
		lineList.add(leftArm);
		lineList.add(rightArm);
		lineList.add(neck);
		lineList.add(back);
		lineList.add(leftLeg);
		lineList.add(rightLeg);
		nodeList.add(leftHandNode);
		nodeList.add(rightHandNode);
		nodeList.add(leftFootNode);
		nodeList.add(rightFootNode);
		nodeList.add(neckNode);
		nodeList.add(backNode);
		nodeList.add(headNode);
		pointList.add(head);
		pointList.add(leftFoot);
		pointList.add(leftHand);
		pointList.add(posterior);
		pointList.add(shoulder);
		pointList.add(rightFoot);
		pointList.add(rightHand);
		intialiseNodes();

		/* Add line list to stick-man group */
		for (Line line : lineList) {
			man.getChildren().add(line);
		}
		/* Add node list to stick-man group */
		for (Node node : nodeList) {
			man.getChildren().add(node);
			node.setOnMouseReleased(nodeReleaseEvent);
		}

		pointsToShapes();
		//sets all action handlers for the nodes
		leftHandNode.setOnMouseDragged(nodeMouseEvent);
		rightHandNode.setOnMouseDragged(nodeMouseEvent);
		leftFootNode.setOnMouseDragged(nodeMouseEvent);
		rightFootNode.setOnMouseDragged(nodeMouseEvent);
		neckNode.setOnMouseDragged(nodeMouseDragBodyEvent);
		headNode.setOnMouseDragged(nodeMouseEvent);
		backNode.setOnMouseDragged(nodeMouseRotateBodyEvent);

		/* Add it to canvas group which is passed in */
		group.getChildren().add(man);

	}

	/* Add nodes to the correct point position on stick-man */
	private void intialiseNodes() {
		for (Node n : nodeList) {
			n.updatePos(n.getPointTwo());
		}
	}

	/** Drags object which is clicked on and keeps line to same length **/
	EventHandler<MouseEvent> nodeMouseEvent = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent e) {
			if (e.getButton() == MouseButton.PRIMARY) {
				//gets the node the user clicked on
				Node node = (Node) e.getSource();
				double lengthToMouse = lengthOfLine(node.getPointOne(), new Point(e.getX(), e.getY()));
				//works out where the hand point should be to keep the body part length the same
				node.setCenterX(node.getPointOne().getX() + (node.getLength() * (e.getX() - node.getPointOne().getX())) / lengthToMouse);
				node.setCenterY(node.getPointOne().getY() + (node.getLength() * (e.getY() - node.getPointOne().getY())) / lengthToMouse);
				pointToNode();

			}
		}
	};
	/** Moves whole stick-man around by dragging mouse on shoulder node **/
	EventHandler<MouseEvent> nodeMouseDragBodyEvent = new EventHandler<MouseEvent>() {

		public void handle(MouseEvent event) {
			Bounds boundsInGroup = man.localToScene(man.getBoundsInLocal());
			if (event.getButton() == MouseButton.PRIMARY) {
				Point mouse = new Point(event.getSceneX() - shoulder.getX(), event.getSceneY() - shoulder.getY());
				//Keeps stick-man in the canvas (border)
				if ((boundsInGroup.getMinX() + mouse.getX()) > 0 && (boundsInGroup.getMaxX() + mouse.getX()) < 750
						&& (boundsInGroup.getMinY() + mouse.getY()) > 0 && (boundsInGroup.getMaxY() + mouse.getY() < 600)) {
					move(mouse);
				}

			}

		}

	};

	
	/** Release event for all nodes, adds new frame to framelist in main **/
	EventHandler<MouseEvent> nodeReleaseEvent = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {
			Main.frameList.get(Main.currentFrame).addToList(Main.canvas);
			pointToNode();

		}
	};

	/**
	 * Moves posterior around when posterior node dragged keeps neck length the same
	 **/
	EventHandler<MouseEvent> nodeMouseRotateBodyEvent = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent event) {
			if (event.getButton() == MouseButton.PRIMARY) {
				Point mouse = new Point(event.getX(), event.getY());
				double lengthOfMouse = lengthOfLine(head, mouse);
				//double lengthOfNeck = lengthOfLine(head, shoulder);
				Point difference = null;
				double x = (headNode.getPointTwo().getX()
						+ ((headNode.getLength()) * ((event.getX()) - headNode.getPointTwo().getX())) / (lengthOfMouse));
				double y = (headNode.getPointTwo().getY()
						+ ((headNode.getLength()) * ((event.getY()) - headNode.getPointTwo().getY())) / (lengthOfMouse));
				difference = new Point(x - shoulder.getX(), y - shoulder.getY());
				setPositionOfNodes(difference);
				pointToNode();

			}
		}

	};
	/* sets position of nodes for rotation of body */
	public void setPositionOfNodes(Point diff) {
		// head should not move in this added back later
		nodeList.remove(headNode);
		for (Node node : nodeList) {
			node.setCenterX(node.getCenterX() + diff.getX());
			node.setCenterY(node.getCenterY() + diff.getY());
		}
		nodeList.add(headNode);
	}

	/* Updates all points in the point list */
	public void move(Point diff) {
		for (Point p : pointList) {
			p.setX(diff.getX());
			p.setY(diff.getY());
		}
		nodeToPoint();
	}

	/* Calculates the length of a line given two points*/
	public double lengthOfLine(Point one, Point two) {
		double length;
		length = (Math.pow((two.getX() - one.getX()), 2) + (Math.pow((two.getY() - one.getY()), 2)));
		length = Math.sqrt(length);
		return length;

	}

	/* Update point position from node position */
	private void pointToNode() {
		leftHand.updatePointFromNode(leftHandNode);
		rightHand.updatePointFromNode(rightHandNode);
		leftFoot.updatePointFromNode(leftFootNode);
		rightFoot.updatePointFromNode(rightFootNode);
		posterior.updatePointFromNode(backNode);
		shoulder.updatePointFromNode(neckNode);
		head.updatePointFromNode(headNode);
		pointsToShapes();
	}

	/* Update node position from point */
	private void nodeToPoint() {
		leftHandNode.updatePos(leftHand);
		rightHandNode.updatePos(rightHand);
		leftFootNode.updatePos(leftFoot);
		rightFootNode.updatePos(rightFoot);
		backNode.updatePos(posterior);
		neckNode.updatePos(shoulder);
		headNode.updatePos(head);
		pointToNode();

	}

	/** Helper method to set up a line using two Points */
	private void setupLine(Line line, Point start, Point end) {
		line.setStartX(start.getX());
		line.setStartY(start.getY());
		line.setEndX(end.getX());
		line.setEndY(end.getY());
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