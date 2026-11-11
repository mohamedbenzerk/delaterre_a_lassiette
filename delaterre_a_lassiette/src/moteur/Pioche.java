/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * La pioche : 36 cartes (une entrée par exemplaire). On tire une carte au hasard.
 */
public class Pioche {

    private final List<Carte> catalogue;
    private final Random hasard;

    /**
     * Crée la pioche.
     *
     * @param catalogue toutes les cartes, avec leurs exemplaires (fourni par CarteSQL)
     */
    public Pioche(List<Carte> catalogue) {
        this.catalogue = new ArrayList<>(catalogue);
        this.hasard = new Random();
    }

    /**
     * Tire une carte au hasard (la pioche ne s'épuise pas).
     *
     * @return la carte tirée, ou null si la pioche est vide
     */
    public Carte tirer() {
        if (catalogue.isEmpty()) {
            return null;
        }
        return catalogue.get(hasard.nextInt(catalogue.size()));
    }
}
