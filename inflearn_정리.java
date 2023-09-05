/**
 * 저장 : request.setAttribute(name, value)
 * 조회 : request.getAttribute(name)
 * 세션 : request.getSession(create: true)
 * 
 * HttpServletRequest 중요 포인트는
 * HTTP 요청 메시지, HTTP 응답 메시지를 편리하게 사용하도록 도와주는 객체.
 * 
 * -GET/POST/API 방식으로 데이터 주고 받음.
 * GET : 쿼리 파라미터 사용 ?를 시작으로, 추가 파라미터는 &로 구분.
 * POST : HTML의 Form을 사용, Command Object를 만들어 데이터 전송.
 * ㄴtest = postman 사용, Body, Headers에서 content-type:application/url 지정
 * API : POST, application/json, messagebody : {"username":"hello", "age":30}
 * 
 * MVC 패턴 적용 : Servlet을 controller사용, JSP를 뷰로 사용하여 MVC 패턴 적용
 * ㄴModel은 HttpServletRequest 객체를 사용, request.setAttribute(), .getAttribute()
 * ㄴServlet/JSP를 사용 안하고 FrontController, View(ModelView), model(아직은 map형태)로 분리
 * ㄴadapter가 도입 되면서, handler가 사용되고 어느 controller가 붙어도 adapter가 조율하여 다양한
 * controller를 사용가능케 한다.
 * 
 * -dispatcherServlet 도입 후, 동작 순서
 * 1.핸들러 조회 : 핸들러 매핑을 통해 요청 URL에 매핑된 헨들러(컨트롤러)를 조회한다.
 * 2.핸들러 어댑터 조회 : 핸들러를 실행할 수 있는 헨들러 어댑터를 조회한다.
 * 3.핸들러 어댑터 실행 : 핸들러 어댑러를 실행한다.
 * 4.핸들러 실행 : 핸들러 어댑터가 실제 헨들러를 실행한다.
 * 5.ModelAndView 반환 : 헨들러 어댑터는 헨들러가 반환하는 정보를 ModelAndView로 변환해서 반환한다.
 * 6.viewResolver 호출 : 뷰 리졸버를 찾고 실행한다.
 * 7.view 반환 : 뷰 리졸버는 뷰의 논리 이름을 물리 이름으로 바꾸고, 렌더링 역할을 담당하는 뷰 객체를 반환한다.
 * 8.뷰 렌더링 : 뷰를 통해서 뷰를 렌더링 한다.
 * 
 * Spring MVC 는 코드 분량이 많고 복잡해서 내부 구조를 다 파악하는 것은 쉽지 않다.
 * 그러나 최고의 강점은 DispatcherServlet 코드의 변경 없이, 원하는 기능을 변경하거나 확장할 수 있다는 점.
 * 지금까지 설명한 대부분을 확장 가능할 수 있게 인터페이스로 제공.
 */
