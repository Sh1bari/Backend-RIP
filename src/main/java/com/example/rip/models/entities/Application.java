package com.example.rip.models.entities;

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
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic
    private LocalDateTime createTime;

    @Basic
    private LocalDateTime formationTime;

    @Basic
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "creator_user_id")
    private User creatorUser;

    @ManyToOne
    @JoinColumn(name = "moderator_user_id")
    private User moderatorUser;
}
