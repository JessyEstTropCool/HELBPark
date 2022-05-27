public interface IGraphics extends IObserver
{
    void showText(String x);

    void setCellCount(int i);

    void showError(String message);

    void showVehicleMenu(int index, boolean occupied);
}
