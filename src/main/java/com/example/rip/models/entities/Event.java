package com.example.rip.models.entities;

import com.example.rip.models.enums.EventState;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    @Lob
    private byte[] image;

    private Integer tickets;

    @Enumerated(EnumType.STRING)
    private EventState state;
}
