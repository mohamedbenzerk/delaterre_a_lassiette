# Ressources graphiques — De la terre à l'assiette

Le dossier `resources` est déjà en place dans **`delaterre_a_lassiette/src/resources`** (à côté des packages `ig`, `moteur`, `sql`),
comme dans l'exemple du prof (`AIDE_DU_PROF/CODE_AIDE/IHM_JeuDeBase/src/resources`).
Les images sont alors chargées avec `getClass().getClassLoader().getResource("resources/…")`
et elles sont incluses automatiquement dans le .jar.

| Dossier | Contenu | Taille | Utilisé par |
|---|---|---|---|
| `tilemap/` | `tileset.png` (28 tuiles), `carte_plateau.txt` (30 × 20 numéros), `parcours.txt` (36 cases + repos des monstres) | tuiles 32 × 32 | `TileMap`, `Plateau` (moteur) |
| `fond/` | `fond_plateau.png` : le décor + les cases déjà assemblés | 960 × 640 | tests, diapos |
| `icones/` | 21 icônes : 9 ingrédients, départ, grange, pioche, bouchon, raccourci, station, entrée, laver, couper, cuire, dresser, assiette | 32 × 32 | `Case`, `PanneauHUD` (inventaire) |
| `cartes/` | 10 cartes + `dos_carte.png` + `liste_cartes.txt` | 100 × 140 | `Carte`, `PanneauHUD` (main du joueur) |
| `recettes/` | 4 fiches : tajine, couscous, burger, poulet aux légumes | 140 × 190 | `EcranChoixRecette`, `PanneauHUD` |
| `sprites/` | 4 pions, 4 camions, sanglier, tempête, rat | 24 × 24 et 48 × 48 | `Pion`, `Monstre` |

## Important pour la note
Le cours impose la **technique du tile mapping**. Le décor doit donc être dessiné dans le jeu avec
`tileset.png` + `carte_plateau.txt` (tuile n° `n` → `getSubimage((n % 8) * 32, (n / 8) * 32, 32, 32)`).
`fond_plateau.png` sert seulement à tester l'affichage au début ou à illustrer les diapos.

## Les cartes (même contenu que la table CARTE de la base)
| Fichier | Nom | Famille | Valeur | Exemplaires | Effet |
|---|---|---|---|---|---|
| carte_avance1.png | Avance 1 | MOUVEMENT | 1 | 6 | Avance de 1 case |
| carte_avance2.png | Avance 2 | MOUVEMENT | 2 | 6 | Avance de 2 cases |
| carte_avance3.png | Avance 3 | MOUVEMENT | 3 | 5 | Avance de 3 cases |
| carte_avance4.png | Avance 4 | MOUVEMENT | 4 | 4 | Avance de 4 cases |
| carte_secheresse.png | Sécheresse | ATTAQUE | 0 | 3 | La cible recule de 2 cases |
| carte_panne.png | Panne | ATTAQUE | 0 | 3 | La cible passe son prochain tour |
| carte_vol.png | Vol | ATTAQUE | 0 | 2 | La cible perd 1 ingrédient (Ferme) |
| carte_appat.png | Appât | ATTAQUE | 0 | 2 | Le monstre ira chez la cible |
| carte_bouclier.png | Bouclier | DEFENSE | 0 | 3 | Annule la prochaine attaque |
| carte_epouvantail.png | Épouvantail | DEFENSE | 0 | 2 | Le monstre ne t'attaque pas pendant 2 tours |

Les cartes Mouvement sont les plus nombreuses (21 sur 36) pour que chaque joueur ait presque
toujours de quoi avancer. On peut changer ces nombres dans la colonne `nb_exemplaires`.

Le panneau de droite (320 px) contient 3 cartes de 100 px côte à côte.

Les illustrations viennent de la police Noto Color Emoji (licence libre Apache 2.0).

