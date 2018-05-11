import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class Maze {
    private final static String START_CHAR = "S";
    private final static String END_CHAR = "E";
    private Scanner scanner;
    // 2 dimensional String array representing the maze
    private String[][] maze;

    //The height, width, starting and ending positions (x and y) of the maze
    private int  height, width, startPosX, startPosY, endPosX, endPosY;

    //constructor for maze
     public Maze(String filename){
        try {
            //read input file into scanner object
            scanner = new Scanner(new File(filename));
            String line = "";
            String[] lines;

            //determine height and width of maze as stated by first line in text file
            line = scanner.nextLine();
            lines = line.split(" ");
            this.width = Integer.parseInt(lines[0]);
            this.height = Integer.parseInt(lines[1]);

            this.maze = new String[this.height][this.width];


            // determine starting x and y positions in maze, as stated by second line in text file
            line = scanner.nextLine();
            lines = line.split(" ");
            this.startPosX = Integer.parseInt(lines[0]);
            this.startPosY = Integer.parseInt(lines[1]);

            // determine starting x and y positions in maze, as stated by third line in text file
            line = scanner.nextLine();
            lines = line.split(" ");
            this.endPosX = Integer.parseInt(lines[0]);
            this.endPosY = Integer.parseInt(lines[1]);


            // iterate through rest of text to determine the maze body
            for(int i=0; i<height; i++){
                line = scanner.nextLine();
                lines = line.split(" ");
                this.maze[i] = lines;
            }
            //if a file cannot be found then print the error
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            maze = null;
        }
    }

    /**
     * This method is used to solve the maze, if it is possible
     * @param xPos the x-coordinate of the grid
     * @param yPos  the y-coordinate of the grid
     * @return true if solvable, false otherwise
     */
    public boolean recursiveSolve(int xPos, int yPos){
        boolean solved = false;
        // if we reached base case (i.e. come to the end) return true
        if(xPos == this.endPosX && yPos == this.endPosY) return true;

        // markPos the current position of the maze
        markPos(xPos, yPos, MazeMain.PathChar);

        //check if moving North is valid, the position has not been already visited and the maze is not solved
        if(isValid(xPos, yPos-1) && !wasHere(xPos, yPos-1) && !solved){
            solved = recursiveSolve(xPos, yPos-1);
        }

        // check if moving East is valid, the position has not been already visited and the maze is not solved
        if(isValid(xPos+1, yPos) && !wasHere(xPos+1, yPos) && !solved){
            solved = recursiveSolve(xPos+1, yPos);
        }

        //check if moving South is valid, the position has not been already visited and the maze is not solved
        if(isValid(xPos, yPos+1) && !wasHere(xPos, yPos+1) && !solved){
            solved = recursiveSolve(xPos, yPos+1);
        }

        // check if moving West is valid, the position has not been already visited and the maze is not solved
        if(isValid(xPos-1, yPos) && !wasHere(xPos-1, yPos) && !solved){
            solved = recursiveSolve(xPos-1, yPos);
        }

        //if we are unable to reach the end from the current pos, mark the pos as 0, and return as the end is unreachable
        // from this pos
        if(!solved) markPos(xPos, yPos, MazeMain.PassChar);
        return solved;
    }

    public String makeToString(){
        markStartPos();
        markEndPos();

        String route = "";
        for(int i=0; i<this.maze.length; i++){
            for(int j=0; j< this.maze[i].length; j++){
                if(this.maze[i][j].equals(MazeMain.WallChar)){
                    route += "#";
                }else if(maze[i][j].equals(MazeMain.PassChar)){
                    route += " ";
                }else{
                    route += maze[i][j];
                }
            }
            if(i < this.maze.length -1)route += "\n";
        }
        return route;
    }

    //mark start position of the maze
    private void markStartPos(){
        this.maze[startPosY][startPosX] = START_CHAR;
    }

    //mark end position of the maze
    private void markEndPos(){
        this.maze[endPosY][endPosX] = END_CHAR;
    }

    public int getStartPosX(){
        return startPosX;
    }

    public int getStartPosY(){
        return startPosY;
    }

    /**
     * mark a specific maze grid with a given char
     *
     * @param xPos = x-coordinate of the grid to markPos
     * @param yPos = y-coordinate of the grid to markPos
     * @param mark - the Char used to mark the position
     */
    private void markPos(int xPos, int yPos, String mark){
        this.maze[yPos][xPos] = mark;
    }

    /**
     * This method is used to check is the position in the maze is valid
     * A position is considered valid if it is within the bounds of the maze
     * and it is not a wall of the maze
     * @param  xPos  the x-coordinate of the grid to check
     * @param  yPos  the y-coordinate of the grid to check
     * @return true if position is valid, false otherwise
     */
    private boolean isValid(int xPos, int yPos){
        if(xPos<0 || yPos<0 || xPos >= this.maze[0].length || yPos >= this.maze.length) return false;
        if(this.maze[yPos][xPos].equals(MazeMain.WallChar))return false;
        return true;
    }


    /**
     * Check if position has been visited
     *
     * @param xPos = x coordinate of the grid to check
     * @param  yPos = y coordinate of the grid to check
     * @return true if has been visited, false otherwise
     */
    private boolean wasHere(int xPos, int yPos){
        return this.maze[yPos][xPos].equals(MazeMain.PathChar);
    }

}
