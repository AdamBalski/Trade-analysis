package com.trade_analysis.dtos_validation;

import com.trade_analysis.dtos.StockQueryDto;
import com.trade_analysis.model.StockPricesPeriod;
import com.trade_analysis.model.StockSymbol;

import static com.trade_analysis.dtos_validation.StockQueryValidationResult.*;

public interface StockQueryValidator extends Validator<StockQueryDto, StockQueryValidationResult> {
    StockQueryValidator fullValidator = stockQueryDto -> StockQueryValidator.symbolValidator
            .and(StockQueryValidator.periodValidator)
            .validate(stockQueryDto);

    StockQueryValidator symbolValidator = stockQueryDto -> {
        String querySymbol = stockQueryDto.getSymbol();

        for(StockSymbol symbol: StockSymbol.values()) {
            if(symbol.name().equals(querySymbol)) {
                return SUCCESS;
            }
        }

        return SYMBOL_NOT_CORRECT;
    };

    StockQueryValidator periodValidator = stockQueryDto -> {
        String queryPeriod = stockQueryDto.getPeriod();

        for(StockPricesPeriod period: StockPricesPeriod.values()) {
            if(period.name().equals(queryPeriod)) {
                return SUCCESS;
            }
        }

        return PERIOD_NOT_CORRECT;
    };
}
