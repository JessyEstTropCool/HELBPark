public class HalfOffTrucksDiscount extends DiscountType
{
    @Override
    public double applyDiscount(double initialPrice, Vehicle vehicle) 
    {
        if ( vehicle.getType() == "truck" ) return initialPrice / 2;
        
        return initialPrice;
    }

    @Override
    public String toString() 
    {
        return "Half off for trucks";
    }
}
