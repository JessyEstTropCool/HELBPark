public class FourthOffForPDiscount extends DiscountType
{
    @Override
    public double applyDiscount(double initialPrice, Vehicle vehicle) 
    {
        if ( vehicle.getPlate().startsWith("P") ) return initialPrice * (3.0 / 4);
        
        return initialPrice;
    }
}
