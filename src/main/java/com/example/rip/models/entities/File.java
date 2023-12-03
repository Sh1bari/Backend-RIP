package com.example.rip.models.entities;

import jakarta.persistence.*;
import lombok.*;

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
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;


    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "event_id")
    private Event event;

    private String name;

    private String type;

    private Long size;

    private String path;

}
