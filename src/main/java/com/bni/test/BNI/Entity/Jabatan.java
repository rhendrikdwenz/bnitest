package com.bni.test.BNI.Entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="jabatan")
public class Jabatan {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String namaJabatan;
    private Long gaji;
}
