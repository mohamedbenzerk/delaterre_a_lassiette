/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 * Une carte à jouer (table CARTE). Chaque sous-classe définit son effet
 * dans appliquer() : Jeu.jouerCarte() l'appelle sans savoir quelle carte c'est.
 */
public abstract class Carte {

    protected final int id;
    protected final String nom;
    protected final int valeur;
    private BufferedImage image;

    /**
     * Crée une carte.
     *
     * @param id identifiant en base (id_carte)
     * @param nom nom exact de la carte en base, par exemple "Avance 2"
     * @param valeur nombre de cases pour une carte Mouvement, 0 sinon
     */
    protected Carte(int id, String nom, int valeur) {
        this.id = id;
        this.nom = nom;
        this.valeur = valeur;
    }

    /**
     * Crée la bonne sous-classe à partir des données de la base (utilisée par CarteSQL).
     *
     * @param id identifiant en base
     * @param nom nom exact de la carte en base
     * @param famille "MOUVEMENT", "ATTAQUE" ou "DEFENSE"
     * @param valeur nombre de cases (cartes Mouvement)
     * @return la carte créée
     * @throws IllegalArgumentException si le nom est inconnu
     */
    public static Carte creer(int id, String nom, String famille, int valeur) {
        if ("MOUVEMENT".equals(famille)) {
            return new CarteMouvement(id, nom, valeur);
        }
        switch (nom) {
            case "Sécheresse":
                return new CarteSecheresse(id, nom);
            case "Panne":
                return new CartePanne(id, nom);
            case "Vol":
                return new CarteVol(id, nom);
            case "Appât":
                return new CarteAppat(id, nom);
            case "Bouclier":
                return new CarteBouclier(id, nom);
            case "Épouvantail":
                return new CarteEpouvantail(id, nom);
            default:
                throw new IllegalArgumentException("Carte inconnue : " + nom);
        }
    }

    /** @return identifiant en base */
    public int getId() {
        return id;
    }

    /** @return nom de la carte */
    public String getNom() {
        return nom;
    }

    /** @return nombre de cases (cartes Mouvement), 0 sinon */
    public int getValeur() {
        return valeur;
    }

    /** @return "MOUVEMENT", "ATTAQUE" ou "DEFENSE" */
    public abstract String getFamille();

    /** @return vrai si le joueur doit choisir un adversaire pour jouer cette carte */
    public abstract boolean necessiteCible();

    /**
     * Applique l'effet de la carte.
     *
     * @param joueur pion du joueur qui joue la carte
     * @param cible pion visé (null si la carte n'en demande pas)
     * @param jeu la partie en cours
     */
    public abstract void appliquer(Pion joueur, Pion cible, Jeu jeu);

    /** @return nom du fichier image dans resources/cartes, par exemple "carte_panne.png" */
    protected abstract String getNomFichierImage();

    /** @return image de la carte (100 x 140), chargée une seule fois */
    public BufferedImage getImage() {
        if (image == null) {
            try {
                URL url = getClass().getClassLoader().getResource("resources/cartes/" + getNomFichierImage());
                if (url != null) {
                    image = ImageIO.read(url);
                }
            } catch (IOException e) {
                System.err.println("Image introuvable pour la carte " + nom + " : " + e.getMessage());
            }
        }
        return image;
    }

    @Override
    public String toString() {
        return nom;
    }
}
