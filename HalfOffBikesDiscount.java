public class HalfOffBikesDiscount extends DiscountType
{
    @Override
    public double applyDiscount(double initialPrice, Vehicle vehicle)
    {
        if ( vehicle.getType() == "bike" ) return initialPrice / 2;
        
        return initialPrice;
    }

    @Override
    public String toString() 
    {
        return "Half off for bikes";
    }
}
