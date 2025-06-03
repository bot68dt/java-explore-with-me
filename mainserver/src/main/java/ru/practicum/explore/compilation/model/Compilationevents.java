package ru.practicum.explore.compilation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "compilationevents")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Compilationevents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "compilation_id", nullable = false)
    private Long compilationId;

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Compilationevents)) return false;
        return id != null && id.equals(((Compilationevents) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}