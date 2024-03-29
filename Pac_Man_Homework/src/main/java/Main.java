import entrants.pacman.yixing.*;
import examples.commGhosts.POCommGhosts;
import pacman.Executor;
import pacman.controllers.examples.StarterGhosts;


/**
 * Created by pwillic on 06/05/2016.
 */
public class Main {

    public static void main(String[] args) {

        Executor executor = new Executor(false, true);

//        executor.runGameTimed(new POPacMan(), new POCommGhosts(50), true);

        // HW2 implemented uniformed search and informed search
        // uninformed search
//        executor.runGameTimed(new MyPacMan_BFS(), new POCommGhosts(50), true);
//        executor.runGameTimed(new MyPacMan_DLS(), new POCommGhosts(50), true);
        // informed search
//        executor.runGameTimed(new MyPacMan_AStar(), new POCommGhosts(50), true);
//        executor.runGameTimed(new MyPacMan_GBFS(), new POCommGhosts(50), true);
        // HW3 implemented adevarsrial search
        // alpha beta pruning
        executor.runGameTimed(new MyPacMan_AlphaBetaPurning(), new POCommGhosts(50), true);
    }
}
