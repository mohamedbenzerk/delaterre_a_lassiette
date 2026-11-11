/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevBDD (BDD-1 à BDD-4).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package sql;

import java.util.ArrayList;
import java.util.List;
import moteur.Recette;

/**
 * Accès aux tables RECETTE, INGREDIENT et COMPOSITION.
 */
public class RecetteSQL {
    /**
     * @return les 4 recettes avec leurs ingrédients
     */
    public List<Recette> listerRecettes() {
        // TODO BDD-4
        return new ArrayList<>();
    }
}
