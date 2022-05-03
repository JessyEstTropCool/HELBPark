import java.util.AbstractMap;
import java.util.HashMap;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;
import javafx.geometry.Pos;
import javafx.geometry.Insets;

/*A simple Window with a Button in the center*/
public class MoneyGiver extends Application {

    Label lTitre;
    Button button;
    VBox vbDebts;

    HashMap<String, Integer> dettes = new HashMap<String, Integer>();

    public static void main(String[] args){
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        //In javafX a Stage (Window) contains a Scene(Layout+components)
        primaryStage.setTitle("Money Giver");

        VBox layout = new VBox();
        layout.setFillWidth(true);

        lTitre = new Label("You have 0 monies");
        lTitre.prefWidthProperty().bind(primaryStage.widthProperty());;
        lTitre.setPadding(new Insets(10, 0, 10, 0));
        lTitre.setAlignment(Pos.CENTER);

        vbDebts = new VBox();
        vbDebts.setPadding(new Insets(10, 0, 10, 0));
        vbDebts.setFillWidth(true);
        vbDebts.setSpacing(10);
        //vbDebts.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        
        button = new Button();
        button.setText("Nouvelle dépense");
        button.prefWidthProperty().bind(primaryStage.widthProperty());
        button.setOnAction(e -> {
            AbstractMap.SimpleEntry<String, Integer> res = DebtBox.display();
            if (res != null)
            {
                vbDebts.getChildren().add(makeNewDepense(res.getKey(), res.getValue()));
            }
        });

        layout.getChildren().add(lTitre);
        layout.getChildren().add(vbDebts);
        layout.getChildren().add(button);
        
        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public HBox makeNewDepense(String _nom, int _montant)
    {
        HBox depense = new HBox();
        Label nom = new Label(_nom), montant = new Label(""+_montant);
        Button nvDep = new Button("Ajouter/Retirer");

        nom.setAlignment(Pos.CENTER_LEFT);
        nom.prefHeightProperty().bind(depense.heightProperty());
        nom.maxWidthProperty().bind(depense.widthProperty());
        HBox.setHgrow(nom, Priority.ALWAYS);

        montant.setAlignment(Pos.CENTER_RIGHT);
        montant.prefHeightProperty().bind(depense.heightProperty());
        montant.maxWidthProperty().bind(depense.widthProperty());
        HBox.setHgrow(montant, Priority.ALWAYS);

        depense.prefWidthProperty().bind(vbDebts.widthProperty());
        depense.setSpacing(10);
        //depense.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, Insets.EMPTY)));
        depense.getChildren().addAll(nom, montant, nvDep);

        return depense;
    }
}

