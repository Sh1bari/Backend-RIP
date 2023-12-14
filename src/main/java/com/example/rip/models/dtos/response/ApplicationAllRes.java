package com.example.rip.models.dtos.response;

import com.example.rip.models.entities.Application;
import com.example.rip.models.enums.ApplicationStatus;
import lombok.*;

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
public class ApplicationAllRes {
    private Integer id;

    private LocalDateTime createTime;

    private LocalDateTime formationTime;

    private LocalDateTime endTime;

    private Integer creatorUserId;

    private String creatorUsername;

    private Integer moderatorUserId;

    private String moderatorUsername;

    private ApplicationStatus status;

    public static ApplicationAllRes mapFromEntity(Application application){
        ApplicationAllRes res = new ApplicationAllRes();
        res.setId(application.getId());
        res.setCreatorUsername(application.getCreatorUser() != null ? application.getCreatorUser().getUsername() : null);
        res.setModeratorUsername(application.getModeratorUser() != null ? application.getModeratorUser().getUsername() : null);
        res.setCreateTime(application.getCreateTime());
        res.setStatus(application.getStatus());
        res.setEndTime(application.getEndTime());
        res.setFormationTime(application.getFormationTime());
        res.setCreatorUserId(application.getCreatorUser() != null ? application.getCreatorUser().getId() : null);
        res.setModeratorUserId(application.getModeratorUser() != null ? application.getModeratorUser().getId() : null);
        return res;
    }
}
