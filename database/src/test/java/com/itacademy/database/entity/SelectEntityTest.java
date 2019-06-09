package com.itacademy.database.entity;

import com.itacademy.database.config.TestConfig;
import com.itacademy.database.repository.DepartmentRepository;
import com.itacademy.database.repository.DocPartRepository;
import com.itacademy.database.repository.DocTypeRepository;
import com.itacademy.database.repository.DocumentRepository;
import com.itacademy.database.repository.ManufacturerRepository;
import com.itacademy.database.repository.PartRepository;
import com.itacademy.database.repository.ProjectRepository;
import com.itacademy.database.repository.RoleRepository;
import com.itacademy.database.repository.UserDetailRepository;
import com.itacademy.database.repository.UserRepository;
import com.itacademy.database.util.DatabaseHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Rollback
@Sql("classpath:doc-service_dml.sql")
public class SelectEntityTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private DocTypeRepository docTypeRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private DocPartRepository docPartRepository;

    @Autowired
    private DatabaseHelper databaseHelper;

    @Before
    public void init() {
        databaseHelper.cleanDatabase();
        //      databaseHelper.prepareData();
    }

    @Test
    public void getAllRole() {
        List<Role> allRoles = new ArrayList<>();
        roleRepository.findAll().forEach(allRoles::add);
        int expectedSize = 3;
        assertEquals(expectedSize, allRoles.size());
    }

    @Test
    public void getAllDepartment() {
        List<Department> allDepartments = new ArrayList<>();
        departmentRepository.findAll().forEach(allDepartments::add);
        int expectedSize = 3;
        assertEquals(expectedSize, allDepartments.size());
    }

    @Test
    public void getDetailUser() {
        Optional<UserDetail> userDetailId = userDetailRepository.findById(1);
        assertNotNull(userDetailId.isPresent());
        String expectedEmail = "ivanovi@gmail.com";
        assertEquals(expectedEmail, userDetailId.get().getEmail());
    }

    @Test
    public void getAllUser() {
        List<User> allUser = new ArrayList<>();
        userRepository.findAll().forEach(allUser::add);
        int expectedSize = 7;
        assertEquals(expectedSize, allUser.size());
    }

    @Test
    public void getAllProject() {
        List<Project> allProject = new ArrayList<>();
        projectRepository.findAll().forEach(allProject::add);
        int expectedSize = 3;
        assertEquals(expectedSize, allProject.size());
    }

    @Test
    public void getAllManufacturer() {
        List<Manufacturer> allManufacturer = new ArrayList<>();
        manufacturerRepository.findAll().forEach(allManufacturer::add);
        int expectedSize = 6;
        assertEquals(expectedSize, allManufacturer.size());
        Optional<Manufacturer> allManufacturerId = manufacturerRepository.findById(1);
        String expectedEmail = "murata";
        assertEquals(expectedEmail, allManufacturerId.get().getName());
    }

    @Test
    public void getAllPart() {
        List<Part> allParts = new ArrayList<>();
        partRepository.findAll().forEach(allParts::add);
        int expectedSize = 21;
        assertEquals(expectedSize, allParts.size());
    }

    @Test
    public void getAllDocType() {
        List<DocType> allDocType = new ArrayList<>();
        docTypeRepository.findAll().forEach(allDocType::add);
        int expectedSize = 2;
        assertEquals(expectedSize, allDocType.size());
    }

    @Test
    public void getAllDocument() {
        List<Document> allDocument = new ArrayList<>();
        documentRepository.findAll().forEach(allDocument::add);
        int expectedSize = 7;
        assertEquals(expectedSize, allDocument.size());
    }

    @Test
    public void getAllDocPart() {
        List<DocPart> allDocPart = new ArrayList<>();
        docPartRepository.findAll().forEach(allDocPart::add);
        int expectedSize = 35;
        assertEquals(expectedSize, allDocPart.size());
    }
}