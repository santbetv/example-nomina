package com.sbvdeveloper.poc_nomina.dto;

import lombok.*;

@Getter
@AllArgsConstructor
@Builder
public class LineDataDTO {

    private final int offset; // Incremento en la posición
    private final String text;
    private final boolean boldAndUnderlined;
}
