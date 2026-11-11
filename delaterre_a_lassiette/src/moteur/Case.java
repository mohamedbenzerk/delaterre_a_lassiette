/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

import java.awt.Graphics2D;

/**
 * Une case du parcours. Une case occupe 2 x 2 tuiles, soit 64 x 64 pixels.
 * <p>
 * Chaque sous-classe définit son effet dans appliquerEffet() : c'est le
 * polymorphisme demandé par le cours (Jeu ne sait pas quel type de case il manipule).
 */
public abstract class Case implements Dessinable {

    /** Taille d'une case à l'écran, en pixels. */
    public static final int TAILLE = 2 * TileMap.TAILLE_TUILE;

    protected final Zone zone;
    protected final int index;
    protected final int colonne;
    protected final int ligne;

    /**
     * Crée une case.
     *
     * @param zone zone de la case
     * @param index position de la case dans sa zone (0 = première case)
     * @param colonne colonne de la tuile en haut à gauche (fichier parcours.txt)
     * @param ligne ligne de la tuile en haut à gauche (fichier parcours.txt)
     */
    protected Case(Zone zone, int index, int colonne, int ligne) {
        this.zone = zone;
        this.index = index;
        this.colonne = colonne;
        this.ligne = ligne;
    }

    /** @return zone de la case */
    public Zone getZone() {
        return zone;
    }

    /** @return position de la case dans sa zone */
    public int getIndex() {
        return index;
    }

    /** @return abscisse en pixels du coin haut-gauche de la case */
    public int getX() {
        return colonne * TileMap.TAILLE_TUILE;
    }

    /** @return ordonnée en pixels du coin haut-gauche de la case */
    public int getY() {
        return ligne * TileMap.TAILLE_TUILE;
    }

    /**
     * Applique l'effet de la case quand un pion s'y arrête.
     *
     * @param pion le pion qui vient de s'arrêter sur la case
     * @param jeu la partie en cours
     */
    public abstract void appliquerEffet(Pion pion, Jeu jeu);

    /**
     * Dessine le contour de la case et son icône.
     *
     * @param contexte contexte graphique
     */
    @Override
    public void rendu(Graphics2D contexte) {
        // TODO MO-2 : contour arrondi de 64 x 64 + icône 32 x 32 au centre
    }
}
