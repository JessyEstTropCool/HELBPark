import java.util.Timer;
import java.util.TimerTask;
import java.io.File; // Import the File class
import java.util.Scanner; // Import the Scanner class to read text files

public class Simulator 
{
    private final static String FILENAME = "simu_helbpark.txt";
    private final static int SECOND = 1000;

    private static Simulator instance = null;
    private static ParkSpaces model = null;

    private Scanner reader;
    private Timer timer = new Timer();
    private int cpt, secs, totalSecs = 0;
    private File simfile;
    private String line;
    private Vehicle car;
    private boolean ok = false;

    //monte le conteur d'une seconde et ajoute une voiture si besoin
    private TimerTask ttask = new TimerTask() {
        @Override
        public void run() 
        {
            cpt++;

            if ( cpt >= secs )
            {
                //structure de ligne : [Nombre de secondes, type véhicule, immatriculation]
                car = VehicleFactory.build(line.split(",")[1], line.split(",")[2]); 
                model.addVehicle(car);

                totalSecs += cpt;
                cpt = 0;

                try
                {
                    //si il ya une autre ligne on utilise celle-ci, sinon on arrete la simulation
                    if ( reader.hasNextLine() )
                    {
                        int seconds = getSeconds(totalSecs), minutes = getMinutes(totalSecs);
                        line = reader.nextLine();
                        secs = Integer.parseInt(line.split(",")[0]);
                        System.out.println(minutes+"m "+seconds+"s CHANGE !!!!!!");
                    }
                    else 
                    {
                        stopSimulation();
                    }
                }
                catch (Exception e) 
                {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
            else
            {
                int seconds = getSeconds(totalSecs + cpt), minutes = getMinutes(totalSecs + cpt);
                System.out.println(minutes+"m "+seconds+"s");
            }
        }
    };

    //singleton pour éviter d'avoir deux simulation en cous au même moment
    public static Simulator getInstance()
    {
        if (instance == null) instance = new Simulator();

        return instance;
    }

    private Simulator()
    {
        //lit le fichier et prend la première ligne
        try 
        {
            simfile = new File(FILENAME);
            reader = new Scanner(simfile);
            cpt = 0;

            if ( reader.hasNextLine() )
            {
                line = reader.nextLine();
                secs = Integer.parseInt(line.split(",")[0]);
                ok = true;
            }
            else reader.close();
        } 
        catch (Exception e) 
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    //commence la simulation s'il n'y a pas eu d'erreurs au chargement
    public void startSimulation()
    {
        if ( ok ) timer.scheduleAtFixedRate(ttask, SECOND, SECOND);
        else System.out.println("An error occurred.");
    }

    //spécifie le modèle a qui envoyer les vehicules
    public static void setModel(ParkSpaces spaces)
    {
        model = spaces;
    }

    //arrete la simulation en indiquant le temps de fin
    public void stopSimulation()
    {
        int seconds = getSeconds(totalSecs), minutes = getMinutes(totalSecs);
        System.out.println(minutes+"m "+seconds+"s END !!!!!!!");
        reader.close();
        timer.cancel();
        timer.purge();
    }

    private int getMinutes(int totalSeconds) { return (totalSeconds / 60) % 60; }
    private int getSeconds(int totalSeconds) { return totalSeconds % 60; }
}
