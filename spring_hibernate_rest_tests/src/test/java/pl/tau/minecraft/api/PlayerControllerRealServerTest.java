package pl.tau.minecraft.api;

import pl.tau.minecraft.domain.Player;
import pl.tau.minecraft.domain.Equipment;
import pl.tau.minecraft.service.EquipmentManager;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

@Ignore
@RunWith(SpringRunner.class)
@ComponentScan({"pl.tau"})
@PropertySource("classpath:jdbc.properties")
@ImportResource({"classpath:/beans.xml"})
@Rollback
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PlayerControllerRealServerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private PlayerController controller;
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    EquipmentManager equipmentManager;

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(controller);
    }

    @Test
    public void greetingShouldReturnHelloMessage() throws Exception {
        assertThat(
                this.restTemplate.getForObject("http://localhost:" + port + "/",
                String.class)).contains("Hello");
    }

    @Test
    public void getAllShouldReturnSomeResultsFromDatabase() throws Exception {
        assertThat(
                this.restTemplate.getForObject("http://localhost:" + port + "/persons",
                        List.class)).isNotNull();
    }

    @Test
    public void getAllShouldReturnResultsThatWerePreviouslyPutIntoDatabase() throws Exception {
        Player newPlayer= new Player();
        newPlayer.setName("Wiedzminowy wiedzmin");
        newPlayer.setHp(100);
        newPlayer.setArmor(100);
        Long newId = equipmentManager.addPlayer(newPlayer);
        List<java.util.LinkedHashMap> playersFromRest =
                this.restTemplate.getForObject("http://localhost:" + port + "/players", List.class);
        boolean found = false;
        for (LinkedHashMap p: playersFromRest) {
            if (p.get("id").toString().equals(newId.toString())) found = true;
        }
        assertTrue(found);
    }
}
