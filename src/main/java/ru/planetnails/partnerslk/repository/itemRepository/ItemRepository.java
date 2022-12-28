package ru.planetnails.partnerslk.repository.itemRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.planetnails.partnerslk.model.item.Item;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long>, CustomItemRepository {
    @Override
    Optional<Item> findById(Long id);

    @Query(value = "select p.id from items  as p WHERE p.id = ?1",nativeQuery = true)
   Long getItemIDByGuid1c(String guid1c);


}
