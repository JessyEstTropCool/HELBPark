import java.util.Timer;
import java.util.TimerTask;
import java.io.File; // Import the File class
import java.util.Scanner; // Import the Scanner class to read text files

public class Simulator 
{
    private final static String FILENAME = "simu_helbpark.txt";
    private final static int SECOND = 1000;

    private static Simulator instance = null;

    private Scanner reader;
    private Timer timer = new Timer();
    private int cpt, secs, totalSecs = 0;
    private File simfile;
    private String line;
    private Vehicle car;

    TimerTask ttask = new TimerTask() {
        @Override
        public void run() 
        {
            cpt++;

            if ( cpt >= secs )
            {
                car = new Vehicle(Vehicle.englishDoYouSpeakIt(line.split(",")[1]), line.split(",")[2]);
                LogicPark.registerVehicle(car);

                totalSecs += cpt;
                cpt = 0;

                try
                {
                    if ( reader.hasNextLine() )
                    {
                        line = reader.nextLine();
                        secs = Integer.parseInt(line.split(",")[0]);
                    }
                    else 
                    {
                        int seconds = totalSecs % 60, minutes = (totalSecs / 60) % 60;
                        System.out.println(minutes+"m "+seconds+"s");
                        reader.close();
                        timer.cancel();
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
                int seconds = (totalSecs + cpt) % 60, minutes = ((totalSecs + cpt) / 60) % 60;
                System.out.println(minutes+"m "+seconds+"s");
            }
        }
    };

    public static Simulator getInstance()
    {
        if (instance == null) instance = new Simulator();

        return instance;
    }

    private Simulator()
    {
        try 
        {
            simfile = new File(FILENAME);
            reader = new Scanner(simfile);
            cpt = 0;

            if ( reader.hasNextLine() )
            {
                line = reader.nextLine();
                secs = Integer.parseInt(line.split(",")[0]);
                timer.scheduleAtFixedRate(ttask, SECOND, SECOND);
            }
            else reader.close();
        } 
        catch (Exception e) 
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
