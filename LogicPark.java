public class LogicPark 
{
    static IGraphics form;
    static Simulator simu;

    public static void start(IGraphics form)
    {
        LogicPark.form = form; 
        Simulator.getInstance();
    }
    
    public static void registerVehicle(Vehicle v)
    {
        form.showText(v+" "+v.getType()+" ["+v.getPlate()+"]");
    }
}