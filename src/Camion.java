/**
 * Représente un camion qui effectue des tournées pour charger et déposer du stock dans les sites.
 */
public class Camion extends Thread {
    private final Site[] sites;
    private int stock;

    /**
     * Obtient le stock actuel du camion.
     *
     * @return Le stock du camion.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Modifie le stock du camion.
     *
     * @param stock Le nouveau stock du camion.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Constructeur de la classe Camion.
     *
     * @param sites Les sites auxquels le camion est associé.
     */
    public Camion(Site[] sites) {
        this.sites = sites;
        this.stock = 0;
    }

    /**
     * Méthode pour effectuer une tournée, chargeant et déposant du stock dans chaque site associé au camion.
     */
    public void tourner() {
        for (Site site : sites) {
            site.charger(this);
            if (stock > 0) {
                site.deposer(this);
            }
        }
    }

    @Override
    public void run() {
        //while(SystemeEmprunt.current_nbClient > 0)
        while(Thread.activeCount() > 3) {
            tourner();
        }
    }
}
