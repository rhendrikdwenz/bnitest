package com.bni.test.BNI.Entity;
import com.bni.test.BNI.Constant.Gender;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="pegawai")
public class Pegawai {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String fullName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phoneNumber;

    private String address;
}