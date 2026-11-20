/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Tests du moteur en console (demandé par le cours avant toute partie graphique).
 * <p>
 * Lancer avec un clic droit sur le fichier, puis « Run File » (Maj+F6).
 * Chaque test affiche [OK] ou [ÉCHEC] ; le bilan est écrit à la fin.
 */
public class TestMoteur {

    private static final int PARTIES_SIMULEES = 50;
    private static final int IMAGES_MAX = 200_000;

    private static int reussis;
    private static int echecs;

    /**
     * Lance tous les tests du moteur.
     *
     * @param args non utilisés
     */
    public static void main(String[] args) {
        System.out.println("=== Tests du moteur : De la terre à l'assiette ===");
        testerPlateau();
        testerIngredients();
        testerTourDeLaFerme();
        testerGrangeRouteCuisine();
        testerRecul();
        testerCases();
        testerCartes();
        testerTourParTour();
        testerPartiesCompletes();
        System.out.println();
        System.out.println("Bilan : " + reussis + " tests réussis, " + echecs + " en échec.");
    }

    // ----------------------------------------------------------- MO-1

    /** Le plateau lit bien parcours.txt. */
    private static void testerPlateau() {
        titre("Plateau (parcours.txt)");
        Plateau plateau = new Plateau(null);
        verifier("16 cases dans la Ferme", plateau.nbCases(Zone.FERME) == 16);
        verifier("11 cases sur la Route", plateau.nbCases(Zone.ROUTE) == 11);
        verifier("9 cases dans la Cuisine", plateau.nbCases(Zone.CUISINE) == 9);
        verifier("F0 est le Départ", plateau.getCase(Zone.FERME, 0) instanceof CaseDepart);
        verifier("F11 est la Grange", plateau.getCase(Zone.FERME, 11) instanceof CaseGrange
                && plateau.getIndexGrange() == 11);
        verifier("C8 est l'Assiette", plateau.getCase(Zone.CUISINE, 8) instanceof CaseArrivee);
        verifier("R2 est un Bouchon", plateau.getCase(Zone.ROUTE, 2) instanceof CaseEvenement);
        verifier("case hors plateau = null", plateau.getCase(Zone.ROUTE, 11) == null);
        verifier("F0 dessinée en (64, 448)", plateau.getCase(Zone.FERME, 0).getX() == 64
                && plateau.getCase(Zone.FERME, 0).getY() == 448);
        verifier("place de repos du Sanglier", plateau.getReposMonstre("SANGLIER") != null);
    }

    /** Les ingrédients se comparent par leur code, avec ou sans accent. */
    private static void testerIngredients() {
        titre("Ingrédients");
        verifier("« Bœuf » donne BOEUF", "BOEUF".equals(Ingredient.versCode("Bœuf")));
        verifier("« Pomme de terre » donne POMME_DE_TERRE",
                "POMME_DE_TERRE".equals(Ingredient.versCode("Pomme de terre")));
        verifier("même code = même ingrédient",
                new Ingredient(1, "Blé", TypeIngredient.CEREALE).equals(new Ingredient(6, "BLE", TypeIngredient.CEREALE)));
        verifier("Carte.creer reconnaît « EPOUVANTAIL »",
                Carte.creer(10, "EPOUVANTAIL", "DEFENSE", 0) instanceof CarteEpouvantail);
    }

    /** Un pion pioche des cartes Avance et fait le tour de la Ferme (« Fini quand » de MO-1). */
    private static void testerTourDeLaFerme() {
        titre("Un pion fait le tour de la Ferme avec des cartes Avance");
        Plateau plateau = new Plateau(null);
        Pion pion = nouveauPion(plateau, 1);
        Pioche pioche = Pioche.creerPiocheLocale();
        verifier("la pioche contient 36 cartes", pioche.taille() == 36);
        int total = 0;
        while (total < plateau.nbCases(Zone.FERME)) {
            Carte carte = pioche.tirer();
            if (!(carte instanceof CarteMouvement)) {
                continue;
            }
            pion.avancer(carte.getValeur());
            total += carte.getValeur();
            System.out.println("    pioche « " + carte + " » -> case " + plateau.getCase(pion.getZone(), pion.getIndexCase()));
        }
        verifier("sans ingrédient, le pion reste dans la Ferme", pion.getZone() == Zone.FERME);
        verifier("la boucle repasse par le Départ", pion.getIndexCase() == total % 16);
    }

