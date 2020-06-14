package com.trade_analysis.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockQueryDto {
    private String apiKey;
    private String symbol;
    private String period;

    public StockQueryDto(String apiKey) {
        this.apiKey = apiKey;
    }
}
