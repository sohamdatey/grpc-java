package org.example;

import java.nio.charset.StandardCharsets;

public class DeleteLater {
    public static void main(String[] args) {
        byte[] bytes = "test".getBytes(StandardCharsets.UTF_8);
        System.out.println(bytes.length*1000000);
    }
}
