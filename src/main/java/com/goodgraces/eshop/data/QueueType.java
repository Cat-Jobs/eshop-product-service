package com.goodgraces.eshop.data;

import com.goodgraces.eshop.rabbitmq.RabbitQueue;

/*刷数据的问题

业务系统，特别是在快速迭代过程中，可能会因为一些代码的bug，或者要上线一些新功能，导致需要对全量的数据刷一遍数据

本来有个字段，status，0，1，2这样的值，但是现在要将所有数据，status这个字段的值刷为OPEN，CLOSED，SEND之类的状态

刷数据，一般是在晚上凌晨的时候执行的，依赖服务会大量的更新数据，大量的刷数据的请求会到我们的消息队列中

此时我们的系统压力会非常的大

甚至可能会影响夜间一些正常用户的购买行为，等等

所以一般对这个问题是这样的，我们会针对刷数据的问题，单独开出来队列，专门处理刷数据的请求，对这些队列的消费，通常来说，只会在凌晨0点之后才开始执行

这样的话呢，好处在于，正常的消息不会跟刷数据的消息混杂在一个队列中，可以拆分到不同的队列中

高优先级问题

大家可以这么想，有些数据，比如说，一些特别紧急的活动对应的数据，需要尽快的反应到页面中，那么此时，一个道理，如果这种高优先级的数据

跟其他的消息混杂的一个队列中，势必需要去等待队列中其他的普通消息先处理完了，才能轮到自己

所以一般来说，也会单开高优先级的队列，然后如果业务系统有高优先级的消息，直接写到高优先的队列中，这样的话呢，后续流程全部单独处理*/

/**
 * @description:封装不同的数据操作类型,返回不同的队列
 * @author:阮志龙
 * @date:2020年3月29日
 * @param:
 */
public class QueueType {
	public final static String REFRESH_OPTIONTYPE = "";

	public static String getQueue(String optionType) {
		String queue = RabbitQueue.DATA_CHANGE_QUEUE;
		if ("refresh".equals(optionType)) {
			queue = RabbitQueue.REFRESH_DATA_CHANGE_QUEUE;
		} else if ("high".equals(optionType)) {
			queue = RabbitQueue.HIGH_PRIORITY_DATA_CHANGE_QUEUE;
		}
		return queue;
	}
}
