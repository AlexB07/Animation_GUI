import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class Main extends Application {
	static Canvas canvas = new Canvas(800, 600);
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
		toolbar.getChildren().add(btnUndo);
		btnUndo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				undo(frameList.get(currentFrame).getUndoList(), frameList.get(currentFrame).getSizeOfList());
				canvas = new Canvas(frameList.get(currentFrame).getFrame());
				resetVBox(vb, toolbar);
			}
		});

		Button btnAddStickman = new Button("Add Stickman");
		toolbar.getChildren().add(btnAddStickman);
		btnAddStickman.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				canvas.addStickman();
				frameList.get(currentFrame).addToList(canvas);
			}
		});

		Button btnAddFrame = new Button("Add Frame");
		toolbar.getChildren().add(btnAddFrame);
		btnAddFrame.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				System.out.println("Adding new frame");
				frameList.add(new Frame(canvas));
				currentFrame++;
				setFrameText(txtFrame, currentFrame);

			}
		});

		Button btnBackFrame = new Button("Back Frame");
		toolbar.getChildren().add(btnBackFrame);
		btnBackFrame.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (currentFrame > 1) {
					currentFrame--;
					System.out.println("currentFrame " + currentFrame + " size " + frameList.size());
					canvas = new Canvas(frameList.get(currentFrame).getFrame());
					resetVBox(vb, toolbar);
					setFrameText(txtFrame, currentFrame);
				}

			}
		});
		toolbar.getChildren().add(txtFrame);
		Button btnNextFrame = new Button("Next Frame");
		toolbar.getChildren().add(btnNextFrame);
		btnNextFrame.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (currentFrame < frameList.size() - 1) {
					currentFrame++;
					System.out.println("frame " + currentFrame + " size " + frameList.size());
					canvas = new Canvas(frameList.get(currentFrame).getFrame());
					resetVBox(vb, toolbar);
					setFrameText(txtFrame, currentFrame);
				}

			}
		});

		AnimationTimer t = new AnimationTimer() {
			private int frame = 0;
			private long lastUpdate = 0;

			@Override
			public void handle(long now) {
				if (now - lastUpdate >= frameRate) {
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
		
		
		
		
		Button btnPlay = new Button("Play 24 Frames");
		toolbar.getChildren().add(btnPlay);
		btnPlay.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//41.66667 milliseconds equals 24 frames per second
				frameRate = 41_666_667;
				t.start();
			}
		});
		
		Button btnPlay12 = new Button("Play 12 Frames");
		toolbar.getChildren().add(btnPlay12);
		btnPlay12.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//83.333333 milliseconds equals 12 frames per second
				frameRate = 83_333_333;
				t.start();
				
			}
		});

		vb.getChildren().add(toolbar);
		stage.setScene(new Scene(vb));
		stage.show();
	}

	public void setFrameText(Text txt, int num) {
		txt.setText("Frame " + (num) + " of " + (frameList.size() - 1));
	}

	public void resetVBox(VBox vb, HBox hb) {
		vb.getChildren().clear();
		vb.getChildren().addAll(canvas, hb);
	}

	public void playFrames(long delay, VBox vb, HBox hb) {

	}

}
