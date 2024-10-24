# 회원제 게시판 서비스 연습 구현
## 프로젝트 개요
- 회원제로 운영하는 게시판 서비스
- 게시글 - 댓글 서비스

## 

<hr>

## 사용 기술
- **Thymeleaf, BootStrap**: 프론트 구현 & 디자인
- **MySQL**: user, post, commnet 세 가지 데이터를 저장할 DB
- **Java 17, SpringBoot, Spring Data JPA**: 백엔드 구현
- **Gradle**: 프로젝트 빌드
- **Git, GitHub**: 프로젝트 버전 관리

<hr>

## 기능
### 회원 시스템
- 아이디, 닉네임, 비밀번호를 통해 회원가입
- 아이디, 닉네임은 중복 불가
- 닉네임, 비밀번호는 회원 개인 페이지에서 변경 가능
- 회원 탈퇴 기능

### 게시판
- 회원은 로그인한 상태에서만 글/댓글을 작성
- 비회원 상태로는 글 조회만 가능
- 작성(write), 조회(post), 수정(modify) 페이지로 구성
- 게시글/댓글 작성자일 경우 삭제 가능 (비작성자가 삭제 요청 시 alert & refresh)
- 페이지네이션: 한 페이지에 10개 씩, 5 블록 씩 보여준다. 이전/다음, 맨 앞/맨 뒤

### 관리자 페이지
- 관리자 계정 로그인 시 관리자 전용 회원관리/게시글 페이지가 존재
- 관리자는 자유롭게 게시글, 회원, 댓글을 삭제할 수 있음. (수정은 X)
- 관리자 페이지 - 회원 리스트(members)(default), 게시글 리스트(posts)로 구성

### 회원 개인 페이지
- 닉네임, 비밀번호 변경 기능 (default)
- 자신이 작성한 게시글 리스트 출력
- 회원 탈퇴 버튼 (2중 확인)

<hr>

## DB
![board-service](https://github.com/user-attachments/assets/7112429b-e153-4ad9-8db0-ad84ec45508c)

<hr>

## 추가 구현 예정 기능
- Spring Security, OAuth2 학습 후 도입
- 게시글 이미지 첨부 기능