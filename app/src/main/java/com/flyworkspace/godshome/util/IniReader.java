package com.flyworkspace.godshome.util;

import java.io.*;
import java.util.*;

/**
 * Created by jinpengfei on 15/3/14.
 */
public class IniReader {
    protected HashMap<String, Properties> sections = new HashMap<String, Properties>();
    protected HashMap<String, List<String>> allKeys = new HashMap<>();
    private transient String currentSecion;
    private transient Properties current;
    private List<String> keysList = new ArrayList<>();

    public IniReader(String filename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        read(reader);
        reader.close();
    }

    public IniReader(InputStream inputSteam) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputSteam));
        read(reader);
        reader.close();
    }

    protected void read(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            parseLine(line);
        }
    }

    protected void parseLine(String line) {
        line = line.trim();
        if (line.matches("\\[.*\\]")) {
            currentSecion = line.replaceFirst("\\[(.*)\\]", "$1");
            current = new Properties();
            keysList.clear();
            sections.put(currentSecion, current);
            allKeys.put(currentSecion, keysList);
        } else if (line.matches(".*=.*")) {
            if (current != null) {
                int i = line.indexOf('=');
                String name = line.substring(0, i);
                String value = line.substring(i + 1);
                current.setProperty(name, value);
                keysList.add(name);
            }
        }
    }

    public String getValue(String section, String name) {
        Properties p = (Properties) sections.get(section);

        if (p == null) {
            return null;
        }

        String value = p.getProperty(name);
        return value;
    }

    public List<String> getKeyList(String section){
        return allKeys.get(section);
    }

}
