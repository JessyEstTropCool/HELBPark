import javafx.application.Application;  
import javafx.stage.Stage;  
import javafx.scene.Scene;  
import javafx.scene.control.Button;  
import javafx.scene.layout.StackPane;  
import javafx.event.ActionEvent;  
import javafx.event.EventHandler;  

public class Form extends Application implements IGraphics
{
    Button button;
    int compt = 1;

    public static void main (String[] args)  
    {  
        launch(args);  
    }  

    @Override  
    public void start(Stage primaryStage) throws Exception 
    {  
        primaryStage.setTitle("Hello !");
        
        button = new Button();
        button.setText("Click me");
        button.setOnAction(e -> {
            String s = "th";

            switch ( compt % 10 )
            {
                case 1:
                    s = "st";
                    break;

                case 2:
                    s = "nd";
                    break;

                case 3:
                    s = "rd";
                    break;
            }

            System.out.println("Hello " + compt++ + s + " world");
        });

        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }  

    //probablement singleton

    public void showText(String x)
    {
        System.out.println(x);
    }
}