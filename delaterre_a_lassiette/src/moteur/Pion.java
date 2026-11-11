/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Le pion d'un joueur (table PION). Sur la Route, il est dessiné comme un camion.
 */
public class Pion implements Dessinable, Animable {

    private final int id;
    private final String pseudo;
    private final String couleur;
    private final int ordre;
    private Plateau plateau;
    private Zone zone;
    private int indexCase;
    private Recette recette;
    private final List<Ingredient> inventaire;
    private final List<Carte> main;
    private int toursBloques;
    private boolean protegeCarte;
    private int toursEpouvantail;
    private boolean cibleAppat;
    private boolean pret;
    private int rang;
    private int score;
    private double x;
    private double y;

    /**
     * Crée un pion sur la case Départ.
     *
     * @param id identifiant en base (id_pion)
     * @param pseudo pseudo du joueur
     * @param couleur "BLEU", "ROUGE", "VERT" ou "JAUNE"
     * @param ordre ordre de jeu, de 1 à 4 (1 = hôte)
     */
    public Pion(int id, String pseudo, String couleur, int ordre) {
        this.id = id;
        this.pseudo = pseudo;
        this.couleur = couleur;
        this.ordre = ordre;
        this.zone = Zone.FERME;
        this.indexCase = 0;
        this.inventaire = new ArrayList<>();
        this.main = new ArrayList<>();
    }

    // ----------------------------------------------------------- getters / setters

    /** @return identifiant en base */
    public int getId() {
        return id;
    }

    /** @return pseudo du joueur */
    public String getPseudo() {
        return pseudo;
    }

    /** @return couleur : "BLEU", "ROUGE", "VERT" ou "JAUNE" */
    public String getCouleur() {
        return couleur;
    }

    /** @return ordre de jeu, de 1 à 4 */
    public int getOrdre() {
        return ordre;
    }

    /** @return zone où se trouve le pion */
    public Zone getZone() {
        return zone;
    }

    /** @return position du pion dans sa zone */
    public int getIndexCase() {
        return indexCase;
    }

    /**
     * Place directement le pion (sans animation), par exemple au chargement depuis la base.
     *
     * @param zone zone de la case
     * @param indexCase position dans la zone
     */
    public void setPosition(Zone zone, int indexCase) {
        this.zone = zone;
        this.indexCase = indexCase;
    }

    /** @param plateau plateau sur lequel le pion se déplace */
    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
    }

    /** @return recette choisie (null avant le choix) */
    public Recette getRecette() {
        return recette;
    }

    /** @param recette recette choisie par le joueur */
    public void setRecette(Recette recette) {
        this.recette = recette;
    }

    /** @return ingrédients déjà récoltés */
    public List<Ingredient> getInventaire() {
        return inventaire;
    }

    /** @return les cartes en main (index 0 à 2 = emplacement 1 à 3 en base) */
    public List<Carte> getMain() {
        return main;
    }

    /** @return nombre de tours que le pion doit encore passer */
    public int getToursBloques() {
        return toursBloques;
    }

    /** @param n nombre de tours à passer */
    public void setToursBloques(int n) {
        this.toursBloques = n;
    }

    /** @return vrai si un Bouclier protège le pion de la prochaine attaque */
    public boolean isProtegeCarte() {
        return protegeCarte;
    }

    /** @param b vrai pour activer le Bouclier */
    public void setProtegeCarte(boolean b) {
        this.protegeCarte = b;
    }

    /** @return nombre de tours de protection contre le monstre (Épouvantail) */
    public int getToursEpouvantail() {
        return toursEpouvantail;
    }

    /** @param n nombre de tours de protection contre le monstre */
    public void setToursEpouvantail(int n) {
        this.toursEpouvantail = n;
    }

    /** @return vrai si une carte Appât envoie le monstre sur ce pion */
    public boolean isCibleAppat() {
        return cibleAppat;
    }

    /** @param b vrai si le pion est visé par une carte Appât */
    public void setCibleAppat(boolean b) {
        this.cibleAppat = b;
    }

    /** @return vrai si le joueur est prêt (salle d'attente) */
    public boolean isPret() {
        return pret;
    }

    /** @param b vrai si le joueur est prêt */
    public void setPret(boolean b) {
        this.pret = b;
    }

    /** @return rang à l'arrivée (0 tant que le plat n'est pas fini) */
    public int getRang() {
        return rang;
    }

    /** @param rang rang à l'arrivée (1 à 4) */
    public void setRang(int rang) {
        this.rang = rang;
    }

    /** @return score du joueur */
    public int getScore() {
        return score;
    }

    /** @param score score du joueur */
    public void setScore(int score) {
        this.score = score;
    }

    /** @return abscisse du pion à l'écran, en pixels (animée) */
    public int getX() {
        return (int) x;
    }

    /** @return ordonnée du pion à l'écran, en pixels (animée) */
    public int getY() {
        return (int) y;
    }

    // ----------------------------------------------------------- règles du pion

    /**
     * Ajoute un ingrédient à l'inventaire.
     *
     * @param ingredient ingrédient récolté
     */
    public void ajouterIngredient(Ingredient ingredient) {
        inventaire.add(ingredient);
    }

    /**
     * Retire un ingrédient de l'inventaire (Sanglier, carte Vol).
     *
     * @return l'ingrédient perdu, ou null si l'inventaire est vide
     */
    public Ingredient perdreIngredient() {
        // TODO MO-3 : retirer un ingrédient au hasard
        return null;
    }

    /** @return vrai si le pion a les 4 ingrédients de sa recette */
    public boolean aTousLesIngredients() {
        // TODO MO-2
        return false;
    }

    /**
     * Fait avancer le pion (boucle de la Ferme, sortie par la Grange, Route, Cuisine).
     *
     * @param nbCases nombre de cases
     */
    public void avancer(int nbCases) {
        // TODO MO-1
    }

    /**
     * Fait reculer le pion dans sa zone.
     *
     * @param nbCases nombre de cases
     */
    public void reculer(int nbCases) {
        // TODO MO-1
    }

    /** @return valeur qui mesure l'avance du pion (sert à trouver le joueur en tête) */
    public int progression() {
        // TODO MO-2 : zone, case, nombre d'ingrédients
        return 0;
    }

    /** @return vrai si le pion a atteint l'Assiette */
    public boolean estFini() {
        return rang > 0;
    }

    // ----------------------------------------------------------- animation et dessin

    @Override
    public void miseAJour() {
        // TODO MO-2 : rapprocher x, y de la case visée (case par case)
    }

    @Override
    public boolean estEnMouvement() {
        // TODO MO-2
        return false;
    }

    @Override
    public void rendu(Graphics2D contexte) {
        // TODO MO-2 : pion_<couleur>.png, ou camion_<couleur>.png sur la Route
    }

    @Override
    public String toString() {
        return pseudo + " (" + couleur + ")";
    }
}
