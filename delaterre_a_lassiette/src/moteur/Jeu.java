/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sql.ActionSQL;
import sql.CarteSQL;
import sql.LigneAction;
import sql.MonstreSQL;
import sql.PartieSQL;
import sql.PionSQL;

/**
 * Une partie en cours : plateau, pions, monstre, pioche, tour de jeu.
 * <p>
 * FenetreDeJeu appelle miseAJour() puis rendu() toutes les 40 ms, comme dans le modèle du prof.
 * <p>
 * Déroulement : le joueur courant joue une carte (jouerCarte), l'effet de la carte puis celui
 * de la case s'appliquent, il pioche pour revenir à 3 cartes, puis c'est au joueur suivant
 * (un joueur bloqué passe son tour). Quand les 4 joueurs ont joué (tour complet), c'est le tour
 * du monstre, calculé par le poste de l'hôte. La partie s'arrête quand 3 joueurs ont fini.
 * <p>
 * En réseau, chaque poste écrit ce qu'il change dans la base (pions, mains, journal ACTION,
 * joueur courant) et relit la base toutes les 0,5 s pour voir les coups des autres.
 */
public final class Jeu {

    /** Nombre de cartes en main. */
    public static final int TAILLE_MAIN = 3;
    /** Carte du décor (tile mapping). */
    public static final String FICHIER_CARTE_PLATEAU = "resources/tilemap/carte_plateau.txt";
    /** Image des tuiles du décor. */
    public static final String FICHIER_TILESET = "resources/tilemap/tileset.png";

    private static final long PERIODE_LECTURE_MS = 500;
    private static final int[] POINTS_PAR_RANG = {100, 70, 40, 10};
    private static final int JOURNAL_MAX = 100;
    private static final int TEXTE_MAX = 250;
    private static final String[] COULEURS = {"BLEU", "ROUGE", "VERT", "JAUNE"};
    private static final Color COULEUR_TOUR = new Color(255, 215, 0);
    private static final Stroke TRAIT_TOUR = new BasicStroke(3f);

    /** Étapes d'un tour de jeu. */
    private enum Phase {
        /** Un joueur doit jouer une carte. */
        JOUEUR,
        /** Tour complet terminé : le monstre va jouer (sur le poste de l'hôte). */
        MONSTRE_A_LANCER,
        /** Le monstre se déplace et agit. */
        MONSTRE_EN_COURS,
        /** La partie est terminée. */
        FIN
    }

    private final int idPartie;
    private final boolean enReseau;
    private final Plateau plateau;
    private final List<Pion> pions;
    private Pion pionLocal;
    private final GestionMonstres gestionMonstres;
    private final Pioche pioche;
    private int ordreCourant;
    private int numTour;
    private final List<String> journal;
    private Phase phase;
    private boolean rejouer;
    private Pion cibleMonstre;
    private Map<Integer, EtatPion> etatAvantMonstre;

    // Réseau (null en partie locale)
    private final PartieSQL partieSQL;
    private final PionSQL pionSQL;
    private final CarteSQL carteSQL;
    private final ActionSQL actionSQL;
    private final MonstreSQL monstreSQL;
    private final List<ActionAEnvoyer> actionsAEnvoyer;
    private int idDerniereAction;
    private long derniereLecture;

    /**
     * Partie LOCALE de test : 4 pions sur un seul PC, sans base de données
     * (Release 1 et tests de l'interface). En local, getPionLocal() = getPionCourant()
     * et estMonTour() est vrai dès que le jeu attend une carte.
     */
    public Jeu() {
        this.idPartie = 0;
        this.enReseau = false;
        this.partieSQL = null;
        this.pionSQL = null;
        this.carteSQL = null;
        this.actionSQL = null;
        this.monstreSQL = null;
        this.actionsAEnvoyer = new ArrayList<>();
        this.journal = new ArrayList<>();
        this.pions = new ArrayList<>();
        this.plateau = new Plateau(new TileMap(FICHIER_CARTE_PLATEAU, FICHIER_TILESET));
        this.pioche = Pioche.creerPiocheLocale();
        List<Recette> recettes = creerRecettesLocales();
        for (int i = 0; i < COULEURS.length; i++) {
            Pion pion = new Pion(i + 1, "Joueur " + (i + 1), COULEURS[i], i + 1);
            pion.setPlateau(plateau);
            pion.setRecette(recettes.get(i % recettes.size()));
            completerMain(pion);
            pions.add(pion);
        }
        this.gestionMonstres = new GestionMonstres(plateau);
        this.ordreCourant = 1;
        this.numTour = 1;
        this.phase = Phase.JOUEUR;
        ajouterLigneJournal("Partie locale à 4 joueurs. " + getPionCourant() + " commence.");
    }

