package com.itacademy.database.dao;


import com.itacademy.database.dto.ViewPartBasicDto;
import com.itacademy.database.entity.Manufacturer;
import com.itacademy.database.entity.Manufacturer_;
import com.itacademy.database.entity.Part;
import com.itacademy.database.entity.Part_;
import com.itacademy.database.util.SessionManager;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
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

    public List<ViewPartBasicDto> findListPart(int page, int recordsPerPage,
                                               String partNumber, String sort, String manufacturer) {
        try (Session session = SessionManager.getSession()) {
            session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<ViewPartBasicDto> criteria = cb.createQuery(ViewPartBasicDto.class);
            Root<Part> root = criteria.from(Part.class);
            Join<Part, Manufacturer> manufacturerJoin = root.join(Part_.manufacturer);
            criteria.select(
                    cb.construct(ViewPartBasicDto.class,
                            root.get(Part_.partNumber),
                            root.get(Part_.description),
                            root.get(Part_.type),
                            root.get(Part_.sort),
                            root.get(Part_.createPartDate),
                            manufacturerJoin.get(Manufacturer_.name)
                    )
            ).where(
                    cb.like(root.get(Part_.partNumber), "%" + partNumber + "%"),
                    cb.like(root.get(Part_.sort), "%" + sort + "%"),
                    cb.like(manufacturerJoin.get(Manufacturer_.name), "%" + manufacturer + "%")
            );
            List<ViewPartBasicDto> parts = session.createQuery(criteria)
                    .setFirstResult((page - 1) * recordsPerPage)
                    .setMaxResults(recordsPerPage).list();
            session.getTransaction().commit();
            return parts;
        }
    }
}



