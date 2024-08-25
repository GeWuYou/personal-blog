package com.gewuyou.blog.common.service;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.data.domain.Sort;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 服务
 *
 * @author gewuyou
 * @since 2024-04-22 下午9:54:05
 */
public interface IRedisService {

    /**
     * 设置字符串
     *
     * @param key   键
     * @param value 值
     */
    void set(String key, String value);

    /**
     * 设置字符串
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间
     * @param unit    时间单位
     */
    void set(String key, String value, long timeout, TimeUnit unit);

    /**
     * 设置字符串
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间
     * @apiNote 时间单位：秒
     */
    void set(String key, String value, long timeout);

    /**
     * 获取字符串
     *
     * @param key 键
     * @return 值
     */
    Object get(String key);

    /**
     * 设置对象
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间
     * @param unit    时间单位
     * @apiNote 此处的值为对象使用了Json序列化，如果在对象中具有复杂结构时使用
     */
    void setObject(String key, Object value, long timeout, TimeUnit unit);

    /**
     * 设置对象
     *
     * @param key     键
     * @param value   值
     * @param timeout 过期时间
     * @apiNote 时间单位：秒
     */
    void setObject(String key, Object value, long timeout);

    /**
     * 获取对象
     *
     * @param key   键
     * @param clazz 对象类型
     * @return 对象
     */
    <T> T getObject(String key, Class<T> clazz);

    /**
     * 获取对象
     *
     * @param key           键
     * @param typeReference 对象类型
     * @param <T>           T
     * @return 对象
     */
    <T> T getObject(String key, TypeReference<T> typeReference);

    /**
     * 删除值
     *
     * @param key 键
     * @return 是否成功
     */
    Boolean delete(String key);

    Boolean delayedDelete(String key, long delay, TimeUnit unit);

    /**
     * 批量删除值
     *
     * @param keys 键列表
     * @return 成功删除的个数
     */
    Long delete(String... keys);


    /**
     * 设置过期时间
     *
     * @param key  键
     * @param time 过期时间
     * @return 是否成功
     * @apiNote 单位：秒
     */
    Boolean expire(String key, long time);

    /**
     * 获取剩余过期时间
     *
     * @param key 键
     * @return 剩余过期时间
     * @apiNote 单位：秒
     */
    Long getExpire(String key);

    /**
     * 判断键是否存在
     *
     * @param key 键
     * @return 是否存在
     */
    Boolean hasKey(String key);

    /**
     * 自增指定键的值
     *
     * @param key   键
     * @param delta 增量
     * @return 增量后的结果
     */
    Long incr(String key, long delta);

    /**
     * 自增指定键的值，并设置过期时间
     *
     * @param key  键
     * @param time 过期时间
     * @return 增量后的结果
     * @apiNote 时间单位：秒
     */
    Long incrExpire(String key, long time);

    /**
     * 自减指定键的值
     *
     * @param key   键
     * @param delta 增量
     * @return 增量后的结果
     */
    Long decr(String key, long delta);

    /**
     * 获取哈希表中指定字段的值
     *
     * @param key     键
     * @param hashKey 哈希表字段
     * @return 字段的值
     */
    Object hGet(String key, String hashKey);

    /**
     * 设置哈希表中指定字段的值
     *
     * @param key     键
     * @param hashKey 哈希表字段
     * @param value   字段的值
     * @param time    过期时间
     * @return 是否成功
     */
    Boolean hSet(String key, String hashKey, Object value, long time);

    /**
     * 设置哈希表中指定字段的值
     *
     * @param key     键
     * @param hashKey 哈希表字段
     * @param value   值
     */
    void hSet(String key, String hashKey, Object value);

    Map<String, Object> hGetAll(String key);

    Boolean hSetAll(String key, Map<String, Object> map, long time);

    void hSetAll(String key, Map<String, ?> map);

    void hDel(String key, Object... hashKey);

    Boolean hHasKey(String key, String hashKey);

    /**
     * 增量并获取指定键名哈希表中字段的值
     *
     * @param key     键
     * @param hashKey 哈希表字段
     * @param delta   增量
     * @return 增量后的结果
     */
    Long hIncr(String key, String hashKey, Long delta);

    Long hDecr(String key, String hashKey, Long delta);

    /**
     * 增量并获取指定键名集合的值的分数
     *
     * @param key   集合键
     * @param value 值
     * @param score 分数
     * @return 增量后的结果
     */
    Double zIncr(String key, Object value, Double score);

    Double zDecr(String key, Object value, Double score);

    Map<Object, Double> zReverseRangeWithScore(String key, long start, long end);

    Double zScore(String key, Object value);

    Map<Object, Double> zAllScore(String key);

    Set<Object> sMembers(String key);

    /**
     * 向集合中添加元素
     *
     * @param key    键
     * @param values 值列表
     * @return 成功添加的个数
     */
    Long sAdd(String key, Object... values);

    Long sAddExpire(String key, long time, Object... values);

    /**
     * 判断集合中是否存在指定键值
     *
     * @param key   键
     * @param value 值
     * @return 是否存在
     */
    Boolean sIsMember(String key, Object value);

    Long sSize(String key);

    /**
     * 获取集合的差集
     *
     * @param keys 键列表
     * @return 差集
     */
    Set<Object> sDiff(String... keys);

    /**
     * 获取集合的交集
     *
     * @param keys 键列表
     * @return 交集
     */
    Set<Object> sInter(String... keys);

    Long sRemove(String key, Object... values);

    List<Object> lRange(String key, long start, long end);

    Long lSize(String key);

    Object lIndex(String key, long index);

    Long lPush(String key, Object value);

    Long lPush(String key, Object value, long time);

    Long lPushAll(String key, Object... values);

    Long lPushAll(String key, Long time, Object... values);

    Long lRemove(String key, long count, Object value);

    Boolean bitAdd(String key, int offset, boolean b);

    Boolean bitGet(String key, int offset);

    Long bitCount(String key);

    List<Long> bitField(String key, int limit, int offset);

    byte[] bitGetAll(String key);

    Long hyperAdd(String key, Object... value);

    Long hyperGet(String... key);

    void hyperDel(String key);

    Long geoAdd(String key, Double x, Double y, String name);

    List<Point> geoGetPointList(String key, Object... place);

    Distance geoCalculationDistance(String key, String placeOne, String placeTow);

    GeoResults<RedisGeoCommands.GeoLocation<Object>> geoNearByPlace(String key, String place, Distance distance, long limit, Sort.Direction sort);

    List<String> geoGetHash(String key, String... place);

    Boolean setIfAbsent(String key, Object value, Duration duration);
}
