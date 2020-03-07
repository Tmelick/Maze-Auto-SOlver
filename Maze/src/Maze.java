import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.media.AudioClip;

import java.util.Random;


public class Maze {
    private GridPane paneForMaze = new GridPane();
    /*
===============================================
Created all the images, and sounds used
===============================================
*/
    private Image rat = new Image("Images/PacmanBlackBackGround.jpg", 15, 15, false, true);
    private Image ghost = new Image("Images/Ghost1.png", 15, 15, false, true);
    private Image ghost2 = new Image("Images/Ghost2.png", 15, 15, false, true);
    private Image ghost3 = new Image("Images/Ghost3.png", 15, 15, false, true);
    private Image ghost4 = new Image("Images/Ghost4.png", 15, 15, false, true);
    private Image dot = new Image("Images/bigwhitedot.png", 5, 5, false, true);
    private Image blank = new Image("Images/BlackSpace.jpg", 6, 6, false, true);
    private Image win = new Image("Images/WinNothing.jpg", 550, 265, false, true);
    private ImageView ivWin = new ImageView(win);
    AudioClip myStep = new AudioClip(this.getClass().getResource("Images/pacman_chomp.wav").toExternalForm());
    AudioClip winNothing = new AudioClip(this.getClass().getResource("Images/030904.mp3").toExternalForm());
    //Creates the maze used
    private int[][] maze;
    // Creates solved variable
    private boolean solved = false;

    //Constructor for the maze maze class
    public Maze(int[][] maze) {
        this.maze = maze;
    }
    /*
   ===============================================
   This method loads the random picture for the
   ghosts choosing between 4 different pictures
   ===============================================
   */
            public Image choseGhost(){
        Random rand = new Random();
        int random = rand.nextInt((4-1) + 1) + 1;
        switch(random){
            case 1:
                return ghost;
            case 2:
                return ghost2;
            case 3:
                return ghost3;
            case 4:
                return ghost4;
        }
        return ghost;
    }
    /*
   ===============================================
   Method that changes all the numbers in the
   array to Images.
   ===============================================
   */
    public GridPane displayMaze() {
        paneForMaze.getChildren().clear();
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                 if (maze[i][j] == 1)
                    paneForMaze.add(new ImageView(dot), j, i);
                else if (maze[i][j] == 6)
                    paneForMaze.add(new ImageView(rat), j, i);
                else if (maze[i][j] == 5)
                    paneForMaze.add(new ImageView(blank), j, i);
                else if (maze[i][j] == 4)
                    paneForMaze.add(new ImageView(blank), j, i);

                else if  (maze[i][j] == 0)
                    paneForMaze.add(new ImageView(choseGhost()), j, i);
            }
        }
        return paneForMaze;
    }

    /*
   ===============================================
   findExit method that is a continuous loop of
   the take a step method. It uses a Try and catch
   block. When the maze is completed it gets an
   index out of bounds error which ends the loop
   and just displays the maze
   ===============================================
   */
    public void findExit() {
        try {
            for (int count = 0; count < 1000; count++) {
                int row = 0;
                int col = 0;
                for (int i = 0; i < maze.length; i++) {
                    for (int j = 0; j < maze[i].length; j++) {
                        if (maze[i][j] == 6) {
                            row = i;
                            col = j;
                        }
                    }
                }
                // Find the rat in the maze
                if ((row == maze.length - 1 || col == maze[0].length - 1 || (row == 0 && col != 2) || col == 0)) {
                    solved = true;
                }

                // The base cases
                if (maze[row][col - 1] == 1) {
                    if (maze[row][col + 1] == 5) {
                        if (maze[row - 1][col] != 0 && maze[row - 1][col] != 5 && maze[row - 1][col] != 4) {
                            maze[row - 1][col] = 6;
                            maze[row][col] = 5;
                        } else {
                            maze[row][col - 1] = 6;
                            maze[row][col] = 5;
                        }

                    } else if (maze[row + 1][col] == 5) {
                        if (maze[row][col + 1] != 0 && maze[row][col + 1] != 5 && maze[row][col + 1] != 4) {
                            maze[row][col + 1] = 6;
                            maze[row][col] = 5;
                        } else if (maze[row - 1][col] != 0 && maze[row - 1][col] != 5) {
                            maze[row - 1][col] = 6;
                            maze[row][col] = 5;
                        } else {
                            maze[row][col - 1] = 6;
                            maze[row][col] = 5;
                        }


                    } else {
                        maze[row][col - 1] = 6;
                        maze[row][col] = 5;
                    }

                } else if (maze[row + 1][col] == 1) {
                    maze[row + 1][col] = 6;
                    maze[row][col] = 5;
                } else if (maze[row][col + 1] == 1) {
                    maze[row][col + 1] = 6;
                    maze[row][col] = 5;
                } else if (maze[row - 1][col] == 1) {
                    maze[row - 1][col] = 6;
                    maze[row][col] = 5;
                }
                // This part makes it go back words when it hits a wall
                else {
                    if (maze[row][col - 1] == 5) {
                        if (maze[row - 1][col] != 0 && maze[row - 1][col] != 4) {
                            maze[row - 1][col] = 6;
                            maze[row][col] = 4;
                        } else {
                            maze[row][col - 1] = 6;
                            maze[row][col] = 4;
                        }
                    } else if (maze[row + 1][col] == 5) {
                        if (maze[row][col + 1] == 4) {
                            if (maze[row - 1][col] != 0 && maze[row - 1][col] != 4) {
                                maze[row - 1][col] = 6;
                                maze[row][col] = 4;

                            } else {
                                maze[row + 1][col] = 6;
                                maze[row][col] = 4;

                            }
                        } else {
                            maze[row + 1][col] = 6;
                            maze[row][col] = 4;
                        }
                    } else if (maze[row][col + 1] == 5) {
                        maze[row][col + 1] = 6;
                        maze[row][col] = 4;
                    } else if (maze[row - 1][col] == 5) {
                        maze[row - 1][col] = 6;
                        maze[row][col] = 4;
                    }
                }

            }
        } catch (Exception e) {
            displayMaze();
        }
    }
