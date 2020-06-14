package util;

import lombok.Getter;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class File {
    @Getter
    private final String file;

    public File(String path) throws IOException {
        var fileReader = new FileReader(path);
        var reader = new BufferedReader(fileReader);

        StringBuilder sb = new StringBuilder();

        while(reader.ready()) {
            sb.append(reader.readLine());
            sb.append("\n");
        }
        reader.close();
        fileReader.close();

        // Deletes the last "new line"
        sb.deleteCharAt(sb.length() - 1);


        file = sb.toString();
    }

    public JSONObject getFileAsJSONObject() {
        return new JSONObject(file);
    }

    public String[] getFileAsStringArray() {
        return file.split("\n");
    }

    public String getFileAsString() {
        return file;
    }
}
