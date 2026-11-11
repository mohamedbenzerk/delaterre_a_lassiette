/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevIG_MONSTER (IG-1 à IG-4).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

/**
 * Le Sanglier, monstre de la Ferme (Version 1) : il vole 1 ingrédient,
 * ou fait reculer de 2 cases si la cible n'a rien.
 */
public class Sanglier extends Monstre {

    /**
     * Crée le Sanglier à sa place de repos.
     *
     * @param xRepos abscisse de repos en pixels
     * @param yRepos ordonnée de repos en pixels
     */
    public Sanglier(int xRepos, int yRepos) {
        super(Zone.FERME, xRepos, yRepos, "resources/sprites/sanglier.png");
    }

    @Override
    public String getType() {
        return "SANGLIER";
    }

    @Override
    public void agir(Pion cible, Jeu jeu) {
        // TODO IG-4 : if (cible.perdreIngredient() == null) { cible.reculer(2); }
    }
}
