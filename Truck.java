public class Truck extends Vehicle
{
    private static final int BASE_PRICE = 30;
    private static final String TYPE_STRING = VehicleFactory.TRUCK;

    public Truck(String plate)
    {
        super(plate);
    }

    @Override
    public double getBasePrice() { return BASE_PRICE; }

    @Override
    public String getType() { return TYPE_STRING; }
    public static String getTypeString() { return TYPE_STRING; }
}
