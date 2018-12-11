
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Main extends Application
{
    Canvas canvas = new Canvas(800, 600);
    
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
    	VBox vb = new VBox();
    	vb.getChildren().add(canvas);
        stage.setTitle("Animation");
       
        
        Group test = new Group();
        Button b = new Button("Undo");
        test.getChildren().add(b);
        vb.getChildren().add(test);
       stage.setScene(new Scene(vb));
        stage.show();
    }
}

