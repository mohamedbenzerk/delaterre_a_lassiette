/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

/**
 * Épouvantail : le monstre ne peut pas attaquer le joueur pendant 2 tours.
 */
public class CarteEpouvantail extends CarteDefense {

    /** Nombre de tours de protection contre le monstre. */
    public static final int DUREE = 2;

    /**
     * Crée la carte.
     *
     * @param id identifiant en base
     * @param nom nom exact de la carte
     */
    public CarteEpouvantail(int id, String nom) {
        super(id, nom);
    }

    @Override
    public void appliquer(Pion joueur, Pion cible, Jeu jeu) {
        joueur.setToursEpouvantail(DUREE);
        jeu.ajouterAuJournal(joueur + " installe un Épouvantail : protégé du monstre pendant " + DUREE + " tours.");
    }

    @Override
    protected String getNomFichierImage() {
        return "carte_epouvantail.png";
    }
}
