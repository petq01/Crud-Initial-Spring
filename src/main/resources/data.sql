
INSERT INTO ROLES (ID,  ROLE_NAME)  values (1, 'ROLE_USER')
INSERT INTO ROLES (ID,  ROLE_NAME)  values (2, 'ROLE_ADMIN')
INSERT INTO ROLES (ID,  ROLE_NAME)  values (3, 'ROLE_EDITOR')

INSERT INTO PERMISSIONS  (ID, PERM_NAME) values (1,'PERM_CREATE_USERS')
INSERT INTO PERMISSIONS  (ID, PERM_NAME) values (2,'PERM_READ_USERS')
INSERT INTO PERMISSIONS  (ID, PERM_NAME) values (3,'PERM_DELETE_USERS')
INSERT INTO PERMISSIONS  (ID, PERM_NAME) values (4,'PERM_UPDATE_USERS')

INSERT INTO ROLES_PERMISSIONS (ROLE_ID, PERMISSIONS_ID) values (1,2)
INSERT INTO ROLES_PERMISSIONS (ROLE_ID, PERMISSIONS_ID) values (2,1)
INSERT INTO ROLES_PERMISSIONS (ROLE_ID, PERMISSIONS_ID) values (2,2)
INSERT INTO ROLES_PERMISSIONS (ROLE_ID, PERMISSIONS_ID) values (2,3)
INSERT INTO ROLES_PERMISSIONS (ROLE_ID, PERMISSIONS_ID) values (2,4)
INSERT INTO ROLES_PERMISSIONS (ROLE_ID, PERMISSIONS_ID) values (3,2)
INSERT INTO ROLES_PERMISSIONS (ROLE_ID, PERMISSIONS_ID) values (3,4)

INSERT INTO usersdata(id,  firstname, lastname, dob, phonenumber, email,password, ROLE_ID) values(1, 'petq', 'marinova',  '1991','0882660599','userpetq@gmail.com','$2a$08$sS2T1e1JrpsjhGWXpHkrOemtSVLR/W61DF0kd6e0i2.oMYJjL98oa', 1)
INSERT INTO usersdata(id, firstname, lastname, dob, phonenumber, email,password, ROLE_ID) values(2, 'marina', 'marinova', '1991','0882660599','adminarina@gmail.com','$2a$06$4/boXx7X.TIsAO944aoE3u/dIpECM.YmP9zWSfxGJsE4glZg9WCpu', 2)
INSERT INTO usersdata(id,  firstname, lastname, dob, phonenumber, email,password, ROLE_ID) values(3,  'alexandra', 'marinova', '1991','0882660599','useralex@gmail.com','$2a$08$sS2T1e1JrpsjhGWXpHkrOemtSVLR/W61DF0kd6e0i2.oMYJjL98oa', 3)
