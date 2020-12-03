//    @PostMapping("findBorrowerByUserId")
//    ResultModel findBorrowerByUserId(Integer userId) throws BusinessException;



//简单介绍redis：
//redis是开源的、基于内存的NOSql框架,适用于海量的k-v结构的数据存取，同时redis还支持丰富的数据类型，redis还提供了数据持久化、数据分片、
//主从复制、内存回收的等特性满足我们再应对大数据和高并发场景下的使用，我们在项目中主要redis作为缓存框架来使用。存储一些静态的数据，比如字典、
//城市、token这些数据；


redis支持哪些数据类型
//有: String ："存储简单的k-v结构的数据，并且是二进制安全的，表示String可以存取任何类型的数据，最多存取512M"
//"    List：存取有序的、可以通过下标获取的元素，是基于双向链表的，可以在list两端进行操作，存取2~32-1个元素
//"    set：无序的、不允许重复、不可以通过下标获取元素，可以获取集合中的交集、差集、并集，最多存取2~32-1个元素、
//"    hash:是一组k-v结构的集合，比较适合存取对象，当hash表中数据较少时采用紧凑型数据接收，当数据较多时变为真正的hashmap，
//"        最多存取2~32对键值

redis的优点
//"    性能方面来说的话：redis是基于内存的，所以它的性能优异且高效；redis支持很多的数据类型可以在大多数业务场景下使用，
//"同时还支持rdb和aof两种持久化策略来保证数据的安全性和更高的数据一致性，redis还支持集群、数据分片、主从、内存回收的高级特性，在应对
//"大数据和高并发有着不错的表现
//"性能方面来说，redis是基于内存的，redis支持很多的数据类型可以在大多数业务场景下使用， 同时还支持rdb和aof两种不同的持久化策略来保证数据的安全性和更高的数据一致性" +
//"redis还支持集群、主从、数据分片、内存回收的高级特性，在应对大数据和高并发的业务场景都有着不错的表现"
//性能方面来说Reid是基于内存性能优异且高效，redis还支持很多的数据类型，同时还rdb和aof两种不同的持久化策略来保证数据的安全性和更高的数据一致性。reids有数据分片集群内存回收
//主从等这些高级特性，在应对大数据和高并发的业务场景都有着不错的表现
redis有哪些劣势
//由于redis是基于内存的所以受物理内存限制，虽说可以数据分片但是这样又会增加运维的难度和工作量
redis为什么高效
//因为是基于内存的素以读取速度快
//采用的是单线程，单线程不用考虑锁操作和上下文的切换
//使用的非阻塞IO，采用ePoll多路复用的技术提高了IO的能力
//reids对数据存储进行了优化，加快了读取的速度
//reids采用了自己实现的事件分离器，效率比较高
reids的持久化策略
//有有两种持久化策略
//1、rdb版持久化策略 2、全持久化cel
//redis默认开启rdb，两者也可同时开启。rdb的工作方式就是定时将内存中的全部数据持久化到dramp.rdb文件中，数据恢复时将dramp.rdb文件中的全部数据加载到内存中，
//由于是这个定时持久的方式，所以可能会丢失一个事件间隔内的数据，数据的安全性和一致性不如aof安全。而aof是通过追加的方式记录存储每个非查询的操作命令，在进行数据恢复时进行重放来实现持久化的机制，并且在aof文件过多时还会进行重写来减少命令的个数，
//最多丢失1S或一个命令，数据的安全性和一致性比rdb高，当二者同时开启时，优先使用aof中的数据。
//rdb半持久化策略和aof全持久化策略
//Redis默认开去rdb，也可同时开启rdb和aof。rdb的工作方式就是定时将内存中的全部数据持久化到dromp.rdb文件中，数据恢复时将这个文件中的数据全部加载到内存中，
//由于是定时持久的方式，所以可能丢失一个事件间隔内的数据，数据的安全性和一致性不如aof安全；而aof是通过命令追加的方式记录的，存储每个非查询的操作命令，在进行数据恢复时通过重放实现持久化机制，并且在aof文件过多时还会进行重写
//来减少命令的个数，最多丢失1S或1个命令，数据的安全性和一致性比aof高，当二者同时开启时优先使用aof
redis更新缓存策略
//先操作数据库再操作缓存，否则可能会出现数据不一致的问题；具体的过程：1、将数据写入数据库 2、删除缓存 3、获取缓存时如果缓存中没有就从数据库中获取，并加入到缓存中，这样可以尽可能的保持缓存中的数据和数据库的一致，
//虽然步骤2可能会失败但是概率比较小，也可以加入MQ并通过ACK消息确认机制来保证数据缓存的一致性
//先操作数据库再操作缓存，否则可能会出现数据不一致的问题，具体的过程：1、将修改写入数据库 2、删除缓存 3、获取缓存时如果缓存中没有就从数据库获取，并加入到缓存中，这样可以进尽可能的保证缓存中的数据和数据库的一致，
//虽然步骤2可能会失败但是这个概率比较小，也可以加入MQ并通过ACK消息机制来保证数据缓存的一致性
//先操作数据库再操作缓存，否则可能会出现数据不一致的问题，具体的过程：1、将数据写入数据库 2、删除缓存 3、获取缓存时如果缓存中没有就从数据库获取，并加入到缓存中，这样可以尽可能的保证缓存中的数据和数据库的保持一致
//虽然步骤2可能会失败但是这个概率不叫小，也可以加入MQ并通过ACK消息机制来保证数据库的一致性
为什么是删除缓存，不是更新缓存
//因为删除的操作比更新快，而且也更简单，另外，如果更新了缓存而不使用，也会造成一定的资源浪费
//因为删除比更新的性能号，而且也更简单，另外，如果更新了缓存而不使用，也会造成一定的资源浪费
redis主从不一致的问题
//如果从库还没来得及更新主库的数据就产生了读的操作就出现了主从不一致的问题，通常业务都是允许短时间内的数据不一致问题，但如果强一致的场景下，就可以选择，当从库没有读取到数据，就去主库再读一遍来保证主从数据一致
//如果从库还没来得及更新主库的数据就产生了读的操作就出现了只从不一致的问题，通常业务都是允许短时间内数据不一致的问题，但是如果强一致的场景下，就可以选择，当没有再从库读到数据，就去主库再读一次来保证主从数据一致
如何利用redis实现简单队列
//可以使用List结构作为队列，rpush生产消息，ipop消费消息，并利用主题订阅模式可以实现多个消费者的情况，但是如果没有消费者会存在丢失消息的问题（得使用专门的MQ服务才能解决此问题，因为redis的专业不在队列）
//可以使用List接收作为队列，rpush生产消息，ipop消费消息，并利用主题订阅模式可以实现多个消费者的情况，但是如果没有消费者会存在丢失消息的问题（得使用专门的MQ服务才能解决此问题，因为redis的专业不在队列）
redis集群相关
//据我所知目前集群策略比较流行的方案有主从哨兵模式和数据分片，主从哨兵模式着眼于高可用，在主服务器宕机后自动将从服务器升级为主服务器，继续提供服务，主从+哨兵其实还是单redis的存储，只是保证了redis的高可用，
//数据分片主要解决的就是reids进行大数据存储的问题，在单个reids内存不足时，使用数据cluster进行分片存储
//据我所知目前就集群策略比较流行的方案有主从哨兵模式和数据分片模式，主从哨兵模式模式着眼于高可用，在主服务器宕机后自动将从服务器升级为主服务器，继续提供服务，主从+哨兵其实还是redis的存储，只是保证了reids'的高可用
//数据分片主要解决的就是reids进行大数据存储的问题，单个redis内存不足时，使用数据cluster进行分片存储
//据我所知目前rredis集群方案流行的是主从哨兵模式和数据分片，主从哨兵模式着眼于高可用，在主服务器宕机后从服务器将升级为主服务器继续提供服务，主从哨兵模式其实还是单redis实例，只是保证了redis的高可用
//数据分片主要解决的就是redis大数据量存储的问题，在单redis内存不足时，使用cluster进行分片存储
redis分布式锁的实现
//在早期的redis（2.8版本之前）使用setNx+expire来实现分布式锁，锁的实现主要依赖setNx的互斥性，只有key不相同时才能设置成功，expire主要是为锁设置超时来避免死锁的产生，但是此操作是非原子性的
//所以极端情况下还是会出现死锁的情况（setNx成功， expire失败）。在redis2.8知后可以通过set命令来完成锁的创建，set key value EX timeout NX ,由一个命令的原子操作来完成，避免了死锁的产生
rabbitMQ简介
//rabbitMQ是一款高性能的消息中间件，是AMQP（高级消息队列协议）下的标准实现，rabbitMQ也是典型的生产者消费模式，由消息生产者产生消息传递到rabbitMQ服务，并经过路由器转发到指定的队列中，消息消费者在进行消费消息的
//过程中rabbitMQ还提供了消息确认机制来保证消息的一致性，死信机制来避免消息的阻塞，同时还支持集群部署，我们项目中通常可用于模块解耦，业务异步处理，限流等业务场景
//rabbit MQ是一款高性能的消息中间件，是AMQP（高级消息队列协议）下的标准实现，也是典型的生产消费模式，生产者产生消息传递到rabbitMQ服务，由路由转发到指定的队列中，消费者消费消息的过程中rabbitMQ还提供了消息确认机制
//来保证消息的一致性，死信机制来避免消息阻塞，同时rabbitMQ还支持集群部署，我们在项目剧中通常可用于模块解耦，业务异步处理，限流等业务场景
//rabbitMQ是一款高性能的消息中间件，是AMQP(高级消息队列协议)下的标准实现，rabbitMQ也是典型的生产者消费者模式，由生产者产生消息传递到rabbitMQ服务，并经过路由器🐖发到指定的队列中，消息消费者消费消息的过程中，rabbitMQ
//还提供了消息确认机制来保证数据的一致性，死信机制来避免消息阻塞，同时rabbitMQ还支持部署集群，我们项目中通常用于模块解耦，业务异步处理，限流等业务场景
rabbit核心内容
//虚拟机 主要用来控制权限
//路由/交换机 转发消息
//绑定 用于交换机和队列之间的绑定
//队列 用于存储消息，遵守先进先出的原则
//消息 消息的主体，由header和body两部分组成，body是具体的数据
rabbitMQ 运行原理
//生产者产生消息后发送到rabbitMQ，MQ接收到消息后把消息发送到指定的路由中，路由会根据路由的转发规则和routingkey找到绑定的队列，消费者监听到队列中有消息后进行消费
//生产者产生消息后发送到rabbit MQ服务，MQ收到消息后把消息发送到指定的路由，路由会根据路由的转发规则和routingkey找到绑定的队列，消费者监听到队列中的消息后进行消费
消息队列怎么防止消息阻塞
//由于队列是先进先出原则，当前面的消息消费失败时，MQ会不断重发该消息，而消息一直消费失败，就会导致队列阻塞，其他消息无法被消费的情况。为了避免此情况的产生，我们会在消费者处理消息异常时，重试一次，重试还失败的话，将消息
//转到其他队列，进行重试或者记录消息进行人工干预，来防止消息的堆积，或利用MQ的死信机制进行消息异常的处理，当发生消息处理失败的情况下将该消息加入到死信队列中，不影响后面的消息处理，进入死信队列中的消息进行其他处理（重试，或记录消息进行人工干预处理）

