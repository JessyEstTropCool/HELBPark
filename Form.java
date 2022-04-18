import javafx.application.Application;  
import javafx.stage.Stage;  
import javafx.scene.Scene;  
import javafx.scene.control.Button;  
import javafx.scene.layout.StackPane;  
import javafx.event.ActionEvent;  
import javafx.event.EventHandler;  

public class Form extends Application implements IGraphics
{
    public static void main (String[] args)  
    {  
        launch(args);  
    }  

    @Override  
    public void start(Stage primaryStage) throws Exception 
    {  
        Button btn1 = new Button("Say, Hello World");
        btn1.setOnAction(new EventHandler<ActionEvent>() {  
              
            @Override  
            public void handle(ActionEvent arg0) {   
                System.out.println("hello world");  
            }  
        });  

        StackPane root=new StackPane();  
        root.getChildren().add(btn1);  
        Scene scene=new Scene(root);   

        primaryStage.setScene(scene);  
        primaryStage.setTitle("First JavaFX Application");  
        primaryStage.show();  
    }  

    //probablement singleton

    public void showText(String x)
    {
        System.out.println(x);
    }
}