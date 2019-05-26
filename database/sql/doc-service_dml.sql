\c doc_service;

INSERT INTO doc_service.doc_type (id, name, type, assembly, detail)
VALUES (1, 'AD', 'assembly', true, false),
       (2, 'DT', 'detail', false, true);

INSERT INTO doc_service.department (id, name)
VALUES (1, 'DEV'),
       (2, 'QA'),
       (3, 'DEVOPS');

INSERT INTO doc_service.role (id, role)
VALUES (1, 'user'),
       (2, 'manager'),
       (3, 'admin');

INSERT INTO doc_service.user_detail (id, firstname, lastname, email, department_id)
VALUES (1, 'Ivan', 'Ivanov', 'ivanovi@gmail.com', 3),
       (2, 'Kate', 'Katerinina', 'katkate@gmail.com', 1),
       (3, 'Sergey', 'Brin', 'thefirstserg@gmail.com', 1),
       (4, 'Sveta', 'Svetlakova', 'svetik123@tut.by', 3),
       (5, 'Roman', 'Romanov', 'rom4ik@mail.ru', 2),
       (6, 'Mikael', 'Grev', 'mikgrev@yahoo.com', 3),
       (7, 'Semen', 'Semenov', 'semsem@yahoo.com', 2);

INSERT INTO doc_service.user (id, login, password, detail_user_id, role_id)
VALUES (1, 'van', 'simpass', 1, 1),
       (2, 'kate', 'Ivanov', 2, 2),
       (3, 'gman', 'text', 3, 3),
       (4, 'sowa', 'qwezxc', 4, 1),
       (5, 'rom4ik', '123q456z', 5, 2),
       (6, 'grev', 'vfrujm123a', 6, 3),
       (7, 'semsim', 'simsem', 7, 1);

INSERT INTO doc_service.project (id, name)
VALUES (1, ' mars '),
       (2, ' saturn '),
       (3, ' venera ');

INSERT INTO doc_service.user_project (user_id, project_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 1),
       (5, 2),
       (6, 3),
       (7, 2),
       (1, 2),
       (3, 2),
       (5, 3),
       (2, 1),
       (4, 2),
       (7, 3);

INSERT INTO doc_service.manufacturer (id, name)
VALUES (1, 'murata'),
       (2, 'kemet'),
       (3, 'vishay'),
       (4, 'koa'),
       (5, 'tdk'),
       (6, 'panasonic');

INSERT INTO doc_service.document (id, number, create_doc_date, type_doc_id, user_id)
VALUES (1, 'ad-0000001', '2018-12-21 10:23:54', 1, 1),
       (2, 'ad-0000002', '2019-01-20 18:53:11', 1, 2),
       (3, 'dt-0000001', '2019-01-25 08:33:00', 2, 3),
       (4, 'ad-0000003', '2019-02-10 22:37:02', 1, 1),
       (5, 'dt-0000002', '2019-02-12 05:23:25', 2, 2),
       (6, 'dt-0000003', '2019-03-01 15:15:46', 2, 3),
       (7, 'ad-0000004', '2019-03-03 19:33:38', 1, 1);

INSERT INTO doc_service.part (id, part_number, description, type, sort, create_part_date, create_user_id,
                              manufacturer_id)
VALUES (1, 'GRM188R71C104KA01D', '0.1 uF_10%_16V_X7R_0603', 'cap', 'c', '2018-12-21 10:23:54', 1, 1),
       (2, 'T520A106M010ATE080', '10 uF_20%_10V_A', ' cap_el ', 'ce', '2018-12-22 17:35:00', 1, 2),
       (3, 'GRM188R61A225KE34D', '2.2 uF_10%_10V_X5R_0603', 'cap', 'c', '2019-01-05 07:35:00', 1, 1),
       (4, 'ECJ-1VB1A474K', '0.47uF_10%_10V_X5R_0603', 'cap', 'c', '2019-01-08 07:35:00', 2, 6),
       (5, 'ECJ-1VB1A224K', '0.22uF_10%_10V_X5R_0603', 'cap', 'c', '2019-01-18 07:35:00', 3, 6),
       (6, 'C1608X5R1A106M080AC', '10uF_10%_10V_X5R_0603', 'cap', 'c', '2019-01-29 09:51:11', 7, 5),
       (7, 'VJ0603Y472KCAA', '4700pF_10%_50V_X7R_0603', 'cap', 'c', '2019-02-02 19:31:11', 4, 3),
       (8, 'C0402C101J5GAC', '100pF_5%_50V_C0G_0402', 'cap', 'c', '2019-02-20 23:32:31', 5, 2),
       (9, 'C0402C104K4RAC', '0.1uF_10%_16V_X7R_0402', 'cap', 'c', '2019-03-10 06:11:41', 4, 2),
       (10, 'C1608X5R1A226M080AC', '10uF_10%_10V_X5R_0603', 'cap_el', 'c', '2019-03-12 12:03:07', 4, 5),
       (11, 'NLFV25T101KPF', '100uH_10%_1008', 'ind', 'l', '2019-04-01 01:05:33', 3, 5),
       (12, 'LQW18AN39NG00x', '39nH_2%_0603', 'ind', 'l', '2019-04-01 01:05:55', 3, 1),
       (13, 'B82498F3271J', '270nH_5%_0805', 'ind', 'l', '2019-04-11 11:22:47', 3, 5),
       (14, 'LQH32CN3R3M53', '3.3uH_20%', 'ind', 'l', '2019-04-11 11:23:05', 2, 1),
       (15, 'LQM18NN68NM00', '68nH_10%_0603', 'ind', 'l', '2019-04-15 15:03:27', 4, 1),
       (16, 'NLFV25T102KPF', '100uH_10%_1008%', 'ind', 'l', '2019-04-15 17:05:21', 5, 5),
       (17, 'RK73B2ATTD1002J', '10k_1%_0.1W_0603', 'res', 'r', '2019-04-01 01:05:55', 3, 4),
       (18, 'RK73B1JTTD0R0J', '0_5%_0.1W_0603', 'res', 'r', '2019-04-11 11:22:47', 3, 4),
       (19, 'RK73B1JTTD470J', '47_5%_0.1W_0603', 'res', 'r', '2019-04-11 11:23:05', 2, 4),
       (20, 'RK73B1JTTD181J', '180_5%_0.1W_0603', 'res', 'r', '2019-04-15 15:03:27', 4, 4),
       (21, 'RK73B1JTTD333J', '33k_5%_0.1W_0603', 'res', 'r', '2019-04-15 17:05:21', 5, 4);

INSERT INTO doc_service.doc_part (doc_id, part_id, quantity_part)
VALUES (1, 1, 1),
       (2, 2, 2),
       (3, 3, 3),
       (4, 4, 10),
       (5, 5, 5),
       (6, 6, 3),
       (7, 7, 4),
       (1, 8, 6),
       (2, 9, 7),
       (3, 10, 52),
       (4, 1, 12),
       (5, 2, 12),
       (6, 3, 7),
       (7, 4, 77),
       (1, 5, 1),
       (2, 6, 6),
       (3, 7, 4),
       (4, 8, 9),
       (5, 9, 8),
       (6, 10, 2),
       (7, 1, 3),
       (1, 2, 1),
       (2, 10, 1),
       (3, 11, 8),
       (4, 12, 1),
       (5, 13, 6),
       (6, 14, 5),
       (7, 15, 57),
       (1, 16, 33),
       (2, 17, 12),
       (3, 18, 1),
       (4, 19, 2),
       (5, 20, 4),
       (6, 21, 9),
       (7, 20, 11);