    /**
     * Partie en réseau : tout est chargé depuis la base.
     *
     * @param idPartie identifiant de la partie (id_partie)
     * @param idPionLocal pion du joueur assis devant ce PC (id_pion)
     * @throws IllegalStateException si le pion local n'est pas dans la partie
     */
    public Jeu(int idPartie, int idPionLocal) {
        this.idPartie = idPartie;
        this.enReseau = true;
        this.partieSQL = new PartieSQL();
        this.pionSQL = new PionSQL();
        this.carteSQL = new CarteSQL();
        this.actionSQL = new ActionSQL();
        this.monstreSQL = new MonstreSQL();
        this.actionsAEnvoyer = new ArrayList<>();
        this.journal = new ArrayList<>();
        this.plateau = new Plateau(new TileMap(FICHIER_CARTE_PLATEAU, FICHIER_TILESET));
        this.pions = new ArrayList<>(pionSQL.listerPions(idPartie));
        pions.sort(Comparator.comparingInt(Pion::getOrdre));
        for (Pion pion : pions) {
            pion.setPlateau(plateau);
            if (pion.getId() == idPionLocal) {
                pionLocal = pion;
            }
        }
        if (pionLocal == null) {
            throw new IllegalStateException("Pion " + idPionLocal + " introuvable dans la partie " + idPartie
                    + " : vérifier la base de données.");
        }
        this.pioche = new Pioche(carteSQL.catalogue());
        this.gestionMonstres = new GestionMonstres(plateau);
        this.numTour = Math.max(1, partieSQL.getNumTour(idPartie));
        this.ordreCourant = pions.get(0).getOrdre();
        this.phase = Phase.JOUEUR;
        appliquerOrdreLu(partieSQL.getOrdreCourant(idPartie));
        if ("TERMINEE".equals(partieSQL.getEtat(idPartie))) {
            phase = Phase.FIN;
        }
        if (pionLocal.getMain().isEmpty() && !pionLocal.estFini()) {
            completerMain(pionLocal);
            sauvegarderMain(pionLocal);
        }
        lireNouvellesActions(false);
        this.derniereLecture = System.currentTimeMillis();
    }

    // ----------------------------------------------------------- getters

    /** @return les pions de la partie, rangés par ordre de jeu */
    public List<Pion> getPions() {
        return pions;
    }

    /** @return pion du joueur de ce PC (en local : le joueur dont c'est le tour) */
    public Pion getPionLocal() {
        return enReseau ? pionLocal : getPionCourant();
    }

    /** @return pion du joueur dont c'est le tour (le dernier joueur pendant le tour du monstre) */
    public Pion getPionCourant() {
        return trouverPionParOrdre(ordreCourant);
    }

    /** @return les adversaires que le joueur courant peut viser (pas lui-même, pas les pions finis) */
    public List<Pion> getCiblesPossibles() {
        List<Pion> cibles = new ArrayList<>();
        Pion courant = getPionCourant();
        for (Pion pion : pions) {
            if (pion != courant && !pion.estFini()) {
                cibles.add(pion);
            }
        }
        return cibles;
    }

    /** @return les pions du 1er au dernier (rang 0 = pas encore classé, à la fin) */
    public List<Pion> getClassement() {
        List<Pion> classement = new ArrayList<>(pions);
        classement.sort(Comparator.comparingInt((Pion p) -> p.getRang() == 0 ? Integer.MAX_VALUE : p.getRang())
                .thenComparing(Comparator.comparingInt(Pion::progression).reversed()));
        return classement;
    }

