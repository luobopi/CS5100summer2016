import entrants.pacman.yixing.MyPacMan_DFS;
import entrants.pacman.yixing.MyPacMan_GBFS;
import examples.commGhosts.POCommGhosts;
import pacman.Executor;


/**
 * Created by pwillic on 06/05/2016.
 */
public class Main {

    public static void main(String[] args) {

        Executor executor = new Executor(false, true);

//        executor.runGameTimed(new POPacMan(), new POCommGhosts(50), true);
        // uninformed search
//        executor.runGameTimed(new MyPacMan_DFS(), new POCommGhosts(50), true);
        // informed search
        executor.runGameTimed(new MyPacMan_GBFS(), new POCommGhosts(50), true);
    }
}
