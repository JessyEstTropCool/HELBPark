import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Date;

/*A simple Window with a Button in the center*/
public class StopWatch extends Application {

    Button button, bStart, bStop; 
    Label lTime;
    Timer tChrono;
    int compt = 1, time = 0;
    boolean chronoOn = false;
    public static void StopWatch(String[] args){
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        //In javafX a Stage (Window) contains a Scene(Layout+components)
        primaryStage.setTitle("Pierre qui roule");
        
        StackPane layout = new StackPane();
        
        lTime = new Label();
        lTime.setText("00:00:00");
        layout.getChildren().add(lTime);
        
        lTime.setTranslateY(-25);
        
        bStart = new Button();
        bStart.setText("Start");
        
        bStart.setOnAction(new EventHandler<ActionEvent>() 
        {
        	@Override
        	public void handle(ActionEvent event)
        	{
        		    if (!chronoOn)
        		    {
        		        tChrono = new Timer();
                        tChrono.scheduleAtFixedRate(new TimerTask(){
                        	@Override
                        	public void run()
                        	{
                        		Platform.runLater(() -> {
			                        time++;
			                        lTime.setText(String.format("%02d:%02d:%02d", time / 3600, (time % 3600) / 60, time % 60));  
                        		});      	
                        	}
                        }, 1000, 1000);
        		        System.out.println("There it goes");
        		        chronoOn = true;
        		    }
        	}
        });
        
        layout.getChildren().add(bStart);
        bStart.setTranslateX(-25);
        
        bStop = new Button();
        bStop.setText("Stop");
        
        bStop.setOnAction(new EventHandler<ActionEvent>() 
        {
        	@Override
        	public void handle(ActionEvent event)
        	{
        		    if (chronoOn)
        		    {
                        tChrono.cancel();
                        tChrono.purge();
        		        System.out.println("And stop");
                        chronoOn = false;
        		    }
        	}
        });
        
        layout.getChildren().add(bStop);
        bStop.setTranslateX(25);
        
        button = new Button();
        button.setText("Hello world");
        
        button.setOnAction(new EventHandler<ActionEvent>() 
        {
        	@Override
        	public void handle(ActionEvent event)
        	{
        		String s = "";
        		
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
        				
        			default:
        				s = "th";
        				break;
        		}
        		
        		System.out.println("Hello " + compt++ + s + " world");
        	}
        });
        layout.getChildren().add(button);
        
        layout.setAlignment(button, Pos.TOP_LEFT);
        
        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    @Override
    public void stop()
    {
        tChrono.cancel();
        tChrono.purge();
    }
}

