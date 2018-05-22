package game;
import java.util.*;

import joueurs.DummyJoueur;
import joueurs.GreedyJoueur;
import joueurs.Joueur;

/**********************************************************************************
 * Class Tournoi TODO
 **********************************************************************************/
class Tournoi {
	
	/**
	 * A la fin de chaque tour, pour chaque cellule, tous les joueurs vivants présents à l'intérieur d'une même cellule participent à un "tournoi" qui determine
	 * le vainqueur qui restera dans la cellule. Les autres joueurs sont éliminés.
	 * <ul>
	 * <li>Phase 1 Seeding
	 * <ol>
	 * <li>Le classement d'un joueur est calculé grâce à la somme des niveaux des trois meilleurs pokemons de chaque joueur</li>
	 * <li>La liste des joueurs est triée en utilisant les classements du joueur</li>
	 * <li>Si le nombre de joueurs n'est pas une puissance de 2, il faut ajouter quelques DummyJoueurs sans pokemon à la fin de la liste jusqu'à obtenir un nombre de joueurs égal à une puissance de 2</li>
	 * <li>la sequence de combats est decidée par les règles de seeding. Il faut mettre les joueurs dans
	 * une queue (java.util.Queue) pour définir la séquence de combats (Pour la définition du seeding, reporter à https://en.wikipedia.org/wiki/Seed_(sports))</li>
	 * </ol></li>
	 * <li>Phase 2 Bataille
	 * <ol>
	 * <li>les joueurs se battent par paires sous les regles de "seeding"
	 * <ul>
	 * <li>le joueur avec le plus de points (première phase) combat celui qui en a le moins</li>
	 * <li>le deuxieme joueur avec le plus de points combat le deuxieme qui en a le moins</li>
	 * <li>... et etc.</li>
	 * <li>le vainqueur continue le sous-tournoi, l'autre est éliminé</li>
	 * </ul></li>
	 * <li>Utiliser fight(Joueur j1, Joueur j2) pour determiner le vainqueur</li>
	 * <li>Le tournoi se termine quand il ne reste plus qu'un joueur</li>
	 * </ol></li>
	 * </ul>
	 * 
	 * @return retourne l'ID du meilleur joueur
	 * @param joueurs liste des joueurs dans la cellule (ne se limite pas aux vivants)
	 * @param terrain type du terrain
	 */
	static int faireTournoi(List<Joueur> joueurs, String terrain){
		//Si pas de joueur ou un seul joueur, pas de tournoi
		if(joueurs.isEmpty() || joueurs.size() == 1)
			return -1;
		
		/*
		 * Si plusieurs joueur dans la case on enleve les joueurs mort de la liste
		 * Stocke les joueurs mort dans une liste temporaire
		 * Enleve les joueur de la liste temporaire (temp) de la liste de joueur
		 */
		List<Joueur> temp = new ArrayList<Joueur>();
		for(Joueur j : joueurs){
			temp.add(j);
		}
		for(Joueur j : temp){
			if(!j.getVivant())
				joueurs.remove(j);
		}
		temp.removeAll(temp);
		
		/*
		 * On verifie si le nombre de joueurs pour le tournoi est une puissance de 2
		 * Si ce n'est pas le cas on ajoute le nombre de dummyjoueur qu'ils faut
		 */
		double puiss = Math.log(joueurs.size())/Math.log(2);
		if(!((int)puiss == puiss)){
			DummyJoueur d1 = new DummyJoueur(1000);
			double lim = Math.pow(2,(int)(puiss+1))-joueurs.size();
			for(int i=0;i<lim;i++)
				joueurs.add(d1.copy(new Random().nextInt(100000)));
		}
		
		/*
		 * On classe les joueurs en fonction des regles du classement
		 * Les joueurs sont classe dans l'ordre decroissant de niveau
		 */
		boolean changement = true;
		while(changement){
			changement = false;
			for(int i=0;i+1<joueurs.size();i++){
				if(RendreNiveau(joueurs.get(i+1).montreTroisPokemon()) > RendreNiveau(joueurs.get(i).montreTroisPokemon())){
					Joueur jo = joueurs.get(i+1);
					joueurs.remove(jo);
					joueurs.add(i,jo);
					changement=true;
				}
			}
		}
		
		/*
		 * On place les joueur dans une queue en fonction des regles du seed
		 * Si les nombre de joueurs est 2 pas besoin de trie la queue
		 */
		Queue<Joueur> queuetournoi = new LinkedList<Joueur>();
		if(joueurs.size()>2){
			List<Joueur> debutjoueur = new ArrayList<Joueur>();
			for(int k=0;k<2;k++)
				debutjoueur.add(joueurs.get(k));
			temp = OrdreTournoi(debutjoueur,joueurs,2*debutjoueur.size());
			joueurs.removeAll(joueurs);
			joueurs.addAll(0, temp);
		}
		System.out.println("-Debut tournoi-");
		System.out.print("Ordre et joueur present dans le tournoi :");
		for(Joueur jo : joueurs){
			System.out.print(" j" + jo.getID());
			queuetournoi.add(jo);
		}
		System.out.println();
		/*
		 * Tant que le nombre qu'il n'y pas de vainqueur
		 */
		while(queuetournoi.size()>1){
			Joueur j1 = queuetournoi.poll();
			Joueur j2 = queuetournoi.poll();
			//En fonction du vainqueur on le rajoute dans la queue et on tue le perdant
			if(fight(j1,j2,terrain) == 1){
				queuetournoi.add(j2);
				System.out.println(j1.mort());
			}
			else{
				queuetournoi.add(j1);
				System.out.println(j2.mort());
			}
			//Affichage des joueurs restant dans le tournoi
			System.out.print("Joueur restant dans le tournoi :");
			for(Joueur jo : queuetournoi)
				System.out.print(" j"+jo.getID());
			System.out.println();
		}
		return queuetournoi.peek().getID();	
	}
	
