package org.example;

import com.azure.messaging.eventhubs.EventData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    String bigStr = "";

    // This method can only to use once, if you want change size to another size, you need to restart program.
    public List<EventData> getList(int size) {
        List<EventData> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add(new EventData("2"));
        }
        return list;
    }


    public List<EventData> getList2(int size) {
        List<EventData> list = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            bigStr += "wwwwwwwwwwww00000000000000000000000";
        }
        for (int i = 0; i < size; i++) {
            list.add(new EventData(bigStr));
        }

        return list;
    }

    public List<EventData> get1MStr() {
        List<EventData> list = new ArrayList<>();
        try {
            list.add(new EventData(new String(Files.readAllBytes(Paths.get("/Users/yunbozhang/Downloads/1M.txt")))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return list;

    }



}
