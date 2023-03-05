package org.example;

import com.azure.core.credential.TokenCredential;
import com.azure.core.util.BinaryData;
import com.azure.identity.AzureAuthorityHosts;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusMessageBatch;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import org.common.base.SystemParams;

import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {

         System.setProperty("AZURE_CLIENT_ID", SystemParams.ServiceBusAADTestStr1);
         System.setProperty("AZURE_CLIENT_SECRET", SystemParams.ServiceBusAADTestStr2);
         System.setProperty("AZURE_TENANT_ID", SystemParams.ServiceBusAADTestStr3);


        String queueName = "test01";


        TokenCredential credential = new DefaultAzureCredentialBuilder().authorityHost(AzureAuthorityHosts.AZURE_CHINA).build();
        ServiceBusSenderClient sender = new ServiceBusClientBuilder()
                //.connectionString(connectionString)
                .credential("zyb-can-delete.servicebus.chinacloudapi.cn",credential)
                .sender()
                .queueName(queueName)
                .buildClient();

        // Create a message to send.
        final ServiceBusMessageBatch messageBatch = sender.createMessageBatch();
        IntStream.range(0, 10)
                .mapToObj(index -> new ServiceBusMessage(BinaryData.fromString("Hello world! " + index)))
                .forEach(message -> messageBatch.tryAddMessage(message));

        // Send that message. It completes successfully when the event has been delivered to the Service queue or topic.
        // It completes with an error if an exception occurred while sending the message.
        sender.sendMessages(messageBatch);

        // Close the sender.
        sender.close();
    }


}