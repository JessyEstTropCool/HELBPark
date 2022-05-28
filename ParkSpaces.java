import java.util.ArrayList;

public class ParkSpaces
{
    private final int TOTAL_CELLS = 20;
    private final Vehicle[] spots = new Vehicle[TOTAL_CELLS];

    private Simulator simu = Simulator.getInstance();
    private ArrayList<IObserver> observers = new ArrayList<IObserver>();

    public ParkSpaces()
    {
        Simulator.setModel(this);
    }

    public int getSpotsCount() { return TOTAL_CELLS; }

    /**
     * Retourne un véhicule des places disponible, à l'index spécifié
     * @param index index du véhicule à récupérer
     * @return Retourne le vehicule à l'index spécifié ou null s'il n'y en a pas
     */
    public Vehicle getVehicle(int index)
    {
        if ( spots[index] != null ) return spots[index].clone();
        return null;
    }

    public void addVehicle(Vehicle vehicle) 
    {
        int i = 0;
        boolean gotIn = false;

        while ( i < spots.length && !gotIn )
        {
            if ( spots[i] == null ) 
            {
                spots[i] = vehicle;
                gotIn = true;
            }

            i++;
        }

        if ( !gotIn ) System.out.println("The "+vehicle+" is gone");

        sendUpdate();
    }

    public void replaceVehicle(int index, Vehicle vehicle)
    {
        spots[index] = vehicle;

        sendUpdate();
    }

    public void removeVehicle(int index)
    {
        if ( index >= 0 && index < getSpotsCount() )
        {
            spots[index] = null;

            sendUpdate();
        }
    }

    public void startSimulation() { simu.startSimulation(); }
    public void stopSimulation() { simu.stopSimulation(); }

    public void addObserver(IObserver observer)
    {
        observers.add(observer);
    }

    public void removeObserver(IObserver observer)
    {
        observers.remove(observer);
    }

    public void sendUpdate()
    {
        for ( IObserver obs : observers )
            obs.update(this);
    }
}
