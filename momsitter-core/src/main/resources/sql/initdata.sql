insert into parent(request_information)
values ("맞벌이 부부입니다. 24시간 상시 거주하면서 애기들 돌봐줄 수 있는 맘시터를 원합니다.");

insert into parent_children(parent_id, age, birth, gender, name, note)
values (1, 6, "2016-07-12", "MALE", "윌리엄 해밍턴", "");

insert into parent_children(parent_id, age, birth, gender, name, note)
values (1, 5, "2017-11-08", "MALE", "벤틀리 해밍턴", "");

insert into member(account_id, birth, email, gender, name, parent_id, password, sitter_id)
values ("test1", "1977-07-31", "test1@naver.com", "MALE", "샘해밍턴", 1, "Password123$", null);

insert into member_roles(member_id, roles)
values (1, "PARENT");

insert into parent(request_information)
values ("아내가 모델이라 다이어트식 밖에 못합니다... 우리 사랑이한테 맛잇는 요리 해주실 수 있는 맘시터 구합니다.");

insert into parent_children(parent_id, age, birth, gender, name, note)
values (2, 11, "2011-10-24", "FEMAIL", "추사랑", "");

insert into member(account_id, birth, email, gender, name, parent_id, password, sitter_id)
values ("test2", "1975-07-29", "test2@naver.com", "MALE", "추성훈", 2, "Password123$", null);

insert into member_roles(member_id, roles)
values (2, "PARENT");

insert into sitter(bio, max_age, min_age)
values ("세상에 나쁜 개가 없듯이 세상에 나쁜 애기는 없다!", 10, 1);

insert into member(account_id, birth, email, gender, name, parent_id, password, sitter_id)
values ("test3", "1985-05-27", "test3@naver.com", "MALE", "강형욱", null, "Password123$", 1);

insert into member_roles(member_id, roles)
values (3, "SITTER");

insert into sitter(bio, max_age, min_age)
values ("문제 아이는 없다, 양육에 문제가 있을 뿐", 10, 1);

insert into parent(request_information)
values ("나름 전문가지만... 제 아이 육아는 역시나 어렵네요. 도움주실분 구합니다.");

insert into parent_children(parent_id, age, birth, gender, name, note)
values (3, 7, "2015-10-02", "FEMALE", "이선빈", "");

insert into member(account_id, birth, email, gender, name, parent_id, password, sitter_id)
values ("test4", "1965-09-09", "test4@naver.com", "FEMALE", "오은영", 3, "Password123$", 2);

insert into member_roles(member_id, roles)
values (4, "SITTER");

insert into member_roles(member_id, roles)
values (4, "PARENT");
