/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.lang.reflect.Constructor;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.security.auth.Destroyable;
import javax.swing.*;


/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 * 
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

	public static int numVisible;
	private int seconds;
	private final static Tile[][] tiles = new Tile[10][10];

	private int numFruits = 9;
	private TreeMap<Integer, Integer> fruitIdxToNum = new TreeMap<>();
	private TreeMap<Integer, Tile> firstAppear = new TreeMap<>();
	public static int numSelected;
	public static Tile tileSelected1;
	public static Tile tileSelected2;
	public boolean playing = false; 
	private JLabel status; 

	private Timer timer = new Timer(INTERVAL, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			tick();
		}
	});
	// Game constants
	public static final int COURT_WIDTH = 600;
	public static final int COURT_HEIGHT = 600;
	public static final int INTERVAL = 1000;

	public GameCourt(JLabel status) {
		setBorder(BorderFactory.createLineBorder(Color.PINK));

		setFocusable(true);

		this.status = status;
		constructTiles();
	}

	private void constructTiles() {

		//construct the tiles randomly 
		for (int i = 0; i < numFruits; i++) {
			fruitIdxToNum.put(i, 0);
		}
		JButton button = new JButton();
		setLayout(new GridLayout(10,10));

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (i == 0 || j == 0 || i == 9 || j == 9) {
					tiles[i][j] = new Tile(0, i, j);

					button = tiles[i][j].getButton();
					this.add(button);
					tiles[i][j].setInvisible(); //visibility of tile from true to false


				} else {
					int x = (int) (Math.random() * numFruits); //x = current fruitIdx

					tiles[i][j] = new Tile(x, i, j);
					int val = fruitIdxToNum.remove(x);
					if (val == 0) {
						firstAppear.put(x, tiles[i][j]);
					}
					val++;
					fruitIdxToNum.put(x, val);
					button = tiles[i][j].getButton();
					this.add(button);
				}
			} 
		}
		//make sure all fruits have even amount of tiles
		for (int i = 0; i < numFruits - 1; i++) {
			int numfruit = fruitIdxToNum.get(i); //total num of current fruit
			boolean isEven = ((numfruit % 2) == 0);
			if (!isEven) {
				fruitIdxToNum.put(i, numfruit-1);

				//firstAppear
				Tile curr = firstAppear.get(i);
				curr.setIcn(i + 1);
				//update the number of fruit # i + 1
				int temp = fruitIdxToNum.remove(i+1);
				fruitIdxToNum.put(i+1, temp+1);
			}
		} //do not have to check the last i because it has to be even
		if (fruitIdxToNum.get(numFruits - 1) %2 != 0) throw new RuntimeException("not even numFruits"
				+ "in the end");

	}

	/**
	 * (Re-)set the game to its initial state.
	 */
	public void reset() {
		seconds = 0;
		numSelected = 0;
		tileSelected1 = null;
		tileSelected2 = null;
		numVisible = 32; //pairs
		this.removeAll();
		constructTiles();

		timer.start(); 
		playing = true;

	}

	/**
	 * This method is called every time the timer defined in the constructor
	 * triggers.
	 */
	void tick() {
		if (playing) {
			seconds++;
			status.setText("Time elapsed: " + seconds + " s");

			if (numVisible <= 0) {
				status.setFont(new Font("Courier New", Font.BOLD, 12));
				status.setText("You won!!!" + " You used " + seconds + " seconds.");
				Game.winWindow(seconds);
				playing = false;
			}
		} 
	}


	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}

	public static JButton getButtonFromTile (int fc, int sc) {
		return tiles[fc][sc].getButton();
	}


	public static boolean checkZeroTurn(int start, int end, boolean isVaryingFc, int c) {
		//c is the constant coordinate (the other one) 
		for (int i = start + 1; i < end; i++) {
			if (isVaryingFc) {

				if (tiles[i][c].getButton().isVisible()) return false;
			} else {
				if (tiles[c][i].getButton().isVisible()) return false;
			}
		}

		return true;
	}
}