/*
==================================================
Take a step method. The basic logic behind this
method is that it if finds the rat in maze on
each calling. Once found it looks to the left
then bottom, then right, then top, for the 1.
Priority being in that order. If it can't find
a 1 then it looks for a 5. The rat leaves
behind 5s as it goes through the maze. Then
uses these 5s to backtrack from a dead end.
The logic is hard wired to always follow the left
wall. So lets say you are moving up and you need
to go right instead of left, because left has
the priority, What it does is check below it
, and if it is a 5 then it goes up, and if below
is a 5 and the step it would take is a 0, a 5
already, or the path right is a 1 it goes to the right.
So it follows the wall. It uses similar logic
for all the directions. Since left is the priority
most of the if statements are within the left move.
The only other major part of the takeAStep method
is that it uses a try and catch block as well,
but when it gets the error it changes the pane
to a sudo victory screen.
=======================================================
*/
    public void takeAStep() {
            myStep.stop();
        if(solved == false)
            myStep.play();
        try {
            // Find the rat in the maze
            int row = 0;
            int col = 0;
            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[i].length; j++) {
                    if (maze[i][j] == 6) {
                        row = i;
                        col = j;
                    }
                }
            }
            if ((row == maze.length - 1 || col == maze[0].length - 1 || (row == 0 && col != 2) || col == 0)) {
                solved = true;
            }

            // The base cases
            if (maze[row][col - 1] == 1) {
                if (maze[row][col + 1] == 5) {
                    if (maze[row - 1][col] != 0 && maze[row - 1][col] != 5 && maze[row - 1][col] != 4) {
                        maze[row - 1][col] = 6;
                        maze[row][col] = 5;
                        displayMaze();
                    } else {
                        maze[row][col - 1] = 6;
                        maze[row][col] = 5;
                        displayMaze();
                    }

                } else if (maze[row + 1][col] == 5) {
                    if (maze[row][col + 1] != 0 && maze[row][col + 1] != 5 && maze[row][col + 1] != 4) {
                        maze[row][col + 1] = 6;
                        maze[row][col] = 5;
                        displayMaze();
                    } else if (maze[row - 1][col] != 0 && maze[row - 1][col] != 5) {
                        maze[row - 1][col] = 6;
                        maze[row][col] = 5;
                        displayMaze();
                    } else {
                        maze[row][col - 1] = 6;
                        maze[row][col] = 5;
                        displayMaze();
                    }


                } else {
                    maze[row][col - 1] = 6;
                    maze[row][col] = 5;
                    displayMaze();
                }

            } else if (maze[row + 1][col] == 1) {
                if( maze[row][col + 1] == 5 && maze[row-1][col] == 1){
                    maze[row - 1][col] = 6;
                    maze[row][col] = 5;
                    displayMaze();
                }
                else {
                    maze[row + 1][col] = 6;
                    maze[row][col] = 5;
                    displayMaze();
                }
            } else if (maze[row][col + 1] == 1) {
                maze[row][col + 1] = 6;
                maze[row][col] = 5;
                displayMaze();
            } else if (maze[row - 1][col] == 1) {
                maze[row - 1][col] = 6;
                maze[row][col] = 5;
                displayMaze();
            }
            // This part makes it go back words when it hits a wall
            else {
                if (maze[row][col - 1] == 5) {
                    if (maze[row - 1][col] != 0 && maze[row - 1][col] != 4) {
                        maze[row - 1][col] = 6;
                        maze[row][col] = 4;
                        displayMaze();
                    } else {
                        maze[row][col - 1] = 6;
                        maze[row][col] = 4;
                        displayMaze();
                    }
                } else if (maze[row + 1][col] == 5) {
                    if (maze[row][col + 1] == 4) {
                        if (maze[row - 1][col] != 0 && maze[row - 1][col] != 4) {
                            maze[row - 1][col] = 6;
                            maze[row][col] = 4;
                            displayMaze();
                        } else {
                            maze[row + 1][col] = 6;
                            maze[row][col] = 4;
                            displayMaze();
                        }
                    } else {
                        maze[row + 1][col] = 6;
                        maze[row][col] = 4;
                        displayMaze();
                    }
                } else if (maze[row][col + 1] == 5) {
                    maze[row][col + 1] = 6;
                    maze[row][col] = 4;
                    displayMaze();
                } else if (maze[row - 1][col] == 5) {
                    maze[row - 1][col] = 6;
                    maze[row][col] = 4;
                    displayMaze();
                }
            }

        } catch (Exception e) {
            myStep.stop();
            if (solved = true) {
                paneForMaze.getChildren().clear();
                paneForMaze.getChildren().add(ivWin);
                winNothing.play();
            }
        }
    }

}