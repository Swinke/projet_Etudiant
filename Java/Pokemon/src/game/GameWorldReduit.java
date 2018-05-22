package game;

import java.util.ArrayList;
import java.util.List;

import game.GameWorld.Cellule;
import joueurs.Joueur;
import joueurs.JoueurAction;

public class GameWorldReduit extends GameWorld{

	GameWorldReduit(int dimx, int dimy, List<Joueur> jList, int jNum, boolean automatic, int tours) {
		super(dimx, dimy, jList, jNum, automatic, tours);
	}
	
	@Override
	void nextTurn(){
		System.out.println();
		System.out.println("--- Tours : " +totalTours+" ---");
		
		int vivant= 0;
		int jVivant=0;
		
		for( int i =1; i<=totalJoueurs;i++){
			if(tousJoueurs.get(i).getVivant()){
				vivant++;
				jVivant=tousJoueurs.get(i).getID();
			}
			if(vivant>1)
				break;
		}
		
		if(vivant==1){
			System.out.println("Le vainquer est J"+jVivant+" du niveau "+tousJoueurs.get(jVivant).getNiveau());
			System.exit(0);
		}else if(vivant==0){
			System.out.println("Vous êtes tous dummies...");
			System.exit(0);
		}
		
		for( int i =1; i<=totalJoueurs;i++){
			if(!tousJoueurs.get(i).getVivant())
				continue;
			Joueur j = tousJoueurs.get(i);
			JoueurAction a = tousJoueurs.get(i).nextAction();
			
			if(a==null){
				System.out.println(j.mort()+" Raison: rien d'action choisi");
			}
			
			String actDef = a.getActionDefinition();
			switch(a.getActionType()){
			case move:
				moveAction(j,actDef);
				break;
			case fight:
				fightAction(tousJoueurs.get(i),myGameWorld[j.getPositionX()][j.getPositionY()].getPokemon(actDef),myGameWorld[j.getPositionX()][j.getPositionY()].terrain);
				break;
			case capture:
				captureAction(tousJoueurs.get(i),myGameWorld[j.getPositionX()][j.getPositionY()].getPokemon(actDef));
				break;
			case mort:
				System.out.println(j.mort()+" Raison: Action mort choisir ?!");
				break;
			}
		}
		//Reduction du gameWorld tout les 10 tours
		if(totalTours > 0 && totalTours%10 == 0){
			int xmin=0;
			int ymin=0;
			List<Joueur> temp = new ArrayList<Joueur>();
			/*
			 * Limite de dimension de map a raccourcir dimensionX-1
			 */
			
			while(myGameWorld[xmin][ymin].active == false){
				if(xmin<(int)(dimensionX-1)/2)
					xmin++;
				if(ymin<(int)(dimensionY-1)/2)
					ymin++;
			}
			//Elimination des 4 bords, et replacement des joueurs si joueur dans une case à enlever
			/*
			 * elimination des colonnes
			 */
			if(xmin < (int)(dimensionX-1)/2){
				for(int y=ymin;y<dimensionY-ymin;y++){
					if(myGameWorld[xmin][y].joueurs != null){
						temp.addAll(myGameWorld[xmin][y].joueurs);
						for(Joueur j:temp){
							myGameWorld[xmin+1][y].addJoueur(j);
							myGameWorld[xmin][y].removeJoueur(j.getID());
							j.updatePosition(xmin+1, y);
						}
					}
					temp.removeAll(temp);
					myGameWorld[xmin][y].active = false;
					if(myGameWorld[dimensionX-xmin-1][y].joueurs != null){
						temp.addAll(myGameWorld[dimensionX-xmin-1][y].joueurs);
						for(Joueur j:temp){
							myGameWorld[dimensionX-xmin-2][y].addJoueur(j);
							myGameWorld[dimensionX-xmin-1][y].removeJoueur(j.getID());
							j.updatePosition(dimensionX-xmin-2, y);
						}
					}
					temp.removeAll(temp);
					myGameWorld[dimensionX-xmin-1][y].active = false;
				}
			}
			/*
			 * Elimination des lignes
			 */
			if(ymin < (int)(dimensionY-1)/2){
				for(int x=0;x<dimensionX-xmin;x++){
					if(myGameWorld[x][ymin].joueurs != null){
						temp.addAll(myGameWorld[x][ymin].joueurs);
						for(Joueur j: temp){
							myGameWorld[x][ymin+1].addJoueur(j);
							myGameWorld[x][ymin].removeJoueur(j.getID());
							j.updatePosition(x,ymin+1);
						}
					}
					temp.removeAll(temp);
					myGameWorld[x][ymin].active = false;
					if(myGameWorld[x][dimensionY-ymin-1].joueurs != null){
						temp.addAll(myGameWorld[x][dimensionY-ymin-1].joueurs);
						for(Joueur j: temp){
							myGameWorld[x][dimensionY-ymin-2].addJoueur(j);
							myGameWorld[x][dimensionY-ymin-1].removeJoueur(j.getID());
							j.updatePosition(x, dimensionY-ymin-2);
						}
					}
					temp.removeAll(temp);
					myGameWorld[x][dimensionY-ymin-1].active = false;
				}
			}
			/*
			 * Si une des deux limites est atteintes
			 */
			if(ymin == (int)(dimensionY-1)/2 && xmin == (int)(dimensionX-1)/2){
				if(dimensionY > dimensionX){
					for(int x=0;x<dimensionX-xmin;x++){
						if(myGameWorld[x][ymin].joueurs != null){
							temp.addAll(myGameWorld[x][ymin].joueurs);
							for(Joueur j: temp){
								myGameWorld[x][ymin+1].addJoueur(j);
								myGameWorld[x][ymin].removeJoueur(j.getID());
								j.updatePosition(x,ymin+1);
							}
						}
					}
				}
				else{
					for(int y=ymin;y<dimensionY-ymin;y++){
						if(myGameWorld[xmin][y].joueurs != null){
							temp.addAll(myGameWorld[xmin][y].joueurs);
							for(Joueur j:temp){
								myGameWorld[xmin+1][y].addJoueur(j);
								myGameWorld[xmin][y].removeJoueur(j.getID());
								j.updatePosition(xmin+1, y);
							}
						}
					}
				}
			}
		}
		
		for(Cellule[] cList : myGameWorld){
			for(Cellule c : cList){
				c.finTour();
			}
		}
		
		Joueur meilleurJ = tousJoueurs.get(1);
		for(int id : tousJoueurs.keySet()){
			if(tousJoueurs.get(id).getNiveau() > meilleurJ.getNiveau())
				meilleurJ = tousJoueurs.get(id);
		}
		System.out.println("Niveau meilleur pokemon possede par un ou plusieurs joueurs : " + meilleurJ.getNiveau());
		
		gInterface.updateGameWorld(getGameWorldData());
		totalTours++;
	}
	
}