    /** @return le plateau */
    public Plateau getPlateau() {
        return plateau;
    }

    /** @return les messages du journal, du plus ancien au plus récent (liste non modifiable) */
    public List<String> getJournal() {
        return Collections.unmodifiableList(journal);
    }

    /** @return identifiant de la partie (0 en partie locale) */
    public int getIdPartie() {
        return idPartie;
    }

    /** @return numéro du tour en cours */
    public int getNumTour() {
        return numTour;
    }

    // ----------------------------------------------------------- boucle de jeu

    /**
     * Met à jour la partie (toutes les 40 ms) : animations, tour du monstre,
     * et lecture de la base toutes les 0,5 s en réseau.
     */
    public void miseAJour() {
        for (Pion pion : pions) {
            pion.miseAJour();
        }
        gestionMonstres.miseAJour();
        if (enReseau && System.currentTimeMillis() - derniereLecture >= PERIODE_LECTURE_MS) {
            derniereLecture = System.currentTimeMillis();
            synchroniser();
        }
        if (phase == Phase.MONSTRE_A_LANCER && estHote() && !pionsEnMouvement()) {
            lancerTourDuMonstre();
        } else if (phase == Phase.MONSTRE_EN_COURS && !gestionMonstres.estEnMouvement()) {
            terminerTourDuMonstre();
        }
    }

    /**
     * Dessine la partie : plateau et cases, monstres, puis pions (le joueur courant est entouré).
     *
     * @param contexte contexte graphique du plateau (960 x 640)
     */
    public void rendu(Graphics2D contexte) {
        contexte.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        plateau.rendu(contexte);
        gestionMonstres.rendu(contexte);
        Pion courant = getPionCourant();
        if (phase == Phase.JOUEUR && courant != null && !courant.estFini()) {
            Stroke ancienTrait = contexte.getStroke();
            contexte.setStroke(TRAIT_TOUR);
            contexte.setColor(COULEUR_TOUR);
            contexte.drawOval(courant.getX() - 4, courant.getY() - 4, 32, 32);
            contexte.setStroke(ancienTrait);
        }
        // Les pions immobiles d'abord, ceux qui bougent par-dessus.
        for (Pion pion : pions) {
            if (!pion.estEnMouvement()) {
                pion.rendu(contexte);
            }
        }
        for (Pion pion : pions) {
            if (pion.estEnMouvement()) {
                pion.rendu(contexte);
            }
        }
    }

    // ----------------------------------------------------------- état de la partie

    /** @return vrai quand la partie est finie (3 joueurs à l'Assiette) et les animations terminées */
    public boolean estTermine() {
        return phase == Phase.FIN && !animationEnCours();
    }

    /** @return vrai si c'est au joueur de ce PC de jouer (et que rien ne bouge à l'écran) */
    public boolean estMonTour() {
        if (phase != Phase.JOUEUR || animationEnCours()) {
            return false;
        }
        Pion courant = getPionCourant();
        if (courant == null || courant.estFini()) {
            return false;
        }
        return !enReseau || courant == pionLocal;
    }

    /** @return vrai si le joueur de ce PC est l'hôte (ordre 1), toujours vrai en local */
    public boolean estHote() {
        return pionLocal == null || pionLocal.getOrdre() == 1;
    }

    /** @return vrai pendant le tour du monstre (aucun joueur ne peut jouer) */
    public boolean estTourDuMonstre() {
        return phase == Phase.MONSTRE_A_LANCER || phase == Phase.MONSTRE_EN_COURS;
    }

    /** @return vrai si un pion ou un monstre est en train de se déplacer */
    public boolean animationEnCours() {
        return pionsEnMouvement() || gestionMonstres.estEnMouvement();
    }

    // ----------------------------------------------------------- un tour de joueur

