package modeles;

import static org.junit.Assert.*;
import org.junit.Test;

public class JoueurTest {

	Map map = new Map();
	Inventaire inventaire = new Inventaire();
	Joueur p = new Joueur(map,inventaire);
	Personnage ennemi = new Zombie(map);
	
	@Test
	public final void testMove() {
		p.move(0.0, 0.0);
		assertEquals("aucun mouvement côté X", 0.0, p.getX(), 0.0);
		assertEquals("aucun mouvement côté Y", 0.0, p.getY(), 0.0);
		p.move(32.0, 128.0);
		assertEquals("mouvement vers le sud-est côté X", 32.0, p.getX(), 0.0);
		assertEquals("mouvement vers le sud-est côté Y", 128.0, p.getY(), 0.0);
		p.move(-8.0, -64.0);
		assertEquals("mouvement vers le nord-ouest côté X", 24.0, p.getX(), 0.0);
		assertEquals("mouvement vers le nord-ouest côté Y", 64.0, p.getY(), 0.0);
		p.move(-24.0, 128.0);
		assertEquals("mouvement vers le sud-ouest côté X", 0.0, p.getX(), 0.0);
		assertEquals("mouvement vers le sud-ouest côté Y", 192.0, p.getY(), 0.0);
		p.move(4.0, -4.0);
		assertEquals("mouvement vers le nord-est côté X", 4.0, p.getX(), 0.0);
		assertEquals("mouvement vers le nord-ouest côté Y", 188.0, p.getY(), 0.0);
	}
	
	@Test
	public final void testOnGround() {
		//Si l'on ne modifie pas la carte "MapEmilie.csv",
		//le premier bloc où il y a collision a comme coordonnées (0, 20).
		//Le personnage fait 64 pixels de haut donc il touche à y=
		//Soit cp(x,y) les coordonnées du personnage en bloc.
		assertFalse("cas où cp(0,0)", p.onGround());
		p.setX(992.0);
		p.setY(256.0);
		assertFalse("cas où cp(31,8)", p.onGround());
		p.setX(0.0);
		p.setY(576);
		assertTrue("cas où cp(0,18)", p.onGround());
		p.setX(704.0);
		p.setY(512.0);
		assertTrue("cas où cp(22,16)", p.onGround());
	}
	
	@Test
	public final void testPlafond() {
		//On utilise pour ce test "MapEmilie.csv" qui est utilisé quand on construit la classe Map.
		//Cette méthode ne teste pas les bordures de la carte.
		//Soit cp(x,y) les coordonnées du personnage en bloc.
		assertFalse("cas où cp(0,0)", p.plafond(0));
		p.setX(640);
		p.setY(960);
		assertTrue("cas où cp(20,32)", p.plafond(64));
		p.setX(256);
		p.setY(576);
		assertFalse("cas où cp(8,18)", p.plafond(0));
	}
	
	@Test
	public final void testCollision() {
		assertFalse("cas il n'y a aucune collision", p.collision(32, 32));
		p.setY(576);
		assertTrue("cas où il y a collision en bas", p.collision(0, 8));
		p.setX(640);
		assertTrue("cas où il y a collision à droite", p.collision(8, 0));
		p.setX(1148);
		assertTrue("cas où il y a collision à gauche", p.collision(-8, 0));
		p.setX(512);
		p.setY(1000);
		assertTrue("cas où il y a collision en haut", p.collision(0, -8));
		p.setX(1148);
		p.setY(576);
		assertTrue("cas où il y a plusieurs collisions", p.collision(-8, 8));
	}
	
	@Test
	public final void testCasserBlock() {
		//La méthode casserBlock de Joueur fait appel à la méthode useItem dans Pickaxe.
		p.setX(644);
		p.setY(576);
		inventaire.add(new Pickaxe());
		p.casserBlock(21, 19);
		int premierElementInventaire = Integer.parseInt(inventaire.getInventoryList().
				get(inventaire.getInventoryList().size()-1).getId());
		assertEquals("cas où il ramasse de la pierre", 8, premierElementInventaire, 0.0);
	}
	
