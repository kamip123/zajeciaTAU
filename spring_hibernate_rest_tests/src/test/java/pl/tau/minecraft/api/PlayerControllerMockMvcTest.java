package pl.tau.minecraft.api;

import pl.tau.minecraft.domain.Player;
import pl.tau.minecraft.service.EquipmentManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.Mockito.when;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PlayerControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EquipmentManager service;

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(mockMvc);
    }

    @Test
    public void greetingShouldReturnHelloMessage() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Hello")));
    }


    @Test
    public void getAllShouldReturnEmptyResults() throws Exception {
        when(service.findAllPlayers()).thenReturn(new LinkedList<Player>());
        this.mockMvc.perform(get("/players"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void getAllShouldReturnSomeResults() throws Exception {
        List<Player> expectedResult = new LinkedList<Player>();
        Player np = new Player();
        np.setId(1l);
        np.setName("Geralt");
        np.setHp(1000);
        np.setArmor(1000);
        expectedResult.add(np);
        when(service.findAllPlayers()).thenReturn(expectedResult);
        this.mockMvc.perform(get("/players"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"name\":\"Geralt\",\"hp\":1000,\"armor\":1000}]"));
    }

    @Test
    public void postNewPlayerShouldReallyAddItToDatabase() throws Exception {
        Player p = new Player();
        p.setName("Lambert");
        p.setHp(1000);
        p.setArmor(1000);
        when(service.addPlayer(p)).thenReturn(2l);
        this.mockMvc.perform(post("/players")
                    .content("{\"name\":\"Lambert\",\"hp\":1000,\"armor\":1000}")
                    .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Lambert")))
                .andExpect(content().string(containsString("\"id\":2")));
        p.setId(2l);
        verify(service).addPlayer(p);
    }

    @Test
    public void deletePlayerFromDatabase() throws Exception{
        Player p = new Player();
        p.setId(1l);
        p.setName("Geralt");
        p.setHp(1000);
        p.setArmor(1000);
        when(service.findPlayerById(1l)).thenReturn(p);

        this.mockMvc.perform(delete("/player/" + p.getId())
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("OK")));

        verify(service).deletePlayer(p);        
    }

    @Test
    public void updatePlayerFromDatabase() throws Exception{
        Player p = new Player();
        p.setId(1l);
        p.setName("Geralt");
        p.setHp(1000);
        p.setArmor(1000);
        this.mockMvc.perform(put("/player/" + p.getId())
                .content("{\"id\":1,\"name\":\"Geralt Pogromca\",\"hp\":1000,\"armor\":1000}")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().string("OK"));

        p.setName("Geralt Pogromca");
        verify(service).updatePlayer(p);
    }

}
