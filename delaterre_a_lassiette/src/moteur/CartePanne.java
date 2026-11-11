/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

/**
 * Panne : la cible passe son prochain tour.
 */
public class CartePanne extends CarteAttaque {

    /**
     * Crée la carte.
     *
     * @param id identifiant en base
     * @param nom nom exact de la carte
     */
    public CartePanne(int id, String nom) {
        super(id, nom);
    }

    @Override
    public void appliquer(Pion joueur, Pion cible, Jeu jeu) {
        // TODO MO-3 : si !cibleProtegee(cible), cible.setToursBloques(1)
    }

    @Override
    protected String getNomFichierImage() {
        return "carte_panne.png";
    }
}
