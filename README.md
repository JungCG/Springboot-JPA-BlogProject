# Springboot-JPA-BlogProject
1. **Basic knowledge of Spring Boot**
2. Creating a **blog website using spring boot**

-----------------

## Contents
1. [Using](#using)
2. [Spring Boot](#spring-boot)
3. [JPA](#jpa)
4. [아파치 톰켓](#아파치-톰켓)
5. [Http 통신](#http-통신)
6. [MIME 타입](#mime-타입)
7. [Maven](#maven)
8. [Lombok 세팅](#lombok-세팅)
9. [영속성 컨텍스트](#영속성-컨텍스트)
10. [Json 데이터로 통신하기](#json-데이터로-통신하기)
11. [DB 격리수준](#db-격리수준)
12. [Spring boot Transaction](#Spring-boot-transaction)
13. [CSRF와 XSS](#csrf와-xss)
14. [Spring Security](#spring-security)
15. [카카오 로그인을 통한 OAuth 개념이해](#카카오-로그인을-통한-oauth-개념이해)
16. [Licence](#license)

--------------------------------------------

## Using
1. **FrontEnd** - HTML5, JS, CSS3, jQuery
2. **BackEnd** - Java(JDK 1.8), MySQL(v8.0.25), JS, Spring Boot(2.3.12.RELEASE), JPA
3. **Library&API** - BootStrap(v4.5.2), JSTL, KAKAO API, Jasper, Spring Security, Lombok
4. **IDE** - STS (Spring Tool Suite 3.9.12.RELEASE), MySQL Workbench 8.0 CE

--------------------------------------------

## Spring Boot
- **스프링**이란?
    - **오픈 소스 프레임워크** : 틀 안에서 동작한다.
    - **IoC** (Inversion Of Control, 제어의 역전) 컨테이너를 가진다 : 주도권이 스프링에게 있다.
        - 클래스 (설계도), 오브젝트 (실체화가 가능한 것), 인스턴스 (실체화 된 것)
        - 내가 오브젝트를 직접 new 해서 힙 메모리 공간에 올리게 되면 이 주소가 'S'라고 했을 때 'S'를 관리하는 주체는 Stack Method 내부에서 관리 (공유가 힘들어진다)
        - IoC는 스프링이 직접 스캔해서 힙 메모리에 객체를 올려준다 (공유가 가능해짐)
    - **DI** (Dependency Injection, 의존성 주입)를 지원한다 : 예전에는 내가 new 해서 관리했다면 이제는 스프링이 스캔을 해서 메모리에 올렸기 때문에 스프링이 관리를 하게 되고 (IoC), 이 객체들을 내가 원하는 모든 곳 (클래스, 메소드)에서 가져와서 사용할 수 있다 (SingleTon)
        - 필요한 곳에서 가져다 쓰는 것을 DI라고 한다.
    - 엄청나게 많은 **필터**를 가지고 있다. (검열의 기능)
        - 톰켓이 들고 있는 filter, 파일은 web.xml
        - 스프링 컨테이너가 들고 있는 필터는 인터셉터라고 부른다. (AOP라는 개념 존재)
    - 엄청나게 많은 **어노테이션** (주석+힌트)을 가지고 있다. (리플렉션, 컴파일 체킹)
        - **리플렉션** : 스프링이 어떤 클래스를 스캔할 때 (읽어들일 때), 클래스 내부에 어떤 것이 있는지 분석하는 기법을 리플렉션이라고 한다. 리플렉션을 통해 메서드, 필드, 어노테이션을 체킹할 수 있다. 있는지 체킹하고 나서 있으면 어떤 행동을 하게끔 설정할 수 있다. 리플렉션은 런타임 시 일어난다.
        - **컴파일 체킹** : 어노테이션은 주석인데 주석은 컴파일러가 무시하는 것이고, 어노테이션의 주석은 컴파일러가 무시하는 것이 아니라 뭔가를 Checking 할 수 있게 힌트를 주는 주석이다.
    - **스프링에서는 어노테이션을 이용해서 객체 생성을 한다.**
        - @Component (해당 클래스를 스프링이 관리하는 힙 메모리에 로딩)
        - @Autowired (로딩된 객체를 해당 변수에 집어 넣는다, new 로 생성하는 것이 아니라)
    - **MessageConverter**를 가지고 있다. (기본 값은 JSON)
        - 중간 데이터를 만든다.
        - 예를 들어 java object와 python object가 있을 때 java->json->python 순으로 전송이 된다.
        - MessageConverter는 java를 json으로 바꿔주는 역할, MessageConverter는 기본적으로 Jackson으로 설정되어 있고 json으로 변경해주는 라이브러리이다.
        - BufferedReader와 BufferedWriter를 쉽게 사용할 수 있다.
        - (+) InputStream은 바이트 전송, (char)와 같이 변환이 필요
        - (+) InputStreamReader는 문자 하나를 전송하고 배열로 여러 개의 문자를 보낼 수 있음, 그러나 배열이기 때문에 크기를 정해둬야 한다.
        - BufferedReader는 가변길이의 문자를 받을 수 있다. (PrintWriter도 있음.)
        - BufferedReader, Writer를 위한 어노테이션을 제공해준다. @ResponseBody는 BufferedWriter, @RequestBody는 BufferedReader 동작
        
--------------------------------------------

## JPA
- **JPA**란?
    - **Java Persistence API** : 자바 프로그래밍을 할 때 영구적으로 데이터를 저장하기 위해서 필요한 인터페이스
    - **Persistence** : 데이터를 생성한 프로그램의 실행이 종료되더라도 사라지지 않는 데이터의 특성을 의미한다.
        - RAM은 휘발성, 하드디스크에 기록하면 비휘발성이기에 영속
        - DBMS과 같은 시스템에 저장하면 영속
        - 자바의 데이터를 영구히 저장할 수 있게 해주는 API다.
        - (+) API란? Application Programming Interface : 프로토콜과 인터페이스는 둘 다 약속을 의미, 인터페이스는 상하관계가 존재하는 약속, 프로토콜은 동등한 관계, www은 인터페이스가 아닌 프로토콜로 만들어짐.
    - **ORM** (Object Relational Mapping, 객체와 관계형 데이터베이스의 데이터를 자동으로 매핑해주는) 기술이다.
        - 오브젝트를 데이터베이스에 연결하는 방법론
        - 클래스를 먼저 만들고, 테이블을 만든다. 이때 필요한 것이 JPA가 가지고 있는 인터페이스이다.
        - (+) SQL Mapping과의 차이점 : ORM을 사용한다면, SQL문 작성 없이 매핑하는 설정만으로 DB 테이블내의 데이터를 객체로 전달 받을 수 있다. SQL Mapping은 객체와 SQL 문을 매핑하여 데이터를 객체화하는 기술, 객체와 관계를 매핑하기보다는 SQL문의 질의 결과와 객체를 매핑 시켜주기 때문에 ORM과는 다른 기술이라고 할 수 있다. 대표적인 프레임워크로는 iBatis, MyBatis, Oracle SQLJ가 있다.
    - 반복적인 CRUD 작업을 생략하게 해준다.
    - **영속성 컨텍스트**를 가지고 있다.
        - 영속성이란 어떤 데이터를 영구적으로 저장하는 성질. 컨텍스트 (context)는 어떤 대상의 모든 정보를 가지고 있는 것.
    - DB와 OOP의 불일치성을 해결하기 위한 방법론을 제공한다. (DB는 객체저장 불가능)
    - **OOP의 관점에서 모델링을 할 수 있게 해준다.**
    - OOP 관점에서 테이블 생성 = 데이터베이스를 먼저 만들고 테이블을 통해 Class를 모델링하는 것이 아니라 Class를 먼저 만들고 Class를 토대로 자동 생성해서 데이터베이스에 테이블을 만들어주는 기법
    - 방언 처리가 용이하여 Migration하기 좋음
        - Dialect 종류 다양
        - Oracle, MariaDB, MySQL 등 정해놓고 사용하는 것이 아니라 추상화 객체를 사용. 유지보수 용이
        
--------------------------------------------

## 아파치 톰켓
- (+) **소켓 통신** : Time Slice해서 동시에 동작하는 것으로 보임, 실제로는 동시가 아닌 시간을 쪼개서 동작하는 것. 연결이 계속 이어지고 있기 때문에 수가 늘어나면 생각보다 부하가 크다.
- (+) **HTTP 통신** (웹) : 문서(html)를 전달하는 통신. 연결은 지속시키지 않고, 끊어버리는 Stateless 방식
    - A, B가 있을 때 C에 연결하려 할 때, A가 요청하면 C는 새로운 포트를 여는 것이 아니라 곧바로 응답하고 연결을 끊어버린다. B가 요청했을 때도 동일하고 응답하고 끊어버린다. 소켓 통신은 부하가 크지만 한번 연결되면 계속 연결되어 있기 때문에 대상이 누군지 알 수 있다. HTTP 통신은 연결을 끊기 때문에 부하가 적지만 다시 연결될 때는 항상 새로운 대상으로 인식한다.
- (+) **웹 서버는 정적인 자원**, static 자원을 지원. 주로 아파치라는 웹서버를 사용한다.
- (+) 웹 브라우저는 .html, javascript, css, avi 같은 정적인 자원만 인식 가능, .jsp 같은 건 인식 못해서 깨진다. **웹 서버가 이해하지 못하는 요청이 오면 제어권을 톰켓에게 넘겨준다.** 톰켓은 .jsp 파일 안에 있는 모든 자바코드를 컴파일하고 컴파일이 끝나면 그 데이터를 .html에다가 덮어씌운다. 톰켓이 하는 일은 자바 코드를 컴파일하고 html 문서로 만들어주는 것이다. 그 다음 아파치에게 다시 돌려주고 해당 .html 파일을 응답해준다. **그래서 톰켓이 필요하다.**
- **서블릿 컨테이너**
    - 클라이언트가 요청을 한다. 서블릿 컨테이너(톰켓)이 요청을 받고
    - (1) 최초 요청일 경우 메모리 로딩-객체생성-init
    - (2) 최초 요청이 아니면 객체를 생성하지 않고 이미 생성된 객체를 재사용하고 Service(HttpServletRequest, HttpServletResponse)
    - 요청을 하게 되면 요청을 받을 때 무조건 동작하는 것이 아니라 정적인 파일을 요청하게 되면 아파치가 동작, 톰켓은 일을 안 한다.
    - 스프링은 URL 접근 방식을 사용 못하도록 막아뒀다. 스프링은 식별자를 통해서 요청해야 한다. : 특정한 파일 요청을 할 수 없다. 요청 시에는 무조건 자바를 거쳐야 한다. 즉 아파치는 무조건 톰켓에 제어권을 넘겨준다.
        - URL(location) : 자원에 접근할 때 사용하는 주소 요청 방식 / http://naver.com/a.png
        - URI(Identifier, 식별자) : 식별자를 통해 접근하는 방식 / http://naver.com/picture/a
    - 클라이언트가 자바 관련된 request를 하게 되면 Servlet이 만들어져야 한다.
    - **Servlet이란 자바 코드로 웹을 할 수 있는 것**. **Servlet Container는 Servlet을 모아둔 것** (톰켓).
    - 요청을 했을 때 최초에 요청이 들어오면 스레드(1번)를 하나 만든다. 그리고 해당 스레드가 서블릿 객체를 하나 만든다. (request는 동시에 여러 개가 오기 때문에 요청이 올 때마다 스레드를 만든다. 동시 접근을 가능하게 하기 위해 스레드를 만든다. 무한정 만들어지는 것은 아니다. 내가 한계를 정해놓고 요청이 그 한계를 넘어가면 대기한다. 앞에 스레드가 응답이 끝나면 stateless기 때문에 사라지는 것 같지만 사라지는 것이 아니라 그 스레드와 객체는 재사용하여 대기 중이던 요청을 처리, 그래서 속도가 빠른 것) 한계까지는 계속 스레드, 객체생성 하다가 한계에 달하면 재사용 시작. **HttpServletRequest, HttpServletResponse 객체는 스프링이 아니라 톰켓이 들고 있는 객체, 톰캣이 만든다.**
- **web.xml 하는 일** : 초반에 사용. **웹 서버에 진입을 하면 최초에 도는게 web.xml**
    - **SevletContext의 초기 파라미터 설정**
    - **Session의 유효시간 설정**
    - **Servlet, JSP에 대한 정의 및 매핑**
    - **Mime** (Multipurpose Internet Mail Extenstions) **Type 매핑** : 요청이 가지고 온 데이터의 타입
    - **Welcome File List** : 아무 데이터 없이 들어 왔을 때 볼 페이지
    - **Error Pages 처리**
    - **리스너, 필터 설정**
    - **보안**
        
--------------------------------------------

## Http 통신
- Http 통신 방법 4가지 (클라이언트가 서버에 보내는 요청 4가지)
    1. **GET** : 서버에 존재하는 데이터를 가져올 때 (**SELECT**)
    2. **POST** : 데이터 추가 (**INSERT**)
    3. **PUT** : 데이터 수정 및 추가 (**UPDATE, INSERT**)
    4. **DELETE** : 데이터 삭제 (**DELETE**)
- **Stateless와 Stateful**
    - **Stateful** : 연결이 지속된다. 인증이 이루어지면 세션이 생겨서 연결 지속, 채팅에 사용
    - **Stateless** : 요청시마다 스트림을 연결해서 Data를 주고받는 방식, HTTP는 Stateless
    - (+) Spring에서는 Security를 이용해서 Session을 유지한다.

--------------------------------------------

## MIME 타입
- **클라이언트가 서버에서 Post 요청할 때 어떤 데이터를 원하는지를 HTTP Header와 Body에 담아서 보낸다.**
- **HTTP Header : 데이터를 설명해주는 내용, HTTP Body : 실제 데이터**
- **클라이언트에게 전송된 문서의 다양성을 알려주기 위한 메커니즘**이다. 웹에서 파일의 확장자는 별 의미가 없다. 그러므로 각 문서와 함께 올바른 MIME 타입을 전송하도록, 서버가 정확히 설정하는 것이 중요하다.

--------------------------------------------

## Maven
- **메이븐을 사용하지 않는 경우 예**
    - OJDBC가 필요할 때 오라클 사이트에서 jar 다운받아서 내 프로젝트 lib 폴더에 복사하고, build
    - 다른 프로젝트에서도 똑같이 lib 폴더를 만들어서 jar 복사, 이렇게 되면 각 프로젝트에 하나씩 존재
    - 카피하지않고 lib를 만들지 않고 c\lib를 만들고 해당 경로를 빌드하면 중복 제거
    - 내가 이 프로젝트를 배포하려고 하면 해당 서버에 다시 lib를 만들어야 한다.
- **메이븐을 사용할 경우 예**
    - **중앙 저장소**를 하나 만든다. 이 저장소에 저장해둠
    - **프로젝트에서 중앙 저장소에 연결**
    - **pom.xml 파일에 내가 받아야할 라이브러리를 기술**만 함 -> 프로젝트를 시작할 때 Maven (프로젝트 관리도구)이 pom.xml을 읽어서 중앙 저장소에서 자동으로 다운로드 (.m2 숨김폴더에 repository 폴더를 만들고 내부에 다 다운받아주고 자동 빌드해줌) -> 배포할 때 Maven만 설치되어 있으면 동일하게 자동 다운, 빌드

--------------------------------------------

## Lombok 세팅
1. 프로젝트의 .m2 폴더로 이동, lombok 찾기
2. jar 위치에서 Command
    ```bash
    java jar {lombor_jar_file.jar}
    ```
3. STS IDE 지정, Install

--------------------------------------------

## 영속성 컨텍스트
- (+) 통신 과정은 컨트롤러로 Request가 들어오고 Response를 해주는 방식
- (+) Request 종류는 Insert, Update, Delete, Select
- **영속성 컨텍스트**(**Persistence Context**) 예시
    1. **Insert**
        - Controller에서 User 객체를 만들어서 **Save**
        - **영속성 컨텍스트 안에 있는 1차 캐시**에 User 객체가 쌓인다. (**1차 캐시에 쌓인 상태를 영속화 되었다**고 한다)
        - 그 다음 DB에 User 객체를 저장한다. (**1차 캐시에서 DB로 들어가는 것이 Flush**)
        - 이후에 **1차 캐시의 데이터는 바로 사라지지는 않는다.**
    2. **Select**
        - 원하는 User 객체를 **Select 하면 제일 먼저 그 객체가 영속화 되었는지 여부를 확인**한다.
        - 만약 **영속화가 되어있다면 굳이 DB까지 가서 데이터를 가져오지 않고 1차 캐시에서 들고 온다.** (부하가 덜하다)
        - 없을 경우에는 DB에서 1차 캐시로 영속화하고 데이터를 Controller로 리턴해준다.
    3. **Update**
        - 특정 User 객체를 Update 한다고 하면 **첫 번째로 DB에서 그 데이터를 읽어와서 영속화를 시킨다.**
        - 그 다음 그 객체를 Controller로 가져와서 **값을 변경**한다.
        - 변경 후 Save를 호출하면 1차 캐시의 영속화된 객체와 비교하여, 아이디가 같은 **영속화된 객체의 값을 변경한 값으로 Update하고 1차 캐시의 객체를 Flush한다.**

--------------------------------------------

## Json 데이터로 통신하기
1. **Get 요청** (select)
    - **주소에 데이터를 담아 보낸다. 데이터 형태는 key=value**
    - ex) http://localhost:8090/myblog/user?username=myname
    - Body로 데이터를 담아 보내지 않는다.
2. **Post, Put, Delete 요청** (데이터를 변경)
    - **Body에 데이터를 담아 보낸다.** 데이터 형태는 JSON으로 통일하는 것이 좋다.
    - **form 태그** -> **get 요청, post 요청 (key=value 형태)만 가능** -> 자바 스크립트로 요청해야 한다.
    - 자바스크립트 Ajax 요청 + 데이터는 JSON으로 통일 : 지금 프로젝트에서 사용
    - (+) **form:form 태그** -> **post, get, delete, put 가능** : 지금 프로젝트에서 사용 X
3. **스프링 컨트롤러의 파싱 전략 1**
    - **스프링 컨트롤러는 key=value 데이터를 자동으로 파싱하여 변수에 담아준다.**
    - 가령 get 요청은 key=value 이고 **post 요청중에 x-www-form-urlencoded** (**form 태그를 만들어서 데이터 전송**) 시에도 key=value 이기 때문에 이러한 데이터는 아래와 같이 함수의 파라메터로 받을 수 있다.
        ```java
        public String home(String username, String email)
        ```
4. **스프링 컨트롤러의 파싱 전략 2**
    - 스프링은 key=value 형태의 데이터를 **오브젝트로 파싱해서 받아주는 역할**도 한다.
    - **이때 주의할 점은 setter가 없으면 key=value 데이터를 스프링이 파싱해서 넣어주지 못한다.** -> 에러 발생
        ```java
        public String home(User user)
        ```
    - **클래스에 없는 필드를 보내면 오류는 없고 무시**
5. **key=value가 아닌 데이터 파싱**
    - json 데이터나 일반 text 데이터는 스프링 컨트롤러에서 받기 위해서는 **@RequestBody 어노테이션**이 필요하다. 기본 전략이 스프링 컨트롤러는 key=value 데이터를 파싱해서 받아주는 일을 하는데 다른 형태의 데이터 가령 json 같은 데이터는 스프링이 파싱해서 오브젝트로 받지 못한다. 그래서 **@RequestBody 어노테이션을 붙이면 MessageConverter 클래스를 구현한 Jackson 라이브러리가 발동하면서 JSON 데이터를 자바 오브젝트로 파싱하여 받아준다.**
6. **form 태그로 JSON 데이터 요청 방법**
    - key=value 데이터가 아니라 json 데이터를 어떻게 전송할 수 있을까?
    - **Javascript + Ajax를 통해서 전송**
7. (+) 추가 : **회원가입 시 Ajax를 사용하는 이유**
    1. **요청에 대한 응답을 html이 아닌 Data(Json)을 받기 위해서**
        - 클라이언트는 브라우저로 서버에 요청
        - 서버는 html로 응답
        - 회원가입 자체를 요청하면 서버는 회원가입을 수행 -> DB에 수행을 하고 서버에서 다시 클라이언트로 던져주는 것을 Data로 통일 -> 이럴 경우 **웹, 앱 구분 없이 하나의 서버 구현으로 실행 가능**
    2. **비동기 통신을 하기 위해서**
    
--------------------------------------------

## DB 격리수준
1. (+) 트랜잭션 : 일이 처리되기 위한 가장 작은 단위
2. **Oracle : Read Commit**
    - A가 트랜잭션 시작, Update문 실행
    - **다음 B가 변경된 데이터를 Select하면 변경 전 데이터를 보게 된다.**
    - 다음 A가 Commit
    - **B가 다시 Select 하면 변경 후 데이터를 보게 된다.**
    - **undo 영역 : (Commit된 영역) B가 읽는 영역, redo 영역 : (아직 Commit 되지 않은 영역)**
    - **데이터의 정합성이 깨진 이러한 경우를 팬텀 리드 (Phantom read)라고 부른다.**
    - **해결하기 위해서는 Repeatable Read 사용해야 한다.**
2. **MySQL : Repeatable Read**
    - InnoDB 스토리지 엔진은 Repeatable Read를 사용하여 부정합이 발생하지 않는다.
    - **트랜잭션이 시작되고 종료될 때까지 항상 동일한 값이 나온다 = 다른 곳에서 값을 변경해도 계속 같은 (변경 전) 데이터가 나온다.**
    - **자기 트랜잭션 번호보다 낮은 undo 로그만 보고 Select 하는 것이 Repeatable Read**
    - **스프링에서 Insert, Delete, Update는 트랜잭션이 필요하기 때문에 꼭 @Transactional을 붙인다.**
    - 정합성을 유지하기 위해서 Select할 때에도 @Transactional을 붙인다.

--------------------------------------------

## Spring boot Transaction
1. **최초 실행할 때**
    1. **스프링 시작**
    2. **톰켓 시작** (서버 작동)
    3. **web.xml** (권한, 인증, 인코딩 등 걸러내야 할 필터들이 메모리에 올라옴)
    4. **context.xml** (DB 연결 테스트, DataSource 하나가 메모리에 뜨고 사용자 요청마다 생기는 것이 아니라 하나만 존재)
2. **클라이언트 요청 시**
    1. **Request**
    2. **web.xml** -> **필터** (**영속성 컨텍스트 시작**, 사용자마다 생성이 된다.)
    3. **스프링 컨테이너** -> **Controller** (요청 분기) -> 이때 **DB 연결 세션 생성, 트랜잭션 시작**
    4. **Service** (요청) -> **Repository** -> DB 갔다가 Select 한 결과를 **영속성 컨텍스트에 객체화**
    5. **Service에서 값만 변경** -> (**자동으로 영속성 컨텍스트에 있는 데이터 변경 감지**) -> **flush**
    6. **Service가 종료될 때 DB 연결 세션 종료하고 Transaction을 종료**
    7. **Controller로 돌아온다.** 만약 여기서 프록시 (Lazy 기법, OneToMany)를 접근하려 하면 (Select만 가능) JDBC 커넥션이 시작되서 DB에 갔다가 데이터를 읽어오고 커넥션 종료
    8. **영속성 컨텍스트 종료** -> 결과를 **Response**
3. **클라이언트 요청 시 요약**
    - Request -> Controller에 Body 데이터 내용이 넘어감 -> Service -> Repository -> 영속성 컨테스트 확인 -> DataSource -> DB -> DataSource -> 영속성 컨텍스트 -> Repository -> Service -> Controller (@Controller 일 경우 항상 View Resolver가 작동)

--------------------------------------------

## CSRF와 XSS
1. **CSRF** (Cross Site Request Forgery)
    - 하이퍼링크인 GET 방식은 사용하지 말고, **POST 방식으로 설계**
    - **Refferer 검증** : 같은 도메인 상에서 요청이 들어오지 않는다면 차단하는 방법
    - **CSRF 토큰 사용** : 사용자 세션에 CSRF 토큰을 저장, 요청 페이지에 토큰을 설정해서 서버에 전송하여 정상, 비정상을 구분한다. (외부 공격 모두 막을 수 있음.)
2. **XSS** (Cross Site Scripting)
    - **자바 스크립트로 공격하는 방법**
    - Naver의 Luzy 필터 등을 사용하면 간단하게 막을 수 있다.

--------------------------------------------

## Spring Security
1. **Security**
    - **Security로 로그인할 때 username, password를 가로채서 로그인 진행**
    - 로그인 진행 완료되면 Security가 들고 있던 전용 세션으로 유저정보를 등록 (IoC)
    - DI로 유저 정보 사용 가능
    - **직접 만든 오브젝트는 저장할 수 없고 타입이 정해져 있다. Type : UserDetails**
    - 사용 방법
        1. **UserDetails가 우리가 만든 클래스를 extends**
        2. **형변환**
    - password는 1234와 같은 것은 Security에서 사용 못하도록 막는다. 해쉬 변환을 해야 사용 가능
2. **Spring Security**
    - **Session에 Security Context라고 불리는 특정 공간을 만들어서 그 안에 Authentication 객체를 만들어서 관리**
    - **Authentication 객체는 AuthenticationManager가 만들어준다.** 만들기 위해서는 username과 password를 알아야 생성할 수 있다.
    - **Spring Security 예시**
        1. 사용자가 **로그인 요청** (username : sename, password : 1234)
        2. **AuthenticationFilter (로그인 필터)가 가로챈다.**
        3. **UsernamePasswordAuthenticationToken** 을 username과 password로 입력한 값을 기반으로 만든다.
        4. **이 토큰을 AuthenticationManager에 보내면 Manager가 세션을 만들어 준다.**
    - **세션을 만들기 위한 조건**을 만족해야 만들어진다.
        1. **AuthenticationManager가 username을 UserDetailService (PrincipalDetailService (implements UserDetailService) )로 보낸다.**
        2. **UserDetailService는 DB에 username이 존재하는지 질의**를 한다.
        3. 있으면 Manager에 있다고 응답을 해주고 **Manager가 password를 저장할 때 사용했던 암호화 인코딩 방식을 사용하여 DB에 확인**
        4. **맞으면 Authentication 객체를 만들어서 세션에 저장**

--------------------------------------------

## 카카오 로그인을 통한 OAuth 개념이해
- 내 사이트에서 회원가입을 하지 않고 Naver나 Kakao에 위임하여 **인증 처리 수고를 덜 수 있다.**
- **OAuth : Open Auth, 인증 처리를 대신해주는 개념**
    1. 내 사이트에서 로그인할 때
        - 사용자 : 클라이언트
        - **내 사이트 : 서버**
    2. 카카오 로그인으로 로그인할 때
        - 사용자 : 리소스 오너
        - **내 사이트 : 클라이언트**
        - **카카오 API 서버 : OAuth 서버 (인증 서버)**
        - **카카오 자원 서버 : 리소스 서버**
- **인증 순서**
    1. **사용자가 내 사이트로 로그인 요청을 하는 것이 아니라 카카오 API 서버로 로그인 요청을 다이렉트로 보낸다.**
    2. **정상이면** 카카오 API 서버에서 내 사이트로 Callback하고 내 사이트에서 **코드를 확인**할 수 있다. (**여기까지가 인증처리 완료**, "코드를 받았다.")
    3. **이 코드 값을 가지고** 내 사이트에서 카카오 API 서버에 카카오 **자원서버에 대한 자원을 요청**한다.
    4. 카카오 API 서버에서 내 사이트로 **Access Token을 넘겨준다.** (**자원접근 권한 부여 상태**, "Access Token을 받았다.")
    5. 접근 권한이 생겼기 때문에 내 사이트가 자원 서버에 가서 사용자의 정보에 접근할 수 있다.
- **스프링 공식 OAuth 지원 주체 : Facebook, Google**
- **Spring Security 안**에 **OAuth**-**client** (OAuth2) 라이브러리, **Resource Server**를 만들 수 있는 라이브러리 존재

--------------------------------------------

## License
- **Source Code** based on [codingspecialist'lecture](https://github.com/codingspecialist)