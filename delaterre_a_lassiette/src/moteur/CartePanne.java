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
    protected void appliquerAttaque(Pion joueur, Pion cible, Jeu jeu) {
        cible.setToursBloques(Math.max(cible.getToursBloques(), 1));
        jeu.ajouterAuJournal("Panne : " + cible + " passera son prochain tour.");
    }

    @Override
    protected String getNomFichierImage() {
        return "carte_panne.png";
    }
}
