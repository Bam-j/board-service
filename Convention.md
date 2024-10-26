# board_service 프로젝트 코딩 컨벤션

---

## Java, Spring Framework 코드
- 자바 코딩 컨벤션은 [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html)를 따름

## HTML, CSS, JS - 프론트엔드 코드
- JavaScript도 [Google JS Style Guide](https://google.github.io/styleguide/jsguide.html)를 따른다.
- HTML 태그 개행시 탭은 2칸

## 테스트 코드
```java
@Test
@DisplayName("")
returnType testMethodName() {
    //given
    
    //when
    
    //then
    
}
```

## Database - MySQL
- 쿼리문 명령어를 기술한다면 대문자로 작성
- 쿼리문 변수, 테이블 명, 컬럼 등은 소문자로 작성 
- 테이블에는 명칭 뒤에 `s_table` 삽입
- PK id에는 접미사 `_id` 삽입
- 컬럼에 두 단어 이상 포함될 경우 `_`로 구분, 소문자로 작성

---

## 명명 규칙
### 디렉토리와 파일명
- 사용자는 `user`, 게시판은 `board`, 댓글은 `commnet`라는 명칭을 기본적으로 사용
- 게시판 시스템에서 게시글 하나는 `post`로 명명
- 각 명칭에 `camelCase`, `prefix, suffix`를 사용해서 파일/디렉토리 명을 결정
>ex) 로그인 폼: `loginForm.html`, 게시글 작성 페이지: `boardWrite.html`, 회원 정보 수정 페이지: `userInfoModify.html`
- CSS는 HTML 문서 이름 뒤에 접미사로 `-Style`을 붙인다.
>ex) `indexStyle.css`
- JavaScript 파일명은 HTML 문서를 따른다.
>ex) `LoginForm.js`
- JS 모듈명은 `camelCase`를 사용.
- HTML은 `templates` 하위에, CSS, JS, 이미지 등은 `statics` 하위에 각각 `style, js, img`라는 디렉토리를 하나 두고 생성한다.
- HTML과 CSS, JS의 디렉토리 경로명은 동일하게 맞춘다.
>ex) `/templates/user/loginForm.html`, `/static/style/user/loginFormStyle.css`

### 요청 주소
- 요청은 명칭과 접미사를 분리
>ex) boardList -> /board/list