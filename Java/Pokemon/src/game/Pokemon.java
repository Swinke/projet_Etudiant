package game;
import java.util.*;

/**********************************************************************************
 * <p>Classe Pokemon : pour créer et manipuler les pokemons
 * <p>NOTE: Vous ne pouvez pas modifier cette classe.
 * La modification des attributs privée ou l'utilisation des
 * methodes non-public dans cette classe est interdit aux tous joueurs sous
 * pénalité d'élimination.
 **********************************************************************************/
public class Pokemon {
	
	/*
	 * Ne modifiez pas et n'appellez pas les attribus et les fonctions suivants
	 */
	
	/**
	 * le tableau des noms des pokemons
	 */
	public static final String[] pNoms = {
			"Magmar","Caninos","Salameche",
			"Carapuce","Magicarpe","Psykokwak",
			"Bulbizarre","Mystherbe","Chetiflor"};
	/**
	 * le tableau des types des pokemons
	 */
	public static final String[] pType = {"Feu","Eau","Herbe"};
	
	/**
	 * le nom du pokemon
	 */
	String nom;
	
	/**
	 * le type du pokemon
	 */
	String type;
	
	/**
	 * le niveau du pokemon
	 * @see #addPoints(double)
	 */
	int niveau;
	
	/**
	 * les points experiences du pokemon.
	 * @see #addPoints(double)
	 */
	double pointsXP;
	
	/**
	 * si c'est un pokemon sauvage
	 * @see #capture()
	 * @see #isCaptured()
	 */
	boolean captured;
	
	/**
	 * constructeur protégée du pokemon
	 * @param nom nom du pokemon
	 * @param type type du pokemon
	 * @param niveau niveau du pokemon
	 */
	Pokemon(String nom, String type, int niveau){
		this.nom = nom;
		this.type = type;
		this.niveau = niveau;
		this.pointsXP = 0.0;
		captured=false;
	}
	
	/**
	 * Ajoute points XP au pokemon
	 * @param xPoints nombre des points à ajouter
	 */
	void addPoints(double xPoints){
		if(xPoints<0){
			niveau=-100;
		}
		this.pointsXP+=xPoints;
		if(this.pointsXP>=niveau){
			niveau+=1;
			this.pointsXP-=niveau;
		}
	}
	
	/**
	 * Balise pour supprimer un pokemon sauvage si c'est capturer par au moins un joueur
	 */
	void capture(){
		captured = true;
	}
	
	/**
	 * Rend une copie de ce pokemon
	 * @return une nouvelle copie du pokemon
	 */
	Pokemon copy(){
		Pokemon p = new Pokemon(nom,type,niveau);
		p.pointsXP = pointsXP;
		return p;
	}
	
	/**
	 * Level-up le pokémon
	 */
	void levelUp(){
		this.niveau+=1;
		this.pointsXP=0;
	}
	
	/**
	 * Creer un pokémon random avec niveau entre 1 et 5
	 * @return un pokemon random avec niveau entre 1 et 5
	 */
	static Pokemon makePokemon(){
		
		Random randomGen = new Random();
		int r = randomGen.nextInt(9);
		
		return new Pokemon(pNoms[r],pType[r/3],randomGen.nextInt(5));
	}
	
	/**
	 * Creer un pokémon du type donné avec niveau entre 1 et 5
	 * @return un pokemon du type donné avec niveau entre 1 et 5
	 * @param type type du pokemon désiré
	 */
	static Pokemon makePokemon(String type){
		/*
		 * Résultat: un pokémon du type spécifié
		 * - Le nom et type du pokemon est random
		 * - le niveau du pokémon est entre 1 et 5
		 * 
		 */
		
		int typeNum;
		
		switch(type){
		case "Feu": typeNum=0;break;
		case "Eau": typeNum=1;break;
		case "Herbe":typeNum=2;break;
		case "Psy" :typeNum=3;break;
		default:typeNum=new Random().nextInt(3);break;
		}
		
		Random randomGen = new Random();
		int r = randomGen.nextInt(3);
		if(typeNum==3)
			return new Pokemon("Mewtwo","Psy",5+randomGen.nextInt(5));
		return new Pokemon(pNoms[r+typeNum*3],pType[typeNum],randomGen.nextInt(5));
	}
	
	/**
	 * Creer un pokémon random du niveau 1. Chaque joueur recois un niveau 1 pokemon au commence du jeu
	 * @return un pokemon random de niveau 1
	 */
	static Pokemon makeNiveau1Pokemon(){
		
		Random randomGen = new Random();
		int r = randomGen.nextInt(9);
		
		return new Pokemon(pNoms[r],pType[r/3],1);
	}
	
	/**
	 * Pour mise a jour du gameworld. Vous n'etes pas obliger à verifier si
	 * le pokemon été déjà capturer avant le capturer
	 * @return si le pokemon est sauvage
	 */
	boolean isCaptured(){
		return captured;
	}
	
	/* *********************************************
	 * Vous pouvez appeller les fonctions suivants *
	 * pour les données du pokemon				   *
	 * *********************************************/
	
	/**
	 * Rend le niveau du pokemon
	 * @return le niveau du pokemon
	 */
	public int getNiveau(){
		return niveau;
	}
	
	/**
	 * Rend le nom du pokemon
	 * @return le nom du pokemon
	 */
	public String getNom(){
		return nom;
	}
	
	/**
	 * Rend le type du pokemon
	 * @return type du pokemon ("Feu", "Eau", ou "Herbe")
	 */
	public String getType(){
		return type;
	}
}
