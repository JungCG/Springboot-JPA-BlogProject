package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Controller : 해당 경로에 있는 파일을 리턴해준다.
@Controller
public class TempControllerTest {
	
//	http://localhost:8090/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome()");
		// 파일리턴 기본경로 : src/main/resources/static
		// 리턴명 : /home.html
		// 리턴명을 그냥 home.html로 작성하면 src/main/resources/statichome.html 을 찾는다.
		return "/home.html";
	}
	
	@GetMapping("/temp/img")
	public String tempImage() {
		System.out.println("tempImage()");
		return "/fsck.png";
	}
	
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		System.out.println("tempJsp()");
		
		// return "/test.jsp"; --> jsp는 정적인 파일이 아니라 동적인 파일(자바 파일)이기 때문에 찾지 못한다
		// 컴파일이 일어나야 하는 파일
		// 경로를 다르게 설정해줘야 한다.
//		return "/test.jsp";
		
//		spring:
//			  mvc:
//			    view:
//			      prefix: /WEB-INF/views/
//			      suffix: .jsp
		// return 값 양옆으로 위의 경로를 붙여준다.
		// 플네임 : /WEB-OMF/views/test.jsp
		// .jsp 파일의 위치는 src/main/webapp/WEB-INF/views
		return "test";
	}
}
