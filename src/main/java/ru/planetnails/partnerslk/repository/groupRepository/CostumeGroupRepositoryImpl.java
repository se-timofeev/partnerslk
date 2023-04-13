package ru.planetnails.partnerslk.repository.groupRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.planetnails.partnerslk.model.group.Group;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CostumeGroupRepositoryImpl implements CostumeGroupRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Group> getFilteredGroups(Integer level, String groupId, Integer from, Integer size) {
        var cb = em.getCriteriaBuilder();
        var query = cb.createQuery(Group.class);
        Root<Group> eventRoot = query.from(Group.class);
        List<Predicate> predicates = new ArrayList<>();
        if (level != null) predicates.add(cb.equal(eventRoot.get("level"), level));
        if (groupId != null) predicates.add(cb.equal(eventRoot.get("groupId"), groupId));
        query.where(predicates.toArray(new Predicate[0]));
        final TypedQuery<Group> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult(from);
        typedQuery.setMaxResults(size);
        return typedQuery.getResultList();
    }
}
