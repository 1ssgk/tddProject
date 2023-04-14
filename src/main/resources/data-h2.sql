/*
 홍길동, 860824-1655068    XuXfYvPwSxcaOOkFr7nKxA==
 김둘리, 921108-1582816    7C/JeeKz9lV+cMdsEIL+vw==
 마징가, 880601-2455116    ETSazzN0fdyZvBRow2+J5A==
 베지터, 910411-1656116    NqAxseg6FWumlSLIHenusQ==
 손오공, 820326-2715702    WeWmO1Sct0KHnN3g9tvU9w==
*/

INSERT into TEST_TA (NAME)
values ('111' );


/* 회원가입이 가능한 사람 */
INSERT INTO SIGN_ABLE_USER (ID, USER_NM, REG_NO) values (1, '홍길동','XuXfYvPwSxcaOOkFr7nKxA==');
INSERT INTO SIGN_ABLE_USER (ID, USER_NM, REG_NO) values (2, '김둘리','7C/JeeKz9lV+cMdsEIL+vw==');
INSERT INTO SIGN_ABLE_USER (ID, USER_NM, REG_NO) values (3, '마징가','ETSazzN0fdyZvBRow2+J5A==');
INSERT INTO SIGN_ABLE_USER (ID, USER_NM, REG_NO) values (4, '베지터','NqAxseg6FWumlSLIHenusQ==');
INSERT INTO SIGN_ABLE_USER (ID, USER_NM, REG_NO) values (5, '손오공','WeWmO1Sct0KHnN3g9tvU9w==');

/* 기본 권한 생성*/
INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_USER');
INSERT INTO AUTHORITY (AUTHORITY_NAME) values ('ROLE_ADMIN');


INSERT INTO MEMBER (USER_ID, PASSWORD, MEMBER_NM, REG_NO, ZIPCODE, STREET, CITY)
values ( 'dnjstjr12', 'b048f73a86845eddb810b49cf22b83e5b114f877391056cf80da0cc714fb071fa0d60fab68d86fccabac7fa0b8b77d78b534a34ba9473f1d28514e1fd9958564','김원석', '910202-1234567','집코드','거리','시티');
//dnjstjr12
//b048f73a86845eddb810b49cf22b83e5b114f877391056cf80da0cc714fb071fa0d60fab68d86fccabac7fa0b8b77d78b534a34ba9473f1d28514e1fd9958564
INSERT INTO MEMBER_AUTHORITY (MEMBER_ID, AUTHORITY_NAME)
values ( 1,'ROLE_ADMIN' );


/*기본 카테고리 생성*/
insert into CATEGORY ( CATEGORY_ID, NAME)
values ( 1, '카테고리1' );
insert into CATEGORY ( CATEGORY_ID, NAME)
values ( 2, '카테고리2' );

/*기본 브랜드 생성*/
insert into BRAND ( BRAND_ID, NAME, LOGO_URL, DESCRIPTION, ACTIVATED)
values ( 1, '브랜드1', '로고URL', '브랜드설명', 1 );


/*기본 아이템 생성*/
insert into ITEM(ITEM_ID,NAME,PRICE,STOCK_QUANTITY,BRAND_ID,CATEGORY_ID)
values (1,'아이템1',30000,10,1,1);
insert into ITEM(ITEM_ID,NAME,PRICE,STOCK_QUANTITY,BRAND_ID,CATEGORY_ID)
values (2,'아이템2',10000,5,1,1);