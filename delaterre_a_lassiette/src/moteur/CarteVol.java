/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

/**
 * Vol : la cible perd 1 ingrédient (seulement si elle est dans la Ferme).
 */
public class CarteVol extends CarteAttaque {

    /**
     * Crée la carte.
     *
     * @param id identifiant en base
     * @param nom nom exact de la carte
     */
    public CarteVol(int id, String nom) {
        super(id, nom);
    }

    @Override
    public void appliquer(Pion joueur, Pion cible, Jeu jeu) {
        // TODO MO-3 : si cible dans la Ferme et !cibleProtegee(cible), cible.perdreIngredient()
    }

    @Override
    protected String getNomFichierImage() {
        return "carte_vol.png";
    }
}
