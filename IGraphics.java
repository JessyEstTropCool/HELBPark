public interface IGraphics extends IObserver
{
    //interface contenant les méthodes nécessaire de la vue pour le controlleur

    //affiche du texte de status
    void showText(String x);

    //indique le nombre de places à gérer
    void setCellCount(int i);

    //affiche une erreur
    void showError(String message);

    //affiche le menu de modification de vehicule
    void showVehicleMenu(int index, double price, Vehicle vehicle);
}
