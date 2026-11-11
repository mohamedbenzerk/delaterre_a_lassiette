/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevBDD (BDD-1 à BDD-4).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package ig;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Salle d'attente : créer ou rejoindre une partie, Prêt, Lancer (rafraîchi toutes les 0,5 s).
 */
public class EcranSalleAttente extends JPanel {

    private final FenetreDeJeu fenetre;

    /**
     * Crée l'écran.
     *
     * @param fenetre fenêtre principale (changement d'écran, identifiants de session)
     */
    public EcranSalleAttente(FenetreDeJeu fenetre) {
        this.fenetre = fenetre;
        add(new JLabel("EcranSalleAttente — à faire (BDD-4)"));
        // TODO BDD-4
    }
}
