import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.text.TextAlignment;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;

/*A simple Window with a Button in the center*/
public class IMC extends Application {

    Button button;
    TextField tfWeight, tfHeight;
    Label lResult, lWeight, lHeight;

    public static void main(String[] args){
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        //In javaFX a Stage (Window) contains a Scene(Lasertyout+components)
        primaryStage.setTitle("IMC Calculator");

        StackPane layout = new StackPane();

        lResult = new Label();
        lResult.setText("Votre IMC");
        lResult.setTranslateY(-75);
        lResult.setTextAlignment(TextAlignment.CENTER);
        //lResult.setTextFill(Color.web("#FFFFFF"));

        lWeight = new Label();
        lWeight.setText("Poids (kg)");
        lWeight.setTranslateY(-45);

        tfWeight = new TextField ();
        tfWeight.setPromptText("Poids (kg)");
        tfWeight.setTranslateY(-25);

        lHeight = new Label();
        lHeight.setText("Taille (m)");
        lHeight.setTranslateY(5);

        tfHeight = new TextField ();
        tfHeight.setPromptText("Taille (m)");
        tfHeight.setTranslateY(25);

        button = new Button();
        button.setText("Calculer");
        button.setTranslateY(75);

        button.setOnAction(new EventHandler<ActionEvent>()
        {
        	@Override
        	public void handle(ActionEvent event)
        	{
        	    if ( isNumeric(tfHeight.getText()) && isNumeric(tfWeight.getText()) )
        	    {
                    double height = Double.parseDouble(tfHeight.getText()), weight = Double.parseDouble(tfWeight.getText());
                    double bmi = Math.floor((weight / ( height * height )) * 100) / 100;
        	        String comment = "";

        	        if (bmi < 18.5) comment = "Insuffisance pondérale";
        	        else if (bmi < 25) comment = "Corpulance normale";
        	        else if (bmi < 30) comment = "Surpoids";
        	        else if (bmi < 35) comment = "Obésité modérée";
        	        else if (bmi < 40) comment = "Obésité sévère";
        	        else comment = "Obésité morbide";

                    lResult.setText("Votre IMC est : "+bmi+"\n"+comment);
        	    }
        	    else
        	    {
        	        lResult.setText("Erreur de nombre, réessayez !");
        	    }
        	}
        });

        layout.getChildren().add(lResult);
        layout.getChildren().add(lWeight);
        layout.getChildren().add(tfWeight);
        layout.getChildren().add(lHeight);
        layout.getChildren().add(tfHeight);
        layout.getChildren().add(button);

        layout.setAlignment(lWeight, Pos.CENTER_LEFT);
        layout.setAlignment(lHeight, Pos.CENTER_LEFT);
        //layout.setFill(Color.web("#008080"));

        Scene scene = new Scene(layout, 300, 250);
        scene.setFill(Color.web("#008080"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean isNumeric(String nbr)
    {
        try
        {
            Double.parseDouble(nbr);
            return true;
        }
        catch ( Exception e )
        {
            return false;
        }
    }
}

