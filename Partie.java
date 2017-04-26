package jeuDuMoulin;

/**
 * Classe Partie
 * @date 25/04/2017
 * @version 1.0
 */

import joueur.Joueur;

public class Partie {
	
	private Joueur j1, j2;
	private Moulin m;
	
	public Partie(Joueur j1, Joueur j2, int maxCoups) {
		this.j1 = j1;
		this.j2 = j2;
		m = new Moulin(maxCoups);
	}
	
	public void jouer() {
		while(!m.partieFinie()) {
			
		}
	}
	
	public char getGagnant() {
		// Assert partie finie
		return 0;
	}
}
