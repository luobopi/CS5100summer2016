package entrants.pacman.yixing;

import pacman.controllers.PacmanController;
import pacman.game.Constants.*;
import pacman.game.Game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by yixing on 7/19/16.
 * This class implemented Breadth-First search algorithm on Pac Man to eat pills and ghost
 * The initial state is current game state
 * The goal is to find an edible eat object, could be pill, power pill or ghost
 * The state space is the game maze
 */
public class MyPacMan_BFS extends PacmanController {
    private static final int MIN_DISTANCE = 20;

    @Override
    public MOVE getMove(Game game, long timeDue) {

        // Should always be possible as we are PacMan
        int current = game.getPacmanCurrentNodeIndex();

        // Strategy: Go after the pills, power pills and edible ghost that we can see use BFS algorithms.
        // Used queue to record traversal successor nodes. select a node in queue for expansion and build
        // new PathNode objects to store the traversal path and put it into queue.

        // initialize data structure
        Queue<PathNode> queue= new LinkedList<PathNode>();
        HashSet<Integer> visited = new HashSet<Integer>();

        // put current node index into queue and start BFS
        queue.add(new PathNode(current, new ArrayList<Integer>()));
        while(!queue.isEmpty()) {
            PathNode top = queue.remove();
            int topIndex = top.nodeIndex;
            ArrayList<Integer> topPath = top.path;
            if(visited.contains(topIndex)) continue;
            visited.add(topIndex);

            // check if this node index is the goal. If it is a goal, the search ends. return
            // the goal search path and turn to the first steop.
            if(GoalHelper.isEdiblePill(game, topIndex) || GoalHelper.isEdiblePowerPill(game, topIndex) ||
            GoalHelper.isEdibleGhost(game, topIndex)) {
                return game.getNextMoveTowardsTarget(current, topPath.get(0), DM.PATH);
            }

            // put node successor nodes into queue.
            int[] neighbours = game.getNeighbouringNodes(topIndex);
            for(int i = 0; i < neighbours.length; i++) {
//                Constants.MOVE direction = game.getNextMoveTowardsTarget(topIndex, neighbours[i], Constants.DM.PATH);
                ArrayList<Integer> newPath = new ArrayList<Integer>(topPath);
                newPath.add(neighbours[i]);
                queue.add(new PathNode(neighbours[i], newPath));
            }
        }

        // Must be possible to turn around
        return game.getPacmanLastMoveMade().opposite();
    }

}
