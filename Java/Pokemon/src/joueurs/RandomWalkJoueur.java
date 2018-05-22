package joueurs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import game.Pokemon;
import game.TestPokemonJava;
import game.GameWorld.CelluleData;
import game.GameWorld.Creature;

public class RandomWalkJoueur extends Joueur {
	
	/**
	 * Constructeur du RandomWalkJoueur
	 * @param ID
	 */
	public RandomWalkJoueur(int ID) {
		mesPokemons = new ArrayList<Pokemon>();
		vivant=true;
		id=ID;
	}

	/**
	 * Ajoute un pokemon, peut importe la position
	 * @param p
	 */
	@Override
	public void ajoutePokemon(Pokemon p) {
		mesPokemons.add(p);
	}
	
	/**
	 * Renvoie le premier pokemon
	 * @return mesPokemons.get(0)
	 */
	@Override
	public Pokemon choisirPokemon() {
		if(mesPokemons.isEmpty())
			return null;
		else return mesPokemons.get(0);
	}

	@Override
	public Joueur copy(int id) {
		return new RandomWalkJoueur(id);
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
	 * Le rambomWalkJoueur choisit aléatoirement une action parmis celle disponible
	 */
	@Override
	public JoueurAction nextAction() {
		//Initialisation des limites de terrain
		List<List<CelluleData>> allCellule = TestPokemonJava.getGameWorldData();
		int maxX = allCellule.size()-1;
		int maxY = allCellule.get(0).size()-1;
		int x = getPositionX();
		int y = getPositionY();
		
		//Initialisation des mouvements possible
		HashMap<String,List<String>> mvt = new HashMap<String,List<String>>();
		mvt.put("capture",new ArrayList<String>());
		mvt.put("fight",new ArrayList<String>());
		mvt.put("move",new ArrayList<String>());
		
		// Initialisation des directions possibles pour "move"
		List<String> dir = new ArrayList<String>();
		dir.add("Right");
		dir.add("Left");
		dir.add("Up");
		dir.add("Down");
		
		// On retire de la liste les directions interdites
		if(x == 0 || !(allCellule.get(x-1).get(y).active))
			dir.remove("Left");
		if(x == maxX || !(allCellule.get(x+1).get(y).active))
			dir.remove("Right");
		if(y == 0 || !(allCellule.get(x).get(y-1).active))
			dir.remove("Down");
		if(y == maxY || !(allCellule.get(x).get(y+1).active))
			dir.remove("Up");
		
		// En fonction de la presence d'un pokemon dans la cellule du jouer on retire ou ajoute les actions possibles
		mvt.get("move").addAll(dir);
		Creature pokemon = PokemonCellule(x,y);		
		if(pokemon != null){
			mvt.get("fight").add(pokemon.nom.substring(1));
			mvt.get("capture").add(pokemon.nom.substring(1));
		}
		else{
			mvt.remove("capture");
			mvt.remove("fight");
		}
		int random = new Random().nextInt(mvt.size());
		List<String> mouvement = new ArrayList<String>();
		for(String key : mvt.keySet()){
			mouvement.add(key);
		}
		
		// Choisit aleatoirement un des mouvements possibles
		int taille = mvt.get(mouvement.get(random)).size();
		return new JoueurAction(mouvement.get(random),
				mvt.get(mouvement.get(random)).get(new Random().nextInt(taille)));
	}

	/**
	 * Renvoi le meilleur du joueur
	 * @return niveau du meilleur pokemon
	 */
	@Override
	public int getNiveau() {
		if(mesPokemons.isEmpty())
			return 0;
		else{
			Pokemon bestPok = mesPokemons.get(0);
			for(Pokemon p : mesPokemons){
				if(bestPok.getNiveau() < p.getNiveau())
					bestPok = p;
			}
			return bestPok.getNiveau();
		}
	}

	/**
	 * Renvoie le type du meilleur pokemon du joueur
	 * @return type du meilleur pokemon
	 */
	@Override
	public String getStrongestPokemonType() {
		int highLevel = this.getNiveau();
		Pokemon p = mesPokemons.get(0);
		int i=1;
		if(!vivant)
			return "Mort";
		
		while(p.getNiveau() != highLevel){
			p = mesPokemons.get(i);
			i++;
		}
		return p.getType();
	}

	@Override
	public boolean getVivant() {
		return vivant;
	}

	/**
	 * Renvoie les trois meilleurs pokemons du joueurs
	 * @return trois meilleurs pokemon joueur
	 */
	@Override
	public List<Pokemon> montreTroisPokemon() {
		List<Pokemon> mesTroisPokemon = new ArrayList<Pokemon>();
		if(mesPokemons.isEmpty())
			return null;
		else if(mesPokemons.size() <= 2)
			return mesPokemons;
		for(int i=0;i<3;i++){
			int j=0;
			while(mesPokemons.get(j).getNiveau() != getNiveau())
				j++;
			mesTroisPokemon.add(mesPokemons.get(j));
			mesPokemons.remove(mesPokemons.get(j));
		}
		mesPokemons.addAll(mesTroisPokemon);
		return mesTroisPokemon;
	}

	/**
	 * RandomWalkJoueur n'utilise pas cette méthode
	 */
	@Override
	public void prepareBattle(String type) {
		
	}

	/**
	 * RandomWalkJoueur n'utilise pas cette méthode
	 */
	@Override
	public void creerBonbon() {
		
	}
	
	/**
	 * RandomWalkJoueur n'utilise pas cette méthode
	 */
	@Override
	public void utiliseBonbon() {
		
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
