import java.util.ArrayList;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Stickman extends Group {
	/* Group in which the stickman is added to */
	private Group man = new Group();
	

	private double x;
	private double y;
	
	
	
	/* Set up some initial positions for the parts of the stick figure */
	private Point shoulder = new Point(100, 100);
	private Point posterior = new Point(100, 150);
	private Point leftHand = new Point(50, 100);
	private Point rightHand = new Point(150, 100);
	private Point head = new Point(100, 50);
	private Point leftFoot = new Point(65, 200);
	private Point rightFoot = new Point(135, 200);

	/* Array node list */
	public ArrayList<Node> nodeList = new ArrayList<Node>();

	/* initial creation of line for body parts */
	private Line leftArm = new Line();
	private Line rightArm = new Line();
	private Line neck = new Line();
	private Line back = new Line();
	private Line leftLeg = new Line();
	private Line rightLeg = new Line();

	/* Line array list */
	public ArrayList<Line> lineList = new ArrayList<Line>();
	
	public ArrayList<Point> pointList = new ArrayList<Point>();

	/* And some a node to drag the left hand around with */
	private Node leftHandNode = new Node(8, Color.RED, shoulder, leftHand);
	private Node rightHandNode = new Node(8, Color.RED, shoulder, rightHand);
	private Node leftFootNode = new Node(8, Color.RED, posterior, leftFoot);
	private Node rightFootNode = new Node(8, Color.RED, posterior, rightFoot);
	private Node neckNode = new Node(8, Color.ORANGE, posterior, shoulder);
	private Node backNode = new Node(8, Color.RED, shoulder, posterior);
	private Node headNode = new Node(25, Color.GREY, shoulder, head);

	private Canvas group;
	
	public double layoutX;
	public double layoutY;
	
	public Stickman(Canvas group) {
		initialiseStickman(group);
	}
	
	public Stickman(Stickman other, Canvas group) {
		// do all stuff from other constructor
		
		shoulder.updatePointFromOther(other.shoulder);
		head.updatePointFromOther(other.head);
		posterior.updatePointFromOther(other.posterior);
		leftHand.updatePointFromOther(other.leftHand);
		rightHand.updatePointFromOther(other.rightHand);
		leftFoot.updatePointFromOther(other.leftFoot);
		rightFoot.updatePointFromOther(other.rightFoot);

		initialiseStickman(group);
		//this.updateTranslate(other);
		
		
		//...
	}
	
	public void initialiseStickman(Canvas group) {
		this.group = group;
		/* Initiate line list and node list */
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

		/*Add line list to stick-man group*/
		for (Line line : lineList) {
			man.getChildren().add(line);
		}
		/*Add node list to stick-man group*/
		for (Node node : nodeList) {
			man.getChildren().add(node);
			node.setOnMouseReleased(nodeReleaseEvent);	
		}
		
		pointsToShapes();
		leftHandNode.setOnMouseDragged(nodeMouseEvent);
		rightHandNode.setOnMouseDragged(nodeMouseEvent);
		leftFootNode.setOnMouseDragged(nodeMouseEvent);
		rightFootNode.setOnMouseDragged(nodeMouseEvent);
		neckNode.setOnMouseDragged(nodeMouseDragBodyEvent);
		headNode.setOnMouseDragged(nodeMouseEvent);
		backNode.setOnMouseDragged(nodeMouseRotateBodyEvent);

		/*Add it to canvas group which is passed in*/
		group.getChildren().add(man);
		
		
	}
	
	
	
	/*Add nodes to the correct point position on stick-man*/
	private void intialiseNodes() {
		for (Node n : nodeList) {
			n.updatePos(n.getPointTwo());
		}
	}

	/** Drags object which is clicked on and keeps line to same length **/
	EventHandler<MouseEvent> nodeMouseEvent = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent e) {
			if (e.getButton() == MouseButton.PRIMARY) {
				Node temp = (Node) e.getSource();
				Point mouse = new Point(e.getX(), e.getY());
				double lengthToMouse = lengthOfLine(temp.getPointOne(), mouse);
				double lengthOfPart = lengthOfLine(temp.getPointOne(), temp.getPointTwo());
				if ((lengthToMouse >= lengthOfPart) || (lengthToMouse <= lengthOfPart)) {
					temp.setCenterX(temp.getPointOne().x + (lengthOfPart * (e.getX() - temp.getPointOne().x)) / lengthToMouse);
					temp.setCenterY(temp.getPointOne().y + (lengthOfPart * (e.getY() - temp.getPointOne().y)) / lengthToMouse);
					pointToNode();

				}
			}
		}
	};
	/** Moves whole stick-man around by dragging mouse on shoulder node **/
	EventHandler<MouseEvent> nodeMouseDragBodyEvent = new EventHandler<MouseEvent>() {

		public void handle(MouseEvent event) {
			Bounds boundsInGroup = man.localToScene(man.getBoundsInLocal());
			if (event.getButton() == MouseButton.PRIMARY) {
				Point mouse = new Point(event.getSceneX() - shoulder.x, event.getSceneY() - shoulder.y);
				if (boundsInGroup.getMinX() > 0.5 && boundsInGroup.getMaxX() < 700 && boundsInGroup.getMinY() > 0 && boundsInGroup.getMaxY() < 600) {	
					
				move(mouse);	
				}
				//TODO fix error with border, wont move after it touches border
				
				
			}
			
		

		}

	};
	
	
	public void updateTranslate(Stickman other) {
		//other.setTranslateX(man.getTranslateX());
		//other.setTranslateY(man.getTranslateY());
		System.out.println("x " + man.getTranslateX());
		other.man.setTranslateX(x);
		other.man.setTranslateY(y);
	}
	
	
	
	
	EventHandler<MouseEvent> nodeReleaseEvent = new EventHandler<MouseEvent>() {

		@Override
		public void handle(MouseEvent event) {
			System.out.println("creating undo record and size is "+ Main.frameList.size());  
			System.out.println(Main.currentFrame);
			//Main.undoList.add(new Canvas(Main.canvas));
			Main.frameList.get(Main.currentFrame).addToList(Main.canvas);
			
			
			pointToNode();
			
		}
	};
	
	
	/** Moves posterior around when posterior node dragged keeps neck length the same **/
	EventHandler<MouseEvent> nodeMouseRotateBodyEvent = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent event) {
			if (event.getButton() == MouseButton.PRIMARY) {
				Point mouse = new Point(event.getX(), event.getY());
				double lengthOfMouse = lengthOfLine(head, mouse);
				double lengthOfNeck = lengthOfLine(head, shoulder);
				Point difference = null;
				double x = (headNode.getPointTwo().x + ((lengthOfNeck) * ((event.getX()) - headNode.getPointTwo().x)) / (lengthOfMouse));
				double y = (headNode.getPointTwo().y + ((lengthOfNeck) * ((event.getY()) - headNode.getPointTwo().y)) / (lengthOfMouse));
				difference = new Point(x - shoulder.x, y - shoulder.y);
				setPositionOfNodes(difference);
				pointToNode();

			}
		}

	};
	
	
	

	public void setPositionOfNodes(Point diff) {
		//head should not move in this added back later
		nodeList.remove(headNode);
		for (Node node : nodeList) {
			node.setCenterX(node.getCenterX() + diff.x);
			node.setCenterY(node.getCenterY() + diff.y);
		}
		nodeList.add(headNode);
	}
	
	
	public void move(Point diff) {
		//head should not move in this added back later
		//nodeList.remove(headNode);
		for (Point p : pointList) {
			p.x += diff.x;
			p.y += diff.y;
		}
		nodeToPoint();
		pointToNode();
		//nodeList.add(headNode);
	}

	public double lengthOfLine(Point one, Point two) {
		double length;
		length = (Math.pow((two.x - one.x), 2) + (Math.pow((two.y - one.y), 2)));
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
	
	private void nodeToPoint() {
		leftHandNode.updatePos(leftHand);
		rightHandNode.updatePos(rightHand);
		leftFootNode.updatePos(leftFoot);
		rightFootNode.updatePos(rightFoot);
		backNode.updatePos(posterior);
		neckNode.updatePos(shoulder);
		headNode.updatePos(head);
		
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