package com.trade_analysis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Entity
@Table(name = "stock_prices_history_entry", schema = "public")
public class StockPricesHistoryEntry {
    @Id
    @Column(name = "stock_prices_history_entry_id", nullable = false, columnDefinition = "uuid")
    UUID id = UUID.randomUUID();

    @OneToOne(targetEntity = User.class)
    @JoinColumn(nullable = false, name = "user_id", updatable = false)
    User userId;


    @OneToOne(targetEntity = StockPricesEntity.class)
    @JoinColumn(nullable = false, name = "stock_prices_id", updatable = false)
    StockPricesEntity stockPrices;


    @OneToOne(targetEntity = Note.class)
    @JoinColumn(nullable = false, name = "note_id", updatable = false)
    Note note;
}