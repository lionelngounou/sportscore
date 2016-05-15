package lionel.demos.sportscore.model.tennis;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author lionel.ngounou
 */
public class TennisPlayer  implements Serializable {
    
    private String firstname, lastname, shortCode;

    public TennisPlayer() { }

    public TennisPlayer(String firstname, String lastname) {
        this(firstname, lastname, shortCodeFromNames(firstname, lastname));
    }

    public TennisPlayer(String firstname, String lastname, String shortCode) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.shortCode = shortCode;
    }
    
    private static String shortCodeFromNames(final String firstname, final String lastname){
        String code = "";
        if(firstname!=null)
            code = firstname.substring(0, Math.min(firstname.length()-1, 3)).toUpperCase() + firstname.length();
        if(lastname!=null)
            code += lastname.length() + lastname.substring(0, Math.min(lastname.length()-1, 3)).toUpperCase();
        return code;
    }

    @Override
    public String toString() {
        return "TennisPlayer{" + "firstname=" + firstname + ", lastname=" + lastname + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.shortCode);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TennisPlayer other = (TennisPlayer) obj;
        if (!Objects.equals(this.shortCode, other.shortCode)) {
            return false;
        }
        return true;
    }
    
    public String getFirstAndLastNames() {
        return firstname + " " + lastname;
    }
    
    public String getLastAndFirstNames() {
        return lastname + " " + firstname;
    }
    
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }
        
}
