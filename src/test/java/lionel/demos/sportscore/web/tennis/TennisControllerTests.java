package lionel.demos.sportscore.web.tennis;

import lionel.demos.sportscore.web.tennis.TennisController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @author lionel.ngounou
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/root-context.xml", "classpath:spring/sportscore-web-servlet.xml"})
@WebAppConfiguration
@ActiveProfiles("test")
public class TennisControllerTests {
    
    @Autowired
    private TennisController tennisController;
    
    private MockMvc mvc;
    
    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(tennisController).build();
    }
    
    @Test
    public void testUpdateMatch() throws Exception {
        System.out.println("updateMatch");
        
        Map body = Maps.newHashMap();
        body.put("servicePoint", -10);
        body.put("opponentPoint", 10);
        String matchId = "U712_a8";
        
        //success create
        mvc.perform(post("/tennis/matches/{matchId}/point/update", matchId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(body))
            )
           .andExpect(status().isOk())
           .andExpect(jsonPath("score", notNullValue()))
        ;
        
        //success create
        mvc.perform(put("/tennis/matches/{matchId}/point/update", matchId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsBytes(body))
            )
           .andExpect(status().isOk())
           .andExpect(jsonPath("score", notNullValue()))
        ;
    }
        
}
