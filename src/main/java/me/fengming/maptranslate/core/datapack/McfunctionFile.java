package me.fengming.maptranslate.core.datapack;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class McfunctionFile {
    private McFunctionData data;
    public McfunctionFile(File f) throws IOException {
        data = new McFunctionData(f);
        if (f.getName().endsWith(".mcfunction")) {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            int lineIndex = 1, colIndex = 1;
            while ((line = br.readLine()) != null) {
                Matcher m = getLineString(line);
                if (m.find()) {
                    for (int i = 0; i < m.groupCount(); i++) {
                        colIndex = m.start();
                        String text = line.substring(colIndex, m.end());
                        if (!text.isEmpty()) {
                            data.addLine(lineIndex, colIndex, text);
                        }
                    }
                }
                lineIndex++;
            }
        }
    }

    public McFunctionData getData() {
        return data;
    }

    public Matcher getLineString(String line) {
        String regexText = "\\{.*\"text\":\".*\".*\\}";
        line = line.replaceAll("\\s*", "");
        Pattern pattern = Pattern.compile(regexText);
        return pattern.matcher(line);
    }
}
