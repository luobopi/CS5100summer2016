package entrants.pacman.yixing;

import pacman.game.Constants.*;

import java.util.ArrayList;

/**
 * Created by yixing on 7/18/16.
 */
public class PathNode {
    public int nodeIndex;
    public ArrayList<Integer> path;
    public double gn;
    public double hn;

    public PathNode(int nodeIndex, ArrayList path, double gn, double hn){
        this.nodeIndex = nodeIndex;
        this.path = path;
        this.gn = gn;
        this.hn = hn;
    }

    public PathNode(int nodeIndex, ArrayList path) {
        this.nodeIndex = nodeIndex;
        this.path = path;
        this.gn = 0.0;
        this.hn = 0.0;
    }
}
