package joueurs;
import java.util.*;

import game.Pokemon;

/**********************************************************************************
 * Class DummyJoueur : exemple pour une classe joueur
 *
 * 
 **********************************************************************************/
public class DummyJoueur extends Joueur {
	
	/**
	 * Constructeur de DummyJoueur
	 * @param ID id du joueur
	 */
	public DummyJoueur(int ID){
		mesPokemons = new ArrayList<Pokemon>();
		vivant=true;
		id=ID;
		/*
		 * Bonjour, je suis un dummy.
		 * boolean dummy = true;
		 */
	}

	@Override
	public void ajoutePokemon(Pokemon p) {
		// D'accord, j'ajoute le pokemon au fin de mesPokemons
		mesPokemons.add(p);
	}
	
	@Override
	public Pokemon choisirPokemon() {
		// Je n'ai pas de pokémon parce que je suis dummy...
		if(mesPokemons.isEmpty())
			return null;
		else return mesPokemons.get(0);
	}
	
	@Override
	public Joueur copy(int id) {
		// rend une copie du dummy joueur...mais quel est le sense d'avoir plus des dummies ?!
		return new DummyJoueur(id);
	}

	
	@Override
	public int getNiveau() {
		// Je suis toujours un dummy du niveau 0
		return 0;
	}
	
	@Override
	public String getStrongestPokemonType() {
		// Mon meilleur pokemon ? Le premier alors, si je suis vivant.
		if(!vivant)
			return "Mort";
		else if(mesPokemons.size()>0)
			return mesPokemons.get(0).getType();
		return "";
	}

	

	@Override
	public JoueurAction nextAction() {
		// Je toujours va à la gauche parce que je suis dummy...
		return new JoueurAction("move","Left");
	}

	@Override
	public boolean getVivant() {
		// Je suis un zombie...
		return vivant;
	}

	@Override
	public String mort() {
		// Non..ne me tuez pas...
		vivant=false;
		return "DummyJoueur"+id+" disqualified. Je ne veux pas mourir...";
		
	}

	@Override
	public void utiliseBonbon() {
		//Bonbon ? Je veux des bonbons s'il vous plait !
	}

	@Override
	public void prepareBattle(String type) {
		// bataille ? noooon je peux pas....
		
	}

	@Override
	public List<Pokemon> montreTroisPokemon() {
		// je suis le perdant
		return mesPokemons;
	}

	@Override
	public void creerBonbon() {
		// comment creer un bonbon ? peut-etre il faut demander la classe bonbon !
		
	}

	
	

	

	

	

}