    /**
     * Joue une carte du joueur courant : effet de la carte, effet de la case, pioche,
     * puis joueur suivant. La demande est ignorée si ce n'est pas le tour de ce PC,
     * si la carte n'est pas dans la main du joueur, ou si une cible valide manque.
     *
     * @param carte carte jouée (prise dans la main du joueur)
     * @param cible pion visé, ou null si la carte n'en demande pas
     */
    public void jouerCarte(Carte carte, Pion cible) {
        Pion joueur = getPionCourant();
        if (carte == null || !estMonTour() || !joueur.getMain().contains(carte)) {
            return;
        }
        List<Pion> ciblesPossibles = getCiblesPossibles();
        if (!carte.necessiteCible() || ciblesPossibles.isEmpty()) {
            cible = null;
        } else if (!ciblesPossibles.contains(cible)) {
            ajouterLigneJournal("Choisis un adversaire pour jouer « " + carte + " ».");
            return;
        }
        Map<Integer, EtatPion> avant = photographier();
        rejouer = false;
        joueur.getMain().remove(carte);
        String texte = joueur + " joue « " + carte + " »" + (cible == null ? "." : " sur " + cible + ".");
        noter("CARTE", joueur, carte, cible, texte);
        carte.appliquer(joueur, cible, this);
        if (!joueur.estFini()) {
            completerMain(joueur);
        }
        if (nombreDeFinis() >= seuilDeFin()) {
            terminerPartie();
        } else if (!rejouer || joueur.estFini()) {
            passerAuJoueurSuivant();
        }
        rejouer = false;
        enregistrer(avant);
    }

    /**
     * Case Pioche ou Station-service : le joueur pioche une carte de plus et rejoue tout de suite.
     *
     * @param pion le pion qui vient de s'arrêter sur la case
     */
    public void donnerCarteBonus(Pion pion) {
        if (phase == Phase.JOUEUR && pion == getPionCourant()) {
            rejouer = true;
            ajouterAuJournal(pion + " pioche une carte en plus et rejoue !");
        }
    }

    /**
     * L'Assiette est atteinte : le joueur reçoit son rang (1er, 2e, 3e) et son score.
     *
     * @param pion le pion qui vient d'arriver sur l'Assiette
     */
    public void attribuerRang(Pion pion) {
        if (pion.estFini()) {
            return;
        }
        classer(pion);
        String plat = pion.getRecette() == null ? "son plat" : "son plat (" + pion.getRecette().getNom() + ")";
        ajouterAuJournal(pion + " termine " + plat + " : " + texteRang(pion.getRang()) + " !");
    }

    /**
     * Ajoute un message au journal de la partie (visible par tous les joueurs).
     *
     * @param texte le message
     */
    public void ajouterAuJournal(String texte) {
        noter("INFO", null, null, null, texte);
    }

    /**
     * Donne la main au joueur suivant ; à la fin d'un tour complet, c'est au monstre.
     */
    private void passerAuJoueurSuivant() {
        int suivant = chercherJoueurSuivant(ordreCourant);
        if (suivant > 0) {
            ordreCourant = suivant;
        } else {
            phase = Phase.MONSTRE_A_LANCER;
        }
    }

    /**
     * Cherche le prochain joueur après l'ordre donné. Les pions finis sont sautés ;
     * un pion bloqué passe son tour (toursBloques diminue de 1).
     *
     * @param depuisOrdre ordre du dernier joueur (0 = début d'un tour complet)
     * @return l'ordre du joueur suivant, ou 0 si le tour complet est terminé
     */
    private int chercherJoueurSuivant(int depuisOrdre) {
        for (Pion pion : pions) {
            if (pion.getOrdre() <= depuisOrdre || pion.estFini()) {
                continue;
            }
            if (pion.getToursBloques() > 0) {
                pion.setToursBloques(pion.getToursBloques() - 1);
                noter("PASSE", pion, null, null, pion + " est bloqué : il passe son tour.");
                continue;
            }
            return pion.getOrdre();
        }
        return 0;
    }

    /**
     * Pioche des cartes jusqu'à en avoir 3 en main.
     *
     * @param pion le pion qui pioche
     */
    private void completerMain(Pion pion) {
        while (pion.getMain().size() < TAILLE_MAIN) {
            Carte carte = pioche.tirer();
            if (carte == null) {
                return;
            }
            pion.getMain().add(carte);
        }
    }

