/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

/**
 * Carte d'attaque : le joueur choisit un adversaire.
 * Un adversaire protégé par un Bouclier perd sa protection et ne subit rien.
 */
public abstract class CarteAttaque extends Carte {

    /**
     * Crée une carte d'attaque.
     *
     * @param id identifiant en base
     * @param nom nom exact de la carte
     */
    protected CarteAttaque(int id, String nom) {
        super(id, nom, 0);
    }

    @Override
    public String getFamille() {
        return "ATTAQUE";
    }

    @Override
    public boolean necessiteCible() {
        return true;
    }

    /**
     * Vérifie le Bouclier de la cible et le consomme s'il est actif.
     *
     * @param cible pion visé
     * @return vrai si l'attaque est annulée
     */
    protected boolean cibleProtegee(Pion cible) {
        // TODO MO-3
        return false;
    }
}
