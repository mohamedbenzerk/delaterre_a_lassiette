/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevIG_MONSTER (IG-1 à IG-4).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Le tour du monstre, après chaque tour complet. Version 1 : seulement le Sanglier.
 */
public class GestionMonstres implements Dessinable, Animable {

    private final List<Monstre> monstres;
    private final Random hasard;

    /**
     * Crée les monstres de la partie (Version 1 : le Sanglier).
     *
     * @param plateau plateau (positions des cases et place de repos du monstre)
     */
    public GestionMonstres(Plateau plateau) {
        this.monstres = new ArrayList<>();
        this.hasard = new Random();
        // TODO IG-3 : lire la place de repos dans parcours.txt (ligne MONSTRE;SANGLIER;...)
    }

    /**
     * Tour du monstre, calculé sur le poste de l'hôte : 1 chance sur 2, choix de la cible,
     * déplacement, puis effet.
     *
     * @param pions les pions de la partie
     * @param jeu la partie en cours
     * @return le pion visé, ou null si le monstre n'agit pas
     */
    public Pion tourDuMonstre(List<Pion> pions, Jeu jeu) {
        // TODO IG-4 : Appât prioritaire ; sinon tirage parmi les pions de la Ferme (le premier compte 2 fois)
        return null;
    }

    /**
     * Sur les autres postes : seulement l'animation (l'effet est déjà dans la base).
     *
     * @param typeMonstre "SANGLIER", "TEMPETE" ou "RAT"
     * @param cible pion visé
     */
    public void animer(String typeMonstre, Pion cible) {
        // TODO IG-4
    }

    @Override
    public void miseAJour() {
        for (Monstre monstre : monstres) {
            monstre.miseAJour();
        }
    }

    @Override
    public boolean estEnMouvement() {
        for (Monstre monstre : monstres) {
            if (monstre.estEnMouvement()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void rendu(Graphics2D contexte) {
        for (Monstre monstre : monstres) {
            monstre.rendu(contexte);
        }
    }
}
