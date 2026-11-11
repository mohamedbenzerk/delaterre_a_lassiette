/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevBDD (BDD-1 à BDD-4).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package sql;

import java.util.ArrayList;
import java.util.List;

/**
 * Accès à la table ACTION : journal de la partie et synchronisation des postes.
 */
public class ActionSQL {
    /**
     * Écrit une action dans le journal.
     * 
     * @param idPartie partie
     * @param numTour numéro du tour
     * @param idPionAuteur pion qui joue (null pour le monstre)
     * @param type CARTE, MONSTRE, PASSE, INFO ou FIN
     * @param idCarte carte jouée (ou null)
     * @param idPionCible pion visé (ou null)
     * @param texte message du journal
     */
    public void ajouter(int idPartie, int numTour, Integer idPionAuteur, String type,
            Integer idCarte, Integer idPionCible, String texte) {
        // TODO BDD-3
    }

    /**
     * Lit les actions plus récentes que la dernière déjà vue (appelée toutes les 0,5 s).
     * 
     * @param idPartie partie
     * @param idDerniereAction dernière action déjà lue
     * @return les nouvelles actions
     */
    public List<LigneAction> lireDepuis(int idPartie, int idDerniereAction) {
        // TODO BDD-3
        return new ArrayList<>();
    }
}
