
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
	static Canvas canvas = new Canvas(850, 600);
	static ArrayList<Frame> frameList = new ArrayList<Frame>();
	static int currentFrame = 0;
	Text txtFrame = new Text("No Frames");
	long frameRate = 0;

	public static void main(String[] args) {
		launch(args);
	}

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

	public void start(Stage stage) {
		// undoList.add(new Canvas(canvas));
		frameList.add(new Frame(canvas));
		VBox vb = new VBox();
		vb.getChildren().add(canvas);
		stage.setTitle("Animation");
		HBox toolbar = new HBox();
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
		txtFrame.getStyleClass().add("txtFrame");
		Button btnAddStickman = new Button("Add Stickman");
		btnAddStickman.getStyleClass().add("addStickman");
		toolbar.getChildren().add(btnAddStickman);
		btnAddStickman.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				canvas.addStickman();
				frameList.get(currentFrame).addToList(canvas);
			}
		});

		Button btnAddFrame = new Button("Add Frame");
		btnAddFrame.getStyleClass().add("addFrame");
		toolbar.getChildren().add(btnAddFrame);
		btnAddFrame.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("Adding new frame");
				frameList.add(new Frame(canvas));
				currentFrame++;
				setFrameText();

			}
		});

		Button btnBackFrame = new Button("Back Frame");
		btnBackFrame.getStyleClass().add("changeFrame");
		toolbar.getChildren().add(btnBackFrame);
		btnBackFrame.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (currentFrame >= 1) {
					currentFrame--;
					System.out.println("currentFrame " + currentFrame + " size " + frameList.size());
					canvas = new Canvas(frameList.get(currentFrame).getFrame());
					resetVBox(vb, toolbar);
					setFrameText();
				}

			}
		});
		toolbar.getChildren().add(txtFrame);
		Button btnNextFrame = new Button("Next Frame");
		btnNextFrame.getStyleClass().add("changeFrame");
		toolbar.getChildren().add(btnNextFrame);
		btnNextFrame.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (currentFrame < frameList.size() - 1) {
					currentFrame++;
					System.out.println("frame " + currentFrame + " size " + frameList.size());
					canvas = new Canvas(frameList.get(currentFrame).getFrame());
					resetVBox(vb, toolbar);
					setFrameText();
				}

			}
		});

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
		
		/** Creation of the submit button for frame **/
		Button btnSubmit = new Button("Sumbit");
		btnSubmit.getStyleClass().add("submit");
		toolbar.getChildren().add(btnSubmit);
		btnSubmit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println(txtFInput.getText());
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

		AnimationTimer t = new AnimationTimer() {
			private int frame = 0;
			private long lastUpdate = 0;

			@Override
			public void handle(long now) {
				if (now - lastUpdate >= frameRate && frameList.size() > 1) {
					frame++;
					canvas = new Canvas(frameList.get(frame).getFrame());
					resetVBox(vb, toolbar);
					if (frame >= frameList.size() - 1) {
						frame = 0;
						this.stop();
					}
					lastUpdate = now;
				}

			}

		};

		Button btnPlay = new Button("Play 24");
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

		Button btnPlay12 = new Button("Play 12");
		btnPlay12.getStyleClass().add("play");
		toolbar.getChildren().add(btnPlay12);
		btnPlay12.setTooltip(new Tooltip("Plays animation 12 Frames Per Second (12 FPS)"));
		btnPlay12.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// 83.333333 milliseconds equals 12 frames per second
				frameRate = 83_333_333;
				t.start();

			}
		});

		vb.getChildren().add(toolbar);
		vb.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		stage.setScene(new Scene(vb));
		stage.show();
	}

	public void setFrameText() {
		txtFrame.setText("Frame " + (currentFrame) + " of " + (frameList.size() - 1));
	}

	public void resetVBox(VBox vb, HBox hb) {
		vb.getChildren().clear();
		vb.getChildren().addAll(canvas, hb);
	}

}
