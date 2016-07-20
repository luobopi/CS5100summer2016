package entrants.pacman.yixing;


import pacman.game.Constants;
import pacman.game.Game;

/**
 * Created by yixing on 7/20/16.
 * This class is a helper class used in uninformed search
 */
public class GoalHelper {

    /**
     * This method return if the node index in the given game is an edible pill
     * @param game
     * @param node
     * @return
     */
    public static boolean isEdiblePill(Game game, int node) {
        int pillIndex = game.getPillIndex(node);
        if(pillIndex != -1) {
            System.out.println("The pill is still available" + game.isPillStillAvailable(pillIndex));
            if(game.isPillStillAvailable(pillIndex)!=null && game.isPillStillAvailable(pillIndex)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method return if the node index in the given game is an edible power pill
     * @param game
     * @param node
     * @return
     */
    public static boolean isEdiblePowerPill(Game game, int node) {
        int powerPillIndex = game.getPowerPillIndex(node);
        if (powerPillIndex != -1) {
            System.out.println("The power pill is still available" + game.isPowerPillStillAvailable(powerPillIndex));
            if(game.isPowerPillStillAvailable(powerPillIndex)!=null && game.isPowerPillStillAvailable(powerPillIndex)) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method return if the node index in the given game is an edible ghost
     * @param game
     * @param node
     * @return
     */
    public static boolean isEdibleGhost(Game game, int node) {
        for(Constants.GHOST ghost: Constants.GHOST.values()) {
            if(game.getGhostEdibleTime(ghost) > 0 && game.getGhostCurrentNodeIndex(ghost) == node) {
                return true;
            }
        }
        return false;
    }

}
