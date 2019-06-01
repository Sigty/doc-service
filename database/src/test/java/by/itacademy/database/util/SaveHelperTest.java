package by.itacademy.database.util;

import by.itacademy.database.entity.Department;
import by.itacademy.database.entity.Manufacturer;
import by.itacademy.database.entity.Part;
import by.itacademy.database.entity.Project;
import by.itacademy.database.entity.Role;
import by.itacademy.database.entity.User;
import by.itacademy.database.entity.UserDetail;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SaveHelperTest {

    private static SaveHelperTest INSTANCE = new SaveHelperTest();

    public static SaveHelperTest getInstance() {
        return INSTANCE;
    }

    private Role saveRole(Session session, String role) {
        Role roleUser = new Role(role);
        session.save(roleUser);
        return roleUser;
    }

    private Department saveDepartment(Session session, String name) {
        Department department = new Department(name);
        session.save(department);
        return department;
    }

    public User fullUser(Session session, String login, String password, String email, String role,
                         String department, String firtsProject, String secondProject) {
        Set<Project> userProjects = new HashSet<>();
        userProjects.add(new Project(firtsProject));
        userProjects.add(new Project(secondProject));
        Role roleUser = new Role(role);
        Department departmentUser = new Department(department);
        UserDetail detail = new UserDetail("Sveta", "Svetlgva", email, departmentUser);
        User user = new User(login, password, roleUser, detail);
        session.save(roleUser);
        session.save(departmentUser);
        session.save(detail);
        session.save(user);
        return user;
    }

    public Part fullPart(Session session, String partNumber, String description, String type, String sort,
                         String data, User user, String manufacturer) {
//        "2019-03-03T01:02:03Z"
        OffsetDateTime dataFirst = OffsetDateTime.parse(data);
        Manufacturer manufacturerPart = new Manufacturer(manufacturer);
        Part part = Part.builder()
                .partNumber(partNumber)
                .description(description)
                .type(type)
                .sort(sort)
                .createPartDate(dataFirst)
                .partUser(user)
                .manufacturer(manufacturerPart)
                .build();
        session.save(manufacturerPart);
        session.save(part);
        return part;
    }
}
