package lionel.demos.sportscore.web.tennis;

import java.io.Serializable;

/**
 * @author lionel.ngounou
 */
public class TennisPointUpdate implements Serializable{
    
    private Integer servicePoint, opponentPoint; 

    public TennisPointUpdate() {
    }
    
    static final int[] POINTS = new int[]{0, 1}; 

    public void setServicePoint(Integer servicePoint) {
        this.servicePoint = servicePoint;
    }

    public Integer getServicePoint() {
        return servicePoint;
    }

    public void setOpponentPoint(Integer opponentPoint) {
        this.opponentPoint = opponentPoint;
    }

    public Integer getOpponentPoint() {
        return opponentPoint;
    }

    @Override
    public String toString() {
        return "TennisPointUpdate{" + "servicePoint=" + servicePoint + ", opponentPoint=" + opponentPoint + '}';
    }
    
}