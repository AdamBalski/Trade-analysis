package com.trade_analysis.model;

import com.trade_analysis.exception.InvalidApiCallException;
import com.trade_analysis.exception.TooManyApiCallsException;
import lombok.Getter;
import org.json.JSONObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Getter
@Entity
@Table(name = "stock_prices")
public class StockPricesEntity  extends StockPrices {
    @Id
    @Column(name = "stock_prices_id", nullable = false, columnDefinition = "uuid")
    private UUID id = UUID.randomUUID();

    @Column(name = "final", nullable = false, columnDefinition = "text")
    String finalAsString;

    public StockPricesEntity() {}

    public StockPricesEntity(String finalAsString) throws InvalidApiCallException, TooManyApiCallsException {
        this(UUID.randomUUID(), finalAsString);
    }

    public StockPricesEntity(UUID id, String finalAsString) throws InvalidApiCallException, TooManyApiCallsException {
        super(new JSONObject(finalAsString));

        this.id = id;
        this.finalAsString = finalAsString;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setFinalAsString(String finalAsString) throws InvalidApiCallException, TooManyApiCallsException {
        this.finalAsString = finalAsString;
        this.setTo(new StockPricesEntity(finalAsString));
    }

    public void setTo(StockPrices stockPrices) {
        this.setRaw(stockPrices.getRaw());
        this.setSymbol(stockPrices.getSymbol());
        this.setPeriod(stockPrices.getPeriod());
        this.setFinalJSON(stockPrices.getFinalJSON());
        this.setMetaData(stockPrices.getMetaData());
        this.setTimeSeries(stockPrices.getTimeSeries());
        this.setTimeSeriesLabel(stockPrices.getTimeSeriesLabel());
    }
}