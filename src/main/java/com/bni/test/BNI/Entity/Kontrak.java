package com.bni.test.BNI.Entity;

import com.bni.test.BNI.Constant.StatusContract;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="kontrak")
public class Kontrak {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pegawai_id")
    private Pegawai pegawai;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "jabatan_id")
    private Jabatan jabatan;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cabang_id")
    private Cabang cabang;

    @Column(nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Jakarta")
    private LocalDate tanggalMulai;

    private LocalDate tanggalAkhir;

    @Enumerated(EnumType.STRING)
    private StatusContract statusContract;

    @PrePersist
    protected void onCreate() {
        if (tanggalMulai == null) {
            tanggalMulai = LocalDate.now();
        }
    }
}
