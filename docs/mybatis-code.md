### mybatis使用与总结

    有问题或者宝贵意见联系我的QQ,非常希望你的加入！
    
> mybatis 使用
#### resultType 和 resultMap
    
      MyBatis的每一个查询映射的返回类型都是ResultMap，
      只是当我们提供的返回类型属性是resultType的时候，MyBatis对自动的给我们把对应的值赋给resultType所指定对象的属性，
      而当我们提供的返回类型是resultMap的时候，将数据库中列数据复制到对象的相应属性上，可以用于复制查询，两者不能同时用。