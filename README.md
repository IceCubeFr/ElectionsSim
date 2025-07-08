# ElectionsSim
ElectionsSim est un simulateur d'élections intéractif à but pédagogique simulant des élections en France.

## Pré-requis
- **Système d'exploitation :** Linux (MacOs et Windows n'ont pas été testés)
- **Machine :** Pas de pré-requis particulier concernant les performances de la machine

## Installation
**Etape 1 : Téléchargez**  
Récupérez les fichiers suivants dans la release et mettez les dans un dossier ensemble : 
- IHM-App.jar
- ihm-launch.sh
- openjfx-21.0.7_linux-x64_bin-sdk.zip

**Etape 2 : Installation**  
Glissez le fichier .zip dans un dossier que vous nommerez `lib`. Dézippez ensuite le fichier dans ce même dossier.
Pas d'autre installation nécessaire

**Etape 3 : Lancement**  
Dans un terminal, mettez vous dans le dossier que vous venez de créer où il y a le fichier `ihm-launch.sh` puis executez la commande suivante : 
```
./ihm-launch.sh
```
L'application se lancera correctement.

**Bonus : Lancement sans le fichier .sh**  
Si l'étape 3 ne fonctionne pas (ce qui pourrait arriver pour différentes raisons), executez les commande suivantes : 
```
mkdir .data
java --module-path lib/openjfx-21.0.7_linux-x64_bin-sdk/javafx-sdk-21.0.7/lib --add-modules javafx.controls,javafx.fxml -jar IHM-App.jar
```
En cas de problème, même après cela, contactez moi.

-----

## Objectifs du projet
L'objectif principal de cette application est de montrer aux personnes l'impact qu'a l'abstention dans les résultats des élections en France. Il s'agit de la prévention contre cette cause importante qui se normalise malheureusement depuis ces dernières années.  

Ce projet a été effectué dans le contexte d'un SNU durant l'été 2025. Il ne sera pas maintenu à jour mais l'objectif est qu'il fonctionne sans bug majeur.

## Fonctionnement de l'application
### Mise en place
Un maître du jeu définit deux candidats et un code secret (le code servira à valider les votes pour éviter les votes doubles). Le maître du jeu peut créer un nouveau tour des élections, qui demandera, jusqu'à ce que le maître le décide, pour quel candidat la personne souhaite voter (par défaut sans possibilité d'abstention, paramètre réglable). A chaque vote, le code secret doit être entré par le maître du jeu, permettant ainsi d'éviter tout vote double (cette fonctionnalité peut être désactivée dans les paramètres). Dès que le maître du jeu souhaite arrêter le tour, il inscrit "fin" au lieu du code secret. Cela amènera directement au menu de gestion. Les résultats de l'élection s'afficheront automatiquement.  

### Création des scénatios
Le maître du jeu peut maintenant créer des abstentionnistes dans les paramètres ou les générer aléatoirement en générant un nouveau scénario qu'il pourra afficher pour le comparer au résultat des élections à priori sans abstentionnistes.  

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
La maquette basse fidélité se trouve dans le dossier "Maquette" du projet

### Droits d'utilisations
Ce projet est soumis à la licence GNU GPL v3. Retrouvez les conditions dans le fichier LICENCE