	/**
	 * Les joueurs sont trie selon les regles du seed
	 * Dans un premier temps prend les deux premiers joueurs de la liste et les 2 suivant en fonction du seed
	 * Les joueur sont places selon leur position dans la liste original
	 * La somme des indices du joueur deja present et celui que l'on ajoute doit toujours etre egal
	 * a 2 puissance la taille de notre debut de liste trie moins 1
	 * Puis on revoie ce debut de liste trie en fonction du seed et la taille de la nouvelle liste 2*la taille de j
	 * Ainsi de suite jusqu'a ce que la taille de la liste trie selon le seed soit egal a celle des Joueurs
	 * @param j le debut de la liste pas trie selon le seed
	 * @param joueurs la liste doit etre trie
	 * @param n taille de nouvelle liste trie selon le seed
	 * @return liste dont les joueurs sont tries selon le seed
	 */
	public static List<Joueur> OrdreTournoi(List<Joueur> j, List<Joueur> joueurs, int n){
		List<Joueur> ordretour = new ArrayList<Joueur>();
		ordretour.addAll(j);
		if(n > joueurs.size())
			return ordretour;
		for(int i=0;i<j.size()-1;i++){
			int compt = 0;
			//Selectionne le nouveau joueur en fonction de la position celui d'avant
			while(j.get(i) != ordretour.get(compt))
				compt++;
			ordretour.add(compt+1,joueurs.get(n-1-joueurs.indexOf(j.get(i))));
		}
		/*
		 * On s'arrete avant la fin de la liste car le deuxieme meilleur joueur de la liste original doit
		 * toujours ce retrouver en fin de liste 
		 */
		ordretour.add(ordretour.size()-1,joueurs.get(n-2));
		return OrdreTournoi(ordretour,joueurs,2*n);
	}
	
	/**
	 * RendreNiveau permet de calcule le classement du joueur
	 * @param liste les trois pokemons doit etre les trois meilleur de la liste
	 * @return Rend la somme des niveaux des pokemons de la liste
	 */
	public static int RendreNiveau(List<Pokemon> liste){
		int niveau = 0;
		for(Pokemon p : liste){
			niveau += p.niveau;
		}
		return niveau;
	}
	
