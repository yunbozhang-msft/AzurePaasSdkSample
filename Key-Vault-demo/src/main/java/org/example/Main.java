package org.example;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.AzureAuthorityHosts;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import org.common.base.SystemParams;

public class Main {
    public static void main(String[] args) {


        System.setProperty("AZURE_CLIENT_ID", SystemParams.KeyVaultDemoStr1);
        System.setProperty("AZURE_CLIENT_SECRET", SystemParams.KeyVaultDemoStr2);
        System.setProperty("AZURE_TENANT_ID", SystemParams.KeyVaultDemoStr3);

        String keyVaultName = "zyb-key-test";
        String keyVaultUri = "https://" + keyVaultName + ".vault.azure.cn";

        TokenCredential credential = new DefaultAzureCredentialBuilder().authorityHost(AzureAuthorityHosts.AZURE_CHINA).build();

        SecretClient secretClient = new SecretClientBuilder()
                .vaultUrl(keyVaultUri)
                .credential(credential)
                .buildClient();


        String secretName = "mySecret111";
        System.out.println("Please provide the value of your secret > ");
        String secretValue = "secretValue1111";
        System.out.print("Creating a secret in " + keyVaultName + " called '" + secretName + "' with value '" + secretValue + "' ... ");

        secretClient.setSecret(new KeyVaultSecret(secretName, secretValue));

        System.out.println("done.");
        System.out.println("Forgetting your secret.");

        secretValue = "yweyuweyuyuwe1111";
        System.out.println("Your secret's value is '" + secretValue + "'.");

        System.out.println("Retrieving your secret from " + keyVaultName + ".");

        KeyVaultSecret retrievedSecret = secretClient.getSecret(secretName);

        System.out.println("Your secret's value is '" + retrievedSecret.getValue() + "'.");
        System.out.print("Deleting your secret from " + keyVaultName + " ... ");

        // SyncPoller<DeletedSecret, Void> deletionPoller = secretClient.beginDeleteSecret(secretName);
        // deletionPoller.waitForCompletion();

        System.out.println("done.");
    }
}