import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TicketPrinter 
{
    private static final String TICKET_DIR = "tickets/";
    private static final String PROMO_CODE_PREFIX = "PIERRE";
    private static final String GOLD_SAMPLE = "HELBPARK";
    private static final String SILVER_SAMPLE = "OXP";
    
    private static final String STANDARD_FILE_EXTENTION = "_std.txt";
    private static final String SILVER_FILE_EXTENTION = "_sil.txt";
    private static final String GOLD_FILE_EXTENTION = "_gol.txt";

    private static final String[] DAYS = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };

    private static final int GOLD_GRID_SIZE = 3;
    private static final int GOLD_MULTIPLIER = 2;
    private static final int SILVER_MULTIPLIER = 2;

    private static final int STANDARD_LOW_VALUE = 5;
    private static final int STANDARD_HIGH_VALUE = 10;
    private static final int SILVER_LOW_VALUE = 10;
    private static final int SILVER_HIGH_VALUE = 15;
    private static final int GOLD_LOW_VALUE = 20;
    private static final int GOLD_HIGH_VALUE = 40;

    private static final double INITIAL_CHANCE = 0.25;
    private static final double ADDED_CHANCE = 0.25; 
    private static final double HIGH_VALUE_CHANCE = 0.5;

    //créer un ticket pour une place et véhicule donné
    public static String makeTicket(int spotIndex, Vehicle vehicle, double finalPrice, String discountName)
    {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTime(new Date(System.currentTimeMillis()));
        String year = String.format("%02d", cal.get(Calendar.YEAR)); //doit être fait séparément pour avoir les 2 derniers chiffres
        String datedir = String.format("%02d%02d%s/", cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), year.substring(year.length() - 2));
        String filename = vehicle.getPlate(); //nom de fichier final devrait être [plaque]_[type].txt

        double standardChance = INITIAL_CHANCE, silverChance = INITIAL_CHANCE, goldChance = INITIAL_CHANCE, selectedTicket;
        int ticketValue;
        String extraGame = "", ticketType;

        //choix de ticket
        if ( vehicle.getType() == VehicleFactory.BIKE ) standardChance += ADDED_CHANCE;
        else if ( vehicle.getType() == VehicleFactory.CAR ) silverChance += ADDED_CHANCE;
        else if ( vehicle.getType() == VehicleFactory.TRUCK ) goldChance += ADDED_CHANCE;

        selectedTicket = Math.random()*(standardChance + silverChance + goldChance);

        // détermination des spécificités du tickets
        if ( selectedTicket < standardChance )
        {
            //ticket standard
            if ( Math.random() > HIGH_VALUE_CHANCE ) ticketValue = STANDARD_LOW_VALUE;
            else ticketValue = STANDARD_HIGH_VALUE;

            filename += STANDARD_FILE_EXTENTION;
            ticketType = "Standard";
        }
        else if ( selectedTicket < standardChance + silverChance )
        {
            //ticket silver
            char[] gameChars = new char[]{ SILVER_SAMPLE.charAt( (int)(Math.random()*SILVER_SAMPLE.length()) ), SILVER_SAMPLE.charAt( (int)(Math.random()*SILVER_SAMPLE.length()) ) };

            filename += SILVER_FILE_EXTENTION;
            ticketType = "Silver";

            if ( Math.random() > HIGH_VALUE_CHANCE ) ticketValue = SILVER_LOW_VALUE;
            else ticketValue = SILVER_HIGH_VALUE;

            extraGame = String.format("Game : %s, %s\n", gameChars[0], gameChars[1]);

            if ( gameChars[0] == gameChars[1] )
            {
                ticketValue *= SILVER_MULTIPLIER;
                extraGame += "You win, discount is doubled !\n";
            }
        }
        else
        {
            //ticket gold
            char[][] gameChars = new char[GOLD_GRID_SIZE][GOLD_GRID_SIZE];
            boolean sameOnRow = true, sameOnColumn = true, winningTicket = false;

            filename += GOLD_FILE_EXTENTION;
            ticketType = "Gold";

            if ( Math.random() > HIGH_VALUE_CHANCE ) ticketValue = GOLD_LOW_VALUE;
            else ticketValue = GOLD_HIGH_VALUE;

            extraGame = "Game :\n";

            for ( int comptx = 0; comptx < GOLD_GRID_SIZE; comptx++ )
            {
                for ( int compty = 0; compty < GOLD_GRID_SIZE; compty++ )
                {
                    gameChars[comptx][compty] = GOLD_SAMPLE.charAt( (int)(Math.random()*GOLD_SAMPLE.length()) );

                    extraGame += gameChars[comptx][compty] + " ";
                }

                extraGame += "\n";
            }

            //Vérification des colonnes et lignes; ne fonctionne que si la grille est carré car on regarde les colonnes et les lignes en même temps
            for ( int comptx = 0; comptx < GOLD_GRID_SIZE; comptx++ )
            {
                sameOnColumn = true;
                sameOnRow = true;

                for ( int compty = 0; compty < GOLD_GRID_SIZE; compty++ )
                {
                    if ( compty != 0 ) 
                    {
                        if ( gameChars[comptx][compty] != gameChars[comptx][compty - 1] ) sameOnRow = false;
                        if ( gameChars[compty][comptx] != gameChars[compty - 1][comptx] ) sameOnColumn = false;
                    }
                }

                if ( sameOnColumn || sameOnRow ) winningTicket = true;
            }

            if ( winningTicket ) 
            {
                ticketValue *= GOLD_MULTIPLIER;
                extraGame += "You win, discount is doubled !\n";
            }
        }

        //écriture du ticket lui-même
        try
        {
            File dirs = new File(TICKET_DIR+datedir);
            if ( !dirs.exists() ) dirs.mkdirs();

            File ticket = new File(dirs.getPath()+"/"+filename);
            ticket.createNewFile();

            FileWriter writer = new FileWriter(ticket);
            writer.write("---------------"+ticketType+" Ticket---------------\n");

            writer.write("Date : " + String.format("%d/%d/%d", cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR)) + "\n");
            writer.write("Spot : " + spotIndex + "\n");
            writer.write("Type : " + vehicle.getType() + "\n");
            writer.write("Plate : " + vehicle.getPlate() + "\n");
            writer.write("Base Price : " + vehicle.getBasePrice() + " euros\n");
            writer.write("Discount : " + DAYS[cal.get(Calendar.DAY_OF_WEEK) - 1] + " - " + discountName + "\n");
            writer.write("Total : " + finalPrice + " euros\n");
            writer.write("Theater Promo Code : " + PROMO_CODE_PREFIX + vehicle.getPlate().hashCode() + "\n");
            writer.write(ticketType+" Ticket \nValue : " + ticketValue + "%\n");
            writer.write(extraGame);

            writer.write("----------------------------------------");
            writer.close();
        }
        catch ( Exception e )
        {
            e.printStackTrace();
        }

        return filename;
    }
}
