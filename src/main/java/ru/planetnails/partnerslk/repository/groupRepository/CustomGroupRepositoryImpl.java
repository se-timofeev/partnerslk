package ru.planetnails.partnerslk.repository.groupRepository;

import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.planetnails.partnerslk.model.group.Group;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomGroupRepositoryImpl implements CustomGroupRepository {

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

    @Override
    public List<Group> getRootGroups() {
        String queryText=
                "select  * " +
                        "from Groups  as t1 " +
                        " where level=1";

        Query query= em.createNativeQuery(queryText,Group.class);
        @SuppressWarnings("unchecked")
        List<Group> list= (List<Group>)  query.getResultList();
        return list;

    }

    @Override
    public List<Group> getChildGroups(Group group) {
        String queryText=
                "select  *" +
                        "from Groups  as t1 " +
                        " where group_id=?1";

        Query query = em.createNativeQuery(queryText,Group.class);
        query.setParameter(1,group.getId());
        @SuppressWarnings("unchecked")
        List<Group> list= (List<Group>)  query.getResultList();
        return list;
    }
}
