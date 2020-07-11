package com.trade_analysis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "note", schema = "public")
public class Note {
    @Id
    @Column(name = "note_id", nullable = false, columnDefinition = "uuid")
    private UUID id = UUID.randomUUID();

    @Column(name = "markdown_text", nullable = false, columnDefinition = "text")
    private String text;
}
