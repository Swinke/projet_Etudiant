package game;
import java.util.List;

/**********************************************************************************
 * Classe Bonbon : le vendeur des bonbons
 * <ul>
 * <li>Joueurs peuvent échanger des pokemons pour les bonbons utilisant les methodes publics
 * proposé dans cette classe. </li>
 * <li>Chaque bonbon consumé par un pokemon du bon type donnes 
 * un level-up au pokemon.</li>
 * </ul>
 * 
 * Pour les règles d'échange, reporter aux fonctions creerBonbon et consumeBonbon.
 * @see #creerBonbon(List) 
 * @see #consumeBonbon(List, List)
 * @author huiyin
 *
 *********************************************************************************/
public class Bonbon {
	
	private String type; 				// type du bonbon
	static final double valeur = 1.0; 	//valeur pour points xp
	
	/**
	 * Constructeur privée de bonbon. Les autres classes peuvent pas utiliser 
	 * ce constructeur, et il faut utiliser les fonctions fournies pour créer et
	 * consumer les bonbons
	 * @param type type du bonbon: feu, eau, ou herbe
	 */
	private Bonbon(String type){
		this.type = type;
	}
	
	/**
	 * <p>Prérequis : 
	 * <ul>
	 * <li>pList n'est pas null</li>
	 * <li>bonbons n'est pas null</li>
	 * <li>il y a un bonbon dans la liste bonbons avec le meme type du premier pokemon dans pList</li>
	 * </ul>
	 * 
	 * 
	 * <p>Spécification :
	 * <ul>
	 * <li>consume un bonbon, le supprimer de la liste</li>
	 * <li>donne un level-up à premier pokemon dans la liste</li>
	 * </ul>
	 * 
	 * @param pList liste des pokemons du joueur
	 * @param bonbons liste des bonbons du joueur
	 * @return un String à propos du résultat
	 */
	public static String consumeBonbon(List<Pokemon> pList,List<Bonbon> bonbons){
		
		boolean verifyBonbon=false;
		String type = "";
		
		if(pList.isEmpty())
			return "Vous n'avez pas de pokemon dans la liste.";
		
		for(int i =0; i<bonbons.size();i++){
			if(bonbons.get(i).type.equals(pList.get(0).getType())){
				verifyBonbon=true;
				type = bonbons.get(i).type;
				bonbons.remove(i);
				break;
			}
		}
		if(verifyBonbon){
			pList.get(0).levelUp();;
			return "Bonbon du type "+type+" donné à "+pList.get(0).getNom()+" avec succès!";
		}
		else return "Mauvais pokemon ou rien bonbon du type "+ pList.get(0).getType();
	}
	
	/**
	 * <p>Prérequis : pList n'est pas null
	 * <p>Spécification :Transformer le premier pokemon dans la liste en un bonbon du meme type
	 * @param pList une liste des pokemons
	 * @return un bonbon du meme type du premier pokemon dans pList (ou null, si pList est vide)
	 */
	public static Bonbon creerBonbon(List<Pokemon> pList){
		if(!pList.isEmpty()){
			
			Bonbon b = new Bonbon(pList.get(0).getType());
			
			// pour m'assurer que vous n'avez pas garder une copie du pokemon
			pList.get(0).addPoints(-1000.0);
			
			// merci pour votre pokemon
			pList.remove(0);
			
			
			// voila le bonbon
			return b;
		}
		else return null;
	}
	
	
}
