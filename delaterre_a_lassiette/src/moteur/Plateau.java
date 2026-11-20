/*
 * De la terre à l'assiette — projet INFO2 (SUPMICROTECH-ENSMM)
 * Propriétaire de ce fichier : DevMO (MO-1 à MO-3).
 * Signatures publiques fixées par docs/CONTRAT_CODE.md : ne pas les changer sans DevMO.
 */
package moteur;

import java.awt.Graphics2D;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Le plateau : le décor (TileMap) et les 36 cases du parcours
 * (16 dans la Ferme, 11 sur la Route, 9 dans la Cuisine).
 * <p>
 * Les cases sont lues dans resources/tilemap/parcours.txt. Une ligne par case :
 * {@code zone;index;colonne_tuile;ligne_tuile;type;parametre}. Les lignes
 * {@code MONSTRE;type;zone;colonne_tuile;ligne_tuile} donnent la place de repos des monstres.
 */
public class Plateau implements Dessinable {

    /** Fichier qui décrit les 36 cases, dans src/resources. */
    public static final String FICHIER_PARCOURS = "resources/tilemap/parcours.txt";

    private final TileMap tileMap;
    private final Map<Zone, List<Case>> cases;
    private final Map<String, Ingredient> ingredients;
    private final Map<String, Point> reposMonstres;
    private int indexGrange;

