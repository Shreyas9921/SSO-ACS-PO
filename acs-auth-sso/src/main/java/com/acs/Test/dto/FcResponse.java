package com.acs.Test.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FcResponse {
    private Integer fcId;
    private String fcName;

    public FcResponse(String fcName, Integer fcId) {
        this.fcName = fcName;
        this.fcId = fcId;
    }
}
