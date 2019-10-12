package com.jt.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.druid.util.StringUtils;
import com.jt.annotation.Cache_Find;
import com.jt.enums.KEY_ENUM;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;

@Component	//将对象交给sping容器管理
@Aspect		//标识这是一个切面	切面=切入点表达式+通知
public class RedisAspect {
	
	//表示spring加载时不会立即注入对象
	@Autowired(required = false)
	private JedisCluster jedisCluster;
	
	/**
	 * 规定：
	 * 	1.环绕通知必须使用ProceedingJoinPoint
	 * 	2.如果通知中有参数joinPoint.必须位于第一位
	 * 
	 * @param joinPoint
	 * @return
	 */
	@SuppressWarnings("unchecked")
	//问题1：如何获取直接中的属性
	//该切入点表达式 规定只能获取注解类型 用法名称必须匹配
	@Around(value = "@annotation(cache_Find)")
	public Object around(ProceedingJoinPoint joinPoint,
						 Cache_Find cache_Find) {
		//1.动态获取key
		String key = getKey(joinPoint,cache_Find);
		
		//2.从redis中获取数据
		String resultJSON = jedisCluster.get(key);
		Object resultData = null;
		//3.判断数据是否有值
		try {
			if (StringUtils.isEmpty(resultJSON)) {
				//3.1表示缓存中没有数据，则查询数据库(调用数据库)
				resultData = joinPoint.proceed();
				//3.2将数据保存到缓存中
				resultJSON = ObjectMapperUtil.toJSON(resultData);
				if (cache_Find.secondes()==0) {
					jedisCluster.set(key, resultJSON);
				} else {
					jedisCluster.setex(key, cache_Find.secondes(), resultJSON);
				}
				System.out.println("查询数据库");
			} else {
				Class returnType = getType(joinPoint);
				resultData = ObjectMapperUtil.toObject(resultJSON, returnType);
				System.out.println("查询缓存");
			}
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return resultData;
	}

	private Class getType(ProceedingJoinPoint joinPoint) {
		MethodSignature methodType = (MethodSignature) joinPoint.getSignature();
		return methodType.getReturnType();
	}

	/**
	 * key的定义规则如下:
	 * 	1.如果用户使用AUTO.则自动生成KEY 方法名_第一个参数
	 * 	2.如果用户使用EMPTY,使用用户自己的key
	 * @param joinPoint
	 * @param cache_Find
	 * @return
	 */
	private String getKey(ProceedingJoinPoint joinPoint, Cache_Find cache_Find) {
		//1.判断用户选择类型
		if (KEY_ENUM.EMPTY.equals(cache_Find.keyType())) {
			return cache_Find.key();
		}
		//2.表示用户动态生成key
		String methodName = joinPoint.getSignature().getName();	//得到方法名(getSignature封装目标方法api)
		String arg0 = String.valueOf(joinPoint.getArgs()[0]);
		
		return methodName+"::"+arg0;
	}
	
}





