    /**
     * Crée le plateau et lit le fichier parcours.txt.
     *
     * @param tileMap décor du plateau (peut être null pour les tests en console)
     * @throws IllegalStateException si parcours.txt est absent ou mal écrit
     */
    public Plateau(TileMap tileMap) {
        this.tileMap = tileMap;
        this.cases = new EnumMap<>(Zone.class);
        for (Zone zone : Zone.values()) {
            cases.put(zone, new ArrayList<>());
        }
        this.ingredients = creerIngredients();
        this.reposMonstres = new HashMap<>();
        this.indexGrange = -1;
        chargerParcours();
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

    /** @return position de la Grange dans la Ferme (11) */
    public int getIndexGrange() {
        return indexGrange;
    }

    /** @return décor du plateau */
    public TileMap getTileMap() {
        return tileMap;
    }

    /**
     * @param code code de l'ingrédient, par exemple "CAROTTE" ou "POMME_DE_TERRE"
     * @return l'ingrédient, ou null s'il n'existe pas
     */
    public Ingredient getIngredient(String code) {
        return ingredients.get(code);
    }

    /** @return les 9 ingrédients du jeu */
    public Collection<Ingredient> getIngredients() {
        return Collections.unmodifiableCollection(ingredients.values());
    }

    /**
     * Place de repos d'un monstre, lue dans parcours.txt (utile pour GestionMonstres).
     *
     * @param typeMonstre "SANGLIER", "TEMPETE" ou "RAT"
     * @return coin haut-gauche en pixels, ou null si le monstre n'est pas dans le fichier
     */
    public Point getReposMonstre(String typeMonstre) {
        Point repos = reposMonstres.get(typeMonstre);
        return repos == null ? null : new Point(repos);
    }

    /**
     * Crée les 9 ingrédients du jeu (mêmes noms que la table INGREDIENT).
     *
     * @return les ingrédients rangés par code ("CAROTTE", "POMME_DE_TERRE"…)
     */
    private static Map<String, Ingredient> creerIngredients() {
        Map<String, Ingredient> liste = new LinkedHashMap<>();
        Object[][] donnees = {
            {"Carotte", TypeIngredient.LEGUME}, {"Pomme de terre", TypeIngredient.LEGUME},
            {"Tomate", TypeIngredient.LEGUME}, {"Oignon", TypeIngredient.LEGUME},
            {"Courgette", TypeIngredient.LEGUME}, {"Blé", TypeIngredient.CEREALE},
            {"Bœuf", TypeIngredient.VIANDE}, {"Poulet", TypeIngredient.VIANDE},
            {"Agneau", TypeIngredient.VIANDE}
        };
        for (int i = 0; i < donnees.length; i++) {
            Ingredient ingredient = new Ingredient(i + 1, (String) donnees[i][0], (TypeIngredient) donnees[i][1]);
            liste.put(ingredient.getCode(), ingredient);
        }
        return liste;
    }

    /**
     * Lit parcours.txt et crée les cases (zone;index;colonne;ligne;type;parametre).
     */
    private void chargerParcours() {
        InputStream flux = getClass().getClassLoader().getResourceAsStream(FICHIER_PARCOURS);
        if (flux == null) {
            throw new IllegalStateException("Fichier introuvable : src/" + FICHIER_PARCOURS);
        }
        int numeroLigne = 0;
        try (BufferedReader lecteur = new BufferedReader(new InputStreamReader(flux, StandardCharsets.UTF_8))) {
            String ligne;
            while ((ligne = lecteur.readLine()) != null) {
                numeroLigne++;
                ligne = ligne.trim();
                if (ligne.isEmpty() || ligne.startsWith("#")) {
                    continue;
                }
                lireLigne(ligne.split(";"));
            }
        } catch (IOException e) {
            throw new IllegalStateException("Lecture impossible de " + FICHIER_PARCOURS, e);
        } catch (RuntimeException e) {
            throw new IllegalStateException(FICHIER_PARCOURS + ", ligne " + numeroLigne + " : " + e.getMessage(), e);
        }
        for (Zone zone : Zone.values()) {
            if (cases.get(zone).isEmpty()) {
                throw new IllegalStateException(FICHIER_PARCOURS + " : aucune case dans la zone " + zone);
            }
        }
        if (indexGrange < 0) {
            throw new IllegalStateException(FICHIER_PARCOURS + " : la Ferme n'a pas de case GRANGE");
        }
    }

    /**
     * Crée la case (ou la place de repos du monstre) décrite par une ligne du fichier.
     *
     * @param champs les champs de la ligne, séparés par « ; »
     */
    private void lireLigne(String[] champs) {
        if ("MONSTRE".equals(champs[0])) {
            int x = Integer.parseInt(champs[3].trim()) * TileMap.TAILLE_TUILE;
            int y = Integer.parseInt(champs[4].trim()) * TileMap.TAILLE_TUILE;
            reposMonstres.put(champs[1].trim(), new Point(x, y));
            return;
        }
        if (champs.length < 6) {
            throw new IllegalArgumentException("6 champs attendus");
        }
        Zone zone = Zone.valueOf(champs[0].trim());
        int index = Integer.parseInt(champs[1].trim());
        int colonne = Integer.parseInt(champs[2].trim());
        int ligne = Integer.parseInt(champs[3].trim());
        String type = champs[4].trim();
        String parametre = champs[5].trim();
        List<Case> liste = cases.get(zone);
        if (index != liste.size()) {
            throw new IllegalArgumentException("index " + index + " inattendu (attendu : " + liste.size() + ")");
        }
        liste.add(creerCase(zone, index, colonne, ligne, type, parametre));
    }

    /**
     * Choisit la bonne sous-classe de Case d'après le type écrit dans parcours.txt.
     *
     * @param zone zone de la case
     * @param index position dans la zone
     * @param colonne colonne de la tuile en haut à gauche
     * @param ligne ligne de la tuile en haut à gauche
     * @param type DEPART, INGREDIENT, PIOCHE, GRANGE, EVENEMENT, ARRIVEE ou SIMPLE
     * @param parametre ingrédient, événement ou étape (« - » si rien)
     * @return la case créée
     */
    private Case creerCase(Zone zone, int index, int colonne, int ligne, String type, String parametre) {
        switch (type) {
            case "DEPART":
                return new CaseDepart(zone, index, colonne, ligne);
            case "INGREDIENT":
                Ingredient ingredient = ingredients.get(parametre);
                if (ingredient == null) {
                    throw new IllegalArgumentException("ingrédient inconnu : " + parametre);
                }
                return new CaseIngredient(zone, index, colonne, ligne, ingredient);
            case "PIOCHE":
                return new CasePioche(zone, index, colonne, ligne);
            case "GRANGE":
                indexGrange = index;
                return new CaseGrange(zone, index, colonne, ligne);
            case "EVENEMENT":
                return new CaseEvenement(zone, index, colonne, ligne, parametre);
            case "ARRIVEE":
                return new CaseArrivee(zone, index, colonne, ligne);
            case "SIMPLE":
                return new CaseSimple(zone, index, colonne, ligne, parametre);
            default:
                throw new IllegalArgumentException("type de case inconnu : " + type);
        }
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
