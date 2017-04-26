package joueur;

/**
 * Classe UIJoueurHumain
 * @date 25/04/2017
 * @version 1.0
 */

import java.util.Scanner;

public class UIJoueurHumain {
	private static Scanner sc;
	
	public UIJoueurHumain() {
		
	}

	public void afficher(String grille) {
		System.out.println("Tour du joueur :");
		System.out.println(grille);
		
	}

	public String getPlacement() {
		System.out.println("Placement (Insérez x et y) : ");
		return null;
	}

	public String getDéplacement() {
		System.out.println("Déplacement (Insérez x et y d'origine et x et y de destination) : ");
		return null;
	}
}
