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

public class NotreIA extends Joueur {
	/*
	 * Liste contenant les bonbons du joueur
	 */
	private List<Bonbon> bonbon = new ArrayList<Bonbon>();
	/*
	 * indice x de la prochaine cellule a atteindre
	 */
	private int xcellsuivante = 0;
	/*
	 * indice x de la prochaine cellule a atteindre
	 */
	private int ycellsuivante = 0;
	
	/**
	 * Constructeur de NotreIA
	 * @param ID
	 */
	public NotreIA(int ID) {
		mesPokemons = new ArrayList<Pokemon>();
		vivant=true;
		id=ID;
	}

	@Override
	public void ajoutePokemon(Pokemon p) {
		mesPokemons.add(0,p);
	}
	
	/**
	 * Choisit le pokemon qui va combattre
	 * On choisit le premier pokemon de notre liste car la liste est trie pour
	 * @see prepareBattle
	 *@return le premier pokemon de la liste
	 */
	@Override
	public Pokemon choisirPokemon() {
		if(mesPokemons.isEmpty())
			return null;
		else 
			return mesPokemons.get(0);
	}

	@Override
	public Joueur copy(int id) {
		return new NotreIA(id);
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
	 * NotreIA essaye de capturer un pokemon de type different que celui de base
	 * sinon il combat le pokemon de la cellule si celui-ci est de type different que les pokemon possede
	 * ou le capture si de meme type
	 * Sinon il essaye de ce deplacer vers des pokemons
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
		 * Si le pokemon present dans la cellule est pas le meme type que le sien, il combat
		 * sinon capture (sauf si notreIA n'a qu'un pokemon)
		 */
		Creature pokemon = PokemonCellule(x,y);		
		if(pokemon != null && pokemon.niveau <= niveau && typeExiste(pokemon.type) == -1){
			String poke = pokemon.nom.substring(1);
			if(mesPokemons.size() < 2 && pokemon.niveau != 0){
				System.out.println(id + " capture : " + poke);
				return new JoueurAction("capture",poke);
			}
		}
		else if(pokemon != null && pokemon.niveau <= niveau+1){
			String poke = pokemon.nom.substring(1);
			System.out.println(id + " capture : " + poke);
			return new JoueurAction("capture",poke);
		}
		else if(pokemon != null && pokemon.niveau <= niveau+1){
			String poke = pokemon.nom.substring(1);
			System.out.println(id + " combat : " + poke);
			return new JoueurAction("fight",poke);
		}
		
		if(mesPokemons.size() > 2){
			this.creerBonbon();
			this.utiliseBonbon();
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
		 *  Si un pokemon de notre type est present dans une des cases qui l'entoure
		 *  va vers cette case
		 */
		for(String key : mvt.keySet()){
			pokemon = PokemonCellule(mvt.get(key).get(0),(mvt.get(key).get(1)));
			if(pokemon != null && pokemon.niveau<= getNiveau()+1	
					&& (typeExiste(pokemon.type) != -1))
					return new JoueurAction("move",key);
		}
		/*
		 * Si le pokemon est present dans les cases a coté du joueur
		 */
		for(String key : mvt.keySet()){
			pokemon = PokemonCellule(mvt.get(key).get(0),(mvt.get(key).get(1)));
			if(pokemon != null && pokemon.niveau<= getNiveau()+1)
					return new JoueurAction("move",key);
		}
		
		/*
		 * Si la cellule a atteindre n'as pas de pokemon, on cherche une autre cellule contenant des pokemons
		 */
		if(PokemonCellule(xcellsuivante,ycellsuivante) == null){
			int i=0;
			int j=0;

			while(i<=maxX && PokemonCellule(i,j) == null){
				while(j<=maxY && PokemonCellule(i,j) == null)
					j++;
				//Pas de pokemon dans la colonne
				if(j>maxY){
					j=0;
					i++;
				}
				//Si le pokemon a un niveau trop eleve
				if(PokemonCellule(i,j) != null && PokemonCellule(i,j).niveau > getNiveau()){
					if(j==maxY) {
						j=0;
						i++;
					}else
						j++;
				}
			}
			xcellsuivante = i;
			ycellsuivante = j;
		}
		
		if(xcellsuivante != 0 && ycellsuivante != 0){
			//Mouvement pour aller a la cellule
			if(xcellsuivante > x && mvt.containsKey("Right"))
				return new JoueurAction("move","Right");
			else if(xcellsuivante < x && mvt.containsKey("Left"))
				return new JoueurAction("move","Left");
			else if(ycellsuivante < y && mvt.containsKey("Down"))
				return new JoueurAction("move","Down");
			else if(ycellsuivante > y && mvt.containsKey("Up"))
				return new JoueurAction("move","Up");
		}
		//  Sinon ce deplace aleatoirement
		int random = new Random().nextInt(mvt.size());
		List<String> mouvement = new ArrayList<String>();
		for(String key : mvt.keySet()){
			mouvement.add(key);
		}
		return new JoueurAction("move",mouvement.get(random));
	}

	@Override
	public int getNiveau() {
		int niveau = mesPokemons.get(0).getNiveau();
		for(Pokemon p: mesPokemons){
			if(niveau<p.getNiveau())
				niveau = p.getNiveau();
		}
		return niveau;
	}

	@Override
	public String getStrongestPokemonType() {
		for(Pokemon p : mesPokemons){
			if(p.getNiveau() == getNiveau())
				return p.getType();
		}
		return null;
	}

	@Override
	public boolean getVivant() {
		return vivant;
	}

	@Override
	public List<Pokemon> montreTroisPokemon() {
		// Notre IA aura au plus trois pokemons
		return mesPokemons;
	}
	
	/**
	 * Renvoie l'index du pokemon de type recherche
	 * @param type du pokemon recherche
	 * @return l'index du pokemon de meme type que le type transmis, -1 sinon
	 */
	public int typeExiste(String type){
		for(Pokemon p :mesPokemons){
			if(p.getType() == type)
				return mesPokemons.indexOf(p);
		}
		return -1;		
	}
	
	/**
	 * Le premier pokemon de la liste est celui utilise pour le combat
	 * On regarde si on a le pokemon pour le bonusType
	 * Sinon on envoie notre meilleur pokemon
	 */
	@Override
	public void prepareBattle(String type) {
		String type2 ="";
		switch(type){
			case "Feu" : type2 = "Eau";break;
			case "Eau" : type2 = "Herbe";break;
			default : type2 = "Feu";
		}
		/*
		 *  Si on possede le pokemon pour avoir le bonusType
		 */
		if(typeExiste(type2) != -1 && mesPokemons.get(typeExiste(type2)).getNiveau() +1>= getNiveau()){
			Pokemon p = mesPokemons.get(typeExiste(type2));
			mesPokemons.remove(p);
			mesPokemons.add(0,p);
		}
			
	}

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

	@Override
	public String mort() {
		vivant= false;
		return getID() + " est mort !";
	}

}
