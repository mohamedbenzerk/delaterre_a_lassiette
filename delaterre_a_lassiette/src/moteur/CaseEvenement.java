/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

/**
 * Case Événement de la Route : BOUCHON, RACCOURCI ou STATION.
 */
public class CaseEvenement extends Case {

    private final String type;

    /**
     * Crée la case.
     *
     * @param zone zone de la case
     * @param index position dans la zone
     * @param colonne colonne de la tuile en haut à gauche
     * @param ligne ligne de la tuile en haut à gauche
     * @param type BOUCHON (recule de 1), RACCOURCI (avance de 2) ou STATION (pioche 1 carte)
     */
    public CaseEvenement(Zone zone, int index, int colonne, int ligne, String type) {
        super(zone, index, colonne, ligne);
        this.type = type;
    }

    /** @return BOUCHON (recule de 1), RACCOURCI (avance de 2) ou STATION (pioche 1 carte) */
    public String getType() {
        return type;
    }

    @Override
    public void appliquerEffet(Pion pion, Jeu jeu) {
        // TODO MO-2 : selon type, pion.reculer(1), pion.avancer(2) ou une carte en plus.
        // L'effet de la nouvelle case n'est pas appliqué (pas d'enchaînement).
    }
}
