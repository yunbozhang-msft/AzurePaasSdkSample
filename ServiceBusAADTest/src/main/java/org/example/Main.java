package org.example;

import com.azure.core.credential.TokenCredential;
import com.azure.core.util.BinaryData;
import com.azure.identity.AzureAuthorityHosts;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderAsyncClient;
import org.common.base.SystemParams;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {


        System.setProperty("AZURE_CLIENT_ID", SystemParams.ServiceBusAADTestStr1);
        System.setProperty("AZURE_CLIENT_SECRET", SystemParams.ServiceBusAADTestStr2);
        System.setProperty("AZURE_TENANT_ID", SystemParams.ServiceBusAADTestStr3);



        TokenCredential credential =
                new DefaultAzureCredentialBuilder().authorityHost(AzureAuthorityHosts.AZURE_CHINA)
                .build();


        ServiceBusSenderAsyncClient sender = new ServiceBusClientBuilder()
                .credential("test01-servicebus.servicebus.chinacloudapi.cn", credential)
                .sender()
                .queueName("test01")
                .buildAsyncClient();

        ServiceBusMessage message = new ServiceBusMessage(BinaryData.fromString("Microsoft HQ is at Redmond."));
        sender.sendMessage(message)
                .subscribe(
                        unused -> System.out.println("Sent."),
                        error -> System.err.println("Error occurred while publishing message: " + error),
                        () -> System.out.println("Message was sent with id."));

        // .subscribe is not a blocking call. We sleep here so the program does not end before the send is complete.
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Close the sender.
        sender.close();



    }
}