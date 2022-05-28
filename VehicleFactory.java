public class VehicleFactory 
{
    public static final String BIKE = "bike", TRUCK = "truck", CAR = "car";

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

    public static String[] getTypes()
    {
        return new String[]{ BIKE, CAR, TRUCK };
    }
}
