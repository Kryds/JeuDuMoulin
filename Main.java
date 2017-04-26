package jeuDuMoulin;

import java.util.Scanner;

/**
 * Classe interface entre l'utilisateur et le jeu du moulin
 * @author COUDERC Jacques, ZHANG Philippe, WALI Salah-Eddine
 * @version 1.0
 * @see Moulin.java
 */

public class Main {
	private static Moulin m;
	private static Scanner sc;
	
	public static void main(String[] args) {
		sc = new Scanner(System.in);
		System.out.print("Insérer un nombre de tours maximum :");
		m = new Moulin(sc.nextInt());
		partie();
		gagnant();
	}
	
	private static void partie() {
		int x = 0, y = 0, xOrigine = 0, yOrigine = 0;
		
		while(!m.partieFinie()) {
			System.out.println("Tour du joueur " + m.aQui());
			
			// Placement des pions
			if(m.getNBPions() < Moulin.NB_PIONS_MAX) {
				do {
					System.out.println("Insérez x et y :");
					x = sc.nextInt();
					y = sc.nextInt();
				} while(!m.peutEtrePlacé(x, y));
				m.placer(x, y);
			}
			// Déplacement des pions
			else {
				do {
					System.out.println("Insérez les coordonnées d'origine et les nouvelles coordonnées de votre pion :");
					xOrigine = sc.nextInt();
					yOrigine = sc.nextInt();
					x = sc.nextInt();
					y = sc.nextInt();
				} while(!m.peutEtreDéplacéEn(xOrigine, yOrigine, x, y));
				m.déplacerEn(xOrigine, yOrigine, x, y);
			}
			System.out.println(m.toString());
		}
		
		sc.close();
	}
	
	private static void gagnant() {
		if(m.alignement()) {
			System.out.println("Le gagnant est le joueur " + m.getGagnant());
		}
		else {
			System.out.println("Egalité");
		}
	}

}
