public class LogicPark 
{
    IGraphics form;
    Simulator simu;

    public LogicPark(IGraphics form)
    {
        this.form = form;
        simu = Simulator.getInstance();
        Simulator.setController(this);

        Parking.addObserver(form);
    }

    public void start()
    {
        simu.startSimulation();
    }
    
    public void registerVehicle(Vehicle v)
    {
        form.showText(v.getType()+" ["+v.getPlate()+"]");
        Parking.addVehicle(v);
    }
}