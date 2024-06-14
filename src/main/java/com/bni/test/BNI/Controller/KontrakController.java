package com.bni.test.BNI.Controller;

import com.bni.test.BNI.Entity.Cabang;
import com.bni.test.BNI.Entity.Kontrak;
import com.bni.test.BNI.Model.Request.CabangRequest;
import com.bni.test.BNI.Model.Request.KontrakRequest;
import com.bni.test.BNI.Model.Request.UpdateRequest;
import com.bni.test.BNI.Model.Response.KontrakResponse;
import com.bni.test.BNI.Model.Response.PagingResponse;
import com.bni.test.BNI.Model.Response.WebResponse;
import com.bni.test.BNI.Service.CabangService;
import com.bni.test.BNI.Service.KontrakService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/kontrak")
@AllArgsConstructor

public class KontrakController {
    @Autowired
    private KontrakService kontrakService;


    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public ResponseEntity<?> tambahKontrak(@RequestBody KontrakRequest kontrakRequest) {
        KontrakResponse newKontrak = kontrakService.tambahKontrak(kontrakRequest);
        WebResponse<KontrakResponse> response = WebResponse.<KontrakResponse>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Sukses menambahkan Kontrak baru")
                .data(newKontrak)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN', 'PEGAWAI')")
    public ResponseEntity<?> getAllCabang(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ){
        Page<Kontrak> kontrakList = kontrakService.getAllKontrak(page, size);
        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .totalPages(kontrakList.getTotalPages())
                .totalElements(kontrakList.getTotalElements())
                .build();
        WebResponse<List<Kontrak>> response = WebResponse.<List<Kontrak>> builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Sukses mengambil semua data Kontrak")
                .paging(pagingResponse)
                .data(kontrakList.getContent())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN', 'PEGAWAI')")
    public ResponseEntity<?> getById(@PathVariable String id){
        Kontrak findKontrak = kontrakService.getById(id);
        WebResponse<Kontrak> response = WebResponse.<Kontrak>builder()
                .status("Sukses mengambil data Kontrak")
                .data(findKontrak)
                .build();
        return ResponseEntity.ok(response);
    }
    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public ResponseEntity<?> deleteById(@PathVariable String id){
        kontrakService.deleteById(id);
        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Berhasil hapus data Kontrak")
                .data("OK")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{kontrakId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public ResponseEntity<Kontrak> updateKontrak(
            @PathVariable String kontrakId,
            @RequestBody KontrakRequest kontrakRequest) {
        try {
            Kontrak updatedKontrak = kontrakService.updateKontrak(kontrakId, kontrakRequest);
            return ResponseEntity.ok(updatedKontrak);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Adjust error handling as needed
        }
    }

    @GetMapping("/findByNamaPegawai")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN', 'PEGAWAI')")
    public List<Kontrak> findByNamaPegawai(@RequestParam String fullName) {
        return kontrakService.findKontrakByNamaPegawai(fullName);
    }

    @GetMapping("/findByEmailPegawai")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN', 'PEGAWAI')")
    public List<Kontrak> findByEmailPegawai(@RequestParam String email) {
        return kontrakService.findKontrakByEmailPegawai(email);
    }

    @PutMapping("/updateByNamaPegawai")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public Kontrak updateKontrakByNamaPegawai(@RequestParam String fullName, @RequestBody KontrakRequest kontrakRequest) {
        return kontrakService.updateKontrakByNamaPegawai(fullName, kontrakRequest);
    }

    @PutMapping("/update-kontrak/{kontrakId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    public ResponseEntity<?> updateTanggalKontrak(
            @PathVariable String kontrakId, @RequestBody UpdateRequest updateRequest) {

        // Panggil service untuk memperbarui tanggal kontrak
        Kontrak response = kontrakService.updateTanggalKontrak(
                kontrakId,
                updateRequest.getTanggalAkhir()
        );

        // Mengembalikan response dengan data yang telah diperbarui
        return ResponseEntity.ok(response);

    }

    @GetMapping("/expired")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN', 'PEGAWAI')")
    public ResponseEntity<List<Kontrak>> getExpiredKontrak() {
        List<Kontrak> expiredKontraks = kontrakService.findKontrakByStatusExpired();
        if (expiredKontraks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(expiredKontraks, HttpStatus.OK);
    }

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN', 'PEGAWAI')")
    public ResponseEntity<List<Kontrak>> getActiveKontrak() {
        List<Kontrak> expiredKontraks = kontrakService.findKontrakByStatusActive();
        if (expiredKontraks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(expiredKontraks, HttpStatus.OK);
    }

    //EXCELL :


}