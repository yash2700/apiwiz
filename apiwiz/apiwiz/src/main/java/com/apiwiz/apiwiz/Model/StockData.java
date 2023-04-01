package com.apiwiz.apiwiz.Model;

import com.apiwiz.apiwiz.ResponseDto.Values;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StockData {
    public String open;
    public String high;
    public String low;
    public String close;
    public String volume;

    public StockData(Values values){
        this.open=values.getopen();
        this.high=values.getHigh();
        this.low=values.getLow();
        this.close=values.getClose();
        this.volume=values.getVolume();
    }



}