    // ----------------------------------------------------------- tour du monstre

    /**
     * Poste de l'hôte : lance le tour du monstre (tirage, déplacement, effet).
     */
    private void lancerTourDuMonstre() {
        etatAvantMonstre = photographier();
        phase = Phase.MONSTRE_EN_COURS;
        cibleMonstre = gestionMonstres.tourDuMonstre(pions, this);
    }

    /**
     * Le monstre est revenu à sa place : on note ce qu'il a fait, on décompte
     * les Épouvantails, on efface les Appâts, puis le joueur 1 reprend la main.
     */
    private void terminerTourDuMonstre() {
        Map<Integer, EtatPion> avant = etatAvantMonstre != null ? etatAvantMonstre : photographier();
        etatAvantMonstre = null;
        Pion cible = cibleMonstre;
        cibleMonstre = null;
        if (cible == null) {
            noter("MONSTRE", null, null, null, "Tour du monstre : il n'attaque personne.");
        } else {
            String texte = "Tour du monstre : le " + nomMonstre(cible.getZone()) + " attaque " + cible
                    + " : " + decrireEffet(avant.get(cible.getId()), cible);
            noter("MONSTRE", null, null, cible, texte);
            if (enReseau) {
                monstreSQL.sauvegarder(idPartie, typeMonstre(cible.getZone()), cible.getZone().name(),
                        cible.getIndexCase());
            }
        }
        for (Pion pion : pions) {
            if (pion.getToursEpouvantail() > 0) {
                pion.setToursEpouvantail(pion.getToursEpouvantail() - 1);
            }
            pion.setCibleAppat(false);
        }
        numTour++;
        phase = Phase.JOUEUR;
        int premier = chercherJoueurSuivant(0);
        if (premier > 0) {
            ordreCourant = premier;
        } else {
            phase = Phase.MONSTRE_A_LANCER;
        }
        enregistrer(avant);
    }

    /**
     * Décrit l'effet du monstre en comparant la cible avant et après son passage.
     *
     * @param avant état de la cible avant le tour du monstre
     * @param cible la cible après le tour du monstre
     * @return par exemple "il perd Carotte." ou "il recule de 2 cases."
     */
    private String decrireEffet(EtatPion avant, Pion cible) {
        if (avant == null) {
            return "effet inconnu.";
        }
        List<Ingredient> perdus = new ArrayList<>(avant.inventaire);
        for (Ingredient ingredient : cible.getInventaire()) {
            perdus.remove(ingredient);
        }
        if (!perdus.isEmpty()) {
            return "il perd " + perdus.get(0).getNom() + ".";
        }
        if (avant.indexCase != cible.getIndexCase()) {
            int taille = plateau.nbCases(cible.getZone());
            int recul = cible.getZone() == Zone.FERME
                    ? Math.floorMod(avant.indexCase - cible.getIndexCase(), taille)
                    : avant.indexCase - cible.getIndexCase();
            return "il recule de " + recul + " case" + (recul > 1 ? "s." : ".");
        }
        if (cible.getToursBloques() > avant.toursBloques) {
            return "il est bloqué " + cible.getToursBloques() + " tour.";
        }
        if (cible.getToursEpouvantail() > 0) {
            return "l'Épouvantail le fait fuir !";
        }
        return "rien ne se passe.";
    }

    /**
     * @param zone zone de la cible
     * @return "SANGLIER" (Ferme), "TEMPETE" (Route) ou "RAT" (Cuisine)
     */
    private static String typeMonstre(Zone zone) {
        switch (zone) {
            case ROUTE:
                return "TEMPETE";
            case CUISINE:
                return "RAT";
            default:
                return "SANGLIER";
        }
    }

    /**
     * @param zone zone de la cible
     * @return nom du monstre de la zone, pour le journal
     */
    private static String nomMonstre(Zone zone) {
        switch (zone) {
            case ROUTE:
                return "Tempête";
            case CUISINE:
                return "Rat";
            default:
                return "Sanglier";
        }
    }

    // ----------------------------------------------------------- fin de partie

