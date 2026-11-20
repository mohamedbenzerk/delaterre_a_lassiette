/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Le pion d'un joueur (table PION). Sur la Route, il est dessiné comme un camion.
 * <p>
 * La position logique (zone, indexCase) change tout de suite ; la position à l'écran
 * (x, y) suit case par case grâce à miseAJour() : c'est l'animation.
 */
public class Pion implements Dessinable, Animable {

    /** Vitesse de l'animation, en pixels par image (une case de 64 px en 8 images). */
    private static final int VITESSE = 8;
    /** Taille des images pion_xxx.png et camion_xxx.png. */
    private static final int TAILLE_SPRITE = 24;
    /** Décalage du pion dans son quart de case. */
    private static final int DECALAGE = 4;
    /** Sécurité : nombre maximum de pas pour une animation entre deux cases. */
    private static final int PAS_MAX = 60;
    private static final Random HASARD = new Random();

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
    private Zone zoneAffichee;
    private final Deque<Etape> chemin;

    /**
     * Une étape de l'animation : le point à atteindre à l'écran et la zone de la case.
     */
    private static final class Etape {

        private final int x;
        private final int y;
        private final Zone zone;

        private Etape(int x, int y, Zone zone) {
            this.x = x;
            this.y = y;
            this.zone = zone;
        }
    }

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
        this.zoneAffichee = Zone.FERME;
        this.inventaire = new ArrayList<>();
        this.main = new ArrayList<>();
        this.chemin = new ArrayDeque<>();
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
        placerSansAnimation();
    }

    /**
     * Donne le plateau au pion (obligatoire avant avancer() et reculer()) et le place sur sa case.
     *
     * @param plateau plateau sur lequel le pion se déplace
     */
    public void setPlateau(Plateau plateau) {
        this.plateau = plateau;
        placerSansAnimation();
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
        return (int) Math.round(x);
    }

    /** @return ordonnée du pion à l'écran, en pixels (animée) */
    public int getY() {
        return (int) Math.round(y);
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
     * Retire un ingrédient de l'inventaire, choisi au hasard (Sanglier, carte Vol).
     *
     * @return l'ingrédient perdu, ou null si l'inventaire est vide
     */
    public Ingredient perdreIngredient() {
        if (inventaire.isEmpty()) {
            return null;
        }
        return inventaire.remove(HASARD.nextInt(inventaire.size()));
    }

    /** @return vrai si le pion a les 4 ingrédients de sa recette */
    public boolean aTousLesIngredients() {
        return recette != null && inventaire.containsAll(recette.getIngredients());
    }

    /**
     * Fait avancer le pion case par case : boucle de la Ferme, sortie par la Grange
     * (seulement avec les 4 ingrédients), Route, puis Cuisine jusqu'à l'Assiette.
     * Le pion s'arrête sur l'Assiette même s'il lui reste des cases à faire.
     *
     * @param nbCases nombre de cases
     * @throws IllegalStateException si setPlateau() n'a pas été appelé
     */
    public void avancer(int nbCases) {
        verifierPlateau();
        if (estFini()) {
            return;
        }
        boolean sortieAutorisee = aTousLesIngredients();
        for (int pas = 0; pas < nbCases && !estSurAssiette(); pas++) {
            avancerDUneCase(sortieAutorisee);
        }
    }

    /**
     * Fait reculer le pion dans sa zone : dans la Ferme, il tourne dans la boucle ;
     * sur la Route et dans la Cuisine, il s'arrête sur la première case de la zone.
     *
     * @param nbCases nombre de cases
     * @throws IllegalStateException si setPlateau() n'a pas été appelé
     */
    public void reculer(int nbCases) {
        verifierPlateau();
        if (estFini()) {
            return;
        }
        int taille = plateau.nbCases(zone);
        for (int pas = 0; pas < nbCases; pas++) {
            if (zone == Zone.FERME) {
                allerSur(Zone.FERME, (indexCase - 1 + taille) % taille);
            } else if (indexCase > 0) {
                allerSur(zone, indexCase - 1);
            }
        }
    }

    /**
     * Déplace le pion jusqu'à une case avec l'animation (sert quand un autre poste
     * a changé la position dans la base). Le chemin le plus court est choisi.
     *
     * @param zoneCible zone d'arrivée
     * @param indexCible position d'arrivée dans la zone
     */
    public void allerA(Zone zoneCible, int indexCible) {
        if (zoneCible == zone && indexCible == indexCase) {
            return;
        }
        if (plateau == null || plateau.getCase(zoneCible, indexCible) == null
                || zoneCible.ordinal() < zone.ordinal()) {
            setPosition(zoneCible, indexCible);
            return;
        }
        if (zoneCible == zone && zone == Zone.FERME) {
            int taille = plateau.nbCases(Zone.FERME);
            int enAvant = (indexCible - indexCase + taille) % taille;
            int sens = enAvant <= taille - enAvant ? 1 : -1;
            while (indexCase != indexCible) {
                allerSur(Zone.FERME, (indexCase + sens + taille) % taille);
            }
        } else if (zoneCible == zone) {
            int sens = indexCible > indexCase ? 1 : -1;
            while (indexCase != indexCible) {
                allerSur(zone, indexCase + sens);
            }
        } else {
            for (int pas = 0; pas < PAS_MAX && (zone != zoneCible || indexCase != indexCible); pas++) {
                avancerDUneCase(true);
            }
            if (zone != zoneCible || indexCase != indexCible) {
                setPosition(zoneCible, indexCible);
            }
        }
    }

    /**
     * Recopie l'état d'un pion relu dans la base (synchronisation entre les postes).
     * Si la position a changé, le pion s'y rend avec l'animation.
     *
     * @param source le même pion, relu dans la base
     */
    public void copierEtat(Pion source) {
        recette = source.recette;
        inventaire.clear();
        inventaire.addAll(source.inventaire);
        if (!memesCartes(main, source.main)) {
            // Les objets Carte ne sont remplacés que si la main a vraiment changé :
            // une carte déjà sélectionnée dans le panneau du joueur reste valable.
            main.clear();
            main.addAll(source.main);
        }
        toursBloques = source.toursBloques;
        protegeCarte = source.protegeCarte;
        toursEpouvantail = source.toursEpouvantail;
        cibleAppat = source.cibleAppat;
        pret = source.pret;
        rang = source.rang;
        score = source.score;
        allerA(source.zone, source.indexCase);
    }

    /**
     * Mesure l'avance du pion (sert à trouver le joueur en tête et à classer le dernier).
     * Ferme : 20 points par ingrédient + la case ; Route : 100 + la case ;
     * Cuisine : 200 + la case ; plat fini : 1000 - rang.
     *
     * @return valeur d'autant plus grande que le pion est en avance
     */
    public int progression() {
        if (estFini()) {
            return 1000 - rang;
        }
        switch (zone) {
            case FERME:
                return 20 * inventaire.size() + indexCase;
            case ROUTE:
                return 100 + indexCase;
            default:
                return 200 + indexCase;
        }
    }

    /** @return vrai si le pion a atteint l'Assiette et reçu son rang */
    public boolean estFini() {
        return rang > 0;
    }

    /**
     * Compare deux mains carte par carte (même identifiant, même ordre).
     *
     * @return vrai si les deux mains contiennent les mêmes cartes
     */
    private static boolean memesCartes(List<Carte> a, List<Carte> b) {
        if (a.size() != b.size()) {
            return false;
        }
        for (int i = 0; i < a.size(); i++) {
            if (a.get(i).getId() != b.get(i).getId()) {
                return false;
            }
        }
        return true;
    }

    /** @return vrai si le pion est sur la dernière case de la Cuisine (l'Assiette) */
    private boolean estSurAssiette() {
        return zone == Zone.CUISINE && indexCase == plateau.nbCases(Zone.CUISINE) - 1;
    }

    /**
     * Avance d'une seule case.
     *
     * @param sortieAutorisee vrai si le pion peut sortir de la Ferme par la Grange
     */
    private void avancerDUneCase(boolean sortieAutorisee) {
        int derniere = plateau.nbCases(zone) - 1;
        switch (zone) {
            case FERME:
                if (indexCase == plateau.getIndexGrange() && sortieAutorisee) {
                    allerSur(Zone.ROUTE, 0);
                } else {
                    allerSur(Zone.FERME, (indexCase + 1) % (derniere + 1));
                }
                break;
            case ROUTE:
                if (indexCase == derniere) {
                    allerSur(Zone.CUISINE, 0);
                } else {
                    allerSur(Zone.ROUTE, indexCase + 1);
                }
                break;
            default:
                if (indexCase < derniere) {
                    allerSur(Zone.CUISINE, indexCase + 1);
                }
        }
    }

    /**
     * Change la position logique et ajoute la case au chemin de l'animation.
     *
     * @param nouvelleZone zone de la case
     * @param nouvelIndex position dans la zone
     */
    private void allerSur(Zone nouvelleZone, int nouvelIndex) {
        zone = nouvelleZone;
        indexCase = nouvelIndex;
        Etape etape = etapeEcran(nouvelleZone, nouvelIndex);
        if (etape != null) {
            chemin.add(etape);
        }
    }

    /**
     * Calcule la position à l'écran du pion sur une case : chaque joueur a son quart de case.
     *
     * @return l'étape, ou null si le plateau n'est pas connu
     */
    private Etape etapeEcran(Zone zoneCase, int index) {
        if (plateau == null) {
            return null;
        }
        Case laCase = plateau.getCase(zoneCase, index);
        if (laCase == null) {
            return null;
        }
        int quart = Math.floorMod(ordre - 1, 4);
        int ex = laCase.getX() + (quart % 2) * TileMap.TAILLE_TUILE + DECALAGE;
        int ey = laCase.getY() + (quart / 2) * TileMap.TAILLE_TUILE + DECALAGE;
        return new Etape(ex, ey, zoneCase);
    }

    /**
     * Place le pion à l'écran sur sa case, sans animation.
     */
    private void placerSansAnimation() {
        chemin.clear();
        zoneAffichee = zone;
        Etape etape = etapeEcran(zone, indexCase);
        if (etape != null) {
            x = etape.x;
            y = etape.y;
        }
    }

    /**
     * @throws IllegalStateException si le pion ne connaît pas le plateau
     */
    private void verifierPlateau() {
        if (plateau == null) {
            throw new IllegalStateException("Pion " + this + " : appeler setPlateau() avant de le déplacer");
        }
    }

    /** @return couleur de secours si l'image du pion est introuvable */
    private Color couleurDeSecours() {
        switch (String.valueOf(couleur)) {
            case "ROUGE":
                return Color.RED;
            case "VERT":
                return new Color(0, 150, 0);
            case "JAUNE":
                return Color.YELLOW;
            default:
                return Color.BLUE;
        }
    }

    // ----------------------------------------------------------- animation et dessin

    /**
     * Rapproche le pion de la prochaine case du chemin (appelée toutes les 40 ms).
     * Si le chemin est long (relecture de la base), le pion va deux fois plus vite.
     */
    @Override
    public void miseAJour() {
        Etape etape = chemin.peek();
        if (etape == null) {
            return;
        }
        double vitesse = chemin.size() > 4 ? 2 * VITESSE : VITESSE;
        double dx = etape.x - x;
        double dy = etape.y - y;
        double distance = Math.hypot(dx, dy);
        if (distance <= vitesse) {
            x = etape.x;
            y = etape.y;
            zoneAffichee = etape.zone;
            chemin.poll();
        } else {
            x += dx / distance * vitesse;
            y += dy / distance * vitesse;
        }
    }

    @Override
    public boolean estEnMouvement() {
        return !chemin.isEmpty();
    }

    /**
     * Dessine pion_couleur.png, ou camion_couleur.png quand le pion est sur la Route.
     *
     * @param contexte contexte graphique
     */
    @Override
    public void rendu(Graphics2D contexte) {
        String modele = zoneAffichee == Zone.ROUTE ? "camion_" : "pion_";
        String fichier = "resources/sprites/" + modele + String.valueOf(couleur).toLowerCase(Locale.ROOT) + ".png";
        BufferedImage image = Images.charger(fichier);
        if (image != null) {
            contexte.drawImage(image, getX(), getY(), TAILLE_SPRITE, TAILLE_SPRITE, null);
        } else {
            contexte.setColor(couleurDeSecours());
            contexte.fillOval(getX(), getY(), TAILLE_SPRITE, TAILLE_SPRITE);
        }
    }

    @Override
    public String toString() {
        return pseudo + " (" + couleur + ")";
    }
}
