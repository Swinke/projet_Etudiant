
package game;
import java.util.*;

import joueurs.Joueur;
import joueurs.JoueurAction;
import joueurs.NotreIA;

/**********************************************************************************
 * <p>Classe GameWorld : Tous les fonctions concernant le GameWorld.
 * 
 * 
 *********************************************************************************/
public class GameWorld {
	
	/**
	 * La classe interne protégée d'une cellule dans le GameWorld
	 */
	class Cellule{
		
		/**
		 * les cordonnées x et y de la cellule
		 */
		private int x, y;
		
		/**
		 * les pokemons dans la cellule
		 */
		List<Pokemon> pokemons;
		
		/**
		 * les joueurs dans la cellule
		 */
		List<Joueur> joueurs;
		
		/**
		 * le terrain de la cellule : feu, eau, ou herbe
		 */
		String terrain;
		
		/**
		 * Si la cellule est active ou pas. 
		 * (Utilisant pour disactvée les cellules dans une carte rétrécissant)
		 */
		public boolean active;
		
		
		/**
		 * <p>Prérequis : Cordonnées de la cellule
		 * 
		 * <p>Spécification :
		 * <ul>
		 * <li>Constructeur pour une cellule</li>
		 * <li>Initialiser les cordonnées, les listes des pokemons et joueurs</li>
		 * <li>Frayer quelques pokemons sauvages dans la cellule</li>
		 * </ul>
		 * @param x x position de la cellule
		 * @param y y position de la cellule
		 */
		Cellule(int x, int y){
			
			this.x=x;
			this.y=y;
			pokemons = new ArrayList<Pokemon>();
			joueurs = new ArrayList<Joueur>();
			terrain=Pokemon.pType[new Random().nextInt(3)];
			active =true;
			frayerPokemonInitiale();
		}
		
		/**
		 * <p>Prérequis : Le joueur j est vivant
		 * <p>Spécification : enregistrer le joueur dans la cellule. Utilisé par GameWorld.
		 * @param j joueur vivant
		 */
		void addJoueur(Joueur j){
			joueurs.add(j);
		}
		
		/**
		 * <p>Prérequis : id d'un joueur dans la liste
		 * <p>Spécification : supprimer un joueur dans la cellule
		 * @param id id du joueur à supprimer
		 */
		void removeJoueur(int id){
			for(int i = 0; i<joueurs.size();i++){
				if(joueurs.get(i).getID()==id){
					joueurs.remove(i);
					break;
				}
			}
		}
		
		/**
		 * <p>Spécification : 
		 * <ul>
		 * <li>Supprimer les pokemons capturés, </li>
		 * <li>faireTournoi pour tous les joueurs dans la meme cellule, </li>
		 * <li>et frayer des pokemons dans la cellule</li>
		 * </ul>
		 */
		void finTour(){
			if(active){
				supprimerPokemonCapture();
				invokeBattleDansCell();
				frayerPokemon();
			}
		}
		
		/**
		 * rend les données de la cellule
		 * @return une liste des données de la cellule
		 * @see CelluleData
		 */
		public CelluleData getCellData(){
			List<Creature> data = new ArrayList<Creature>();
			for(int i=0;i<joueurs.size();i++){
				data.add(new Creature("J"+joueurs.get(i).getID(),joueurs.get(i).getStrongestPokemonType(),joueurs.get(i).getNiveau()));
			}
			for(Pokemon p : pokemons){
				data.add(new Creature("P"+p.getNom(),p.getType(),p.getNiveau()));
			}
			
			CelluleData cd = new CelluleData();
			cd.active=active;
			cd.cellCreatures = data;
			cd.celluleType = terrain;
			
			return cd;
		}
		
		/**
		 * cherche pour un pokemon dans la cellule
		 * @param nom nom du pokemon
		 * @return le premier instance du pokemon avec le nom fourni
		 */
		public Pokemon getPokemon(String nom){
			for(Pokemon p : pokemons){
				if(p.getNom().equals(nom))
					return p;
			}
			System.out.println(nom+" not found.");
			return null;
		}
		
		/**
		 * à la fin du tour, faire tournoi entre les joueurs vivant dans la cellule
		 */
		public void invokeBattleDansCell(){
			List<Joueur> j = new ArrayList<Joueur>();
			
			for(int i =0; i<joueurs.size(); i++){
				if(joueurs.get(i).getVivant())
					j.add(joueurs.get(i));
			}
			Tournoi.faireTournoi(j,terrain);
			
			//int vainqueur = Tournoi.faireTournoi(j);
			//System.out.println("Le vanqueur du Cell est " + vainqueur);
			
		}
		
