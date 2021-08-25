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

--��ǰ ���̺�
    --product_id : ��ǰ��ȣ
	--Product_name : ��ǰ��
	--product_price : �ǸŰ���
	--product_detail : ��ǰ ����
	--product_keyword : �˻���
	--product_url : ��ǰ�������
	
insert into Product values('product001','�ұ�',2180,'û���� õ�Ͽ� ���¼ұ� 500g','�ұ� ������ ������� ��ⱹ�� ��ī�� ���� ���� �˸����ø��� ����ġŲī�� ','../images/salt.jpg');
insert into Product values('product002','������',5700,'û���� ������ 200g','���� ������ �˸����ø��� ����ġŲī��','../images/pepper.jpg');
insert into Product values('product003','������',9900,'Ǫ���� ������ 600g','����  ������� ��ⱹ�� �˸����ø��� ����ġŲī��','../images/������.jpg');
insert into Product values('product004','��������',1780,'���ѱ� �������� 1.8L','����  ������ ������� ��ⱹ�� ��ǳ���� ������','../images/vinegar.jpg');
insert into Product values('product005','�丶��',15700,'���߹���丶�� 1500g','�丶��  ������','../images/tomato.jpg');
insert into Product values('product006','�丶�� ������',8900,'�հ�ٵ� �丶�� ������ 2.55kg','�丶��������  ��ī�� ���� ����','../images/�丶��������.jpg');
insert into Product values('product007','������ī',8900,'������ ������ī 600g','������ī  ������','../images/paprika.jpg');
insert into Product values('product008','����',8900,'��Ű��Ʈ ���� 100g x 22����','����  ������','../images/lemon.jpg');
insert into Product values('product009','����',5800,'ģȯ�� ���� ������ �� �޾��� 500g','����  ������ ������� ��ⱹ�� ��ī�� ���� ����','../images/onion.jpg');
insert into Product values('product010','����',33800,'���� ���� (����) 1.15~1.2kg','���� ���� ������','../images/lamp.png');
insert into Product values('product011','����',41700,'�ѵ� ���� (����) 1.5kg','��� ����� ������','../images/pork.png');
insert into Product values('product012','�����',44000,'���ֵ� ����� ����� 1kg','���� ���� ����� ������� ��ⱹ��','../images/jeju.png');
insert into Product values('product013','����',6700,'û���� �緡�� ������ 1kg','���� ������� ��ⱹ��','../images/����.jpg');
insert into Product values('product014','����',9140,'��ǥ �������� 930ml x 2��','���� ������� ��ⱹ�� ��ǳ���� ������','../images/����.jpg');
insert into Product values('product015','����',6500,'������ ���ܴ��� 500g','���� ������� ��ⱹ�� ���Ա�ġ������ Ȳ�ݺ�����','../images/����.jpg');
insert into Product values('product016','����',6250,'������ ����� 200g','���� ������� ��ⱹ�� ����ġŲī��','../images/����.jpg');
insert into Product values('product017','�߸�',6080,'���ѱ� ���� ���� �߸� 2.5kg','�߸� ������� ��ⱹ��','../images/�߸�.jpg');
insert into Product values('product018','����',5830,'������ �񸶴� 500g','���� ������� ��ⱹ�� ��ī�� ���� ���� �˸����ø��� ���Ա�ġ������ ����ġŲī��','../images/����.jpg');
insert into Product values('product019','���',7790,'���� �ݰ�� 1+��� 15�� 900g','��� ������� ��ⱹ�� Ȳ�ݺ����� ������','../images/���.jpg');
insert into Product values('product020','�ᳪ��',5300,'�ᳪ�� 1kg','�ᳪ�� ������� ��ⱹ��','../images/�ᳪ��.jpg');
insert into Product values('product021','���⸧',5630,'���ѱ� ���� ���⸧ 500ml','���⸧ ������� ��ⱹ��','../images/���⸧.jpg');
insert into Product values('product022','��',10130,'������ǥ �������� 1kg','�� ������� ��ⱹ��','../images/��.jpg');
insert into Product values('product023','������',17530,'���� ������ 500g x 18��','������� ������ ������� ��ⱹ��','../images/������.jpg');
insert into Product values('product024','�߷� �а���',3160,'�鼳 �߷� �а��� 2.5kg','�а��� ��ī�� ���� ����','../images/�а���.jpg');
insert into Product values('product025','����������',3200,'���� ���������� 300g','���������� ��ī�� ���� ����','../images/����������.jpg');
insert into Product values('product026','����',7410,'�鼳 �Ͼἳ�� 5kg','���� ��ī�� ���� ���� ��ǳ����','../images/sugar.jpg');
insert into Product values('product027','�̽�Ʈ',8510,'��밨 �̽�Ʈ 90g','�̽�Ʈ ��ī�� ���� ����','../images/�̽�Ʈ.jpg');
insert into Product values('product028','��������',9900,'�������� ���������� 400g','���� ��ī�� ���� ���� �˸����ø��� ���Ա�ġ������ ����ġŲī��','../images/butter.jpg');
insert into Product values('product029','������',14290,'���� ������ (�õ�) 1kg','������ ��ī�� ���� ����','../images/������.jpg');
insert into Product values('product030','ĥ�� �÷���ũ',12650,'Morton Bassett Spices ����ĥ�� �÷���ũ 37g x 2��','ĥ���÷���ũ ��ī�� ���� ����','../images/ĥ���÷���ũ.jpg');
insert into Product values('product031','��������',6300,'�����Ž����� �������� 70g','�������� ��ī�� ���� ����','../images/��������.jpg');
insert into Product values('product032','����',3580,'������ ���� 20g','���� ��ī�� ���� ����','../images/����.jpg');
insert into Product values('product033','��¥����',20660,'���� ������ ��¥����ġ�� ��뷮 2.5kg','��¥���� ġ�� ��ī�� ���� ���� ���Ա�ġ������','../images/��¥����.jpg');
insert into Product values('product034','�ĸ������Ƴ� �����Ƴ�',7500,'��Ƽ��ī�������� �ĸ������Ƴ� �����Ƴ� ġ�� 150g','�ĸ������Ƴ� �����Ƴ� ġ�� ��ī�� ���� ���� �˸����ø���','../images/�ĸ������Ƴ뷹���Ƴ�.jpg');
insert into Product values('product035','����',7880,'�������Ʈ Ŭ���� ��Ż���� ���� 200g','���� ��ī�� ���� ����','../images/����.jpg');
insert into Product values('product036','��ġ��',19400,'���� ���� �����ӱ� ��ġ�� 300g','��ġ�� �˸����ø���','../images/��ġ��.jpg');
insert into Product values('product037','�Ľ�Ÿ��',6040,'��Ÿ�� į�ĴϾ� ���İ�Ƽ�� 500g x 3��','�Ľ�Ÿ ���İ�Ƽ �˸����ø���','../images/�Ľ�Ÿ��.png');
insert into Product values('product038','ũ������ ����',4030,'�����Ž����� ũ������ �������� 40g','ũ������ �������� �˸����ø���','../images/ũ����������.jpg');
insert into Product values('product039','�Ľ�������',9500,'Ǫ���� �Ľ��� �ķ���ũ ���� ���ϻ� 200g','�Ľ������� �˸����ø���','../images/�Ľ�������.jpg');
insert into Product values('product040','�ø������',26000,'�ø�Ÿ���� ����Ʈ����� �ø����� 1L','�ø������ �˸����ø���','../images/�ø������.jpg');
insert into Product values('product041','���ġ��',8330,'�ϵ������� �ڶ� �긮 �� ��� ġ�� 125g','���ġ�� �˸����ø���','../images/���ġ��.jpg');
insert into Product values('product042','����',5200,'���� CJ �̱� Ŭ���� 80g x 5��','���� ��ġ������','../images/����.jpg');
insert into Product values('product043','����ġ',16900,'������ ������ ����ġ 1.7kg','��ġ ��ġ������','../images/����ġ.jpg');
insert into Product values('product044','�Ｎ��',21840,'���ִ� ���ѱ�� 210g x 24��','�Ｎ�� ��ġ������ Ȳ�ݺ�����','../images/�Ｎ��.jpg');
insert into Product values('product045','��Ʈ��ġ��',16440,'��Ʈ��ġ�� 200g x 3��','��Ʈ��ġ�� ��ġ������','../images/��Ʈ��ġ��.jpg');
insert into Product values('product046','�Ŀ���',5600,'��ǥ �Ŀ��� 1.5L','�Ŀ��� ��ġ������ ����ġŲī�� Ȳ�ݺ����� ��ǳ���� ������','../images/�Ŀ���.jpg');
insert into Product values('product047','�߰�����',9980,'��귣�� �õ� �߰����� 1kg','�߰����� ����ġŲī��','../images/�߰�����.jpg');
insert into Product values('product048','�ߴٸ���',11900,'�ϸ� �ڿ��Ƿ� ���׻��� ���� �ߴٸ��� ���� (����) 350g x 2��','�ߴٸ��� ������','../images/�ߴٸ���.jpg');
insert into Product values('product049','�ݴ޷�Ŀ��',14980,'������ �ε�����޷�Ŀ�� 1kg','�ݴ޷�Ŀ�� ����ġŲī��','../images/��޷�Ŀ��.jpg');
insert into Product values('product050','����Ϳ���',12800,'[�����] ����� �������� ���� 147ml','����Ϳ��� ����ġŲī��','../images/����Ϳ���.jpg');
insert into Product values('product051','��ũ��',4100,'����ŷ��Ƽ ������� ��ũ�� 500ml','��ũ�� ����ġŲī��','../images/��ũ��.jpg');
insert into Product values('product052','�÷��ο��Ʈ',11700,'����׸����Ʈ �÷��� 100g x 3��','�÷��ο��Ʈ ����ġŲī��','../images/�÷��ο��Ʈ.jpg');
insert into Product values('product053','���۷�ġ��',6700,'�ٸ��̰� ���۷�ġ��Ȧ 60g','���۷�ġ�� ����ġŲī��','../images/���۷�ġ��.jpg');
insert into Product values('product054','���',6000,'������� 300g','��� ����ġŲī��','../images/���.jpg');
insert into Product values('product055','��',14910,'���̿�Ǫ�� �������ͳ� Ŀ���ҽ���Ʈ (�õ�) 500g x 2��','�� ����ġŲī��','../images/��.jpg');
insert into Product values('product056','����',11000,'������ ������ 500g','���� Ȳ�ݺ�����','../images/����.jpg');
insert into Product values('product057','ġŲ����',16000,'û���� ������ ġŲ���� 340g x 2��','ġŲ���� Ȳ�ݺ����� ��ǳ���� ������','../images/ġŲ����.jpg');
insert into Product values('product058','���ҽ�',7100,'û���� �����̾� ���ҽ� 500g','���ҽ� Ȳ�ݺ����� ��ǳ���� ������','../images/���ҽ�.jpg');
insert into Product values('product059','������',14950,'���� ������ (�õ�) 1.3kg','������ ��ǳ����','../images/������.jpg');
insert into Product values('product060','����',5040,'���� ģȯ�� û����� 300g','���� ��ǳ���� ������','../images/����.jpg');
insert into Product values('product061','��������',5900,'û�� ���� �������� 1kg','�������� ��ǳ���� ������','../images/����.jpg');
insert into Product values('product062','�ø����',5420,'�鼳 �丮 �ø���� 2.45kg','�ø���� ������','../images/�ø����.jpg');
insert into Product values('product063','�����',2300,'�׸��� ����� 1�� (400g)','����� ������','../images/�����.jpg');

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

--��ٱ��� ���̺�
	--OrderNum : �ֹ���ȣ (��ٱ��Ϲ�ȣ)
	--ProductID : ��ǰ ID
	--MemID : ȸ��ID
	--Quantity : ����


-- ��ٱ������̺� ������ ����
CREATE SEQUENCE seq_Basket
START WITH 1
INCREMENT BY 1;

-- ��ٱ��� ���̺� ��������(�ܷ�Ű) ����
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
