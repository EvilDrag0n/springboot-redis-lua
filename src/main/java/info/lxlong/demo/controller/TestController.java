package info.lxlong.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @description: 测试
 * @author: lxlong
 * @create: 2022/07/05 15:22
 */
@RestController
@RequestMapping
public class TestController {

    private final static AtomicInteger atomic = new AtomicInteger(1);

    @Autowired
    private DefaultRedisScript<Boolean> limitRedisScript;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/redis/{name}")
    public String testRedis(@PathVariable String name) {
        int age = atomic.getAndIncrement();
        User user = new User(name, age);
        redisTemplate.opsForValue().set("test-user", user);
        return "ok";
    }

    @GetMapping("/redis/user")
    public String testRedis() {
        Object o = redisTemplate.opsForValue().get("test-user");
        assert o != null;
        return o.toString();
    }

    @GetMapping("/script")
    public Boolean testScript() {
        return redisTemplate.execute(limitRedisScript, Arrays.asList("key-script", String.valueOf(5), String.valueOf(30)));
    }


    static class User {
        private String name;
        private Integer age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public User() {

        }

        public User(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
