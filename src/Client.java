public class Client extends Thread {
    private final Site siteDep;
    private final Site siteArr;

    public Client(Site siteDep, Site siteArr) {
        this.siteDep = siteDep;
        this.siteArr = siteArr;
        SystemeEmprunt.current_nbClient++;
    }

    public void deplacer() {
        siteDep.emprunter();
        try {
            Thread.sleep(1000L * siteArr.getIdSite()* siteDep.getIdSite());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        siteArr.restituer();
        SystemeEmprunt.current_nbClient--;
    }

    @Override
    public void run() {
        deplacer();
    }
}
