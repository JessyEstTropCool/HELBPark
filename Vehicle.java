public class Vehicle
{
    private String type;
    private String plate;

    public Vehicle(String type, String plate)
    {
        this.type = type;
        this.plate = plate;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getPlate() { return plate; }
    public void setPlate(String plate) { this.plate = plate; }

    public static String englishDoYouSpeakIt(String french)
    {
        switch ( french )
        {
            case "moto":
                return "bike";

            case "camionette":
                return "truck";

            case "voiture":
                return "car";

            default:
                return "other";
        }
    }

    public Vehicle clone()
    {
        return new Vehicle(type, plate);
    }
}
