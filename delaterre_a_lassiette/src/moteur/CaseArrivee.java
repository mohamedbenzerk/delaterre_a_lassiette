/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

/**
 * L'Assiette : dernière case de la Cuisine (fin du plat).
 */
public class CaseArrivee extends Case {

    /**
     * Crée la case.
     *
     * @param zone zone de la case
     * @param index position dans la zone
     * @param colonne colonne de la tuile en haut à gauche
     * @param ligne ligne de la tuile en haut à gauche
     */
    public CaseArrivee(Zone zone, int index, int colonne, int ligne) {
        super(zone, index, colonne, ligne);
    }

    @Override
    public void appliquerEffet(Pion pion, Jeu jeu) {
        // TODO MO-2 : le plat est terminé, le joueur reçoit son rang (jeu donne le rang suivant).
    }
}
