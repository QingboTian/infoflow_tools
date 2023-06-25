package cn.tiaqb.infoflowtools.service.remind;

import cn.tiaqb.infoflowtools.constant.Constant;
import cn.tiaqb.infoflowtools.constant.MessageConstant;
import cn.tiaqb.infoflowtools.entity.Message;
import cn.tiaqb.infoflowtools.service.message.MessageService;
import org.slf4j.MDC;

import java.util.TimerTask;

/**
 * 提醒定时任务
 * @author tianqingbo_dxm
 * @date 2023/6/25 4:31 PM
 * @since 1.0
 */
public class RemindTask extends TimerTask {

    final private Message message;

    final private MessageService messageService;

    final private RemindService remindService;

    public RemindTask(Message message, MessageService messageService, RemindService remindService) {
        this.message = message;
        this.messageService = messageService;
        this.remindService = remindService;
    }

    @Override
    public void run() {
        // set sub thread trace
        MDC.put(Constant.INFO_FLOW_TRACE_ID, message.getTraceId());

        message.setMessage(String.format(MessageConstant.REMIND_HIT, message.getMessage()));
        messageService.send(message);
        remindService.expire(message.getMessageId());

        // remove
        MDC.remove(Constant.INFO_FLOW_TRACE_ID);
    }
}
