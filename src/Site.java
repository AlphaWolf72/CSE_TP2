/**
 * Représente un site de gestion de stock avec des opérations d'emprunt, de restitution,
 * de chargement et de dépôt.
 */
public class Site {
    // Codes ANSI pour la couleur du texte
    private final String YELLOW = "\u001B[33m";
    private final String BLUE = "\u001B[34m";
    private final String RESET = "\u001B[0m"; // Réinitialisation de la couleur

    /* Constantes communes à tous les sites */
    static final int STOCK_INIT = 5;
    static final int STOCK_MAX = 50;
    static final int BORNE_SUP = 8;
    static final int BORNE_INF = 2;
    private final int idSite;
    private int stock;

    /**
     * Constructeur de la classe Site.
     *
     * @param idSite L'identifiant unique du site.
     */
    public Site(int idSite) {
        this.idSite = idSite;
        this.stock = STOCK_INIT;
    }

    /**
     * Obtient l'identifiant du site.
     *
     * @return L'identifiant du site.
     */
    public int getIdSite() {
        return idSite;
    }

    /**
     * Méthode synchronisée pour emprunter un élément du stock.
     * Attendant jusqu'à ce qu'un élément soit disponible, ou qu'il n'y est pas de camion.
     */
    public synchronized void emprunter() {
        while (stock == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        stock--;
        System.out.println("|" + BLUE + "\tClient\t" + RESET + "|\t" + Thread.currentThread().getName() + "\t|\temprunter\t|\t  1\t\t|\t  " + idSite + "\t\t|\t  " + stock + "\t\t|\t");
        notifyAll();
    }

    /**
     * Méthode synchronisée pour restituer un élément dans le stock.
     * Attendant jusqu'à ce qu'il y ait de l'espace dans le stock, ou qu'il n'y est pas de camion..
     */
    public synchronized void restituer() {
        while (stock == STOCK_MAX) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        stock++;
        System.out.println("|" + BLUE + "\tClient\t" + RESET + "|\t" + Thread.currentThread().getName() + "\t|\trestituter\t|\t  1\t\t|\t  " + idSite + "\t\t|\t  " + stock + "\t\t|\t");
        notifyAll();
    }

    /**
     * Méthode synchronisée pour charger un camion avec du stock.
     *
     * @param camion Le camion à charger.
     */
    public synchronized void charger(Camion camion) {
        if (stock >= BORNE_SUP) {
            int temp = stock - STOCK_INIT;
            stock = STOCK_INIT;
            camion.setStock(camion.getStock() + temp);
            System.out.println("|" + YELLOW + "\tCamion\t" + RESET + "|\t" + Thread.currentThread().getName() + "\t|\tcharger\t\t|\t  " + temp + "\t\t|\t  " + idSite + "\t\t|\t  " + stock + "\t\t|\t");
        }
        notifyAll();
    }

    /**
     * Méthode synchronisée pour déposer du stock depuis un camion.
     *
     * @param camion Le camion depuis lequel le stock est déposé.
     */
    public synchronized void deposer(Camion camion) {
        if (stock <= BORNE_INF) {
            System.out.print("|" + YELLOW + "\tCamion\t" + RESET + "|\t" + Thread.currentThread().getName() + "\t|\tdeposer\t\t|\t  ");
            if (STOCK_INIT - stock < camion.getStock()) {
                System.out.print(STOCK_INIT - stock);
                camion.setStock(camion.getStock() - (STOCK_INIT - stock));
                stock = STOCK_INIT;
            } else {
                System.out.print(camion.getStock());
                stock += camion.getStock();
                camion.setStock(0);
            }
            System.out.println("\t\t|\t  " + idSite + "\t\t|\t  " + stock + "\t\t|\t");
        }
        notifyAll();
    }
}
