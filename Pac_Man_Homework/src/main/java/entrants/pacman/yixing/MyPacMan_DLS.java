package entrants.pacman.yixing;

import pacman.controllers.PacmanController;
import pacman.game.Constants.DM;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

import java.net.Inet4Address;
import java.util.*;

import java.util.Random;

/*
 * This class implement Depth Limited Search on Pac Man to eat pill, power pill and ghost
 * State space: every getMove() game maze
 * Initial state: current node index
 * Goal state: an edible pill, power pill and ghost
 */
public class MyPacMan_DLS extends PacmanController {
    private Random random = new Random();
    private static int MAX_DEPTH = 100;

    private int count = 0;
    private int random_count = 0;


    @Override
    public MOVE getMove(Game game, long timeDue) {

        // Should always be possible as we are PacMan
        int current = game.getPacmanCurrentNodeIndex();

        // data struct: stack
        Stack<PathNode> stack = new Stack<PathNode>();
        HashSet<Integer> visited = new HashSet<Integer>();

        // Strategy: Use a stack to implement Depth Limited Search. The object in the stack is PathNode to record the
        // node index, node search path and depth. Every time, pop a node to expansion. Push the successor nodes to stack
        // and update the node search path and depth. If it search a goal will end the search.
        // If the stack is empty, go to random step.
        stack.push(new PathNode(current, new ArrayList<MOVE>(),0));
        while(!stack.isEmpty()) {
            PathNode top = stack.pop();
            int topIndex = top.nodeIndex;
            ArrayList<Integer> topPath = top.path;

            if(top.depth > MAX_DEPTH) continue;

            if(visited.contains(topIndex)) continue;
            visited.add(topIndex);

            // check if this node index has an edible pill, power pill or ghost
            if(GoalHelper.isEdiblePill(game,topIndex)||GoalHelper.isEdiblePowerPill(game, topIndex)||
                    GoalHelper.isEdibleGhost(game, topIndex)) {
                MOVE nextMove = game.getNextMoveTowardsTarget(current, top.path.get(0), DM.PATH);
                if (game.getPacmanLastMoveMade().opposite() != nextMove) {
                    System.out.println("Go to search path direction");
                    count++;
                    return nextMove;
                } else{
                    continue;
                }
            }

            // random add successor nodes into stack
            int[] neighbours = game.getNeighbouringNodes(topIndex);
            ArrayList<Integer> neighboursArray = new ArrayList<Integer>();
            while(neighboursArray.size() != neighbours.length) {
                int randomIndex = random.nextInt(neighbours.length);
                if(!neighboursArray.contains(randomIndex)) {
                    ArrayList<Integer> newPath = new ArrayList<Integer>(topPath);
                    newPath.add(neighbours[randomIndex]);
                    stack.push(new PathNode(neighbours[randomIndex], newPath, top.depth + 1));
                    neighboursArray.add(randomIndex);
                }
            }

        }

        // if the stack is empty, go random move
        MOVE[] moves = game.getPossibleMoves(current, game.getPacmanLastMoveMade());
        if (moves.length > 0) {
//            System.out.println("Random step");
            random_count++;
            System.out.println("The ration of random is " + (random_count * 1.0/(count + random_count)));
            return moves[random.nextInt(moves.length)];
        }

        // Must be possible to turn around
        return game.getPacmanLastMoveMade().opposite();
    }


}
