package com.servicery.service.modelDto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class DefaultResponse<T> {
    private String message = "success";
    private long status = 200;
    private boolean ok = true;
    private List<T> objectsList = new ArrayList<>();
}
