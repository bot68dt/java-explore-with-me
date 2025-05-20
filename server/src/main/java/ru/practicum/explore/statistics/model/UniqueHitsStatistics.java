package ru.practicum.explore.statistics.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "stats")
@Data
@ToString
public class UniqueHitsStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "uri", nullable = false)
    private String uri;

    @Column(name = "app", nullable = false)
    private String app;

    @Column(name = "time", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime time;

    @Column(name = "unique_hits", nullable = false)
    private Long unique;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UniqueHitsStatistics)) return false;
        return id != null && id.equals(((UniqueHitsStatistics) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}