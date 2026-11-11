# Contrat de code — De la terre à l'assiette

Ce fichier fixe **qui écrit quelle classe** et **les signatures publiques** que chacun doit respecter.
Tant que tout le monde respecte ces noms et ces signatures, les trois parties compileront ensemble sans retouche.

**Règle d'or : on ne change jamais une signature de ce fichier seul.** Si un changement est nécessaire, on le demande à DevMO (intégrateur), qui met ce fichier à jour pour toute l'équipe.

---

## 1. Le projet

- Projet NetBeans : `delaterre_a_lassiette` (Java with Ant), dépôt GitHub `mohamedbenzerk/delaterre_a_lassiette`.
- Dossiers :

```
delaterre_a_lassiette/
├── lib/                      pilote JDBC (même .jar pour tous, ajouté dans Libraries)
├── bd.properties             identifiants de la base (JAMAIS sur GitHub)
├── bd.properties.exemple     modèle sans mot de passe (sur GitHub)
└── src/
    ├── moteur/               règles du jeu            (DevMO + DevIG_MONSTER pour TileMap et monstres)
    ├── ig/                   fenêtre et écrans        (DevIG_MONSTER + SM + DevBDD pour la salle d'attente)
    ├── sql/                  accès à la base          (DevBDD)
    └── resources/            images et fichiers du plateau (déjà prêts)
```

- Classe principale : `ig.FenetreDeJeu` (méthode `main`).
- **Java** : la version choisie par le SM (tâche SM-1), la même pour tous. Par sécurité, on n'utilise que du Java compatible **Java 17** (pas de `record`, pas de `var` dans les attributs, pas de fonctions très récentes).
- Encodage des fichiers : UTF-8.

## 2. Règles communes

1. **Chacun modifie seulement ses fichiers** (voir le tableau en section 3).
2. **Pas de SQL en dehors du package `sql`.** Pas de `JFrame` / `JPanel` en dehors du package `ig`. Le moteur peut utiliser `Graphics2D`, `BufferedImage` et `ImageIO` pour dessiner, comme dans le cours.
3. Images et fichiers chargés depuis `src/resources` :
   - image : `ImageIO.read(getClass().getClassLoader().getResource("resources/sprites/pion_bleu.png"))`
   - texte : `getClass().getClassLoader().getResourceAsStream("resources/tilemap/parcours.txt")`
4. Noms en français, `camelCase` pour les variables et méthodes, `MajusculeAuDebut` pour les classes, `MAJUSCULES` pour les constantes.
5. Ordre dans une classe : attributs → constructeurs → getters/setters → méthodes → méthodes `@Override`.
6. **JavaDoc** sur chaque classe et chaque méthode publique. Formatage NetBeans `Alt+Maj+F` avant chaque commit.
7. Les erreurs SQL sont attrapées **dans** le package `sql` (message dans la console), le jeu ne doit pas planter.
8. Chaque dev garde une **classe de test console** : `moteur.TestMoteur`, `sql.TestSQL`. DevIG_MONSTER teste avec `FenetreDeJeu.demarrerPartieLocale()` et peut ajouter `ig.TestAffichage`. Chacun peut ajouter ses propres classes `TestXxx` dans le package qu'il possède ; elles n'appellent jamais de code non fini des autres.
9. Git : `pull` → travail → `commit` avec tag (`[MOT]`, `[IHM]`, `[SQL]`) → `pull` → `push`. Jamais de `bd.properties` sur GitHub.

## 3. Qui écrit quoi

| Package | Classe | Propriétaire |
|---|---|---|
| moteur | `Dessinable`, `Animable`, `Zone`, `TypeIngredient`, `Ingredient`, `Recette` | DevMO |
| moteur | `Case` + `CaseSimple`, `CaseDepart`, `CaseIngredient`, `CasePioche`, `CaseGrange`, `CaseEvenement`, `CaseArrivee` | DevMO |
| moteur | `Plateau`, `Pion`, `Pioche`, `Jeu`, `TestMoteur` | DevMO |
| moteur | `Carte` + `CarteMouvement`, `CarteAttaque` (`CarteSecheresse`, `CartePanne`, `CarteVol`, `CarteAppat`), `CarteDefense` (`CarteBouclier`, `CarteEpouvantail`) | DevMO |
| moteur | `TileMap`, `Monstre`, `Sanglier`, `GestionMonstres` (+ `Tempete`, `Rat` en version complète) | DevIG_MONSTER |
| ig | `FenetreDeJeu`, `PanneauPlateau`, `PanneauHUD` | DevIG_MONSTER |
| ig | `EcranConnexion`, `EcranChoixRecette`, `EcranClassement` | SM |
| ig | `EcranSalleAttente` | DevBDD |
| sql | `ConnexionBD`, `JoueurSQL`, `PartieSQL`, `PionSQL`, `CarteSQL`, `RecetteSQL`, `MonstreSQL`, `ActionSQL`, `LigneAction`, `TestSQL` | DevBDD |

