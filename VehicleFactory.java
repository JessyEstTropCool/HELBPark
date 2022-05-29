public class VehicleFactory 
{
    //regroupe toutes les string de type
    public static final String BIKE = "bike", TRUCK = "truck", CAR = "car";

    //génére un vehicule en fonction du type et de la plaque
    public static Vehicle build(String type, String plate)
    {
        switch ( type.toLowerCase() )
        {
            case "moto":
            case BIKE:
                return new Bike(plate);

            case "camionette":
            case TRUCK:
                return new Truck(plate);

            case "voiture":
            case CAR:
                return new Car(plate);

            default:
                return null;
        }
    }

    //donne un tableau de tous les types existants
    public static String[] getTypes()
    {
        return new String[]{ BIKE, CAR, TRUCK };
    }
}
