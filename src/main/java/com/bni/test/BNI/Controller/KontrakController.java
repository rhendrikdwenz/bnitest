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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/kontrak")
public class KontrakController {
    @Autowired
    private KontrakService kontrakService;


    @PostMapping
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
    public ResponseEntity<?> getById(@PathVariable String id){
        Kontrak findKontrak = kontrakService.getById(id);
        WebResponse<Kontrak> response = WebResponse.<Kontrak>builder()
                .status("Sukses mengambil data Kontrak")
                .data(findKontrak)
                .build();
        return ResponseEntity.ok(response);
    }
    @DeleteMapping(path = "/{id}")
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

   /* @GetMapping("/findByNamaPegawai")
    public List<Kontrak> findByNamaPegawai(@RequestParam String fullName) {
        return kontrakService.findKontrakByNamaPegawai(fullName);
    }*/
    @GetMapping("/findByNamaPegawai")
    public ResponseEntity<WebResponse<List<Kontrak>>> findByNamaPegawai(@RequestParam String fullName) {
        List<Kontrak> kontraks = kontrakService.findKontrakByNamaPegawai(fullName);
        if (kontraks.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(WebResponse.<List<Kontrak>>builder()
                            .status(HttpStatus.NO_CONTENT.getReasonPhrase())
                            .message("Tidak ada data kontrak yang ditemukan untuk nama " + fullName + " pegawai tersebut")
                            .data(kontraks)
                            .build());
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(WebResponse.<List<Kontrak>>builder()
                        .status(HttpStatus.OK.getReasonPhrase())
                        .message("Sukses menampilkan data dengan nama: " + fullName)
                        .data(kontraks)
                        .build());
    }


    @GetMapping("/findByEmailPegawai")
    public List<Kontrak> findByEmailPegawai(@RequestParam String email) {
        return kontrakService.findKontrakByEmailPegawai(email);
    }

    @PutMapping("/updateByNamaPegawai")
    public Kontrak updateKontrakByNamaPegawai(@RequestParam String fullName, @RequestBody KontrakRequest kontrakRequest) {
        return kontrakService.updateKontrakByNamaPegawai(fullName, kontrakRequest);
    }

    /*@PutMapping("/update-kontrak/{kontrakId}")
    public ResponseEntity<?> updateTanggalKontrak(
            @PathVariable String kontrakId, @RequestBody UpdateRequest updateRequest) {

        // Panggil service untuk memperbarui tanggal kontrak
        Kontrak response = kontrakService.updateTanggalKontrak(
                kontrakId,
                updateRequest.getTanggalAkhir()
        );

        // Mengembalikan response dengan data yang telah diperbarui
        return ResponseEntity.ok(response);

    }*/
    @PutMapping("/update-kontrak/{kontrakId}")
    public ResponseEntity<?> updateTanggalKontrak(
            @PathVariable String kontrakId, @RequestBody UpdateRequest updateRequest) {

        // Panggil service untuk memperbarui tanggal kontrak
        Kontrak response = kontrakService.updateTanggalKontrak(
                kontrakId,
                updateRequest.getTanggalAkhir()
        );

        // Mengembalikan response dengan data yang telah diperbarui
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(WebResponse.<Kontrak>builder()
                        .status(HttpStatus.OK.getReasonPhrase())
                        .message("Sukses Memperbaharui kontrak")
                        .data(response)
                        .build());

    }

    /*@GetMapping("/expired")
    public ResponseEntity<List<Kontrak>> getExpiredKontrak() {
        List<Kontrak> expiredKontraks = kontrakService.findKontrakByStatusExpired();
        if (expiredKontraks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(expiredKontraks, HttpStatus.OK);
    }*/
    @GetMapping("/expired")
    public ResponseEntity<WebResponse<List<Kontrak>>> getExpiredKontrak() {
        List<Kontrak> expiredKontraks = kontrakService.findKontrakByStatusExpired();
        if (expiredKontraks.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(WebResponse.<List<Kontrak>>builder()
                            .status(HttpStatus.NO_CONTENT.getReasonPhrase())
                            .message("Tidak ada data kontrak yang Expired")
                            .data(expiredKontraks)
                            .build());
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(WebResponse.<List<Kontrak>>builder()
                        .status(HttpStatus.OK.getReasonPhrase())
                        .message("Sukses mengambil data kontrak yang Expired")
                        .data(expiredKontraks)
                        .build());
    }

    /*@GetMapping("/active")
    public ResponseEntity<List<Kontrak>> getActiveKontrak() {
        List<Kontrak> expiredKontraks = kontrakService.findKontrakByStatusActive();
        if (expiredKontraks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(expiredKontraks, HttpStatus.OK);
    }*/
    @GetMapping("/active")
    public ResponseEntity<WebResponse<List<Kontrak>>> getActiveKontrak() {
        List<Kontrak> activeKontraks = kontrakService.findKontrakByStatusActive();
        if (activeKontraks.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NO_CONTENT)
                    .body(WebResponse.<List<Kontrak>>builder()
                            .status(HttpStatus.NO_CONTENT.getReasonPhrase())
                            .message("Tidak ada data kontrak yang aktif")
                            .data(activeKontraks)
                            .build());
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(WebResponse.<List<Kontrak>>builder()
                        .status(HttpStatus.OK.getReasonPhrase())
                        .message("Sukses mengambil data kontrak yang Active")
                        .data(activeKontraks)
                        .build());
    }


    //EXCELL :


}