	/**TODO
	 * <p>Bataille entre deux pokémon : le pokemon p1 et le Pokemon p2
	 * Si un pokemon est null, l'adversaire est le vainqueur. 
	 * Si les deux sont null, p1 est le vainqueur.
	 * <ul>
	 * <li>PHASE 1 : calcul de l'attaque de chaque pokémon
	 * <ul>
	 * <li>l'attaque du pokemon est determinée par la formule suivante :
	 *   niveau du pokemon + random bonus d'un double entre 0 et 2 + bonus type + bonus terrain</li>
	 * <li>calcule du bonus type: 10% bonus sur niveau du pokemon de l'adversaire + 1.0 d'attack pour feu sur herbe, herbe sur eau, et eau sur feu</li>
	 * <li>calcule du bonus terrain: les pokemons du meme type du terrain gagnent +1.0 d'avantage</li>
	 * </ul></li>
	 * <li>PHASE 2 : Bataille
	 * <ul>
	 * <li>chaque bataille se compose de 3 tours entre les deux pokemon, et le vainqueur d'un tour est le pokemon avec la meilleure valeur d'attaque</li>
	 * <li>l'attaque est recaculée pour chaque tour</li>
	 * <li>le pokemon qui gagne le plus de tours est le vainqueur</li>
	 * </ul></li>
	 * <li>PHASE 3 : Calcul des points d'experience
	 * <ul>
	 * <li>le pokemon vainqueur gagne 1.0 point XP + 10% bonus du niveau de l'adversaire</li>
	 * <li>le perdant gagne 0.1 point, plus 10% bonus du niveau de l'adversaire </li>
	 * <li>si les deux pokémon sont à égalité, chaque pokémon gagne 0.5 points XP (et pas de bonus)</li>
	 * </ul></li>
	 * </ul>
	 * @return le perdant, 1 pour p1, 2 pour p2, et -1 en cas d'égalité
	 * @param p1 pokemon du j1
	 * @param p2 pokemon sauvage ou pokemon du j2
	 * @param terrain type du terrain ("Feu", "Eau", ou "Herbe")
	 */
	static int bataillePokemon(Pokemon p1, Pokemon p2, String terrain){
		if(p1 == null)
			return 1;
		else if(p2 == null)
			return 2;
		
		double att1 = p1.niveau;
		double att2 = p2.niveau;
		//Verifie si le pokemon est du meme type que le terrain
		if(p1.type == terrain)
			att1 += 1;
		if(p2.type == terrain)
			att2 += 1;
		
		//Bonus de type pour le pokemon choisit du joueur 1
		switch(p1.type){
		case "Psy" : att1 += 0.1*p2.niveau + 1;break;
		case "Feu":
			if(p2.type == "Herbe")
				att1 += 0.1*p2.niveau + 1;break;
		case "Eau":
			if(p2.type == "Feu")
				att1 += 0.1*p2.niveau + 1;break;
		case "Herbe":
			if(p2.type == "Eau")
				att1 += 0.1*p2.niveau + 1;break;
		}
		//Bonus de type pour le pokemon choisit du joueur 1
		switch(p2.type){
		case "Psy" : att2 += 0.1*p1.niveau + 1;break;
		case "Feu":
			if(p1.type == "Herbe")
				att2 += 0.1*p1.niveau + 1;break;
		case "Eau":
			if(p1.type == "Feu")
				att2 += 0.1*p1.niveau + 1;break;
		case "Herbe":
			if(p1.type == "Eau")
				att2 += 0.1*p1.niveau + 1;break;
		}
		
		double newatt1;
		double newatt2;
		int compt1=0;
		int compt2=0;
		
		
		// Trois tour pour determiner le vainqueur, seul change le nombre aleatoire
		for(int i=0;i<3;i++){
			newatt1 =0;
			newatt2 =0;
			newatt1 = att1 +  new Random().nextDouble()*3;
			newatt2 = att2 + new Random().nextDouble()*3;
			if(newatt1>newatt2)
				compt1++;
			else if(newatt1<newatt2)
				compt2++;
		}
		
		//Perdant joueur 2, affectation des points
		if(compt1 > compt2){
			p1.addPoints(1 + 0.1*p2.niveau);
			p2.addPoints(0.1 + 0.1*p1.niveau);
			return 2;
		}
		//Perdant joueur 1, affectation des points
		else if(compt2 > compt1){
			p1.addPoints(0.1 + 0.1*p2.niveau);
			p2.addPoints(1 + 0.1*p1.niveau);
			return 1;
		}
		//Egalite
		else{
			p1.addPoints(0.5);
			p2.addPoints(0.5);
			return -1;
		}
	}
	
	/**TODO
	 * <p>prérequis : les deux joueurs ne sont pas null
	 * <ol>
	 * <li>prepare les deux joueurs en leur donnant le type du meilleur pokemon de l'adversaire en utilisant Joueur.prepareBattle(String)
	 * par exemple Joueur.prepareBattle("Eau")</li>
	 * <li>prend les pokemons choisis par chaque joueur</li>
	 * <li>lance la bataille</li>
	 * <li>si deux joueurs sont à égalité, relance la bataille jusqu'à obtenir un vainqueur </li>
	 * <li>élimine le joueur perdant</li>
	 * </ol>
	 * 
	 * @return retourne le perdant, 1 pour j1, 2 pour j2
	 * @param j1 premier joueur
	 * @param j2 deuxième joueur
	 * @param terrain type du terrain ("Feu", "Eau", ou "Herbe")
	 */
	static int fight(Joueur j1, Joueur j2, String terrain){
		j1.prepareBattle(j2.getStrongestPokemonType());
		j2.prepareBattle(j1.getStrongestPokemonType());
		int perdant = bataillePokemon(j1.choisirPokemon(),j2.choisirPokemon(),terrain);
		System.out.println("j"+j1.getID() + " combat j" + j2.getID());
		if(perdant == -1)
			return fight(j1,j2,terrain) ;
		else
			return perdant;
	}
	
	/** TODO
	 * Un joueur qui essaie de capturer un pokemon.
	 * Si le niveau du Joueur + un réel aléatoire entre 0 et 2 est superieur 
	 * ou égal au niveau de p plus un réel aléatoire entre 0 et 1, la capture est un succès
	 * Si le pokemon p est null, c'est faux
	 * Si la capture est un succès, donne une copie du pokémon au joueur (p.copy())
	 * @return si la capture est un succès
	 * @param j joueur
	 * @param p pokemon que le joueur désire capturer
	 */
	static boolean capture(Joueur j, Pokemon p){
		int i = new Random().nextInt(2);
		int k = new Random().nextInt(1);
		if(p != null && j.getNiveau()+i >= p.niveau+k){
			j.ajoutePokemon(p.copy());
			p.captured = true;
			return true;
		}
		else
			return false;
	}
}
