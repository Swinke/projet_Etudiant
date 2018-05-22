package game;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.List;

/**********************************************************************************
 * Class GameWorldGUI : le GUI pour PokemonJava GameWorld
 * 
 * 
 **********************************************************************************/
class GameWorldGUI {
	
	/**
	 * largeur du GameWorld en nombre de cellules
	 */
	int dimX;
	
	/**
	 * longueur du GameWorld en nombre de cellules
	 */
	int dimY;
	
	/**
	 * Taille d'une cellule dans le GUI
	 */
	final int cellSize = 100;
	
	/**
	 * Si le jeu est en mode automatique, 
	 */
	boolean automatic;
	
	/**
	 * <p>Constructeur de GameWorldGUI. Initialise une fenêtre de taille donnée, plus de l'espace de chaque côté.
	 * @param dimensionX largeur du GameWorld supérieur ou égal à 1
	 * @param dimensionY longueur du GameWorld supérieur ou égal à 1
	 * @param auto si le GameWorld est automatique
	 */
	GameWorldGUI(int dimensionX,int dimensionY, boolean auto){
		if(dimensionX<=0||dimensionY<=0){
			System.err.println("Dimensions doivent etre au moins 1.");
			System.exit(0);
		}
		dimX = dimensionX;
		dimY = dimensionY;
		StdDraw.setCanvasSize(cellSize*(dimX+2), cellSize*(dimY+2));
		StdDraw.setXscale(0, cellSize*(dimX+2));
		StdDraw.setYscale(0, cellSize*(dimY+2));
		StdDraw.enableDoubleBuffering();
		automatic =auto;
	}
	
	void inAnimation(){
		//TODO: bonus
	}
	
	void outAnimation(){
		//TODO: bonus
	}
	
	void getNextKey(){
		boolean mp = false;
		while(!mp){
			if(StdDraw.isKeyPressed(KeyEvent.VK_N)){
				mp=true;
				//TestPokemonJava.nextTurn();
			}
		}
	}
	
	/**
	 * Mise à jour du GUI du GameWorld, incluant:
	 * <ul>
	 * <li>Les attributs de chaque cellule (active, terrain, position),</li>
	 * <li>les pokemons sauvages dans chaque cellule,</li>
	 * <li>les joueurs (vivants et morts) dans chaque cellule,</li>
	 * </ul>
	 * @param gameWorld données des cellules du GameWorld
	 * @see GameWorld.CelluleData
	 */
	void updateGameWorld(List<List<GameWorld.CelluleData>> gameWorld){
		StdDraw.clear();
		
		for(int x = 0; x<gameWorld.size();x++){
			for(int y = 0; y<gameWorld.get(x).size();y++){
				
				//les bords de la cellule
				StdDraw.setPenRadius(0.001);
				StdDraw.setPenColor(StdDraw.GRAY);
				StdDraw.square((x+1)*cellSize+cellSize/2,(y+1)*cellSize+cellSize/2, (cellSize/2)-1);
				
				//Les cellules inactives se remplissent de gris
				if(!gameWorld.get(x).get(y).active){
					StdDraw.filledSquare((x+1)*cellSize+cellSize/2,(y+1)*cellSize+cellSize/2, (cellSize/2)-1);
					continue;
				}
				
				//Les cellules actives sont remplies par les images correspondant aux terrains de celles-ci
				StdDraw.picture((x+1)*cellSize+cellSize/2, (y+1)*cellSize+cellSize/2, "images/"+gameWorld.get(x).get(y).celluleType+".png",cellSize-2,cellSize-2);
				
				int pokeNum =0;
				int jNum=0;
				
				for(int k=0; k<gameWorld.get(x).get(y).cellCreatures.size();k++){
					GameWorld.Creature c = gameWorld.get(x).get(y).cellCreatures.get(k);
					if(c.nom.charAt(0)=='P'){
						StdDraw.picture((x+1)*cellSize+pokeNum*(cellSize/4)+(cellSize/5),(y+1)*cellSize+(cellSize/7), "images/"+c.nom.substring(1,c.nom.length())+".png",cellSize/5,cellSize/5);
						pokeNum++;
					}
					else{
						switch(c.type){
						case "Feu":StdDraw.setPenColor(StdDraw.RED);break;
						case "Eau":StdDraw.setPenColor(StdDraw.BLUE);break;
						case "Herbe":StdDraw.setPenColor(StdDraw.GREEN);break;
						case "Mort":StdDraw.setPenColor(StdDraw.GRAY);break;
						default:StdDraw.setPenColor(StdDraw.BLACK);break;
						}
						Font font = new Font("Arial", Font.BOLD, (cellSize/5));
						StdDraw.setFont(font);
						StdDraw.text((x+1)*cellSize+(jNum%4)*(cellSize/4)+(cellSize/10),(y+1)*cellSize-(jNum/4)*((cellSize/10))+(cellSize-(cellSize/5)), c.nom.substring(1,c.nom.length()));
						jNum++;
					}
				}
			}
		}
		StdDraw.show();
		StdDraw.pause(1000);
		
		//fonction pour debugger etape par etape
		if(!automatic)
			getNextKey();
	}
}
