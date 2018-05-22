/*  INF3105 - Structures de données et algorithmes
    UQAM / Département d'informatique
    Hiver 2018 / TP3
    
    Auteur : Philippe Lavoie, LAVP24059100
	     Hippolyte Damand DAMH15119208
*/

#include <fstream>
#include <iostream>
#include <string>
#include <list>
#include <math.h>
#include "carte.h"

using namespace std;

bool inferieur(string s1, string s2) {
	s1 = s1.substr(1,s1.size());
	s2 = s2.substr(1,s2.size());
	
	return atoi(s1.c_str()) < atoi(s2.c_str());
}

bool egal(string s1, string s2) {
	s1 = s1.substr(1,s1.size());
	s2 = s2.substr(1,s2.size());
	
	return atoi(s1.c_str()) == atoi(s2.c_str());
}

void tp3(Carte& carte){

	int total = 0;
	std::list<Arete> listearete;
	std::map<string,Noeud> NoeudsVisites;
	struct Noeud tempoNo;
	tempoNo = carte.LesNoeuds[carte.LesNoeuds.begin()->first];
        NoeudsVisites[tempoNo.nom] = tempoNo;	
	int i = 0;
	while(carte.LesNoeuds != NoeudsVisites) {
		struct Arete areteTempo;
		areteTempo.poid = 0;	
		areteTempo.distance = 0;
		
		for (std::list<Arete>::iterator ait = carte.LesAretes.begin(); ait != carte.LesAretes.end(); ++ait){
                	if(NoeudsVisites[(*ait).p1].nom != "" && NoeudsVisites[(*ait).p2].nom == ""){
				if((*ait).poid > areteTempo.poid){
					areteTempo = (*ait);
				}else if((*ait).poid == areteTempo.poid){
					if((*ait).distance < areteTempo.distance){
						areteTempo = (*ait);		
					}						
				}
			}else if(NoeudsVisites[(*ait).p1].nom == "" && NoeudsVisites[(*ait).p2].nom != ""){
				if((*ait).poid > areteTempo.poid){
					areteTempo = (*ait);
				}else if((*ait).poid == areteTempo.poid){
					if((*ait).distance < areteTempo.distance){
						areteTempo = (*ait);		
					}						
				}

			}
		}
		
		if(NoeudsVisites[areteTempo.p1].nom == "")
			NoeudsVisites[areteTempo.p1] = carte.LesNoeuds[areteTempo.p1];
		else
			NoeudsVisites[areteTempo.p2] = carte.LesNoeuds[areteTempo.p2];
		
		if(listearete.empty())
		    	listearete.push_front(areteTempo);
		else {
			std::list<Arete>::iterator it = listearete.begin();
			while(it != listearete.end() && inferieur(it->p1,areteTempo.p1)) 
				it++;
			
			if(it == listearete.end())
				listearete.push_back(areteTempo);
			else if(egal(it->p1,areteTempo.p1)) {
				if(inferieur(areteTempo.p2,it->p2))
					listearete.insert(it,areteTempo);
				else {
					it++;
					listearete.insert(it,areteTempo);
				}
			}
			else
				listearete.insert(it,areteTempo);
		}
		
		//NoeudsVisites[areteTempo.p2] = carte.LesNoeuds[areteTempo.p2];
		//NoeudsVisites[areteTempo.p1] = carte.LesNoeuds[areteTempo.p1];
		//for(std::map<string,Noeud>::iterator it = NoeudsVisites.begin(); it != NoeudsVisites.end(); it++)
			//std::cout << NoeudsVisites[it->first].nom << ",";
		total += areteTempo.poid;
		i++;
		//std::cout << areteTempo.p1 << " " << areteTempo.p2 << "\n" << areteTempo.rue << "\n" << areteTempo.poid << endl;
	}
	
	for(std::list<Arete>::iterator temp = listearete.begin(); temp != listearete.end(); temp++)
		std::cout << temp->p1 << " " << temp->p2 << "\n" << temp->rue << "\n" << temp->poid << endl;
	std::cout << "---" << endl;
	std::cout << total << endl;

}

int main(int argc, const char** argv)
{
  
    if(argc!=2){
        cout << "Syntaxe: ./tp3 carteVisites.txt" << endl;
        return 1;
    }

    Carte carte;
    {
        ifstream fichiercarte(argv[1]);
        if(fichiercarte.fail()){
            cout << "Erreur ouverture du fichier : " << argv[1] << endl;    
            return 1;
        }
        fichiercarte >> carte;
    }
    tp3(carte);
    return 0;
}
