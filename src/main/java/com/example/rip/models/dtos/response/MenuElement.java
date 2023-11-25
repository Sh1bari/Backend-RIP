package com.example.rip.models.dtos.response;

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
public class MenuElement {
    private String name;
    private String url;
}
