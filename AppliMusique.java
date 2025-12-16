package SAE_spotify;

// Importation des bibliothèques nécessaires pour l'interface graphique (Desktop),
// la gestion des entrées/sorties (fichiers), le réseau (URI), les dates et les collections.
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class AppliMusique {

    // Variables globales statiques
    private static String[] titre;          // Tableau pour stocker les en-têtes du fichier CSV
    private static List<Musique> ListMusique; // La liste principale contenant les objets Musique
    private static long start, stop;        // Variables pour mesurer le temps d'exécution (chronomètre)

    public static void main(String[] args) {
        // Variable de configuration pour choisir l'implémentation de la liste
        boolean ARRAYlist = true;
        int choix = -1; // Initialisation du choix de l'utilisateur pour entrer dans la boucle

        // Initialisation de la liste selon le choix (ArrayList est généralement plus rapide pour l'accès, LinkedList pour les ajouts/suppressions en tête/milieu)
        if (ARRAYlist) {
            ListMusique = new ArrayList<>();
        } else {
            ListMusique = new LinkedList<>();
        }

        // Boucle principale du menu (s'arrête si l'utilisateur entre 0)
        while (choix != 0) {
            Scanner scanner = new Scanner(System.in);
            int choix_charge;

            // Affichage du menu principal
            System.out.println("Que voulez vous faire ?");
            System.out.println("1 - Charger les données (choix du fichier)");
            System.out.println("2 - Afficher les données");
            System.out.println("3 - Trier les données");
            System.out.println("4 - Filtrer les données");
            System.out.println("5 - Rechercher des données concernant un titre");

            choix = scanner.nextInt(); // Lecture du choix de l'utilisateur

            switch (choix) {
                // CAS 1 : Chargement des fichiers CSV
                case 1:
                    System.out.println("Vous avez choisi de Charger les données");
                    System.out.println(" ");
                    System.out.println("Quel fichier voulez-vous charger ?");
                    // Sous-menu pour choisir la taille du dataset
                    System.out.println("1 - spotify_100.csv");
                    System.out.println("2 - spotify_1000.csv");
                    System.out.println("3 - spotify_10000.csv");
                    System.out.println("4 - spotify_100000.csv");
                    System.out.println("5 - spotify_FULL.csv");

                    choix_charge = scanner.nextInt();

                    switch (choix_charge) {
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
                
                // CAS 2 : Affichage des données
                case 2:
                    System.out.println("Vous avez choisi d'afficher les données");
                    Afficher();
                    break;

                // CAS 3 : Tris (Algorithmes de tri)
                case 3:
                    System.out.println("Vous avez choisi de Trier les données");
                    System.out.println(" ");
                    System.out.println("Quel type de tri voulez-vous faire ?");
                    System.out.println("1 - Tri Sélection"); // Tri lent O(n^2)
                    System.out.println("2 - Tri Fusion");    // Tri rapide récursif O(n log n)
                    System.out.println("3 - Tri Java");      // Tri optimisé natif (TimSort)

                    choix = scanner.nextInt();

                    switch (choix) {
                        case 1:
                            long start = System.nanoTime(); // Démarrage du chrono
                            triSelection();
                            stop = System.nanoTime();       // Arrêt du chrono
                            Afficher();
                           
                           // Calcul et affichage du temps en millisecondes
                           System.out.println("Temps d'exécution : " + (float) (stop-start)/1000000 + " ms");

                            start = 0;
                            stop = 0;
                            break;
                        case 2:
                            start = System.nanoTime();
                            triFusion(0, (int) (ListMusique.size() - 1)); // Appel récursif initial
                            stop = System.nanoTime();
                            Afficher();
                           
                           System.out.println("Temps d'exécution : " + (float) (stop - start)/1000000 + " ms");
                            break;
                        case 3:
                            // Sous-menu pour le tri natif Java avec Comparators
                            System.out.println("Tri Java : ");
                            System.out.println("Quelles données voulez-vous trier ?");
                            System.out.println(" ");
                            System.out.println("1 - Selon l'année");
                            System.out.println("2 - Selon le titre d'une chanson");
                            System.out.println("3 - Selon le titre d'album");
                            System.out.println("4 - Selon un artiste");

                            choix = scanner.nextInt();

                            switch (choix) {
                                case 1:
                                    System.out.println("Tri selon l'année");
                                    start = System.nanoTime();
                                    ListMusique.sort(Musique.compareAnnee); // Utilise un Comparator défini dans la classe Musique
                                    stop = System.nanoTime();
                                    Afficher();
                                   
                                   System.out.println("Temps d'exécution : " + (float) (stop - start)/1000000 + " ms");
                                    break;
                                case 2:
                                    System.out.println("Tri selon le titre d'une chanson");
                                    start = System.nanoTime();
                                    ListMusique.sort(Musique.compareChanson);
                                    stop = System.nanoTime();
                                    Afficher();
                                   
                                   System.out.println("Temps d'exécution : " + (float) (stop - start)/1000000 + " ms");
                                    break;
                                case 3:
                                    System.out.println("Tri selon le titre d'album");
                                    ListMusique.sort(Musique.compareAlbum);
                                    Afficher();
                                    break;
                                case 4:
                                    System.out.println("Tri selon le titre un artiste");
                                    ListMusique.sort(Musique.compareArtiste);
                                    Afficher();
                                    break;
                            }
                            break;
                    }

                    break;

                // CAS 4 : Filtrage des données
                case 4:
                    System.out.println("Vous avez choisi de Filter les données");
                    System.out.println(" ");
                    System.out.println("Quel type de filtre voulez-vous faire ?");
                    System.out.println("1 - Filtre manuel"); // Crée une nouvelle liste
                    System.out.println("2 - Filtre java");   // Modifie la liste actuelle (supprime les éléments non désirés)

                    choix = scanner.nextInt();

                    switch (choix) {
                        case 1:
                            System.out.println("Entrez l'année que vous voulez filtrer : ");
                            choix = 0;
                            do {
                                choix = scanner.nextInt();
                            } while (choix == 0); // Validation sommaire
                            start = System.nanoTime();
                            filtreManuel(choix);
                            stop = System.nanoTime();
                           
                           System.out.println("Temps d'exécution : " + (float) (stop - start)/1000000 + " ms");
                            break;
                        case 2:
                            System.out.println("Quelles donnée voulez-vous filtrer ?");
                            System.out.println(" ");
                            System.out.println("1 - Selon l'année");
                            System.out.println("2 - Selon le titre d'une chanson");
                            System.out.println("3 - Selon le titre d'album");
                            System.out.println("4 - Selon un artiste");

                            choix = scanner.nextInt();

                            switch (choix) {
                                case 1:
                                    System.out.println("Entrez l'année que vous voulez filtrer : ");
                                    choix = 0;
                                    do {
                                        choix = scanner.nextInt();
                                    } while (choix == 0);
                                    start = System.nanoTime();
                                    filtreAnnee(ListMusique, choix); // Supprime tout ce qui n'est pas de l'année
                                    stop = System.nanoTime();
                                   
                                   System.out.println("Temps d'exécution : " + (float) (stop - start)/1000000 + " ms");
                                    break;
                                case 2:
                                    System.out.println("Entrez la chanson que vous voulez filtrer : ");
                                    String phrase;
                                    do {
                                        phrase = scanner.nextLine();
                                    } while (phrase.isEmpty());
                                    start = System.nanoTime();
                                    filtreChanson(ListMusique, phrase);
                                    stop = System.nanoTime();
                                   
                                   System.out.println("Temps d'exécution : " + (float) (stop - start)/1000000 + " ms");
                                    break;
                                case 3:
                                    System.out.println("Entrez l'album que vous voulez filtrer : ");
                                    do {
                                        phrase = scanner.nextLine();
                                    } while (phrase.isEmpty());
                                    filtreAlbum(ListMusique, phrase);
                                    break;
                                case 4:
                                    System.out.println("Entrez l'artiste que vous voulez filtrer : ");
                                    do {
                                        phrase = scanner.nextLine();
                                    } while (phrase.isEmpty());
                                    filtreArtiste(ListMusique, phrase);
                                    break;
                            }
                            break;
                    }

                    break;

                // CAS 5 : Recherche de musique
                case 5:
                    System.out.println("Vous avez choisi de Rechercher des données concernant un titre");
                    System.out.println(" ");
                    System.out.println("Quel type de recherche voulez-vous faire ?");
                    System.out.println("1 - Recherche linéaire");       // Parcours simple O(n)
                    System.out.println("2 - Recherche dichotomique");   // Recherche optimisée O(log n), nécessite tri

                    choix = scanner.nextInt();

                    switch(choix) {
                        case 1:
                            System.out.println("Saisissez le titre de la musique recherchée");
                            String phrase = "";
                            do {
                                phrase = scanner.nextLine();
                            } while (phrase.isEmpty());
                            start = System.nanoTime();
                            RechercheLineaire(phrase);
                           
                           System.out.println("Temps d'exécution : " + (float) (stop - start)/1000000 + " ms");
                            break;
                        case 2:
                            phrase = "";
                            // Avertissement utilisateur : la dichotomie ne marche que si c'est trié
                            System.out.println("Pour faire une recherche Dichotomique, veuillez vous assurer que la liste est triée par titre de musique");
                            System.out.println("1 - La liste est triée");
                            System.out.println("0 - La liste n'est pas triée");
                            do {
                                choix = scanner.nextInt();
                            }while(choix== 2);
                            
                            if(choix==1){
                                System.out.println("Saisissez le titre de la musique recherchée");
                                do {
                                    phrase = scanner.nextLine();
                                } while (phrase.isEmpty());
                                start = System.nanoTime();
                                RechercheDichotomique(phrase);
                               
                               System.out.println("Temps d'exécution : " + (float) (stop - start)/1000000 + " ms");
                            }
                            break;
                    }
                    break;
                
                // CAS 10 (Caché) : Test de suppression lente
                case 10:
                    start = System.nanoTime();
                    SuppressionDUrgenceLente();
                    stop = System.nanoTime();
                   
                   System.out.println("Temps d'exécution : " + (float) (stop - start)/1000000 + " ms");
                    break;
                default:
                    System.out.println(" ");
                    System.out.println("Erreur : Veuillez saisir un nombre ente 1 et 5");
                    break;
            }

        }


    }


    /**
     * Charge le fichier CSV spécifié et remplit la liste ListMusique.
     * @param nom Le nom du fichier à charger.
     */
    public static void Chargement(String nom) {
        SuppressionDUrgenceLente(); // Vide la liste actuelle avant de charger
        try {
            // Format de date attendu dans le fichier CSV
            DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'");
            BufferedReader tsvReader = new BufferedReader(new FileReader(nom));
            String row = new String();
            
            row = tsvReader.readLine(); // Lecture de la première ligne (en-têtes)
            //row est égal à null si on est en fin de fichier
            titre = row.split(","); // prends toutes les categories meme les pas necessaires

            row = tsvReader.readLine(); // Lecture de la première ligne de données

            while (row != null) {

                String[] data = row.split(","); // Découpage de la ligne par virgule

                // Création de l'objet Musique avec conversion des types (String -> int, String -> Date)
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

                ListMusique.add(musique); // Ajout à la liste
                row = tsvReader.readLine(); // Passage à la ligne suivante
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Effectue une recherche binaire (dichotomique) sur le titre de la musique.
     * Nécessite que la liste soit triée par titre au préalable.
     */
    private static void RechercheDichotomique(String phrase) {
        int debut = 0;
        int fin = ListMusique.size() - 1;
        int m = 0;
        boolean trouve = false;
        int indexTrouve = -1;

        // Boucle de recherche : on divise l'espace de recherche par 2 à chaque itération
        while (debut <= fin) {
            m = (debut + fin) / 2;
            String nomCourant = ListMusique.get(m).nom_Musique;

            // Comparaison lexicographique
            int resultat = nomCourant.compareToIgnoreCase(phrase);

            if (resultat == 0) {
                trouve = true; // Élément trouvé
                indexTrouve = m;
                break;
            }
            else if (resultat < 0) {
                debut = m + 1; // Chercher dans la moitié supérieure
            }
            else {
                fin = m - 1; // Chercher dans la moitié inférieure
            }
        }
        stop = System.nanoTime();
        
        // Si trouvé, proposer d'ouvrir le lien Spotify
        if (trouve) {
            Musique musiqueTrouvee = ListMusique.get(indexTrouve);
            System.out.println("Musique trouvée à l'index : " + indexTrouve);
            System.out.println(musiqueTrouvee);

            System.out.println(" ");
            System.out.println("Voulez-vous écouter ce morceau ?");
            System.out.println("0 - Non");
            System.out.println("1 - Oui");

            Scanner scanner = new Scanner(System.in);
            int choix;
            do {
                choix = scanner.nextInt();
            } while (choix != 0 && choix != 1);

            if (choix == 1) {
                // Ouverture du navigateur par défaut
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    try {
                        URI uri = new URI("https://open.spotify.com/intl-fr/track/" + musiqueTrouvee.id_Musique);
                        Desktop.getDesktop().browse(uri);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("La recherche n'a pas été concluante");

        }
    }

    /**
     * Recherche linéaire : parcourt toute la liste du début à la fin.
     * Plus lent que la dichotomie pour les grandes listes, mais ne nécessite pas de tri.
     */
    private static void RechercheLineaire(String titre) {
        int max = ListMusique.size();
        boolean trouve = false;
        Musique musiqueTrouvee = null;
        for (int i = 0; i < max; i++) {
            Musique musique = ListMusique.get(i);
            if (musique.nom_Musique.equalsIgnoreCase(titre)) {
                musiqueTrouvee = musique;
                trouve = true;
                break; // On arrête dès qu'on trouve
            }
        }
        stop = System.nanoTime();
        if (trouve) {
            System.out.println(musiqueTrouvee);
            System.out.println(" ");
            System.out.println("Voulez-vous lire le morceau ?");
            System.out.println("0 - Non");
            System.out.println("1 - Oui");

            Scanner scanner = new Scanner(System.in);
            int choix;
            do {
                choix = scanner.nextInt();
            } while (choix != 0 && choix != 1);

            if (choix == 1) {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    try {
                        URI uri = new URI("https://open.spotify.com/track/" + musiqueTrouvee.id_Musique);
                        Desktop.getDesktop().browse(uri);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("La recherche n'a pas été concluante");
        }
    }

    /**
     * Vide la liste élément par élément. 
     * Nommé "Lente" car retirer le dernier élément en boucle est moins efficace que .clear().
     */
    private static void SuppressionDUrgenceLente() {
        int i = 0;

        while (!ListMusique.isEmpty()) {
            ListMusique.remove(ListMusique.size() - 1); // Supprime le dernier élément
            i++;
        }
        System.out.println(i + " lignes supprimées avec succès");
    }

    /**
     * Affiche le contenu de la liste.
     * Pour ne pas surcharger la console, n'affiche qu'un échantillon si la liste est grande.
     */
    public static void Afficher() {
        // Si la liste est vide, on arrête tout de suite pour éviter des erreurs
        if (ListMusique.isEmpty()) {
            System.out.println("La liste est vide.");
            return;
        }

        int compteur = 0;
        // Affiche 1 ligne sur 1000 si la liste est grande, sinon tout
        int step = (ListMusique.size() > 1000) ? 1000 : 1;

        // CORRECTION 1 : Simplification drastique
        int max = ListMusique.size();

        // Affichage des en-têtes (Attention : titre[] doit être initialisé avant !)
        if (titre != null && titre.length > 10) {
            // Utilisation de printf pour un formatage en colonnes alignées
            System.out.printf(
                    "%-150s %-25s %-15s %-40s %-150s %-40s %-30s %-20s%n",
                    titre[0], titre[1], titre[3], titre[4], titre[7], titre[8], titre[9], titre[10]
            );
        }

        for (int i = 0; i < max; i += step) {
            Musique m = ListMusique.get(i); // On récupère l'objet une seule fois

            System.out.printf("%-150s %-25s %-15d %-40s %-150s %-40s %-30s %-20d%n",
                    m.nom_Musique,
                    m.id_Musique,
                    m.duree_titre,
                    m.type_album,
                    m.nom_Album,
                    m.date_album,
                    m.nom_Artiste,
                    m.popularite
            );
            compteur++;
        }

        System.out.println(" ");
        System.out.println(compteur + " lignes affichées (Echantillon) sur " + ListMusique.size() + " au total.");
    }

    /**
     * Algorithme de Tri par Sélection (Selection Sort).
     * Trie la liste selon la popularité.
     */
    public static void triSelection() {
        Musique temp;
        int min;

        // Parcourt la liste pour trouver le plus petit élément restant et le place au début
        for (int i = 0; i < ListMusique.size() - 1; i++) {
            min = i;
            for (int j = i + 1; j < ListMusique.size() - 1; j++)
                if (ListMusique.get(min).popularite > ListMusique.get(j).popularite) {
                    min = j;
                }
            ;
            // Échange des éléments
            temp = ListMusique.get(min);
            ListMusique.set(min, ListMusique.get(i));
            ListMusique.set(i, temp);
        }
    }

    /**
     * Algorithme de Tri Fusion (Merge Sort) - Partie récursive.
     * Divise la liste en deux sous-parties.
     */
    public static void triFusion(int g, int d) {
        int m;
        if (g < d) {
            m = (g + d) / 2; //division entire (trouver le milieu)
            triFusion(g, m);      // Tri partie gauche
            triFusion(m + 1, d);  // Tri partie droite
            fusionner(g, m, d);   // Fusion des deux parties triées
        }
    }

    /**
     * Fusionne deux sous-listes triées en une seule.
     */
    public static void fusionner(int g, int m, int d) {
        Musique[] tab3 = new Musique[d - g + 1];
        int n1 = m, n2 = d, i1 = g, i2 = m + 1, i3 = 0;

        // Comparaison et fusion des éléments
        while ((i1 <= n1) && (i2 <= n2)) {
            if (ListMusique.get(i1).popularite < ListMusique.get(i2).popularite) {
                tab3[i3] = ListMusique.get(i1);
                i1++;
            } else {
                tab3[i3] = ListMusique.get(i2);
                i2++;
            }
            i3++;
        }

        // Copie des éléments restants du premier tableau
        while (i1 <= n1) {
            tab3[i3] = ListMusique.get(i1);
            i1++;
            i3++;
        }

        // Copie des éléments restants du deuxième tableau
        while (i2 <= n2) {
            tab3[i3] = ListMusique.get(i2);
            i2++;
            i3++;
        }

        // Remet les éléments triés dans la liste originale
        for (i3 = 0; i3 < tab3.length; i3++) {
            ListMusique.set(i3 + g, tab3[i3]);
        }
    }

    // --- Méthodes de filtrage utilisant les lambdas (programmation fonctionnelle) ---

    // Supprime de la liste principale tout ce qui ne correspond pas à l'année donnée
    public static void filtreAnnee(List<Musique> listMusique, int date) {
        ListMusique.removeIf(f -> f.date_album.getYear() != date);
        Afficher();
    }

    // Supprime si le nom de l'album ne contient pas la chaîne cherchée
    public static void filtreAlbum(List<Musique> listMusique, String nom) {
        ListMusique.removeIf(f -> !f.nom_Album.contains(nom));
        Afficher();
    }

    // Supprime si le titre ne contient pas la chaîne cherchée
    public static void filtreChanson(List<Musique> listMusique, String chanson) {
        ListMusique.removeIf(f -> !f.nom_Musique.contains(chanson));
        Afficher();
    }

    // Supprime si l'artiste ne contient pas la chaîne cherchée
    public static void filtreArtiste(List<Musique> listMusique, String artiste) {
        ListMusique.removeIf(f -> !f.nom_Artiste.contains(artiste));
        Afficher();
    }

    /**
     * Filtre manuel : Crée une nouvelle liste au lieu de supprimer dans l'ancienne.
     * Cette méthode n'affecte pas ListMusique directement pour les opérations futures, 
     * elle affiche juste le résultat.
     */
    public static void filtreManuel(int date) {
        List<Musique> listeFiltree = new ArrayList<>();
        int max = ListMusique.size();

        for (int i = 0; i < max; i++) {
            Musique musique = ListMusique.get(i);
            int annee = musique.date_album.getYear();
            if (annee == date) listeFiltree.add(musique);
        }
        System.out.println(listeFiltree);
        System.out.println("");
    }
}
