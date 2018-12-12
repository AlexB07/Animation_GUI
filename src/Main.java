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
    
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
    	undoList.add(canvas);
    	VBox vb = new VBox();
    	vb.getChildren().add(canvas);
        stage.setTitle("Animation");
        HBox toolbar = new HBox();
    	Button btnUndo = new Button("Undo");
    	toolbar.getChildren().add(btnUndo);
    	btnUndo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//System.out.println("list size " +undoList.size());
//				System.out.println(undoList.get(undoList.size()-1).getChildren().get(1));
				
				if (undoList.size() >= 1 && undoList.size()  >1) {
				Canvas tempCanvas = undoList.get(undoList.size()-1);
				undoList.remove(undoList.size()-1);
				System.out.println("list size " +undoList.size() );
				canvas = tempCanvas;
				vb.getChildren().clear();
				vb.getChildren().addAll(canvas, toolbar);
				//stage.setScene(new Scene(vb));
				}
			}
		});
    	
    	Button btnAddStickman = new Button("Add Stickman");
    	toolbar.getChildren().add(btnAddStickman);
    	btnAddStickman.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				canvas.addStickman();
			}
		});
        
        
        
        
        
        vb.getChildren().add(toolbar);
       stage.setScene(new Scene(vb));
        stage.show();
    }
    /*
    
    public HBox toolbar() {
    	HBox toolbar = new HBox();
    	Button btnUndo = new Button("Undo");
    	toolbar.getChildren().add(btnUndo);
    	btnUndo.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("list size " +undoList.size());
				Canvas tempCanvas = undoList.get(undoList.size()-1);
				
				
			}
		});
    	
    	Button btnAddStickman = new Button("Add Stickman");
    	toolbar.getChildren().add(btnAddStickman);
    	btnAddStickman.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				new Stickman(canvas);
			}
		});
    	
    	
    	return toolbar;
    }
    */
    
    
}

