/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

/**
 * Carte de défense : elle protège le joueur qui la joue.
 */
public abstract class CarteDefense extends Carte {

    /**
     * Crée une carte de défense.
     *
     * @param id identifiant en base
     * @param nom nom exact de la carte
     */
    protected CarteDefense(int id, String nom) {
        super(id, nom, 0);
    }

    @Override
    public String getFamille() {
        return "DEFENSE";
    }

    @Override
    public boolean necessiteCible() {
        return false;
    }
}
