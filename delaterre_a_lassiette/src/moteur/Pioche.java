/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * La pioche : 36 cartes (une entrée par exemplaire). On tire une carte au hasard.
 * La pioche ne s'épuise pas : la carte tirée reste disponible pour les tirages suivants.
 */
public class Pioche {

    /** Liste des cartes pour la partie locale (même contenu que la table CARTE). */
    public static final String FICHIER_CARTES = "resources/cartes/liste_cartes.txt";

    private static final Random HASARD = new Random();

    private final List<Carte> catalogue;

    /**
     * Crée la pioche.
     *
     * @param catalogue toutes les cartes, avec leurs exemplaires (fourni par CarteSQL)
     */
    public Pioche(List<Carte> catalogue) {
        this.catalogue = new ArrayList<>(catalogue);
    }

    /**
     * Crée la pioche de la partie locale à partir de liste_cartes.txt
     * (fichier;nom;famille;valeur;nb_exemplaires;effet). Les cartes sont numérotées
     * de 1 à 10 dans l'ordre du fichier, comme dans la table CARTE.
     *
     * @return la pioche de 36 cartes
     * @throws IllegalStateException si le fichier est absent ou mal écrit
     */
    public static Pioche creerPiocheLocale() {
        InputStream flux = Pioche.class.getClassLoader().getResourceAsStream(FICHIER_CARTES);
        if (flux == null) {
            throw new IllegalStateException("Fichier introuvable : src/" + FICHIER_CARTES);
        }
        List<Carte> cartes = new ArrayList<>();
        try (BufferedReader lecteur = new BufferedReader(new InputStreamReader(flux, StandardCharsets.UTF_8))) {
            String ligne;
            int id = 0;
            while ((ligne = lecteur.readLine()) != null) {
                ligne = ligne.trim();
                if (ligne.isEmpty() || ligne.startsWith("#")) {
                    continue;
                }
                String[] champs = ligne.split(";");
                id++;
                int valeur = Integer.parseInt(champs[3].trim());
                int exemplaires = Integer.parseInt(champs[4].trim());
                for (int i = 0; i < exemplaires; i++) {
                    cartes.add(Carte.creer(id, champs[1].trim(), champs[2].trim(), valeur));
                }
            }
        } catch (IOException | RuntimeException e) {
            throw new IllegalStateException("Lecture impossible de " + FICHIER_CARTES + " : " + e.getMessage(), e);
        }
        return new Pioche(cartes);
    }

    /** @return nombre de cartes du catalogue (36) */
    public int taille() {
        return catalogue.size();
    }

    /**
     * Tire une carte au hasard.
     *
     * @return la carte tirée, ou null si la pioche est vide
     */
    public Carte tirer() {
        if (catalogue.isEmpty()) {
            return null;
        }
        return catalogue.get(HASARD.nextInt(catalogue.size()));
    }
}
