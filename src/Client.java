/**
 * Représente un client qui effectue des déplacements entre deux sites.
 */
public class Client extends Thread {
    private final Site siteDep;
    private final Site siteArr;

    /**
     * Constructeur de la classe Client.
     *
     * @param siteDep Le site de départ du client.
     * @param siteArr Le site d'arrivée du client.
     */
    public Client(Site siteDep, Site siteArr) {
        this.siteDep = siteDep;
        this.siteArr = siteArr;
    }

    /**
     * Méthode pour déplacer le client d'un site de départ à un site d'arrivée.
     * Le client emprunte un élément du site de départ, attend un certain temps, puis restitue l'élément au site d'arrivée.
     */
    public void deplacer() {
        siteDep.emprunter();
        try {
            Thread.sleep(1000L * siteArr.getIdSite() * siteDep.getIdSite());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        siteArr.restituer();
        //SystemeEmprunt.current_nbClient--;
    }

    @Override
    public void run() {
        deplacer();
    }
}
