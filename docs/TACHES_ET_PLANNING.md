# Tâches GitHub — De la terre à l'assiette

Répartition des tâches par rôle, avec dates et dépendances. Chaque bloc « Issue » se copie tel quel dans GitHub (titre = ligne en gras, label, milestone, puis le texte comme description).

## 1. Calendrier

| Période | Dates | Résultat attendu |
|---|---|---|
| Sprint 0 — Préparation | 11/11 → 17/11 | GitHub prêt, story map validée, base créée, backlog et planning du Sprint 1 |
| Sprint 1 — Release 1 | 18/11 → 04/12 | Plateau en tile mapping, 4 pions qui avancent avec les cartes tour par tour, Sanglier qui se déplace, données dans la base (sans collisions ni salle d'attente) |
| Sprint 2 — Release 2 = Version 1 | 07/12 → 18/12 | Jeu complet avec UN seul monstre (le Sanglier) : cartes Attaque/Défense, collisions, salle d'attente, 4 PC, classement |
| Version complète (bonus) | 21/12 → 04/01 | Tempête et Rat, seulement si la Version 1 est validée le 18/12 |
| Finalisation | 04/01 → 11/01 | Version stable, diapos, vidéo, rapports, zip |
| Soutenance | mi-janvier 2027 (date exacte à confirmer avec l'encadrant) | Sprint review de 30 min + démo |

Réunions fixes (animées par le SM) : planning Sprint 1 le 17/11, review + rétro Sprint 1 le 04/12, planning Sprint 2 le 07/12, review + rétro Sprint 2 le 04/01.

## 2. Labels et milestones à créer

- **Labels** : `[PO]` `[SM]` `[SQL]` `[MOT]` `[IHM]` `[DOC]` `bug`
- **Milestones** : `Sprint 0` (17/11), `Sprint 1` (04/12), `Sprint 2` (18/12), `Version complète` (04/01), `Finalisation` (11/01)
- **Commits** : commencer par le tag du package, par exemple `[MOT] Ajout de la classe Pion` (règle du prof)

## 3. Tâches déjà terminées (toute l'équipe)

À créer sur GitHub puis fermer tout de suite (statut Fini), avec le label `[DOC]` et le milestone `Sprint 0`.

| N° | Tâche | Dates | Contenu |
|---|---|---|---|
| FAIT-1 | Concept et règles du jeu (thème validé) | 01/11 → 11/11 | Jeu de plateau type Parcheesi, tour par tour, 3 zones (Ferme, Route, Cuisine), cartes, monstres, 4 recettes. |
| FAIT-2 | Première story map | 01/11 → 02/11 | Présentation « De la terre à l'assiette – Story mapping » (sera refaite au format du cours dans PO-1). |
| FAIT-3 | Diagrammes UML et base de données | 02/11 → 11/11 | 14 diagrammes : cas d'utilisation, classes (×3), séquence (×3), activité (×2), états (×2), déploiement, MCD, MLD. |
| FAIT-4 | Tile mapping et ressources graphiques | 02/11 → 11/11 | Tileset, carte du plateau, parcours des 36 cases, icônes, 10 cartes, 4 fiches recettes, pions, monstres (src/resources). |

## 4. Tâches à faire

| N° | Rôle | Tâche | Début | Fin | Points | Dépend de |
|---|---|---|---|---|---|---|
| SM-1 | SM | Organiser le dépôt GitHub | 11/11 | 13/11 | 2 | — |
| PO-1 | PO | Story map et user stories | 11/11 | 14/11 | 3 | — |
| BDD-1 | DevBDD | Créer la base de données | 12/11 | 17/11 | 3 | — |
| MO-0 | DevMO | Squelette du projet NetBeans (contrat de code) | 11/11 | 14/11 | 2 | — |
| MO-1 | DevMO | Noyau du moteur (en console) | 15/11 | 24/11 | 5 | MO-0 |
| IG-1 | DevIG_MONSTER | Fenêtre de jeu et tile mapping | 12/11 | 21/11 | 5 | — |
| SM-2 | SM | Product backlog et planning du Sprint 1 | 14/11 | 17/11 | 3 | PO-1 |
| BDD-2 | DevBDD | Connexion à la base et premières classes SQL | 16/11 | 24/11 | 5 | BDD-1 |
| MO-2 | DevMO | Tour par tour, parcours complet et récolte | 25/11 | 04/12 | 8 | MO-1 |
| IG-2 | DevIG_MONSTER | Plateau dans la fenêtre et panneau du joueur | 22/11 | 04/12 | 5 | IG-1, MO-1 |
| BDD-3 | DevBDD | Synchronisation entre les postes | 25/11 | 04/12 | 5 | BDD-2 |
| IG-3 | DevIG_MONSTER | Premier monstre : le Sanglier se déplace | 28/11 | 04/12 | 3 | IG-2 |
| PO-2 | PO | Tests d'acceptation de la Release 1 | 30/11 | 04/12 | 3 | MO-2, IG-2 |
| SM-3 | SM | Suivi Scrum des deux sprints | 18/11 | 04/01 | 5 | SM-2 |
| MO-3 | DevMO | Cartes Attaque et Défense | 07/12 | 18/12 | 8 | MO-2 |
| IG-4 | DevIG_MONSTER | Tour du monstre — Version 1 (Sanglier seul) | 07/12 | 18/12 | 5 | IG-3, MO-2 |
| IG-5 | DevIG_MONSTER | Version complète : Tempête et Rat (bonus) | 21/12 | 04/01 | 5 | IG-4 + Version 1 validée (18/12) |
| BDD-4 | DevBDD | Salle d'attente et parties à 4 postes | 07/12 | 18/12 | 8 | BDD-3 |
| SM-4 | SM | Écrans de connexion, de recette et de classement | 07/12 | 18/12 | 3 | IG-2 |
| PO-3 | PO | Tests d'acceptation de la Release 2 | 14/12 | 04/01 | 3 | MO-3, IG-4, BDD-4 |
| MO-4 | DevMO | Intégration finale et correction des bugs | 04/01 | 08/01 | 3 | PO-3 |
| PO-4 | PO | Présentation PO, vidéo de démo et rapport | 04/01 | 11/01 | 5 | PO-3 |
| SM-5 | SM | Présentation SM, bilan et rendu du projet | 04/01 | 11/01 | 5 | SM-3 |
| DEV-1 | DevBDD + DevMO + DevIG_MONSTER | Diapos et rapports des 3 développeurs | 04/01 | 11/01 | 3 | — |

**Charge par rôle (points) :**

| Rôle | Points | Remarque |
|---|---|---|
| PO | 14 | + validation des stories tout au long du projet |
| SM | 18 | + animation de toutes les réunions Scrum |
| DevBDD | 24 |  |
| DevMO | 29 | coordonne l'intégration finale |
| DevIG_MONSTER | 26 | le SM l'aide avec SM-4 (3 écrans simples) |

## 5. Le détail de chaque issue

### Issue SM-1 — **[SM-1] Organiser le dépôt GitHub**

- **Assigné à** : SM  ·  **Label** : `[SM]`  ·  **Milestone** : `Sprint 0`
- **Dates** : du 11/11 au 13/11  ·  **Points** : 2  ·  **Dépend de** : —

**Objectif** : Préparer GitHub pour que toute l'équipe puisse travailler proprement dès le début.

**À faire**
- [ ] Créer le tableau GitHub Project : À faire / En cours / À tester / Fini
- [ ] Créer les labels [PO] [SM] [SQL] [MOT] [IHM] [DOC] et les 4 milestones (Sprint 0, Sprint 1, Sprint 2, Finalisation)
- [ ] Compléter le .gitignore : build/, dist/, nbproject/private/, bd.properties, et autoriser lib/*.jar (ligne !lib/*.jar)
- [ ] Créer le dossier lib/ avec le pilote JDBC (le même .jar pour tout le monde)
- [ ] Ajouter dans le dépôt : dossier docs/ (diagrammes + conception) et src/resources (images)
- [ ] Choisir UNE version de Java pour toute l'équipe (celle des PC de l'école) et la régler dans le projet (réglée sur 26 aujourd'hui)
- [ ] Mettre docs/CONTRAT_CODE.md sur GitHub et le faire lire à toute l'équipe

**Fini quand** : Chaque membre a cloné le dépôt, voit le tableau et a fait un premier commit de test.

### Issue PO-1 — **[PO-1] Story map et user stories**

- **Assigné à** : PO  ·  **Label** : `[PO]`  ·  **Milestone** : `Sprint 0`
- **Dates** : du 11/11 au 14/11  ·  **Points** : 3  ·  **Dépend de** : —

**Objectif** : Refaire la story map au format du cours et écrire les user stories.

**À faire**
- [ ] Story map : activités en haut, puis lignes Release 1 / Release 2 / Bonus
- [ ] Écrire chaque story au format « En tant que… je veux… afin de… »
- [ ] Ajouter 1 à 3 critères d'acceptation par story
- [ ] Faire valider la story map par l'encadrant

**Fini quand** : La story map validée est dans docs/ (photo ou fichier).

### Issue BDD-1 — **[BDD-1] Créer la base de données**

- **Assigné à** : DevBDD  ·  **Label** : `[SQL]`  ·  **Milestone** : `Sprint 0`
- **Dates** : du 12/11 au 17/11  ·  **Points** : 3  ·  **Dépend de** : —

**Objectif** : Créer les 11 tables du MLD sur le serveur de l'école.

**À faire**
- [ ] Demander à l'encadrant le nom de la base et les identifiants
- [ ] Écrire le script creation_base.sql (11 tables, clés primaires et étrangères)
- [ ] Écrire le script donnees_base.sql : 4 recettes, 9 ingrédients, compositions, 10 cartes
- [ ] Exécuter les scripts sur nemrod.ens2m.fr et vérifier avec des SELECT

**Fini quand** : Les tables existent sur le serveur et les données de base sont dedans. Scripts dans docs/sql/.

### Issue MO-0 — **[MO-0] Squelette du projet NetBeans (contrat de code)**

- **Assigné à** : DevMO  ·  **Label** : `[MOT]`  ·  **Milestone** : `Sprint 0`
- **Dates** : du 11/11 au 14/11  ·  **Points** : 2  ·  **Dépend de** : —

**Objectif** : Donner à toute l'équipe un projet qui compile, avec toutes les classes et leurs méthodes vides.

**À faire**
- [ ] Créer toutes les classes des packages moteur, ig et sql avec les signatures de docs/CONTRAT_CODE.md (méthodes vides avec TODO)
- [ ] Écrire en entier les interfaces Dessinable et Animable et les enums Zone et TypeIngredient
- [ ] Écrire les getters de Pion, Carte, Recette, Ingredient (utilisés par DevBDD)
- [ ] Vérifier que le projet compile (Clean and Build), puis push sur GitHub

**Fini quand** : Le projet compile sur GitHub et chaque dev peut commencer à remplir SES classes.

### Issue MO-1 — **[MO-1] Noyau du moteur (en console)**

- **Assigné à** : DevMO  ·  **Label** : `[MOT]`  ·  **Milestone** : `Sprint 1`
- **Dates** : du 15/11 au 24/11  ·  **Points** : 5  ·  **Dépend de** : MO-0

**Objectif** : Coder les classes de base du moteur et les tester sans affichage, comme demandé par le cours.

**À faire**
- [ ] Enums Zone et TypeIngredient, classes Ingredient et Recette
- [ ] Case (abstraite) + ses 7 sous-classes, Plateau.chargerParcours(parcours.txt)
- [ ] Pion.avancer() / reculer() avec la boucle de la Ferme
- [ ] Carte (abstraite) + CarteMouvement, Pioche.tirer()
- [ ] Petit main de test : un pion pioche et avance, la console affiche sa case

**Fini quand** : En console, un pion fait le tour de la Ferme avec des cartes Avance.

### Issue IG-1 — **[IG-1] Fenêtre de jeu et tile mapping**

- **Assigné à** : DevIG_MONSTER  ·  **Label** : `[IHM]`  ·  **Milestone** : `Sprint 1`
- **Dates** : du 12/11 au 21/11  ·  **Points** : 5  ·  **Dépend de** : —

**Objectif** : Afficher le plateau avec la technique du tile mapping.

**À faire**
- [ ] FenetreDeJeu sur le modèle du prof (Timer 40 ms, miseAJour() puis rendu())
- [ ] Classe TileMap : lire tileset.png et carte_plateau.txt, dessiner tuile par tuile
- [ ] Dessiner le décor tuile par tuile (les cases, les pions et leurs icônes sont dessinés par DevMO)
- [ ] Images dans src/resources, chargées avec getClassLoader().getResource()

**Fini quand** : La fenêtre affiche le décor du plateau, comme resources/fond/fond_plateau.png (sans les cases).

### Issue SM-2 — **[SM-2] Product backlog et planning du Sprint 1**

- **Assigné à** : SM  ·  **Label** : `[SM]`  ·  **Milestone** : `Sprint 0`
- **Dates** : du 14/11 au 17/11  ·  **Points** : 3  ·  **Dépend de** : PO-1

**Objectif** : Transformer la story map en product backlog et lancer le Sprint 1.

**À faire**
- [ ] Ranger les stories par priorité dans la colonne « À faire »
- [ ] Donner des story points à chaque story (1, 2, 3, 5, 8) avec l'équipe
- [ ] Animer le sprint planning : choisir les stories de la Release 1 et les répartir
- [ ] Créer le fichier de suivi : tableau des réunions + burndown chart (Excel ou Google Sheets)

**Fini quand** : Le backlog est sur GitHub avec ses points, le Sprint 1 est planifié et noté.

### Issue BDD-2 — **[BDD-2] Connexion à la base et premières classes SQL**

- **Assigné à** : DevBDD  ·  **Label** : `[SQL]`  ·  **Milestone** : `Sprint 1`
- **Dates** : du 16/11 au 24/11  ·  **Points** : 5  ·  **Dépend de** : BDD-1

**Objectif** : Permettre au jeu de lire et d'écrire dans la base.

**À faire**
- [ ] ConnexionBD : une seule connexion, identifiants lus dans bd.properties (jamais sur GitHub)
- [ ] Ajouter le pilote JDBC MySQL/MariaDB dans les Libraries de NetBeans
- [ ] JoueurSQL, PartieSQL, PionSQL sur le modèle JoueurSQL du prof
- [ ] Test console : créer un joueur, créer une partie, changer la case d'un pion

**Fini quand** : Le test console écrit dans la base et relit la bonne valeur.

### Issue MO-2 — **[MO-2] Tour par tour, parcours complet et récolte**

- **Assigné à** : DevMO  ·  **Label** : `[MOT]`  ·  **Milestone** : `Sprint 1`
- **Dates** : du 25/11 au 04/12  ·  **Points** : 8  ·  **Dépend de** : MO-1

**Objectif** : Faire tourner une partie complète à 4 pions sur un seul PC.

**À faire**
- [ ] Classe Jeu : ordre des joueurs, joueur suivant, estMonTour(), estTermine()
- [ ] Récolte d'un ingrédient quand le pion s'arrête sur une case utile
- [ ] Grange : sortie vers la Route si les 4 ingrédients sont là
- [ ] Route, Cuisine, Assiette : rang du joueur, fin quand 3 joueurs ont fini
- [ ] Cases Pioche et Événement (Bouchon, Raccourci, Station)
- [ ] Affichage des cases et de leurs icônes, et des pions (camions sur la Route) qui avancent case par case, 4 places par case

**Fini quand** : Une partie complète se joue à 4 sur un PC, du Départ jusqu'au classement.

### Issue IG-2 — **[IG-2] Plateau dans la fenêtre et panneau du joueur**

- **Assigné à** : DevIG_MONSTER  ·  **Label** : `[IHM]`  ·  **Milestone** : `Sprint 1`
- **Dates** : du 22/11 au 04/12  ·  **Points** : 5  ·  **Dépend de** : IG-1, MO-1

**Objectif** : Jouer avec la souris.

**À faire**
- [ ] PanneauPlateau : appelle jeu.rendu() à chaque image
- [ ] PanneauHUD : 3 cartes cliquables, recette et ingrédients cochés, joueur dont c'est le tour, journal
- [ ] Clic sur une carte → jeu.jouerCarte(carte, cible) (choix d'un pion adverse si la carte en demande un)
- [ ] Une seule fenêtre de 1280 × 640 (plateau 960 + panneau 320)

**Fini quand** : En cliquant sur une carte Avance, le pion avance.

### Issue BDD-3 — **[BDD-3] Synchronisation entre les postes**

- **Assigné à** : DevBDD  ·  **Label** : `[SQL]`  ·  **Milestone** : `Sprint 1`
- **Dates** : du 25/11 au 04/12  ·  **Points** : 5  ·  **Dépend de** : BDD-2

**Objectif** : Faire voir la même partie sur plusieurs PC.

**À faire**
- [ ] ActionSQL : écrire chaque action dans la table ACTION (journal)
- [ ] PartieSQL : lire et changer ordre_courant (à qui c'est le tour)
- [ ] Lecture des nouvelles actions toutes les 0,5 s (pas toutes les 40 ms)
- [ ] Test sur 2 PC : un pion avance sur le PC 1, il bouge sur le PC 2

**Fini quand** : Le test sur 2 PC marche.

### Issue IG-3 — **[IG-3] Premier monstre : le Sanglier se déplace**

- **Assigné à** : DevIG_MONSTER  ·  **Label** : `[IHM]`  ·  **Milestone** : `Sprint 1`
- **Dates** : du 28/11 au 04/12  ·  **Points** : 3  ·  **Dépend de** : IG-2

**Objectif** : Avoir un PNJ qui bouge (exigé pour la Release 1), sans effet pour l'instant.

**À faire**
- [ ] Classe abstraite Monstre : seDeplacerVers(), miseAJour(), rendu()
- [ ] Classe Sanglier
- [ ] Classe GestionMonstres : après chaque tour complet, le Sanglier va vers un pion de la Ferme puis revient

**Fini quand** : On voit le Sanglier aller jusqu'à un pion et revenir à sa place.

### Issue PO-2 — **[PO-2] Tests d'acceptation de la Release 1**

- **Assigné à** : PO  ·  **Label** : `[PO]`  ·  **Milestone** : `Sprint 1`
- **Dates** : du 30/11 au 04/12  ·  **Points** : 3  ·  **Dépend de** : MO-2, IG-2

**Objectif** : Vérifier que la Release 1 fait bien ce que disent les stories.

**À faire**
- [ ] Écrire les tests d'acceptation de chaque story de la Release 1
- [ ] Tester et noter le résultat (OK / pas OK + capture)
- [ ] Valider les stories finies, renvoyer les autres en « En cours »

**Fini quand** : Le tableau des tests de la Release 1 est rempli dans docs/.

### Issue SM-3 — **[SM-3] Suivi Scrum des deux sprints**

- **Assigné à** : SM  ·  **Label** : `[SM]`  ·  **Milestone** : `Sprint 2`
- **Dates** : du 18/11 au 04/01  ·  **Points** : 5  ·  **Dépend de** : SM-2

**Objectif** : Faire vivre la méthode Scrum et garder les preuves pour le rapport.

**À faire**
- [ ] Stand-up au début de chaque séance (15 min) : fait / à faire / blocages, avec une note écrite
- [ ] Mettre à jour le tableau GitHub et le burndown chart chaque semaine
- [ ] Sprint review + rétrospective le 04/12 (Sprint 1) et le 04/01 (Sprint 2)
- [ ] Planning du Sprint 2 le 07/12
- [ ] Capture du task board à chaque réunion
- [ ] À chaque fin de sprint, vérifier avec l'équipe que les diagrammes correspondent toujours au code

**Fini quand** : Le tableau des réunions, les captures et le burndown sont à jour.

### Issue MO-3 — **[MO-3] Cartes Attaque et Défense**

- **Assigné à** : DevMO  ·  **Label** : `[MOT]`  ·  **Milestone** : `Sprint 2`
- **Dates** : du 07/12 au 18/12  ·  **Points** : 8  ·  **Dépend de** : MO-2

**Objectif** : Ajouter l'interaction entre les joueurs.

**À faire**
- [ ] CarteAttaque : Sécheresse, Panne, Vol, Appât (choix de la cible)
- [ ] CarteDefense : Bouclier, Épouvantail (Appât et Épouvantail agissent sur le Sanglier en Version 1)
- [ ] Joueur bloqué qui passe son tour
- [ ] Score et classement final

**Fini quand** : Chaque carte fait l'effet prévu dans le tableau des cartes.

### Issue IG-4 — **[IG-4] Tour du monstre — Version 1 (Sanglier seul)**

- **Assigné à** : DevIG_MONSTER  ·  **Label** : `[IHM]`  ·  **Milestone** : `Sprint 2`
- **Dates** : du 07/12 au 18/12  ·  **Points** : 5  ·  **Dépend de** : IG-3, MO-2

**Objectif** : Finir le Sanglier avec son effet (première version : un seul monstre, dans la Ferme).

**À faire**
- [ ] Tirage : 1 chance sur 2 ; cible parmi les pions de la Ferme, le joueur en tête compte 2 fois ; carte Appât prioritaire
- [ ] S'il n'y a personne dans la Ferme, le Sanglier dort
- [ ] Collision avec la case de la cible, puis Sanglier.agir() : vole 1 ingrédient (sinon recul de 2)
- [ ] Épouvantail : la cible est protégée, le Sanglier repart
- [ ] Le tour du monstre est calculé par le poste de l'hôte

**Fini quand** : Le Sanglier agit avec son effet, et c'est visible sur tous les postes.

### Issue IG-5 — **[IG-5] Version complète : Tempête et Rat (bonus)**

- **Assigné à** : DevIG_MONSTER  ·  **Label** : `[IHM]`  ·  **Milestone** : `Version complète`
- **Dates** : du 21/12 au 04/01  ·  **Points** : 5  ·  **Dépend de** : IG-4 + Version 1 validée (18/12)

**Objectif** : Passer à 3 monstres, SEULEMENT si la Version 1 est validée par le PO.

**À faire**
- [ ] Tempete et Rat : sous-classes de Monstre, seule la méthode agir() change
- [ ] Le monstre qui agit est celui de la zone de la cible (Ferme, Route, Cuisine)
- [ ] La cible peut maintenant être dans toutes les zones

**Fini quand** : Les 3 monstres agissent, sans casser la Version 1.

### Issue BDD-4 — **[BDD-4] Salle d'attente et parties à 4 postes**

- **Assigné à** : DevBDD  ·  **Label** : `[SQL]`  ·  **Milestone** : `Sprint 2`
- **Dates** : du 07/12 au 18/12  ·  **Points** : 8  ·  **Dépend de** : BDD-3

**Objectif** : Jouer à 4 sur 4 PC différents.

**À faire**
- [ ] Créer / rejoindre une partie, couleurs, bouton Prêt, lancement par l'hôte
- [ ] Écran de la salle d'attente (rafraîchi toutes les 0,5 s)
- [ ] Sauvegarde de la main, de l'inventaire, des monstres et des scores
- [ ] Test sur 4 PC de l'école

**Fini quand** : 4 joueurs sur 4 PC rejoignent la même partie et la jouent.

### Issue SM-4 — **[SM-4] Écrans de connexion, de recette et de classement**

- **Assigné à** : SM  ·  **Label** : `[SM]`  ·  **Milestone** : `Sprint 2`
- **Dates** : du 07/12 au 18/12  ·  **Points** : 3  ·  **Dépend de** : IG-2

**Objectif** : Aider l'équipe de développement sur des écrans simples.

**À faire**
- [ ] EcranConnexion (pseudo)
- [ ] EcranChoixRecette (4 fiches recettes)
- [ ] EcranClassement (rangs et scores)
- [ ] Passage d'un écran à l'autre avec un CardLayout

**Fini quand** : On va de la connexion jusqu'au classement sans erreur.

### Issue PO-3 — **[PO-3] Tests d'acceptation de la Release 2**

- **Assigné à** : PO  ·  **Label** : `[PO]`  ·  **Milestone** : `Sprint 2`
- **Dates** : du 14/12 au 04/01  ·  **Points** : 3  ·  **Dépend de** : MO-3, IG-4, BDD-4

**Objectif** : Valider la Version 1 (jeu complet avec le Sanglier seul), puis la version complète si elle est faite.

**À faire**
- [ ] Tests d'acceptation des stories de la Release 2 (Version 1) avant le 18/12 : c'est ce qui autorise IG-5
- [ ] Une partie complète à 4 PC, notée du début à la fin
- [ ] Liste des bugs trouvés, mise dans GitHub (1 bug = 1 issue)

**Fini quand** : Toutes les stories de la Release 2 sont testées et le résultat est noté.

### Issue MO-4 — **[MO-4] Intégration finale et correction des bugs**

- **Assigné à** : DevMO  ·  **Label** : `[MOT]`  ·  **Milestone** : `Finalisation`
- **Dates** : du 04/01 au 08/01  ·  **Points** : 3  ·  **Dépend de** : PO-3

**Objectif** : Livrer une version stable (le dév. moteur coordonne, toute l'équipe aide).

**À faire**
- [ ] Corriger les bugs ouverts par ordre de gravité
- [ ] Partie complète à 4 PC sans plantage
- [ ] Code formaté (Alt+Maj+F) et commenté (JavaDoc sur les classes principales)

**Fini quand** : Aucun bug bloquant ouvert sur GitHub.

### Issue PO-4 — **[PO-4] Présentation PO, vidéo de démo et rapport**

- **Assigné à** : PO  ·  **Label** : `[DOC]`  ·  **Milestone** : `Finalisation`
- **Dates** : du 04/01 au 11/01  ·  **Points** : 5  ·  **Dépend de** : PO-3

**Objectif** : Préparer la soutenance (partie PO).

**À faire**
- [ ] 3 diapos : concept, règles du jeu, story map
- [ ] Vidéo de démonstration d'une partie
- [ ] Rapport individuel PO (environ 10 pages)

**Fini quand** : Diapos, vidéo et rapport déposés.

### Issue SM-5 — **[SM-5] Présentation SM, bilan et rendu du projet**

- **Assigné à** : SM  ·  **Label** : `[DOC]`  ·  **Milestone** : `Finalisation`
- **Dates** : du 04/01 au 11/01  ·  **Points** : 5  ·  **Dépend de** : SM-3

**Objectif** : Préparer la soutenance (partie SM) et rendre le projet.

**À faire**
- [ ] 3 diapos : architecture, diagramme de classes, base de données
- [ ] 2 diapos bilan : stories finies / en cours / à faire, burndown, rétrospective
- [ ] Zip du projet NetBeans complet SANS le dossier build
- [ ] Rapport individuel SM (environ 10 pages) + partie Scrum demandée par le prof

**Fini quand** : Le zip est déposé et la présentation est prête.

### Issue DEV-1 — **[DEV-1] Diapos et rapports des 3 développeurs**

- **Assigné à** : DevBDD + DevMO + DevIG_MONSTER  ·  **Label** : `[DOC]`  ·  **Milestone** : `Finalisation`
- **Dates** : du 04/01 au 11/01  ·  **Points** : 3  ·  **Dépend de** : —

**Objectif** : Tâche commune à DevBDD, DevMO et DevIG_MONSTER : une copie par personne.

**À faire**
- [ ] 2 diapos par personne : une story réalisée (comment, captures)
- [ ] Rapport individuel : une story en détail (diagramme d'activité, tests), explication, autres stories

**Fini quand** : Chaque développeur a déposé ses diapos et son rapport.
