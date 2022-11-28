package com.dw.emp.conf;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.dw.emp.mapper.LogsMapper;
import com.dw.emp.vo.LogsVO;

//@Component를 써줘야 Webconfig에서 인식할수 있음
@Component
public class Interceptor implements HandlerInterceptor {

	@Autowired
	private LogsMapper logsMapper;
	
	private final Logger logger = LoggerFactory.getLogger(Interceptor.class); // 이 클래스를 추적하겠다.
	
	
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		//이제 System.out.println은 그만~
		//logger를 이용해서 기록을 남기자!
		//logger를 이용하면 원하는 레벨에 맞게 출력이 가능하다.
		//레벨? (디버깅 모드, 경고 모드, 출력 모드)
		
//		logger.debug("디버그 전용 메세지");
//		logger.warn("경고 메세지");
		logger.info("=============================================preHandle=================");
		String requestUrl = request.getRequestURI(); //접속 url 호출
		String HttpMethod = request.getMethod(); // HTTP 메소드 호출
		String userIP = request.getHeader("X-Forwarded-For");
		if(userIP == null) userIP = request.getRemoteAddr();
		
		logger.info("요청 URL : "+requestUrl);
		logger.info("요청 HTTP METHOD :"+HttpMethod);
		logger.info("사용자 IP : "+userIP);
		logger.info("=============================================preHandle=================");
		
		
		LogsVO logVO = new LogsVO();
		logVO.setHttpMethod(HttpMethod);
		logVO.setIp(userIP);
		logVO.setUrl(requestUrl);
		
		logsMapper.insertLogs(logVO); //접속 로그 insert!
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
