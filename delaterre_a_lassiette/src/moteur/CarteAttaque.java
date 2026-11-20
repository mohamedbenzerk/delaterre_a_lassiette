/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

/**
 * Carte d'attaque : le joueur choisit un adversaire.
 * <p>
 * Le déroulement est écrit une seule fois ici : si l'attaque peut agir sur la cible
 * et que la cible n'a pas de Bouclier, l'effet propre à chaque carte est appliqué
 * par appliquerAttaque() (polymorphisme). Un Bouclier est consommé et annule l'attaque.
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
     * Indique si l'attaque peut agir sur la cible (par défaut : oui).
     * Par exemple, la carte Vol n'agit que dans la Ferme.
     *
     * @param cible pion visé
     * @return vrai si l'attaque a un effet possible
     */
    protected boolean agitSur(Pion cible) {
        return true;
    }

    /**
     * Applique l'effet propre à la carte (la cible n'est pas protégée).
     *
     * @param joueur pion du joueur qui attaque
     * @param cible pion visé
     * @param jeu la partie en cours
     */
    protected abstract void appliquerAttaque(Pion joueur, Pion cible, Jeu jeu);

    /**
     * Vérifie le Bouclier de la cible et le consomme s'il est actif.
     *
     * @param cible pion visé
     * @return vrai si l'attaque est annulée
     */
    protected boolean cibleProtegee(Pion cible) {
        if (cible.isProtegeCarte()) {
            cible.setProtegeCarte(false);
            return true;
        }
        return false;
    }

    @Override
    public void appliquer(Pion joueur, Pion cible, Jeu jeu) {
        if (cible == null) {
            jeu.ajouterAuJournal(nom + " : aucun adversaire à viser, la carte est sans effet.");
        } else if (!agitSur(cible)) {
            jeu.ajouterAuJournal(nom + " : sans effet sur " + cible + ".");
        } else if (cibleProtegee(cible)) {
            jeu.ajouterAuJournal("Le Bouclier de " + cible + " annule l'attaque !");
        } else {
            appliquerAttaque(joueur, cible, jeu);
        }
    }
}
