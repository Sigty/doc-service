package com.itacademy.database.util;

import com.itacademy.database.entity.Assembly;
import com.itacademy.database.entity.Department;
import com.itacademy.database.entity.Detail;
import com.itacademy.database.entity.DocPart;
import com.itacademy.database.entity.DocType;
import com.itacademy.database.entity.Document;
import com.itacademy.database.entity.Manufacturer;
import com.itacademy.database.entity.Part;
import com.itacademy.database.entity.Project;
import com.itacademy.database.entity.Role;
import com.itacademy.database.entity.User;
import com.itacademy.database.entity.UserDetail;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserTestDataImport {

    private static UserTestDataImport INSTANCE = new UserTestDataImport();

    public static UserTestDataImport getInstance() {
        return INSTANCE;
    }

    public void importUserData(SessionFactory sessionFactory) {
        try (Session session = sessionFactory.openSession()) {
            Role roleUser = saveRole(session, "user");
            Role manager = saveRole(session, "manager");
            Role admin = saveRole(session, "admin");
            Department QA = saveDepartment(session, "QA");
            Department DEV = saveDepartment(session, "DEV");
            UserDetail detailSveta = saveUserDetail(session, "Sveta", "Svtlova", "qe@e", DEV);
            UserDetail detailJoo = saveUserDetail(session, "Joo", "Ra", "ex@qwe", QA);
            UserDetail detailPetr = saveUserDetail(session, "Petr", "sa", "ex1@qwe", DEV);
            UserDetail detailKate = saveUserDetail(session, "Kate", "Kate", "ex2@qwe", QA);
            User Sveta = saveUser(session, "svet", "pass", roleUser, detailSveta);
            User Jo = saveUser(session, "jojo", "qwe123", manager, detailJoo);
            Manufacturer murata = saveManufacturer(session, "murata");
            Manufacturer td = saveManufacturer(session, "td");
            OffsetDateTime dataFirst = OffsetDateTime.parse("2019-05-03T01:02:03Z");
            OffsetDateTime dataLast = OffsetDateTime.parse("2019-05-03T01:02:03Z");
            Part firsPart = savePart(session, 1, "GRM188R71C104KA01D", "0.1 uF_10%_16V_X7R_0603",
                    "cap", "c", dataFirst, Sveta, murata);
            Part lastPart = savePart(session, 2, "RK73B1JTTD333J", "33k_5%_0.1W_0603",
                    "res", "r", dataFirst, Jo, td);
            Assembly assembly = saveAdType(session, "drawing", true);
            Detail detail = saveDtType(session, "solder", true);
            Document documentA = saveDocement(session, 1, "ad-1234567", dataFirst, Sveta, assembly);
            Document documentD = saveDocement(session, 2, "dt-7654321", dataLast, Jo, detail);
            DocPart docPartFirst = saveDocPart(session, new DocPart.Id(1, 1), 3, documentA, firsPart);
            DocPart docPartLast = saveDocPart(session, new DocPart.Id(2, 1), 2, documentD, lastPart);
            Project venera = saveProject(session, "venera");
            Project earth = saveProject(session, "earth");
            Set<Project> userProjects = new HashSet<>();
            userProjects.add(venera);
            userProjects.add(earth);
            User allPetr = saveUserAll(session, "petr", "qwe", admin, detailPetr, userProjects);
            User allKate = saveUserAll(session, "katekate", "ew", admin, detailKate, userProjects);
            Set<User> marsUser = new HashSet<>();
            marsUser.add(allPetr);
            marsUser.add(allKate);
            Project mars = saveProjectAll(session, "mars", marsUser);
        }
    }

    private DocPart saveDocPart(Session session, DocPart.Id id, Integer quantityPart, Document document, Part part) {
        DocPart docPart = new DocPart(id, quantityPart, document, part);
        session.save(docPart);
        return docPart;
    }

    private Document saveDocement(Session session, Integer id, String number, OffsetDateTime createDocDate, User docUser,
                                  DocType docType) {
        Document document = Document.builder()
                .id(id)
                .number(number)
                .createDocDate(createDocDate)
                .docUser(docUser)
                .docType(docType)
                .build();
        session.save(document);
        return document;
    }

    private Detail saveDtType(Session session, String name, Boolean detail) {
        Detail det = new Detail(name, detail);
        session.save(det);
        return det;
    }

    private Assembly saveAdType(Session session, String name, Boolean assembly) {
        Assembly ad = new Assembly(name, assembly);
        session.save(ad);
        return ad;
    }

    private Part savePart(Session session, Integer id, String partNumber, String description, String type,
                          String sort, OffsetDateTime createPartDate, User partUser, Manufacturer manufacturer) {
        Part part = Part.builder()
                .id(id)
                .partNumber(partNumber)
                .description(description)
                .type(type)
                .sort(sort)
                .createPartDate(createPartDate)
                .partUser(partUser)
                .manufacturer(manufacturer)
                .build();
        session.save(part);
        return part;
    }

    private Manufacturer saveManufacturer(Session session, String name) {
        Manufacturer manufacturer = new Manufacturer(name);
        session.save(manufacturer);
        return manufacturer;
    }

    private Project saveProjectAll(Session session, String name, Set<User> projectUser) {
        Project project = new Project(name, projectUser);
        session.save(project);
        return project;
    }

    private Project saveProject(Session session, String name) {
        Project project = new Project(name);
        session.save(project);
        return project;
    }

    private User saveUserAll(Session session, String login, String password, Role roles, UserDetail userDetail,
                             Set<Project> projects) {
        User user = new User(login, password, roles, userDetail, projects);
        session.save(user);
        return user;
    }

    private User saveUser(Session session, String login, String password, Role roles, UserDetail userDetail) {
        User user = new User(login, password, roles, userDetail);
        session.save(user);
        return user;
    }

    private UserDetail saveUserDetail(Session session, String firstName, String lastName, String email, Department departments) {
        UserDetail userDetail = new UserDetail(firstName, lastName, email, departments);
        session.save(userDetail);
        return userDetail;
    }

    private Department saveDepartment(Session session, String name) {
        Department department = new Department(name);
        session.save(department);
        return department;
    }

    private Role saveRole(Session session, String role) {
        Role roleUser = new Role(role);
        session.save(roleUser);
        return roleUser;
    }

}