package lionel.demos.sportscore.model.tennis;

/**
 * @author lionel.ngounou
 */
public class TennisPlayerPoint {
    
    private final TennisPlayer tennisPlayer;
    private final int point;

    private TennisPlayerPoint(TennisPlayer tennisPlayer, int point) {
        this.tennisPlayer = tennisPlayer;
        this.point = point;
    }

    public TennisPlayer getTennisPlayer() {
        return tennisPlayer;
    }

    public int getPoint() {
        return point;
    }
}
