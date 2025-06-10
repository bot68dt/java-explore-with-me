package ru.practicum.explore.library.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "adlocations")
@Data
@ToString
public class AdLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "town", nullable = false)
    private String town;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "place", nullable = false)
    private String place;

    @Column(name = "lat", nullable = false)
    private Float lat;

    @Column(name = "lon", nullable = false)
    private Float lon;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdLocation)) return false;
        return id != null && id.equals(((AdLocation) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}