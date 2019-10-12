package com.jt.inter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.druid.util.StringUtils;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;
import com.jt.util.UserThreadLocal;

import redis.clients.jedis.JedisCluster;
/**
 * 
 * @author 000
 *Spring4版本HandlerInterceptor必须重写3个方法
 *Spring5版本HandlerInterceptor都有默认值
 */
@Component
public class UserInterceptor implements HandlerInterceptor{

	@Autowired
	private JedisCluster jedisCluster;
	
	/**
	 * boolean:
	 * 		true:	表示放行
	 * 		falst: 	表示拦截  一般配合重定向使用(指条明路 不然会报错)
	 * 
	 * 业务实现步骤:
	 * 	1.获取用户Cookie中的token信息
	 * 	2.校验数据是否有效
	 * 	3.校验redis中是否有数据
	 * 	如果上诉操作准确无误,返回true
	 * 	否则return false  重定向到登录页面
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//1.获取cookie数据
		Cookie[] cookies = request.getCookies();
		String token = null;
		if (cookies.length>0) {
			for (Cookie cookie : cookies) {
				if ("JT_TICKET".equals(cookie.getName())) {
					//获取指定数据的值
					token = cookie.getValue();
					break;
				}
			}
		}
		
		//2.校验token的有效性
		if (!StringUtils.isEmpty(token)) {
			String userJSON = jedisCluster.get(token);
			if(!StringUtils.isEmpty(userJSON)) {
				User user = ObjectMapperUtil.toObject(userJSON, User.class);
				request.setAttribute("JT_USER", user);
				//利用threadLocal实现数据共享
				UserThreadLocal.set(user);
				return true;
			}
		}
		
		//必须重定向
		response.sendRedirect("/user/login.html");
		return false;
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		UserThreadLocal.remove();
	}
	
}





