//由于队列是先进先出的原则，当前面的消息消费失败时，MQ会不断重发该消息，而消息一直消费失败，会导致后面的消息无法消费，发生队列阻塞的情况。为了避免此情况的产生，我们会在消费者处理消息异常时，重试一次，重试还是失败的话，将消息
//转到其他队列，进行重试或记录消息进行人工干预，或者加入到死信机制进行异常消息处理。当消息处理失败的情况下将该消息加入到死信队列中，不影响后面的消息处理，进入死信队列的消息进行其他处理（重试，或者记录消息进行人工干预处理）
怎么保证消息的一致性/消息确认机制
//rabbitMQ遵循了AMQP规范，用消息确认机制来保证，只要消息发送，就能确保被消费者消费来做到消息最终一致性，确认发送过程如下：
//1、生产者发送消息到消息服务 2、如果消息落地持久化完成，则返回一个标志（ACK）给生产者，生产者拿到这个确认后，才能说消息发送成功，否则进入异常处理流程。
//3、消息服务将消息发送给消费者 4、消费者接收并处理消息，如果处理成功则手动确认，当消息服务拿到这个确认后才能说终于消费完成了，否则重发，或者进入异常处理
//rabbitMQ遵循你了AMPQ规范，用消息确认机制来保证，只要消息发送，就能确保被消费者消费来做到消息最终一致性，确认发送过程如下：
//1、生产者发送消息到消息服务 2、如果消息落地持久化完成，则返回一个标志（ACK）给生产者，生产者拿到这个确认后才能说消息发送成功，否则进入或异常处理流程
//3、消息服务将信息发送给消费者 4、消费者接收并处理消息，如果处理成功则手动确认，当消息服务拿到这个确认后才能终于说消费完成了，否则重发，或者进入异常处理
MQ消息重复消费怎么解决
//在每个消息中带一个唯一的消息id，消费方收到消息后拿着消息id在redis中判断是否存在，不存在就将消息存入redis中，同时设置超时时间，存在就说明消费过了，直接向MQ返回消费成功
//在每个消息中带一个唯一的消息id，消费方收到消息后拿着消息id在redis中判断是否存在，不存在就将消息存入redis中，同时设置超时时间，存在就说明消费过了，直接向MQ返回消费成功
//在每个消息中带一个唯一的消息id，消费方收到消息后拿着消息id在redis中判断是否存在，不存在就将消息存入redis中，同时设置超时时间，存在就说明消费过了，直接向MQ返回消费成功
@ApiOperation("提现")
@PostMapping("withdrawal")
public ResultModel withdrawal(@RequestHeader("token") String token, String transactionPwd, BigDecimal withdrawalAmount) throws BusinessException {
    log.info("进入提现接口");
    Assert.notNull(withdrawalAmount, "提现金额不能为空");
    Assert.hasText(transactionPwd, "交易密码不能为空");
    UserDetail users = (UserDetail) redisTemplate.opsForValue().get(token);
    if(users.getOpenStatus().equals(DictConstant.BANK_NOT_OPEN_STATUS)){
        return new ResultModel().error(300, "操作失败","还未开户，请先去开户");
    }
    UserDetail userDetail = userDetailService.getUserDetailByUserId(users.getUserId());
    if (!userDetail.getTransactionPwd().equals(transactionPwd)) {
        return new ResultModel().error(100, "业务处理失败", "交易密码输入错误");
    }
    UserWallet userWallet = userWalletService.getUserWallet(users.getId());
    BigDecimal balance = userWallet.getBalance().subtract(withdrawalAmount);
    if (balance.intValue() < 0) {
        return new ResultModel().error(400, "业务处理失败", "余额不足");
    }
    userWallet.setBalance(balance);
    Boolean status = userWalletService.updateWallet(userWallet);
    if (status) {
        log.info("进入提现接口结束");
        return new ResultModel().success("提现成功");
    }
    return new ResultModel().error("充值失败");
}

