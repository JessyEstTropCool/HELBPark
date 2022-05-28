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

        if ( v != null ) 
        {
            form.showVehicleMenu(index, discount.applyDiscount(v.getBasePrice(), v), v);
        }
    }

    public void freeSpace(int index)
    {
        Vehicle vehicle = model.getVehicle(index);

        if ( vehicle != null )
        {
            TicketPrinter.makeTicket(index, vehicle, discount.applyDiscount(vehicle.getBasePrice(), vehicle), discount.toString());
    
            model.removeVehicle(index);
    
            form.showText("Spot "+index+" freed !");
        }
        else
        {
            form.showError("This spot is empty");
        }
    }

    public boolean applyVehiculeChanges(int index, String type, String plate)
    {
        if ( type == null ) form.showError("Please specify a type");
        else if ( plate.length() != 2 || !Character.isLetter(plate.charAt(0)) || !Character.isDigit(plate.charAt(1)) ) form.showError("Invalid plate");
        else 
        {
            model.replaceVehicle(index, VehicleFactory.build(type, plate));
            form.showText("Changes applied !");
            return true;
        }

        return false;
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