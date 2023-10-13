
class Site {

    /* Constantes communes à tous les sites */

    static final int STOCK_INIT = 5;
    static final int STOCK_MAX = 10;
    static final int BORNE_SUP = 8;
    static final int BORNE_INF = 2;

    private int idSite;

    private int stock;

    public Site(int idSite) {
        this.idSite = idSite;
        this.stock = STOCK_INIT;
    }

    public synchronized void emprunter() {
        while (stock <= 0){
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
        while (stock >= STOCK_MAX){
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

    public synchronized int charger(int nb){
        if(stock <= BORNE_SUP){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        int temp = stock;
        stock = STOCK_INIT;
        temp = temp - STOCK_INIT;
        System.out.println("Le camion "+Thread.currentThread().getName()+" vient charger "+temp+" vélos du site " +idSite+" il reste "+stock+" vélos");
        notifyAll();
        return nb + (temp);
    }

    public synchronized int deposer(int nb){
        if(stock <= BORNE_INF){
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if(STOCK_INIT - stock <= nb){
            int temp = stock;
            stock = STOCK_INIT;
            temp = (STOCK_INIT - temp);
            System.out.println("Le camion "+Thread.currentThread().getName()+" vient deposer "+temp+" vélos au site " +idSite+" il y a "+stock+" vélos");
            notifyAll();
            return nb - temp;
        }else{
            stock += nb;
            notifyAll();
            return 0;
        }
    }
}