    /** @return nombre de joueurs qui ont fini leur plat */
    private int nombreDeFinis() {
        int nombre = 0;
        for (Pion pion : pions) {
            if (pion.estFini()) {
                nombre++;
            }
        }
        return nombre;
    }

    /** @return nombre de plats finis qui arrête la partie (3 pour 4 joueurs) */
    private int seuilDeFin() {
        return Math.max(1, pions.size() - 1);
    }

    /**
     * Donne au pion le rang suivant et le score correspondant.
     *
     * @param pion pion à classer
     */
    private void classer(Pion pion) {
        int rang = nombreDeFinis() + 1;
        pion.setRang(rang);
        pion.setScore(POINTS_PAR_RANG[Math.min(rang, POINTS_PAR_RANG.length) - 1]);
    }

    /**
     * Fin de partie : les joueurs restants sont classés d'après leur avance.
     */
    private void terminerPartie() {
        List<Pion> restants = new ArrayList<>();
        for (Pion pion : pions) {
            if (!pion.estFini()) {
                restants.add(pion);
            }
        }
        restants.sort(Comparator.comparingInt(Pion::progression).reversed());
        for (Pion pion : restants) {
            classer(pion);
        }
        phase = Phase.FIN;
        StringBuilder texte = new StringBuilder("Partie terminée ! Classement :");
        for (Pion pion : getClassement()) {
            texte.append(' ').append(pion.getRang()).append(". ").append(pion.getPseudo())
                    .append(" (").append(pion.getScore()).append(" pts)");
        }
        noter("FIN", null, null, null, texte.toString());
    }

    /**
     * @param rang rang de 1 à 4
     * @return "1er", "2e", "3e" ou "4e"
     */
    private static String texteRang(int rang) {
        return rang == 1 ? "1er" : rang + "e";
    }

    // ----------------------------------------------------------- base de données (réseau)

    /**
     * Relit la base : nouvelles actions (journal, animation du monstre), pions,
     * joueur courant et état de la partie.
     */
    private void synchroniser() {
        lireNouvellesActions(true);
        if (phase == Phase.MONSTRE_EN_COURS && estHote()) {
            return; // l'hôte n'a pas encore enregistré l'effet du monstre : on ne relit pas les pions
        }
        for (Pion relu : pionSQL.listerPions(idPartie)) {
            Pion pion = trouverPion(relu.getId());
            if (pion != null) {
                pion.copierEtat(relu);
            }
        }
        int tour = partieSQL.getNumTour(idPartie);
        if (tour > 0) {
            numTour = tour;
        }
        appliquerOrdreLu(partieSQL.getOrdreCourant(idPartie));
        if ("TERMINEE".equals(partieSQL.getEtat(idPartie))) {
            phase = Phase.FIN;
        }
    }

    /**
     * Applique le joueur courant lu dans la base (0 = tour du monstre).
     *
     * @param ordreLu valeur de PARTIE.ordre_courant
     */
    private void appliquerOrdreLu(int ordreLu) {
        if (phase == Phase.FIN) {
            return;
        }
        if (ordreLu == 0) {
            if (phase == Phase.JOUEUR) {
                phase = Phase.MONSTRE_A_LANCER;
            }
        } else if (trouverPionParOrdre(ordreLu) != null) {
            ordreCourant = ordreLu;
            phase = Phase.JOUEUR;
        }
    }

    /**
     * Lit les actions écrites depuis la dernière lecture et les ajoute au journal.
     *
     * @param animer vrai pour animer le monstre sur les postes autres que l'hôte
     */
    private void lireNouvellesActions(boolean animer) {
        for (LigneAction ligne : actionSQL.lireDepuis(idPartie, idDerniereAction)) {
            idDerniereAction = Math.max(idDerniereAction, ligne.idAction);
            if (ligne.texte != null && !ligne.texte.isEmpty()) {
                ajouterLigneJournal(ligne.texte);
            }
            if (animer && !estHote() && "MONSTRE".equals(ligne.type) && ligne.idPionCible != null) {
                Pion cible = trouverPion(ligne.idPionCible);
                if (cible != null) {
                    gestionMonstres.animer(typeMonstre(cible.getZone()), cible);
                }
            }
        }
    }

