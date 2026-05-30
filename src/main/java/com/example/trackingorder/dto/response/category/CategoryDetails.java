package com.example.trackingorder.dto.response.category;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryDetails {
    private String id;
    private String parentId;
    private String name;
    private List<CategorySummary> children;
}
