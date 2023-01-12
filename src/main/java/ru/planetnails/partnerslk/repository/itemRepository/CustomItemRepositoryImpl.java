package ru.planetnails.partnerslk.repository.itemRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.planetnails.partnerslk.model.item.Item;
import ru.planetnails.partnerslk.model.item.ItemQueryParams;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomItemRepositoryImpl implements CustomItemRepository {

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
    public List<Item> getFilteredItems(String groupId, Integer from, Integer size) {
        var cb = em.getCriteriaBuilder();
        var criteriaQuery  = cb.createQuery(Item.class);
        Root<Item> eventRoot = criteriaQuery.from(Item.class);
        List<Predicate> predicates = new ArrayList<>();
        if (groupId != null) {
            predicates.add(cb.equal(eventRoot.get("parentId"), groupId));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        final TypedQuery<Item> query = em.createQuery(criteriaQuery);

        query.setFirstResult(from);
        query.setMaxResults(size);

        return query.getResultList();
    }

    @Override
    public List<Item> getItemByParams(ItemQueryParams params) {
        var cb = em.getCriteriaBuilder();
        var query = cb.createQuery(Item.class);
        Root<Item> eventRoot = query.from(Item.class);
        List<Predicate> predicates = new ArrayList<>();
        if (params.getName() != null)
            predicates.add(cb.like(cb.lower(eventRoot.get("name")), "%" + params.getName() + "%"));
        if (params.getDescription() != null)
            predicates.add(cb.like(cb.lower(eventRoot.get("description")), "%" + params.getDescription() + "%"));
        if (params.getCountries() != null && params.getCountries().size() > 0)
            predicates.add(eventRoot.get("countryOfOrigin").in(params.getCountries()));
        if (params.getVendorCode() != null)
            predicates.add(cb.equal(eventRoot.get("vendorCode"), params.getVendorCode()));
        if (params.getMinPrice() != null && params.getMaxPrice() != null)
            predicates.add(cb.between(eventRoot.get("price").get("sale"),
                    params.getMinPrice(), params.getMaxPrice()));
        else if (params.getMinPrice() != null)
            predicates.add(cb.greaterThanOrEqualTo(eventRoot.get("price").get("sale"), params.getMinPrice()));
        else if (params.getMaxPrice() != null)
            predicates.add(cb.lessThanOrEqualTo(eventRoot.get("price").get("sale"), params.getMaxPrice()));
        query.where(predicates.toArray(new Predicate[0]));
        return em.createQuery(query).getResultList();
    }
}
