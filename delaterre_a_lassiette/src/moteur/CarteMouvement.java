/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

/**
 * Carte « Avance 1 » à « Avance 4 » : le pion avance de valeur cases,
 * puis l'effet de la case d'arrivée s'applique.
 */
public class CarteMouvement extends Carte {

    /**
     * Règle de récolte, à valider par l'équipe.
     * false (règle actuelle) : on récolte seulement l'ingrédient de la case où l'on s'arrête.
     * true : on récolte aussi les ingrédients utiles des cases où l'on passe (partie plus courte).
     */
    public static final boolean RECOLTE_EN_PASSANT = false;

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
        Zone zoneDepart = joueur.getZone();
        if (RECOLTE_EN_PASSANT) {
            for (int pas = 1; pas < valeur; pas++) {
                joueur.avancer(1);
                Case passage = jeu.getPlateau().getCase(joueur.getZone(), joueur.getIndexCase());
                if (passage instanceof CaseIngredient) {
                    passage.appliquerEffet(joueur, jeu);
                }
            }
            joueur.avancer(1);
        } else {
            joueur.avancer(valeur);
        }
        if (joueur.getZone() == Zone.ROUTE && zoneDepart == Zone.FERME) {
            jeu.ajouterAuJournal(joueur + " sort de la Ferme par la Grange : en route !");
        } else if (joueur.getZone() == Zone.CUISINE && zoneDepart == Zone.ROUTE) {
            jeu.ajouterAuJournal(joueur + " arrive en Cuisine.");
        }
        Case arrivee = jeu.getPlateau().getCase(joueur.getZone(), joueur.getIndexCase());
        if (arrivee != null) {
            arrivee.appliquerEffet(joueur, jeu);
        }
    }

    @Override
    protected String getNomFichierImage() {
        return "carte_avance" + valeur + ".png";
    }
}
