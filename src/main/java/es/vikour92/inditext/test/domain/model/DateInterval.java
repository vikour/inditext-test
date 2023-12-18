package es.vikour92.inditext.test.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

record DateInterval(LocalDateTime start,LocalDateTime end) {

    DateInterval(LocalDateTime start, LocalDateTime end) {
        Objects.requireNonNull(start, "Start interval cannot be null");
        Objects.requireNonNull(end, "End interval cannot be null");

        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start cannot be before end");
        }

        this.start = start;
        this.end = end;
    }

    static DateInterval of(LocalDateTime start, LocalDateTime end) {
        return new DateInterval(start, end);
    }

}