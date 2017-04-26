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
		System.out.print("Ins�rer un nombre de tours maximum :");
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
					System.out.println("Ins�rez x et y :");
					x = sc.nextInt();
					y = sc.nextInt();
				} while(!m.peutEtrePlac�(x, y));
				m.placer(x, y);
			}
			// D�placement des pions
			else {
				do {
					System.out.println("Ins�rez les coordonn�es d'origine et les nouvelles coordonn�es de votre pion :");
					xOrigine = sc.nextInt();
					yOrigine = sc.nextInt();
					x = sc.nextInt();
					y = sc.nextInt();
				} while(!m.peutEtreD�plac�En(xOrigine, yOrigine, x, y));
				m.d�placerEn(xOrigine, yOrigine, x, y);
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
			System.out.println("Egalit�");
		}
	}

}
