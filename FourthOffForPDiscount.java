public class FourthOffForPDiscount extends DiscountType
{
    @Override
    public double applyDiscount(double initialPrice, Vehicle vehicle) 
    {
        if ( vehicle.getPlate().startsWith("P") ) return initialPrice * (3.0 / 4);
        
        return initialPrice;
    }

    @Override
    public String toString() 
    {
        return "25% off if your plate begins with P";
    }
}
