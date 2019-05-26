package by.itacademy.database.util;

import by.itacademy.database.entity.Department;
import by.itacademy.database.entity.Role;
import by.itacademy.database.entity.User;
import by.itacademy.database.entity.UserDetail;
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

    public User fullUser(Session session, String login, String password, String email, String role, String department) {
        Role roleUser = new Role(role);
        Department departmentUser = new Department(department);
        UserDetail detail = new UserDetail("Sveta", "Svetlgva", email, departmentUser);
        User user = new User(login, password, roleUser, detail);
        session.save(roleUser);
        session.save(department);
        session.save(detail);
        session.save(user);
        return user;
    }

    public User fullUser(Session session, String login, String password, String email, Role role, Department department) {
        UserDetail detail = new UserDetail("Sveta", "Svetlgva", email, department);
        User user = new User(login, password, role, detail);
        session.save(role);
        session.save(department);
        session.save(detail);
        session.save(user);
        return user;
    }

}
