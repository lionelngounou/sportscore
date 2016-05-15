
package lionel.demos.sportscore.web.tennis;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lionel.demos.sportscore.model.tennis.*;
import lionel.demos.sportscore.service.tennis.TennisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lionel.ngounou
 */
@Controller
@RequestMapping("/tennis/matches")
public class TennisMatchController extends GeneralTennisController{
    
    public static final String BASE_DIR = "tennis/match";
    public static final String LIST_VIEW = BASE_DIR + "/list";
    public static final String CREATE_VIEW = BASE_DIR + "/create";
    public static final String SHOW_VIEW = BASE_DIR + "/show";
    
    @Autowired
    private MessageSendingOperations<String> messagingTemplate; 
        
    @RequestMapping(method = RequestMethod.GET)
    public String list(Map<String,Object> model){
        Collection<TennisMatch> matches = tennisService.getAllMatches();
        model.put("matches", matches);
        model.put("liveMatchesCount", matches.stream().filter(m -> !m.isOver()).count());
        return LIST_VIEW;
    }
    
    @RequestMapping(value="/{matchId}", method = RequestMethod.GET)
    public String show(@PathVariable String matchId, Map<String,Object> model){
        Optional<TennisMatch> optMatch = tennisService.getMatch(matchId);
        if(optMatch.isPresent()){
            model.put("match", optMatch.get());
            return SHOW_VIEW;
        }
        model.put("message", "match " + matchId + " not found!");
        model.put("messageStatus", "danger");
        return "redirect:/tennis/matches"; //list
    }
    
    @RequestMapping(value="/create", method = RequestMethod.GET)
    public String initCreate(){
        return CREATE_VIEW;
    }
    
    @RequestMapping(value="/create", method = RequestMethod.POST)
    public String create(){
        return "redirect:/tennis/matches"; //list
    }
    
    @RequestMapping(value = "/v1/{matchId}/update/point", method = {RequestMethod.PUT, RequestMethod.POST}
        , consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<Map> updateMatch(@PathVariable String matchId){
        Map body = Maps.newHashMap();
        body.put("success", true);
        Optional<TennisMatch> optMatch = tennisService.getMatch(matchId);
        if(!optMatch.isPresent()){
            body.put("success", false);
            return new ResponseEntity(body, HttpStatus.NOT_FOUND);
        }
        Map payload = buildPayload(optMatch.get().addRandomPoint());
        body.put("score", payload);
        messagingTemplate.convertAndSend("/sockTMQ/result", payload);//update sockets
        return new ResponseEntity(body, HttpStatus.OK);
    }
	
    @RequestMapping(value = "/v1/{matchId}/score")
    @ResponseBody
    public ResponseEntity<Map> getScore(@PathVariable String matchId){
        Map body = Maps.newHashMap();
        body.put("success", true);
        Optional<TennisMatch> optMatch = tennisService.getMatch(matchId);
        if(!optMatch.isPresent()){
            body.put("success", false);
            return new ResponseEntity(body, HttpStatus.NOT_FOUND);
        }
        body.put("score", buildPayload(optMatch.get()));
        return new ResponseEntity(body, HttpStatus.OK);
    }
    
    static Map<String, Object> buildPayload(TennisMatch match){
        Map<String, Object> pl = Maps.newHashMap();       
        pl.put("id", match.getId());   
        pl.put("over", match.isOver());
        pl.put("players", asArray(match.getPlayerOne().getFirstAndLastNames(), match.getPlayerTwo().getFirstAndLastNames()));
        List<String[]> setScores = new LinkedList();
        match.getSets().forEach(s -> {
            TennisScore score = s.getScore();
            setScores.add(asArray(score));
        });
        pl.put("sets", setScores);
        pl.put("currentScore", EMPTY_GAME);        
        pl.put("serving", 0);
        match.getLastGame().ifPresent(g -> {
            pl.put("currentScore", asArray(g.getScore()));
            pl.put("serving", g.playerOneIsServing()? 0 : 1);
        });
        match.getWinner().ifPresent(w -> {
            pl.put("winner", w.equals(match.getPlayerOne())? 0 : 1);
        });
        return pl;
    }
    
    final static String[] asArray(TennisScore s){
        return asArray(s.getPlayerOneScore().getScore(), s.getPlayerTwoScore().getScore());
    }
    
    final static String[] asArray(String val0, String val1){
        return new String[]{val0, val1};
    }
    
    static final String[] EMPTY_GAME = {"*","*"};
    
    @MessageMapping("/sockTM" /*"/sock/tennis/matches/{matchId}"*/)
    @SendTo("/sockTMQ/result" /*"/sock/tennis/queue"*/)
    public String sockShow(/*@DestinationVariable String matchId*/) throws Exception{
        System.out.println("Request socket for match > at  " + System.currentTimeMillis());
        return "Resp to ur sock connection at" + System.currentTimeMillis();
    }
    
    @RequestMapping(value="/pushSock", method = RequestMethod.GET)
    public String pushSock(){
        messagingTemplate.convertAndSend("/sockTMQ/result", "pushed to sock at" + System.currentTimeMillis());
        return LIST_VIEW;
    }
    
}
