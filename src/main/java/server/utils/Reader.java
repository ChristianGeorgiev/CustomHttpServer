package server.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class Reader {
    public static String readAllBytes(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder result = new StringBuilder();


        while (reader.ready()){
            result.append(reader.readLine()).append(System.lineSeparator());
        }
        return result.toString();
    }
}
