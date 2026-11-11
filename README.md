# De la terre à l'assiette

Jeu de plateau multijoueur (4 joueurs, tour par tour) en Java — projet INFO2, SUPMICROTECH-ENSMM.
Thème : produire, transporter, cuisiner… de la terre à l'assiette.

## Contenu du dépôt

| Dossier / fichier | Contenu |
|---|---|
| `delaterre_a_lassiette/` | Projet NetBeans (à ouvrir avec *File → Open Project*) |
| `delaterre_a_lassiette/src/moteur` | Règles du jeu (plateau, pions, cartes, monstres) |
| `delaterre_a_lassiette/src/ig` | Fenêtre et écrans (Swing) |
| `delaterre_a_lassiette/src/sql` | Accès à la base de données (JDBC) |
| `delaterre_a_lassiette/src/resources` | Images, tileset et fichiers du plateau |
| `delaterre_a_lassiette/bd.properties.exemple` | Modèle des identifiants de la base (copier en `bd.properties`, jamais sur GitHub) |
| `docs/` | Contrat de code, conception, tâches, principe du jeu, diagrammes UML |
| `AIDE_DU_PROF/` | Aide fournie par l'encadrant |

## À lire avant de coder

1. `docs/Principe_du_jeu.pdf` — les règles du jeu
2. `docs/CONTRAT_CODE.md` — qui écrit quelle classe et les signatures à respecter
3. `docs/TACHES_ET_PLANNING.md` — les tâches de chacun et les dates

## Règles Git

- `pull` avant de commencer et avant chaque `push`.
- Chacun modifie seulement ses propres classes.
- Message de commit avec un tag : `[MOT]`, `[IHM]`, `[SQL]` ou `[DOC]`.
- Avant chaque push : supprimer les fichiers de test, garder des noms de fichiers clairs.
- Version de Java du projet : 17 (compatible avec les PC de l'école).
