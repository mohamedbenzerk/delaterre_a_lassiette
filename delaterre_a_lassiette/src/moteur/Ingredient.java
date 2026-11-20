/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

import java.text.Normalizer;
import java.util.Locale;

/**
 * Un ingrédient à récolter dans la Ferme (table INGREDIENT).
 * <p>
 * Deux ingrédients sont égaux s'ils ont le même code (par exemple "POMME_DE_TERRE"),
 * même s'ils viennent de deux endroits différents (fichier parcours.txt ou base de données).
 */
public class Ingredient {

    private final int id;
    private final String nom;
    private final TypeIngredient type;
    private final String code;

    /**
     * Crée un ingrédient.
     *
     * @param id identifiant en base (id_ingredient)
     * @param nom nom affiché, par exemple "Pomme de terre"
     * @param type famille de l'ingrédient
     */
    public Ingredient(int id, String nom, TypeIngredient type) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.code = versCode(nom);
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

    /** @return code sans accent ni espace, par exemple "POMME_DE_TERRE" ou "BOEUF" */
    public String getCode() {
        return code;
    }

    /** @return chemin de l'icône, par exemple "resources/icones/pomme_de_terre.png" */
    public String getCheminIcone() {
        return "resources/icones/" + code.toLowerCase(Locale.ROOT) + ".png";
    }

    /**
     * Transforme un nom en code : majuscules, sans accent, "_" à la place des espaces.
     *
     * @param nom nom de l'ingrédient, par exemple "Bœuf" ou "Pomme de terre"
     * @return le code, par exemple "BOEUF" ou "POMME_DE_TERRE"
     */
    public static String versCode(String nom) {
        String texte = nom.replace("œ", "oe").replace("Œ", "OE");
        texte = Normalizer.normalize(texte, Normalizer.Form.NFD).replaceAll("\\p{M}", "");
        texte = texte.toUpperCase(Locale.ROOT).replaceAll("[^A-Z0-9]+", "_");
        return texte.replaceAll("^_+|_+$", "");
    }

    @Override
    public boolean equals(Object autre) {
        if (this == autre) {
            return true;
        }
        if (!(autre instanceof Ingredient)) {
            return false;
        }
        return code.equals(((Ingredient) autre).code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    @Override
    public String toString() {
        return nom;
    }
}
