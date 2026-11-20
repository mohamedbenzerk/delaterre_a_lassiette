/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

/**
 * Sortie de la Ferme vers la Route (Ferme, index 11).
 * <p>
 * Un pion qui PASSE par la Grange avec ses 4 ingrédients continue sur la Route
 * (c'est géré dans Pion.avancer()). S'il S'ARRÊTE sur la Grange avec ses 4 ingrédients,
 * il sort aussi : il va sur la première case de la Route.
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
        if (pion.aTousLesIngredients()) {
            pion.avancer(1);
            jeu.ajouterAuJournal(pion + " sort de la Ferme par la Grange : en route !");
        }
    }

    @Override
    protected String getNomIcone() {
        return "grange.png";
    }
}
