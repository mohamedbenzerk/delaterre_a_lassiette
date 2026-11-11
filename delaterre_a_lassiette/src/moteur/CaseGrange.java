/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

/**
 * Sortie de la Ferme vers la Route (Ferme, index 11).
 */
public class CaseGrange extends Case {

    /**
     * Crée la case.
     *
     * @param zone zone de la case
     * @param index position dans la zone
     * @param colonne colonne de la tuile en haut à gauche
     * @param ligne ligne de la tuile en haut à gauche
     */
    public CaseGrange(Zone zone, int index, int colonne, int ligne) {
        super(zone, index, colonne, ligne);
    }

    @Override
    public void appliquerEffet(Pion pion, Jeu jeu) {
        // TODO MO-2 : la sortie se fait dans Pion.avancer() quand le pion PASSE par la Grange
        // avec ses 4 ingrédients ; s'il s'arrête dessus sans les avoir, rien ne se passe.
    }
}
