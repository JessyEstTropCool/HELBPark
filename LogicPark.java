import java.util.Timer;
import java.util.TimerTask;
import java.io.File; // Import the File class
import java.util.Scanner; // Import the Scanner class to read text files

public class LogicPark 
{
    IGraphics form = new Form();
    final static String FILENAME = "simu_helbpark.txt";
    static Scanner reader;
    static Timer timer = new Timer();
    static int cpt, secs, totalSecs = 0;
    static File simfile;
    static String line;

    public static void start()
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
                timer.scheduleAtFixedRate(ttask, 1000, 1000);
            }
            else reader.close();
        } 
        catch (Exception e) 
        {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    static TimerTask ttask = new TimerTask() {
        @Override
        public void run() 
        {
            cpt++;

            if ( cpt >= secs )
            {
                System.out.println(line);
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
                        System.out.println("Da end "+totalSecs+" secs");
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
            else System.out.println(cpt);
        }
    };
}