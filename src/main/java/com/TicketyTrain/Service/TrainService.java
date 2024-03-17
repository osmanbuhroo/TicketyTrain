package com.TicketyTrain.Service;

import com.TicketyTrain.Entity.Destination;
import com.TicketyTrain.Entity.User;
import com.TicketyTrain.Repository.TicketRepository;
import com.TicketyTrain.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketRepository ticketRepository;

    public Destination purchaseTicket(String from, String to, User user) {
        user = userRepository.save(user);
        Destination destination = new Destination();
        destination.setFrom(from);
        destination.setTo(to);
        destination.setUser(user);
        destination.setPrice(20.0);
        String section = allocateSeat();
        if (section == null) {
            throw new RuntimeException("No seats available on the train!");
        }
        destination.setSection(section);
        destination.setSeat(generateSeat(section));

        // Fetch the saved Destination
        Destination savedDestination = ticketRepository.save(destination);

        // Return the saved destination
        return savedDestination;
    }
    public Destination getTicketDetails(Long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found!"));
    }

    public List<Destination> getUsersBySection(String section) {
        return ticketRepository.findBySection(section);
    }

    public void removeUser(Long id) {
        ticketRepository.deleteById(id);
    }

    public Destination modifyTicket(Long id, Destination modifiedTicket) {
        Destination destination = ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found!"));

        //  Section Modification Check
        if (!destination.getSection().equals(modifiedTicket.getSection())) {
            // Section is being changed
            if (isSeatAvailable(modifiedTicket.getSection(), extractSeatNumber(modifiedTicket.getSeat()))) {
                // Seat is available in the new section
                destination.setSection(modifiedTicket.getSection());
                destination.setSeat(modifiedTicket.getSeat());
            } else {
                throw new RuntimeException("Selected seat is already occupied in the new section!");
            }
        }

       // Other Field Updates
        destination.setFrom(modifiedTicket.getFrom());
        destination.setTo(modifiedTicket.getTo());
        return  ticketRepository.save(destination);
    }

    private String allocateSeat() {
        // Check available seats in both sections
        List<Destination> sectionADestinations = ticketRepository.findBySection("A");
        List<Destination> sectionBDestinations = ticketRepository.findBySection("B");
        if (sectionADestinations.size() < 50) {
            return "A";
        } else if (sectionBDestinations.size() < 50) {
            return "B";
        } else {
            return null; // No seats available
        }
    }

    private String generateSeat(String section) {
        int seatNumber = ticketRepository.findBySection(section).size() + 1;
        return section + "-" + seatNumber;
    }

    private boolean isSeatAvailable(String section, int seatNumber) {
        return !ticketRepository.existsBySectionAndSeat(section, section + "-" + seatNumber);
    }

    private String extractSection(String seat) {
        return seat.split("-")[0];
    }

    private int extractSeatNumber(String seat) {
        return Integer.parseInt(seat.split("-")[1]);
    }
}
