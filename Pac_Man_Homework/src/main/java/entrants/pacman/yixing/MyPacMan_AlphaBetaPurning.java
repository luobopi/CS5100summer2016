package entrants.pacman.yixing;

import pacman.controllers.PacmanController;
import pacman.game.Constants;
import pacman.game.Constants.MOVE;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.DM;
import pacman.game.Game;



import java.util.*;

/**
 * This class implement alpha-beta pruning algorithm on PacMan.
 * The state space: the current maze
 * The initial state: the current pac man location
 * The final goal: maximize pac man score and minimize ghosts score.
 */
public class MyPacMan_AlphaBetaPurning extends PacmanController{
    private static int DEPTH = 4;
    private MOVE lastlastMove;
    private int closetPillIndex = -1;

    /**
     * This function return the move direction of pacman
     * @param game
     * @param timeDue
     * @return MOVE the next move
     */
    @Override
    public MOVE getMove(Game game, long timeDue) {

        // Should always be possible as we are PacMan
        closetPillIndex = findClosetNodeIndex(game);
        System.out.println("The closet pill index is" + closetPillIndex);
        int current = game.getPacmanCurrentNodeIndex();
        int minDistance = Integer.MAX_VALUE;
        for (GHOST ghost : GHOST.values()) {
            // If can't see these will be -1 so all fine there
            if (game.getGhostEdibleTime(ghost) == 0 && game.getGhostLairTime(ghost) == 0) {
                int ghostLocation = game.getGhostCurrentNodeIndex(ghost);
                if (ghostLocation != -1) {
//                    if (game.getShortestPathDistance(current, ghostLocation) < minDistance) {
//
//                    }
                    minDistance = Math.min(minDistance, game.getManhattanDistance(current, ghostLocation));
                }
            }
        }
        if(minDistance > 20) {
            System.out.println("The min ghost distance is" + minDistance);
            return getBFSMove(game);
        }

        closetPillIndex = findClosetNodeIndex(game);
        System.out.println("The ghost is approching! The min ghost distance is" + minDistance);
        return alphaBetaAction(game);
    }

    /**
     * This function return the max value of minimizer layer. Use alpha and beta to pruning
     * When it reach the terminal situation, will return heuristic score
     * Otherwise will get maximize value of next min layer
     * @param game the update game
     * @param alpha
     * @param beta
     * @param depth the tree level
     * @return max score
     */
    private double maxValue(Game game, double alpha, double beta, int depth) {
        double v = Integer.MIN_VALUE;
        if(game.wasPacManEaten() || game.gameOver() || depth == DEPTH) {
            return evaluationFunction(game);
        }
        int currentPac = game.getPacmanCurrentNodeIndex();
        for(MOVE move: game.getPossibleMoves(currentPac)) {
            Game newGame = game.copy();
            newGame.updatePacMan(move);
//            newGame.updateGame();
            v = Math.max(v, minValue(newGame, alpha, beta, depth + 1));
            if(v >= beta) {
                return v;
            }
            alpha = Math.max(alpha, v);
        }

        return v;
    }

    /**
     * This function minimize the max level value
     * When it reach the terminal situation, will return heuristic score
     * Otherwise will get minimize value of next max layer
     * @param game the updated game
     * @param alpha
     * @param beta
     * @param depth the tree level
     * @return the minimize value of lower level
     */
    private double minValue(Game game, double alpha, double beta, int depth) {
        double v = Integer.MAX_VALUE;
//        int current = game.getPacmanCurrentNodeIndex();
        if(game.wasPacManEaten() || game.gameOver() || depth == DEPTH) {
            return evaluationFunction(game);
        }

        ArrayList<EnumMap<GHOST, MOVE>> ghostsMoves = findPossibleCombination(game);
//        System.out.println("The possible move" + ghostsMoves.size());
        for(int i = 0; i < ghostsMoves.size(); i++) {
            Game newGame = game.copy();
            newGame.updateGhosts(ghostsMoves.get(i));
            newGame.updateGame();
            v = Math.min(v, maxValue(newGame, alpha, beta, depth + 1));
            if(v <= alpha) {
                return v;
            }
            beta = Math.min(beta, v);
        }
        return v;

    }

    /**
     * This function is the alpha beta pruning start function
     * Use the current pac man information as root and return the max value of next layer
     * @param game
     * @return
     */
    private MOVE alphaBetaAction(Game game) {
        MOVE bestMove = MOVE.NEUTRAL;
        double v = Integer.MIN_VALUE;
        double alpha = Integer.MIN_VALUE;
        double beta = Integer.MAX_VALUE;
        MOVE[] moves = game.getPossibleMoves(game.getPacmanCurrentNodeIndex());
//        ArrayList<MOVE> bestMoves = new ArrayList<>();
//        MOVE lastMove = game.getPacmanLastMoveMade();
        for(MOVE move: moves) {
//            if(move == game.getPacmanLastMoveMade()) continue;
            double prev = v;
            Game newGame = game.copy();
            newGame.updatePacMan(move);
            newGame.gameOver();
            newGame.updateGame();
            double temp = minValue(newGame, alpha, beta, 0);
            v = Math.max(v, temp);
            System.out.println("Now the move " + move + "the temp is " + temp);
            if(v > prev) {
                bestMove = move;
//                bestMoves.clear();
//                System.out.println("Now add best move " + move + "v is " + temp);
//                bestMoves.add(bestMove);
//            } else if(temp == prev) {
//                System.out.println("Now add best move " + move + "v is " + temp);
//                bestMoves.add(move);
            }
            if(v >= beta) {
                System.out.println("The best move is " + bestMove);
                return bestMove;
            }

            alpha = Math.max(alpha, v);
        }
//        System.out.println("The v is " + v);
//        System.out.println(bestMoves.toString());
////
//        if(bestMoves.size()>1) {
//            if(bestMoves.contains(lastMove)) {
//                System.out.println("Chose last move as best move " + lastMove);
//                return lastMove;
//            }
//            int randomMove = new Random().nextInt(bestMoves.size());
//            System.out.println("The random move index is" + randomMove);
//            System.out.println("The best move is " + bestMove);
//            return bestMoves.get(randomMove);
//        }
        System.out.println("The best move is " + bestMove);
        return bestMove;
    }

