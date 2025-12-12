package SAE_spotify;

import java.time.LocalDate;
import java.util.Comparator;

public class Musique {
    public String nom_Musique;
    public String id_Musique;
    public int duree_titre;
    public String type_album;
    public String nom_Album;
    public LocalDate date_album;
    public String nom_Artiste;
    public int popularite;
    public String artiste;

    public Musique (String titre, String id_titre, int duree, String type, String nomAl, LocalDate date, String nomArt, int pop, String art){
            nom_Musique = titre;
            id_Musique = id_titre;
            duree_titre = duree;
            type_album = type;
            nom_Album = nomAl;
            date_album = date;
            nom_Artiste = nomArt;
            setPopularite(pop);
            artiste = art;
    }

    static public Comparator<Musique> compareAnnee = new Comparator<Musique>() {
        public int compare(Musique m1, Musique m2) {
            return m1.date_album.compareTo(m2.date_album);
        }
    };

    static public Comparator<Musique> compareChanson = new Comparator <Musique>() {
        public int compare(Musique m1, Musique m2) {
            return m1.nom_Musique.compareToIgnoreCase(m2.nom_Musique);
        }
    };

    static public Comparator<Musique> compareAlbum = new Comparator <Musique>() {
        public int compare(Musique m1, Musique m2) {
            return m1.nom_Album.compareToIgnoreCase(m2.nom_Album);
        }
    };

    static public Comparator<Musique> compareArtiste = new Comparator <Musique>() {
        public int compare(Musique m1, Musique m2) {
            return m1.nom_Artiste.compareToIgnoreCase(m2.nom_Artiste);
        }
    };

    public String toString()
    {
        return "\n "+nom_Musique + ", " +
            nom_Artiste + ", " +
            nom_Album + ", " +
            date_album + ", " +
            duree_titre + " sec, " +
                getPopularite();
    }

    public int getPopularite() {
        return popularite;
    }

    public void setPopularite(int popularite) {
        this.popularite = popularite;
    }
}

    
