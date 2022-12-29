package ru.planetnails.partnerslk.repository.itemRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import ru.planetnails.partnerslk.model.item.Item;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomItemRepositoryImpl implements CustomItemRepository{

    @PersistenceContext
    private final EntityManager em;
    @Override
    public List<Item> getFilteredGroups(Integer level, String parentId) {
        var cb = em.getCriteriaBuilder();
        var query = cb.createQuery(Item.class);
        Root<Item> eventRoot = query.from(Item.class);
        List<Predicate> predicates = new ArrayList<>();
        if (level != null) {
            predicates.add(cb.equal(eventRoot.get("level"), level));
        }
        if (parentId != null) {
            predicates.add(cb.equal(eventRoot.get("parentId"), parentId));
        }
        predicates.add(cb.equal(eventRoot.get("isGroup"), true));
        query.where(predicates.toArray(new Predicate[0]));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Item> getFilteredItems(String groupId, PageRequest pageRequest) {
        var cb = em.getCriteriaBuilder();
        var query = cb.createQuery(Item.class);
        Root<Item> eventRoot = query.from(Item.class);
        List<Predicate> predicates = new ArrayList<>();
        if (groupId != null) {
            predicates.add(cb.equal(eventRoot.get("parentId"), groupId));
        }
        query.where(predicates.toArray(new Predicate[0]));
        return em.createQuery(query).getResultList();
    }
}
