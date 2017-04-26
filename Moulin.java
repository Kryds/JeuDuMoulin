package jeuDuMoulin;

/**
 * Classe du jeu du moulin
 * @author COUDERC Jacques, ZHANG Philippe, WALI Salah-Eddine
 * @version 1.0
 */

public class Moulin {
	public static final int TAILLE = 3;
	public static final int NB_PIONS_MAX = 6;
	private int nbCoups;
	private int maxCoups;
	private char[][] grille;
	private char gagnant;
	
	/**
	 * Constructeur de Moulin
	 * @param maxCoups Nombre de coups maximum
	 */
	public Moulin(int maxCoups) {
		this("...\n...\n...\n0");
		this.maxCoups = maxCoups;
	}
	
	/**
	 * Constructeur de Moulin
	 * @param position String contenant la grille et le nombre de coups joués
	 */
	public Moulin(String position) {
		nbCoups = 0;
		maxCoups = 20;
		gagnant = '.';
		grille = new char[TAILLE][TAILLE];
		stringToGrid(position);
	}
	
	/**
	 * Insère le String position dans la grille
	 * @param position Le String à insérer
	 */
	private void stringToGrid(String position) {
		String[] positions;
		positions = position.split("\n");
		for(int i = 0; i < TAILLE; i++) {
			for(int j = 0; j < TAILLE; j++) {
				grille[j][i] = positions[i].charAt(j);
			}
		}
		nbCoups = Integer.parseInt(positions[TAILLE]);
	}

	/**
	 * Renvoie le nombre de coups maximum de la partie
	 * @return Le nombre maximum de coups
	 */
	public int getMaxCoups() {
		return maxCoups;
	}
	
	/**
	 * Indique à la classe qu'un tour a été joué
	 */
	private void tourJoué() {
		nbCoups++;
	}
	
	/**
	 * Vérifie si les coordonnées (d'une case) entrées par l'utilisateur 
	 * sont valides
	 * @param x Coordonnée sur l'axe x
	 * @param y Coordonnée sur l'axe y
	 * @return true si les coordonnées sont valides sinon false
	 */
	public static boolean xyEstValide(int x, int y) {
		return x > 0 && x <= TAILLE && y > 0 && y <= TAILLE;
	}
	
	/**
	 * Renvoie le nombre de coups joués dans la partie
	 * @return le nombre de coups joués
	 */
	public int getNBCoups() {
		return nbCoups;
	}
	
	/**
	 * Donne le contenu d'une case demandée par l'utilisateur
	 * @param x Coordonnée de la case sur l'axe x
	 * @param y Coordonnée de la case sur l'axe y
	 * @return Le contenu de la case
	 */
	public char getPion(int x, int y) {
		assert(xyEstValide(x, y));
		return grille[x - 1][TAILLE - y];
	}
	
	/**
	 * Vérifie s'il est possible de placer un pion à cette case
	 * @param x Coordonnée sur l'axe x de cette case
	 * @param y Coordonnée sur l'axe y de cette case
	 * @return true s'il est possible de placer un pion sinon false
	 */
	public boolean peutEtrePlacé(int x, int y) {
		// Vérifie si la case est vide et si les coordonnées sont valides
		if(xyEstValide(x, y) && grille[convertX(x)][convertY(y)] == '.') {
			return true;
		}
		return false;
	}
	
	/**
	 * Place le pion du joueur dans la grille
	 * @param x Coordonnée sur l'axe x du pion
	 * @param y Coordonnée sur l'axe y du pion
	 */
	public void placer(int x, int y) {
		assert(!partieFinie());
		assert(peutEtrePlacé(x, y));
		assert(getNBPions() < NB_PIONS_MAX);
		
		// Place le pion du joueur qui joue
		grille[convertX(x)][convertY(y)] = aQui();
		
		tourJoué();
	}
	
