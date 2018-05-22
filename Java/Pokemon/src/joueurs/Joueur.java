package joueurs;
import java.util.List;

import game.Pokemon;

public abstract class Joueur {
	
	private int x,y;
	int id;
	List<Pokemon> mesPokemons;
	boolean vivant;
	
	/**
	 * TODO
	 * Ajoute un pokemon donné dans la liste des pokemons du joueur
	 * @param p pokemon donné
	 */
	public abstract void ajoutePokemon(Pokemon p);
	
	/**
	 * TODO
	 * Resultat : retour le pokemon que vous voulez utiliser en combat (pour fight/tournoi du fin de tour)
	 * @return Pokemon
	 */
	public abstract Pokemon choisirPokemon();
	
	/**
	 * UTILISER LA SOLUTION DE "DummyJoueur.java"
	 * Resultat : retour une copie de cette type du joueur (mais tous attribus privee doivent etre du valeur defaut)
	 * @param id ID du nouveau joueur
	 * @return une nouvelle copie de ce type du joueur
	 */
	public abstract Joueur copy(int id);
	
	/**
	 * TODO
	 * Decider l'action suivant pour le joueur. Utiliser constructeur de la classe JoueurAction pour créer une action. 
	 * - ATTENTION : si l'action ne conforme pas aux règles, le joueur est disqualifié
	 * @return JoueurAction l'action (fight/capture/move) choisi
	 * @see JoueurAction
	*/
	public abstract JoueurAction nextAction();
	
	/**
	 * rend le ID du joueur
	 * @return int id
	*/
	public final int getID(){
		/*  */
		return id;
	}
	

	/**
	 * rend le niveau du meilleur pokemon du joueur
	 * @return int niveau
	*/
	public abstract int getNiveau();
	
	/** 
	 * rend la position x du joueur
	 * @return int position x
	 */
	public final int getPositionX(){
		return x;
	}
	
	/** 
	 * rend la position y du joueur
	 * @return int position y
	 */
	public final int getPositionY(){
		return y;
	}
	
	/** TODO
	 * @return rend le type du meilleur pokemon du joueur
	 */
	public abstract String getStrongestPokemonType();
	
	/** TODO
	 * @return si ce joueur est vivant
	 */
	public abstract boolean getVivant();
	
	/** TODO
	 * 
	 * Mettre les trois meilleur pokemon du joueur dans une liste
	 * @return une liste avec les trois meilleur pokemons
	 */
	public abstract List<Pokemon> montreTroisPokemon();
	
	/** TODO 
	 * Appeller par Tournoi pour preparer votre bataille, donnée le type du meilleur pokemon de l'adversaire
	 * Vous pouvez trier la liste des pokemons en preparation pour combat contre un Joueur.
	 * @param type type du meilleur pokemon de l'adversaire
	 */
	public abstract void prepareBattle(String type);
	
	/** 
	 * mise a jour la position du joueur
	 * @param x position x
	 * @param y position y
	 */
	public final void updatePosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/** TODO
	 * creer un bonbon
	 */
	public abstract void creerBonbon();
	
	/** TODO
	 * utiliser un bonbon
	 */
	public abstract void utiliseBonbon();
	
	/** TODO
	 * tue ce player
	 * @return last message du joueur
	 */
	public abstract String mort();
	


}
