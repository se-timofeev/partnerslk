package ru.planetnails.partnerslk.repository.itemRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.planetnails.partnerslk.model.item.Item;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long>, CustomItemRepository {
    @Override
    Optional<Item> findById(Long id);


    List<Item> getFilteredItems(String groupId, PageRequest pageRequest);
}
