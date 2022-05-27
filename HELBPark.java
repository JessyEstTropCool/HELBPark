import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;  
import javafx.scene.control.Button;
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
    private int compt = 1, cellCount = 0;
    static final int MAX_COLUMNS = 5; //total cells a déplacer dans modèle

    public static void main (String[] args)  
    {  
        System.out.println("Hello !");
        launch(args); 
    }  

    @Override  
    public void start(Stage primaryStage) throws Exception 
    {  
        controller = new Parking(this);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("HELBPark's funky parking");
        primaryStage.getIcons().add(new Image("icon.png"));

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

            gridButton.setStyle("-fx-border-color: WHITE; -fx-border-width: 2px; ");
            gridButton.setBackground(BACKGROUNDS[0]);
            gridButton.textFillProperty().set(Color.WHITE);
            
            final int index = compt;

            gridButton.setOnAction(e -> {
                SpaceModifier.display(index);
            });
            
            GridPane.setColumnIndex(gridButton, compt % MAX_COLUMNS);
            GridPane.setRowIndex(gridButton, compt / MAX_COLUMNS);

            container.getChildren().add(gridButton);
            parkButtons[compt] = gridButton;
        }
        
        //button = new Button();
        button.setText("");
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

        /*StackPane layout = new StackPane();
        layout.getChildren().add(button);*/

        Scene scene = new Scene(root/*layout*/, 750, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        controller.start(); 
    } 
    
    private void updateButtons(ParkSpaces model)
    {
        for ( int compt = 0; compt < model.getPlacesCount(); compt++ )
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
    public void stop(){
        System.out.println("The end");
        controller.closing();
    }
}