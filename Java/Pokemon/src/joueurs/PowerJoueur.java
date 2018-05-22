package joueurs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import game.Pokemon;
import game.TestPokemonJava;
import game.Bonbon;
import game.GameWorld.CelluleData;
import game.GameWorld.Creature;

public class PowerJoueur extends Joueur{
	
	private List<Bonbon> bonbon = new ArrayList<Bonbon>();
	
	/**
	 * Constructeur de powerJoueur
	 * Le powerJoueur améliore juste le premier pokemon qu'il a
	 * @param ID
	 */
	public PowerJoueur(int ID) {
		mesPokemons = new ArrayList<Pokemon>();
		vivant=true;
		id=ID;
	}

	@Override
	public void ajoutePokemon(Pokemon p) {
		mesPokemons.add(0,p);
	}

	@Override
	public Pokemon choisirPokemon() {
		if(mesPokemons.isEmpty())
			return null;
		else 
			return mesPokemons.get(0);
	}

	@Override
	public Joueur copy(int id) {
		return new PowerJoueur(id);
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
	 * PowerJoueur tente de monter son unique pokémon
	 * Va en priorité sur les cases ou un pokemon du meme type que le sien est present (de son niveau ou inferieur)
	 * Sinon va sur une case ou il y a un pokemon (de son niveau ou inferieur)
	 * Sinon ce deplace aleatoirement
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
		
		//Des qu'il a capture un pokemon il le transforme en bonbon
		if(mesPokemons.size() > 1){
			this.creerBonbon();
			this.utiliseBonbon();
		}
		
		/*
		 * Si le pokemon present dans la cellule est pas le meme type que le sien, il combat
		 * sinon capture
		 */
		Creature pokemon = PokemonCellule(x,y);		
		if(pokemon != null && pokemon.niveau <= niveau && pokemon.type != mesPokemons.get(0).getType()){
			String poke = pokemon.nom.substring(1);
			System.out.println(id + " combat : " + poke);
			return new JoueurAction("fight",poke);
		}
		else if(pokemon != null && pokemon.niveau <= niveau){
			String poke = pokemon.nom.substring(1);
			System.out.println(id + " capture : " + poke);
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
		 * Si il est du même type on se déplace vers cette case
		 */
		for(String key : mvt.keySet()){
			pokemon = PokemonCellule(mvt.get(key).get(0),(mvt.get(key).get(1)));
			if(pokemon != null && pokemon.niveau <= getNiveau() 	
					&& (pokemon.type == mesPokemons.get(0).getType()))
					return new JoueurAction("move",key);
		}
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
	 * Renvoie le niveau de son unique pokemon
	 */
	@Override
	public int getNiveau() {
		if(mesPokemons.isEmpty())
			return 0;
		else
			return mesPokemons.get(0).getNiveau();
	}
	
	/**
	 * Renvoie le type de son unique pokemon
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
	 * Le PowerJoueur n'a qu'un seul pokemon
	 * on peut donc renvoyer directement mesPokemons
	 */
	@Override
	public List<Pokemon> montreTroisPokemon() {
		return mesPokemons;
	}

	@Override
	public void prepareBattle(String type) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * creer un Bonbon
	 * Le pokemon a transforme est toujours en 1er position de la liste mesPokemons
	 * Donne directement le bonbon a son pokemon
	 * @see ajoutePokemon
	 */
	@Override
	public void creerBonbon() {
		List<Pokemon> pokebonbon = new ArrayList<Pokemon>();
		pokebonbon.add(mesPokemons.get(0));
		mesPokemons.remove(0);
		bonbon.add(Bonbon.creerBonbon(pokebonbon));
		
	}

	@Override
	public void utiliseBonbon() {
		System.out.println(Bonbon.consumeBonbon(mesPokemons, bonbon));
	}

	/**
	 * Tue le joueur
	 * @return ID + "est mort !"
	 */
	@Override
	public String mort() {
		vivant= false;
		return getID() + " est mort !";
	}

}
