--drop table product;

CREATE TABLE Product(
    product_id      	VARCHAR2(10) NOT NULL,
	product_name    	VARCHAR2(50) NOT NULL,
    product_price       int NOT NULL,
    product_detail      VARCHAR2(300),
    product_keyword     VARCHAR2(200) NOT NULL,
    product_url	  	    VARCHAR2(100)	 
);

ALTER TABLE Product ADD PRIMARY KEY (product_id);

--상품 테이블
    --product_id : 상품번호
	--Product_name : 상품명
	--product_price : 판매가격
	--product_detail : 상품 설명
	--product_keyword : 검색어
	--product_url : 상품사진경로
	
insert into Product values('product001','소금',2180,'청정원 천일염 가는소금 500g','소금 샤슬릭 돔베고기 고기국수 시카고 딥디쉬 피자 알리오올리오 버터치킨카레 ','../images/salt.jpg');
insert into Product values('product002','순후추',5700,'청정원 순후추 200g','후추 샤슬릭 알리오올리오 버터치킨카레','../images/pepper.jpg');
insert into Product values('product003','통후추',9900,'푸르빈 통후추 600g','후추  돔베고기 고기국수 알리오올리오 버터치킨카레','../images/통후추.jpg');
insert into Product values('product004','양조식초',1780,'오뚜기 양조식초 1.8L','식초  샤슬릭 돔베고기 고기국수 깐풍만두 유린기','../images/vinegar.jpg');
insert into Product values('product005','토마토',15700,'대추방울토마토 1500g','토마토  샤슬릭','../images/tomato.jpg');
insert into Product values('product006','토마토 통조림',8900,'롱고바디 토마토 통조림 2.55kg','토마토통조림  시카고 딥디쉬 피자','../images/토마토통조림.jpg');
insert into Product values('product007','파프리카',8900,'국내산 파프리카 600g','파프리카  샤슬릭','../images/paprika.jpg');
insert into Product values('product008','레몬',8900,'썬키스트 레몬 100g x 22개입','레몬  샤슬릭','../images/lemon.jpg');
insert into Product values('product009','양파',5800,'친환경 인증 국내산 깐 햇양파 500g','양파  샤슬릭 돔베고기 고기국수 시카고 딥디쉬 피자','../images/onion.jpg');
insert into Product values('product010','양등심',33800,'램원 양등심 (냉장) 1.15~1.2kg','양고기 양등심 샤슬릭','../images/lamp.png');
insert into Product values('product011','통목살',41700,'한돈 통목살 (냉장) 1.5kg','목살 목삼겹살 샤슬릭','../images/pork.png');
insert into Product values('product012','오겹살',44000,'제주도 흑돼지 오겹살 1kg','제주 삼겹살 오겹살 돔베고기 고기국수','../images/jeju.png');
insert into Product values('product013','된장',6700,'청정원 재래식 생된장 1kg','된장 돔베고기 고기국수','../images/된장.jpg');
insert into Product values('product014','간장',9140,'샘표 양조간장 930ml x 2개','간장 돔베고기 고기국수 깐풍만두 유린기','../images/간장.jpg');
insert into Product values('product015','대파',6500,'국내산 절단대파 500g','대파 돔베고기 고기국수 스팸김치볶음밥 황금볶음밥','../images/대파.jpg');
insert into Product values('product016','생강',6250,'국내산 깐생강 200g','생강 돔베고기 고기국수 버터치킨카레','../images/생강.jpg');
insert into Product values('product017','중면',6080,'오뚜기 옛날 국수 중면 2.5kg','중면 돔베고기 고기국수','../images/중면.jpg');
insert into Product values('product018','마늘',5830,'국내산 깐마늘 500g','마늘 돔베고기 고기국수 시카고 딥디쉬 피자 알리오올리오 스팸김치볶음밥 버터치킨카레','../images/마늘.jpg');
insert into Product values('product019','계란',7790,'가농 금계란 1+등급 15구 900g','계란 돔베고기 고기국수 황금볶음밥 유린기','../images/계란.jpg');
insert into Product values('product020','콩나물',5300,'콩나물 1kg','콩나물 돔베고기 고기국수','../images/콩나물.jpg');
insert into Product values('product021','참기름',5630,'오뚜기 옛날 참기름 500ml','참기름 돔베고기 고기국수','../images/참기름.jpg');
insert into Product values('product022','깨',10130,'사조해표 볶음참깨 1kg','깨 돔베고기 고기국수','../images/깨.jpg');
insert into Product values('product023','사골곰탕',17530,'비비고 사골곰탕 500g x 18개','사골육수 사골곰탕 돔베고기 고기국수','../images/사골곰탕.jpg');
insert into Product values('product024','중력 밀가루',3160,'백설 중력 밀가루 2.5kg','밀가루 시카고 딥디쉬 피자','../images/밀가루.jpg');
insert into Product values('product025','옥수수가루',3200,'국산 옥수수가루 300g','옥수수가루 시카고 딥디쉬 피자','../images/옥수수가루.jpg');
insert into Product values('product026','설탕',7410,'백설 하얀설탕 5kg','설탕 시카고 딥디쉬 피자 깐풍만두','../images/sugar.jpg');
insert into Product values('product027','이스트',8510,'배대감 이스트 90g','이스트 시카고 딥디쉬 피자','../images/이스트.jpg');
insert into Product values('product028','무염버터',9900,'프레지덩 무염버터팩 400g','버터 시카고 딥디쉬 피자 알리오올리오 스팸김치볶음밥 버터치킨카레','../images/butter.jpg');
insert into Product values('product029','베이컨',14290,'곰곰 베이컨 (냉동) 1kg','베이컨 시카고 딥디쉬 피자','../images/베이컨.jpg');
insert into Product values('product030','칠리 플레이크',12650,'Morton Bassett Spices 레드칠리 플레이크 37g x 2팩','칠리플레이크 시카고 딥디쉬 피자','../images/칠리플레이크.jpg');
insert into Product values('product031','오레가노',6300,'딜리셔스마켓 오레가노 70g','오레가노 시카고 딥디쉬 피자','../images/오레가노.jpg');
insert into Product values('product032','바질',3580,'국내산 바질 20g','바질 시카고 딥디쉬 피자','../images/바질.jpg');
insert into Product values('product033','모짜렐라',20660,'곰곰 슈레드 모짜렐라치즈 대용량 2.5kg','모짜렐라 치즈 시카고 딥디쉬 피자 스팸김치볶음밥','../images/모짜렐라.jpg');
insert into Product values('product034','파르미지아노 레지아노',7500,'안티노카세이피초 파르미지아노 레지아노 치즈 150g','파르미지아노 레지아노 치즈 시카고 딥디쉬 피자 알리오올리오','../images/파르미지아노레지아노.jpg');
insert into Product values('product035','살라미',7880,'존쿡델리미트 클래식 이탈리안 살라미 200g','살라미 시카고 딥디쉬 피자','../images/살라미.jpg');
insert into Product values('product036','살치살',19400,'숙성 냉장 프라임급 살치살 300g','살치살 알리오올리오','../images/살치살.jpg');
insert into Product values('product037','파스타면',6040,'폰타나 캄파니아 스파게티면 500g x 3개','파스타 스파게티 알리오올리오','../images/파스타면.png');
insert into Product values('product038','크러쉬드 페퍼',4030,'딜리셔스마켓 크러쉬드 레드페퍼 40g','크러쉬드 레드페퍼 알리오올리오','../images/크러쉬드페퍼.jpg');
insert into Product values('product039','파슬리가루',9500,'푸른빈 파슬리 후레이크 분태 독일산 200g','파슬리가루 알리오올리오','../images/파슬리가루.jpg');
insert into Product values('product040','올리브오일',26000,'올리타리아 엑스트라버진 올리브유 1L','올리브오일 알리오올리오','../images/올리브오일.jpg');
insert into Product values('product041','블루치즈',8330,'일드프랑스 쁘띠 브리 오 블루 치즈 125g','블루치즈 알리오올리오','../images/블루치즈.jpg');
insert into Product values('product042','스팸',5200,'스팸 CJ 싱글 클래식 80g x 5개','스팸 김치볶음밥','../images/스팸.jpg');
insert into Product values('product043','맛김치',16900,'종가집 썰어담은 맛김치 1.7kg','김치 김치볶음밥','../images/맛김치.jpg');
insert into Product values('product044','즉석밥',21840,'맛있는 오뚜기밥 210g x 24개','즉석밥 김치볶음밥 황금볶음밥','../images/즉석밥.jpg');
insert into Product values('product045','스트링치즈',16440,'스트링치즈 200g x 3개','스트링치즈 김치볶음밥','../images/스트링치즈.jpg');
insert into Product values('product046','식용유',5600,'해표 식용유 1.5L','식용유 김치볶음밥 버터치킨카레 황금볶음밥 깐풍만두 유린기','../images/식용유.jpg');
insert into Product values('product047','닭가슴살',9980,'노브랜드 냉동 닭가슴살 1kg','닭가슴살 버터치킨카레','../images/닭가슴살.jpg');
insert into Product values('product048','닭다리살',11900,'하림 자연실록 무항생제 인증 닭다리살 정육 (냉장) 350g x 2팩','닭다리살 유린기','../images/닭다리살.jpg');
insert into Product values('product049','반달루커리',14980,'이츠웰 인델리빈달루커리 1kg','반달루커리 버터치킨카레','../images/빈달루커리.jpg');
insert into Product values('product050','기버터오일',12800,'[만토바] 기버터 스프레이 오일 147ml','기버터오일 버터치킨카레','../images/기버터오일.jpg');
insert into Product values('product051','생크림',4100,'베이킹파티 서울우유 생크림 500ml','생크림 버터치킨카레','../images/생크림.jpg');
insert into Product values('product052','플레인요거트',11700,'요즘그릭요거트 플레인 100g x 3개','플레인요거트 버터치킨카레','../images/플레인요거트.jpg');
insert into Product values('product053','페퍼론치노',6700,'바른미각 페퍼론치노홀 60g','페퍼론치노 버터치킨카레','../images/페퍼론치노.jpg');
insert into Product values('product054','고수',6000,'고수나물 300g','고수 버터치킨카레','../images/고수.jpg');
insert into Product values('product055','난',14910,'세미원푸드 갈릭버터난 커리소스세트 (냉동) 500g x 2개','난 버터치킨카레','../images/난.jpg');
insert into Product values('product056','쪽파',11000,'국내산 깐쪽파 500g','쪽파 황금볶음밥','../images/쪽파.jpg');
insert into Product values('product057','치킨스톡',16000,'청정원 셰프의 치킨스톡 340g x 2개','치킨스톡 황금볶음밥 깐풍만두 유린기','../images/치킨스톡.jpg');
insert into Product values('product058','굴소스',7100,'청정원 프리미엄 굴소스 500g','굴소스 황금볶음밥 깐풍만두 유린기','../images/굴소스.jpg');
insert into Product values('product059','물만두',14950,'비비고 물만두 (냉동) 1.3kg','물만두 깐풍만두','../images/물만두.jpg');
insert into Product values('product060','고추',5040,'곰곰 친환경 청양고추 300g','고추 깐풍만두 유린기','../images/고추.jpg');
insert into Product values('product061','감자전분',5900,'청은 국산 감자전분 1kg','감자전분 깐풍만두 유린기','../images/전분.jpg');
insert into Product values('product062','올리고당',5420,'백설 요리 올리고당 2.45kg','올리고당 유린기','../images/올리고당.jpg');
insert into Product values('product063','양상추',2300,'그린팜 양상추 1통 (400g)','양상추 유린기','../images/양상추.jpg');

