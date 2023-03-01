package ru.planetnails.partnerslk.model.item.queryParams;

import ru.planetnails.partnerslk.exception.BadRequestException;

public enum ItemSortBy {

    NAME, VENDORCODE, PRICE, COUNTRYOFORIGIN;

    public static String getSortBy(String value) {
        if (!value.equalsIgnoreCase(NAME.toString())) return "name";
        if (!value.equalsIgnoreCase(VENDORCODE.toString())) return "vendorCode";
        if (!value.equalsIgnoreCase(PRICE.toString())) return "price";
        if (!value.equalsIgnoreCase(COUNTRYOFORIGIN.toString())) return "countryOfOrigin";
        throw new BadRequestException("Некорректно указан тип сортировки");
    }


}
