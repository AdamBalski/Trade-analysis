package com.trade_analysis.util;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import util.FileManager;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.fail;

public class FileManagerTest {
    public final String expectedAsString = "{\n" +
            "  \"fruit\": \"Apple\",\n" +
            "  \"size\": \"Large\",\n" +
            "  \"color\": \"Red\"\n" +
            "}";

    public final String[] expectedAsStringArray = new String[]{
            "{",
            "  \"fruit\": \"Apple\",",
            "  \"size\": \"Large\",",
            "  \"color\": \"Red\"",
            "}"
    };

    public final JSONObject expectedAsJSONObject = new JSONObject(expectedAsString);

    @Test
    void testGetFileAsJSONObject() {
        JSONObject actual = null;
        try {
            actual = new FileManager("src/test/resources/sample file").getFileAsJSONObject();
        } catch (IOException e) {
            fail("IOException on getting sample file on testGetFileAsJSONObject()");
        }

        Assertions.assertTrue(expectedAsJSONObject.similar(actual));
    }

    @Test
    void getFileAsStringArray() {
        String[] actual = new String[0];
        try {
            actual = new FileManager("src/test/resources/sample file").getFileAsStringArray();
        } catch (IOException e) {
            fail("IOException on getting sample file on testGetFileAsJSONObject()");
        }

        Assertions.assertArrayEquals(expectedAsStringArray, actual);
    }

    @Test
    void testGetFileAsString() {
        String actual = null;
        try {
            actual = new FileManager("src/test/resources/sample file").getFileAsString();
        } catch (IOException e) {
            fail("IOException on getting sample file on testGetFileAsJSONObject()");
        }

        Assertions.assertEquals(expectedAsString, actual);
    }
}
