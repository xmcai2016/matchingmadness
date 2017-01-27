/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

// imports necessary libraries for Java swing
import java.awt.*;

import java.awt.event.*;

import javax.swing.*;

import java.lang.Exception;
/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
	private static String username = "Anonymous";
	public void run() {
		// NOTE : recall that the 'final' keyword notes inmutability
		// even for local variables.

		//Prompt
		final JFrame firstFrame = new JFrame("Welcome to Match Madness");
	
		firstFrame.setLocation(300, 300);
		final JPanel welcome = new JPanel();
		firstFrame.add(new CoverImage(), BorderLayout.CENTER);
		final JPanel ins = new JPanel();
		final JLabel instructions = new JLabel("<html>  <font color='orange'><font size='7'>"
				+ "Instructions: <font size='3'>"
				+ "<font color='green'> <br> For each pair of tiles, "
				+ "they can be eliminated if and only if the <br>following conditions are met:"
				+ "<br>1. they are of the same image; <br>2. they can be connected using a line "
				+ "that has a maximum of 1 turn,  <br>with the exception of the case where both tiles "
				+ "are on the border  <br>of the original 8x8 grid, in which case two turns are "
				+ "allowed;"
				+ "<br>3. the imaginary line connecting the two tiles cannot go through  <br>any "
				+ "existing tiles.");
		
		final JButton play = new JButton("PLAY");
		final JButton setName = new JButton("Set Your Name");
		final JButton highscores = new JButton("HIGH SCORES");
		
		final JPanel twoButtons = new JPanel();
		ins.add(instructions);
		firstFrame.add(ins, BorderLayout.SOUTH); 

		twoButtons.add(setName);
		twoButtons.add(play);
		twoButtons.add(highscores);
		twoButtons.setVisible(true);

		firstFrame.add(twoButtons,BorderLayout.NORTH);
		
		welcome.add(instructions);
		firstFrame.add(welcome,BorderLayout.SOUTH);
		firstFrame.setSize(450,600);

		
		firstFrame.setVisible(true);
		setName.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				final JTextField text = new JTextField("Anonymous");
				final JPanel setPanel = new JPanel();
				final JButton set = new JButton("Set");
				final JFrame fra = new JFrame("Set Your Username");
				setPanel.add(set);
				fra.add(text);
				fra.add(setPanel, BorderLayout.SOUTH);
				fra.setLocation(400,400);
				fra.setSize(300, 120);
				fra.setVisible(true);
				set.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						username = text.getText();
						fra.setVisible(false);
					}
				});
			}
		});
		highscores.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				//high scores page
				try {new HighScores();} catch (Exception ex) {
					System.out.println("error in generating HighScores -- in Game");
					};
				final JLabel hsLable = new JLabel(HighScores.getHighscores());
				final JFrame hsFrame = new JFrame("HIGH SCORES");
				final JPanel hsPanel = new JPanel();
				hsLable.setOpaque(false);
				hsPanel.add(hsLable);
				hsFrame.add(hsPanel, BorderLayout.CENTER);
				hsFrame.setSize(350, 400);
				hsPanel.setBackground(Color.BLACK);
				hsFrame.setVisible(true);
				
				
			}
		});
		play.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				firstFrame.setVisible(false);
				final JFrame frame = new JFrame(username + "'s Match Madness");
				frame.setLocation(10, 10);
				// Status panel
				final JPanel status_panel = new JPanel();
				frame.add(status_panel, BorderLayout.SOUTH);
				final JLabel status = new JLabel("Time elapsed: 0 s");
				status_panel.add(status);

				// Main playing area
				final GameCourt court = new GameCourt(status);
				frame.add(court, BorderLayout.CENTER);

				final JPanel control_panel = new JPanel();
				frame.add(control_panel, BorderLayout.NORTH);
				

				final JButton reset = new JButton("Reset");
				reset.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						court.reset();
					}
				});
				control_panel.add(reset);

				// Put the frame on the screen
			
				frame.pack();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				frame.setSize(1000, 1800);
				frame.setVisible(true);

				// Start game
				court.reset();
				
			}
		});

		
	}
	
	//displays the win window and calculates if we should put the user in the high scores
	public static void winWindow(int seconds) {
		final JFrame win = new JFrame("YOU WON!!!!!!!!");

		win.setSize(200, 550);
		win.setLocation(350,350);
		final JLabel label; 
		
		if ((seconds < HighScores.getHighestSeconds()) || (HighScores.getDocSize() < 10)) {

			label = new JLabel("<html> <p align='CENTER'> <font color='Green'>"
					+ "<font size='7'>" +
			"YOU USED " + seconds + " SECONDS TO FINISH <br> AND MADE IT TO THE "
					+ "<br>TOP TEN HIGH SCORES CHART!!!");
			HighScores.updateHighscores(seconds, username);
			
		} else {

			label = new JLabel("<html> <p align='CENTER'> <font color='Green'>"+
			"Congrats! You used " + seconds + " seconds to finish.");
		}

		label.setOpaque(false);
		win.add(label, BorderLayout.CENTER);

		win.setBackground(Color.PINK);
		win.setVisible(true);
	}


	/*
	 * Main method run to start and run the game Initializes the GUI elements
	 * specified in Game and runs it IMPORTANT: Do NOT delete! You MUST include
	 * this in the final submission of your game.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Game());
	}
}
