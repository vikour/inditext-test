package es.vikour92.inditext.test.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A date interval have a beginning and an end. The beginning must be before or at same time than end.
 *
 * @param start A datetime representing the start
 * @param end   A datetime representing the end
 */

public record DateInterval(LocalDateTime start,LocalDateTime end) {

    public DateInterval(LocalDateTime start, LocalDateTime end) {
        Objects.requireNonNull(start, "Start interval cannot be null");
        Objects.requireNonNull(end, "End interval cannot be null");

        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start cannot be before end");
        }

        this.start = start;
        this.end = end;
    }

    public static DateInterval of(LocalDateTime start, LocalDateTime end) {
        return new DateInterval(start, end);
    }

}