/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevBDD (BDD-1 à BDD-4).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package sql;

import java.util.ArrayList;
import java.util.List;
import moteur.Carte;

/**
 * Accès aux tables CARTE et MAIN.
 */
public class CarteSQL {
    /**
     * Charge les 36 cartes (une par exemplaire), créées avec Carte.creer(...).
     * 
     * @return le catalogue
     */
    public List<Carte> catalogue() {
        // TODO BDD-3
        return new ArrayList<>();
    }

    /**
     * @param idPion pion
     * @param emplacement 1 à 3
     * @param idCarte carte posée
     */
    public void poserDansMain(int idPion, int emplacement, int idCarte) {
        // TODO BDD-3
    }

    /**
     * @param idPion pion
     * @param emplacement 1 à 3
     */
    public void retirerDeMain(int idPion, int emplacement) {
        // TODO BDD-3
    }
}
