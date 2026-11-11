/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevBDD (BDD-1 à BDD-4).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package sql;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Une seule connexion à la base pour tout le jeu (conseil du prof).
 * <p>
 * L'adresse, l'utilisateur et le mot de passe sont lus dans le fichier bd.properties,
 * à la racine du projet. Ce fichier n'est JAMAIS envoyé sur GitHub (dépôt public) :
 * copier bd.properties.exemple et le remplir.
 */
public final class ConnexionBD {

    private static Connection connexion;

    private ConnexionBD() {
        // Classe utilitaire : pas d'objet.
    }

    /**
     * Ouvre la connexion au premier appel, puis renvoie toujours la même.
     *
     * @return la connexion, ou null si la base n'est pas joignable
     */
    public static Connection get() {
        // TODO BDD-2 : lire bd.properties (url, utilisateur, motdepasse) puis DriverManager.getConnection(...)
        return connexion;
    }

    /**
     * Ferme la connexion (à la fermeture du jeu).
     */
    public static void fermer() {
        try {
            if (connexion != null) {
                connexion.close();
                connexion = null;
            }
        } catch (SQLException e) {
            System.err.println("Erreur à la fermeture de la base : " + e.getMessage());
        }
    }
}
