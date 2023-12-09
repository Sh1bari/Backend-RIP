package com.example.rip.models.dtos.request;

import com.example.rip.models.enums.ApplicationStatus;
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
public class ApplicationVote {
    private ApplicationStatus status;
}
