package com.example.mspayment.criteria;

import lombok.Data;

@Data
public class PageCriteria {
    private Integer pageNumber=0;
    private Integer count=5;
    private String sortBy = "createdAt";
    private String sortDirection = "DESC";
}
