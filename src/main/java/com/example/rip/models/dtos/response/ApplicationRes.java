package com.example.rip.models.dtos.response;

import com.example.rip.models.entities.Application;
import com.example.rip.models.entities.User;
import com.example.rip.models.enums.ApplicationStatus;
import jakarta.persistence.Basic;
import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationRes {

    private Integer id;

    private LocalDateTime createTime;

    private LocalDateTime formationTime;

    private LocalDateTime endTime;

    private Integer creatorUserId;

    private Integer moderatorUserId;

    private ApplicationStatus status;

    private List<EventRes> events;

    public static ApplicationRes mapFromEntity(Application application){
        ApplicationRes res = new ApplicationRes();
        res.setEvents(application.getEvents().stream()
                .map(EventRes::mapFromEntity)
                .collect(Collectors.toList()));
        res.setId(application.getId());
        res.setCreateTime(application.getCreateTime());
        res.setStatus(application.getStatus());
        res.setEndTime(application.getEndTime());
        res.setFormationTime(application.getFormationTime());
        res.setCreatorUserId(application.getCreatorUser().getId());
        res.setModeratorUserId(application.getModeratorUser() != null ? application.getModeratorUser().getId() : null);
        return res;
    }
}
