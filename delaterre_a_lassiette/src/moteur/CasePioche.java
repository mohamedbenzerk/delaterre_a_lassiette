/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

/**
 * Case qui donne une carte en plus (Ferme).
 */
public class CasePioche extends Case {

    /**
     * Crée la case.
     *
     * @param zone zone de la case
     * @param index position dans la zone
     * @param colonne colonne de la tuile en haut à gauche
     * @param ligne ligne de la tuile en haut à gauche
     */
    public CasePioche(Zone zone, int index, int colonne, int ligne) {
        super(zone, index, colonne, ligne);
    }

    @Override
    public void appliquerEffet(Pion pion, Jeu jeu) {
        // TODO MO-2 : le joueur pioche une carte en plus (jeu.getPioche()).
    }
}
