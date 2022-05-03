import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/*A simple Window with a Button in the center*/
public class Main extends Application {

    Button button; 

    public static void main(String[] args){
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        //In javafX a Stage (Window) contains a Scene(Layout+components)
        primaryStage.setTitle("Hello World Window");
        
        button = new Button();
        button.setText("Click me");
        
        StackPane layout = new StackPane();
        layout.getChildren().add(button);
        
        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

