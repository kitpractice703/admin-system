package com.admin.content.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class NoticeRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;
}
