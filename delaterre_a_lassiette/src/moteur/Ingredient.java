/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

/**
 * Un ingrédient à récolter dans la Ferme (table INGREDIENT).
 */
public class Ingredient {

    private final int id;
    private final String nom;
    private final TypeIngredient type;

    /**
     * Crée un ingrédient.
     *
     * @param id identifiant en base (id_ingredient)
     * @param nom nom affiché, par exemple "Carotte"
     * @param type famille de l'ingrédient
     */
    public Ingredient(int id, String nom, TypeIngredient type) {
        this.id = id;
        this.nom = nom;
        this.type = type;
    }

    /** @return identifiant en base */
    public int getId() {
        return id;
    }

    /** @return nom de l'ingrédient */
    public String getNom() {
        return nom;
    }

    /** @return famille de l'ingrédient */
    public TypeIngredient getType() {
        return type;
    }

    @Override
    public String toString() {
        return nom;
    }
}
