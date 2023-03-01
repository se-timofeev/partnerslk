package ru.planetnails.partnerslk.model.item.queryParams;

import org.springframework.data.domain.Sort;
import ru.planetnails.partnerslk.exception.BadRequestException;

public enum SortType {
    ASC, DESC;

    public static Sort.Direction getSortType(String value) {
        if(value.equalsIgnoreCase(ASC.toString())) return Sort.Direction.ASC;
        else if (value.equalsIgnoreCase(DESC.toString())) return Sort.Direction.DESC;
        else throw new BadRequestException("Некорректно указано направление сортировки");
    }
}
