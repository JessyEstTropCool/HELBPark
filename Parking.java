public class Parking 
{
    IGraphics form;
    ParkSpaces model;

    public Parking(IGraphics form)
    {
        this.form = form;
        model = new ParkSpaces();

        form.setCellCount(model.getPlacesCount());

        model.addObserver(form);
    }

    //public int getSpacesCount() { return model.getPlacesCount(); }

    public void start()
    {
        model.startSimulation();
    }
    
    /*public void registerVehicle(Vehicle v)
    {
        form.showText(v.getType()+" ["+v.getPlate()+"]");
        model.addVehicle(v);
    }*/

    public void closing()
    {
        model.stopSimulation();
    }
}