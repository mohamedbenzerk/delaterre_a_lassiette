/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevBDD (BDD-1 à BDD-4).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package sql;


/**
 * Accès à la table MONSTRE.
 */
public class MonstreSQL {
    /**
     * Crée les monstres de la partie (Version 1 : le Sanglier).
     * 
     * @param idPartie partie
     */
    public void initialiser(int idPartie) {
        // TODO BDD-4
    }

    /**
     * @param idPartie partie
     * @param type SANGLIER, TEMPETE ou RAT
     * @param zone FERME, ROUTE ou CUISINE
     * @param indexCase position du monstre
     */
    public void sauvegarder(int idPartie, String type, String zone, int indexCase) {
        // TODO BDD-4
    }
}
