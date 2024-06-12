package com.bni.test.BNI.Controller;

import com.bni.test.BNI.Entity.Jabatan;
import com.bni.test.BNI.Entity.Pegawai;
import com.bni.test.BNI.Model.Request.JabatanRequest;
import com.bni.test.BNI.Model.Request.PegawaiRequest;
import com.bni.test.BNI.Model.Response.PagingResponse;
import com.bni.test.BNI.Model.Response.WebResponse;
import com.bni.test.BNI.Service.JabatanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jabatan")
public class JabatanController {
    @Autowired
    private JabatanService jabatanService;

    @PostMapping
    public ResponseEntity<?> tambahJabatan(@RequestBody JabatanRequest jabatanRequest) {
        Jabatan newJabatan = jabatanService.tambahJabatan(jabatanRequest);
        WebResponse<Jabatan> response = WebResponse.<Jabatan>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Sukses menambahkan Jabatan")
                .data(newJabatan)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllJabatan(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ){
        Page<Jabatan> jabatanList = jabatanService.getAllJabatan(page, size);
        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .totalPages(jabatanList.getTotalPages())
                .totalElements(jabatanList.getTotalElements())
                .build();
        WebResponse<List<Jabatan>> response = WebResponse.<List<Jabatan>> builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Sukses mengambil semua data Jabatan")
                .paging(pagingResponse)
                .data(jabatanList.getContent())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        Jabatan findJabatan = jabatanService.getById(id);
        WebResponse<Jabatan> response = WebResponse.<Jabatan>builder()
                .status("Sukses mengambil data Jabatan")
                .data(findJabatan)
                .build();
        return ResponseEntity.ok(response);
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id){
        jabatanService.deleteById(id);
        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Berhasil hapus data Jabatan")
                .data("OK")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Jabatan jabatan){
        Jabatan updateJabatan = jabatanService.update(jabatan);
        WebResponse<Jabatan> response = WebResponse.<Jabatan>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Succes update nasabah")
                .data(updateJabatan)
                .build();
        return ResponseEntity.ok(response);
    }
}
