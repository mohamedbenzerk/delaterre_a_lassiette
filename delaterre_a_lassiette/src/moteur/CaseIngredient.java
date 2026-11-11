/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

/**
 * Case où l'on récolte un ingrédient (Ferme).
 */
public class CaseIngredient extends Case {

    private final Ingredient ingredient;

    /**
     * Crée la case.
     *
     * @param zone zone de la case
     * @param index position dans la zone
     * @param colonne colonne de la tuile en haut à gauche
     * @param ligne ligne de la tuile en haut à gauche
     * @param ingredient ingrédient récolté sur cette case
     */
    public CaseIngredient(Zone zone, int index, int colonne, int ligne, Ingredient ingredient) {
        super(zone, index, colonne, ligne);
        this.ingredient = ingredient;
    }

    /** @return ingrédient récolté sur cette case */
    public Ingredient getIngredient() {
        return ingredient;
    }

    @Override
    public void appliquerEffet(Pion pion, Jeu jeu) {
        // TODO MO-2 : si pion.getRecette() contient l'ingrédient et que le pion ne l'a pas encore,
        // pion.ajouterIngredient(ingredient) et message dans le journal.
    }
}
