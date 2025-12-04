package SAE_spotify;





import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class AppliMusique {

    private static List<Musique> ListMusique;
    private static String nom;


    public static void main(String[] args){
        boolean ARRAYlist = false;
        if (ARRAYlist) {
            ListMusique = new ArrayList<>();
        } else {
            ListMusique = new LinkedList<>();
        }
        Scanner scanner = new Scanner(System.in);
        int choix = 0, choix_charge;

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
                    case 1:
                        System.out.println("Chargement du fichier : spotify_100.csv");
                        Lecture("spotify_100.csv", ARRAYlist);
                        break;
                    case 2:
                        System.out.println("Chargement du fichier : spotify_1000.csv");
                        Lecture("spotify_1000.csv", ARRAYlist);
                        break;
                    case 3:
                        System.out.println("Chargement du fichier : spotify_10000.csv");
                        Lecture("spotify_10000.csv", ARRAYlist);
                        break;
                    case 4:
                        System.out.println("Chargement du fichier : spotify_100000.csv");
                        Lecture("spotify_100000.csv", ARRAYlist);
                        break;
                    case 5:
                        System.out.println("Chargement du fichier : spotify_FULL.csv");
                        Lecture("spotify_FULL.csv", ARRAYlist);
                        break;
                }
                break;
            case 2:
                System.out.println("Vous avez choisi d'afficher les données");
                Lecture("spotify_100.csv", ARRAYlist);

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
                System.out.println("Quel type de filtre voulez-vous faire ?");
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
                System.out.println("Erreur : Veuillez saisir un nombre ente 1 et 5");
                break;
        }
    }

    public static void Afficher(){
        
        
    }
    public static void Lecture(String nom, boolean ARRAYlist) {
        
        try{

            DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'");
            BufferedReader tsvReader = new BufferedReader(new FileReader(nom));
            String row = new String();
            row = tsvReader.readLine(); //row est égal à null si on est en fin de fichier
            String[] titre = row.split(",");
            row = tsvReader.readLine();


            while(row != null){

                String[] data = row.split(",");
                //System.out.println(data[0]+", "+ data[1]+", "+ data[3]+", "+ data[4]+", "+data[7]+", "+data[8]+", "+data[9]+", "+data[10]+", "+data[5]);
                Musique musique = new Musique(
                        data[0],     // titre_Musique = track_name
                        data[1],     // id_titre = track_id
                        Integer.parseInt(data[3]),    // duree_titre = duration_sec
                        data[4],     // type_album = album_type
                        data[7],     // nom_Album = album_name
                        LocalDateTime.parse(data[8], f).toLocalDate(),     // date_album = release_date
                        data[9],    // nom_Artiste = artist_0
                        Integer.parseInt(data[10]),    // popularite = album_popularity
                        data[5]      // artiste = artists (la liste complète)
                );

                ListMusique.add(musique);
                row = tsvReader.readLine();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
