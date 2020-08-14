package com.exadel.placebook.model.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class PlaceBlockRequest {
    @NotNull
    private Long userId;
    @NotNull
    private LocalDateTime blockEnd;
}
