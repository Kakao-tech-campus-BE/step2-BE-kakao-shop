package com.example.kakaoshop.global;

import com.example.kakaoshop.global.error.ErrorDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseDTO {
    private boolean success;
    private String response;
    private ErrorDTO errorDTO;
}
