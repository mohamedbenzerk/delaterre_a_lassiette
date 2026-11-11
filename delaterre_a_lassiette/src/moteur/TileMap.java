/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevIG_MONSTER (IG-1 à IG-4).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Le décor du plateau dessiné avec la technique du tile mapping :
 * tileset.png (tuiles de 32 x 32, 8 par ligne) + carte_plateau.txt (20 lignes de 30 numéros).
 */
public class TileMap implements Dessinable {

    /** Taille d'une tuile, en pixels. */
    public static final int TAILLE_TUILE = 32;

    private BufferedImage tileset;
    private int[][] carte;

    /**
     * Charge le tileset et la carte.
     *
     * @param cheminCarte "resources/tilemap/carte_plateau.txt"
     * @param cheminTileset "resources/tilemap/tileset.png"
     */
    public TileMap(String cheminCarte, String cheminTileset) {
        // TODO IG-1 : lire le fichier texte et l'image avec getClass().getClassLoader()
        this.carte = new int[0][0];
    }

    /** @return largeur du décor en pixels (960) */
    public int getLargeur() {
        return carte.length == 0 ? 0 : carte[0].length * TAILLE_TUILE;
    }

    /** @return hauteur du décor en pixels (640) */
    public int getHauteur() {
        return carte.length * TAILLE_TUILE;
    }

    @Override
    public void rendu(Graphics2D contexte) {
        // TODO IG-1 : dessiner chaque tuile avec tileset.getSubimage((n % 8) * 32, (n / 8) * 32, 32, 32)
    }
}
