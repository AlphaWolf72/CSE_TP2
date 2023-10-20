class Site {
    // Codes ANSI pour la couleur du texte
    private final String YELLOW = "\u001B[33m";
    private final String BLUE = "\u001B[34m";
    private final String RESET = "\u001B[0m"; // Réinitialisation de la couleur

    /* Constantes communes à tous les sites */

    static final int STOCK_INIT = 5;
    static final int STOCK_MAX = 30;
    static final int BORNE_SUP = 8;
    static final int BORNE_INF = 2;

    private static boolean unCamion = false;

    private final int idSite;

    private int stock;

    public Site(int idSite) {
        this.idSite = idSite;
        this.stock = STOCK_INIT;
    }

    public int getIdSite(){
        return idSite;
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
        System.out.println("|"+BLUE+"\tClient\t"+RESET+"|\t"+Thread.currentThread().getName()+"\t|\temprunter\t|\t  1\t\t|\t  "+idSite+"\t\t|\t  "+stock+"\t\t|\t");
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
        System.out.println("|"+BLUE+"\tClient\t"+RESET+"|\t"+Thread.currentThread().getName()+"\t|\trestituter\t|\t  1\t\t|\t  "+idSite+"\t\t|\t  "+stock+"\t\t|\t");
        notifyAll();
    }

    public synchronized void charger(Camion camion){
        unCamion = true;
        if(stock >= BORNE_SUP){
            int temp = stock - STOCK_INIT;
            stock = STOCK_INIT;
            camion.setStock(camion.getStock() + temp);
            System.out.println("|"+YELLOW+"\tCamion\t"+RESET+"|\t"+Thread.currentThread().getName()+"\t|\tcharger\t\t|\t  "+temp+"\t\t|\t  " +idSite+"\t\t|\t  "+stock+"\t\t|\t");
        }
        unCamion = false;
        notifyAll();
    }

    public synchronized void deposer(Camion camion) {
        unCamion = true;
        if(stock <= BORNE_INF){
            System.out.print("|"+YELLOW+"\tCamion\t"+RESET+"|\t" + Thread.currentThread().getName() + "\t|\tdeposer\t\t|\t  ");
            if(STOCK_INIT - stock < camion.getStock()){
                System.out.print(STOCK_INIT - stock);
                camion.setStock(camion.getStock() - (STOCK_INIT-stock));
                stock = STOCK_INIT;
            }else{
                System.out.print(camion.getStock());
                stock += camion.getStock();
                camion.setStock(0);
            }
            System.out.println("\t\t|\t  " + idSite + "\t\t|\t  " + stock + "\t\t|\t");
        }
        unCamion = false;
        notifyAll();
    }
}
