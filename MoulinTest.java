package jeuDuMoulin;
import static org.junit.Assert.*;

import org.junit.Test;

public class MoulinTest {
	private final int MAXCOUPS = 20;
	private final int N = Moulin.TAILLE;

	@Test
	public void test() {
		Moulin m = new Moulin(MAXCOUPS);
		
		assertEquals(m.getMaxCoups(), MAXCOUPS);
		assertEquals("...\n...\n...\n0", m.toString());
		// la position est indiquée ligne par ligne
		// le nombre à la fin est le nombre de coups déjà joués
		// s'il est pair c'est à O de jouer sinon à X
		
		assertTrue(Moulin.xyEstValide(1, 1));
		assertTrue(Moulin.xyEstValide(1, N));
		assertTrue(Moulin.xyEstValide(N, 1));
		assertTrue(Moulin.xyEstValide(N, N));
		assertFalse(Moulin.xyEstValide(0, 0));
		assertFalse(Moulin.xyEstValide(1, 0));
		assertFalse(Moulin.xyEstValide(0, N));
		assertFalse(Moulin.xyEstValide(1, N+1));
		assertFalse(Moulin.xyEstValide(N+1, 1));
		
		assertEquals(m.getNBCoups(), 0);
		assertEquals(m.getPion(2, 2), '.');
		assertEquals('O',m.aQui());  		// à qui de jouer
		m.placer(2, 2);
		
		assertEquals("...\n.O.\n...\n1", m.toString());
		assertEquals(m.getNBCoups(), 1);
		assertEquals(m.getPion(2,2), 'O');
		assertEquals('X',m.aQui());
		
		m.placer(1, 1);
		assertEquals("...\n.O.\nX..\n2", m.toString());
		assertEquals(m.getNBCoups(), 2);
		assertEquals(m.getPion(1, 1), 'X');
		
		
		m.placer(1,3); // en HAUT à GAUCHE
		assertEquals("O..\n.O.\nX..\n3", m.toString());
		assertEquals(m.getNBCoups(), 3);
		assertEquals(m.getPion(1, 3), 'O');

		String position = ".XO\n.OX\nOX.\n10"; // 10 coups ont été joués
		System.out.println(position);

		m = new Moulin(position);
		
		assertTrue(m.alignement(3, 3));
		assertTrue(m.alignement(1, 1));
		assertTrue(m.alignement(2, 2));
		assertFalse(m.alignement(2, 3));
		assertFalse(m.alignement(3, 2));
		assertFalse(m.alignement(2, 1));
		assertFalse(m.alignement(1, 2));
		assertFalse(m.alignement(1, 3));
		assertFalse(m.alignement(3, 1));
		assertTrue(m.alignement());

		position = "..O\n.XX\nOXO\n11"; // 11 coups ont été joués
		System.out.println(position);
		m = new Moulin(position);

		assertEquals(position, m.toString());
		assertFalse(m.alignement(3, 3));
		assertFalse(m.alignement(1, 1));
		assertFalse(m.alignement(2, 2));
		assertFalse(m.alignement(2, 3));
		assertFalse(m.alignement(3, 2));
		assertFalse(m.alignement(2, 1));
		assertFalse(m.alignement(1, 2));
		assertFalse(m.alignement(1, 3));
		assertFalse(m.alignement(3, 1));
		assertFalse(m.alignement());		
		
		assertTrue(m.peutEtreDéplacéEn(2, 2, 1, 3));
		assertTrue(m.peutEtreDéplacéEn(2, 2, 1, 2));
		assertTrue(m.peutEtreDéplacéEn(2, 2, 2, 3));
		assertFalse(m.peutEtreDéplacéEn(3, 3, 2, 3)); // c'est à 'X' de jouer
		assertFalse(m.peutEtreDéplacéEn(1, 1, 1, 2));  // c'est à 'X' de jouer
		assertFalse(m.peutEtreDéplacéEn(2, 1, 1, 2));
		assertFalse(m.peutEtreDéplacéEn(3, 2, 2, 3));
		assertFalse(m.peutEtreDéplacéEn(1, 3, 2, 3));
		assertFalse(m.peutEtreDéplacéEn(3, 3, 1, 3));
		
		m.déplacerEn(2,2,1,3);
		position = "X.O\n..X\nOXO\n12";
		System.out.println(position);
		assertEquals(position, m.toString());
		assertEquals('O',m.aQui());  

	}
	
	@Test
	public void testAlignement() {
		Moulin m = new Moulin("X.O\n.XO\nX.O\n6");
		assertEquals(m.aQui(), 'O');
		assertFalse(m.alignement(1, 3));
		assertFalse(m.alignement(2, 3));
		assertFalse(m.alignement(1, 2));
		assertFalse(m.alignement(2, 2));
		assertFalse(m.alignement(1, 1));
		assertFalse(m.alignement(2, 1));
		assertTrue(m.alignement(3, 3));
		assertTrue(m.alignement(3, 2));
		assertTrue(m.alignement(3, 1));
		assertTrue(m.alignement());
		
		m = new Moulin("X.O\n.OX\nO.X\n6");
		assertEquals(m.aQui(), 'O');
		assertFalse(m.alignement(1, 2));
		assertFalse(m.alignement(1, 3));
		assertFalse(m.alignement(2, 1));
		assertFalse(m.alignement(2, 3));
		assertFalse(m.alignement(3, 1));
		assertFalse(m.alignement(3, 2));
		assertTrue(m.alignement(3, 3));
		assertTrue(m.alignement(2, 2));
		assertTrue(m.alignement(1, 1));
		assertTrue(m.alignement());
	}
	
