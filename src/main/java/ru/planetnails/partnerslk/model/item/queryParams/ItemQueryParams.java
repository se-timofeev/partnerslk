package ru.planetnails.partnerslk.model.item.queryParams;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ItemQueryParams {

    private String name;
    private String description;
    private List<String> countries;
    private String vendorCode;
    private Double minPrice;
    private Double maxPrice;
}