    /**
     * Enregistre dans la base tout ce qui a changé depuis la photo « avant » :
     * pions, inventaires, mains, journal, puis joueur courant ou fin de partie.
     *
     * @param avant photo des pions prise avant l'action
     */
    private void enregistrer(Map<Integer, EtatPion> avant) {
        if (!enReseau) {
            return;
        }
        for (Pion pion : pions) {
            EtatPion etatAvant = avant.get(pion.getId());
            if (etatAvant != null) {
                enregistrerPion(pion, etatAvant);
            }
        }
        for (ActionAEnvoyer action : actionsAEnvoyer) {
            actionSQL.ajouter(idPartie, action.numTour, action.idPionAuteur, action.type,
                    action.idCarte, action.idPionCible, action.texte);
        }
        actionsAEnvoyer.clear();
        if (phase == Phase.FIN) {
            partieSQL.setEtat(idPartie, "TERMINEE");
        } else {
            partieSQL.passerTour(idPartie, phase == Phase.JOUEUR ? ordreCourant : 0, numTour);
        }
        // Le joueur voit tout de suite ses messages dans le journal, sans attendre la prochaine lecture.
        lireNouvellesActions(false);
    }

    /**
     * Enregistre un pion s'il a changé : colonnes de PION, puis INVENTAIRE et MAIN.
     *
     * @param pion le pion
     * @param avant son état avant l'action
     */
    private void enregistrerPion(Pion pion, EtatPion avant) {
        EtatPion apres = new EtatPion(pion);
        if (!avant.memesColonnes(apres)) {
            pionSQL.sauvegarder(pion);
        }
        List<Ingredient> retires = new ArrayList<>(avant.inventaire);
        List<Ingredient> ajoutes = new ArrayList<>(apres.inventaire);
        for (Ingredient ingredient : apres.inventaire) {
            retires.remove(ingredient);
        }
        for (Ingredient ingredient : avant.inventaire) {
            ajoutes.remove(ingredient);
        }
        for (Ingredient ingredient : retires) {
            pionSQL.retirerIngredient(pion.getId(), ingredient.getId());
        }
        for (Ingredient ingredient : ajoutes) {
            pionSQL.ajouterIngredient(pion.getId(), ingredient.getId());
        }
        if (!avant.main.equals(apres.main)) {
            sauvegarderMain(pion);
        }
    }

    /**
     * Réécrit la main du pion dans la table MAIN (emplacements 1 à 3).
     *
     * @param pion le pion
     */
    private void sauvegarderMain(Pion pion) {
        for (int emplacement = 1; emplacement <= TAILLE_MAIN; emplacement++) {
            carteSQL.retirerDeMain(pion.getId(), emplacement);
        }
        List<Carte> main = pion.getMain();
        for (int i = 0; i < main.size() && i < TAILLE_MAIN; i++) {
            carteSQL.poserDansMain(pion.getId(), i + 1, main.get(i).getId());
        }
    }

    /**
     * Note un événement : directement dans le journal en local ; en réseau, il est écrit
     * dans la table ACTION à la fin de l'action, puis relu par tous les postes.
     */
    private void noter(String type, Pion auteur, Carte carte, Pion cible, String texte) {
        if (enReseau) {
            actionsAEnvoyer.add(new ActionAEnvoyer(numTour, type, auteur, carte, cible, texte));
        } else {
            ajouterLigneJournal(texte);
        }
    }

    /**
     * Ajoute une ligne au journal affiché (les 100 dernières lignes sont gardées).
     *
     * @param texte la ligne
     */
    private void ajouterLigneJournal(String texte) {
        journal.add(texte);
        if (journal.size() > JOURNAL_MAX) {
            journal.remove(0);
        }
    }

    /**
     * Photo de tous les pions, pour savoir ensuite ce qu'il faut enregistrer.
     *
     * @return l'état de chaque pion, rangé par id_pion
     */
    private Map<Integer, EtatPion> photographier() {
        Map<Integer, EtatPion> photo = new HashMap<>();
        for (Pion pion : pions) {
            photo.put(pion.getId(), new EtatPion(pion));
        }
        return photo;
    }

