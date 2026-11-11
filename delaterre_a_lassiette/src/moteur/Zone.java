/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

/**
 * Les 3 zones du plateau, dans l'ordre du parcours.
 */
public enum Zone {
    /** Boucle de 16 cases : récolte des ingrédients. */
    FERME,
    /** 11 cases : transport (le pion devient un camion). */
    ROUTE,
    /** 9 cases : préparation du plat jusqu'à l'Assiette. */
    CUISINE
}
