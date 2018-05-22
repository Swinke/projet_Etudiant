package joueurs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import game.Pokemon;
import game.TestPokemonJava;
import game.GameWorld.CelluleData;
import game.GameWorld.Creature;

public class GreedyJoueur extends Joueur {

	/**
	 * Constructeur GreedyJoueur
	 * Le GreedyJoueur essaye de capturer le plus de pokemon possible
	 * @param ID
	 */
	public GreedyJoueur(int ID) {
		mesPokemons = new ArrayList<Pokemon>();
		vivant=true;
		id=ID;
	}

	/**
	 * Ajoute le pokemon dans la liste
	 * Ajoute le pokemon en fonction de son niveau
	 * mesPokemons est triee en ordre décroissant de niveau
	 */
	@Override
	public void ajoutePokemon(Pokemon p) {
		boolean ajout = false;
		List<Pokemon> temp = new ArrayList<Pokemon>();
		temp.addAll(mesPokemons);
		int i=0;
		while(i<temp.size() && !ajout){
			if(temp.get(i).getNiveau() < p.getNiveau()){
				mesPokemons.add(i,p);
				ajout = true;
			}
			i++;
		}
		if(!(ajout)){
			mesPokemons.add(p);
		}
	}
	
	/**
	 * GreedyJoueur retourne son meilleur pokemon en combat ou capture
	 * liste trie donc le pokemon a l'indice 0
	 * @see ajoutePokemon
	 */
	@Override
	public Pokemon choisirPokemon() {
		if(mesPokemons.isEmpty())
			return null;
		else return mesPokemons.get(0);
	}

	@Override
	public Joueur copy(int id) {
		return new GreedyJoueur(id);
	}
	
	/**
	 * Renvoie le premier pokemon de la cellule, si pas de pokemon renvoie null
	 * @param x la position x de la cellule
	 * @param y la position y de la cellule
	 * @return p est null si pas de pokemon sinon le 1er de la cellule
	 */
	public Creature PokemonCellule(int x, int y){
		List<List<CelluleData>> allCellule = TestPokemonJava.getGameWorldData();
		CelluleData myCellule = allCellule.get(x).get(y);
		List<Creature> creatureCell = myCellule.cellCreatures;
		for(Creature p : creatureCell){
			if(p.nom.charAt(0) == 'P')
				return p;
		}
		return null;
	}
	
	/**
	 * GreedyJoueur tente de capturer le plus de pokemon
	 * Va en priorite sur les cases ou il y a un pokemon
	 */
	@Override
	public JoueurAction nextAction() {
		//Initialisation des limites de terrain
		List<List<CelluleData>> allCellule = TestPokemonJava.getGameWorldData();
		int maxX = allCellule.size()-1;
		int maxY = allCellule.get(0).size()-1;
		int x = getPositionX();
		int y = getPositionY();
		int niveau = getNiveau();
		
		// Initialisation des directions possibles pour "move"
		HashMap<String,List<Integer>> mvt = new HashMap<String,List<Integer>>();
		mvt.put("Right",new ArrayList<Integer>());
		mvt.get("Right").add(x+1);
		mvt.get("Right").add(y);
		mvt.put("Left",new ArrayList<Integer>());
		mvt.get("Left").add(x-1);
		mvt.get("Left").add(y);
		mvt.put("Up",new ArrayList<Integer>());
		mvt.get("Up").add(x);
		mvt.get("Up").add(y+1);
		mvt.put("Down",new ArrayList<Integer>());
		mvt.get("Down").add(x);
		mvt.get("Down").add(y-1);
		
		
		/*
		 * Si pokemon dans la cellule le capture
		 */
		Creature pokemon = PokemonCellule(x,y);		
		if(pokemon != null && pokemon.niveau <= niveau){
			System.out.println(id + " capture : " + pokemon.nom);
			String poke = pokemon.nom.substring(1);
			return new JoueurAction("capture",poke);
		}
		
		// On retire de la liste les directions interdites
		if(x == 0 || !(allCellule.get(x-1).get(y).active))
			mvt.remove("Left");
		if(x == maxX || !(allCellule.get(x+1).get(y).active))
			mvt.remove("Right");
		if(y == 0 || !(allCellule.get(x).get(y-1).active))
			mvt.remove("Down");
		if(y == maxY || !(allCellule.get(x).get(y+1).active))
			mvt.remove("Up");
		
		/*
		 * Pour tout les mouvement possibles on regarde si un pokemon est present dans la cellule d'arrive
		 * Si un pokemon est present on se déplace vers cette case
		 */
		for(String key : mvt.keySet()){
			pokemon = PokemonCellule(mvt.get(key).get(0),(mvt.get(key).get(1)));
			if(pokemon != null && pokemon.niveau <= getNiveau())
				return new JoueurAction("move",key);
		}
		
		/*
		 * Si pas de pokemon present autour du Joueur on se déplace aleatoirement
		 */
		int random = new Random().nextInt(mvt.size());
		List<String> mouvement = new ArrayList<String>();
		for(String key : mvt.keySet()){
			mouvement.add(key);
		}
		return new JoueurAction("move",mouvement.get(random));
	}

	/**
	 * Renvoi le niveau du premier pokemon car mesPokemons est trie par ordre decroissant
	 * @see ajoutePokemon
	 */
	@Override
	public int getNiveau() {
		if(mesPokemons.isEmpty())
			return 0;
		else
			return mesPokemons.get(0).getNiveau();
			
	}

	/**
	 * Renvoi le type du premier pokemon car mesPokemons est trie par ordre decroissant
	 * @see ajoutePokemon
	 */
	@Override
	public String getStrongestPokemonType() {
		if(!vivant)
			return "Mort";
		return mesPokemons.get(0).getType();
	}

	@Override
	public boolean getVivant() {
		// TODO Auto-generated method stub
		return vivant;
	}

	/**
	 * Renvoi les trois premiers pokemon de mesPokemons, car mesPokemons est trie par ordre decroissant
	 * @see ajoutePokemon
	 */
	@Override
	public List<Pokemon> montreTroisPokemon() {
		List<Pokemon> mesTroisPokemon = new ArrayList<Pokemon>();
		if(mesPokemons.isEmpty())
			return null;
		else if(mesPokemons.size() <= 2)
			return mesPokemons;
		for(int i=0;i<3;i++){
			mesTroisPokemon.add(mesPokemons.get(i));
		}
		return mesTroisPokemon;
	}

	@Override
	public void prepareBattle(String type) {
		// TODO Auto-generated method stub
	}

	@Override
	public void creerBonbon() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void utiliseBonbon() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String mort() {
		vivant= false;
		return getID() + " est mort !";
	}

}
