import java.util.AbstractMap;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class DebtBox 
{
    static AbstractMap.SimpleEntry<String, Integer> result = null;

    public static AbstractMap.SimpleEntry<String, Integer> display()
    {
        Stage window = new Stage();
        VBox layout = new VBox(10);
        Label label = new Label("Bonjour !");
        Button okButton = new Button("OK");
        result = null;

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Bonjour !");
        window.setMinWidth(250);

        okButton.setOnAction(e -> {
            result = new AbstractMap.SimpleEntry<String, Integer>("entry", 8);
            window.close();
        });

        layout.getChildren().addAll(label, okButton);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return result;
    }
}
