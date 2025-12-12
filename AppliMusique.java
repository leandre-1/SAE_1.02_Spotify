package SAE_spotify;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;


public class AppliMusique {
    private static long nombreLignes = 0;
    private static int[] tabPopularite ;
    private static List<Musique> ListMusique;
    private static String nom;

    public static void main(String[] args){
        boolean ARRAYlist = false;
        int choix = -1;
        long start,stop;

        if (ARRAYlist) {
            ListMusique = new ArrayList<>();
        } else {
            ListMusique = new LinkedList<>();
        }

        while (choix != 0){
        Scanner scanner = new Scanner(System.in);
        int choix_charge;

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
                System.out.println(" ");
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
                        Chargement("spotify_100.csv");
                        break;
                    case 2:
                        System.out.println("Chargement du fichier : spotify_1000.csv");
                        Chargement("spotify_1000.csv");
                        break;
                    case 3:
                        System.out.println("Chargement du fichier : spotify_10000.csv");
                        Chargement("spotify_10000.csv");
                        break;
                    case 4:
                        System.out.println("Chargement du fichier : spotify_100000.csv");
                        Chargement("spotify_100000.csv");
                        break;
                    case 5:
                        System.out.println("Chargement du fichier : spotify_FULL.csv");
                        Chargement("spotify_FULL.csv");
                        break;
                }
                break;
            case 2:
                System.out.println("Vous avez choisi d'afficher les données");
                Afficher();
                break;
            case 3:
                System.out.println("Vous avez choisi de Trier les données");
                System.out.println(" ");
                System.out.println("Quel type de tri voulez-vous faire ?");
                System.out.println("1 - Tri Sélection");
                System.out.println("2 - Tri Fusion");
                System.out.println("3 - Tri Java");

                choix = scanner.nextInt();

                switch(choix){
                    case 1:
                        start = System.nanoTime();
                        triSelection();
                        stop = System.nanoTime();
                        System.out.println(ListMusique.toString());
                        System.out.println(stop -start +" ms");
                        break;
                    case 2:
                        start = System.nanoTime();
                        //triFusion(tabPopularite,0, (int) (nombreLignes-1));
                        stop = System.nanoTime();
                        AfficherTab(tabPopularite);
                        System.out.println(stop -start +" ms");
                        break;
                    case 3:
                        System.out.println("Tri Java : ");
                        System.out.println("Quelles données voulez-vous trier ?");
                        System.out.println(" ");
                        System.out.println("1 - Selon l'année");
                        System.out.println("2 - Selon le titre d'une chanson");
                        System.out.println("3 - Selon le titre d'album");
                        System.out.println("4 - Selon un artiste");

                        choix = scanner.nextInt();

                        switch(choix){
                            case 1:
                                System.out.println("Tri selon l'année");
                                ListMusique.sort(Musique.compareAnnee);
                                System.out.println(ListMusique.toString());
                                break;
                            case 2:
                                System.out.println("Tri selon le titre d'une chanson");
                                ListMusique.sort(Musique.compareChanson);
                                System.out.println(ListMusique.toString());
                                break;
                            case 3:
                                System.out.println("Tri selon le titre d'album");
                                ListMusique.sort(Musique.compareAlbum);
                                System.out.println(ListMusique.toString());
                                break;
                            case 4:
                                System.out.println("Tri selon le titre un artiste");
                                ListMusique.sort(Musique.compareArtiste);
                                System.out.println(ListMusique.toString());
                                break;
                        }
                        break;
                }

                break;
            case 4:
                System.out.println("Vous avez choisi de Filter les données");
                System.out.println(" ");
                System.out.println("Quel type de filtre voulez-vous faire ?");
                System.out.println("1 - Filtre manuel");
                System.out.println("2 - Filtre java");

                choix = scanner.nextInt();

                switch(choix){
                    case 1:
                        break;
                    case 2:
                        System.out.println("Quelles donnée voulez-vous filtrer ?");
                        System.out.println(" ");
                        System.out.println("1 - Selon l'année");
                        System.out.println("2 - Selon le titre d'une chanson");
                        System.out.println("3 - Selon le titre d'album");
                        System.out.println("4 - Selon un artiste");

                        choix = scanner.nextInt();

                        switch(choix){
                            case 1:
                                System.out.println("Entrez l'année que vous voulez filtrer : ");
                                String phrase = scanner.nextLine();
                                LocalDate date = LocalDate.parse(phrase);
                                filtreAnnee(ListMusique,date);
                                break;
                            case 2:
                                System.out.println("Entrez la chanson que vous voulez filtrer : ");
                                do{
                                    phrase = scanner.nextLine();
                                }while(phrase.isEmpty());
                                filtreChanson(ListMusique,phrase);
                                break;
                            case 3:
                                System.out.println("Entrez l'album que vous voulez filtrer : ");
                                do{
                                    phrase = scanner.nextLine();
                                }while(phrase.isEmpty());
                                filtreAlbum(ListMusique,phrase);
                                break;
                            case 4:
                                System.out.println("Entrez l'artiste que vous voulez filtrer : ");
                                do{
                                    phrase = scanner.nextLine();
                                }while(phrase.isEmpty());
                                filtreArtiste(ListMusique,phrase);
                                break;
                        }
                        break;
                }

                break;
            case 5:
                System.out.println("Vous avez choisi de Rechercher des données concernant un titre");
                System.out.println(" ");
                System.out.println("Entrez le titre que vous recherchez : ");

                String choix_titre = scanner.nextLine();

                break;
            default:
                System.out.println(" ");
                System.out.println("Erreur : Veuillez saisir un nombre ente 1 et 5");
                break;
        }
        }
    }

    public static void Afficher(){
        System.out.println(ListMusique.toString());
        System.out.println(" ");
    }

    public static void Chargement(String nom) {

        try{
            Path chemin = Paths.get(nom);

            try (Stream<String> lignes = Files.lines(chemin)) {
                nombreLignes = lignes.count();
            } catch (IOException e) {}

            tabPopularite = new int[Math.toIntExact(nombreLignes)];

            int i=0;
            DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'");
            BufferedReader tsvReader = new BufferedReader(new FileReader(nom));
            String row = new String();
            row = tsvReader.readLine(); //row est égal à null si on est en fin de fichier
            String[] titre = row.split(",");
            row = tsvReader.readLine();

            while(row != null){

                String[] data = row.split(",");
                tabPopularite[i] = Integer.parseInt(data[10]);

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
                i++;
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    public static void AfficherTab(int[] tab){
        for(int i = 0; i< tab.length; i++){
            System.out.println(tab[i]);
        }
    }

    public static void triSelection(){
        Musique temp;
        int min;

        for (int i = 0; i<nombreLignes - 1; i++) {
            min = i;
            for (int j = i+1; j<nombreLignes -1 ; j++) if (ListMusique.get(min).popularite > ListMusique.get(j).popularite){ min = j;};
            temp = ListMusique.get(min);
            ListMusique.set(min, ListMusique.get(i));
            ListMusique.set(i, temp);
        }
    }
    /*
    public static void triFusion( int g, int d){
        int m;
        if(g<d) {
            m = (g + d)/2; //division enti�re
            triFusion(g, m);
            triFusion(m+1, d);
            fusionner(g, m, d);
        }
    }

    public static void fusionner ( int g, int m, int d) {
        Musique []tab3 = new int[d-g+1];
        int n1 = m, n2 = d, i1 = g, i2 = m+1, i3 = 0;


        while ((i1 <= n1) && (i2 <= n2)) {
            if (tab[i1] < tab[i2]) {
                tab3[i3] = tab[i1];
                i1++;
            }

            else {
                tab3[i3] = tab[i2];
                i2++;
            }
            i3++;
        }

        while (i1 <= n1){
            tab3[i3] = tab[i1];
            i1++;
            i3++;
        }

        while (i2 <= n2){
            tab3[i3] = tab[i2];
            i2++;
            i3++;
        }

        for (i3 = 0; i3 < tab3.length; i3++) {
            tab[g + i3] = tab3[i3];
        }
    }*/

    public static void filtreAnnee(List<Musique> listMusique, LocalDate date){
        ListMusique.removeIf(f -> f.date_album != date);
        System.out.println(ListMusique.toString());

    }

    public static void filtreAlbum(List<Musique> listMusique, String nom){
        ListMusique.removeIf(f -> !f.nom_Album.contains(nom));
        System.out.println(ListMusique.toString());
    }

    public static void filtreChanson(List<Musique> listMusique, String chanson){
        ListMusique.removeIf(f -> !f.nom_Musique.contains(chanson));
        System.out.println(ListMusique.toString());
    }

    public static void filtreArtiste(List<Musique> listMusique, String artiste){
        ListMusique.removeIf(f -> !f.nom_Artiste.contains(artiste));
        System.out.println(ListMusique.toString());
    }
}
