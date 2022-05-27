import java.lang.ref.Cleaner.Cleanable;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Parking 
{
    IGraphics form;
    ParkSpaces model;
    DiscountType discount;

    public Parking(IGraphics form)
    {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTime(new Date(System.currentTimeMillis()));

        switch ( cal.get(Calendar.DAY_OF_WEEK) )
        {
            case Calendar.TUESDAY:
                this.discount = new HalfOffBikesDiscount();
                break;

            case Calendar.WEDNESDAY:
                this.discount = new FourthOffForPDiscount();
                break;

            case Calendar.FRIDAY:
                this.discount = new HalfOffTrucksDiscount();
                break;

            case Calendar.SATURDAY:
                this.discount = new HalfOffOnEvenDayDiscount();
                break;

            default:
                this.discount = new NoDiscount();
                break;
        }

        this.form = form;
        this.model = new ParkSpaces();

        form.setCellCount(model.getSpotsCount());
        model.addObserver(form);
    }

    public void vehicleButtonPressed(int index)
    {
        Vehicle v = model.getVehicle(index);
        boolean occupied = true;

        if ( v == null ) occupied = false;

        form.showVehicleMenu(index, occupied);
    }

    public void freeSpace(int index)
    {
        if ( model.getVehicle(index) != null )
        {
            //TODO make ticket
    
            model.removeVehicle(index);
    
            form.showText("Spot "+index+" freed !");
        }
        else
        {
            form.showError("This spot is empty");
        }
    }

    public void start()
    {
        model.startSimulation();
    }

    public void closing()
    {
        model.stopSimulation();
    }
}