package id.sch.kafila.catalog.service;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class CustomObjectMapper extends ObjectMapper {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public  <T> T readValue(InputStream src, JavaType valueType)   throws IOException, JsonParseException, JsonMappingException{
        InputStream src2 = new InputStream() {
            @Override
            public int read() throws IOException {
                return src.read();
            }
        };
        System.out.println("Override Read Value");
        StringBuilder textBuilder = new StringBuilder();
        try (Reader reader = new BufferedReader(new InputStreamReader
                (src2, Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;
            while ((c = reader.read()) != -1) {
                textBuilder.append((char) c);
            }
        }
        System.out.println("textBuilder: "+textBuilder.toString());

        return super.readValue(src,valueType);
    }
}
