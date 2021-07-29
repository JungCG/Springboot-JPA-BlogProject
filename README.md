# Springboot-JPA-BlogProject
1. **Spring boot related Basic Knowledge**
2. Creating a **blog website using spring boot**

-----------------

## Contents
1. [Using](#using)
2. [Spring Boot](#spring-boot)
3. [JPA](#jpa)
4. [Spring Boot 동작원리](#spring-boot-동작원리)
5. [Http1.1 통신](#http1.1-통신)
6. [MIME 타입](#mime-타입)
7. [Maven](#maven)
8. [Lombok 세팅](#lombok-세팅)
9. [영속성 컨텍스트](#영속성-컨텍스트)
10. [Json 데이터로 통신하기](#json-데이터로-통신하기)
11. [DB격리수준](#db격리수준)
12. [Spring boot Transaction](#Spring-boot-transaction)
13. [Spring Security](#spring-security)
14. [CSRF와 XSS](#csrf와-xss)
15. [카카오 로그인을 통한 OAuth2.0 개념이해](#카카오-로그인을-통한-oauth2.0-개념이해)
16. [무한 참조 방지하기](#무한-참조-방지하기)
17. [Licence](#license)

--------------------------------------------

## Using
1. **FrontEnd** - HTML5, JS, CSS3, jQuery
2. **BackEnd** - Java(JDK 1.8), MySQL(v8.0.25), JS, Spring Boot(2.3.12.RELEASE), JPA
3. **Library&API** - BootStrap(v4.5.2), JSTL, KAKAO API, Jasper, Spring Security, Lombok
4. **IDE** - STS (Spring Tool Suite 3.9.12.RELEASE), MySQL Workbench 8.0 CE

--------------------------------------------

## Spring Boot
1. 스프링이란?
    - 오픈 소스 프레임워크 : 틀 안에서 동작한다.
    - IoC (Inversion Of Control, 제어의 역전) 컨테이너를 가진다 : 주도권이 스프링에게 있다.
        - 클래스 (설계도), 오브젝트 (실체화가 가능한 것), 인스턴스 (실체화 된 것)
        - 내가 오브젝트를 직접 new 해서 힙 메모리 공간에 올리게 되면 이 주소가 'S'라고 했을 때 'S'를 관리하는 주체는 Stack Method 내부에서 관리 (공유가 힘들어진다)
        - IoC는 스프링이 직접 스캔ㅇ해서 힙 메모리에 객체를 올려준다 (공유가 가능해짐)
    - DI (Dependency Injection, 의존성 주입)를 지원한다 : 예전에는 내가 new 해서 관리했다면 이제는 스프링이 스캔을 해서 메모리에 올렸기 때문에 스프링이 관리를 하게 되고 (IoC), 이 객체들을 내가 원하는 모든 곳 (클래스, 메소드)에서 가져와서 사용할 수 있다 (SingleTon)
        - 필요한 곳에서 가져다 쓰는 것을 DI라고 한다.
    - 엄청나게 많은 필터를 가지고 있다. (검열의 기능)
        - 톰켓이 들고 있는 filter, 파일은 web.xml
        - 스프링 컨테이너가 들고 있는 필터는 인터셉터라고 부른다. (AOP라는 개념 존재)
    - 엄청나게 많은 어노테이션 (주석+힌트)을 가지고 있다. (리플렉션, 컴파일 체킹)
        - 리플렉션 : 스프링이 어떤 클래스를 스캔할 때 (읽어들일 때), 클래스 내부에 어떤 것이 있는지 분석하는 기법을 리플렉션이라고 한다. 리플렉션을 통해 메서드, 필드, 어노테이션을 체킹할 수 있다. 있는지 체킹하고 나서 있으면 어떤 행동을 하게끔 설정할 수 있다. 리플렉션은 런타임 시 일어난다.
        - 컴파일 체킹 : 어노테이션은 주석인데 주석은 컴파일러가 무시하는 것이고, 어노테이션의 주석은 컴파일러가 무시하는 것이 아니라 뭔가를 Checking 할 수 있게 힌트를 주는 주석이다.
    - 스프링에서는 어노테이션을 이용해서 객체 생성을 한다.
        - @Component (해당 클래스를 스프링이 관리하는 힙 메모리에 로딩)
        - @Autowired (로딩된 객체를 해당 변수에 집어 넣는다, new 로 생성하는 것이 아니라)
    - MessageConverter를 가지고 있다. (기본 값은 JSON)
        - 중간 데이터를 만든다.
        - 예를 들어 java object와 python object가 있을 때 java->json->python 순으로 전송이 된다.
        - MessageConverter는 java를 json으로 바꿔주는 역할, MessageConverter는 기본적으로 Jackson으로 설정되어 있고 json으로 변경해주는 라이브러리이다.
        - BufferedReader와 BufferedWriter를 쉽게 사용할 수 있다.
        - (+)InputStream은 바이트 전송, (char)와 같이 변환이 필요
        - (+)InputStreamReader는 문자 하나를 전송하고 배열로 여러 개의 문자를 보낼 수 있음, 그러나 배열이기 때문에 크기를 정해둬야 한다.
        - BufferedReader는 가변길이의 문자를 받을 수 있다. (PrintWriter도 있음.)
        - BufferedReader, Writer를 위한 어노테이션을 제공해준다. @ResponseBody는 BufferedWriter, @RequestBody는 BufferedReader 동작
        
--------------------------------------------

## JPA
- To be written

--------------------------------------------

## Spring Boot 동작원리
- To be written

--------------------------------------------

## Http1.1 통신
- To be written

--------------------------------------------

## MIME 타입
- To be written

--------------------------------------------

## Maven
- To be written

--------------------------------------------

## Lombok 세팅
- To be written

--------------------------------------------

## 영속성 컨텍스트
- To be written

--------------------------------------------

## Json 데이터로 통신하기
- To be written

--------------------------------------------

## DB격리수준
- To be written

--------------------------------------------

## Spring boot Transaction
- To be written

--------------------------------------------

## Spring Security
- To be written

--------------------------------------------

## CSRF와 XSS
- To be written

--------------------------------------------

## 카카오 로그인을 통한 OAuth2.0 개념이해
- To be written

--------------------------------------------

## 무한 참조 방지하기
- To be written

--------------------------------------------

## License
- **Source Code** based on [codingspecialist'lecture](https://github.com/codingspecialist)