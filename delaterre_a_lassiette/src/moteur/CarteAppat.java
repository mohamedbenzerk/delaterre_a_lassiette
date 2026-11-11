/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

/**
 * Appât : au prochain tour du monstre, il va chez la cible.
 */
public class CarteAppat extends CarteAttaque {

    /**
     * Crée la carte.
     *
     * @param id identifiant en base
     * @param nom nom exact de la carte
     */
    public CarteAppat(int id, String nom) {
        super(id, nom);
    }

    @Override
    public void appliquer(Pion joueur, Pion cible, Jeu jeu) {
        // TODO MO-3 : si !cibleProtegee(cible), cible.setCibleAppat(true)
    }

    @Override
    protected String getNomFichierImage() {
        return "carte_appat.png";
    }
}
