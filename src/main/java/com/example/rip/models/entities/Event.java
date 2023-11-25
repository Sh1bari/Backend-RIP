package com.example.rip.models.entities;

import com.example.rip.models.enums.EventState;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    private String name;

    private String description;

    @Basic
    private LocalDateTime eventTime;

    @Basic
    private LocalDateTime createTime;

    @Basic
    private LocalDateTime deleteTime;

    @Basic
    private LocalDateTime archiveTime;

    @OneToOne(mappedBy = "event", orphanRemoval = true)
    private File file;

    private Integer tickets;

    @Enumerated(EnumType.STRING)
    private EventState state;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "Event_applications",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "applications_id"))
    private List<Application> applications = new ArrayList<>();

}