-------------------------------------
select * from Product order by PRODUCT_ID ASC;
-------------------------------------

--drop table basket;
CREATE TABLE Basket (
       OrderNum     NUMBER NOT NULL,
       ProductID    VARCHAR(10) NOT NULL,
       MemID		VARCHAR(20) NOT NULL,	
       Quantity     NUMBER DEFAULT 0
);

ALTER TABLE Basket ADD PRIMARY KEY (OrderNum);

--장바구니 테이블
	--OrderNum : 주문번호 (장바구니번호)
	--ProductID : 상품 ID
	--MemID : 회원ID
	--Quantity : 수량


-- 장바구니테이블 시퀀스 생성
CREATE SEQUENCE seq_Basket
START WITH 1
INCREMENT BY 1;

-- 장바구니 테이블 제약조건(외래키) 생성
ALTER TABLE Basket ADD CONSTRAINT fk_basket_ProductID FOREIGN KEY(ProductID) REFERENCES product(product_id);
ALTER TABLE Basket ADD CONSTRAINT fk_basket_MemID FOREIGN KEY(MemId) REFERENCES member(memid);

-------------------------------------
select * from Basket;
-------------------------------------

--drop table PaymentInfo;
CREATE TABLE PaymentInfo (
		paymentNum		NUMBER NOT NULL,
		memID 			VARCHAR(20) NOT NULL,	
		email      		varchar(30) not null,
  		phone      		varchar(13) not null,
  		address    		varchar(100),
   		payment    		varchar(50), 		
   		product_id      VARCHAR2(999) NOT NULL,
		product_name    VARCHAR2(999) NOT NULL,
   		product_price   VARCHAR2(999) NOT NULL,
   		Quantity		VARCHAR2(999) NOT NULL,	
   		Money			VARCHAR2(999) NOT NULL,	
   		sumMoney		int NOT NULL,
   		fee		        int,
   		allSum			int NOT NULL,
   		paymentDate DATE
);

ALTER TABLE PaymentInfo ADD PRIMARY KEY (paymentNum);

CREATE SEQUENCE seq_PaymentInfo
START WITH 1
INCREMENT BY 1;

-------------------------------------
select * from PaymentInfo;
-------------------------------------

--delete from PaymentInfo where paymentNum = 21;