	/**
	 * Vérifie si un pion d'une case d'origine peut être  déplacé sur 
	 * une autre case
	 * @param xOrigine Coordonnée sur l'axe x du pion
	 * @param yOrigine Coordonnée sur l'axe y du pion
	 * @param x Coordonnée sur l'axe x de l'autre case
	 * @param y Coordonnée sur l'axe y de l'autre case
	 * @return true si le pion est déplaçable sinon renvoie false
	 */
	public boolean peutEtreDéplacéEn(int xOrigine, int yOrigine, int x, int y) {
		if(!xyEstValide(xOrigine, yOrigine)) {
			return false;
		}
		
		if(!peutEtrePlacé(x, y)) {
			return false;
		}
		
		// Convertion
		xOrigine = convertX(xOrigine);
		yOrigine = convertY(yOrigine);
		x = convertX(x);
		y = convertY(y);
		
		
		if(partieFinie()) {
			return false;
		}
		
		// Vérifie si tous les pions ont été placés avant de les déplacer
		if(getNBPions() != NB_PIONS_MAX) {
			return false;
		}
		
		// Vérifie si la case d'origine est vide
		if(grille[xOrigine][yOrigine] == '.') {
			return false;
		}
		
		// Vérifie si le joueur déplace son propre pion
		if((grille[xOrigine][yOrigine] == 'O' && aQui() != 'O')
			|| (grille[xOrigine][yOrigine] == 'X' && aQui() != 'X')) {
			return false;
		}
				
		// Vérifie si les cases d'origine et de destination sont connexes
		if(Math.abs(x - xOrigine) > 1 && Math.abs(y - yOrigine) > 1) {
			return false;
		}
		else { // Les cases sont connexes
			// Vérifie les déplacements connexes interdits
			if((x + y) % 2 != 0 && (xOrigine + yOrigine) % 2 != 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Déplace un pion d'une case d'origine vers une autre case
	 * @param xOrigine Coordonnée sur l'axe x de la case d'origine
	 * @param yOrigine Coordonnée sur l'axe y de la case d'origine
	 * @param x Coordonnée sur l'axe x de l'autre case
	 * @param y Coordonnée sur l'axe y de l'autre case
	 */
	public void déplacerEn(int xOrigine, int yOrigine, int x, int y) {
		assert(peutEtreDéplacéEn(xOrigine, yOrigine, x, y));
		
		// Convertion
		xOrigine = convertX(xOrigine);
		yOrigine = convertY(yOrigine);
		x = convertX(x);
		y = convertY(y);
		
		// Déplacement
		grille[x][y] = grille[xOrigine][yOrigine];
		grille[xOrigine][yOrigine] = '.';
		
		tourJoué();
	}
	
	/**
	 * Vérifie si une partie est terminée
	 * @return true si la partie est terminée sinon renvoie false
	 */
	public boolean partieFinie() {
		if(nbCoups >= maxCoups || alignement()) {
			return true;
		}
		return false;
	}

	/**
	 * Vérifie si les axes de la case comportent des pions 
	 * identiques alignés
	 * @param x Coordonnée sur l'axe x de la case
	 * @param y Coordonnée sur l'axe y de la case
	 * @return true si un axe est aligné sinon false
	 */
	public boolean alignement(int x, int y) {
		// Convertion des paramètres
		x = convertX(x);
		y = convertY(y);
		
		// Vérification ligne verticale
		if(verticaleAlignée(x)) {
			return true;
		}
		
		// Vérification ligne horizontale
		if(horizontaleAlignée(y)) {
			return true;
		}
		
		// Vérification des diagonales si besoin
		if(((x + y) % 2) == 0) {
			// Diagonale gauche
			if(x == y && diagGaucheAlignée()) {
				return true;
			}
			
			// Diagonale droite
			if((x + y) == 2 && diagDroiteAlignée()) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Vérifie si l'axe vertical de x comporte des pions 
	 * identiques alignés
	 * @param x Coordonnée de l'axe à vérifier
	 * @return true si l'axe vertical est aligné sinon false
	 */
	private boolean verticaleAlignée(int x) {
		return (grille[x][0] != '.'
				&& grille[x][0] == grille[x][1]
				&& grille[x][0] == grille[x][2]);
	}
	
	/**
	 * Vérifie si l'axe horizontal de y comporte des pions
	 * identiques alignés
	 * @param y Coordonnée de l'axe à vérifier
	 * @return true si l'axe vertical est aligné sinon false
	 */
	private boolean horizontaleAlignée(int y) {
		return (grille[0][y] != '.'
				&& grille[0][y] == grille[1][y]
				&& grille[0][y] == grille[2][y]);
	}
	
	/**
	 * Vérifie si la diagonale commencant en haut au gauche
	 * comporte des pions identiques alignés
	 * @return true si la diagonale gauche est alignée sinon false
	 */
	private boolean diagGaucheAlignée() {
		return(grille[0][0] != '.'
				&& grille[0][0] == grille[1][1]
				&& grille[0][0] == grille[2][2]);
	}
	
	/**
	 * Vérifie si la diagonale commencant en haut à droite
	 * comporte des pions identiques alignés
	 * @return true si la diagonale droite est alignée sinon false
	 */
	private boolean diagDroiteAlignée() {
		return(grille[2][0] != '.'
				&& grille[2][0] == grille[1][1]
				&& grille[2][0] == grille[0][2]);
	}

	/**
	 * Vérifie si un axe de la grille comporte des pions identiques
	 * alignés
	 * @return true si il y a un alignement sinon false
	 */
	public boolean alignement() {
		for(int i = 1; i <= TAILLE; i++) {
			if(alignement(i, i)) {
				// Récupération du joueur gagnant
				gagnant = grille[convertX(i)][convertY(i)];
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Renvoie le jeu du moulin en cours sous forme de String
	 * @return Le jeu du moulin en cours
	 */
	@Override
	public String toString() {
		String grilleStr = "";
		
		for(int i = 0; i < TAILLE; i++) {
			for(int j = 0; j < TAILLE; j++) {
				grilleStr += grille[j][i];
			}
			grilleStr += "\n";
		}
			
		return grilleStr + nbCoups;
	}

	/**
	 * Indique sous forme de caractère quel joueur est en train de jouer
	 * @return Le caractère du joueur qui joue ('O' ou 'X')
	 */
	public char aQui() {
		if(nbCoups % 2 == 0) {
			return 'O';
		}
		return 'X';
	}
	
	/**
	 * Donne le nombre de pions joués sur la grille
	 * @return Le nombre de pions joués
	 */
	public int getNBPions() {
		int nbPions = 0;
		for(int i = 0; i < TAILLE; i++) {
			for(int j = 0; j < TAILLE; j++) {
				if(grille[i][j] != '.') {
					nbPions++;
				}
			}
		}
		return nbPions;
	}
	
	/**
	 * Convertie la coordonnée de l'axe x du joueur en coordonnée 
	 * de la grille
	 * @param x Coordonnée de l'axe x à convertir
	 * @return La coordonnée convertie
	 */
	private int convertX(int x) {
		return --x;
	}
	
	/**
	 * Convertie la coordonnée de l'axe y du joueur en coordonnée
	 * de la grille
	 * @param y Coordonnée de l'axe y à convertir
	 * @return La coordonnée convertie
	 */
	private int convertY(int y) {
		return TAILLE - y;
	}

	/**
	 * Renvoie le gagnant de la partie lorsqu'elle est terminée
	 * @return renvoie 'X' ou 'O' en cas de victoire sinon renvoie '.'
	 */
	public char getGagnant() {
		if(alignement()) {
			// Le joueur qui a gagné est le joueur qui a joué au tour précédent
			if(gagnant == 'O') {
				return 'O';
			}
			return 'X';
		}
		
		return '.';
	}
}
