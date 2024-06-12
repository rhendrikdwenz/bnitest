package com.bni.test.BNI.Controller;

import com.bni.test.BNI.Entity.Cabang;
import com.bni.test.BNI.Entity.Kontrak;
import com.bni.test.BNI.Model.Request.CabangRequest;
import com.bni.test.BNI.Model.Request.KontrakRequest;
import com.bni.test.BNI.Model.Response.KontrakResponse;
import com.bni.test.BNI.Model.Response.PagingResponse;
import com.bni.test.BNI.Model.Response.WebResponse;
import com.bni.test.BNI.Service.CabangService;
import com.bni.test.BNI.Service.KontrakService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

//    @PutMapping
//    public ResponseEntity<?> update(@RequestBody Kontrak kontrak){
//        Kontrak updateKontrak = kontrakService.update(kontrak);
//        WebResponse<Kontrak> response = WebResponse.<Kontrak>builder()
//                .status(HttpStatus.CREATED.getReasonPhrase())
//                .message("Succes update Kontrak")
//                .data(updateKontrak)
//                .build();
//        return ResponseEntity.ok(response);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<WebResponse<Kontrak>> update(@PathVariable("id") Long id, @RequestBody Kontrak kontrak) {
        // Pastikan ID yang dikirimkan sesuai dengan ID dari objek kontrak yang akan diperbarui
        if (!id.equals(kontrak.getId())) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Kontrak updateKontrak = kontrakService.update(kontrak);
            WebResponse<Kontrak> response = WebResponse.<Kontrak>builder()
                    .status(HttpStatus.OK.getReasonPhrase())
                    .message("Success update Kontrak")
                    .data(updateKontrak)
                    .build();
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            // Tangani jika kontrak tidak ditemukan
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new WebResponse<>("Kontrak not found with id: " + id, null));
        } catch (Exception e) {
            // Tangani pengecualian lain yang mungkin terjadi saat memperbarui kontrak
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new WebResponse<>("Error occurred while updating Kontrak", null));
        }
    }
}
