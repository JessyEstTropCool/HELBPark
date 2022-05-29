import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;  
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.Image;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;  

public class HELBPark extends Application implements IGraphics
{
    private final int MIN_WIDTH = 500;
    private final int MIN_HEIGHT = 400;
    private final int INITIAL_WIDTH = 750;
    private final int INITIAL_HEIGHT = 500;
    private final int POPUP_WIDTH = 250;
    private final int POPUP_HEIGHT = 250;
    private static final int MAX_COLUMNS = 5; //total cells a déplacer dans modèle

    private final String MAIN_SCREEN_FILENAME = "MainScreen.fxml";
    private final String ERROR_SCREEN_FILENAME = "Error.fxml";
    private final String POPUP_SCREEN_FILENAME = "Popup.fxml";
    private final String ICON_FILENAME = "icon.png";
    private final String WINDOW_TITLE = "HELBPark's funky parking";

    private final String NO_VEHICLE_KEY = "none";

    private final HashMap<String, Background> BACKGROUNDS = new HashMap<String, Background>() {{
        put(NO_VEHICLE_KEY, new Background(new BackgroundFill(Color.valueOf("#00A74F"), CornerRadii.EMPTY, Insets.EMPTY)) );
        put(VehicleFactory.BIKE, new Background(new BackgroundFill(Color.valueOf("#0071B5"), CornerRadii.EMPTY, Insets.EMPTY)) );
        put(VehicleFactory.CAR, new Background(new BackgroundFill(Color.valueOf("#EA1C24"), CornerRadii.EMPTY, Insets.EMPTY)) );
        put(VehicleFactory.TRUCK, new Background(new BackgroundFill(Color.valueOf("#872971"), CornerRadii.EMPTY, Insets.EMPTY)) );
    }};

    private Parking controller;
    private Label lStatus;
    private Button[] parkButtons;
    private GridPane container;
    private int cellCount = 0;

    public static void main (String[] args)  
    {  
        launch(args); 
    }  

    //Méthode de démmarage nécessaire à JavaFX
    //Ajoute aussi le controlleur Parking
    @Override  
    public void start(Stage primaryStage) throws Exception 
    {  
        FXMLLoader loader = new FXMLLoader(getClass().getResource(MAIN_SCREEN_FILENAME));
        Parent root = loader.load();
        Scene scene = new Scene(root, INITIAL_WIDTH, INITIAL_HEIGHT);

        controller = new Parking(this);

        lStatus = (Label)loader.getNamespace().get("lStatus");
        container = (GridPane)loader.getNamespace().get("parkingContainer");

        for ( int compt = 0; compt < MAX_COLUMNS; compt++ )
        {
            ColumnConstraints col = new ColumnConstraints();
            col.setHalignment(HPos.CENTER);
            col.setHgrow(Priority.ALWAYS);
            col.setMinWidth(10);

            container.getColumnConstraints().add(col);
        }

        RowConstraints row;
        parkButtons = new Button[cellCount];

        for ( int compt = 0; compt < cellCount; compt++)
        {
            if ( compt % MAX_COLUMNS == 0 )
            {
                row = new RowConstraints();
                row.setValignment(VPos.CENTER);
                row.setVgrow(Priority.ALWAYS);
                row.setMinHeight(10);

                container.getRowConstraints().add(row);
            }

            Button gridButton = new Button(""+compt);
            gridButton.setAlignment(Pos.CENTER);
            gridButton.setMaxHeight(Double.MAX_VALUE);
            gridButton.setMaxWidth(Double.MAX_VALUE);
            gridButton.prefWidthProperty().bind(container.widthProperty().divide(MAX_COLUMNS));

            gridButton.setTextAlignment(TextAlignment.CENTER);

            gridButton.getStyleClass().add("parkButton");
            gridButton.setBackground(BACKGROUNDS.get(NO_VEHICLE_KEY));
            gridButton.textFillProperty().set(Color.WHITE);
            
            final int index = compt;

            gridButton.setOnAction(e -> {
                controller.vehicleButtonPressed(index);
            });
            
            GridPane.setColumnIndex(gridButton, compt % MAX_COLUMNS);
            GridPane.setRowIndex(gridButton, compt / MAX_COLUMNS);

            container.getChildren().add(gridButton);
            parkButtons[compt] = gridButton;
        }

        primaryStage.setTitle(WINDOW_TITLE);
        primaryStage.getIcons().add(new Image(ICON_FILENAME));
        primaryStage.setMinWidth(MIN_WIDTH);
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();

        controller.start(); 
    } 

