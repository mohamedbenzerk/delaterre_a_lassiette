/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * Charge les images de src/resources une seule fois, puis les garde en mémoire.
 * <p>
 * Exemple : {@code Images.charger("resources/icones/carotte.png")}.
 * Toutes les classes du moteur peuvent s'en servir (cases, pions, cartes, monstres).
 */
public final class Images {

    private static final Map<String, BufferedImage> CACHE = new HashMap<>();

    private Images() {
        // Classe utilitaire : pas d'objet.
    }

    /**
     * Renvoie l'image demandée, lue une seule fois dans src/resources.
     *
     * @param chemin chemin depuis src, par exemple "resources/sprites/pion_bleu.png"
     * @return l'image, ou null si le fichier est introuvable (message dans la console)
     */
    public static BufferedImage charger(String chemin) {
        if (CACHE.containsKey(chemin)) {
            return CACHE.get(chemin);
        }
        BufferedImage image = null;
        URL url = Images.class.getClassLoader().getResource(chemin);
        if (url == null) {
            System.err.println("Image introuvable : " + chemin);
        } else {
            try {
                image = ImageIO.read(url);
            } catch (IOException e) {
                System.err.println("Image illisible : " + chemin + " (" + e.getMessage() + ")");
            }
        }
        CACHE.put(chemin, image);
        return image;
    }
}
