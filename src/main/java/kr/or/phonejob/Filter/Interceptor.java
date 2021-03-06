package kr.or.phonejob.Filter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.or.phonejob.Dto.LoginFilterDto;
import kr.or.phonejob.Service.GoogicService;
import kr.or.phonejob.Service.GooinService;
import kr.or.phonejob.Service.LoginFilterService;
 

@Configurable
public class Interceptor extends HandlerInterceptorAdapter {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	LoginFilterService lfservice;
	
	@Autowired
	private GoogicService gservice;
	
	@Autowired
	private GooinService gooservice;
   
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	
    	
    	logger.info("구직자 등록 수 계산!!!");
	    //구직자 등록 수 계산
	  	int googicCount = gservice.countAllGoogic();
	  	
	  	logger.info("구인글 등록 수 계산!!!");
	  	//구인글 등록 수 계산
	  	int gooinCount = gooservice.countAllGooin();
	  	  
	  	logger.info("종료된 구인글 수 계산!!!");
	  	//종료된 구인글 수 계산
	  	int gooinEndCount = gooservice.countEndGooin();
	  	  
    	
    	
    	logger.info("로그인 관련 인터셉터 작동!!");
    	HttpServletRequest httpRequest = (HttpServletRequest) request;
    	
    	httpRequest.setCharacterEncoding("UTF-8");
		boolean login = false;
		
        HttpSession session = request.getSession();    
        session.setAttribute("googicCount", googicCount);
        session.setAttribute("gooinCount", gooinCount);
        session.setAttribute("gooinEndCount", gooinEndCount);
        
        
		String[] uri = request.getRequestURI().split("/");
		String realuri = "/"+uri[uri.length-1]; // 배열의 마지막 값이 파일이름

		logger.info("접근 URI : " + realuri);
		
		
        
		//////////////// 이전 페이지 세션 저장  ////////////////////////
		String referrer = request.getHeader("Referer");
		
		if(referrer !=null){
			String[] backUrl = referrer.split("/");
			String realbackUrl = "/"+backUrl[backUrl.length-1];
			
			if(realbackUrl.equals("/login.do")){
				logger.info("자꾸 로그인으로 들어올래?");
				referrer = "/index.do";
			}			
		}
		
		request.getSession().setAttribute("prevPage", referrer);
	    logger.info("Referer 세션 변수!!" + referrer);
	    ////////////////이전 페이지 세션 저장  ////////////////////////
	    
	    List<LoginFilterDto> result = lfservice.getPassUri();
	    
	    
	    for(int i=0; i< result.size(); i++){
	    	logger.info("result : " + result.get(i).toString());
	    	if(realuri.equalsIgnoreCase(result.get(i).getUri())){ 
	    		logger.info("로그인 패스와 매칭되는 URI가 있음!");
	    		
				if (session.getAttribute("loginData") != null) {
					logger.info("가입고객인인데 회원가입으로 갈 수 있으니 로그인 정보를  조회합시다.(loginData)");
					
					if(realuri.equals("/privateRegister.do")||realuri.equals("/login.do")){
						logger.info("회원가입을 또할라고?..");
						response.sendRedirect(request.getContextPath()+"/index.do");
			        	return false;
					}
				}
				
				login = true;
	    		return super.preHandle(request, response, handler);
	    	}else{
	    		logger.info("로그인 패스와 매칭되는 URI가 없음!!");

	    	}
	    	
	    }
	    
	    
		logger.info("그렇다면 세션 검사를 시작!");
		if (session != null) {
			if (session.getAttribute("loginData") != null) {
				logger.info("로그인 세션 데이터가 있으니까 통과해도 됨!!");
				login = true; // 세션변수가 null이 아닐경우 true로 설정.
			}
		}
		
	    
	    
		if (login) {
			logger.info("로그인 세션 데이터가 있거나 Pass니까 그대로 쭉!! 고!!!");
			

        	return super.preHandle(request, response, handler);
        }else{
        	logger.info("로그인 페이지로 가 주셔야 겠습니다..");
        	response.sendRedirect(request.getContextPath()+"/login.do");
        	return false;
        }
    }
}
