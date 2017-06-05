package net.news.dao;

import net.news.domain.news.News;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class DaoHelperSpecifications {
    public static Specification<News> findByAttrAndJoin(List<Pair<String, Object>> attrs, List<String> columnsForJoin) {
        return (Root<News> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            Join<Object, Object> join = null;
            for (String columnForJoin : columnsForJoin) {
                if (join == null) {
                    join = root.join(columnForJoin);
                } else {
                    join.join(columnForJoin);
                }
            }
            List<Predicate> predicates = new ArrayList<>(attrs.size());
            for (Pair<String, Object> attr : attrs) {
                predicates.add(criteriaBuilder.equal(join.get(attr.getFirst()), attr.getSecond()));
            }
            Predicate[] predicates1 = new Predicate[predicates.size()];
            for (int i = 0; i < predicates1.length; i++) {
                predicates1[i] = predicates.get(i);
            }
            return criteriaBuilder.and(predicates1);
        };
    }

//    return builder.like(root.get("state").get("country").get("name"), name.trim());

}
