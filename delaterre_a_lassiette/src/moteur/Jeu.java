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
 * Une partie en cours : plateau, pions, monstre, pioche, tour de jeu.
 * <p>
 * FenetreDeJeu appelle miseAJour() puis rendu() toutes les 40 ms, comme dans le modèle du prof.
 */
public class Jeu {

    private final int idPartie;
    private Plateau plateau;
    private final List<Pion> pions;
    private Pion pionLocal;
    private GestionMonstres gestionMonstres;
    private Pioche pioche;
    private int ordreCourant;
    private int numTour;
    private final List<String> journal;

    /**
     * Partie LOCALE de test : 4 pions sur un seul PC, sans base de données
     * (Release 1 et tests de l'interface). En local, getPionLocal() = getPionCourant()
     * et estMonTour() est toujours vrai.
     */
    public Jeu() {
        this.idPartie = 0;
        this.pions = new ArrayList<>();
        this.journal = new ArrayList<>();
        this.ordreCourant = 1;
        this.numTour = 1;
        // TODO MO-2 : créer le plateau, 4 pions, la pioche locale et le Sanglier
    }

    /**
     * Partie en réseau : tout est chargé depuis la base.
     *
     * @param idPartie identifiant de la partie (id_partie)
     * @param idPionLocal pion du joueur assis devant ce PC (id_pion)
     */
    public Jeu(int idPartie, int idPionLocal) {
        this.idPartie = idPartie;
        this.pions = new ArrayList<>();
        this.journal = new ArrayList<>();
        // TODO MO-2 : charger les pions (PionSQL), la pioche (CarteSQL), le tour (PartieSQL)
    }

    /**
     * Met à jour la partie (toutes les 40 ms) : animations, et lecture de la base toutes les 0,5 s.
     */
    public void miseAJour() {
        // TODO MO-2
    }

    /**
     * Dessine la partie : plateau, cases, monstres, pions.
     *
     * @param contexte contexte graphique du plateau (960 x 640)
     */
    public void rendu(Graphics2D contexte) {
        // TODO MO-2
    }

    /** @return vrai quand 3 joueurs ont atteint l'Assiette */
    public boolean estTermine() {
        // TODO MO-2
        return false;
    }

    /** @return vrai si c'est au joueur de ce PC de jouer */
    public boolean estMonTour() {
        // TODO MO-2
        return idPartie == 0;
    }

    /** @return vrai si le joueur de ce PC est l'hôte (ordre 1) */
    public boolean estHote() {
        return pionLocal == null || pionLocal.getOrdre() == 1;
    }

    /**
     * Joue une carte du joueur courant.
     *
     * @param carte carte jouée (prise dans la main du joueur)
     * @param cible pion visé, ou null si la carte n'en demande pas
     */
    public void jouerCarte(Carte carte, Pion cible) {
        // TODO MO-2 / MO-3 : carte.appliquer(...), effet de la case, pioche, sauvegarde, joueur suivant
    }

    /** @return les pions de la partie */
    public List<Pion> getPions() {
        return pions;
    }

    /** @return pion du joueur de ce PC */
    public Pion getPionLocal() {
        return pionLocal;
    }

    /** @return pion du joueur dont c'est le tour */
    public Pion getPionCourant() {
        for (Pion pion : pions) {
            if (pion.getOrdre() == ordreCourant) {
                return pion;
            }
        }
        return null;
    }

    /** @return le plateau */
    public Plateau getPlateau() {
        return plateau;
    }

    /** @return les messages du journal, du plus ancien au plus récent */
    public List<String> getJournal() {
        return journal;
    }

    /** @return identifiant de la partie (0 en partie locale) */
    public int getIdPartie() {
        return idPartie;
    }

    /** @return numéro du tour en cours */
    public int getNumTour() {
        return numTour;
    }
}
