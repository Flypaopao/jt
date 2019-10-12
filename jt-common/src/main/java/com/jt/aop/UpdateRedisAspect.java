package com.jt.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jt.annotation.Cache_Update;
import com.jt.enums.KEY_ENUM;

import redis.clients.jedis.JedisCluster;

@Component
@Aspect
public class UpdateRedisAspect {
	
	@Autowired(required = false)
	private JedisCluster jedisCluster;
	
	@Around(value = "@annotation(cache_Update)")
	public void update(ProceedingJoinPoint joinPoint,
					   Cache_Update cache_Update) {
		
		
	}

	
	
}



