	@Test
	public final void testPlacerBlock() {
		//La méthode casserBlock de Joueur fait appel à la méthode useItem dans BlockItem.
		//Ne gère pas la portée.
		p.setX(76);
		p.setY(576);
		inventaire.add(new BlockItem("8"));
		p.placerBlock(4, 19);
		assertEquals("cas où il a une pierre mais qu'il ne peut pas poser à l'endroit donné",
				8, Integer.parseInt(inventaire.getInventoryList().
						get(inventaire.getInventoryList().size()-1).getId()), 0.0);
		p.placerBlock(20, 19);
		assertEquals("cas où il a une pierre dans l'inventaire qu'il peut poser", -1,
				Integer.parseInt(inventaire.getInventoryList().
						get(inventaire.getInventoryList().size()-1).getId()), 0.0);
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public final void testPlacerBlockError() {
		p.setX(76);
		p.setY(576);
		p.placerBlock(1,19);
		assertEquals("cas où il n'y a rien dans l'inventaire qu'il ne peut rien poser", -1,
				Integer.parseInt(inventaire.getInventoryList().
						get(inventaire.getInventoryList().size()-1).getId()), 0.0);
	}
	
	@Test
	public final void testCalculationIndex() {
		//TAILLE_BLOC = 32 et TAILLE_LARGEUR_MAP = 60.
		//Le calcul consiste à faire (y/TAILLE_BLOC)*TAILLE_LARGEUR_MAP+(x/TAILLE_BLOC).
		//Soit cp(x,y) les coordonnées du personnage en pixels et i l'indice.
		assertEquals("cp(0,0) donc i=0", 0.0, p.calculationIndex(0,0), 0.0);
		assertEquals("cp(1152,576) donc i=1116", 1116.0, p.calculationIndex(1152,576), 0.0);
		assertEquals("cp(-128,3200) donc i=5996", 5996.0, p.calculationIndex(-128,3200), 0.0);
		assertEquals("cp(640,-32) donc i=-40", -40.0, p.calculationIndex(640,-32), 0.0);
		assertEquals("cp(-128,-32) donc i=-64", -64.0, p.calculationIndex(-128,-32), 0.0);
	}
	
	@Test
	public final void testPossibiliteAttaque() {
		//y non traité car l'ennemi suit forcément le joueur proche du y de l'ennemi.
		p.setY(576);
		ennemi.setY(544);
		p.setX(32);
		ennemi.setX(50);
		assertTrue("cas où l'ennemi est à la portée du joueur"
				+ "que x de p - taille des blocs > x de ennemi,"
				+ "que x de p + taille des blocs < x de ennemi"
				+ "et que x de p < x de ennemi"
				, p.possibiliteAttaque(ennemi));
		p.setX(32);
		ennemi.setX(20);
		assertTrue("cas où l'ennemi est à la portée du joueur"
				+ "que x de p - taille des blocs > x de ennemi"
				+ "que x de p + taille des blocs < x de ennemi"
				+ "et que x de p > x de ennemi"
				, p.possibiliteAttaque(ennemi));
		p.setX(32);
		ennemi.setX(64);
		assertTrue("cas où l'ennemi est à la portée du joueur"
				+ "que x de p - taille des blocs > x de ennemi"
				+ "que x de p + taille des blocs = x de ennemi"
				+ "et que x de p < x de ennemi"
				, p.possibiliteAttaque(ennemi));
		p.setX(64);
		ennemi.setX(32);
		assertTrue("cas où l'ennemi est à la portée du joueur"
				+ "que x de p - taille des blocs = x de ennemi"
				+ "que x de p + taille des blocs < x de ennemi"
				+ "et que x de p > x de ennemi"
				, p.possibiliteAttaque(ennemi));
		p.setX(32);
		ennemi.setX(32);
		assertTrue("cas où l'ennemi est à la portée du joueur"
				+ "que x de p - taille des blocs > x de ennemi"
				+ "que x de p + taille des blocs < x de ennemi"
				+ "et que x de p = x de ennemi"
				, p.possibiliteAttaque(ennemi));
		p.setX(640);
		ennemi.setX(128);
		assertFalse("cas où l'ennemi n'est pas à la portée du joueur"
				+ "que x de p - taille des blocs < x de ennemi"
				+ "que x de p + taille des blocs < x de ennemi"
				+ "et que x de p > x de ennemi"
				, p.possibiliteAttaque(ennemi));
		p.setX(128);
		ennemi.setX(640);
		assertFalse("cas où l'ennemi n'est pas à la portée du joueur"
				+ "que x de p - taille des blocs < x de ennemi"
				+ "que x de p + taille des blocs < x de ennemi"
				+ "et que x de p < x de ennemi"
				, p.possibiliteAttaque(ennemi));
	}
	
	@Test
	public final void testAttaque() {
		//Au départ, un personnage a 100 de PV.
		p.attaque(ennemi);
		assertEquals("cas où ennemi a plus de 25 de PV", 75.0, ennemi.getPV(), 0.0);
		ennemi.setPV(-50);
		p.attaque(ennemi);
		assertEquals("cas où le PV de l'ennemi = 25", 0.0 , ennemi.getPV(), 0.0);
		ennemi.setPV(5);
		p.attaque(ennemi);
		assertEquals("cas où ennemi a moins de 25 de PV", 0.0, ennemi.getPV(), 0.0);
		p.attaque(ennemi);
		assertEquals("cas où ennemi a 0 PV", 0.0, ennemi.getPV(), 0.0);
	}
	
}

