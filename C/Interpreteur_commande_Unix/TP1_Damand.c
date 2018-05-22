/** TP1 3172 - Principe des systèmes d'exploitation
 * author:	Damand Hippolyte
 *		DAMH151192
 * title: TP1
 * */

#include <stdio.h>
#include <string.h>
#include <time.h>
#include <errno.h>
#include <stdlib.h>
#include <pwd.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <dirent.h>

#define BUF_SIZE 8192
/**
* Commande ls
**/
void commandels() {
    char option[4];
    char path[100];
    printf("Entrer un chemin ('.' si relatif) : ");
    scanf("%s",path);
    
    printf("Rentrez une option(nom,-l,-a,-R) : ");
    scanf("%s",option);
    //Appel de la fonction qui affiche les fichiers du répertoires
    fls(path,option);
    // Si option -R on parcours tout le dossier
    if(strcmp(option,"-R") == 0) {
	DIR *rept;
	rept = opendir(path);
	struct dirent *lect2;
	while((lect2 = readdir(rept))) {
	    if((lect2->d_name)[0] != '.'){
		//Si le fichier lu est un répertoire
		if((lect2->d_type & DT_DIR)) {
		    char pathtemp[100];
		    //Création du chemin vers le répertoire
		    strcpy(pathtemp,path);
		    strcat(path,"/");
		    strcat(path,lect2->d_name);
		    printf("\n%s\n",path);
		    //Affichage fichiers repertoires
		    fls(path,"nom");
		    //Réinitialisation du chemin
		    strcpy(path,pathtemp);
		}
	    }
	}
	closedir(rept);
    }
}

/**
* Fonction qui affiche les fichiers d'un répertoire 
* en fonction de l'option choisit
**/
void fls(char* path, char* op) {
    DIR *rep; 
    rep = opendir(path);

    char* nom;

    struct dirent *lect;
    struct stat buf;
    struct tm *time;
    struct passwd *pwd;
    
    if(rep == NULL)
	perror("Erreur chemin");
    else {
        while ((lect = readdir(rep))) {
	    nom = lect->d_name;
	    if(strcmp(op,"-l") == 0 && nom[0] != '.') { 
	        stat(nom,&buf);
	        printf((S_ISDIR(buf.st_mode)) ? "d" : "-");
	        printf((buf.st_mode & S_IRUSR) ? "r" : "-");
	        printf((buf.st_mode & S_IWUSR) ? "w" : "-");
    	        printf((buf.st_mode & S_IXUSR) ? "x" : "-");
    	        printf((buf.st_mode & S_IRGRP) ? "r" : "-");
    	        printf((buf.st_mode & S_IWGRP) ? "w" : "-");
    	        printf((buf.st_mode & S_IXGRP) ? "x" : "-");
    	        printf((buf.st_mode & S_IROTH) ? "r" : "-");
    	        printf((buf.st_mode & S_IWOTH) ? "w" : "-");
    	        printf((buf.st_mode & S_IXOTH) ? "x" : "-");

	        printf(" %d", buf.st_nlink);
	        pwd = getpwuid(buf.st_uid);
	        printf(" %s", pwd->pw_name);
	        printf(" %d", buf.st_size);
	        printf(" %s", ctime(&buf.st_mtime));
	    }
	    if(strcmp(op,"-a") == 0)
	        printf(" %s\n",nom);
	    else if(nom[0] != '.')
	        printf("%s\n",nom);
        }
	closedir(rep);
    }
}

/**
* Lecture d'un fichier
**/
void lectureFichier() {
    ssize_t ret_in;
    char buffer[BUF_SIZE], path[100];
    printf("Entrez un chemin :");
    scanf("%s",path);
    int input = open(path,O_RDONLY);
    if(input == -1) 
	perror("open");
    
    while((ret_in = read(input, &buffer,BUF_SIZE)) > 0) {
	printf("%s\n",buffer);
    }
    close(input);
}

void supprimerFichier(char* path) {
     unlink(path);
}

void copierFichier(int sup) {
    ssize_t ret_in,ret_out;
    char buffer[BUF_SIZE];
    char pathin[50],pathout[50];

    printf("Entrer un chemin ('.' si relatif) : ");
    scanf("%s",pathin);
    printf("Entrer un chemin ('.' si relatif) : ");
    scanf("%s",pathout);

    int input = open(pathin,O_RDONLY);
    if(input == -1) 
	perror("open");
    int output  = open(pathout,O_WRONLY | O_CREAT, 0644);
    if(output == -1) 
	perror("open");
    
    while((ret_in = read(input, &buffer,BUF_SIZE)) > 0) {
	ret_out = write(output, &buffer, ret_in);
	if(ret_out != ret_in)
	     perror("write");
    }
    close(input);
    if(sup == 1) {
        supprimerFichier(pathin);
    }
    close(output);
}

void afficherPath() {
    char* s = getenv("PATH");
    printf("PATH : %s\n",s);
}

void executerProgramme() {
    char path[100], argtemp[50];
    int  nbarg[1];

    printf("Entrez un chemin ou le nom du programme (si présent dans PATH) : ");
    scanf("%s",path);
    printf("Entrez le nombre d'arguments : ");
    scanf("%i",nbarg);

    char *arg[nbarg[0] + 1];
    for(int i=0;i<nbarg[0];i++) {
	printf("Entrez argument n°%i : ",i+1);
	scanf("%s",argtemp);
        arg[i] = argtemp;
    }

    pid_t pid = fork();
    if(pid == 0) {
       arg[nbarg[0]] = NULL; 
       if(path[0] == '.' || path[0] == '/'){
	    if(execv(path,arg) == -1) {
		perror("execution");
		exit(0);
	    }
       }
       else {
	    if(execvp(path,arg) == -1) {
		perror("execution");
		exit(0);
	    }
       }
    }else
	wait(NULL);
}
	
int main() {
    char menu[3], path[100];

    do {
        printf("Rentrez une commande \n");
        printf("1 - commande ls\n");
        printf("2 - lecture fichier\n");
        printf("3 - copier fichier\n");
        printf("4 - deplacer fichier\n");
        printf("5 - supprimer fichier\n");
        printf("6 - afficher PATH\n");
	printf("7 - executer programme\n");
	printf("8 - quitter\n");
        scanf("%s",menu);
        switch(*menu) {
	   case '1': 
	        commandels();
	        break;
	   case '2':
	        lectureFichier();
	        break;
	   case '3':
	        copierFichier(0);
	        break;
	   case '4':
	        copierFichier(1);
	        break;
	   case '5':
	        printf("Entrer un chemin ('.' si relatif) : ");
                scanf("%s",path);
	        supprimerFichier(path);
	        break;
	   case '6':
		afficherPath();
		break;
	   case '7':
		executerProgramme();
		break;
	}
        printf("----------------------\n");
    }while(*menu != '8'); 
    printf("A bientôt\n");
    return 0;
}