    // ----------------------------------------------------------- outils

    /** @return vrai si au moins un pion se déplace */
    private boolean pionsEnMouvement() {
        for (Pion pion : pions) {
            if (pion.estEnMouvement()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param id identifiant du pion
     * @return le pion, ou null
     */
    private Pion trouverPion(int id) {
        for (Pion pion : pions) {
            if (pion.getId() == id) {
                return pion;
            }
        }
        return null;
    }

    /**
     * @param ordre ordre de jeu
     * @return le pion qui a cet ordre, ou null
     */
    private Pion trouverPionParOrdre(int ordre) {
        for (Pion pion : pions) {
            if (pion.getOrdre() == ordre) {
                return pion;
            }
        }
        return null;
    }

    /**
     * Les 4 recettes de la partie locale (mêmes plats que la table RECETTE).
     *
     * @return Tajine, Couscous, Burger, Poulet aux légumes
     */
    private List<Recette> creerRecettesLocales() {
        String[][] plats = {
            {"Tajine", "AGNEAU", "CAROTTE", "POMME_DE_TERRE", "OIGNON"},
            {"Couscous", "POULET", "BLE", "COURGETTE", "CAROTTE"},
            {"Burger", "BOEUF", "BLE", "TOMATE", "OIGNON"},
            {"Poulet aux légumes", "POULET", "POMME_DE_TERRE", "COURGETTE", "TOMATE"}
        };
        List<Recette> recettes = new ArrayList<>();
        for (int i = 0; i < plats.length; i++) {
            List<Ingredient> ingredients = new ArrayList<>();
            for (int j = 1; j < plats[i].length; j++) {
                ingredients.add(plateau.getIngredient(plats[i][j]));
            }
            recettes.add(new Recette(i + 1, plats[i][0], ingredients));
        }
        return recettes;
    }

    // ----------------------------------------------------------- classes internes

    /**
     * Photo d'un pion à un instant donné (pour savoir ce qui a changé).
     */
    private static final class EtatPion {

        private final Zone zone;
        private final int indexCase;
        private final int toursBloques;
        private final boolean protegeCarte;
        private final int toursEpouvantail;
        private final boolean cibleAppat;
        private final int rang;
        private final int score;
        private final List<Ingredient> inventaire;
        private final List<Carte> main;

        private EtatPion(Pion pion) {
            zone = pion.getZone();
            indexCase = pion.getIndexCase();
            toursBloques = pion.getToursBloques();
            protegeCarte = pion.isProtegeCarte();
            toursEpouvantail = pion.getToursEpouvantail();
            cibleAppat = pion.isCibleAppat();
            rang = pion.getRang();
            score = pion.getScore();
            inventaire = new ArrayList<>(pion.getInventaire());
            main = new ArrayList<>(pion.getMain());
        }

        /** @return vrai si les colonnes de la table PION sont identiques */
        private boolean memesColonnes(EtatPion autre) {
            return zone == autre.zone && indexCase == autre.indexCase && toursBloques == autre.toursBloques
                    && protegeCarte == autre.protegeCarte && toursEpouvantail == autre.toursEpouvantail
                    && cibleAppat == autre.cibleAppat && rang == autre.rang && score == autre.score;
        }
    }

    /**
     * Une ligne à écrire dans la table ACTION à la fin de l'action en cours.
     */
    private static final class ActionAEnvoyer {

        private final int numTour;
        private final String type;
        private final Integer idPionAuteur;
        private final Integer idCarte;
        private final Integer idPionCible;
        private final String texte;

        private ActionAEnvoyer(int numTour, String type, Pion auteur, Carte carte, Pion cible, String texte) {
            this.numTour = numTour;
            this.type = type;
            this.idPionAuteur = auteur == null ? null : auteur.getId();
            this.idCarte = carte == null ? null : carte.getId();
            this.idPionCible = cible == null ? null : cible.getId();
            this.texte = texte.length() > TEXTE_MAX ? texte.substring(0, TEXTE_MAX) : texte;
        }
    }
}
