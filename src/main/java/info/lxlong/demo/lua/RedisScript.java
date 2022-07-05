package info.lxlong.demo.lua;

import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.stereotype.Component;

/**
 * @description: redis-lua-script
 * @author: lxlong
 * @create: 2022/07/05 15:17
 */
@Component
public class RedisScript {

    /**
     * 加载lua脚本
     */
    @Bean
    public DefaultRedisScript<Boolean> limitRedisScript() {
        DefaultRedisScript<Boolean> redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(Boolean.class);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/limit.lua")));
        return redisScript;
    }
}
