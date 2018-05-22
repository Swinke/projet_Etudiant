/*  INF3105 - Structures de données et algorithmes
 *  UQAM / Département d'informatique
 *  Hiver 2018 / TP3
 *
 *  Vos noms + codes permanents :
 *    Philippe Lavoie, LAVP24059100
 *    Hippolyte Damand DAMH15119208
 */
 
#ifndef CARTE_H
#define CARTE_H

#include "coordonnee.h"
#include <cassert>
#include <istream>
#include <list>
#include <map>
#include <iostream>
#include <string>
#include <vector>

using namespace std;

struct Noeud
{
        string nom;
        Coordonnee coordo;
	bool operator == (const Noeud& n) const {return n.nom == nom;}
};
struct Arete
{
	int poid;
	double distance;
	string p1;
	string p2;
	string rue;
};

class Carte{

  public:
	 std::list<Arete> LesAretes;
         std::map<string,Noeud> LesNoeuds;


  private:
  
  friend istream& operator >> (istream& is, Carte& carte);

};

#endif

