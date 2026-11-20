/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

import java.util.Locale;

/**
 * Case Événement de la Route : BOUCHON, RACCOURCI ou STATION.
 * <p>
 * L'effet de la case où le pion arrive ensuite n'est pas appliqué (pas d'enchaînement).
 */
public class CaseEvenement extends Case {

    /** Le camion recule de 1 case. */
    public static final String BOUCHON = "BOUCHON";
    /** Le camion avance de 2 cases. */
    public static final String RACCOURCI = "RACCOURCI";
    /** Station-service : le joueur pioche une carte en plus et rejoue. */
    public static final String STATION = "STATION";

    private final String type;

    /**
     * Crée la case.
     *
     * @param zone zone de la case
     * @param index position dans la zone
     * @param colonne colonne de la tuile en haut à gauche
     * @param ligne ligne de la tuile en haut à gauche
     * @param type BOUCHON (recule de 1), RACCOURCI (avance de 2) ou STATION (carte en plus)
     */
    public CaseEvenement(Zone zone, int index, int colonne, int ligne, String type) {
        super(zone, index, colonne, ligne);
        this.type = type;
    }

    /** @return BOUCHON, RACCOURCI ou STATION */
    public String getType() {
        return type;
    }

    @Override
    public void appliquerEffet(Pion pion, Jeu jeu) {
        switch (type) {
            case BOUCHON:
                pion.reculer(1);
                jeu.ajouterAuJournal("Bouchon : " + pion + " recule de 1 case.");
                break;
            case RACCOURCI:
                pion.avancer(2);
                jeu.ajouterAuJournal("Raccourci : " + pion + " avance de 2 cases.");
                break;
            case STATION:
                jeu.donnerCarteBonus(pion);
                break;
            default:
                System.err.println("Événement inconnu dans parcours.txt : " + type);
        }
    }

    @Override
    protected String getNomIcone() {
        return type.toLowerCase(Locale.ROOT) + ".png";
    }
}
