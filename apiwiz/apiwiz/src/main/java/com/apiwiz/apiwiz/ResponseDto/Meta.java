package com.apiwiz.apiwiz.ResponseDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class Meta {
    private String symbol;
    private String interval;
    private String currency;
    private String exchange_timezone;
    private String exchange;
    private String mic_code;

    private String type;
}
