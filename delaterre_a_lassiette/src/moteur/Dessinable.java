/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

import java.awt.Graphics2D;

/**
 * Objet qui sait se dessiner dans la fenêtre de jeu.
 * <p>
 * Implémentée par Plateau, Case, Pion, Monstre et GestionMonstres.
 * Jeu.rendu() appelle rendu() sur chaque objet, dans l'ordre :
 * décor, cases, monstres, pions.
 */
public interface Dessinable {

    /**
     * Dessine l'objet.
     *
     * @param contexte contexte graphique de la fenêtre de jeu
     */
    void rendu(Graphics2D contexte);
}
