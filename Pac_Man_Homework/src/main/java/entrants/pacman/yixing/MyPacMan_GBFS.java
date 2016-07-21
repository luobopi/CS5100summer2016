package entrants.pacman.yixing;

import pacman.controllers.PacmanController;
import pacman.game.Constants;
import pacman.game.Game;

import java.util.*;

import static pacman.game.Constants.*;

/**
 * This class implemented Greed Best-First Search
 * The state space: the current maze
 * The initial stare: the current node
 * The goal: go to nearest pill or power pill
 */
public class MyPacMan_GBFS extends PacmanController {
    private static final int MIN_DISTANCE = 20;
    private Random random = new Random();
    private MOVE lastLastMove = null;

    // comparator for PathNode class. It will compare the fn of a node which is sum of gn and hn
    // gn is the start to this node cost. hn is this node to goal's state space cost.
    public class PathComparator implements Comparator<PathNode> {



        @Override
        public int compare(PathNode p1, PathNode p2) {
            double f1 = p1.gn + p1.hn;
            double f2 = p2.gn + p2.hn;

            if (f1 > f2) {
                return 1;
            } else if (f1 < f2) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    @Override
    public MOVE getMove(Game game, long timeDue) {

        // Should always be possible as we are PacMan
        int current = game.getPacmanCurrentNodeIndex();

        // use the fully observable map to get the closet node as the next goal
        int closetNode = findClosetNodeIndex(game);

        // Should always be possible as we are PacMan
        // Strategy: Use Greedy Best First Search on Pac Man to eat pills.
        // The estimate function stored in every PathNode class
        // The data structure is Priority Queue to get the lowest path cost of node for expansion
        // And clear other node in the priority queue.
        // Add successor nodes object in the priority queue
        Comparator<PathNode> comparator = new PathComparator();
        PriorityQueue<PathNode> heap = new PriorityQueue<PathNode>(comparator);
        HashSet<Integer> visited = new HashSet<Integer>();
        double heuristic = game.getEuclideanDistance(current, closetNode);

        // At beginning, the start node has 0 gn.
        heap.add(new PathNode(current, new ArrayList<Integer>(), 0, heuristic));

        while(!heap.isEmpty()) {
            PathNode top = heap.poll();
            if(visited.contains(top.nodeIndex)) continue;
            visited.add(top.nodeIndex);

            // check if all possible path to goal has added
            if(top.path.size() != 0 && top.nodeIndex == closetNode) {
                return game.getNextMoveTowardsTarget(current, top.path.get(0), Constants.DM.PATH);

            }

            // update successor nodes path and fn, put it into priority queue.
            int[] neighbours = game.getNeighbouringNodes(top.nodeIndex);
            heap.clear();
            for(int i = 0; i < neighbours.length; i++) {
                if(!top.path.contains(neighbours[i]) && neighbours[i]!= current) {
                    ArrayList<Integer> newPath = new ArrayList<Integer>(top.path);
                    newPath.add(neighbours[i]);
                    double hn = game.getEuclideanDistance(neighbours[i], closetNode);
                    heap.add(new PathNode(neighbours[i], newPath, 0, hn));
                }
            }

        }

        MOVE[] moves = game.getPossibleMoves(current, game.getPacmanLastMoveMade());
        if (moves.length > 0) {
            System.out.println("Random step");
            return moves[random.nextInt(moves.length)];
        }

        // Must be possible to turn around
        return game.getPacmanLastMoveMade().opposite();
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
            closetNode = game.getClosestNodeIndexFromNodeIndex(current, targetsArray, Constants.DM.PATH);
        }
        System.out.println("The current node index is " + current + "The closet node index is " + closetNode);
        return closetNode;

    }
}
