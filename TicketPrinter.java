import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TicketPrinter 
{
    private static final String TICKET_DIR = "tickets/", PROMO_CODE_PREFIX = "PIERRE";
    private static final String GOLD_SAMPLE = "HELBPARK", SILVER_SAMPLE = "OXP";
    private static final String[] DAYS = new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" };
    private static final int GOLD_GRID_SIZE = 3;

    public static void makeTicket(int spotIndex, Vehicle vehicle, double finalPrice, String discountName)
    {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTime(new Date(System.currentTimeMillis()));
        String datedir = String.format("%02d%02d%s/", cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), String.format("%02d", cal.get(Calendar.YEAR)).substring(String.format("%02d", cal.get(Calendar.YEAR)).length() - 2));
        String filename = vehicle.getPlate();

        double standardChance = 0.25, silverChance = 0.25, goldChance = 0.25, selectedTicket, ticketValue = 0;
        String extraGame = "", ticketType = "Standard";

        //choix de ticket
        if ( vehicle.getType() == "bike" ) standardChance += 0.25;
        else if ( vehicle.getType() == "car" ) silverChance += 0.25;
        else if ( vehicle.getType() == "truck" ) goldChance += 0.25;

        selectedTicket = Math.random()*(standardChance + silverChance + goldChance);

        // détermination des spécificités du tickets
        if ( selectedTicket < standardChance )
        {
            //ticket standard
            if ( Math.random() > 0.5 ) ticketValue = 5;
            else ticketValue = 10;

            filename += "_std.txt";
        }
        else if ( selectedTicket < standardChance + silverChance )
        {
            //ticket silver
            char[] gameChars = new char[]{ SILVER_SAMPLE.charAt( (int)(Math.random()*SILVER_SAMPLE.length()) ), SILVER_SAMPLE.charAt( (int)(Math.random()*SILVER_SAMPLE.length()) ) };

            filename += "_sil.txt";
            ticketType = "Silver";

            if ( Math.random() > 0.5 ) ticketValue = 10;
            else ticketValue = 15;

            extraGame = String.format("Game : %s, %s\n", gameChars[0], gameChars[1]);

            if ( gameChars[0] == gameChars[1] )
            {
                ticketValue *= 2;
                extraGame += "You win, discount is doubled !\n";
            }
        }
        else
        {
            //ticket gold
            char[][] gameChars = new char[GOLD_GRID_SIZE][GOLD_GRID_SIZE];
            boolean sameOnRow = true, sameOnColumn = true, winningTicket = false;

            filename += "_gol.txt";
            ticketType = "Gold";

            if ( Math.random() > 0.5 ) ticketValue = 20;
            else ticketValue = 40;

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

            //Vérification des colonnes et lignes; ne fonctionne que qi la grille est carré car on regarde les colonnes et les lignes en même temps
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
                ticketValue *= 2;
                extraGame += "You win, discount is doubled !\n";
            }
        }

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
            writer.write("Base Price : " + vehicle.getBasePrice() + " €\n");
            writer.write("Discount : " + DAYS[cal.get(Calendar.DAY_OF_WEEK) - 1] + " - " + discountName + "\n");
            writer.write("Total : " + finalPrice + " €\n");
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
    }
}
