public class Client extends Thread{

    private Site siteDep;

    private Site siteArr;

    public Client(Site siteDep, Site siteArr) {
        this.siteDep = siteDep;
        this.siteArr = siteArr;
    }

    public void deplacer() {
        siteDep.emprunter();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        siteArr.restituer();
    }

    @Override
    public void run() {
        deplacer();
    }
}
