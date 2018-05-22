
package game;
import java.util.*;

import game.GameWorld.CelluleData;
import joueurs.DummyJoueur;
import joueurs.GreedyJoueur;
import joueurs.Joueur;
import joueurs.NotreIA;
import joueurs.PowerJoueur;
import joueurs.RandomWalkJoueur;

/**********************************************************************************
 * <p>Class TestPokemonGo : Classe main pour tester les joueurs dans le GameWorld.
 * <p>Important : vous pouvez utiliser getGameWorldData() dans votre code Joueurs 
 *  (par exemple, dans la methode nextAction() ) pour obtenir les données
 * des cellules dans le GameWorld. C'est utile pour chercher des pokemons spécifiques,
 * choisir une action légale, et détecter les joueurs avec un niveau plus haut que vous
 **********************************************************************************/
public class TestPokemonJava {
	
	/**
	 * Un GameWorld
	 */
	private static GameWorld g;
	
	/**
	 * Initialise le GameWorld
	 * @param args ne pas utiliser (inutilisé)
	 */
	public static void main(String[] args) {
		
		//Initialise la liste des types de joueur possibles
		List<Joueur> jTypes = new ArrayList<>(Arrays.asList(new PowerJoueur(0)));
		
		/* Pour créer un nouveau "Game world", il faut donner les parametres suivants :
		 * - x, largeur du gameworld
		 * - y, longueur du gameworld
		 * - une liste contenant une instance de chaque type de joueur
		 * - nombre de joueur à générer pour chaque type de joueur
		 */
		g = new GameWorldReduit(12,7,jTypes,5,true,100);
		g.runGameWorld();
	}
	
	
	/**
	 * <p>Spécification : Appeler cette fonction pour obtenir les données actuelles du GameWorld
	 * <p>Résultat : retourne une liste (lignes) de listes (colonnes) de CelluleData. 
	 * @return données de chaque cellule dans le GameWorld
	 * @see CelluleData
	 * @see GameWorld#getGameWorldData()
	 */
	public static List<List<CelluleData>> getGameWorldData(){
		return g.getGameWorldData();
	}
}
