local key=KEYS[1]
local limit=tonumber(ARGV[1])
local current=tonumber(redis.call('get',key) or "0");
if(current +1 >limit ) then
   return 0
else
   redis.call("INCRBY",key,"1")
   redis.call("expire",key,"2")
   return  1
end