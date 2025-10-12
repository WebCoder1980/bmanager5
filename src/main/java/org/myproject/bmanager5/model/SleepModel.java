package org.myproject.bmanager5.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity(name = "sleep")
@Data
@NoArgsConstructor
@Accessors(chain = true)
@SuperBuilder
public class SleepModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "start_dt")
    protected LocalDateTime startDT;

    @Column(name = "end_dt")
    protected LocalDateTime endDT;
}