    /** Sortie par la Grange, Route, Cuisine, arrêt sur l'Assiette. */
    private static void testerGrangeRouteCuisine() {
        titre("Grange, Route et Cuisine");
        Plateau plateau = new Plateau(null);
        Pion sansIngredient = nouveauPion(plateau, 1);
        sansIngredient.setPosition(Zone.FERME, 10);
        sansIngredient.avancer(3);
        verifier("sans ses 4 ingrédients, F10 + 3 = F13", position(sansIngredient, Zone.FERME, 13));

        Pion complet = nouveauPion(plateau, 2);
        donnerRecetteComplete(complet, plateau);
        complet.setPosition(Zone.FERME, 10);
        complet.avancer(3);
        verifier("avec ses 4 ingrédients, F10 + 3 = R1 (sortie par la Grange)", position(complet, Zone.ROUTE, 1));
        complet.setPosition(Zone.FERME, 14);
        complet.avancer(4);
        verifier("avec ses 4 ingrédients, la boucle continue avant la Grange (F14 + 4 = F2)",
                position(complet, Zone.FERME, 2));
        complet.setPosition(Zone.ROUTE, 9);
        complet.avancer(4);
        verifier("R9 + 4 = C2 (entrée en Cuisine)", position(complet, Zone.CUISINE, 2));
        complet.setPosition(Zone.CUISINE, 6);
        complet.avancer(4);
        verifier("C6 + 4 s'arrête sur l'Assiette (C8)", position(complet, Zone.CUISINE, 8));
    }

    /** Recul dans la boucle de la Ferme et au début des autres zones. */
    private static void testerRecul() {
        titre("Recul");
        Plateau plateau = new Plateau(null);
        Pion pion = nouveauPion(plateau, 1);
        pion.setPosition(Zone.FERME, 1);
        pion.reculer(2);
        verifier("F1 - 2 = F15 (boucle)", position(pion, Zone.FERME, 15));
        pion.setPosition(Zone.ROUTE, 1);
        pion.reculer(2);
        verifier("R1 - 2 = R0 (on ne revient pas dans la Ferme)", position(pion, Zone.ROUTE, 0));
        pion.setPosition(Zone.CUISINE, 0);
        pion.reculer(2);
        verifier("C0 - 2 = C0", position(pion, Zone.CUISINE, 0));
    }

    // ----------------------------------------------------------- MO-2

    /** Effets des cases quand le pion s'arrête dessus. */
    private static void testerCases() {
        titre("Effets des cases");
        Jeu jeu = new Jeu();
        Plateau plateau = jeu.getPlateau();
        Pion bleu = jeu.getPions().get(0);      // Tajine : agneau, carotte, pomme de terre, oignon
        plateau.getCase(Zone.FERME, 1).appliquerEffet(bleu, jeu);
        verifier("F1 (carotte) : récoltée pour le Tajine", bleu.getInventaire().size() == 1);
        plateau.getCase(Zone.FERME, 7).appliquerEffet(bleu, jeu);
        verifier("F7 (carotte) : pas deux fois le même ingrédient", bleu.getInventaire().size() == 1);
        plateau.getCase(Zone.FERME, 6).appliquerEffet(bleu, jeu);
        verifier("F6 (blé) : inutile pour le Tajine", bleu.getInventaire().size() == 1);

        donnerRecetteComplete(bleu, plateau);
        bleu.setPosition(Zone.FERME, 11);
        plateau.getCase(Zone.FERME, 11).appliquerEffet(bleu, jeu);
        verifier("arrêt sur la Grange avec 4 ingrédients : sortie sur R0", position(bleu, Zone.ROUTE, 0));

        bleu.setPosition(Zone.ROUTE, 2);
        plateau.getCase(Zone.ROUTE, 2).appliquerEffet(bleu, jeu);
        verifier("Bouchon (R2) : recule de 1", position(bleu, Zone.ROUTE, 1));
        bleu.setPosition(Zone.ROUTE, 6);
        plateau.getCase(Zone.ROUTE, 6).appliquerEffet(bleu, jeu);
        verifier("Raccourci (R6) : avance de 2", position(bleu, Zone.ROUTE, 8));

        bleu.setPosition(Zone.CUISINE, 8);
        plateau.getCase(Zone.CUISINE, 8).appliquerEffet(bleu, jeu);
        verifier("Assiette : rang 1 et 100 points", bleu.getRang() == 1 && bleu.getScore() == 100);
    }

