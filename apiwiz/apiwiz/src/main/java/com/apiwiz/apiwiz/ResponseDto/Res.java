package com.apiwiz.apiwiz.ResponseDto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
public class Res {
    private Meta meta;
    private ArrayList<Values> values;

    private String status;
}
