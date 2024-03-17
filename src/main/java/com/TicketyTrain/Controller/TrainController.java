package com.TicketyTrain.Controller;

import com.TicketyTrain.Entity.Destination;
import com.TicketyTrain.Entity.User;
import com.TicketyTrain.Service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/train")
public class TrainController {

    @Autowired
    private TrainService trainService;

    @PostMapping("/tickets")
    public Destination purchaseTicket(@RequestBody User user, String from, String to) {
        return trainService.purchaseTicket(from, to, user);
    }

    @GetMapping("/tickets/{id}")
    public Destination getTicketDetails(@PathVariable Long id) {
        return trainService.getTicketDetails(id);
    }

    @GetMapping("/users/{section}")
    public List<Destination> getUsersBySection(@PathVariable String section) {
        return trainService.getUsersBySection(section);
    }

    @DeleteMapping("/tickets/{id}")
    public void removeUser(@PathVariable Long id) {
        trainService.removeUser(id);
    }

    @PutMapping("/tickets/{id}") // Update entire ticket, including section
    public void modifyTicket(@PathVariable Long id, @RequestBody Destination modifiedTicket) {
        trainService.modifyTicket(id, modifiedTicket);
    }



}
