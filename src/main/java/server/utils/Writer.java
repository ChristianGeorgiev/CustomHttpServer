package server.utils;

import java.io.*;

public final class Writer {
    public static void writeAllBytes(String content, OutputStream outputStream) throws IOException {
        DataOutputStream writer = new DataOutputStream(outputStream);
        writer.write(content.getBytes());
    }
}
