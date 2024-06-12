package com.bni.test.BNI.Entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="cabang")
public class Cabang {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String namaCabang;
    private String alamat;
}

