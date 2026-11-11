# De la terre à l'assiette — Conception du jeu

Ce document fixe les règles précises utilisées pour tous les diagrammes et pour le tile mapping.
Si l'équipe change une règle, il faut mettre à jour les diagrammes concernés.

## 1. Règles retenues

### Déroulement général
1. **Connexion** : le joueur entre son pseudo.
2. **Salle d'attente** : il crée une partie (il devient l'**hôte**) ou rejoint une partie en attente. Chaque joueur reçoit une couleur (bleu, rouge, vert, jaune) et clique sur « Prêt ».
3. **Lancement** : quand les 4 joueurs sont prêts, l'hôte lance la partie.
4. **Choix de la recette** : chaque joueur choisit un plat. Chaque plat demande 4 ingrédients.
5. **Jeu tour par tour** dans 3 zones : Ferme → Route → Cuisine.
6. **Fin** : la partie s'arrête quand 3 joueurs ont atteint l'Assiette. Le classement s'affiche et les scores sont enregistrés.

### Les recettes (4 plats, même difficulté)
| Plat | Ingrédients |
|---|---|
| Tajine | Agneau, Carotte, Pomme de terre, Oignon |
| Couscous | Poulet, Blé, Courgette, Carotte |
| Burger | Bœuf, Blé, Tomate, Oignon |
| Poulet aux légumes | Poulet, Pomme de terre, Courgette, Tomate |

### Le plateau (3 zones)
- **Ferme** : une **boucle de 16 cases** (comme au Parcheesi). Le pion tourne dans la boucle jusqu'à avoir ses 4 ingrédients.
  - Case **Départ** : point de départ de tous les pions.
  - Cases **Ingrédient** : si le pion **s'arrête** dessus et que sa recette en a besoin, l'ingrédient est ajouté à son inventaire.
  - Cases **Pioche** : le joueur pioche une carte en plus.
  - Case **Grange** : quand le pion passe par la Grange avec ses 4 ingrédients, il sort de la boucle et part sur la Route.
- **Route** : **11 cases** en ligne. Le pion est un camion.
  - Cases **Événement** : Bouchon (recule de 1), Raccourci (avance de 2), Station-service (pioche une carte).
- **Cuisine** : **9 cases** en ligne : Entrée, Laver, Couper, Cuire, Dresser… jusqu'à l'**Assiette**.
  - Dès que le pion atteint l'Assiette, le plat est terminé et le joueur reçoit son rang.

