import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Tile extends JButton {
	private int firCo;
	private int secCo;
	private int iconIdx;
	private ImageIcon icn;
	private boolean visible;
	private ActionListener listener;
	private JButton button;

	private static final String PEACH = "peach.jpeg";
	private static final String APPLE = "apple.jpeg";
	private static final String KIWI = "kiwi.jpeg";
	private static final String GRAPES = "grapes.jpeg";
	private static final String COCONUT = "coconut.jpeg";
	private static final String ORANGE = "orange.jpeg";
	private static final String LEMON = "lemon.jpeg";
	private static final String PINEAPPLE = "pineapple.jpeg";
	private static final String STRAWBERRY = "strawberry.jpeg";

	private final String[] PICTURE_LIST = new String[] {PEACH,APPLE,KIWI,GRAPES,COCONUT,ORANGE,LEMON,PINEAPPLE,STRAWBERRY};
	Border thickBorder = new LineBorder(Color.PINK, 5);
	Border defaultBorder = new JButton().getBorder();
	public Tile (int x, int firCo, int secCo) {
		this.firCo = firCo; //coordinates
		this.secCo = secCo;
		iconIdx = x;
		button = new JButton();
		button.setPreferredSize(new Dimension(50, 50));

		visible = true;
		icn = new ImageIcon (PICTURE_LIST[x]);

		button.setIcon(icn);
		final Tile myself = this;
		listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//first tile
				if (GameCourt.numSelected < 1) {
					GameCourt.numSelected = 1;
					GameCourt.tileSelected1 = myself;

					toggleButtonThickness(button);
				}
				//second tile
				else if (GameCourt.numSelected >= 1) {
					Tile t1 = GameCourt.tileSelected1;
					//different image 
					if (myself.getIconIdxOfTile() != t1.getIconIdxOfTile()) {
						t1.getButton().setBorder(defaultBorder);
						GameCourt.tileSelected1= myself;
						toggleButtonThickness(button);

					} else { //same image
						//if it's myself 
						if (myself.getFirCo() == t1.getFirCo() && myself.getSecCo() == t1.getSecCo()) {

							toggleButtonThickness(button);
							GameCourt.numSelected = 0;
							
						} else {
							//calculate the shortest path and eliminate the two tiles if the path 
							//is within two turns 
							GameCourt.tileSelected2 = myself;
						
							toggleButtonThickness(button);

							int t1fc = t1.getFirCo();
							int t1sc = t1.getSecCo();
							int t2fc = myself.getFirCo();
							int t2sc = myself.getSecCo();
							boolean sameFc = t1fc - t2fc == 0;
							boolean sameSc = t1sc - t2sc ==0;

							//case 1: if the two tiles are next to each other
							if ((Math.abs(t1fc - t2fc) == 1 && sameSc) || 
									(Math.abs(t1sc - t2sc) == 1 && sameFc)) {
								t1.setInvisible();
								myself.setInvisible();
								GameCourt.numSelected = 0;
								GameCourt.numVisible--;
							} 
							//case 2: if the two tiles are on in the same row/col and on the outside
							else if ((sameFc && (t1fc == 1 || t1fc == 8)) || 
									(sameSc && (t1sc == 1 || t1sc == 8))) {
								t1.setInvisible();
								myself.setInvisible();
								GameCourt.numSelected = 0;

								GameCourt.numVisible--;
							}
							//case 3: if a straight line connects the two tiles (zero turns)
							else if (sameFc || sameSc) {
								boolean isValid;
								if (sameFc)	{

									int start = Math.min(t1sc, t2sc);
									int end = Math.max(t1sc, t2sc);
									isValid = GameCourt.checkZeroTurn(start, end, false, t1fc);

								}
								else {

									int start = Math.min(t1fc, t2fc);
									int end = Math.max(t1fc, t2fc);
									isValid = GameCourt.checkZeroTurn(start, end, true, t1sc);
								} 
								if (isValid) {
									t1.setInvisible();
									myself.setInvisible();
									GameCourt.numSelected = 0;
									GameCourt.numVisible--;
								} else {
									t1.getButton().setBorder(defaultBorder);
									GameCourt.tileSelected1= myself;
									button.setBorder(thickBorder);
								}
							} else { //two tiles in different columns and rows
								//case 4: one turn
								int startfc = Math.min(t1fc, t2fc);
								int startsc = Math.min(t1sc, t2sc);
								int endfc = Math.max(t1fc, t2fc);
								int endsc = Math.max(t1sc, t2sc);
								boolean isValidPath;
								//determine if the two tiles are in the northwest- southeast direction,
								//or southwest - northeast 
								boolean isSW = ((t1fc > t2fc) && (t1sc < t2sc)) 
										|| ((t2fc > t1fc) && (t2sc < t1sc));
								if (!isSW) {
									isValidPath = 
											(GameCourt.checkZeroTurn(startsc, endsc + 1, false, startfc) && 
													GameCourt.checkZeroTurn(startfc - 1, endfc, true, endsc)) ||
											(GameCourt.checkZeroTurn(startsc - 1, endsc, false, endfc) &&
													GameCourt.checkZeroTurn(startfc, endfc + 1, true, startsc));
								} else {
									isValidPath = 
											(GameCourt.checkZeroTurn(startsc - 1, endsc, false, startfc) &&
													GameCourt.checkZeroTurn(startfc - 1, endfc, true, startsc)) ||
											(GameCourt.checkZeroTurn(startsc, endsc + 1, false, endfc) &&
													GameCourt.checkZeroTurn(startfc, endfc + 1, true, endsc));
								} 
								if (isValidPath) {
									t1.setInvisible();
									myself.setInvisible();
									GameCourt.numSelected = 0;
									GameCourt.numVisible--;
								} else {
									t1.getButton().setBorder(defaultBorder);
									GameCourt.tileSelected1= myself;
									button.setBorder(thickBorder);
								}
							}
						}
					}
				}
			}
		};
		//if the button is selected, thicken the border
		button.addActionListener(listener);

	}
	public boolean getVisibility() {
		return visible;
	}
	public boolean setInvisible() {

		button.setVisible(false);
		visible = false;
		return visible;
	}
	public void toggleButtonThickness(JButton button) {
		if (button.getBorder() != thickBorder) {
			button.setBorder(thickBorder);
		} else {
			button.setBorder(defaultBorder);
		}
	}
	public JButton getButton() {
		return button;
	}
	public int getIconIdxOfTile() {
		return iconIdx;
	}
	public int getFirCo() {
		return firCo;
	}
	public int getSecCo() {
		return secCo;
	}
	public void setIcn(int idx) {
		icn = new ImageIcon (PICTURE_LIST[idx]);
		button.setIcon(icn);
		iconIdx = idx;
	}
}