DevMO crée d'abord **toutes** ces classes vides avec les signatures ci-dessous (tâche MO-0, avant le 14/11). Ensuite chacun remplit les siennes.

## 4. Signatures — package `moteur`

```java
public interface Dessinable { void rendu(Graphics2D contexte); }
public interface Animable   { void miseAJour(); boolean estEnMouvement(); }

public enum Zone { FERME, ROUTE, CUISINE }
public enum TypeIngredient { LEGUME, CEREALE, VIANDE }

public class Ingredient {
    public Ingredient(int id, String nom, TypeIngredient type)
    public int getId()   public String getNom()   public TypeIngredient getType()
}

public class Recette {
    public Recette(int id, String nom, List<Ingredient> ingredients)
    public int getId()   public String getNom()   public List<Ingredient> getIngredients()
}

public abstract class Case implements Dessinable {
    protected Case(Zone zone, int index, int colonne, int ligne)   // colonne/ligne en TUILES (parcours.txt)
    public Zone getZone()   public int getIndex()
    public int getX()   public int getY()                          // pixels, coin haut-gauche de la case (64 x 64)
    public abstract void appliquerEffet(Pion pion, Jeu jeu);
}
// Sous-classes : CaseSimple, CaseDepart, CaseIngredient(… , Ingredient), CasePioche, CaseGrange, CaseEvenement(… , String type), CaseArrivee

public class Plateau implements Dessinable {
    public Plateau(TileMap tileMap)                // lit resources/tilemap/parcours.txt
    public Case getCase(Zone zone, int index)
    public int nbCases(Zone zone)                  // FERME 16, ROUTE 11, CUISINE 9
    public TileMap getTileMap()
}

public class Pion implements Dessinable, Animable {
    public Pion(int id, String pseudo, String couleur, int ordre)   // couleur : "BLEU", "ROUGE", "VERT", "JAUNE" ; ordre 1..4
    public int getId()   public String getPseudo()   public String getCouleur()   public int getOrdre()
    public Zone getZone()   public int getIndexCase()   public void setPosition(Zone zone, int indexCase)
    public void setPlateau(Plateau plateau)
    public Recette getRecette()   public void setRecette(Recette recette)
    public List<Ingredient> getInventaire()
    public void ajouterIngredient(Ingredient ingredient)
    public Ingredient perdreIngredient()           // null si inventaire vide
    public boolean aTousLesIngredients()
    public List<Carte> getMain()                   // 3 cartes, index 0..2 = emplacement 1..3 en base
    public int getToursBloques()       public void setToursBloques(int n)
    public boolean isProtegeCarte()    public void setProtegeCarte(boolean b)
    public int getToursEpouvantail()   public void setToursEpouvantail(int n)
    public boolean isCibleAppat()      public void setCibleAppat(boolean b)
    public boolean isPret()            public void setPret(boolean b)
    public int getRang()   public void setRang(int rang)   public int getScore()   public void setScore(int score)
    public void avancer(int nbCases)   public void reculer(int nbCases)
    public int progression()           // sert à trouver le joueur en tête
    public boolean estFini()
    public int getX()   public int getY()              // pixels à l'écran (animé)
}

public abstract class Carte {
    protected Carte(int id, String nom, int valeur)
    public int getId()   public String getNom()   public int getValeur()
    public abstract String getFamille();           // "MOUVEMENT", "ATTAQUE", "DEFENSE"
    public abstract boolean necessiteCible();
    public abstract void appliquer(Pion joueur, Pion cible, Jeu jeu);
    public BufferedImage getImage()                // resources/cartes/carte_xxx.png
    public static Carte creer(int id, String nom, String famille, int valeur)   // choisit la bonne sous-classe d'après le nom
}

public class Pioche {
    public Pioche(List<Carte> catalogue)           // catalogue avec les exemplaires (36 cartes)
    public Carte tirer()
}

public class Jeu {
    public Jeu()                                   // partie LOCALE de test : 4 pions, sans base (Release 1, tests IG).
                                                   // En local, getPionLocal() = getPionCourant() et estMonTour() = vrai.
    public Jeu(int idPartie, int idPionLocal)      // partie en réseau, chargée depuis la base
    public void miseAJour()                        // appelée toutes les 40 ms ; lit la base toutes les 0,5 s
    public void rendu(Graphics2D contexte)         // ordre : plateau, cases, monstres, pions
    public boolean estTermine()
    public boolean estMonTour()
    public boolean estHote()                       // ordre du pion local = 1
    public void jouerCarte(Carte carte, Pion cible)   // cible = null si la carte n'en demande pas
    public List<Pion> getPions()   public Pion getPionLocal()   public Pion getPionCourant()
    public Plateau getPlateau()    public List<String> getJournal()
    public int getIdPartie()       public int getNumTour()
}
```

