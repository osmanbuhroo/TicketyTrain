package com.TicketyTrain.Repository;

import com.TicketyTrain.Entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface TicketRepository extends JpaRepository<Destination, Long> {
    List<Destination> findBySection(String section);

    boolean existsBySectionAndSeat(String section, String s);
}