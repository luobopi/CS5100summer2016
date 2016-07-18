package entrants.pacman.yixing;

import pacman.controllers.PacmanController;
import pacman.game.Constants;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.internal.*;
import pacman.game.util.*;

import java.util.ArrayList;
import java.util.*;
import java.util.Arrays;
import java.util.Random;

/*
 * This is the class you need to modify for your entry. In particular, you need to
 * fill in the getMove() method. Any additional classes you write should either
 * be placed in this package or sub-packages (e.g., entrants.pacman.yixing).
 */
public class MyPacMan_DFS extends PacmanController {
    private static final int MIN_DISTANCE = 20;
    private MOVE myMove = MOVE.NEUTRAL;
    private Random random = new Random();
    private Stack stack = new Stack<>();
    private HashSet visited = new HashSet();

    public MOVE getMove(Game game, long timeDue) {

        // Should always be possible as we are PacMan
        int current = game.getPacmanCurrentNodeIndex();

        // Strategy 1: Adjusted for PO
//        for (Constants.GHOST ghost : Constants.GHOST.values()) {
//            // If can't see these will be -1 so all fine there
//            if (game.getGhostEdibleTime(ghost) == 0 && game.getGhostLairTime(ghost) == 0) {
//                int ghostLocation = game.getGhostCurrentNodeIndex(ghost);
//                if (ghostLocation != -1) {
//                    if (game.getShortestPathDistance(current, ghostLocation) < MIN_DISTANCE) {
//                        return game.getNextMoveAwayFromTarget(current, ghostLocation, Constants.DM.PATH);
//                    }
//                }
//            }
//        }
//
//        /// Strategy 2: Find nearest edible ghost and go after them
//        int minDistance = Integer.MAX_VALUE;
//        Constants.GHOST minGhost = null;
//        for (Constants.GHOST ghost : Constants.GHOST.values()) {
//            // If it is > 0 then it is visible so no more PO checks
//            if (game.getGhostEdibleTime(ghost) > 0) {
//                int distance = game.getShortestPathDistance(current, game.getGhostCurrentNodeIndex(ghost));
//
//                if (distance < minDistance) {
//                    minDistance = distance;
//                    minGhost = ghost;
//                }
//            }
//        }
//
//        if (minGhost != null) {
//            return game.getNextMoveTowardsTarget(current, game.getGhostCurrentNodeIndex(minGhost), Constants.DM.PATH);
//        }

        // Strategy 3: Go after the pills and power pills that we can see use DFS algorithms.

        int[] neighbours = game.getNeighbouringNodes(current);

        for(int i = 0; i < neighbours.length; i++) {
            if(!visited.contains(neighbours[i])) {
                visited.add(neighbours[i]);
                stack.push(current);
                return game.getNextMoveTowardsTarget(current, neighbours[i], Constants.DM.PATH);
            }
        }

        while(!stack.isEmpty()) {
            int top = (Integer) stack.pop();
            return game.getNextMoveTowardsTarget(current, top, Constants.DM.PATH);
        }



        //Strategy 4: New PO strategy as now S3 can fail if nothing you can see
        //Going to pick a random action here
//        MOVE[] moves = game.getPossibleMoves(current, game.getPacmanLastMoveMade());
//        if (moves.length > 0) {
//            return moves[random.nextInt(moves.length)];
//        }
        // Must be possible to turn around
        return game.getPacmanLastMoveMade().opposite();
    }


}
