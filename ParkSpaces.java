import java.util.ArrayList;

public class ParkSpaces
{
    private final int TOTAL_CELLS = 20;
    private final Vehicle[] places = new Vehicle[TOTAL_CELLS];

    private Simulator simu = Simulator.getInstance();
    private ArrayList<IGraphics> observers = new ArrayList<IGraphics>();

    public ParkSpaces()
    {
        Simulator.setModel(this);
    }

    public int getPlacesCount() { return TOTAL_CELLS; }

    public Vehicle getVehicle(int index)
    {
        if ( places[index] != null ) return places[index].clone();
        return null;
    }

    public void addVehicle(Vehicle v) 
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

        sendUpdate();
    }

    public void startSimulation() { simu.startSimulation(); }
    public void stopSimulation() { simu.stopSimulation(); }

    public void addObserver(IGraphics observer)
    {
        observers.add(observer);
    }

    public void removeObserver(IGraphics observer)
    {
        observers.remove(observer);
    }

    public void sendUpdate()
    {
        for ( IGraphics obs : observers )
            obs.update(this);
    }
}