    /** Tour par tour : ordre, pioche, Panne qui fait passer un tour, case Pioche qui fait rejouer. */
    private static void testerTourParTour() {
        titre("Tour par tour");
        Jeu jeu = new Jeu();
        List<Pion> pions = jeu.getPions();
        verifier("4 pions, Joueur 1 commence", pions.size() == 4 && jeu.getPionCourant() == pions.get(0));
        verifier("chaque joueur a 3 cartes", pions.get(3).getMain().size() == Jeu.TAILLE_MAIN);

        Pion j1 = pions.get(0);
        Carte panne = new CartePanne(6, "Panne");
        j1.getMain().set(0, panne);
        jeu.jouerCarte(panne, null);
        verifier("une attaque sans cible est refusée", j1.getMain().contains(panne) && jeu.getPionCourant() == j1);
        jeu.jouerCarte(panne, pions.get(1));
        verifier("après sa carte, J1 a toujours 3 cartes", j1.getMain().size() == Jeu.TAILLE_MAIN);
        attendreAnimations(jeu);
        verifier("J2 en panne : c'est à J3", jeu.getPionCourant() == pions.get(2));
        verifier("la panne de J2 est consommée", pions.get(1).getToursBloques() == 0);

        Pion j3 = jeu.getPionCourant();
        j3.setPosition(Zone.FERME, 1);
        Carte avance2 = new CarteMouvement(2, "Avance 2", 2);
        j3.getMain().set(0, avance2);
        jeu.jouerCarte(avance2, null);
        verifier("J3 sur la case Pioche (F3) : il rejoue", jeu.getPionCourant() == j3
                && position(j3, Zone.FERME, 3) && j3.getMain().size() == Jeu.TAILLE_MAIN);
        verifier("pendant l'animation, personne ne peut jouer", !jeu.estMonTour());
        jouerUneCarteAvance(jeu);   // J3 rejoue
        jouerUneCarteAvance(jeu);   // J4
        verifier("après J4, le monstre a joué et le tour 2 commence avec J1",
                jeu.getNumTour() == 2 && jeu.getPionCourant() == j1);
    }

    // ----------------------------------------------------------- MO-3

    /** Effets des cartes Attaque et Défense. */
    private static void testerCartes() {
        titre("Cartes Attaque et Défense");
        Jeu jeu = new Jeu();
        Plateau plateau = jeu.getPlateau();
        Pion j1 = jeu.getPions().get(0);
        Pion j2 = jeu.getPions().get(1);

        j2.setPosition(Zone.FERME, 5);
        new CarteSecheresse(5, "Sécheresse").appliquer(j1, j2, jeu);
        verifier("Sécheresse : la cible recule de 2", position(j2, Zone.FERME, 3));

        new CarteBouclier(9, "Bouclier").appliquer(j2, null, jeu);
        new CarteSecheresse(5, "Sécheresse").appliquer(j1, j2, jeu);
        verifier("Bouclier : l'attaque suivante est annulée", position(j2, Zone.FERME, 3) && !j2.isProtegeCarte());

        new CartePanne(6, "Panne").appliquer(j1, j2, jeu);
        verifier("Panne : la cible passera 1 tour", j2.getToursBloques() == 1);

        j2.ajouterIngredient(plateau.getIngredient("POULET"));
        new CarteVol(7, "Vol").appliquer(j1, j2, jeu);
        verifier("Vol dans la Ferme : la cible perd 1 ingrédient", j2.getInventaire().isEmpty());
        j2.ajouterIngredient(plateau.getIngredient("POULET"));
        j2.setPosition(Zone.ROUTE, 3);
        new CarteVol(7, "Vol").appliquer(j1, j2, jeu);
        verifier("Vol sur la Route : sans effet", j2.getInventaire().size() == 1);

        new CarteAppat(8, "Appât").appliquer(j1, j2, jeu);
        verifier("Appât : la cible attire le monstre", j2.isCibleAppat());
        new CarteEpouvantail(10, "Épouvantail").appliquer(j1, null, jeu);
        verifier("Épouvantail : 2 tours de protection", j1.getToursEpouvantail() == 2);
    }

    /** Parties complètes jouées au hasard, du Départ jusqu'au classement. */
    private static void testerPartiesCompletes() {
        titre(PARTIES_SIMULEES + " parties complètes jouées au hasard");
        Random hasard = new Random(2026);
        int terminees = 0;
        int classementsCorrects = 0;
        int reglesRespectees = 0;
        long totalTours = 0;
        for (int partie = 0; partie < PARTIES_SIMULEES; partie++) {
            Jeu jeu = new Jeu();
            boolean regles = true;
            for (int image = 0; image < IMAGES_MAX && !jeu.estTermine(); image++) {
                if (jeu.estMonTour()) {
                    regles &= reglesRespectees(jeu);
                    jouerAuHasard(jeu, hasard);
                }
                jeu.miseAJour();
            }
            if (jeu.estTermine()) {
                terminees++;
                totalTours += jeu.getNumTour();
                if (classementCorrect(jeu)) {
                    classementsCorrects++;
                }
            }
            if (regles) {
                reglesRespectees++;
            }
        }
        verifier("toutes les parties se terminent", terminees == PARTIES_SIMULEES);
        verifier("rangs 1 à 4 distincts et scores 100/70/40/10", classementsCorrects == PARTIES_SIMULEES);
        verifier("3 cartes en main, inventaire sans doublon et utile à la recette",
                reglesRespectees == PARTIES_SIMULEES);
        if (terminees > 0) {
            System.out.println("    durée moyenne d'une partie : " + totalTours / terminees + " tours complets");
        }
    }

