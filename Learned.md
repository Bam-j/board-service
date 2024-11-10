# 배운 것들 메모

---

### DTO 분할하기
- 용도에 따라 다양한 DTO를 분할하라
- 하나의 DTO를 사용하면 불필요한 순간에 불필요한 정보를 가지고 있을 수 있다.
- 재사용 금지

### Entity와 DTO는 서로 모르도록 - Mapper
- DTO 클래스 내의 entityToDTO()와 같이 서로가 직접 연관관계를 갖는 경우를 피하자
- Mapper 클래스를 따로 정의해서 구현하거나, MapStruct와 같은 라이브러리를 활용하자

### JavaDoc 작성법
- JavaDoc 주석 작성법들
