import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application
{
    Canvas canvas = new Canvas(800, 600);
    
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) {
        stage.setTitle("Animation");
        stage.setScene(new Scene(canvas, 800, 600));
        stage.show();
    }
}