**Signatures écrites par DevIG_MONSTER dans `moteur` :**

```java
public class TileMap implements Dessinable {
    public static final int TAILLE_TUILE = 32;
    public TileMap(String cheminCarte, String cheminTileset)   // "resources/tilemap/carte_plateau.txt", "resources/tilemap/tileset.png"
    public int getLargeur()   public int getHauteur()          // en pixels : 960 x 640
}

public abstract class Monstre implements Dessinable, Animable {
    protected Monstre(Zone zone, int xRepos, int yRepos, String cheminImage)
    public Zone getZone()   public abstract String getType();   // "SANGLIER", "TEMPETE", "RAT"
    public void seDeplacerVers(Pion cible)         // animation case par case jusqu'au pion, puis retour
    public abstract void agir(Pion cible, Jeu jeu);
    public int getX()   public int getY()
}
public class Sanglier extends Monstre { }          // agir : la cible perd 1 ingrédient, sinon recule de 2

public class GestionMonstres implements Dessinable, Animable {
    public GestionMonstres(Plateau plateau)        // Version 1 : crée seulement le Sanglier
    public Pion tourDuMonstre(List<Pion> pions, Jeu jeu)   // poste hôte : tirage + déplacement + effet ; renvoie la cible (ou null)
    public void animer(String typeMonstre, Pion cible)     // autres postes : animation seulement (l'effet est déjà en base)
}
```

Règles du monstre en **Version 1** : après chaque tour complet, 1 chance sur 2 ; la cible est tirée parmi les pions **de la Ferme**, le joueur en tête compte 2 fois ; un pion `cibleAppat` est choisi en priorité ; si la cible a `toursEpouvantail > 0`, le Sanglier repart sans rien faire ; s'il n'y a personne dans la Ferme, il dort.

## 5. Signatures — package `ig`

```java
public class FenetreDeJeu extends JFrame implements ActionListener {   // DevIG_MONSTER
    public FenetreDeJeu()                          // Timer 40 ms, CardLayout, fenêtre 1280 x 640
    public static void main(String[] args)
    public void afficherEcran(String nom)          // "CONNEXION", "SALLE", "RECETTE", "JEU", "CLASSEMENT"
    public void demarrerPartie(int idPartie, int idPionLocal)
    public void demarrerPartieLocale()             // jeu = new Jeu() ; pour tester sans base
    public int getIdJoueur()   public void setIdJoueur(int id)
    public int getIdPartie()   public void setIdPartie(int id)
    public int getIdPion()     public void setIdPion(int id)
    public Jeu getJeu()
}
public class PanneauPlateau extends JPanel { public PanneauPlateau()  public void setJeu(Jeu jeu) }      // DevIG_MONSTER, 960 x 640
public class PanneauHUD extends JPanel implements MouseListener { public PanneauHUD()  public void setJeu(Jeu jeu) }   // DevIG_MONSTER, 320 x 640

public class EcranConnexion    extends JPanel { public EcranConnexion(FenetreDeJeu fenetre) }      // SM
public class EcranSalleAttente extends JPanel { public EcranSalleAttente(FenetreDeJeu fenetre) }   // DevBDD
public class EcranChoixRecette extends JPanel { public EcranChoixRecette(FenetreDeJeu fenetre) }   // SM
public class EcranClassement   extends JPanel { public EcranClassement(FenetreDeJeu fenetre) }     // SM
```

