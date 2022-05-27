import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
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

    private final static Background[] BACKGROUNDS = {
        new Background(new BackgroundFill(Color.valueOf("#00A74F"), CornerRadii.EMPTY, Insets.EMPTY)),
        new Background(new BackgroundFill(Color.valueOf("#0071B5"), CornerRadii.EMPTY, Insets.EMPTY)),
        new Background(new BackgroundFill(Color.valueOf("#EA1C24"), CornerRadii.EMPTY, Insets.EMPTY)),
        new Background(new BackgroundFill(Color.valueOf("#872971"), CornerRadii.EMPTY, Insets.EMPTY))
    };

    Parking controller;
    Button button;
    Button[] parkButtons;
    GridPane container;
    private int cellCount = 0;
    static final int MAX_COLUMNS = 5; //total cells a déplacer dans modèle

    public static void main (String[] args)  
    {  
        System.out.println("Hello !");
        launch(args); 
    }  

    @Override  
    public void start(Stage primaryStage) throws Exception 
    {  
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, INITIAL_WIDTH, INITIAL_HEIGHT);

        controller = new Parking(this);

        button = (Button)loader.getNamespace().get("coolButton");
        container = (GridPane)loader.getNamespace().get("parkingContainer");

        for ( int compt = 0; compt < MAX_COLUMNS; compt++ )
        {
            ColumnConstraints col = new ColumnConstraints(); //container.getColumnConstraints().get(0);
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
                row = new RowConstraints();//container.getRowConstraints().get(0);
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

            gridButton.getStyleClass().add("parkButton"); //.setStyle("-fx-border-color: WHITE; -fx-border-width: 2px; "); //TODO magic
            gridButton.setBackground(BACKGROUNDS[0]);
            gridButton.textFillProperty().set(Color.WHITE);
            
            final int index = compt;

            gridButton.setOnAction(e -> {
                controller.vehicleButtonPressed(index);// SpaceModifier.display(index);
            });
            
            GridPane.setColumnIndex(gridButton, compt % MAX_COLUMNS);
            GridPane.setRowIndex(gridButton, compt / MAX_COLUMNS);

            container.getChildren().add(gridButton);
            parkButtons[compt] = gridButton;
        }
        
        button.setText("");
        button.setOnAction(e -> {
            showError(button.getText());
        });

        primaryStage.setTitle("HELBPark's funky parking");
        primaryStage.getIcons().add(new Image("icon.png"));
        primaryStage.setMinWidth(MIN_WIDTH);
        primaryStage.setMinHeight(MIN_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();

        controller.start(); 
    } 

    @Override
    public void stop()
    {
        controller.closing();
    }
    
    private void updateButtons(ParkSpaces model)
    {
        for ( int compt = 0; compt < model.getSpotsCount(); compt++ )
        {
            if ( model.getVehicle(compt) != null )
            {
                parkButtons[compt].setText(compt+" - "+model.getVehicle(compt).getPlate());
                switch ( model.getVehicle(compt).getType() )
                {
                    case "bike":
                        parkButtons[compt].setBackground(BACKGROUNDS[1]);
                        break;
                    
                    case "car":
                        parkButtons[compt].setBackground(BACKGROUNDS[2]);
                        break;

                    case "truck":
                        parkButtons[compt].setBackground(BACKGROUNDS[3]);
                        break;
                }
            }
            else
            {
                parkButtons[compt].setBackground(BACKGROUNDS[0]);
                parkButtons[compt].setText(""+compt);
            }
        }
    }

    @Override
    public void setCellCount(int i)
    {
        cellCount = i;
    }

    @Override
    public void showText(String x)
    {
        Platform.runLater(() -> {
            button.setText(x);
        });
    }

    @Override
    public void update(Object o) 
    {
        ParkSpaces observable = (ParkSpaces)o;
        
        Platform.runLater(() -> {
            updateButtons(observable);
        });
    }

    @Override
    @SuppressWarnings("unchecked")
    public void showVehicleMenu(int index, boolean occupied) 
    {
        try
        {
            Stage window = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Popup.fxml"));
            Parent root = loader.load();

            Button bApply = (Button)loader.getNamespace().get("bApply");
            Button bFree = (Button)loader.getNamespace().get("bFree");
            Label lSpaceNbr = (Label)loader.getNamespace().get("lSpaceNbr");
            Label lPay = (Label)loader.getNamespace().get("lPay");
            Label lOccupied = (Label)loader.getNamespace().get("lOccupied");
            ComboBox<String> cbType = (ComboBox<String>)loader.getNamespace().get("cbType");
            TextField tfPlate = (TextField)loader.getNamespace().get("tfPlate");

            window.setTitle("HELBPark");
            window.getIcons().add(new Image("icon.png"));
            window.initModality(Modality.APPLICATION_MODAL);
            window.setMinWidth(POPUP_WIDTH);
            window.setMinHeight(POPUP_HEIGHT);

            lSpaceNbr.setText("Space "+index);
            lPay.setText("Give us your monies");
            lOccupied.setText((occupied)? "Occupied" : "Free");

            bApply.setOnAction(e -> {
                System.out.println(cbType.getValue());
            });

            bFree.setOnAction(e -> {
                controller.freeSpace(index);
                window.close();
            });

            cbType.getItems().addAll("Bike", "Car", "Truck");
            tfPlate.setText("---");
    
            Scene scene = new Scene(root);
            window.setScene(scene);
            window.showAndWait();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }
    }

    @Override
    public void showError(String message)
    {
        try
        {
            Stage window = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Error.fxml"));
            Parent root = loader.load();

            Button bOk = (Button)loader.getNamespace().get("bOk");
            Label lMessage = (Label)loader.getNamespace().get("lMessage");

            window.setTitle("HELBPark : Error");
            window.getIcons().add(new Image("icon.png"));
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