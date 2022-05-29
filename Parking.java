import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Parking 
{
    private IGraphics form;
    private ParkSpaces model;
    private DiscountType discount;

    public Parking(IGraphics form)
    {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTime(new Date(System.currentTimeMillis()));

        //sélectionne le type de réduction en fonction du jour
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

        //indique le nombre de places avant de finir
        form.setCellCount(model.getSpotsCount());
        model.addObserver(form);
    }

    //méthode a appeler quand i-on presse le bouton d'une place
    //demande l'affichage du menu d'une place
    public void vehicleButtonPressed(int index)
    {
        Vehicle vehicle = model.getVehicle(index);

        if ( vehicle != null ) 
        {
            form.showVehicleMenu(index, discount.applyDiscount(vehicle.getBasePrice(), vehicle), vehicle);
        }
    }

    //méthode à appeler quand on veut libérer une place
    public void freeSpace(int index)
    {
        Vehicle vehicle = model.getVehicle(index);

        if ( vehicle != null )
        {
            String ticket = TicketPrinter.makeTicket(index, vehicle, discount.applyDiscount(vehicle.getBasePrice(), vehicle), discount.toString());
    
            model.removeVehicle(index);
    
            form.showText("Ticket "+ticket+" created");
        }
        else
        {
            form.showError("This spot is empty");
        }
    }

    //méthode à appeler pour changer le véhicule d'une place
    //retourne vrai si les changement on été appliqués
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

    //méthode à appeler quand l'application est prête; commence la simulation
    public void start()
    {
        model.startSimulation();
    }

    //méthode à appeler quand l'application s'arrete; arrete la simulation
    public void closing()
    {
        model.stopSimulation();
    }
}