package com.bni.test.BNI.Controller;

import com.bni.test.BNI.Entity.Pegawai;
import com.bni.test.BNI.Model.Request.PegawaiRequest;
import com.bni.test.BNI.Model.Response.PagingResponse;
import com.bni.test.BNI.Model.Response.WebResponse;
import com.bni.test.BNI.Service.PegawaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pegawai")
public class PegawaiController {
    @Autowired
    private PegawaiService pegawaiService;
    @PostMapping
    public ResponseEntity<?> tambahPegawai(@RequestBody PegawaiRequest pegawaiRequest) {
        Pegawai newPegawai = pegawaiService.tambahPegawai(pegawaiRequest);
        WebResponse<Pegawai> response = WebResponse.<Pegawai>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Success add data")
                .data(newPegawai)
                .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<?> getAllPegawai(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ){
        Page<Pegawai> pegawaiList = pegawaiService.getAllPegawai(page, size);
        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .totalPages(pegawaiList.getTotalPages())
                .totalElements(pegawaiList.getTotalElements())
                .build();
        WebResponse<List<Pegawai>> response = WebResponse.<List<Pegawai>> builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Succes Get List Nasabah")
                .paging(pagingResponse)
                .data(pegawaiList.getContent())
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        Pegawai findPegawai = pegawaiService.getById(id);
        WebResponse<Pegawai> response = WebResponse.<Pegawai>builder()
                .status("Success Get Nasabah")
                .data(findPegawai)
                .build();
        return ResponseEntity.ok(response);
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id){
        pegawaiService.deleteById(id);
        WebResponse<String> response = WebResponse.<String>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Succes delete nasabah")
                .data("OK")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Pegawai pegawai){
        Pegawai updatePegawai = pegawaiService.update(pegawai);
        WebResponse<Pegawai> response = WebResponse.<Pegawai>builder()
                .status(HttpStatus.CREATED.getReasonPhrase())
                .message("Succes update nasabah")
                .data(updatePegawai)
                .build();
        return ResponseEntity.ok(response);
    }
}