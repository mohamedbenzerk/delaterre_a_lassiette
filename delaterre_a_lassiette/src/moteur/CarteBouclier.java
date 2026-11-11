/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

/**
 * Bouclier : annule la prochaine carte d'attaque reçue.
 */
public class CarteBouclier extends CarteDefense {

    /**
     * Crée la carte.
     *
     * @param id identifiant en base
     * @param nom nom exact de la carte
     */
    public CarteBouclier(int id, String nom) {
        super(id, nom);
    }

    @Override
    public void appliquer(Pion joueur, Pion cible, Jeu jeu) {
        // TODO MO-3 : joueur.setProtegeCarte(true)
    }

    @Override
    protected String getNomFichierImage() {
        return "carte_bouclier.png";
    }
}
