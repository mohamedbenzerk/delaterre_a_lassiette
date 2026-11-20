/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

/**
 * Vol : la cible perd 1 ingrédient au hasard (seulement si elle est dans la Ferme).
 */
public class CarteVol extends CarteAttaque {

    /**
     * Crée la carte.
     *
     * @param id identifiant en base
     * @param nom nom exact de la carte
     */
    public CarteVol(int id, String nom) {
        super(id, nom);
    }

    /**
     * Le Vol n'agit que sur un pion de la Ferme (sur la Route, les ingrédients sont dans le camion).
     *
     * @param cible pion visé
     * @return vrai si la cible est dans la Ferme
     */
    @Override
    protected boolean agitSur(Pion cible) {
        return cible.getZone() == Zone.FERME;
    }

    @Override
    protected void appliquerAttaque(Pion joueur, Pion cible, Jeu jeu) {
        Ingredient perdu = cible.perdreIngredient();
        if (perdu == null) {
            jeu.ajouterAuJournal("Vol : " + cible + " n'a aucun ingrédient à perdre.");
        } else {
            jeu.ajouterAuJournal("Vol : " + cible + " perd " + perdu.getNom() + ".");
        }
    }

    @Override
    protected String getNomFichierImage() {
        return "carte_vol.png";
    }
}