    /**
     * This function find all possible ghost and moves combination
     * @param game
     * @return array list with all combination
     */
    private ArrayList<EnumMap<GHOST, MOVE>> findPossibleCombination(Game game) {
        GHOST[] ghosts = GHOST.values();
        ArrayList<EnumMap<GHOST, MOVE>> res = new ArrayList<EnumMap<GHOST, MOVE>>();
        res.add(new EnumMap<GHOST, MOVE>(GHOST.class));
        for(int i = 0; i < ghosts.length; i++) {
            int ghostIndex = game.getGhostCurrentNodeIndex(ghosts[i]);
            MOVE[] moves = game.getPossibleMoves(ghostIndex);
            if(moves.length == 0) continue;
//            System.out.println("Now is ghost" + ghosts[i].toString() + "has " + moves.length + " possible moves");
            ArrayList<EnumMap<GHOST, MOVE>> temp = new ArrayList<EnumMap<GHOST, MOVE>>();
            for(int j = 0; j < moves.length; j++) {
                for(int k = 0; k < res.size(); k++) {
                    EnumMap<GHOST, MOVE> newElement = new EnumMap<GHOST, MOVE>(res.get(k));
                    newElement.put(ghosts[i], moves[j]);
                    temp.add(newElement);
                }
            }
            res = new ArrayList<EnumMap<GHOST, MOVE>>(temp);
            temp.clear();
        }
//        System.out.println("The size of possible combination is " + res.size());
        return res;
    }

    /**
     * The heuristic score of terminal node
     * It has three factors need to evaluate, pill distance, ghost distance and game score
     * @param game
     * @return
     */
    private double evaluationFunction(Game game) {
        int currentPacmanIndex = game.getPacmanCurrentNodeIndex();
        int closetFoodIndex = findClosetNodeIndex(game);
        MOVE lastMove = game.getPacmanLastMoveMade();
//        int foodDistance = game.getShortestPathDistance(currentPacmanIndex, closetPillIndex, lastMove);
        int foodDistance = game.getShortestPathDistance(currentPacmanIndex, closetFoodIndex, lastMove);
//        int foodDistance = game.getManhattanDistance(currentPacmanIndex,closetPillIndex);
        double ghostsDistance = 0;
        for(GHOST ghost: GHOST.values()) {
            int ghostIndex = game.getGhostCurrentNodeIndex(ghost);
            ghostsDistance = Math.min(ghostsDistance, game.getEuclideanDistance(currentPacmanIndex, ghostIndex));
        }
        if(ghostsDistance > 20) {
            ghostsDistance = 1;
        } else if(ghostsDistance == 0) {
            ghostsDistance = 10000;
        }
        return  (foodDistance * 1.0 + game.getScore()) /ghostsDistance;
//        if(ghostsDistance == 0 || ghostsDistance > 20) {
//            ghostsDistance = -1000;
////            return foodDistance + game.getScore();
//        }
//        return ghostsDistance/foodDistance * 1.0 + game.getScore();

    }


    /**
     * This function use fully observed map to find the nearest pill
     * @param game
     * @return closet pill node index
     */
    private int findClosetNodeIndex(Game game) {
        int current = game.getPacmanCurrentNodeIndex();
        int closetNode = -1;
        // find closet available pills and power pills
        int[] pills = game.getPillIndices();
        int[] powerPills = game.getPowerPillIndices();

        ArrayList<Integer> targets = new ArrayList<Integer>();

        for (int i = 0; i < pills.length; i++) {
            //check which pills are available
            Boolean pillStillAvailable = game.isPillStillAvailable(i);
            if (pillStillAvailable == null) continue;
            if (game.isPillStillAvailable(i)) {
                targets.add(pills[i]);
            }
        }

        for (int i = 0; i < powerPills.length; i++) {
            //check with power pills are available
            Boolean pillStillAvailable = game.isPillStillAvailable(i);
            if (pillStillAvailable == null) continue;
            if (game.isPowerPillStillAvailable(i)) {
                targets.add(powerPills[i]);
            }
        }

        if (!targets.isEmpty()) {
            int[] targetsArray = new int[targets.size()];

            //convert from ArrayList to array
            for (int i = 0; i < targetsArray.length; i++) {
                targetsArray[i] = targets.get(i);
            }
            closetNode = game.getClosestNodeIndexFromNodeIndex(current, targetsArray, DM.EUCLID);
        }
//        System.out.println("The current node index is " + current + "The closet node index is " + closetNode);
        return closetNode;

    }

    /**
     * Implemented Breadth First Search to be helper function.
     * @param game
     * @return the next move
     */
    public MOVE getBFSMove(Game game) {

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
