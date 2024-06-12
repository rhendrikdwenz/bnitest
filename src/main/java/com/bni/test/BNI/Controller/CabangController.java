package com.bni.test.BNI.Controller;

import com.bni.test.BNI.Entity.Cabang;
import com.bni.test.BNI.Entity.Jabatan;
import com.bni.test.BNI.Model.Request.CabangRequest;
import com.bni.test.BNI.Model.Request.JabatanRequest;
import com.bni.test.BNI.Model.Response.PagingResponse;
import com.bni.test.BNI.Model.Response.WebResponse;
import com.bni.test.BNI.Service.CabangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cabang")
public class CabangController {
    @Autowired
    private CabangService cabangService;

    @PostMapping
    public ResponseEntity<?> tambahCabang(@RequestBody CabangRequest cabangRequest) {
        Cabang newCabang = cabangService.tambahCabang(cabangRequest);
        WebResponse<Cabang> response = WebResponse.<Cabang>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Sukses menambahkan Data Cabang")
                .data(newCabang)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllCabang(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ){
        Page<Cabang> cabangList = cabangService.getAllCabang(page, size);
        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .totalPages(cabangList.getTotalPages())
                .totalElements(cabangList.getTotalElements())
                .build();
        WebResponse<List<Cabang>> response = WebResponse.<List<Cabang>> builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Sukses mengambil semua data Cabang")
                .paging(pagingResponse)
                .data(cabangList.getContent())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        Cabang findCabang = cabangService.getById(id);
        WebResponse<Cabang> response = WebResponse.<Cabang>builder()
                .status("Sukses mengambil data Cabang")
                .data(findCabang)
                .build();
        return ResponseEntity.ok(response);
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id){
        cabangService.deleteById(id);
        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Berhasil hapus data Cabang")
                .data("OK")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Cabang cabang){
        Cabang updateCabang = cabangService.update(cabang);
        WebResponse<Cabang> response = WebResponse.<Cabang>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Succes update Cabang")
                .data(updateCabang)
                .build();
        return ResponseEntity.ok(response);
    }
}
