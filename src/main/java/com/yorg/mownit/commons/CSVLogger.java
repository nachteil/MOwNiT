package com.yorg.mownit.commons;

import lombok.SneakyThrows;

import java.io.FileOutputStream;
import java.io.PrintWriter;

public class CSVLogger {

    private final String fileName;
    private final PrintWriter writer;

    StringBuilder lineBuilder;

    @SneakyThrows
    public CSVLogger(String fileName) {
        this.fileName = fileName;
        this.writer = new PrintWriter(new FileOutputStream(fileName));
        lineBuilder = new StringBuilder();
    }

    public synchronized void logLine(Object ... values) {

        for(Object o : values) {
            lineBuilder.append(o).append(",");
        }

        lineBuilder.deleteCharAt(lineBuilder.length()-1);
        writer.println(lineBuilder.toString());
        lineBuilder.setLength(0);
        writer.flush();
    }

}
