package com.example.rip.models.dtos.response;

import lombok.*;
import org.springframework.data.domain.Page;

/**
 * Description:
 *
 * @author Vladimir Krasnov
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventAllRes {
    private Page<EventRes> events;
    private Integer applicationId;
}
