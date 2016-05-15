
package lionel.demos.sportscore.web.tennis;

import java.util.HashMap;
import java.util.Map;
import lionel.demos.sportscore.service.tennis.TennisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author lionel.ngounou
 */
public abstract class GeneralTennisController {
    
    @Autowired
    protected TennisService tennisService; 
    
    @ModelAttribute("generalModel")
    public Map<String,Object> generalModel(){
        Map<String,Object> gm = new HashMap<>();
        gm.put("sportSection", "tennis");
        return gm;
    }
}
