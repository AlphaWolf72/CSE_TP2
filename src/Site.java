
class Site {

    /* Constantes communes à tous les sites */

    static final int STOCK_INIT = 5;
    static final int STOCK_MAX = 10;
    static final int BORNE_SUP = 8;
    static final int BORNE_INF = 2;

    private boolean unCamion = false;

    private int idSite;

    private int stock;

    public Site(int idSite) {
        this.idSite = idSite;
        this.stock = 2;
    }

    public synchronized void emprunter() {
        while (stock == 0 || unCamion){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        stock--;
        System.out.println("Le client "+Thread.currentThread().getName()+" vient d'emprunter un vélo à "+idSite+" il reste "+stock+" vélos");
        notifyAll();
    }

    public synchronized void restituer() {
        while (stock == STOCK_MAX || unCamion){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        stock++;
        System.out.println("Le client "+Thread.currentThread().getName()+" vient restituer un vélo à "+idSite+" il y a "+stock+" vélos");
        notifyAll();
    }

    public synchronized void charger(Camion camion){
        unCamion = true;
        /*
        if (stock < BORNE_SUP){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        */
        if(stock >= BORNE_SUP){
            int temp = stock;
            stock = STOCK_INIT;
            temp = temp - STOCK_INIT;
            camion.setStock(camion.getStock() + temp);
            System.out.println(" Le camion "+Thread.currentThread().getName()+" vient charger "+temp+" vélos du site " +idSite+" il reste "+stock+" vélos");
        }
        unCamion = false;
        notifyAll();
    }

    public synchronized void deposer(Camion camion) {
        unCamion = true;
        /*
        if (stock > BORNE_INF){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        */
        if(stock <= BORNE_INF){
            int temp = stock;
            if (STOCK_INIT - stock <= camion.getStock()) {
                stock = STOCK_INIT;
                temp = (STOCK_INIT - temp);
                camion.setStock(camion.getStock() - temp);
            } else {
                stock += camion.getStock();
                temp = (temp - camion.getStock());
                camion.setStock(0);
            }
            System.out.println("Le camion " + Thread.currentThread().getName() + " vient deposer " + temp + " vélos au site " + idSite + " il y a " + stock + " vélos");
        }
        unCamion = false;
        notifyAll();
    }
}
