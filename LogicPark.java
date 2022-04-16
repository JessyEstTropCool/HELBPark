public class LogicPark 
{
    static IGraphics form = new Form();
    static Simulator simu;

    public static void start()
    {
        Simulator.getInstance();
    }
    
    public static void registerVehicle(Vehicle v)
    {
        form.showText(v+" "+v.getType()+" ["+v.getPlate()+"]");
    }
}