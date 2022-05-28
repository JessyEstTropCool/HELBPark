public class Bike extends Vehicle
{
    private static final int BASE_PRICE = 10;
    private static final String TYPE_STRING = VehicleFactory.BIKE;

    public Bike(String plate)
    {
        super(plate);
    }

    @Override
    public double getBasePrice() { return BASE_PRICE; }

    @Override
    public String getType() { return TYPE_STRING; }
    public static String getTypeString() { return TYPE_STRING; }
}
