/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Le plateau : le décor (TileMap) et les 36 cases du parcours
 * (16 dans la Ferme, 11 sur la Route, 9 dans la Cuisine).
 */
public class Plateau implements Dessinable {

    /** Fichier qui décrit les 36 cases, dans src/resources. */
    public static final String FICHIER_PARCOURS = "resources/tilemap/parcours.txt";

    private final TileMap tileMap;
    private final Map<Zone, List<Case>> cases;

    /**
     * Crée le plateau et lit le fichier parcours.txt.
     *
     * @param tileMap décor du plateau
     */
    public Plateau(TileMap tileMap) {
        this.tileMap = tileMap;
        this.cases = new EnumMap<>(Zone.class);
        for (Zone zone : Zone.values()) {
            cases.put(zone, new ArrayList<>());
        }
        chargerParcours();
    }

    /**
     * Lit parcours.txt et crée les cases (zone;index;colonne;ligne;type;parametre).
     */
    private void chargerParcours() {
        // TODO MO-1
    }

    /**
     * @param zone zone voulue
     * @param index position dans la zone
     * @return la case, ou null si elle n'existe pas
     */
    public Case getCase(Zone zone, int index) {
        List<Case> liste = cases.get(zone);
        if (index < 0 || index >= liste.size()) {
            return null;
        }
        return liste.get(index);
    }

    /**
     * @param zone zone voulue
     * @return nombre de cases de la zone (FERME 16, ROUTE 11, CUISINE 9)
     */
    public int nbCases(Zone zone) {
        return cases.get(zone).size();
    }

    /** @return décor du plateau */
    public TileMap getTileMap() {
        return tileMap;
    }

    @Override
    public void rendu(Graphics2D contexte) {
        if (tileMap != null) {
            tileMap.rendu(contexte);
        }
        for (List<Case> liste : cases.values()) {
            for (Case c : liste) {
                c.rendu(contexte);
            }
        }
    }
}
