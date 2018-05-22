# Makefile pour Hiver 2018 / TP3

#OPTIONS = -Wall           # option standard
#OPTIONS = -gcc -O0 -Wall    # pour rouler dans gdb
OPTIONS = -std=c++11 -O2 -Wall# pour optimiser
EXEC = tp3

all : $(EXEC)

$(EXEC): clean src/tp3.cpp
	g++ ${OPTIONS} -o tp3 src/tp3.cpp src/carte.cpp src/coordonnee.cpp 

clean:
	rm -rf tp3 *~ *.o

