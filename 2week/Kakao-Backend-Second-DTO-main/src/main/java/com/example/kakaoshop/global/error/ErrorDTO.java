package com.example.kakaoshop.global.error;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorDTO {
    private String message;
    private int status;
}