		/**
		 * supprimer les pokemons capturés dans la cellule
		 */
		public void supprimerPokemonCapture(){
			int i = 0;
			while(pokemons.size()>i){
				if(pokemons.get(i).isCaptured()){
					pokemons.remove(i);
				}
				else i++;
			}
		}
		
		/**
		 * frayer des pokemons dans la cellule avec la chance de 1%
		 * !!!! bonus : nous avons ajoute Mewtwo
		 */
		private void frayerPokemon(){
			Random r = new Random();
			if(pokemons.size()==0&&r.nextDouble()<0.01){
				for(int i=0; i<r.nextInt(3)+1;i++)
					pokemons.add(Pokemon.makePokemon(terrain));
				//ajoute mewtwo si celui-ci n'est pas deja présent
				if(!mewtwoPresent){
					pokemons.add(Pokemon.makePokemon("Psy"));
					System.out.println("-----------------------------");
					System.out.println("Mewtwo apparait de niveau : " + pokemons.get(pokemons.size()-1).niveau);
					System.out.println("-----------------------------");
					mewtwoPresent = true;
				}
			}
		}
		
		/**
		 * frayer des pokemons au début du jeu avec la chance de 20%
		 */
		private void frayerPokemonInitiale(){
			/*
			 * Spec: spawn n pokemons dans le cell en random
			 * 
			 */
			Random r = new Random();
			if(pokemons.size()==0&&r.nextDouble()<0.2){
				for(int i=0; i<r.nextInt(3)+1;i++)
					pokemons.add(Pokemon.makePokemon(terrain));
			}
		}
		
		/**
		 * rend la position x de la cellule
		 * @return x position de la cellule 
		 */
		public int getX(){
			return x;
		}
		
		/**
		 * rend la position y de la cellule
		 * @return y position de la cellule 
		 */
		public int getY(){
			return y;
		}
	}
	
	/**
	 * La classe interne CelluleData pour rendre les données de la cellule aux joueurs et 
	 * à la classe GameWorldGUI
	 */
	public class CelluleData{
		
		/**
		 * type de la cellule : feu, eau, ou herbe
		 */
		public String celluleType;
		
		/**
		 * si la cellule est active
		 */
		public boolean active;
		
		/**
		 * liste des creatures dans la cellule
		 * @see Creature
		 */
		public List<Creature> cellCreatures;
	}
	
	/**
	 * Données d'un creature dans une cellule. Les joueurs et les pokemons sont des creatures.
	 * A noter, creature est une copie texte d'un creature dans la cellule, donc c'est futile
	 * à modifier les attributs dans les instances de cette classe.
	 */
	public class Creature{
		
		
		/**
		 * Nom du creature:
		 * <ul>
		 * <li> "J" + id (int) pour les joueurs</li>
		 * <li> "P" + nom du pokémon pour les pokémon</li>
		 * </ul>
		 */
		public String nom;
		
		/**
		 * type du pokemon (pour un pokemon) ou type du meilleur pokemon du joueur (pour un joueur)
		 */
		public String type;
		
		/**
		 * niveau du pokemon ou niveau du meilleur pokemon du joueur
		 */
		public int niveau;
		
		/**
		 * Constructeur pour Creature
		 * @param nom nom du creature suivant
		 * @param type type du creature: feu, eau, ou herbe
		 * @param niv niveau du creature
		 */
		public Creature(String nom,String type,int niv){
			this.nom = nom;
			this.niveau = niv;
			this.type = type;
		}
	}
	
	
	/* ************************************
	 * Les attribus privée pour GameWorld *
	 * ************************************/
	
	/**
	 * !!!! Bonus !!!!
	 * Si Mewtwo est present
	 */
	boolean mewtwoPresent = false;
	/** 
	 * largeur du GameWorld
	 */
	int dimensionX;
	
	/**
	 * longeur du GameWorld
	 */
	int dimensionY;
	
	/**
	 * Tableau avec tous les cellules de GameWorld
	 */
	Cellule[][] myGameWorld;
	
	/**
	 * Nombres des joueurs dans GameWorld
	 */
	int totalJoueurs=0;
	
	/**
	 * Map de tous les joueurs dans GameWorld
	 */
	Map<Integer,Joueur> tousJoueurs;
	
	/**
	 * Interface graphique du GameWorld
	 */
	GameWorldGUI gInterface;
	
	/**
	 * Si le Gameworld est en mode automatique
	 */
	boolean automatic;
	
	/**
	 * Nombre des tours à lancer si le GameWorld est en mode automatique
	 */
	int tours;
	
	/**
	 * Nombre des tours actuellement passé
	 */
	int totalTours;
	
	/* **************************************
	 * Les methodes protégée pour GameWorld *
	 * Protégée c'est à dit: vous ne pouvez *
	 * pas utiliser ces fonctions dans votre*
	 * code joueurs                         *
	 * **************************************/
	
