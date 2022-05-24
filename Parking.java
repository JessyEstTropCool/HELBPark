public class Parking 
{
    IGraphics form;
    Simulator simu;
    ParkSpaces model;

    public Parking(IGraphics form)
    {
        this.form = form;
        simu = Simulator.getInstance();
        model = new ParkSpaces();

        Simulator.setController(this);
        model.addObserver(form);
    }

    public int getSpacesCount() { return model.getPlacesCount(); }

    public void start()
    {
        simu.startSimulation();
    }
    
    public void registerVehicle(Vehicle v)
    {
        form.showText(v.getType()+" ["+v.getPlate()+"]");
        model.addVehicle(v);
    }
}