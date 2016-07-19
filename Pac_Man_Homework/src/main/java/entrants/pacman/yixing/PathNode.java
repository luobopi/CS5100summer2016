package entrants.pacman.yixing;

import pacman.game.Constants.*;

import java.util.ArrayList;

/**
 * Created by yixing on 7/18/16.
 */
public class PathNode {
    public int nodeIndex;
    public ArrayList<MOVE> path;

    public PathNode(int nodeIndex, ArrayList path){
        this.nodeIndex = nodeIndex;
        this.path = path;
    }
}