## 6. Signatures — package `sql` (DevBDD)

```java
public class ConnexionBD {                         // une seule connexion pour tout le jeu
    public static Connection get()                 // lit bd.properties : url, utilisateur, motdepasse
    public static void fermer()
}
public class JoueurSQL {
    public int connecter(String pseudo)            // crée le joueur s'il n'existe pas ; renvoie id_joueur
    public void enregistrerResultat(int idJoueur, int rang)
}
public class PartieSQL {
    public int creer(String nom, int idJoueurHote)          // crée la partie + le pion de l'hôte (ordre 1) ; renvoie id_partie
    public List<String[]> listerEnAttente()                  // {id_partie, nom, nb_joueurs}
    public int rejoindre(int idPartie, int idJoueur)         // crée le pion (ordre libre suivant) ; renvoie id_pion, -1 si pleine
    public int getIdPion(int idPartie, int idJoueur)
    public String getEtat(int idPartie)                      // "ATTENTE", "CHOIX_RECETTE", "EN_COURS", "TERMINEE"
    public void setEtat(int idPartie, String etat)
    public int getOrdreCourant(int idPartie)
    public int getNumTour(int idPartie)
    public void passerTour(int idPartie, int ordreSuivant, int numTour)
}
public class PionSQL {
    public List<Pion> listerPions(int idPartie)              // Pion complets : position, recette, inventaire, main, états
    public void setPret(int idPion, boolean pret)
    public void setRecette(int idPion, int idRecette)
    public void sauvegarder(Pion pion)                       // zone, index_case, tours_bloques, protections, appât, rang, score
    public void ajouterIngredient(int idPion, int idIngredient)
    public void retirerIngredient(int idPion, int idIngredient)
}
public class CarteSQL {
    public List<Carte> catalogue()                           // 36 cartes (nb_exemplaires), via Carte.creer(...)
    public void poserDansMain(int idPion, int emplacement, int idCarte)   // emplacement 1..3
    public void retirerDeMain(int idPion, int emplacement)
}
public class RecetteSQL { public List<Recette> listerRecettes() }        // avec leurs 4 ingrédients
public class MonstreSQL {
    public void initialiser(int idPartie)                    // crée le Sanglier (V1) de la partie
    public void sauvegarder(int idPartie, String type, String zone, int indexCase)
}
public class ActionSQL {
    public void ajouter(int idPartie, int numTour, Integer idPionAuteur, String type,
                        Integer idCarte, Integer idPionCible, String texte)   // type : "CARTE", "MONSTRE", "PASSE", "INFO", "FIN"
    public List<LigneAction> lireDepuis(int idPartie, int idDerniereAction)
}
public class LigneAction {                                   // une ligne de la table ACTION
    public final int idAction;  public final int numTour;  public final String type;
    public final Integer idPionAuteur;  public final Integer idCarte;  public final Integer idPionCible;
    public final String texte;
    public LigneAction(int idAction, int numTour, String type, Integer idPionAuteur,
                       Integer idCarte, Integer idPionCible, String texte)
}
```

## 7. Comment les morceaux se branchent

1. `FenetreDeJeu` crée le `Jeu` puis, toutes les 40 ms : `jeu.miseAJour()` puis `repaint()`.
2. `PanneauPlateau` appelle `jeu.rendu(g)`. `PanneauHUD` lit `jeu.getPionLocal()` et appelle `jeu.jouerCarte(...)` au clic.
3. `Jeu.jouerCarte()` applique la carte (polymorphisme), sauvegarde avec `PionSQL` / `CarteSQL`, écrit dans `ActionSQL`, puis `PartieSQL.passerTour(...)`.
4. Après un tour complet, si `jeu.estHote()`, `Jeu` appelle `gestionMonstres.tourDuMonstre(...)` et écrit l'action `"MONSTRE"`. L'effet (`agir`) est appliqué quand le monstre arrive sur la case : `Jeu` attend que `gestionMonstres.estEnMouvement()` soit faux avant de sauvegarder la cible et de redonner la main au joueur 1.
5. Toutes les 0,5 s, `Jeu` lit `ActionSQL.lireDepuis(...)`, recharge les pions avec `PionSQL.listerPions(...)` et anime les déplacements (`GestionMonstres.animer(...)` pour le monstre).
