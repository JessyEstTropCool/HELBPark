public abstract class Vehicle
{
    //classe de base pour tous les vehicules
    
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

    @Override
    public String toString() 
    {
        return getType()+" ["+getPlate()+"]";
    }
}
