package com.itacademy.database.repository;

import com.itacademy.database.dto.FilterPartBasicDto;
import com.itacademy.database.dto.ViewPartBasicDto;
import com.itacademy.database.entity.Manufacturer;
import com.itacademy.database.entity.Manufacturer_;
import com.itacademy.database.entity.Part;
import com.itacademy.database.entity.Part_;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;

public class ExtendedPartRepositoryImpl implements ExtendedPartRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<ViewPartBasicDto> filterListPart(FilterPartBasicDto filter) {
        CriteriaQuery<ViewPartBasicDto> criteria = getCriteria(filter);
        List<ViewPartBasicDto> parts = entityManager.createQuery(criteria)
                .setFirstResult((filter.getPage() - 1) * filter.getRecordsPerPage())
                .setMaxResults(filter.getRecordsPerPage())
                .getResultList();
        return parts;
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

    public Long findCountPart(FilterPartBasicDto filter) {
        CriteriaQuery<Long> criteria = getCriteriaCount(filter);
        Long countParts = entityManager.createQuery(criteria).getSingleResult();
        return countParts;
    }

    private CriteriaQuery<ViewPartBasicDto> getCriteria(FilterPartBasicDto filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ViewPartBasicDto> criteria = cb.createQuery(ViewPartBasicDto.class);
        Root<Part> root = criteria.from(Part.class);
        Join<Part, Manufacturer> manufacturerJoin = root.join(Part_.manufacturer);
        List<Predicate> predicatePart = getPredicates(filter, cb, root, manufacturerJoin);
        Predicate[] predicatePartArray = new Predicate[predicatePart.size()];
        predicatePart.toArray(predicatePartArray);
        return criteria
                .select(cb.construct(ViewPartBasicDto.class,
                        root.get(Part_.partNumber),
                        root.get(Part_.description),
                        root.get(Part_.type),
                        root.get(Part_.sort),
                        root.get(Part_.createPartDate),
                        manufacturerJoin.get(Manufacturer_.name))
                ).where(predicatePartArray);
    }

    private CriteriaQuery<Long> getCriteriaCount(FilterPartBasicDto filter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
        Root<Part> root = criteria.from(Part.class);
        Join<Part, Manufacturer> manufacturerJoin = root.join(Part_.manufacturer);
        List<Predicate> predicatePart = getPredicates(filter, cb, root, manufacturerJoin);
        Predicate[] predicatePartArray = new Predicate[predicatePart.size()];
        predicatePart.toArray(predicatePartArray);
        return criteria
                .select(cb.count(root))
                .where(predicatePartArray);
    }
}