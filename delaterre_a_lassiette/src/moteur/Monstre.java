/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevIG_MONSTER (IG-1 à IG-4).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

import java.awt.Graphics2D;

/**
 * Un monstre (PNJ). Le déplacement est écrit une seule fois ici ;
 * chaque monstre écrit seulement son effet dans agir() (polymorphisme).
 */
public abstract class Monstre implements Dessinable, Animable {

    protected final Zone zone;
    protected final int xRepos;
    protected final int yRepos;
    protected double x;
    protected double y;
    protected Pion cible;

    /**
     * Crée un monstre à sa place de repos.
     *
     * @param zone zone où le monstre agit
     * @param xRepos abscisse de repos en pixels
     * @param yRepos ordonnée de repos en pixels
     * @param cheminImage image du monstre, par exemple "resources/sprites/sanglier.png"
     */
    protected Monstre(Zone zone, int xRepos, int yRepos, String cheminImage) {
        this.zone = zone;
        this.xRepos = xRepos;
        this.yRepos = yRepos;
        this.x = xRepos;
        this.y = yRepos;
        // TODO IG-3 : charger l'image
    }

    /** @return zone où le monstre agit */
    public Zone getZone() {
        return zone;
    }

    /** @return "SANGLIER", "TEMPETE" ou "RAT" */
    public abstract String getType();

    /**
     * Lance l'animation jusqu'au pion visé, puis le retour à la place de repos.
     *
     * @param cible pion visé
     */
    public void seDeplacerVers(Pion cible) {
        this.cible = cible;
        // TODO IG-3
    }

    /**
     * Applique l'effet du monstre sur la cible (appelé quand il arrive sur sa case).
     *
     * @param cible pion visé
     * @param jeu la partie en cours
     */
    public abstract void agir(Pion cible, Jeu jeu);

    /** @return abscisse du monstre en pixels */
    public int getX() {
        return (int) x;
    }

    /** @return ordonnée du monstre en pixels */
    public int getY() {
        return (int) y;
    }

    @Override
    public void miseAJour() {
        // TODO IG-3
    }

    @Override
    public boolean estEnMouvement() {
        // TODO IG-3
        return false;
    }

    @Override
    public void rendu(Graphics2D contexte) {
        // TODO IG-3
    }
}
