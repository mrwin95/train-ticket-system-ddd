package win.trainticket.ddd.infra.cache.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Optional;

@Component
public class RedisInfraServiceImpl implements RedisInfraService{

    private static final Logger log = LoggerFactory.getLogger(RedisInfraServiceImpl.class);
    @Resource
    private final RedisTemplate<Object, Object> redisTemplate;

    public RedisInfraServiceImpl(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void setString(String key, String value) {

        if(StringUtils.hasLength(key)){
           redisTemplate.opsForValue().set(key, value);
        }
    }

    @Override
    public String getString(String key) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key)).map(String::valueOf).orElse(null);
    }

    @Override
    public <T> T getObject(String key, Class<T> clazz) {

        Object result = redisTemplate.opsForValue().get(key);

        if(result == null){
            return null;
        }

        if(result instanceof String){
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue((String) result, clazz);
            }catch (Exception e){
                log.error("Error deserializing JSON to Object: {}", e.getMessage());
                return null;
            }
        }

        if(result instanceof Map){
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.convertValue(result, clazz);
            }catch (IllegalArgumentException e){
                log.error("Error convert linkedHashMap to object: {}", e.getMessage());
                return null;
            }
        }
        return null;
    }

    @Override
    public void setObject(String key, Object value) {
        try {
            if(StringUtils.hasLength(key)){
                redisTemplate.opsForValue().set(key, value);
            }
        }catch (Exception e){
            log.error("set Objects Error: {} ", e.getMessage());
        }
    }

    @Override
    public void delete(String eventItemKey) {
        redisTemplate.delete(eventItemKey);
    }
}