	/**
	 * Constructeur pour GameWorld
	 * @param dimx largeur du GameWorld supérieur ou égal à 1
	 * @param dimy longeur du GameWorld supérieur ou égal à 1
	 * @param jList liste des joueurs (une copie pour chaque type du joueur)
	 * @param jNum nombre du chaque type du joueur à frayer dans GameWorld
	 * @param automatic si on utilise le mode automatique ou pas
	 * @param tours si en mode automatique, le nombre des tours à lancer
	 */
	GameWorld(int dimx, int dimy, List<Joueur> jList, int jNum, boolean automatic,int tours){
		
		if(dimx<=0||dimy<=0){
			System.err.println("Dimensions doivent etre au moins 1.");
			System.exit(0);
		}
		dimensionX = dimx;
		dimensionY = dimy;
		
		myGameWorld = new Cellule[dimx][dimy];
		
		for(int x = 0; x<dimx;x++){
			for(int y =0; y<dimy; y++){
				myGameWorld[x][y] = new Cellule(x,y);
			}
		}
		
		tousJoueurs = new HashMap<Integer,Joueur>();
		
		gInterface = new GameWorldGUI(dimensionX,dimensionY, automatic);
		
		initialiseJoueurs(jList,jNum);
		
		this.tours = tours;
		this.automatic = automatic;
		totalTours = 0;
		
	}
	
	/**
	 * lancer le GameWorld en mode automatique ou manuel
	 * <ul>
	 * <li>Si automatique: le jeu est autoplay pour les nombres de tours indiquées</li>
	 * <li>Si manuel: l'utilisateur taper 'N' pour lancer chaque tour</li>
	 * </ul>
	 * @see GameWorldGUI#getNextKey()
	 */
	void runGameWorld(){
		if(automatic){
			for(int i =0;i<tours;i++){
				nextTurn();
			}
		}
		else {
			gInterface.automatic=false;
			while(true){
				gInterface.getNextKey();
				nextTurn();
			}
		}
	}
	
	/**
	 * Rend une liste des listes de CelluleData pour chaque cellule dans le game world.
	 * Pour un example d'utilisation, reporter à la methode updateGameWorld dans 
	 * la classe GameWorldInterface
	 * @return un CelluleData pour chaque cellule dans le game world
	 * @see CelluleData
	 */
	List<List<CelluleData>> getGameWorldData(){
		List<List<CelluleData>> gameWorldData = new ArrayList<List<CelluleData>>();
		for(int i =0; i<dimensionX; i++){
			gameWorldData.add(i, new ArrayList<CelluleData>());
			for(int j=0;j<dimensionY;j++){
				gameWorldData.get(i).add(j, myGameWorld[i][j].getCellData());
			}
		}
		return gameWorldData;
	}
	
	/**
	 * Initialiser les joueurs dans le GameWorld : donné un nombre num et une liste des
	 * joueurs types joueurTypeList, frayer num instances du chaque joueur dans joueurTypeList.
	 * Chaque joueur reçoit un ID et un pokemon random du niveau 1
	 * @param joueurTypeList liste d'un instance de chaque type du joueur
	 * @param num nombre de chaque type de joueur à frayer
	 */
	private void initialiseJoueurs(List<Joueur> joueurTypeList, int num){
		Random r = new Random();
		for(int i=0; i<joueurTypeList.size();i++){
			for(int k =0; k<num; k++){
				totalJoueurs+=1;
				int x=r.nextInt(dimensionX);
				int y=r.nextInt(dimensionY);
				Joueur j = joueurTypeList.get(i).copy(totalJoueurs);
				j.updatePosition(x, y);
				tousJoueurs.put(totalJoueurs, j);
				myGameWorld[x][y].addJoueur(j);
				j.ajoutePokemon(Pokemon.makeNiveau1Pokemon());
			}		
		}
		gInterface.updateGameWorld(getGameWorldData());
	}
	
