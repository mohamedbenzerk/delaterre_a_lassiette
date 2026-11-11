/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

/**
 * Sécheresse : la cible recule de 2 cases.
 */
public class CarteSecheresse extends CarteAttaque {

    /**
     * Crée la carte.
     *
     * @param id identifiant en base
     * @param nom nom exact de la carte
     */
    public CarteSecheresse(int id, String nom) {
        super(id, nom);
    }

    @Override
    public void appliquer(Pion joueur, Pion cible, Jeu jeu) {
        // TODO MO-3 : si !cibleProtegee(cible), cible.reculer(2)
    }

    @Override
    protected String getNomFichierImage() {
        return "carte_secheresse.png";
    }
}
