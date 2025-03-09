package win.trainticket.ddd.infra.cache.redis;

public interface RedisInfraService {

    public void setString(String key, String value);
    public String getString(String key);

    public <T> T getObject(String key, Class<T> clazz);
    public void setObject(String key, Object value);
}
