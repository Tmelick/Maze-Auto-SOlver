import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;


public class TestMaze extends Application {
    public void start(Stage primaryStage) {
/*
===============================================
Loading Maze, and Creating maze object
===============================================
*/
        int[][] maze =  {{0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,1,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
                {0,0,1,0,0,0,0,0,0,0,1,1,1,1,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0},
                {0,0,1,1,1,1,1,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,1,0,0,0,0,0,0,0,1,1,1,1,0},
                {0,0,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,1,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0},
                {0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0},
                {0,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0},
                {0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
        maze[0][2] = 6;
        Maze testMaze = new Maze(maze);
        /*
===============================================
Creating the pane for the maze
===============================================
*/
        VBox pane = new VBox();
        Pane paneForMaze = new Pane();
        paneForMaze.getChildren().add(testMaze.displayMaze());
        paneForMaze.setStyle("-fx-background-color: black");


/*
===============================================
All the code for the menu pane.
===============================================
*/
        //Buttons
        Button btStart = new Button("Start");
        Button btQuit = new Button("Quit");
        btStart.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        //panes for Menu
        GridPane menu = new GridPane();
        VBox startMenu = new VBox();
        startMenu.getChildren().addAll(btStart, btQuit);
        menu.setAlignment(Pos.CENTER);
        //Audio for Menu
        AudioClip intro = new AudioClip(this.getClass().getResource("Images/pacman_beginning.wav").toExternalForm());
        intro.play();
        //Gif for menu
        ImageView imageView = new ImageView("Images/pacmanchase.gif");
        menu.getChildren().addAll(imageView, startMenu);
        //Button for start
        btStart.setOnAction(event -> {
            primaryStage.setScene(new Scene(pane, 550, 280));
            intro.stop();
        });
        //Button for quit
        btQuit.setOnAction(event -> {
            System.exit(0);
        });

/*
===============================================
Code for Buttons pane, Which is the maze, and the
buttons for the maze.
===============================================
*/
        // The death sound for finding cheating
        AudioClip findExit = new AudioClip(this.getClass().getResource("Images/pacman_death.wav").toExternalForm());
        //Creating the buttons
        Button btTakeAStep = new Button("Take A Step");
        Button btFindExit = new Button("Find Exit");
        //Pane for the buttons
        HBox paneForButtons = new HBox();
        paneForButtons.getChildren().addAll(btTakeAStep, btFindExit);
        paneForButtons.setSpacing(10);
        paneForButtons.setAlignment(Pos.CENTER);
        //Setting the background to match the maze's
        paneForButtons.setStyle("-fx-background-color: black");
        //Set on action for the buttons
        btTakeAStep.setOnAction(e -> {
            testMaze.takeAStep();

        });
        btFindExit.setOnAction(e -> {
            testMaze.findExit();
            findExit.play();

        });
/*
===============================================
Code for the main pane
===============================================
*/
        //Adds each pane to the main pain
        pane.getChildren().add(paneForMaze);
        pane.getChildren().add(paneForButtons);
        Scene scene = new Scene(menu, 300, 200);
        primaryStage.setTitle("Maze");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


}
