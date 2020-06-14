package com.trade_analysis.dtos;

import com.trade_analysis.model.StockSymbol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class StockQueryDto {
    private String apiKey;
    private StockSymbol symbol;

    public StockQueryDto(String apiKey) {
        this.apiKey = apiKey;
    }
}
