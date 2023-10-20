import java.util.Random;
/**
 * Classe principale pour la simulation du système d'emprunt.
 */
public class SystemeEmprunt {
    /* Constantes de la simulation */
    static final int NB_SITES = 5;
    static final int NB_CLIENTS = 30;

    public static void main(String[] args) {
        Site[] sites = new Site[NB_SITES];
        Client[] clients = new Client[NB_CLIENTS];

        /* Instanciation des sites */
        for (int i = 0; i < NB_SITES; i++) {
            sites[i] = new Site(i);
        }

        /* Instanciation des clients avec des sites de départ et d'arrivée aléatoires */
        for (int i = 0; i < NB_CLIENTS; i++) {
            Random r = new Random();
            int siteDep = r.nextInt(NB_SITES);
            int siteArr = r.nextInt(NB_SITES);
            clients[i] = new Client(sites[siteDep], sites[siteArr]);
        }

        /* Instanciation du camion */
        Camion camion = new Camion(sites);

        /* Début de l'affichage */
        System.out.println("_________________________________________________________________________________");
        System.out.println("|\tType\t|\tThread\t\t|\tAction\t\t|\tNombre\t|\tSite\t|\tStock\t|\t");

        /* Démarrage du camion et des clients */
        camion.start();

        for (Client cl : clients) {
            cl.start();
        }
    }
}

