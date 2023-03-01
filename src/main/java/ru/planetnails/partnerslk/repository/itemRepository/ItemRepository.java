package ru.planetnails.partnerslk.repository.itemRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.planetnails.partnerslk.model.item.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, String>, CustomItemRepository {

    Page<Item> findItemsByGroupId(String groupId, Pageable pageable);

    @Query("select i from Item i where i.groupId in ?1")
    Page<Item> findItemsFirstLevelGroup(List<String> groupIds, Pageable pageable);
}
