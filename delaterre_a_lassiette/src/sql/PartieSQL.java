/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevBDD (BDD-1 à BDD-4).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package sql;

import java.util.ArrayList;
import java.util.List;

/**
 * Accès à la table PARTIE.
 */
public class PartieSQL {
    /**
     * Crée une partie en attente et le pion de l'hôte (ordre 1).
     * 
     * @param nom nom de la partie
     * @param idJoueurHote joueur qui crée la partie
     * @return id_partie
     */
    public int creer(String nom, int idJoueurHote) {
        // TODO BDD-2
        return -1;
    }

    /**
     * @return les parties en attente : {id_partie, nom, nb_joueurs}
     */
    public List<String[]> listerEnAttente() {
        // TODO BDD-4
        return new ArrayList<>();
    }

    /**
     * Ajoute un joueur à la partie (ordre libre suivant).
     * 
     * @param idPartie partie
     * @param idJoueur joueur
     * @return id_pion créé, ou -1 si la partie est pleine
     */
    public int rejoindre(int idPartie, int idJoueur) {
        // TODO BDD-4
        return -1;
    }

    /**
     * @param idPartie partie
     * @param idJoueur joueur
     * @return id_pion du joueur dans cette partie
     */
    public int getIdPion(int idPartie, int idJoueur) {
        // TODO BDD-4
        return -1;
    }

    /**
     * @param idPartie partie
     * @return ATTENTE, CHOIX_RECETTE, EN_COURS ou TERMINEE
     */
    public String getEtat(int idPartie) {
        // TODO BDD-4
        return null;
    }

    /**
     * @param idPartie partie
     * @param etat ATTENTE, CHOIX_RECETTE, EN_COURS ou TERMINEE
     */
    public void setEtat(int idPartie, String etat) {
        // TODO BDD-4
    }

    /**
     * @param idPartie partie
     * @return ordre du joueur dont c'est le tour (1 à 4)
     */
    public int getOrdreCourant(int idPartie) {
        // TODO BDD-3
        return 1;
    }

    /**
     * @param idPartie partie
     * @return numéro du tour en cours
     */
    public int getNumTour(int idPartie) {
        // TODO BDD-3
        return 1;
    }

    /**
     * Donne la main au joueur suivant.
     * 
     * @param idPartie partie
     * @param ordreSuivant ordre du joueur suivant
     * @param numTour numéro du tour
     */
    public void passerTour(int idPartie, int ordreSuivant, int numTour) {
        // TODO BDD-3
    }
}
