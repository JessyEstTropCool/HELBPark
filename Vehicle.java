public abstract class Vehicle
{
    private String plate;

    public Vehicle(String plate)
    {
        this.plate = plate;
    }

    public abstract double getBasePrice();
    public abstract String getType();

    public String getPlate() { return plate; }

    public Vehicle clone()
    {
        return VehicleFactory.build(getType(), getPlate());
    }
}
