/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevIG_MONSTER (IG-1 à IG-4).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package ig;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import moteur.Jeu;

/**
 * Panneau du joueur, à droite du plateau (320 x 640) : joueur dont c'est le tour,
 * recette et ingrédients, 3 cartes cliquables, journal.
 */
public class PanneauHUD extends JPanel implements MouseListener {

    private Jeu jeu;

    /**
     * Crée le panneau du joueur.
     */
    public PanneauHUD() {
        setPreferredSize(new Dimension(320, 640));
        setFocusable(false);
        addMouseListener(this);
    }

    /** @param jeu la partie affichée */
    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // TODO IG-2 : dessiner le joueur courant, la recette, les 3 cartes et le journal
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO IG-2 : carte cliquée → choix de la cible si besoin → jeu.jouerCarte(carte, cible)
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Rien à faire.
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Rien à faire.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Rien à faire.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Rien à faire.
    }
}