	@Test
	public void test2() {
		Moulin m = new Moulin(MAXCOUPS);
		
		assertEquals(m.getMaxCoups(), MAXCOUPS);
		assertEquals("...\n...\n...\n0", m.toString());
		// la position est indiquée ligne par ligne
		// le nombre à la fin est le nombre de coups déjà joués
		// s'il est pair c'est à O de jouer sinon à X
		
		assertFalse(m.partieFinie());
		assertEquals('.',m.getGagnant());
		assertTrue(Moulin.xyEstValide(1, 1));
		assertTrue(Moulin.xyEstValide(1, N));
		assertTrue(Moulin.xyEstValide(N, 1));
		assertTrue(Moulin.xyEstValide(N, N));
		assertFalse(Moulin.xyEstValide(0, 0));
		assertFalse(Moulin.xyEstValide(1, 0));
		assertFalse(Moulin.xyEstValide(0, N));
		assertFalse(Moulin.xyEstValide(1, N+1));
		assertFalse(Moulin.xyEstValide(N+1, 1));
		
		assertEquals(m.getNBCoups(), 0);
		assertEquals(m.getPion(2, 2), '.');
		assertEquals('O',m.aQui());  		// à qui de jouer
		assertEquals(m.getNBPions(), 0);
		assertTrue(m.peutEtrePlacé(2, 2));
		m.placer(2, 2);
		assertFalse(m.peutEtrePlacé(2, 2));
		assertEquals(m.getNBPions(), 1); 
		assertFalse(m.partieFinie());
		
		assertEquals("...\n.O.\n...\n1", m.toString());
		assertFalse(m.partieFinie());
		assertEquals('.',m.getGagnant());
		assertEquals(m.getNBCoups(), 1);
		assertEquals(m.getPion(2,2), 'O');
		assertEquals('X',m.aQui());
		
		assertTrue(m.peutEtrePlacé(1, 1));
		m.placer(1, 1);
		assertFalse(m.peutEtrePlacé(1, 1));
		assertEquals(m.getNBPions(), 2);
		assertNotEquals(1, m.getNBPions());
		assertEquals("...\n.O.\nX..\n2", m.toString());
		assertEquals(m.getNBCoups(), 2);
		assertEquals(m.getPion(1, 1), 'X');
		
		
		m.placer(1,3); // en HAUT à GAUCHE
		assertEquals("O..\n.O.\nX..\n3", m.toString());
		assertEquals(m.getNBCoups(), 3);
		assertEquals(m.getPion(1, 3), 'O');
		assertFalse(m.partieFinie());

		String position = ".XO\n.OX\nOX.\n10"; // 10 coups ont été joués
		System.out.println(position);

		m = new Moulin(position);
		
		assertTrue(m.peutEtrePlacé(1, 3));
		assertTrue(m.peutEtrePlacé(1, 2));
		assertTrue(m.peutEtrePlacé(3, 1));
		assertTrue(m.alignement(3, 3));
		assertTrue(m.alignement(1, 1));
		assertTrue(m.alignement(2, 2));
		assertTrue(m.partieFinie());
		assertEquals(m.getGagnant(),'O');
		assertFalse(m.alignement(2, 3));
		assertFalse(m.alignement(3, 2));
		assertFalse(m.alignement(2, 1));
		assertFalse(m.alignement(1, 2));
		assertFalse(m.alignement(1, 3));
		assertFalse(m.alignement(3, 1));
		assertTrue(m.partieFinie());
		assertTrue(m.alignement());
		assertTrue(m.partieFinie());
		assertEquals(m.getGagnant(),'O');
		
		position = "..O\n.XX\nOXO\n11"; // 11 coups ont été joués
		System.out.println(position);
		m = new Moulin(position);

		assertEquals(position, m.toString());
		assertFalse(m.alignement(3, 3));
		assertFalse(m.alignement(1, 1));
		assertFalse(m.alignement(2, 2));
		assertFalse(m.alignement(2, 3));
		assertFalse(m.alignement(3, 2));
		assertFalse(m.alignement(2, 1));
		assertFalse(m.alignement(1, 2));
		assertFalse(m.alignement(1, 3));
		assertFalse(m.alignement(3, 1));
		assertFalse(m.alignement());
		assertFalse(m.partieFinie());
		assertEquals(m.getGagnant(),'.');
		
		assertTrue(m.peutEtreDéplacéEn(2, 2, 1, 3));
		assertTrue(m.peutEtreDéplacéEn(2, 2, 1, 2));
		assertTrue(m.peutEtreDéplacéEn(2, 2, 2, 3));
		assertFalse(m.peutEtreDéplacéEn(3, 3, 2, 3)); // c'est à 'X' de jouer
		assertFalse(m.peutEtreDéplacéEn(1, 1, 1, 2));  // c'est à 'X' de jouer
		assertFalse(m.peutEtreDéplacéEn(2, 1, 1, 2));
		assertFalse(m.peutEtreDéplacéEn(3, 2, 2, 3));
		assertFalse(m.peutEtreDéplacéEn(1, 3, 2, 3));
		assertFalse(m.peutEtreDéplacéEn(3, 3, 1, 3));
		
		m.déplacerEn(2,2,1,3);
		assertEquals(6, m.getNBPions());
		position = "X.O\n..X\nOXO\n12";
		System.out.println(position);
		assertEquals(position, m.toString());
		assertEquals('O',m.aQui());  

	}
}