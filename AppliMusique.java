package SAE_spotify;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class AppliMusique {


    private static String nom;
    public static Musique ListMusique;

    public static void main(String[] args){
        /*
        System.out.println("Choisir la structure de données :");
        System.out.println("1 - ArrayList");
        System.out.println("2 - LinkedList");

        int choix = scanner.nextInt();

         if (choix == 1) {
            List<Musique> ListMusique = new ArrayList<Musique>();
        } else {
            List<Musique> ListMusique = new LinkedList<Musique>();
        }
        */
        
        boolean ARRAYlist = false;
        Scanner scanner = new Scanner(System.in);
        int choix = 0, choix_charge;

        if (ARRAYlist) {
            List<Musique> ListMusique = new ArrayList<Musique>();

        }
        else {
            List<Musique> ListMusique = new LinkedList<Musique>();
        }

        System.out.println("Que voulez vous faire ?");
        System.out.println("1 - Charger les données (choix du fichier)");
        System.out.println("2 - Afficher les données");
        System.out.println("3 - Trier les données");
        System.out.println("4 - Filtrer les données");
        System.out.println("5 - Rechercher des données concernant un titre");

        choix = scanner.nextInt();

        switch(choix){
            case 1:
                System.out.println("Vous avez choisi de Charger les données");
                System.out.println("");
                System.out.println("Quel fichier voulez-vous charger ?");
                System.out.println("1 - spotify_100.csv");
                System.out.println("2 - spotify_1000.csv");
                System.out.println("3 - spotify_10000.csv");
                System.out.println("4 - spotify_100000.csv");
                System.out.println("5 - spotify_FULL.csv");

                choix_charge = scanner.nextInt();

                switch(choix_charge){

                }

                break;
            case 2:
                System.out.println("Vous avez choisi d'afficher les données");
                Lecture("spotify_100.csv");
                break;
            case 3:
                System.out.println("Vous avez choisi de Trier les données");
                System.out.println("");
                System.out.println("Quel type de tri voulez-vous faire ?");
                System.out.println("1 - Tri Sélection");
                System.out.println("2 - Tri Fusion");
                System.out.println("3 - Tri Java");
                break;
            case 4:
                System.out.println("Vous avez choisi de Filter les données");
                System.out.println("");
                System.out.println("Quel type de filtre ");
                System.out.println("1 - Filtre manuel");
                System.out.println("2 - Filtre java");
                break;
            case 5:
                System.out.println("Vous avez choisi de Rechercher des données concernant un titre");
                System.out.println("");
                System.out.println("Entrez le titre que vous recherchez : ");
                String titrerecherche = scanner.nextLine();
                break;
            default:
                System.out.println("");
                System.out.println("Saississez un nombre ente 1 et 5");
                System.out.println("1 - Charger les données (choix du fichier)");
                System.out.println("2 - Afficher les données");
                System.out.println("3 - Trier les données");
                System.out.println("4 - Filtrer les données");
                System.out.println("5 - Rechercher des données concernant un titre");
                break;
        }
    }

    public static void Lecture(String nom) {
        try{
            BufferedReader tsvReader = new BufferedReader(new FileReader(nom));
            String row = new String();
            Musique musique = new Musique();
            row = tsvReader.readLine(); //row est égal à null si on est en fin de fichier
            /*ListMusique m = new ListMusique();*/

            while(row != null){

                String[] data = row.split(",");
                System.out.println(data[0]);
                row = tsvReader.readLine();
                musique.titre = data[0];
                musique.artiste = data[5];
                musique.date_album = LocalDate.parse(data[8]);
                musique.duree_titre = Integer.parseInt(data[3]);
                musique.id_titre = data[1];
                musique.nom_Album = data[7];

            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}