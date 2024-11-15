package so.siva.telegram.bot.got_t_bot.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import so.siva.telegram.bot.got_t_bot.essences.admin.dto.api.IPlayerStartList;
import so.siva.telegram.bot.got_t_bot.essences.admin.random.IHouseRandomizerService;
import so.siva.telegram.bot.got_t_bot.web.controller.api.IHouseRandomizerController;

@RestController
@RequestMapping("/house_random")
public class HouseRandomizerController implements IHouseRandomizerController {

    @Autowired
    private IHouseRandomizerService service;


    @PostMapping
    public String getRandomizedPlayersList(@RequestBody IPlayerStartList playerStartList){
        return service.randomizePlayers(playerStartList);
    }
}
