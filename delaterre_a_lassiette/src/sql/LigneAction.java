/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevBDD (BDD-1 à BDD-4).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package sql;

/**
 * Une ligne de la table ACTION (journal de la partie), lue par les autres postes.
 */
public class LigneAction {

    public final int idAction;
    public final int numTour;
    public final String type;
    public final Integer idPionAuteur;
    public final Integer idCarte;
    public final Integer idPionCible;
    public final String texte;

    /**
     * Crée une ligne d'action.
     *
     * @param idAction identifiant (croissant)
     * @param numTour numéro du tour
     * @param type "CARTE", "MONSTRE", "PASSE", "INFO" ou "FIN"
     * @param idPionAuteur pion qui a joué (null pour le monstre)
     * @param idCarte carte jouée (ou null)
     * @param idPionCible pion visé (ou null)
     * @param texte message du journal
     */
    public LigneAction(int idAction, int numTour, String type, Integer idPionAuteur,
            Integer idCarte, Integer idPionCible, String texte) {
        this.idAction = idAction;
        this.numTour = numTour;
        this.type = type;
        this.idPionAuteur = idPionAuteur;
        this.idCarte = idCarte;
        this.idPionCible = idPionCible;
        this.texte = texte;
    }
}
