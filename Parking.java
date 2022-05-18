import java.util.ArrayList;

public class Parking
{
    private static final int TOTAL_CELLS = 20;
    private static final Vehicle[] places = new Vehicle[TOTAL_CELLS];

    private static ArrayList<IGraphics> observers = new ArrayList<IGraphics>();

    public static int getPlacesCount() { return TOTAL_CELLS; }

    public static void addVehicle(Vehicle v) 
    {
        int i = 0;
        boolean gotIn = false;

        while ( i < places.length && !gotIn )
        {
            if ( places[i] == null ) 
            {
                places[i] = v;
                gotIn = true;
            }

            i++;
        }

        if ( !gotIn ) System.out.println("The "+v.getType()+" is gone");
    }

    public static void addObserver(IGraphics observer)
    {
        observers.add(observer);
    }

    public static void removeObserver(IGraphics observer)
    {
        observers.remove(observer);
    }

    public static void sendUpdate()
    {
        for ( IGraphics obs : observers )
            obs.update(places);
    }
}
