package org.example;

import com.azure.core.amqp.AmqpRetryMode;
import com.azure.core.amqp.AmqpRetryOptions;
import com.azure.messaging.eventhubs.*;
import com.azure.messaging.eventhubs.checkpointstore.blob.BlobCheckpointStore;
import com.azure.messaging.eventhubs.models.*;
import com.azure.storage.blob.*;

import java.time.Duration;
import java.util.function.Consumer;

public class EventhubConsumer {
    private static final String connectionString = "Endpoint=sb://zyb-test-will-delete.servicebus.chinacloudapi.cn/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=qmi67bxQiyMUqJsS6KtXmkET80ynPLLOS45Oth8IzhM=";
    private static final String eventHubName = "test00";
    private static final String storageConnectionString = "DefaultEndpointsProtocol=https;AccountName=zybrediscopytest01;AccountKey=CfwpyOfeHmxKApXf0WVbWU2NU3Ud/mBxrguEMvZe1cZrtxTQ1qzHuCahCJeYdVnqFdPV4AlG15Vc+AStX4FQsw==;EndpointSuffix=core.chinacloudapi.cn";
    private static final String storageContainerName = "checkpoint00";

    public void consumer() throws Exception {
        // Create a blob container client that you use later to build an event processor client to receive and process events
        BlobContainerAsyncClient blobContainerAsyncClient = new BlobContainerClientBuilder()
                .connectionString(storageConnectionString)
                .containerName(storageContainerName)
                .buildAsyncClient();

        // Create a builder object that you will use later to build an event processor client to receive and process events and errors.
        EventProcessorClientBuilder eventProcessorClientBuilder = new EventProcessorClientBuilder()
                .connectionString(connectionString, eventHubName)
                .retry(new AmqpRetryOptions()
                        .setMaxRetries(3).setMode(AmqpRetryMode.FIXED).setDelay(Duration.ofSeconds(120)))
                .consumerGroup(EventHubClientBuilder.DEFAULT_CONSUMER_GROUP_NAME)
                .processEvent(PARTITION_PROCESSOR)
                .processError(ERROR_HANDLER)
                .checkpointStore(new BlobCheckpointStore(blobContainerAsyncClient));

        // Use the builder object to create an event processor client
        EventProcessorClient eventProcessorClient = eventProcessorClientBuilder.buildEventProcessorClient();

        System.out.println("Starting event processor");
        eventProcessorClient.start();

        System.out.println("Press enter to stop.");
        System.in.read();

        // System.out.println("Stopping event processor");
        // eventProcessorClient.stop();
        //System.out.println("Event processor stopped.");

        // System.out.println("Exiting process");
    }

    public static final Consumer<EventContext> PARTITION_PROCESSOR = eventContext -> {
        PartitionContext partitionContext = eventContext.getPartitionContext();
        EventData eventData = eventContext.getEventData();

        System.out.printf("Processing event from partition %s with sequence number %d with body: %s%n",
                partitionContext.getPartitionId(), eventData.getSequenceNumber(), eventData.getBodyAsString());

        // Every 10 events received, it will update the checkpoint stored in Azure Blob Storage.
        if (eventData.getSequenceNumber() % 10 == 0) {
            eventContext.updateCheckpoint();
        }

    };

    public static final Consumer<ErrorContext> ERROR_HANDLER = errorContext -> {
        System.out.printf("Error occurred in partition processor for partition %s, %s.%n",
                errorContext.getPartitionContext().getPartitionId(),
                errorContext.getThrowable());
    };

}
