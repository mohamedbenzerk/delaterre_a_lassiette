/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevIG_MONSTER (IG-1 à IG-4).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package ig;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import moteur.Jeu;

/**
 * La fenêtre du jeu (1280 x 640), sur le modèle FenetreDeJeu du prof :
 * un Timer de 40 ms appelle jeu.miseAJour() puis redessine.
 * Les écrans sont gérés par un CardLayout.
 */
public class FenetreDeJeu extends JFrame implements ActionListener {

    /** Noms des écrans du CardLayout. */
    public static final String CONNEXION = "CONNEXION";
    public static final String SALLE = "SALLE";
    public static final String RECETTE = "RECETTE";
    public static final String JEU = "JEU";
    public static final String CLASSEMENT = "CLASSEMENT";

    private final CardLayout cartes;
    private final JPanel conteneur;
    private final PanneauPlateau panneauPlateau;
    private final PanneauHUD panneauHUD;
    private final Timer timer;
    private Jeu jeu;
    private int idJoueur;
    private int idPartie;
    private int idPion;

    /**
     * Crée la fenêtre et ses écrans.
     */
    public FenetreDeJeu() {
        super("De la terre à l'assiette");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        cartes = new CardLayout();
        conteneur = new JPanel(cartes);
        panneauPlateau = new PanneauPlateau();
        panneauHUD = new PanneauHUD();

        JPanel ecranJeu = new JPanel(new BorderLayout());
        ecranJeu.add(panneauPlateau, BorderLayout.CENTER);
        ecranJeu.add(panneauHUD, BorderLayout.EAST);

        conteneur.add(new EcranConnexion(this), CONNEXION);
        conteneur.add(new EcranSalleAttente(this), SALLE);
        conteneur.add(new EcranChoixRecette(this), RECETTE);
        conteneur.add(ecranJeu, JEU);
        conteneur.add(new EcranClassement(this), CLASSEMENT);
        setContentPane(conteneur);
        pack();
        setLocationRelativeTo(null);

        timer = new Timer(40, this);
        timer.start();
    }

    /**
     * Affiche un écran.
     *
     * @param nom CONNEXION, SALLE, RECETTE, JEU ou CLASSEMENT
     */
    public void afficherEcran(String nom) {
        cartes.show(conteneur, nom);
    }

    /**
     * Démarre une partie en réseau.
     *
     * @param idPartie identifiant de la partie
     * @param idPionLocal pion du joueur de ce PC
     */
    public void demarrerPartie(int idPartie, int idPionLocal) {
        brancherJeu(new Jeu(idPartie, idPionLocal));
    }

    /**
     * Démarre une partie locale de test, sans base de données.
     */
    public void demarrerPartieLocale() {
        brancherJeu(new Jeu());
    }

    private void brancherJeu(Jeu nouveauJeu) {
        this.jeu = nouveauJeu;
        panneauPlateau.setJeu(nouveauJeu);
        panneauHUD.setJeu(nouveauJeu);
        afficherEcran(JEU);
    }

    /** @return identifiant du joueur connecté */
    public int getIdJoueur() {
        return idJoueur;
    }

    /** @param id identifiant du joueur connecté */
    public void setIdJoueur(int id) {
        this.idJoueur = id;
    }

    /** @return identifiant de la partie */
    public int getIdPartie() {
        return idPartie;
    }

    /** @param id identifiant de la partie */
    public void setIdPartie(int id) {
        this.idPartie = id;
    }

    /** @return identifiant du pion du joueur de ce PC */
    public int getIdPion() {
        return idPion;
    }

    /** @param id identifiant du pion du joueur de ce PC */
    public void setIdPion(int id) {
        this.idPion = id;
    }

    /** @return la partie en cours (null avant le début) */
    public Jeu getJeu() {
        return jeu;
    }

    /**
     * Boucle de jeu appelée par le Timer toutes les 40 ms.
     *
     * @param e événement du Timer
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (jeu != null) {
            jeu.miseAJour();
            panneauPlateau.repaint();
            panneauHUD.repaint();
        }
    }

    /**
     * Lance le jeu.
     *
     * @param args non utilisés
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FenetreDeJeu fenetre = new FenetreDeJeu();
            fenetre.setVisible(true);
            // TODO IG-1 : pour tester sans base, appeler fenetre.demarrerPartieLocale();
        });
    }
}
