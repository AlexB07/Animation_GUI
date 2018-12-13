import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Main extends Application
{
    static Canvas canvas = new Canvas(800, 600);
    static ArrayList<Canvas> undoList = new ArrayList<>();
    static ArrayList<Frame> frameList = new ArrayList<Frame>();
    
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
    	undoList.add(new Canvas(canvas));
    	VBox vb = new VBox();
    	vb.getChildren().add(canvas);
        stage.setTitle("Animation");
        HBox toolbar = new HBox();
    	Button btnUndo = new Button("Undo");
    	toolbar.getChildren().add(btnUndo);
    	btnUndo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (undoList.size() >= 1) {
					if(undoList.size() != 1) {
						undoList.remove(undoList.size()-1);
					}
					else {
						undoList.clear();
						undoList.add(new Canvas(canvas));
					}
					Canvas tempCanvas = undoList.get(undoList.size()-1);
					canvas = new Canvas(tempCanvas);
					vb.getChildren().clear();
					vb.getChildren().addAll(canvas, toolbar);
				}
			}
		});
    	
    	Button btnAddStickman = new Button("Add Stickman");
    	toolbar.getChildren().add(btnAddStickman);
    	btnAddStickman.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				canvas.addStickman();
				undoList.add(new Canvas(canvas));
			}
		});
    	
    	Button btnAddFrame = new Button("Add Frame");
    	toolbar.getChildren().add(btnAddFrame);
    	btnAddFrame.setOnAction(new EventHandler<ActionEvent>() {

			
			public void handle(ActionEvent event) {
				System.out.println("Adding new frame");
				frameList.add(new Frame(undoList));
			}
		});
        
        
        
        
        vb.getChildren().add(toolbar);
       stage.setScene(new Scene(vb));
        stage.show();
    }
    
    
    
}