    // ----------------------------------------------------------- outils de test

    /**
     * Joue une carte au hasard de la main du joueur courant (cible au hasard si besoin).
     */
    private static void jouerAuHasard(Jeu jeu, Random hasard) {
        Pion joueur = jeu.getPionCourant();
        Carte carte = joueur.getMain().get(hasard.nextInt(joueur.getMain().size()));
        List<Pion> cibles = jeu.getCiblesPossibles();
        Pion cible = cibles.isEmpty() ? null : cibles.get(hasard.nextInt(cibles.size()));
        jeu.jouerCarte(carte, carte.necessiteCible() ? cible : null);
    }

    /**
     * Fait jouer une carte Avance 1 au joueur courant, puis attend la fin des animations.
     */
    private static void jouerUneCarteAvance(Jeu jeu) {
        attendreAnimations(jeu);
        Pion joueur = jeu.getPionCourant();
        Carte avance1 = new CarteMouvement(1, "Avance 1", 1);
        joueur.getMain().set(0, avance1);
        joueur.setPosition(Zone.ROUTE, 0);
        jeu.jouerCarte(avance1, null);
        attendreAnimations(jeu);
    }

    /**
     * Appelle miseAJour() jusqu'à ce que plus rien ne bouge et qu'un joueur puisse jouer.
     */
    private static void attendreAnimations(Jeu jeu) {
        for (int image = 0; image < IMAGES_MAX && !jeu.estMonTour() && !jeu.estTermine(); image++) {
            jeu.miseAJour();
        }
    }

    /** @return vrai si le joueur courant a 3 cartes et un inventaire correct */
    private static boolean reglesRespectees(Jeu jeu) {
        Pion joueur = jeu.getPionCourant();
        if (joueur.getMain().size() != Jeu.TAILLE_MAIN) {
            return false;
        }
        for (Pion pion : jeu.getPions()) {
            Set<Ingredient> uniques = new HashSet<>(pion.getInventaire());
            if (uniques.size() != pion.getInventaire().size()
                    || !pion.getRecette().getIngredients().containsAll(pion.getInventaire())) {
                return false;
            }
        }
        return true;
    }

    /** @return vrai si les rangs vont de 1 à 4 avec les bons scores */
    private static boolean classementCorrect(Jeu jeu) {
        int[] points = {100, 70, 40, 10};
        List<Pion> classement = jeu.getClassement();
        for (int i = 0; i < classement.size(); i++) {
            if (classement.get(i).getRang() != i + 1 || classement.get(i).getScore() != points[i]) {
                return false;
            }
        }
        return true;
    }

    /** @return un pion posé sur le Départ du plateau */
    private static Pion nouveauPion(Plateau plateau, int ordre) {
        Pion pion = new Pion(ordre, "Test " + ordre, "BLEU", ordre);
        pion.setPlateau(plateau);
        return pion;
    }

    /** Donne au pion le Tajine avec ses 4 ingrédients déjà récoltés. */
    private static void donnerRecetteComplete(Pion pion, Plateau plateau) {
        List<Ingredient> tajine = new ArrayList<>();
        for (String code : new String[]{"AGNEAU", "CAROTTE", "POMME_DE_TERRE", "OIGNON"}) {
            tajine.add(plateau.getIngredient(code));
        }
        pion.setRecette(new Recette(1, "Tajine", tajine));
        pion.getInventaire().clear();
        pion.getInventaire().addAll(tajine);
    }

    /** @return vrai si le pion est sur la case indiquée */
    private static boolean position(Pion pion, Zone zone, int index) {
        return pion.getZone() == zone && pion.getIndexCase() == index;
    }

    private static void titre(String texte) {
        System.out.println();
        System.out.println("--- " + texte);
    }

    private static void verifier(String description, boolean condition) {
        if (condition) {
            reussis++;
            System.out.println("[OK]     " + description);
        } else {
            echecs++;
            System.out.println("[ÉCHEC]  " + description);
        }
    }
}