@ApiOperation("提交开户信息")
@PostMapping("insertOpenCount")
public ResultModel insertOpenCount(@RequestHeader String token, @RequestBody UserDetail userDetail) throws BusinessException {
    log.info("进入提交开户信息");
    if(userDetail.getOpenStatus().equals(DictConstant.BANK_OPEN_STATUS)){
        return new ResultModel().error(300, "操作失败", "请勿重新开户");
    }
    Assert.hasText("银行卡号不能为空", userDetail.getBankCard());
    Assert.hasText("请选择账户类型", userDetail.getAccountType());
    Assert.hasText("银行预留手机号不能为空", userDetail.getBankPhone());
    Assert.hasText("请设置交易密码", userDetail.getTransactionPwd());

    Assert.state(userDetail.getTransactionPwd().equals(userDetail.getConfirmTransactionPwd()), "两次密码不一致");
    userDetailService.insertOpenCount(userDetail, token);
    UserDetail users = (UserDetail) redisTemplate.opsForValue().get(token);
    userWalletService.insertBalance(users.getUserId());
    log.info("进入提交开户信息结束");
    return new ResultModel().success("开户成功");
}


@Override
@Transactional(rollbackFor = Exception.class)
public ResultModel vipCount() {
    Map<String,Object> map = new HashMap<>();
    Integer vipNum = this.baseMapper.getVip();
    Integer projectNum = this.baseMapper.getProject();
    BigDecimal money = this.baseMapper.getSumMoney();
    BigDecimal waitReturnMoney = this.baseMapper.getWaitReturn();
    map.put("vipNum",vipNum);
    map.put("projectNum",projectNum);
    map.put("money",money);
    map.put("waitReturnMoney",waitReturnMoney);
    return new ResultModel().success(map);
}
@PostMapping("findBorrowerByUserId")
ResultModel findBorrowerByUserId(@RequestParam("userId") Integer userId) throws BusinessException;