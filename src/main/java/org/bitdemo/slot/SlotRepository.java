package org.bitdemo.slot;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface SlotRepository extends JpaRepository<Slot, Long> {

    public Optional<Slot> findByStartAndFinish(ZonedDateTime start, ZonedDateTime finish);

}
