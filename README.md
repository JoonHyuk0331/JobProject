# 채용공고 API 벡엔드 서버
![swagerimg](https://github.com/user-attachments/assets/a0944342-29e7-482b-ade9-2a9d404184e6)

---
## 개발 기술
![다운로드](https://github.com/user-attachments/assets/df65d834-a30f-4299-b54c-6d8061937e2b)
Language: Java<br>
WebFramework: Springboot 3.3.6<br>
DB: mysql,JPA<br>
Data: 사람인사이트 크롤링 with beautifulsoup4,python<br>
Security: JWT access/refresh 토큰 방식

---
## Swagger 설명
swagger url <br>
http://113.198.66.75:17206/swagger-ui/index.html#/

<strong>*** 대부분의 요청에 JSON 객체가 요구됩니다! <br>
필요하지 않은 항목들은 기본값으로 세팅되거나
코드 내부에서 알아서 설정되니 요구되는 값만 입력해서 요청을 보내면 됩니다.
요구되는 입력값은 스웨거 세부 설명이나 해당 Readme 문서에 작성되어 있으니 참고 부탁드립니다.
</strong>

예시 : 회원 정보 수정 (PUT /auth/profile)
```
{
  "id": 0,
  "username": "string",
  "password": "string",
  "role": "string",
  "education": "string",
  "location": "string"
}
```
````
{
  "id": 0,
  "username": "string",
  "password": "변경할 비밀번호",
  "role": "string",
  "education": "변경할 학력사항",
  "location": "변경할 지역"
}
````
---
*** 추가로 만든 API 3개, 이력서 저장,삭제, 상위회사 연봉정보

*** JWT access 토큰의 경우 발급받은 뒤 swagger 우상단 Authorize 버튼을 누르고 상단 access 토큰 부분에 bearer를 제외한 순수 토큰값만 넣으면됩니다

## 1.AuthController
회원가입, 로그인, 토큰 재발급, 회원 정보 수정 및 탈퇴 관련 기능을 제공합니다.

#### 회원 정보 수정 (PUT /auth/profile)
설명: 패스워드,지역,학력을 수정할 수 있습니다
요청입력 키값: password,education,location 그 외 자동입력

#### 회원가입 (POST /auth/register)
이메일 형식을 지켜주세요,username과 password 만 입력하면 됩니다
요청입력 키값: username,password

#### 토큰 재발급 (POST /auth/refresh)
현재 브라우저의 쿠키의 리프레시 토큰이 있다면 새로운 응답으로 엑세스 토큰을 반환합니다.

#### 로그인 (POST /auth/login)
응답 헤더에 JWT 토큰값이 반환됩니다,보안을 위해 Form아 아닌 JSON 객체로 입력받습니다
요청입력 키값: username,password

#### 회원 탈퇴 (DELETE /auth/unregister)
인가 받은 토큰값을 가진 사용자 스스로만 요청가능합니다

---
## 2.ResumeController
추가기능API 1,2입니다 사용자의 이력서를 저장하고 삭제합니다

#### 이력서 저장 (POST /resume)
이력서 제목과 내용을 적어주세요, 사용자마다 각각 저장됩니다
요청입력 키값: title,content

#### 이력서 삭제 (POST /resume/{id})
입력받은 id의 이력서를 삭제합니다

---
## 3.JobController
채용 공고 관련 기능을 제공합니다. 채용 공고 조회, 검색, 필터링 및 상세 정보 제공 기능을 포함합니다.

#### 채용 공고 수정 (POST /jobs/update/{id})
기존 채용 공고를 수정합니다.
JSON객체를 입력으로 받습니다 id,title,view,corp 필드는 변하지 않으니 제외하고 요청을 보내세요

#### 채용 공고 등록 (POST /jobs/create)
id,recruitViews 를 제외한 모든 키 값을 JSON객체로 입력 받습니다
회사 Id(corpId)를 모를경우 회사명 검색 API를 통해 Id를 꼭 찾아 입력해주세요

#### 채용 공고 조회 (GET /jobs)
전체 채용공고를 페이징하여 출력합니다 정렬기준과 정렬방식을 선택할 수 있습니다

#### 채용 공고 상세 조회 (GET /jobs/{id})
입력한 Id값의 상세 공고 정보를 조회합니다

#### 채용 공고 검색 (GET /jobs/search/keyword, /jobs/search/corp, /jobs/search/sector)
검색 키워드를 포함하는 전체 채용 공고를 페이징하여 출력하고, 정렬 기준과 방식도 설정할 수 있습니다.

#### 채용 공고 필터링 (GET /jobs/filter/location, /jobs/filter/experience, /jobs/filter/salary, /jobs/filter/skill)
키워드, 회사명, 포지션 등으로 채용 공고를 필터링 할 수 있습니다.

#### 채용 공고 삭제 (DELETE /jobs/delete/{id})
입력한 id에 해당하는 기존 채용공고를 삭제합니다

---
## 4.BookmarksController

#### 북마크목록 조회 (GET /bookmarks)
현재 로그인한 사용자의 북마크 리스트를 반환합니다

#### 북마크 (POST /bookmarks)
사용자가 특정 id 의 채용공고에 북마크합니다. 한번 더 요청할경우 북마크가 취소됩니다

---
## 5.ApplicationController
사용자가 채용 공고에 지원하고, 지원을 취소하며, 자신의 지원 목록을 조회하는 기능을 제공합니다.
#### 지원 조회 (GET /applications)
현재 로그인된 사용자가 지원한 공고의 제목 리스트들을 지원한 날짜순으로 정렬해서 반환

#### 지원하기 (POST /applications)
현재 로그인된 사용자가 지원한 공고의 제목 리스트들을 지원한 날짜순으로 정렬해서 반환

#### 지원 취소 (DELETE /applications/{id})
취소할 지원내역의 Id를 입력해주세요

---
## 6.SalaryController
상위연봉 회사 연봉정보 출력

#### 상위회사 연봉정보 (GET /salary)
