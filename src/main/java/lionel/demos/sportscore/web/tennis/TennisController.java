
package lionel.demos.sportscore.web.tennis;

import com.google.common.collect.Maps;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lionel.ngounou
 */
@Controller
@RequestMapping("/tennis")
public class TennisController extends GeneralTennisController{
    
    @RequestMapping
    public String home(){
        return "tennis/home";
    }
    
}
