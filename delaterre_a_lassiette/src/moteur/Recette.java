/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Un plat à préparer : 4 ingrédients (table RECETTE + COMPOSITION).
 */
public class Recette {

    private final int id;
    private final String nom;
    private final List<Ingredient> ingredients;

    /**
     * Crée une recette.
     *
     * @param id identifiant en base (id_recette)
     * @param nom nom du plat, par exemple "Tajine"
     * @param ingredients les 4 ingrédients du plat
     */
    public Recette(int id, String nom, List<Ingredient> ingredients) {
        this.id = id;
        this.nom = nom;
        this.ingredients = new ArrayList<>(ingredients);
    }

    /** @return identifiant en base */
    public int getId() {
        return id;
    }

    /** @return nom du plat */
    public String getNom() {
        return nom;
    }

    /** @return les ingrédients du plat (liste non modifiable) */
    public List<Ingredient> getIngredients() {
        return Collections.unmodifiableList(ingredients);
    }

    @Override
    public String toString() {
        return nom;
    }
}
