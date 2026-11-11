/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

/**
 * Carte « Avance 1 » à « Avance 4 » : le pion avance de valeur cases.
 */
public class CarteMouvement extends Carte {

    /**
     * Crée une carte Mouvement.
     *
     * @param id identifiant en base
     * @param nom nom exact, par exemple "Avance 3"
     * @param valeur nombre de cases (1 à 4)
     */
    public CarteMouvement(int id, String nom, int valeur) {
        super(id, nom, valeur);
    }

    @Override
    public String getFamille() {
        return "MOUVEMENT";
    }

    @Override
    public boolean necessiteCible() {
        return false;
    }

    @Override
    public void appliquer(Pion joueur, Pion cible, Jeu jeu) {
        // TODO MO-1 : joueur.avancer(valeur)
    }

    @Override
    protected String getNomFichierImage() {
        return "carte_avance" + valeur + ".png";
    }
}
