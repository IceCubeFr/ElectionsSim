# ElectionsSim
ElectionsSim est un simulateur d'élections intéractif à but pédagogique simulant des élections en France.

## Objectifs du projet
L'objectif principal de cette application est de montrer aux personnes l'impact qu'a l'abstention dans les résultats des élections en France. Il s'agit de la prévention contre cette cause importante qui se normalise malheureusement depuis ces dernières années.  

Ce projet a été effectué dans le contexte d'un SNU durant l'été 2025. Il ne sera pas maintenu à jour mais l'objectif est qu'il fonctionne sans bug majeur.

## Fonctionnement de l'application
### Mise en place
Un maître du jeu définit deux candidats et un code secret (le code servira à valider les votes pour éviter les votes doubles). Le maître du jeu peut créer un nouveau tour des élections, qui demandera, jusqu'à ce que le maître le décide, pour quel candidat la personne souhaite voter (par défaut sans possibilité d'abstention, paramètre réglable). A chaque vote, le code secret doit être entré par le maître du jeu, permettant ainsi d'éviter tout vote double (cette fonctionnalité peut être désactivée dans les paramètres). Dès que le maître du jeu souhaite arrêter le tour, il inscrit "fin" au lieu du code secret. Cela amènera directement au menu de gestion. Les résultats de l'élection s'afficheront automatiquement.  

### Création des scénatios
Le maître du jeu peut maintenant créer des abstentionnistes dans les paramètres ou les générer aléatoirement en générant un nouveau scénario qu'il pourra afficher pour le comparer au résultat des élections à priori sans abstentionnistes.  

### Téléchargement
Actuellement, vous pouvez télécharger le code source et trouver la classe main pour lancer l'application. A terme, des scripts de lancement seront développés pour Linux et Windows. Pas de prise en charge sur MacOs, essayez la version Linux, ça fonctionnera peut être.

## Le coin dev
### Etiquettage des commits
Les commits sont étiquettés en fonction de leur contenu :  
**Modification globale (impactant la forme du projet, pas le contenu)**
```
"global::<modifications>"
```

**Edition de la partie dev**
```
"dev::<modifications>"
```

**Edition de la partie interfaces**
```
"ihm::<modifications>"
```

**Correction de bugs divers**
```
"patch::<modifications>"
```

### Mockups
*Vous retrouverez la maquette basse fidélité ici quand elle sera terminée*

### Droits d'utilisations
Le code est open-source, vous pouvez vous en inspirer pour vos projets. J'interdit néanmoins la copie et l'appropriation dans un but commercial. L'utilisation de l'application est autorisée à tout individu souhaitant l'utiliser. Pensez à vous référer aux Releases pour le télécharger.