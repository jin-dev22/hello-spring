package com.kh.spring.proxy;

public class ProxyMain {

//	FooService fooService = new FooServiceImpl();//이부분을 의존주입이라고 생각하기...
	FooService fooService = new FooProxy(new FooServiceImpl(), new Aspect());//이렇게 프록시에 우리가 만들어야 될 임플객체, aspect 넣어줌
	
	public static void main(String[] args) {
		new ProxyMain().test();
	}

	//주업무코드 : 이 주업무코드는 변경하지 않고 보조업무를 끼워넣음
	private void test() {
		String name = fooService.getName();
		System.out.println(name);
	}

}

class FooProxy implements FooService{
	
	FooService fooService;
	Aspect aspect;
	
	public FooProxy(FooService fooService, Aspect aspect) {
		this.fooService = fooService;
		this.aspect = aspect;
	}
	
	@Override
	public String getName() {
		//before
		aspect.beforeAdvice();
		
		String name = fooService.getName();// 주업무 : joinPoint실행
		
		//after 
		return aspect.afterAdvice(name);//after에서 주업무의 값을 변경시킬수도 있음. xss공격대비 문자열 변환처리등에 활용할 수 있다.
		
//		return name;
	}
}

//보조업무
class Aspect{
	
	public void beforeAdvice() {
		System.out.println("before!!");
	}
	
	public String afterAdvice(String str) {
		System.out.println("after!!");
		return str.toUpperCase();
	}
}

interface FooService {
	String getName();
}
class FooServiceImpl implements FooService{
	@Override
	public String getName() {
		return "abcde";
	}
}
