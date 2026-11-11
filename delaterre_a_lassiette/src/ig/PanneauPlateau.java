/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevIG_MONSTER (IG-1 à IG-4).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package ig;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import moteur.Jeu;

/**
 * Zone de dessin du plateau (960 x 640) : appelle jeu.rendu() à chaque image.
 */
public class PanneauPlateau extends JPanel {

    private Jeu jeu;

    /**
     * Crée le panneau du plateau.
     */
    public PanneauPlateau() {
        setPreferredSize(new Dimension(960, 640));
        setFocusable(false);
    }

    /** @param jeu la partie à dessiner */
    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (jeu != null) {
            jeu.rendu((Graphics2D) g);
        }
    }
}
