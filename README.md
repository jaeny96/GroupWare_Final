# GroupWare_Final_Project

### 개발 인원 및 기간
- 개발 기간: 2021.8.13-2021.9.7
- 개발 인원: 5명
### 요구사항정의서


### 테이블 설계


### About Our Project

### 기능소개
#### 1.메인
 - 로그인 (로그인하지 않으면 메인 페이지에 접근 불가, 로그인은 index 페이지에서 실행)
 - 프로필 확인
 - 나의 결재 예정 문서(오래된 순 5개의 목록)
 - 최근 게시글 (5개의 목록)
 - 나의 휴가 정보
 - 오늘의 일정(최대 5개)

#### 2.임직원 정보 확인
 - 부서별 임직원 확인(직급 순, 직급이 같다면 이름순 정렬)
 - 이름으로 검색
 - 직원별 상세 정보 확인

#### 3.나의 정보 확인
 - 비밀번호 변경
 - 연락처 변경

#### 4.게시판
 - 게시글 목록(등록 날짜 내림차순으로 정렬)
 - 게시글 등록
 - 게시글 수정
 - 게시글 삭제
 - 댓글 등록
 - 댓글 삭제
 - 게시글 제목으로 검색
 - 게시글 작성자 이름으로 검색
 - 페이지 빈 처리(한 페이지 최대 10개의 게시글, 페이지 그룹핑은 4개로 처리, 페이지 자동 생성)

#### 5.일정 공유
 - 개인 / 팀 일정 목록
 - 일정 등록
 - 일정 기간 검색
 - 일정 제목 및 내용으로 검색
 - 일정 상세정보 확인
 - 일정 수정
 - 일정 삭제

#### 6.전자결재
 ##### 기안하기 
  - 기안문서템플릿 로드(지출, 회람, 업무, 품의, 휴가, 연락)
  ##### 결재선 설정 
   - 결재선 설정할 사원명으로 검색 - [x]
   - 결재선 설정(결재선은 자신을 제외한 3단계 설정 가능, 합의/참조는 1명으로 제한)
  - 기안하기
 ##### 목록 조회
  - 대기/승인/반려 메뉴로 문서 조회(자신과 관련된 문서가 조회)
  - 각 메뉴별 자신의 확인 여부에 따른 문서 조회
  - 승인/반려의 결과 선택
  - 코멘트 등록
  - 코멘트 조회
  ##### 결재 프로시저 실행 
   - 결재선 내 모든 사원들이 승인 시 문서의 최종 상태를 '승인'으로 변경
   - 결재선 내 한 명의 사원이라도 반려 시 문서의 최종 상태를 '반려'로 변경
 
 #### 7.채팅기능
  ##### 대나무숲
   - 사용자가 닉네임을 지정해 실시간 채팅에 참여
  
 #### 8.관리자모드
  ##### 공지사항
   - 공지사항 등록
   - 게시글 수정
   - 게시글 삭제
  ##### 임직원 관리
   - 직원추가
   - 직원탈퇴처리(비활성화)
  ##### 직무 관리
   - 직무수정
   - 직무삭제
