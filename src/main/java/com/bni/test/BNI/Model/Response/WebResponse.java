package com.bni.test.BNI.Model.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class WebResponse<T> {
    private String status;
    private String message;
    private PagingResponse paging;
    private T data;
}
