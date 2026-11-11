/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

/**
 * Case sans effet (Route, étapes de la Cuisine : laver, couper, cuire, dresser).
 */
public class CaseSimple extends Case {

    private final String etape;

    /**
     * Crée la case.
     *
     * @param zone zone de la case
     * @param index position dans la zone
     * @param colonne colonne de la tuile en haut à gauche
     * @param ligne ligne de la tuile en haut à gauche
     * @param etape étape affichée (LAVER, COUPER…) ou « - »
     */
    public CaseSimple(Zone zone, int index, int colonne, int ligne, String etape) {
        super(zone, index, colonne, ligne);
        this.etape = etape;
    }

    /** @return étape affichée (LAVER, COUPER…) ou « - » */
    public String getEtape() {
        return etape;
    }

    @Override
    public void appliquerEffet(Pion pion, Jeu jeu) {
        // Pas d'effet.
    }
}
