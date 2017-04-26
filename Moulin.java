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
	 * @param position String contenant la grille et le nombre de coups jou�s
	 */
	public Moulin(String position) {
		nbCoups = 0;
		maxCoups = 20;
		gagnant = '.';
		grille = new char[TAILLE][TAILLE];
		stringToGrid(position);
	}
	
	/**
	 * Ins�re le String position dans la grille
	 * @param position Le String � ins�rer
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
	 * Indique � la classe qu'un tour a �t� jou�
	 */
	private void tourJou�() {
		nbCoups++;
	}
	
	/**
	 * V�rifie si les coordonn�es (d'une case) entr�es par l'utilisateur 
	 * sont valides
	 * @param x Coordonn�e sur l'axe x
	 * @param y Coordonn�e sur l'axe y
	 * @return true si les coordonn�es sont valides sinon false
	 */
	public static boolean xyEstValide(int x, int y) {
		return x > 0 && x <= TAILLE && y > 0 && y <= TAILLE;
	}
	
	/**
	 * Renvoie le nombre de coups jou�s dans la partie
	 * @return le nombre de coups jou�s
	 */
	public int getNBCoups() {
		return nbCoups;
	}
	
	/**
	 * Donne le contenu d'une case demand�e par l'utilisateur
	 * @param x Coordonn�e de la case sur l'axe x
	 * @param y Coordonn�e de la case sur l'axe y
	 * @return Le contenu de la case
	 */
	public char getPion(int x, int y) {
		assert(xyEstValide(x, y));
		return grille[x - 1][TAILLE - y];
	}
	
	/**
	 * V�rifie s'il est possible de placer un pion � cette case
	 * @param x Coordonn�e sur l'axe x de cette case
	 * @param y Coordonn�e sur l'axe y de cette case
	 * @return true s'il est possible de placer un pion sinon false
	 */
	public boolean peutEtrePlac�(int x, int y) {
		// V�rifie si la case est vide et si les coordonn�es sont valides
		if(xyEstValide(x, y) && grille[convertX(x)][convertY(y)] == '.') {
			return true;
		}
		return false;
	}
	
	/**
	 * Place le pion du joueur dans la grille
	 * @param x Coordonn�e sur l'axe x du pion
	 * @param y Coordonn�e sur l'axe y du pion
	 */
	public void placer(int x, int y) {
		assert(!partieFinie());
		assert(peutEtrePlac�(x, y));
		assert(getNBPions() < NB_PIONS_MAX);
		
		// Place le pion du joueur qui joue
		grille[convertX(x)][convertY(y)] = aQui();
		
		tourJou�();
	}
	
	/**
	 * V�rifie si un pion d'une case d'origine peut �tre  d�plac� sur 
	 * une autre case
	 * @param xOrigine Coordonn�e sur l'axe x du pion
	 * @param yOrigine Coordonn�e sur l'axe y du pion
	 * @param x Coordonn�e sur l'axe x de l'autre case
	 * @param y Coordonn�e sur l'axe y de l'autre case
	 * @return true si le pion est d�pla�able sinon renvoie false
	 */
	public boolean peutEtreD�plac�En(int xOrigine, int yOrigine, int x, int y) {
		if(!xyEstValide(xOrigine, yOrigine)) {
			return false;
		}
		
		if(!peutEtrePlac�(x, y)) {
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
		
		// V�rifie si tous les pions ont �t� plac�s avant de les d�placer
		if(getNBPions() != NB_PIONS_MAX) {
			return false;
		}
		
		// V�rifie si la case d'origine est vide
		if(grille[xOrigine][yOrigine] == '.') {
			return false;
		}
		
		// V�rifie si le joueur d�place son propre pion
		if((grille[xOrigine][yOrigine] == 'O' && aQui() != 'O')
			|| (grille[xOrigine][yOrigine] == 'X' && aQui() != 'X')) {
			return false;
		}
				
		// V�rifie si les cases d'origine et de destination sont connexes
		if(Math.abs(x - xOrigine) > 1 && Math.abs(y - yOrigine) > 1) {
			return false;
		}
		else { // Les cases sont connexes
			// V�rifie les d�placements connexes interdits
			if((x + y) % 2 != 0 && (xOrigine + yOrigine) % 2 != 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * D�place un pion d'une case d'origine vers une autre case
	 * @param xOrigine Coordonn�e sur l'axe x de la case d'origine
	 * @param yOrigine Coordonn�e sur l'axe y de la case d'origine
	 * @param x Coordonn�e sur l'axe x de l'autre case
	 * @param y Coordonn�e sur l'axe y de l'autre case
	 */
	public void d�placerEn(int xOrigine, int yOrigine, int x, int y) {
		assert(peutEtreD�plac�En(xOrigine, yOrigine, x, y));
		
		// Convertion
		xOrigine = convertX(xOrigine);
		yOrigine = convertY(yOrigine);
		x = convertX(x);
		y = convertY(y);
		
		// D�placement
		grille[x][y] = grille[xOrigine][yOrigine];
		grille[xOrigine][yOrigine] = '.';
		
		tourJou�();
	}
	
	/**
	 * V�rifie si une partie est termin�e
	 * @return true si la partie est termin�e sinon renvoie false
	 */
	public boolean partieFinie() {
		if(nbCoups >= maxCoups || alignement()) {
			return true;
		}
		return false;
	}

	/**
	 * V�rifie si les axes de la case comportent des pions 
	 * identiques align�s
	 * @param x Coordonn�e sur l'axe x de la case
	 * @param y Coordonn�e sur l'axe y de la case
	 * @return true si un axe est align� sinon false
	 */
	public boolean alignement(int x, int y) {
		// Convertion des param�tres
		x = convertX(x);
		y = convertY(y);
		
		// V�rification ligne verticale
		if(verticaleAlign�e(x)) {
			return true;
		}
		
		// V�rification ligne horizontale
		if(horizontaleAlign�e(y)) {
			return true;
		}
		
		// V�rification des diagonales si besoin
		if(((x + y) % 2) == 0) {
			// Diagonale gauche
			if(x == y && diagGaucheAlign�e()) {
				return true;
			}
			
			// Diagonale droite
			if((x + y) == 2 && diagDroiteAlign�e()) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * V�rifie si l'axe vertical de x comporte des pions 
	 * identiques align�s
	 * @param x Coordonn�e de l'axe � v�rifier
	 * @return true si l'axe vertical est align� sinon false
	 */
	private boolean verticaleAlign�e(int x) {
		return (grille[x][0] != '.'
				&& grille[x][0] == grille[x][1]
				&& grille[x][0] == grille[x][2]);
	}
	
	/**
	 * V�rifie si l'axe horizontal de y comporte des pions
	 * identiques align�s
	 * @param y Coordonn�e de l'axe � v�rifier
	 * @return true si l'axe vertical est align� sinon false
	 */
	private boolean horizontaleAlign�e(int y) {
		return (grille[0][y] != '.'
				&& grille[0][y] == grille[1][y]
				&& grille[0][y] == grille[2][y]);
	}
	
	/**
	 * V�rifie si la diagonale commencant en haut au gauche
	 * comporte des pions identiques align�s
	 * @return true si la diagonale gauche est align�e sinon false
	 */
	private boolean diagGaucheAlign�e() {
		return(grille[0][0] != '.'
				&& grille[0][0] == grille[1][1]
				&& grille[0][0] == grille[2][2]);
	}
	
	/**
	 * V�rifie si la diagonale commencant en haut � droite
	 * comporte des pions identiques align�s
	 * @return true si la diagonale droite est align�e sinon false
	 */
	private boolean diagDroiteAlign�e() {
		return(grille[2][0] != '.'
				&& grille[2][0] == grille[1][1]
				&& grille[2][0] == grille[0][2]);
	}

	/**
	 * V�rifie si un axe de la grille comporte des pions identiques
	 * align�s
	 * @return true si il y a un alignement sinon false
	 */
	public boolean alignement() {
		for(int i = 1; i <= TAILLE; i++) {
			if(alignement(i, i)) {
				// R�cup�ration du joueur gagnant
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
	 * Indique sous forme de caract�re quel joueur est en train de jouer
	 * @return Le caract�re du joueur qui joue ('O' ou 'X')
	 */
	public char aQui() {
		if(nbCoups % 2 == 0) {
			return 'O';
		}
		return 'X';
	}
	
	/**
	 * Donne le nombre de pions jou�s sur la grille
	 * @return Le nombre de pions jou�s
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
	 * Convertie la coordonn�e de l'axe x du joueur en coordonn�e 
	 * de la grille
	 * @param x Coordonn�e de l'axe x � convertir
	 * @return La coordonn�e convertie
	 */
	private int convertX(int x) {
		return --x;
	}
	
	/**
	 * Convertie la coordonn�e de l'axe y du joueur en coordonn�e
	 * de la grille
	 * @param y Coordonn�e de l'axe y � convertir
	 * @return La coordonn�e convertie
	 */
	private int convertY(int y) {
		return TAILLE - y;
	}

	/**
	 * Renvoie le gagnant de la partie lorsqu'elle est termin�e
	 * @return renvoie 'X' ou 'O' en cas de victoire sinon renvoie '.'
	 */
	public char getGagnant() {
		if(alignement()) {
			// Le joueur qui a gagn� est le joueur qui a jou� au tour pr�c�dent
			if(gagnant == 'O') {
				return 'O';
			}
			return 'X';
		}
		
		return '.';
	}
}
