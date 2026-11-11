/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : SM (SM-4).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package ig;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Classement de fin de partie : rangs et scores.
 */
public class EcranClassement extends JPanel {

    private final FenetreDeJeu fenetre;

    /**
     * Crée l'écran.
     *
     * @param fenetre fenêtre principale (changement d'écran, identifiants de session)
     */
    public EcranClassement(FenetreDeJeu fenetre) {
        this.fenetre = fenetre;
        add(new JLabel("EcranClassement — à faire (SM-4)"));
        // TODO SM-4
    }
}
