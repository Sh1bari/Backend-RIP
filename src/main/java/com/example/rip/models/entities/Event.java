package com.example.rip.models.entities;

import com.example.rip.models.enums.EventState;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
    private LocalDateTime createTime = LocalDateTime.now();

    @Basic
    private LocalDateTime archiveTime;

    @OneToOne(mappedBy = "event", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
    private File file = new File();

    private Integer tickets;

    @Enumerated(EnumType.STRING)
    private EventState state = EventState.ACTIVE;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "Event_applications",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "applications_id"))
    private Set<Application> applications = new LinkedHashSet<>();

}
