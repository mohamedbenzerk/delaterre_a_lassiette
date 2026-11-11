/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

/**
 * Objet qui bouge à l'écran (déplacement case par case animé).
 * <p>
 * Implémentée par Pion, Monstre et GestionMonstres.
 * Jeu.miseAJour() appelle miseAJour() sur chaque objet toutes les 40 ms.
 */
public interface Animable {

    /**
     * Fait avancer l'animation d'une image (appelée toutes les 40 ms).
     */
    void miseAJour();

    /**
     * Indique si l'objet est en train de se déplacer.
     *
     * @return vrai pendant toute l'animation
     */
    boolean estEnMouvement();
}
