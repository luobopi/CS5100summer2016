package entrants.pacman.yixing;

import pacman.game.Constants.*;

import java.util.ArrayList;

/**
 * Created by yixing on 7/18/16.
 * This is a node class used in search algorithm.
 * It contains the node index, search path from start node and estimate cost to this node
 */
public class PathNode {
    public int nodeIndex;
    public ArrayList<Integer> path;
    public double gn;
    public double hn;

    // Construct the node with node index, search path, gn and hn
    public PathNode(int nodeIndex, ArrayList path, double gn, double hn){
        this.nodeIndex = nodeIndex;
        this.path = path;
        this.gn = gn;
        this.hn = hn;
    }

    // Construct the node index, search path
    public PathNode(int nodeIndex, ArrayList path) {
        this.nodeIndex = nodeIndex;
        this.path = path;
        this.gn = 0.0;
        this.hn = 0.0;
    }
}
