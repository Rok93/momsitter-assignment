# 2021 1th 맘시터 API 과제

## 요구사항

- [x] API 스펙 정의 (문서화 도구로 대체)
- [x] ERD 설계
- [x] 라이브러리를 사용한 이유 명시 
  - `mockk`
    - (mockito 대신 사용) 코틀린 mock 라이브러리르 쓰기 위해서 대체 (mockito는 exclude 시킴)
  - `swagger`
    - 한정된 시간 속에서 Swagger를 쓰는 것이 더 유리하다고 판단. 
    - gradle.kts의 dsl 방식의 레퍼런스가 너무 적어서 시간 내에 Rest Docs 도입이 불가하다 판단
  - `jsonwebtoken`
    - 인증, 인가의 용도로 JWT 사용을 위해 라이브러리 추가
- [x] API 문서화 도구 (SWAGGER 선택)
- [x] 비밀번호 정책
    * (8자리 ~ 16자까지 허용, (숫자, 문자, 특수문자)가 포함되도록 패턴 지정

## 기능구현 목록
- [x] 회원가입 - [POST] /api/members/signup
- [x] 로그인 - [POST] /api/members/login
- [x] 멤버 정보 업데이트 - [PUT] /api/members/me
- [x] 부모 정보 업데이트 - [PUT] /api/parents
- [x] 시터 정보 업데이트 - [PUT] /api/sitters
- [x] 부모로 활동하기 - [POST] /api/parents
- [x] 시터로 활동하기 - [POST] /api/sitters
- [x] 내 정보 보기 - [GET] /api/members/me
    - `비밀번호`를 제외한 정보노출하기
    - 시터회원: 회원번호, 이름, 생년월일, 아이디, 이메일, 케어 가능한 연령정보, 자기 소개
    - 부모회원: 회원번호, 이름, 생년월일, 아이디, 이메일, 아이 정보, 신청 내용

### Entity 관계도 
![image](https://user-images.githubusercontent.com/47850258/143297962-5e446fbe-9901-4bec-81a6-91e1668b7623.png)

### ERD 설계 
![image](https://user-images.githubusercontent.com/47850258/143301126-749e548b-f475-4c0d-a224-2bf07ed62418.png)

### 문서화 도구 (예시)
애플리케이션 실행 후, `http://localhost:8080/swagger-ui/index.html`로 접근하면 확인 가능
![image](https://user-images.githubusercontent.com/47850258/143298386-9287a711-44d8-4aee-9765-d4fb2904acfc.png)

### 과제진행하면서 고민한 부분들 (시간흐름순) 
https://rok93.notion.site/7e3ec987c0e74f2392f1afc30abffac0

### DB Creation Query 
`main.resources.sql` 디렉토리안에 
* Schema 생성을 위한 `schema.sql` 파일
* 초기 데이터를 위한 `initdata.sql` 파일 

## 모듈 설명 
> 모듈의 단위를 어떻게 나눌지에 대한 기준 & 지식이 없어  
> 권용근님의 세미나 발표 영상에서 소개한 가이드를 최대한 따라보려고 하였습니다. 

![image](https://user-images.githubusercontent.com/37354145/150077511-07b1324e-d7e7-4732-8f60-c640d4c75067.png)
![image](https://user-images.githubusercontent.com/37354145/150075433-6edda5de-1f62-4ad6-afd4-d7332a0c23ea.png)
`momsitter-assignment`: Root Project <br>
&nbsp; ㄴ momsitter-core   <br>
&nbsp; ㄴ momsitter-common   <br>
&nbsp; ㄴ momsitter-api   <br>  
