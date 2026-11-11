/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevBDD (BDD-1 à BDD-4).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package sql;

import java.util.ArrayList;
import java.util.List;
import moteur.Pion;

/**
 * Accès aux tables PION et INVENTAIRE.
 */
public class PionSQL {
    /**
     * Charge les pions complets d'une partie : position, recette, inventaire, main, états.
     * 
     * @param idPartie partie
     * @return les pions
     */
    public List<Pion> listerPions(int idPartie) {
        // TODO BDD-2
        return new ArrayList<>();
    }

    /**
     * @param idPion pion
     * @param pret vrai si le joueur est prêt
     */
    public void setPret(int idPion, boolean pret) {
        // TODO BDD-4
    }

    /**
     * @param idPion pion
     * @param idRecette recette choisie
     */
    public void setRecette(int idPion, int idRecette) {
        // TODO BDD-4
    }

    /**
     * Enregistre zone, index_case, tours_bloques, protections, appât, rang et score.
     * 
     * @param pion pion à sauvegarder
     */
    public void sauvegarder(Pion pion) {
        // TODO BDD-2
    }

    /**
     * @param idPion pion
     * @param idIngredient ingrédient récolté
     */
    public void ajouterIngredient(int idPion, int idIngredient) {
        // TODO BDD-3
    }

    /**
     * @param idPion pion
     * @param idIngredient ingrédient perdu
     */
    public void retirerIngredient(int idPion, int idIngredient) {
        // TODO BDD-3
    }
}