    //appelle la méthode d'arret du controlleur
    @Override
    public void stop()
    {
        controller.closing();
    }

    //méthode de réception de changements du modèles; appelle la mise a jour des boutons
    @Override
    public void update(Object o) 
    {
        ParkSpaces observable = (ParkSpaces)o;
        
        Platform.runLater(() -> {
            updateButtons(observable);
        });
    }
    
    //Mets les boutons à jour en fonction du modèle donné
    private void updateButtons(ParkSpaces model)
    {
        for ( int compt = 0; compt < model.getSpotsCount(); compt++ )
        {
            if ( model.getVehicle(compt) != null )
            {
                parkButtons[compt].setText(compt+" - "+model.getVehicle(compt).getPlate());
                parkButtons[compt].setBackground(BACKGROUNDS.get(model.getVehicle(compt).getType()));
            }
            else
            {
                parkButtons[compt].setBackground(BACKGROUNDS.get(NO_VEHICLE_KEY));
                parkButtons[compt].setText(""+compt);
            }
        }
    }

    //indique le nombre de boutons a générer
    //doit être appelé avant la génération de ceux-ci
    @Override
    public void setCellCount(int i)
    {
        cellCount = i;
    }

    //Affiche du texte sur le label lStatus, pour donner plus d'information
    @Override
    public void showText(String x)
    {
        Platform.runLater(() -> {
            lStatus.setText(x);
        });
    }

    //Affiche une fenêtre séparée avec le menu de place, permettant le changement et la libéation d'une place
    @Override
    @SuppressWarnings("unchecked") //doit rester là à cause de la ComboBox qu'on ne peut pas case facilement
    public void showVehicleMenu(int index, double price, Vehicle vehicle) 
    {
        try
        {
            Stage window = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(POPUP_SCREEN_FILENAME));
            Parent root = loader.load();

            Button bApply = (Button)loader.getNamespace().get("bApply");
            Button bFree = (Button)loader.getNamespace().get("bFree");
            Label lSpaceNbr = (Label)loader.getNamespace().get("lSpaceNbr");
            Label lPay = (Label)loader.getNamespace().get("lPay");
            Label lOccupied = (Label)loader.getNamespace().get("lOccupied");
            ComboBox<String> cbType = (ComboBox<String>)loader.getNamespace().get("cbType");
            TextField tfPlate = (TextField)loader.getNamespace().get("tfPlate");

            window.setTitle(WINDOW_TITLE);
            window.getIcons().add(new Image(ICON_FILENAME));
            window.initModality(Modality.APPLICATION_MODAL);
            window.setMinWidth(POPUP_WIDTH);
            window.setMinHeight(POPUP_HEIGHT);

            lSpaceNbr.setText("Spot "+index);
            lPay.setText("To pay : " + price + " €");

            bApply.setOnAction(e -> {
                //applique le changement de véhicule
                //ferme la fenetre si le changement est valide
                if ( controller.applyVehiculeChanges(index, cbType.getValue(), tfPlate.getText()) ) window.close();
            });

            bFree.setOnAction(e -> {
                //libère la place
                controller.freeSpace(index);
                window.close();
            });

            cbType.getItems().addAll(VehicleFactory.getTypes());

            //met les infos du vehicule s'il y en a un
            if ( vehicle != null )
            {
                lOccupied.setText("Occupied");
                tfPlate.setText(vehicle.getPlate());
                cbType.setValue(vehicle.getType());
            }
            else
            {
                lOccupied.setText("Free");
                tfPlate.setText("");
            }
    
            Scene scene = new Scene(root);
            window.setScene(scene);
            window.showAndWait();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    //Affiche une erreur
    @Override
    public void showError(String message)
    {
        try
        {
            Stage window = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(ERROR_SCREEN_FILENAME));
            Parent root = loader.load();

            Button bOk = (Button)loader.getNamespace().get("bOk");
            Label lMessage = (Label)loader.getNamespace().get("lMessage");

            window.setTitle(WINDOW_TITLE);
            window.getIcons().add(new Image(ICON_FILENAME));
            window.initModality(Modality.APPLICATION_MODAL);
            window.setMinWidth(POPUP_WIDTH);
            window.setMinHeight(POPUP_HEIGHT);

            lMessage.setText(message);

            bOk.setOnAction(e -> {
                window.close();
            });
    
            Scene scene = new Scene(root);
            window.setScene(scene);
            window.showAndWait();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }
}