local countKey = KEYS[1]
if countKey == nil then
    return true
end
-- 获取传递进来的阈值
local requestCount = KEYS[2]
-- 获取传递进来的过期时间ttl
local requestTtl = KEYS[3]
-- 获取redis参数
local countVal = redis.call('GET', countKey)
-- 如果不是第一次请求
if countVal then
    -- 由于lua脚本接收到参数都会转为String，所以要转成数字类型才能比较
    local numCountVal = tonumber(countVal)
    -- 如果超过指定阈值，则返回true
    if numCountVal >= tonumber(requestCount) then
        return true
    else
        numCountVal = numCountVal + 1
        redis.call('SETEX', countKey, requestTtl, numCountVal)
    end
else
    redis.call('SETEX', countKey, requestTtl, 1)
end
return false