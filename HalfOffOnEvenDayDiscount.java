import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HalfOffOnEvenDayDiscount extends DiscountType
{
    @Override
    public double applyDiscount(double initialPrice, Vehicle vehicle) 
    {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTime(new Date(System.currentTimeMillis()));

        if ( cal.get(Calendar.DAY_OF_MONTH) % 2 == 0 ) return initialPrice / 2;
        
        return initialPrice;
    }
}
