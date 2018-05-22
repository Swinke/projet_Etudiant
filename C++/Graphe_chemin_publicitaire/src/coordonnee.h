/*  INF3105 - Structures de données et algorithmes       *
 *  UQAM / Département d'informatique                    *
 *  Hiver 2018 / TP3                                     *
 *                                                       *
 *  Par : Philippe Lavoie, LAVP24059100                  *
 *        Hippolyte Damand DAMH15119208                  */

#if !defined(_COORDONNEE__H_)
#define _COORDONNEE__H_
#include <iostream>

class Coordonnee {
  public:
    Coordonnee(){}
    Coordonnee(double latitude_, double longitude_);
    Coordonnee(const Coordonnee&);
    double distance(const Coordonnee&) const;

  private:
    double latitude;
    double longitude;

  friend std::ostream& operator << (std::ostream&, const Coordonnee&);
  friend std::istream& operator >> (std::istream&, Coordonnee&);
};

#endif

