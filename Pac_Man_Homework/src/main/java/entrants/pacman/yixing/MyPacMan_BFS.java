package entrants.pacman.yixing;

import pacman.controllers.PacmanController;
import pacman.game.Constants;
import pacman.game.Game;

import java.util.*;

/**
 * Created by yixing on 7/18/16.
 * This class implement BFS algorithm for Pac Man.
 * The Initial state is current node index.
 * The goal of problem solving is to find a available pills to eat.
 * The function is Breadth First Search
 */
public class MyPacMan_BFS extends PacmanController {

    @Override
    public Constants.MOVE getMove(Game game, long timeDue) {

        // Should always be possible as we are PacMan
        int current = game.getPacmanCurrentNodeIndex();

        // Strategy: Go after the pills and power pills that we can see use BFS algorithms. Used queue to record the
        // traversal level nodes.

        // initialize data structure
        Queue<PathNode> queue= new LinkedList<PathNode>();
        HashSet<Integer> visited = new HashSet<Integer>();

        // put current node index into queue and start BFS
        queue.add(new PathNode(current, new ArrayList<Constants.MOVE>()));
        while(!queue.isEmpty()) {
            PathNode top = queue.remove();
            int topIndex = top.nodeIndex;
            ArrayList<Constants.MOVE> topPath = top.path;
            if(visited.contains(topIndex)) continue;
            visited.add(topIndex);

            // check if this node index has a active pill
            if(game.getPillIndex(topIndex)!= -1 || game.getPowerPillIndex(topIndex) != -1) {
                int pillIndex = game.getPillIndex(topIndex);
                int powerPillIndex = game.getPowerPillIndex(topIndex);
                System.out.println("The pill index is " + pillIndex + " The power pill index is " + powerPillIndex);

                // Find a available pill or power pill and go to that point with path built via search
                if(pillIndex != -1) {
                    System.out.println("The pill is still available" + game.isPillStillAvailable(pillIndex));
                    if(game.isPillStillAvailable(pillIndex)!=null && game.isPillStillAvailable(pillIndex)) {
                        return topPath.get(0);
                    }
                } else if (powerPillIndex != -1) {
                    System.out.println("The power pill is still available" + game.isPowerPillStillAvailable(powerPillIndex));
                    if(game.isPowerPillStillAvailable(powerPillIndex)!=null && game.isPowerPillStillAvailable(powerPillIndex)) {
//                        return game.getNextMoveTowardsTarget(current, topIndex, Constants.DM.PATH);
                        return topPath.get(0);
                    }
                }
            }

            // put node successor nodes into queue.
            int[] neighbours = game.getNeighbouringNodes(topIndex);
            for(int i = 0; i < neighbours.length; i++) {
                Constants.MOVE direction = game.getNextMoveTowardsTarget(topIndex, neighbours[i], Constants.DM.PATH);
                ArrayList<Constants.MOVE> newPath = new ArrayList<Constants.MOVE>(topPath);
                newPath.add(direction);
                queue.add(new PathNode(neighbours[i], newPath));
            }
        }

        // Must be possible to turn around
        return game.getPacmanLastMoveMade().opposite();
    }
}
