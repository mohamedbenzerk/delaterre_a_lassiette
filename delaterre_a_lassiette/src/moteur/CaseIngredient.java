/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

import java.util.Locale;

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

    /**
     * Le pion récolte l'ingrédient si sa recette en a besoin et s'il ne l'a pas encore.
     *
     * @param pion le pion qui s'arrête sur la case
     * @param jeu la partie en cours
     */
    @Override
    public void appliquerEffet(Pion pion, Jeu jeu) {
        Recette recette = pion.getRecette();
        if (recette == null) {
            return;
        }
        Ingredient utile = recette.trouver(ingredient);
        if (utile != null && !pion.getInventaire().contains(utile)) {
            pion.ajouterIngredient(utile);
            jeu.ajouterAuJournal(pion + " récolte : " + utile.getNom()
                    + " (" + pion.getInventaire().size() + "/" + recette.getIngredients().size() + ")");
        }
    }

    @Override
    protected String getNomIcone() {
        return ingredient.getCode().toLowerCase(Locale.ROOT) + ".png";
    }
}
