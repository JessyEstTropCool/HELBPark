public class Car extends Vehicle 
{
    private static final int BASE_PRICE = 20;
    private static final String TYPE_STRING = VehicleFactory.CAR;

    public Car(String plate)
    {
        super(plate);
    }

    @Override
    public double getBasePrice() { return BASE_PRICE; }

    @Override
    public String getType() { return TYPE_STRING; }
    public static String getTypeString() { return TYPE_STRING; }
}
