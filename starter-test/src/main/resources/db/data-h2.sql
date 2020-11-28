DELETE FROM user;

INSERT INTO user (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@wfc.com'),
(2, 'Jack', 20, 'test2@wfc.com'),
(3, 'Tom', 28, 'test3@wfc.com'),
(4, 'Sandy', 21, 'test4@wfc.com'),
(5, 'Bill5', 24, 'test5@wfc.com'),
(6, 'Bill6', 24, 'test6@wfc.com'),
(7, 'Bill7', 24, 'test7@wfc.com'),
(8, 'Bill8', 24, 'test8@wfc.com'),
(9, 'Bill9', 24, 'test9@wfc.com'),
(10, 'Bill10', 24, 'test10@wfc.com');

-- 123
insert into wfc_account (id, login_name, password, slat, disabled) values
(1, 'admin', '929b10673ff260695b47dbb26d69023b2efc41c8c742a8b0bd612a3ab99269948bb9e7ba1bfd3ac30b5d529c5e8f19525393b20b75881570f7555ea12d5885cf', 'MYJH78', 0);