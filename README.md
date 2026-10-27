# gradle-test-project

Spring Boot 3.5.2 + Gradle + Thymeleaf + jQuery 샘플 프로젝트.

## 요구 사항
- Java 17 이상 (Gradle 설치는 필요 없음 — 포함된 Gradle Wrapper 8.14.3 사용)

## 실행
```bash
# Windows
gradlew.bat bootRun
# macOS / Linux
./gradlew bootRun
```
브라우저에서 http://localhost:8080 을 연다.

## 테스트 / 빌드
```bash
gradlew.bat test        # 테스트
gradlew.bat bootJar     # build/libs/gradle-test-project-0.0.1-SNAPSHOT.jar 생성
```

## 구성
| 경로 | 내용 |
|---|---|
| `build.gradle` | Spring Boot 3.5.2 플러그인, 의존성 (web, thymeleaf, validation, devtools, test, jQuery WebJar) |
| `DemoApplication` | 시작 클래스 |
| `web/HomeController` | `/` — Thymeleaf로 서버 렌더링하는 페이지 |
| `todo/TodoApiController` | `/api/todos` — jQuery Ajax가 호출하는 JSON API (조회·추가·완료 전환·삭제) |
| `todo/TodoService` | 메모리 저장소 (재시작하면 초기화) |
| `web/ApiExceptionHandler` | 입력값 검증 오류를 `{"message": "..."}`로 응답 |
| `templates/index.html` | Thymeleaf 템플릿 (`th:each`, `th:text`, `#temporals`) |
| `static/js/app.js` | jQuery로 목록 그리기·추가·완료·삭제 |
| `static/css/app.css` | 화면 스타일 |

jQuery는 CDN 대신 WebJar(`org.webjars.npm:jquery`)로 넣어 `/webjars/jquery/3.7.1/dist/jquery.min.js`에서 제공한다.
