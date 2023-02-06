package ru.planetnails.partnerslk.repository.itemRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.planetnails.partnerslk.model.item.Item;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, CustomItemRepository {

    @Query("select i from Item i where i.groupId in ?1 and i.isOutOfStock = false")
    Page<Item> findItemsByFirstLevelGroup(List<String> groupIds, Pageable pageable);

    @Query("select i from Item i where i.isOutOfStock = false")
    Page<Item> findAllNotOutOfStock(PageRequest pageRequest);

    @Query("select i from Item i where i.groupId = ?1 and i.isOutOfStock = false")
    Page<Item> findItemsBySecondLevelGroup(String groupId, PageRequest pageRequest);
}
