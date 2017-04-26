package joueur;

/**
 * Classe JoueurHumain
 * @date 25/04/2017
 * @version 1.0
 */

public class JoueurHumain extends Joueur {
	private UIJoueurHumain UI;
	private char pion;
	
	public JoueurHumain(char pion) { // Penser � placer le jeu du moulin pour pouvoir utiliser peutEtreDeplac�
		this.pion = pion;
	}

	@Override
	String placer(String grille) {
		UI.afficher(grille);
		return UI.getPlacement();
	}

	@Override
	String d�placer(String grille) {
		UI.afficher(grille);
		return UI.getD�placement();
	}

}
