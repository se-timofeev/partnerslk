package ru.planetnails.partnerslk.repository.itemRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.planetnails.partnerslk.model.item.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, CustomItemRepository {

    @Query("Select i from Item i where i.parentId = ?1 and i.isOutOfStock = false and i.isGroup = false")
    Page<Item> findItemsByParentId(String parentId, Pageable pageable);

    @Query("Select i from Item i where i.isOutOfStock = false and i.isGroup = false")
    Page<Item> findItems(PageRequest pageRequest);
}
