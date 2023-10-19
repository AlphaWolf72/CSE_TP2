public class Camion extends Thread {
    private final Site[] sites;
    private int stock;

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Camion(Site[] sites) {
        this.sites = sites;
        this.stock = 5;
    }

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
        while(SystemeEmprunt.current_nbClient > 0)
            tourner();
    }
}
