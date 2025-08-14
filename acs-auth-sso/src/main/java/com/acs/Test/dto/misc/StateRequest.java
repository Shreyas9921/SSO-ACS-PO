package com.acs.Test.dto.misc;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class StateRequest {
    @NotBlank
    private List<String> countries;
}
