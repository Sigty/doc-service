package com.itacademy.database.dao;


import com.itacademy.database.dto.FilterPartBasicDto;
import com.itacademy.database.dto.ViewPartBasicDto;
import com.itacademy.database.entity.Manufacturer;
import com.itacademy.database.entity.Manufacturer_;
import com.itacademy.database.entity.Part;
import com.itacademy.database.entity.Part_;
import com.itacademy.database.util.SessionManager;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PartDao implements BaseDao<Integer, Part> {

    private static final PartDao INSTANCE = new PartDao();

    public static PartDao getInstance() {
        return INSTANCE;
    }

    private List<Predicate> getPredicates(FilterPartBasicDto filter, CriteriaBuilder cb,
                                          Root<Part> root, Join<Part, Manufacturer> manufacturerJoin) {

        final String zero = "";
        List<Predicate> predicatePart = new ArrayList<>();
        if (filter.getPartNumber() != null && !(zero == filter.getPartNumber())) {
            predicatePart.add(cb.like(root.get(Part_.partNumber), "%" + filter.getPartNumber() + "%"));
        }
        if (filter.getType() != null && !(zero == filter.getType())) {
            predicatePart.add(cb.equal(root.get(Part_.type), filter.getType()));
        }
        if (filter.getSort() != null && !(zero == filter.getSort())) {
            predicatePart.add(cb.equal(root.get(Part_.sort), filter.getSort()));
        }
        if (filter.getManufacturer() != null && !(zero == filter.getManufacturer())) {
            predicatePart.add(cb.equal(manufacturerJoin.get(Manufacturer_.name), filter.getManufacturer()));
        }
        return predicatePart;
    }

    public List<ViewPartBasicDto> findListPart(FilterPartBasicDto filter) {
        try (Session session = SessionManager.getSession()) {
            CriteriaQuery<ViewPartBasicDto> criteria = getCriteria(filter, session);
            List<ViewPartBasicDto> parts = session.createQuery(criteria)
                    .setFirstResult((filter.getPage() - 1) * filter.getRecordsPerPage())
                    .setMaxResults(filter.getRecordsPerPage()).list();
            session.getTransaction().commit();
            return parts;
        }
    }

    public List<ViewPartBasicDto> findAllListPart(FilterPartBasicDto filter) {
        try (Session session = SessionManager.getSession()) {
            CriteriaQuery<ViewPartBasicDto> criteria = getCriteria(filter, session);
            List<ViewPartBasicDto> parts = session.createQuery(criteria).list();
            session.getTransaction().commit();
            return parts;
        }
    }

    private CriteriaQuery<ViewPartBasicDto> getCriteria(FilterPartBasicDto filter, Session session) {
        session.beginTransaction();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<ViewPartBasicDto> criteria = cb.createQuery(ViewPartBasicDto.class);
        Root<Part> root = criteria.from(Part.class);
        Join<Part, Manufacturer> manufacturerJoin = root.join(Part_.manufacturer);
        List<Predicate> predicatePart = getPredicates(filter, cb, root, manufacturerJoin);
        Predicate[] predicatePartArray = new Predicate[predicatePart.size()];
        predicatePart.toArray(predicatePartArray);
        return criteria
                .select(
                        cb.construct
                                (ViewPartBasicDto.class,
                                        root.get(Part_.partNumber),
                                        root.get(Part_.description),
                                        root.get(Part_.type),
                                        root.get(Part_.sort),
                                        root.get(Part_.createPartDate),
                                        manufacturerJoin.get(Manufacturer_.name)
                                )
                ).where(predicatePartArray);
    }
}



