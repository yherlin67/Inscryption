# Inscryption

Projet réalisé en binôme, dans le cadre de ma première année de BUT informatique.

## Présentation

Ce projet a pour but de développer un jeu de cartes en Java s'inspirant du jeu *Inscryption*. 

Le joueur interagit via la console Java et peut effectuer différentes actions (piocher une carte, la placer sur le plateau, etc.), à l'image du jeu original.

## Fonctionnalités
- A votre gauche, se trouve une balance symbolisant l'écart de score avec votre adversaire. Le premier joueur qui atteint un écart de 5 points en sa faveur remporte la partie. 
- Face à vous, se trouve un plateau constitué de deux lignes de quatre emplacements de cartes. Vous ne pouvez placer des cartes que sur la ligne du bas, votre adversaire uniquement sur la ligne du haut.
- A votre droite, vous disposez d'une pioche. Vous commencez avec 4 cartes en mains et vous pouvez piocher une carte par tour.
  
- Chaque carte "animal" dispose
  - d'un nom,
  - d'un nombre de points d'attaque,
  - d'un nombre de points de vie,
  - d'un nombre de vos cartes sur le plateau à sacrifier pour pouvoir être placée sur le plateau (nombre de gouttes de sang),
  - d'un nombre de vos cartes déjà mortes (tuées ou sacrifiées) pour pouvoir être placée sur le plateau (nombre d'os),
  - d'un booléen indiquant si l'animal est volant ou non,
  - d'un éventuel pouvoir

 Chacune des cartes peut apparaitre en plusieurs exemplaires dans la pioche, dans la main et sur le plateau.
 
- Voci les pouvoirs dont peuvent être dotées les cartes "animaux"
  - Nombreuses vies : reste vivant sur le plateau lorsqu'elle est sacrifiée
  - Croissance : se transforme en loup au début du deuxième tour, où il est sur le plateau
  - Puant : réduit de 1 l'attaque de la carte lui faisant face
  - Coureur : se déplace vers d'un emplacement vers la droite après son attaque. Si l'emplacement vers la droite est bloquée, se déplace vers la         gauche. Si les emplacements vers la gauche et la droite sont bloquées ne se déplace pas.
  - Contact Mortel: s'il inflige des dégâts à une autre créature (donc pas à un obstacle), la créature blessée meurt 
  - Piques pointues : inflige 1 point de dégât à la carte attaquante lorsqu'il est attaqué par une carte

### Déroulement d'un tour
- Au début de votre tour, votre adversaire indique quelles cartes il jouera au tour prochain (représentés par une ligne supplémentaire de 4 emplacements de cartes au-dessus du plateau)
- A chaque tour, vous pouvez piocher une seule carte que vous placez dans votre main,
- Vous pouvez placer autant de cartes de votre main par tour sur le plateau, dans la limite du nombre d'emplacements de cartes disponibles sur votre côté du plateau (au maximum 4) et en respectant les sacrifices à réaliser
- A la fin de votre tour, chacune de vos cartes "animal" attaque. Si une carte de votre adversaire fait face à la carte attaquante, la carte de votre adversaire perd en nombre de points de vie le nombre de points d'attaque de votre carte. 
Si au contraire, aucune carte de votre adversaire ne se trouve face à une de vos cartes, le score est augmenté en votre faveur du nombre de points d'attaque de votre carte.
Les cartes "animal" volantes attaquent directement le score même si une carte adverse se trouve en face d'elle.

Un message indique ensuite les dégâts infligés par les attaques à la fin du tour. 

Après votre tour, votre adversaire joue de la même façon que vous (à la seule différence que vous n'avez pas à indiquer les cartes que vous jouerez au prochain tour).


### Déroulement de la partie
- Au début de la partie le joueur, prend en main les 4 premières cartes de la pioche.
- Des cartes obstacles peuvent être présentes sur le plateau au début de la partie. Elles occupent chacune un emplacement de carte, possèdent un certain nombre de points de vie et doivent être éliminées par vous ou votre adversaire avant de placer une carte à leur emplacement.
- La partie se termine lorsqu'un déséquilibre de 5 points apparaît dans le score.


### Déroulement du jeu
- Au début de la partie le joueur commence avec une pioche de 15 cartes constituée majoritairement d'écureuils.
- Le jeu est constitué de trois parties. Vous gagnez si vous remportez les trois parties.
- A la fin de la deuxième partie, vous pouvez ajouter à votre pioche une nouvelle carte parmi deux cartes proposées. Aussi, vous devez obligatoirement sacrifier une carte, et si elle possède un pouvoir, l'ajouter à une autre carte "animal".

## Captures d'écran
<img width="2158" height="1394" alt="image" src="https://github.com/user-attachments/assets/16f31810-030d-42eb-ae9b-a4c72d2c1313" />
<img width="1850" height="626" alt="image" src="https://github.com/user-attachments/assets/f1ffed41-dbfa-4b55-b2a6-7c08144cb9c8" />

## Installation

1. Cloner le dépôt :
```bash
   git clone https://github.com/yherlin67/Inscryption.git
```

2. Ouvrir le projet dans IntelliJ IDEA :
   - **File** → **Open** → sélectionner le dossier du projet

3. S'assurer que le JDK est bien configuré (OpenJDK 25 recommandé) :
   - **File** → **Project Structure** → **Project SDK** 

4. Lancer le jeu :
   - Ouvrir `Main.java` (dans `src/`)
   - Clic droit → **Run 'Main.main()'**
   - Ou cliquer sur la flèche verte ▶️ en haut de l'écran
