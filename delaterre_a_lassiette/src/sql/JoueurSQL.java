/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevBDD (BDD-1 à BDD-4).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package sql;


/**
 * Accès à la table JOUEUR.
 */
public class JoueurSQL {
    /**
     * Connecte un joueur : le crée s'il n'existe pas.
     * 
     * @param pseudo pseudo saisi
     * @return id_joueur
     */
    public int connecter(String pseudo) {
        // TODO BDD-2
        return -1;
    }

    /**
     * Met à jour les statistiques du joueur à la fin d'une partie.
     * 
     * @param idJoueur joueur
     * @param rang rang obtenu (1 à 4)
     */
    public void enregistrerResultat(int idJoueur, int rang) {
        // TODO BDD-4
    }
}
