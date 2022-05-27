import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SpaceModifier 
{
    @SuppressWarnings("unchecked")
    public static void display(int spaceNbr)
    {
        try
        {
            Stage window = new Stage();
            FXMLLoader loader = new FXMLLoader(SpaceModifier.class.getResource("Popup.fxml"));
            Parent root = loader.load();

            Button bApply = (Button)loader.getNamespace().get("bApply");
            Button bFree = (Button)loader.getNamespace().get("bFree");
            Label lSpaceNbr = (Label)loader.getNamespace().get("lSpaceNbr");
            Label lPay = (Label)loader.getNamespace().get("lPay");
            Label lOccupied = (Label)loader.getNamespace().get("lOccupied");
            ComboBox<String> cbType = (ComboBox<String>)loader.getNamespace().get("cbType");
            TextField tfPlate = (TextField)loader.getNamespace().get("tfPlate");

            window.setTitle("HELBPark's funky parking");
            window.getIcons().add(new Image("icon.png"));
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle("HELBPark");
            window.setMinWidth(250);
            window.setMinHeight(250);

            lSpaceNbr.setText("Space "+spaceNbr);
            lPay.setText("Give us your monies");
            lOccupied.setText("Free");

            bApply.setOnAction(e -> {
                System.out.println("Apply");
            });

            bFree.setOnAction(e -> {
                System.out.println("Free");
            });

            cbType.getItems().addAll("Bike", "Car", "Truck");
            tfPlate.setText("Helloe");
    
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
