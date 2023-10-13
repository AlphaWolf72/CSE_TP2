public class Camion extends Thread{
    private final Site[] sites;

    private int stock;

    public Camion(Site[] sites) {
        this.sites = sites;
        this.stock = 0;
    }

    public void tourner(){
        for (Site site: sites) {
            stock = site.charger(stock);;
            if(stock > 0){
                stock = site.deposer(stock);
            }
        }
    }

    @Override
    public void run() {
        tourner();
    }
}
