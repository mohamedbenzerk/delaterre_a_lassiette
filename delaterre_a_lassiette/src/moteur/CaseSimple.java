/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

import java.util.Locale;

/**
 * Case sans effet (Route, étapes de la Cuisine : entrée, laver, couper, cuire, dresser).
 */
public class CaseSimple extends Case {

    /** Paramètre de parcours.txt quand la case n'a pas d'étape. */
    public static final String SANS_ETAPE = "-";

    private final String etape;

    /**
     * Crée la case.
     *
     * @param zone zone de la case
     * @param index position dans la zone
     * @param colonne colonne de la tuile en haut à gauche
     * @param ligne ligne de la tuile en haut à gauche
     * @param etape étape affichée (ENTREE, LAVER, COUPER, CUIRE, DRESSER) ou « - »
     */
    public CaseSimple(Zone zone, int index, int colonne, int ligne, String etape) {
        super(zone, index, colonne, ligne);
        this.etape = etape;
    }

    /** @return étape affichée (ENTREE, LAVER…) ou « - » */
    public String getEtape() {
        return etape;
    }

    @Override
    public void appliquerEffet(Pion pion, Jeu jeu) {
        // Pas d'effet.
    }

    @Override
    protected String getNomIcone() {
        if (etape == null || SANS_ETAPE.equals(etape)) {
            return null;
        }
        return etape.toLowerCase(Locale.ROOT) + ".png";
    }
}
