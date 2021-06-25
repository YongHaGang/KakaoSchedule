package com.yongha.toy1.dto;

import com.yongha.toy1.entity.Test;
import lombok.Data;

@Data
public class TestDTO {
    private Long id;
    private String text;

    public TestDTO(Test test) {
        this.id = test.getId();
        this.text = test.getText();
    }
}