	/**
	 * lancer un tour dans GameWorld
	 * <ul>
	 * <li>Chaque joueur vivant doit faire une "action valide" dans le temps limité (1 seconde) : bouger
	 * à une cellule voisine active, capturer ou combatter un pokemon dans la cellule du joueur</li>
	 * <li>Si un joueur tent de faire une action interdit, il est tué: bouger dans une cellule inactive,
	 * bouger dehors le GameWorld, capturer ou combatter un pokemon qui n'existe pas dans la
	 * cellule du joueur </li>
	 * <li>après les actions joueurs, on appeller finTour pour chaque cellule à faire les actions
	 * de la fin du tour</li>
	 * <li>mise à jour l'interface visual</li>
	 * <li>incrémenter les tours actuel</li>
	 * </ul>
	 * @see Cellule#finTour()
	 */
	void nextTurn(){
		//Affiche le nombre de tour
		System.out.println();
		System.out.println("--- Tours : " +totalTours+" ---");
		int vivant= 0;
		int jVivant=0;
		
		for( int i =1; i<=totalJoueurs;i++){
			if(tousJoueurs.get(i).getVivant()){
				vivant++;
				jVivant=tousJoueurs.get(i).getID();
			}
			if(vivant>1)
				break;
		}
		
		if(vivant==1){
			System.out.println("Le vainquer est J"+jVivant+" du niveau "+tousJoueurs.get(jVivant).getNiveau());
			System.exit(0);
		}else if(vivant==0){
			System.out.println("Vous êtes tous dummies...");
			System.exit(0);
		}
		
		for( int i =1; i<=totalJoueurs;i++){
			if(!tousJoueurs.get(i).getVivant())
				continue;
			Joueur j = tousJoueurs.get(i);
			JoueurAction a = tousJoueurs.get(i).nextAction();
			
			if(a==null){
				System.out.println(j.mort()+" Raison: rien d'action choisi");
			}
			
			String actDef = a.getActionDefinition();
			switch(a.getActionType()){
			case move:
				moveAction(j,actDef);
				break;
			case fight:
				fightAction(tousJoueurs.get(i),myGameWorld[j.getPositionX()][j.getPositionY()].getPokemon(actDef),myGameWorld[j.getPositionX()][j.getPositionY()].terrain);
				break;
			case capture:
				captureAction(tousJoueurs.get(i),myGameWorld[j.getPositionX()][j.getPositionY()].getPokemon(actDef));
				break;
			case mort:
				System.out.println(j.mort()+" Raison: Action mort choisir ?!");
				break;
			}
		}
		
		for(Cellule[] cList : myGameWorld){
			for(Cellule c : cList){
				c.finTour();
			}
		}
		
		Joueur meilleurJ = tousJoueurs.get(1);
		for(int id : tousJoueurs.keySet()){
			if(tousJoueurs.get(id).getNiveau() > meilleurJ.getNiveau())
				meilleurJ = tousJoueurs.get(id);
		}
		System.out.println("Niveau meilleur pokemon possede par un ou plusieurs joueurs : " + meilleurJ.getNiveau());
		
		gInterface.updateGameWorld(getGameWorldData());
		totalTours++;
	}
	
	/**
	 * Traitement d'une action du type "Capture"
	 * @param j le joueur
	 * @param p le pokemon à capturer
	 */
	void captureAction(Joueur j, Pokemon p){
		if(p!=null)
			Tournoi.capture(j, p);
		else System.out.println(j.mort()+" Capture un pokemon qui n'existe pas dans la cellule.");
	}
	
	/**
	 * Traitement d'une action du type "Move"
	 * @param j joueur
	 * @param direction direction du movement désiré
	 */
	void moveAction(Joueur j, String direction){
		int newPositionX=j.getPositionX();
		int newPositionY=j.getPositionY();
		boolean moveEffective =false;
		
		switch(direction){
		case "Left":
			if(newPositionX>0 && myGameWorld[newPositionX-1][newPositionY].active){
				newPositionX-=1;
				moveEffective=true;
			}
			break;
		case "Right":
			if(newPositionX<(dimensionX-1) && myGameWorld[newPositionX+1][newPositionY].active){
				newPositionX+=1;
				moveEffective=true;
			}
			break;
		case "Down":
			if(newPositionY>0 && myGameWorld[newPositionX][newPositionY-1].active){
				newPositionY-=1;
				moveEffective=true;
			}
			break;
		case "Up":
			if(newPositionY<(dimensionY-1) && myGameWorld[newPositionX][newPositionY+1].active){
				newPositionY+=1;
				moveEffective=true;
			}
			break;
		}
		
		if(moveEffective){
			myGameWorld[j.getPositionX()][j.getPositionY()].removeJoueur(j.getID());
			myGameWorld[newPositionX][newPositionY].addJoueur(j);
			j.updatePosition(newPositionX, newPositionY);
		}
		else System.out.println(j.mort()+" Raison: mal sens de direction à "+direction);
	}
	
	/**
	 * Traitement d'une action du type "Fight"
	 * @param j le joueur
	 * @param p pokemon à batter
	 * @param terrain type du terrain dans la cellule
	 */
	void fightAction(Joueur j, Pokemon p, String terrain){
		if(p!=null){
			int n =Tournoi.bataillePokemon(j.choisirPokemon(), p,terrain);
			if(n==2){
				//si le pokémon sur la carte perde, il disparaît de la carte
				p.capture();
			}
		}
		else System.out.println(j.mort()+" Combat un pokemon qui n'existe pas dans la cellule.");
	}
	
	
	
	
}
