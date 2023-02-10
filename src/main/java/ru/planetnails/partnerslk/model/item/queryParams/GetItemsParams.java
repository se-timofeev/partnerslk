package ru.planetnails.partnerslk.model.item.queryParams;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetItemsParams {

    Integer from;
    Integer size;
    String partnerId;
    String groupId;
    Integer level;
    String sortBy;
    String sortType;
}