### Un tour de joueur
1. Le joueur a toujours **3 cartes** en main.
2. À son tour, il **joue 1 carte** (et choisit une cible si c'est une attaque).
3. L'effet s'applique (le pion avance, l'adversaire recule…), puis l'effet de la case d'arrivée.
4. Il **pioche 1 carte** pour revenir à 3.
5. C'est au joueur suivant. Un joueur bloqué (Panne, Tempête) passe son tour.

### Les cartes
| Famille | Carte | Effet |
|---|---|---|
| Mouvement | Avance 1, 2, 3, 4 | Le pion avance du nombre de cases |
| Attaque | Sécheresse | La cible recule de 2 cases |
| Attaque | Panne | La cible passe son prochain tour |
| Attaque | Vol | La cible perd 1 ingrédient (seulement si elle est dans la Ferme) |
| Attaque | Appât | Au prochain tour du monstre, il va chez la cible |
| Défense | Bouclier | Annule la prochaine carte d'attaque reçue |
| Défense | Épouvantail | Protège du monstre pendant 2 tours |

### Les monstres (PNJ)
- Il y a **un monstre par zone** : **Sanglier** (Ferme), **Tempête** (Route), **Rat** (Cuisine).
- Après chaque **tour complet** (les 4 joueurs ont joué), c'est le **tour du monstre** :
  1. Le jeu tire au sort : **1 chance sur 2** que le monstre agisse.
  2. Il choisit une cible **au hasard**, mais le **joueur en tête compte 2 fois** dans le tirage. Si une carte Appât a été jouée, la cible est imposée.
  3. Le monstre **de la zone de la cible** se déplace jusqu'à elle (collision), fait son effet, puis revient à sa place.
- Effets : Sanglier = vole 1 ingrédient (sinon recule de 2) ; Tempête = bloque 1 tour ; Rat = recule de 2 cases.
- C'est l'ordinateur de l'**hôte** qui calcule le tour du monstre, pour éviter que les 4 postes le fassent en même temps.

### Le multijoueur
- Les 4 postes partagent la **même base MySQL**.
- La table `PARTIE` indique **à qui c'est le tour** (`ordre_courant`).
- Chaque action est écrite dans la table `ACTION` (le journal). Toutes les 0,5 s, chaque poste lit les nouvelles actions et anime les pions.

## 2. Liste des diagrammes

| N° | Fichier | Ce qu'il montre | Utile pour |
|---|---|---|---|
| 01 | `01_cas_utilisation` | Ce que chaque acteur peut faire | PO |
| 02 | `02_classes_vue_ensemble` | Les 3 packages `ig`, `moteur`, `sql` et leurs liens | Scrum Master |
| 03 | `03_classes_moteur` | Les classes du moteur, avec l'héritage Case / Carte / Monstre | Scrum Master, devs |
| 04 | `04_classes_ig_sql` | Les écrans (ig) et l'accès à la base (sql) | Scrum Master, devs |
| 05 | `05_sequence_salle_attente` | Connexion, création ou entrée dans une partie, lancement | Dev interfaces |
| 06 | `06_sequence_tour_joueur` | Un tour de joueur et la mise à jour des 4 postes | Dev avatar |
| 07 | `07_sequence_tour_monstre` | Le tour du monstre | Dev monstres |
| 08 | `08_activite_partie` | Le déroulement complet d'une partie | PO |
| 09 | `09_activite_tour` | Le détail d'un tour de joueur | Devs |
| 10 | `10_etats_partie` | Les états de la partie | Scrum Master |
| 11 | `11_etats_pion` | Les états d'un pion | Dev avatar |
| 12 | `12_deploiement` | Les 4 PC clients et le serveur MySQL | Scrum Master |
| 13 | `13_MCD` | Modèle conceptuel de la base (Merise) | Scrum Master |
| 14 | `14_MLD` | Modèle logique : les tables, clés primaires et étrangères | Scrum Master |

### MLD en écriture du cours (clé primaire en gras, # = clé étrangère)
- JOUEUR (**id_joueur**, pseudo, nb_parties, nb_victoires)
- PARTIE (**id_partie**, nom, etat, ordre_courant, num_tour, date_creation)
- PION (**id_pion**, couleur, ordre, pret, zone, index_case, tours_bloques, protege_carte, tours_epouvantail, cible_appat, rang, score, #id_joueur, #id_partie, #id_recette)
- RECETTE (**id_recette**, nom, image)
- INGREDIENT (**id_ingredient**, nom, type, image)
- COMPOSITION (**#id_recette, #id_ingredient**)
- INVENTAIRE (**#id_pion, #id_ingredient**)
- CARTE (**id_carte**, nom, famille, valeur, description, nb_exemplaires, image)
- MAIN (**#id_pion, emplacement**, #id_carte)
- MONSTRE (**id_monstre**, type, zone, index_case, #id_partie)
- ACTION (**id_action**, num_tour, type, texte, date_action, #id_partie, #id_pion_auteur, #id_monstre, #id_carte, #id_pion_cible)

Les images sont dans `diagrammes/images`. Les sources (PlantUML et Graphviz) sont dans `diagrammes/sources` : on peut les modifier puis régénérer les images sur https://www.plantuml.com/plantuml ou avec l'extension PlantUML de NetBeans/VS Code.

## 3. Le tile mapping (dossier `tilemap`)

| Fichier | Contenu |
|---|---|
| `tileset.png` | 8 × 4 tuiles de 32 × 32 px : herbe, arbres, champs, eau, chemin, clôtures, route, carrelage, murs, grange, cuisinière… |
| `carte_plateau.txt` | La carte : 20 lignes × 30 numéros (un numéro = une tuile du tileset). Plateau de 960 × 640 px. |
| `parcours.txt` | Les 36 cases dans l'ordre (zone, index, colonne, ligne, type, paramètre) + la place de repos des 3 monstres. |
| `icones.png` | Icônes 32 px des cases : ingrédients, pioche, départ, grange, événements, étapes de cuisine, assiette. |
| `pions.png` | 4 pions (bleu, rouge, vert, jaune) + 4 camions, 24 × 24 px. |
| `monstres.png` | Sanglier, Tempête, Rat, 48 × 48 px. |
| `apercu_plateau.png` | Aperçu du plateau complet (pour les diapos). |
| `generer_tilemap.py` | Le script qui fabrique tous ces fichiers (à relancer si on change la carte). |

**Comment lire les numéros :** la tuile n° `n` se trouve dans `tileset.png` à la colonne `n % 8` et à la ligne `n / 8`.
On la récupère avec `tileset.getSubimage((n % 8) * 32, (n / 8) * 32, 32, 32)`, comme dans le cours.

**Une case du parcours = 2 × 2 tuiles (64 × 64 px).** Dans une case, il y a la place pour 4 pions (un par quart).

**Ordre de dessin (comme dans le cours) :** décor (tuiles) → cases et icônes → monstres → pions → textes.

**Taille de la fenêtre conseillée :** 960 × 640 px pour le plateau + environ 320 px à droite pour le panneau du joueur (recette, cartes, journal).

Numéros des tuiles :
0 HERBE · 1 HERBE_FLEURS · 2 BUISSON · 3 ARBRE · 4 TERRE_LABOUREE · 5 CHAMP_LEGUMES · 6 CHAMP_BLE · 7 EAU · 8 CHEMIN_TERRE · 9 CLOTURE_H · 10 CLOTURE_V · 11 PAILLE · 12 ASPHALTE · 13 ASPHALTE_LIGNE_H · 14 ASPHALTE_LIGNE_V · 15 GRAVIER · 16 CARRELAGE · 17 MUR_CUISINE · 18 PLAN_TRAVAIL · 19 TOIT_GRANGE · 20 MUR_GRANGE · 21 PORTE_GRANGE · 22 DALLE_CUISINE · 23 CUISINIERE · 24 EVIER · 25 ROCHER · 26 BERGE_HAUT · 27 FLEUR_JAUNE · 28 à 31 libres.

Les icônes et les monstres viennent de la police Noto Color Emoji (licence libre Apache 2.0).

## 4. Les ressources graphiques (dossier `resources`, à placer dans `src/`)
Dossier prêt à copier dans NetBeans : tile mapping (`tilemap/`), fond du plateau (`fond/`), icônes (`icones/`),
10 cartes à jouer + dos de carte (`cartes/`), 4 fiches recettes (`recettes/`), pions, camions et monstres (`sprites/`).
Voir `resources/LISEZMOI.md`. Aperçu de tout : `planche_ressources.png`.
Nombre de cartes dans la pioche : Avance 1 ×6, Avance 2 ×6, Avance 3 ×5, Avance 4 ×4, Sécheresse ×3, Panne ×3, Vol ×2, Appât ×2, Bouclier ×3, Épouvantail ×2 (36 cartes).

## 5. Corrections du 11/11/2026 (après lecture de AIDE_DU_PROF)
- **Interfaces Java ajoutées** (le cours exige « héritage, interface, polymorphisme ») : `Dessinable` (rendu) implémentée par Plateau, Case, Pion, Monstre ; `Animable` (miseAJour, estEnMouvement) implémentée par Pion et Monstre.
- **Classes du package sql renommées** comme le modèle du prof (`JoueurSQL`) : JoueurSQL, PartieSQL, PionSQL, CarteSQL, RecetteSQL, MonstreSQL, ActionSQL + ConnexionBD (une seule connexion).
- **Identifiants de la base** dans un fichier `bd.properties` non envoyé sur GitHub (le dépôt est public).
- **Images** dans `src/resources` (comme l'exemple du prof), chargées avec `getClassLoader().getResource("resources/…")`.
- Serveur de base de données : `nemrod.ens2m.fr` (MySQL/MariaDB) ; nom de la base à demander à l'encadrant.
