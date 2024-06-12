package com.bni.test.BNI.Model.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PagingResponse {
    private Integer page;
    private Integer size;
    private Integer totalPages;
    private Long totalElements;
}