package org.example;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.common.StorageSharedKeyCredential;
import org.common.base.SystemParams;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        StorageSharedKeyCredential credential = new StorageSharedKeyCredential("zybrediscopytest01",
                SystemParams.StorageAccoutKey);
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .endpoint("http://zybrediscopytest01.blob.core.chinacloudapi.cn/").credential(credential).buildClient();
        BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient("upload");


        for (int i = 0; i < 10; i++) {
            String uuid = UUID.randomUUID().toString();
            String response = blobContainerClient.getBlobClient("imagetwo1/000"+i + uuid + ".jpeg")
                    .copyFromUrl("https://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83epZASYWskSmkdyld8bQpsq3OVM3ibuXcx5USB757e3LYNWy75ibgj2lRm5zlAoAqy5GBl8DNibWr6yBw/132");
                    // .copyFromUrl("https://cdn1.i-scmp.com/sites/default/files/styles/1200x800/public/images/methode/2018/10/16/57704f6e-d100-11e8-81a4-d952f5356e85_1280x720_160850.JPG?itok=81OmZFjT");

            System.out.println(uuid + "已生成: " + response);
        }
    }
}