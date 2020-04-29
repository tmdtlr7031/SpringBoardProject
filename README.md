# SpringBoardProject
Spring에 기능넣기

---

## 환경
- 전자정부 3.9
- JAVA 8
- tomcat 8.5
- maven 4.3.22
- mariaDB 10.3.22

#### init
- 사이트매쉬 적용
- 마지막 접속시간 , 로그인 이후 타이머 설정 (30분 동안 행동 안하면 로그아웃처리), 로그인 연장 타이머

#### 2020-04-27
	- poi를 이용한 엑셀 다운로드 구현
#### 2020-04-29
	- xls 확장자의 경우, 엑셀 파일 업로드 & 파일 읽어서 DB Insert처리 (중복제거는 하지않음.)
	- 업로드 후 업로드한 파일을 따로 보관을 하지 않기 위해 파일 삭제 로직 작성
	- insertDashBoardExcelUpload에 개발 관련한 주석 추가





 
### TODO List
- 아래 내용들에 맞는 DB 테이블 추가
- 마지막 로그인 시간 DB 에서 조회한 값으로
- 로그인한 사용자인지 확인하는 인터셉터 (또는 로직)
- 웝로그 인터셉터 처리
- 특정 ip만 접근가능하게 처리해보자 (인터셉터 이용)
- poi말고 다른 방식으로 엑셀 다운로드 구현
- xls 말고 xlsx도 업로드 되게
- 파일 다운로드, 파일 업로드
- 시각화 사용하기 (하이차트 or canvasJS)
- 다중 파일 업로드 (multiple)
- 중복로그인 방지