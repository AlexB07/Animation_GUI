
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Main extends Application {
	static Canvas canvas = new Canvas();
	static ArrayList<Frame> frameList = new ArrayList<Frame>();
	static int currentFrame = 0;
	Text txtFrame = new Text("No Frames");
	long frameRate = 0;

	public static void main(String[] args) {
		launch(args);
	}


	public void start(Stage stage) {
		//Adds first frame to list, so that the first one can be undone
		frameList.add(new Frame(canvas));
		VBox vb = new VBox();
		//gets CSS style sheet
		vb.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		vb.getChildren().add(canvas);
		stage.setTitle("Animation");
		HBox toolbar = new HBox();
		
		
		
		/*Creating undo button*/
		Button btnUndo = new Button("Undo");
		btnUndo.getStyleClass().add("undo");
		toolbar.getChildren().add(btnUndo);
		btnUndo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				undo(frameList.get(currentFrame).getUndoList(), frameList.get(currentFrame).getSizeOfList());
				canvas = new Canvas(frameList.get(currentFrame).getFrame());
				resetVBox(vb, toolbar);
			}
		});
		/* Creating add stickman button */
		Button btnAddStickman = new Button("Add Stickman");
		btnAddStickman.getStyleClass().add("addStickman");
		toolbar.getChildren().add(btnAddStickman);
		btnAddStickman.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				canvas.addStickman();
				frameList.get(currentFrame).addToList(canvas);
			}
		});

		/*Creating add frame button*/
		Button btnAddFrame = new Button("Add Frame");
		btnAddFrame.getStyleClass().add("addFrame");
		toolbar.getChildren().add(btnAddFrame);
		btnAddFrame.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				frameList.add(new Frame(canvas));
				currentFrame++;
				setFrameText();

			}
		});

		/*Creating back frame button*/
		Button btnBackFrame = new Button("Back Frame");
		btnBackFrame.getStyleClass().add("changeFrame");
		toolbar.getChildren().add(btnBackFrame);
		btnBackFrame.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (currentFrame >= 1) {
					currentFrame--;
					canvas = new Canvas(frameList.get(currentFrame).getFrame());
					resetVBox(vb, toolbar);
					setFrameText();
				}

			}
		});
		
		/*adding style and adding it to the toolbar group for text select frame
		 * Display in middle of change frame buttons
		 * */
		txtFrame.getStyleClass().add("txtFrame");
		toolbar.getChildren().add(txtFrame);
		
		/* Creating Next button */
		Button btnNextFrame = new Button("Next Frame");
		btnNextFrame.getStyleClass().add("changeFrame");
		toolbar.getChildren().add(btnNextFrame);
		btnNextFrame.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (currentFrame < frameList.size() - 1) {
					currentFrame++;
					canvas = new Canvas(frameList.get(currentFrame).getFrame());
					resetVBox(vb, toolbar);
					setFrameText();
				}

			}
		});
		/* Creating textfield for frame number select */
		TextField txtFInput = new TextField();
		txtFInput.setMaxWidth(76);
		txtFInput.setPromptText("Frame Num");
		txtFInput.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					txtFInput.setText(newValue.replaceAll("[^\\d]", ""));
				}

			}
		});
		toolbar.getChildren().add(txtFInput);
		/* Creation of the submit button for frame */
		Button btnSubmit = new Button("Sumbit");
		btnSubmit.getStyleClass().add("submit");
		toolbar.getChildren().add(btnSubmit);
		btnSubmit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// Validation for the input frame number box, if empty, and to make sure its in
				// the correct range.
				if (txtFInput.getText().isEmpty() || (Integer.parseInt(txtFInput.getText()) > frameList.size()
						|| Integer.parseInt(txtFInput.getText()) < 0)) {
					txtFInput.setText("");
					Alert alertSubmit = new Alert(AlertType.ERROR);
					alertSubmit.setTitle("Error Submit");
					alertSubmit.setHeaderText("You submitted a invalid input");
					if (frameList.size() != 1) {
						alertSubmit.setContentText("Please enter a number between 0 and " + (frameList.size() - 1));
					} else {
						alertSubmit.setContentText(
								"Please add a new frame. Then you can select a new frame here \n or using the buttons 'Back Frame ' and 'Next Frame'.");

					}
					alertSubmit.showAndWait();
				} else {
					// If passes validation then show frame number entered
					currentFrame = Integer.parseInt(txtFInput.getText());
					setFrameText();
					canvas = new Canvas(frameList.get(currentFrame).getFrame());
					resetVBox(vb, toolbar);
				}

			}
		});
		/*Creation for animation play buttons*/
		AnimationTimer t = new AnimationTimer() {
			private int frame = -1;
			private long lastUpdate = 0;

			@Override
			public void handle(long now) {
				//creates delay between frames
				if (now - lastUpdate >= frameRate && frameList.size() > 1) {
					frame++;
					canvas = new Canvas(frameList.get(frame).getFrame());
					resetVBox(vb, toolbar);
					//Stops animation when its reaches the end of the frames created
					if (frame >= frameList.size() - 1) {
						frame = -1;
						this.stop();
					}
					lastUpdate = now;
				}

			}

		};
		/*Creating button play 24 frames per second*/
		Button btnPlay = new Button("Play 24");
		//hover over button, display what this button does
		btnPlay.setTooltip(new Tooltip("Plays animation 24 Frames Per Second (24 FPS)"));
		btnPlay.getStyleClass().add("play");
		toolbar.getChildren().add(btnPlay);
		btnPlay.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// 41.66667 milliseconds equals 24 frames per second
				frameRate = 41_666_667;
				t.start();

			}
		});
		/*Creating button play 12 frames per second */
		Button btnPlay12 = new Button("Play 12");
		btnPlay12.getStyleClass().add("play");
		toolbar.getChildren().add(btnPlay12);
		//hover over button, display what this button does
		btnPlay12.setTooltip(new Tooltip("Plays animation 12 Frames Per Second (12 FPS)"));
		btnPlay12.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// 83.333333 milliseconds equals 12 frames per second
				frameRate = 83_333_333;
				t.start();

			}
		});

		/*adds toolbar of buttons and inputs to the display, and creates a new scene*/
		vb.getChildren().add(toolbar);
		stage.setScene(new Scene(vb));
		stage.show();
	}
	//sets frame selected text
	public void setFrameText() {
		txtFrame.setText("Frame " + (currentFrame) + " of " + (frameList.size() - 1));
	}
	/* Resets displayed canvas */
	public void resetVBox(VBox vb, HBox hb) {
		vb.getChildren().clear();
		vb.getChildren().addAll(canvas, hb);
	}
	
	/* Undo procedure */
	public void undo(ArrayList<Canvas> canvasList, int index) {
		if (canvasList.size() >= 1) {
			if (canvasList.size() != 1) {
				canvasList.remove(index - 1);
			} else {
				canvasList.clear();
				canvasList.add(new Canvas(canvas));
			}
		}
	}

}
