public class NoDiscount extends DiscountType
{
    @Override
    public double applyDiscount(double initialPrice, Vehicle vehicle) 
    {
        return initialPrice;
    }

    @Override
    public String toString() 
    {
        return "No discount";
    }
}
