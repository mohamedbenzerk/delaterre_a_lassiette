/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;

/**
 * Une case du parcours. Une case occupe 2 x 2 tuiles, soit 64 x 64 pixels.
 * <p>
 * Chaque sous-classe définit son effet dans appliquerEffet() : c'est le
 * polymorphisme demandé par le cours (Jeu ne sait pas quel type de case il manipule).
 */
public abstract class Case implements Dessinable {

    /** Taille d'une case à l'écran, en pixels. */
    public static final int TAILLE = 2 * TileMap.TAILLE_TUILE;

    private static final int MARGE = 3;
    private static final int ARRONDI = 16;
    private static final int TAILLE_ICONE = 32;
    private static final Color FOND = new Color(255, 255, 255, 40);
    private static final Color TEXTE = new Color(30, 30, 30, 200);
    private static final Font POLICE = new Font(Font.SANS_SERIF, Font.PLAIN, 10);
    private static final Stroke TRAIT = new BasicStroke(2f);
    private static final Color BORD_FERME = new Color(255, 250, 230, 230);
    private static final Color BORD_ROUTE = new Color(255, 255, 255, 230);
    private static final Color BORD_CUISINE = new Color(90, 70, 60, 230);

    /** Zone de la case. */
    protected final Zone zone;
    /** Position de la case dans sa zone (0 = première case). */
    protected final int index;
    /** Colonne de la tuile en haut à gauche de la case. */
    protected final int colonne;
    /** Ligne de la tuile en haut à gauche de la case. */
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

    /** @return nom court de la case, par exemple "F3" (Ferme, case 3) */
    public String getEtiquette() {
        return zone.name().charAt(0) + String.valueOf(index);
    }

    /**
     * Applique l'effet de la case quand un pion s'y arrête.
     *
     * @param pion le pion qui vient de s'arrêter sur la case
     * @param jeu la partie en cours
     */
    public abstract void appliquerEffet(Pion pion, Jeu jeu);

    /** @return nom du fichier de l'icône dans resources/icones (par exemple "pioche.png"), ou null */
    protected abstract String getNomIcone();

    /** @return couleur du contour, selon la zone */
    private Color couleurBord() {
        switch (zone) {
            case FERME:
                return BORD_FERME;
            case ROUTE:
                return BORD_ROUTE;
            default:
                return BORD_CUISINE;
        }
    }

    /**
     * Dessine le contour arrondi de la case, son icône au centre et son étiquette.
     *
     * @param contexte contexte graphique
     */
    @Override
    public void rendu(Graphics2D contexte) {
        int x = getX() + MARGE;
        int y = getY() + MARGE;
        int cote = TAILLE - 2 * MARGE - 1;
        contexte.setColor(FOND);
        contexte.fillRoundRect(x, y, cote, cote, ARRONDI, ARRONDI);
        Stroke ancienTrait = contexte.getStroke();
        contexte.setStroke(TRAIT);
        contexte.setColor(couleurBord());
        contexte.drawRoundRect(x, y, cote, cote, ARRONDI, ARRONDI);
        contexte.setStroke(ancienTrait);

        String nomIcone = getNomIcone();
        if (nomIcone != null) {
            BufferedImage icone = Images.charger("resources/icones/" + nomIcone);
            if (icone != null) {
                int decalage = (TAILLE - TAILLE_ICONE) / 2;
                contexte.drawImage(icone, getX() + decalage, getY() + decalage, TAILLE_ICONE, TAILLE_ICONE, null);
            }
        }
        contexte.setFont(POLICE);
        contexte.setColor(TEXTE);
        contexte.drawString(getEtiquette(), getX() + 7, getY() + 15);
    }

    @Override
    public String toString() {
        return getEtiquette();
    }
}
