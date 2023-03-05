package org.example;

import com.azure.messaging.eventhubs.EventData;
import com.azure.messaging.eventhubs.EventDataBatch;
import com.azure.messaging.eventhubs.EventHubClientBuilder;
import com.azure.messaging.eventhubs.EventHubProducerClient;
import com.azure.messaging.eventhubs.models.SendOptions;
import org.common.base.SystemParams;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EventhubProducer {
    public void sendMessage() {

        String connectionString = SystemParams.EventhubDemoStr1;

        String eventHubName = "test00";


        EventHubProducerClient producer = new EventHubClientBuilder().connectionString(connectionString, eventHubName).buildProducerClient();

        // List<EventData> allEvents = Arrays.asList(new EventData("Foo"), new EventData("Bar"));
        EventDataBatch eventDataBatch = producer.createBatch();

        // To test message increasing... 1，2，3，4...
        int iii=0;
        for (; ; ) {
            eventDataBatch.tryAdd(new EventData(iii+++""));
            //producer.send(eventDataBatch, new SendOptions().setPartitionId("0"));
            System.out.println(eventDataBatch.getCount());
            try {
                TimeUnit.MILLISECONDS.sleep(5000);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

/** 
        List<EventData> allEvents = new Utils().getList(1);
        // List<EventData> allEvents = new Utils().get1MStr();

        System.out.println(allEvents.size());

        for (EventData ed : allEvents) {
            eventDataBatch.tryAdd(ed);
        }


        List<Integer> aList = IntStream.rangeClosed(1, 1).boxed().collect(Collectors.toList());

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            int finalI = i;
            aList.parallelStream().forEach(e -> {
                        // producer.send(eventDataBatch);
                        System.out.println(finalI + "---");
                    }
            );
            try {
                TimeUnit.MILLISECONDS.sleep(1000);

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
*/
    }


}
