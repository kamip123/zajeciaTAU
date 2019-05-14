package pl.tau.minecraft.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.tau.minecraft.domain.Player;
import pl.tau.minecraft.domain.Equipment;
import pl.tau.minecraft.service.EquipmentManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@RestController
public class PlayerController {

    @Autowired
    EquipmentManager equipmentManager;

    @RequestMapping("/players")
    public java.util.List<Player> getPlayers() {
        List<Player> players = new LinkedList<>();
        for (Player p : equipmentManager.findAllPlayers()) {
            players.add(p.clone());
        }
        return players;
    }

    @RequestMapping(value = "/players",method = RequestMethod.POST)
    public Player addClient(@RequestBody Player nplayer) {
        nplayer.setId(equipmentManager.addPlayer(nplayer));
        return nplayer;
    }
    
    @RequestMapping("/")
    public String index() {
        return "Hello";
    }

    @RequestMapping(value = "/player/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Player getPlayer(@PathVariable("id") Long id) throws SQLException {
        return equipmentManager.findPlayerById(id).clone();
    }

    @RequestMapping(value = "/player", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Player> getPlayers(@RequestParam(value = "filter", required = false) String f) throws SQLException {
        List<Player> players = new LinkedList<Player>();
        for (Player p : equipmentManager.findAllPlayers()) {
            if (f == null) {
                players.add(p.clone());
            } else if (p.getName().contains(f)) {
                players.add(p);
            }
        }
        return players;
    }

    @RequestMapping(value = "/player/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deletePlayer(@PathVariable("id") Long id) throws SQLException {
        equipmentManager.deletePlayer(equipmentManager.findPlayerById(id));
        return "OK";
    }

    @RequestMapping(value = "/player/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public String updatePlayer(@PathVariable("id") Long id, @RequestBody Player nplayer) throws SQLException {
        for (Player p : equipmentManager.findAllPlayers()) {
            if(p.getId() == id){
                Player playerToUpdate = equipmentManager.findPlayerById(id);
                playerToUpdate.setName(nplayer.getName());
                playerToUpdate.setHp(nplayer.getHp());
                playerToUpdate.setArmor(nplayer.getArmor());
                equipmentManager.updatePlayer(playerToUpdate);
                return "OK";
            }
        }
        return "Not Ok";
    }
}
