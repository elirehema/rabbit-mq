package com.rabbit.mq.tut6;

import com.rabbit.mq.tut6.message.CustomMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

public class Tut6Receiver {
    public static final Logger log = LoggerFactory.getLogger(Tut6Receiver.class);
    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receive1(CustomMessage in) throws InterruptedException {
        receive(in, 1);
    }

    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
    public void receive2(CustomMessage in) throws InterruptedException {
        receive(in, 2);
    }

    public void receive(CustomMessage in, int receiver) throws
            InterruptedException {
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println("instance " + receiver + " [x] Received '"
                + in.text() + "'");
        doWork(in);
        watch.stop();
        System.out.println("instance " + receiver + " [x] Done in "
                + watch.getTotalTimeSeconds() + "s");
    }

    private void doWork(CustomMessage in) throws InterruptedException {
        for (char ch : in.text().toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }
}
