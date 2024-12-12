# 채용공고 API 벡엔드 서버
![swagerimg](https://github.com/user-attachments/assets/a0944342-29e7-482b-ade9-2a9d404184e6)
## 개발 기술
![다운로드](https://github.com/user-attachments/assets/df65d834-a30f-4299-b54c-6d8061937e2b)

Language: Java<br>
WebFramework: Springboot 3.3.6<br>
DB: mysql,JPA<br>
Data: 사람인사이트 크롤링 with beautifulsoup4,python<br>
Security: JWT access/refresh 토큰 방식


## Swagger 설명
### 1.ApplicationController
   이 컨트롤러는 사용자가 채용 공고에 지원하고, 지원을 취소하며, 자신의 지원 목록을 조회하는 기능을 제공합니다.

   지원하기 (POST /applications)
   사용자가 지원하려는 공고의 ID와 이력서 ID를 받아서 지원을 처리합니다.
   성공적으로 지원하면 "지원 성공, 이력서 성공적으로 제출" 메시지를 반환합니다.
   지원 취소 (DELETE /applications/{id})
   사용자가 취소하려는 지원 내역의 ID를 받아서 해당 지원을 삭제합니다.
   성공적으로 삭제되면 "지원 취소 성공" 메시지를 반환합니다.
   지원 조회 (GET /applications)
   사용자가 지원한 공고의 제목 리스트를 지원 날짜 순으로 정렬하여 반환합니다.
### 2.AuthController
   이 컨트롤러는 회원가입, 로그인, 토큰 재발급, 회원 정보 수정 및 탈퇴 관련 기능을 제공합니다.

   회원가입 (POST /auth/register)
   
   이메일 형식에 맞는 사용자 정보를 입력받아 회원가입을 처리합니다.
   성공적으로 가입하면 "회원가입 성공" 메시지를 반환합니다.
   로그인 (POST /auth/login)
   
   사용자가 로그인할 때, username과 password를 입력받아 JWT 토큰을 반환합니다.
   로그인 성공 시 "로그인 성공" 메시지를 반환합니다.
   토큰 재발급 (POST /auth/refresh)
   
   리프레시 토큰이 있으면 새로운 액세스 토큰을 반환합니다.
   리프레시 토큰이 없거나 만료되었으면 오류 메시지를 반환합니다.
   회원 정보 수정 (PUT /auth/profile)

   사용자가 패스워드, 지역, 학력 등을 수정할 수 있습니다.
   회원 탈퇴 (DELETE /auth/unregister)
   
   로그인된 사용자만이 자신을 탈퇴시킬 수 있습니다.
### 3.BookmarksController
   이 컨트롤러는 사용자가 채용 공고에 북마크를 추가하거나 취소하고, 북마크 목록을 조회하는 기능을 제공합니다.

   북마크 추가/취소 (POST /bookmarks)
   사용자가 특정 채용 공고를 북마크하거나, 이미 북마크된 채용 공고를 취소합니다.
   북마크 목록 조회 (GET /bookmarks)
   현재 로그인한 사용자의 북마크 목록을 반환합니다. 
### 4.CorpController
   이 컨트롤러는 회사 이름을 통해 회사 ID를 조회하는 기능을 제공합니다.

   회사 ID 조회 (GET /corp/{corp_name})
   회사 이름을 입력받아 해당 회사의 ID를 반환합니다.
### 5.JobController
   이 컨트롤러는 채용 공고 관련 기능을 제공합니다. 채용 공고 조회, 검색, 필터링 및 상세 정보 제공 기능을 포함합니다.

   채용 공고 조회 (GET /jobs)
   
   전체 채용 공고를 페이징하여 출력하고, 정렬 기준과 방식도 설정할 수 있습니다.
   채용 공고 검색 (GET /jobs/search/keyword, /jobs/search/corp, /jobs/search/sector)
   
   키워드, 회사명, 포지션 등으로 채용 공고를 검색할 수 있습니다.
   채용 공고 필터링 (GET /jobs/filter/location, /jobs/filter/experience, /jobs/filter/salary, /jobs/filter/skill)
   
   지역, 경력, 급여, 기술 스택 등을 기준으로 채용 공고를 필터링할 수 있습니다.
   채용 공고 상세 조회 (GET /jobs/{id})
   
   채용 공고의 상세 정보를 조회하고, 관련 공고들을 함께 반환합니다.
   채용 공고 등록 (POST /jobs/create)
   
   새로운 채용 공고를 등록합니다.
   채용 공고 수정 (POST /jobs/update/{id})
   
   기존 채용 공고를 수정합니다.
   채용 공고 삭제 (DELETE /jobs/delete/{id})
   
   기존 채용 공고를 삭제합니다.
   위 API들 각각은 Swagger를 사용하여 API 문서화가 되어 있으며, 각 API의 동작에 대한 설명과 요청/응답 샘플을 제공합니다.