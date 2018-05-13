import java.io.File;


public class MazeMain {



    /**
     * @param args command line argument pointing to .txt file
     */
    public static void main(String[] args) {
        //if no arguments were given
        if (args.length == 0) {
            // tell user how to input file
            System.out.println("Enter filename e.g. 'file.txt' when running application");
            System.exit(0);
        }

        //first argument should be a file
        String filename = args[0];
        File file = new File(filename);

        Maze myMaze = null;

        if(file.isFile()){
            myMaze = new Maze(filename);
        }else{
            System.out.println("File not found.");
            System.exit(0);
        }

        if(myMaze.recursiveSolve(myMaze.getStartPosX(), myMaze.getStartPosY())){
            // print the maze with the output requirements - start as S, end as E, passage as ' ' and path as #
            System.out.println(myMaze.makeToString());
        }else{
            System.out.println("Maze is unsolvable.");
        }
    }
}