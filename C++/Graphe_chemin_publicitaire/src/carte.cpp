/*  INF3105 - Structures de données et algorithmes
 *  UQAM / Département d'informatique
 *  Hiver 2018 / TP3
 *
 *  Vos noms + codes permanents :
 *    Philippe Lavoie, LAVP24059100
 *    Hippolyte Damand DAMH15119208
 */

#include "carte.h"
#include <iostream>
#include <stdio.h>
#include <cstdio>
#include <limits>
#include <math.h>
#include <queue>
#include <sstream>


istream& operator >> (istream& is, Carte& carte)
{
    	string nom;
	while(nom != "---"){
		Noeud tempo;
		is >> nom;
		if(nom != "---"){
			tempo.nom = nom;
			is >> tempo.coordo;
			carte.LesNoeuds[tempo.nom] = tempo;
		}
	}
	while(is){
		Arete tempo;
		string rue;
		is >> rue;
		tempo.rue = rue;	
		string input;
		bool premier = true;
		string deuxpoints;
		is >> deuxpoints;
		while(input != ";" && tempo.rue != ""){
			is >> input;
			if(input != ";"){
				if(premier){
					tempo.p1 = input;
					premier = false;
				}else{
					tempo.p2 = input;
					if(!std::isdigit(tempo.p2[0])){
						Coordonnee c1 = carte.LesNoeuds[tempo.p1].coordo;
						Coordonnee c2 = carte.LesNoeuds[tempo.p2].coordo;
						/**for(std::list<Noeud>::iterator it = carte.LesNoeuds.begin(); it != carte.LesNoeuds.end(); ++it){
							if((*it).nom == tempo.p1){
								c1 = (*it).coordo;
							}
							if((*it).nom == tempo.p2){
								c2 = (*it).coordo;
							}
						}**/
						tempo.distance = c1.distance(c2);
						carte.LesAretes.push_front(tempo);
						tempo.p1 = tempo.p2;
					}else{
						for (std::list<Arete>::iterator it = carte.LesAretes.begin(); it != carte.LesAretes.end(); ++it)
    							if((*it).rue == rue){
								(*it).poid = stoi(input);
							}
					}
				}
			}			
		}
	} 
	/*for(std::map<string,Noeud>::iterator it = carte.LesNoeuds.begin(); it != carte.LesNoeuds.end(); it++)
		std::cout << carte.LesNoeuds[it->first].nom << "\n";
	for (std::list<Arete>::iterator it = carte.LesAretes.begin(); it != carte.LesAretes.end(); ++it)
               std::cout << (*it).rue << " : " << (*it).p1 << "-" << (*it).p2 << " " << (*it).poid << "distance: " << (*it).distance << endl; */
                                                        
    	return is;
}

