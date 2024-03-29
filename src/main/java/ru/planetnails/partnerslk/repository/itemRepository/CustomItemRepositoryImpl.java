package ru.planetnails.partnerslk.repository.itemRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.planetnails.partnerslk.model.item.Item;
import ru.planetnails.partnerslk.model.item.queryParams.ItemQueryParams;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomItemRepositoryImpl implements CustomItemRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Item> getItemByParams(ItemQueryParams params, Integer from, Integer size) {
        var cb = em.getCriteriaBuilder();
        var query = cb.createQuery(Item.class);
        Root<Item> eventRoot = query.from(Item.class);
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(eventRoot.get("isOutOfStock"), false));
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
        final TypedQuery<Item> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult(from);
        typedQuery.setMaxResults(size);
        return typedQuery.getResultList();
    }

    @Override
    public List<Item> findAll() {
            String queryText=
                    "select  top 20 * " +
                            "from items  as t1 " +
                            " ";

            Query query= em.createNativeQuery(queryText,Item.class);
            @SuppressWarnings("unchecked")
            List<Item> list= (List<Item>)  query.getResultList();
            return list;
    }

}
