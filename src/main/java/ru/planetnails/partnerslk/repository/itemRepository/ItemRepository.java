package ru.planetnails.partnerslk.repository.itemRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.planetnails.partnerslk.model.item.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, CustomItemRepository {

}
