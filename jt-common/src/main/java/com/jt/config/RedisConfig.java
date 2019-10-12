package com.jt.config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;

@Configuration	//表示一个配置类
@PropertySource("classpath:/properties/redis.properties")
@Lazy			//加载延时
public class RedisConfig {


	@Value("${redis.nodes}")
	private String nodes;
	
	/**
	 * 搭建redis集群
	 */
	@Bean
	public JedisCluster jedisCluster() {
		Set<HostAndPort> nodesSets = getNodes();
		return new JedisCluster(nodesSets);
	}
	
	//set表示不要有重复数据
	private Set<HostAndPort> getNodes() {
		System.out.println(nodes);
		Set<HostAndPort> nodesSets = new HashSet<>();
		String[] strNode = nodes.split(",");
		for (String redisNode : strNode) {
			String host = redisNode.split(":")[0];
			int port = Integer.parseInt(redisNode.split(":")[1]);
			HostAndPort hostAndPort = new HostAndPort(host, port);
			nodesSets.add(hostAndPort);
		}
		return nodesSets;
	}
	
	
	
	
//	/**
//	 * 实现哨兵配置
//	 */
//	@Value("${redis.sentinel.masterName}")
//	private String masterName;
//	@Value("${redis.sentinels}")
//	private String nodes;
//	
//	@Bean  //该对象是单例的
//	public JedisSentinelPool jedisSentinelPool() {
//		Set<String> sentinels = new HashSet<>();
//		sentinels.add(nodes);
//		return new JedisSentinelPool(masterName, sentinels);
//	}
//	
//	//@Qualifier该注解指定bean赋值
//	@Bean
//	@Scope("prototype")	//多例对象
//	public Jedis jedis(
//	@Qualifier("jedisSentinelPool")JedisSentinelPool pool) {
//		Jedis jedis =pool.getResource();
//		return jedis;
//	}
	
	
}


//
//@Value("${redis.nodes}")
//private String nodes;
//

//	@Bean
//	public ShardedJedis	shardedJedis() {
//		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
//		String[] strNodes = nodes.split(","); //[node,node]
//		for (String strNode : strNodes) {
//			String[] node = strNode.split(":");
//			String host = node[0];
//			int port = Integer.parseInt(node[1]);
//			shards.add(new JedisShardInfo(host, port));
//		}
//		return new ShardedJedis(shards);
//	}



/*
 * @Value("${redis.host}") private String host;
 * 
 * @Value("${redis.port}") private Integer port;
 */	
/*
 * 回顾：
 * 	1.xml配置文件 添加bean标签(远古时期)
 * 	2.配置类的形式 @Bean
 * 配置：
 * 	将jedis对象交给spring容器管理
 * 利用properties配置文件为属性动态赋值
 */
/*
 * @Bean public Jedis jedis() { return new Jedis(host, port); }
 */

















