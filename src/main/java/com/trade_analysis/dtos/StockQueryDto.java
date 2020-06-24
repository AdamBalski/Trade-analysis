package com.trade_analysis.dtos;

import lombok.*;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class StockQueryDto {
    private String apiKey;
    private String symbol;
    private String period;

    public StockQueryDto(String apiKey) {
        this.apiKey = apiKey;
    }
}
