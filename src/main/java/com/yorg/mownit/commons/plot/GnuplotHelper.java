package com.yorg.mownit.commons.plot;

import com.yorg.mownit.commons.Point2D;
import lombok.SneakyThrows;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

public class GnuplotHelper {

    private String dataSegment;
    private String resolutionSegment;
    private String headerSegment;

    private String outputFileName;

    private static String chars = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";

    private final Plot plot;

    public GnuplotHelper(Plot plot, String out) {
        this.plot = plot;
        this.outputFileName = out;
    }

    public String getGnuplotInput(int xResolution, int yResolution) {

        headerSegment = getHeaderSection();
        dataSegment = getDataSection();
        resolutionSegment = getResolutionSegment(xResolution, yResolution);

        StringBuilder builder = new StringBuilder(headerSegment);
        builder
                .append("\n\n")
                .append(resolutionSegment)
                .append("\n\n")
                .append(dataSegment)
                .append("\n");

        return builder.toString();
    }

    private String getHeaderSection() {

        StringBuilder headerBuilder = new StringBuilder();

        headerBuilder
                .append("set title \"")
                .append(plot.getTitle())
                .append("\" font \",20\"")
                .append("\n");

        headerBuilder.append("set datafile separator \",\"\n");

        headerBuilder.append("set grid\n");
        headerBuilder
                .append("set yrange [")
                .append(plot.getYrange().start)
                .append(":")
                .append(plot.getYrange().end)
                .append("]\n");
        headerBuilder
                .append("set xrange [")
                .append(plot.getXrange().start)
                .append(":")
                .append(plot.getXrange().end)
                .append("]\n");

        headerBuilder
                .append("set xlabel \"")
                .append(plot.getXlabel())
                .append("\"\n");

        headerBuilder
                .append("set ylabel \"")
                .append(plot.getYlabel())
                .append("\"\n");

        headerBuilder.append("set output \"")
                .append(outputFileName)
                .append("\"\n");

        return headerBuilder.toString();
    }

    private String getResolutionSegment(int x, int y) {
        return String.format("set term png enhanced size %d,%d\n", x, y);
    }

    private String getDataSection() {

        List<DataSeries> series = plot.dataSeries;
        StringBuilder dataBuilder = new StringBuilder("plot ");

        for(int i = 0; i < series.size(); ++i) {

            StringBuilder entryBuilder = new StringBuilder();
            String randomPrefix = RandomStringUtils.random(15, chars);
            DataSeries data = series.get(i);
            String fname = createDataFile(randomPrefix, data);

            entryBuilder.append("\"")
                    .append(fname)
                    .append("\" ")
                    .append("u 1:2 ")
                    .append("with ")
                    .append("lines")
//                    .append(plot.getType().toString())
                    .append(" title \"")
                    .append(data.getDescription())
                    .append("\", \\");

            String entryLine = entryBuilder.toString();
            dataBuilder.append(entryBuilder).append("\n");
        }

        // remove newline, slash, space and comma
        dataBuilder.setLength(dataBuilder.length() - 4);

        return dataBuilder.append("\n").toString();
    }

    @SneakyThrows
    private String createDataFile(String prefix, DataSeries series) {

        String fname = "data-file-".concat(prefix).concat(".csv");
        PrintWriter writer = new PrintWriter(new FileOutputStream(fname));

        for(Point2D p : series.getPoints()) {
            writer.println(p.getX() + "," + p.getY());
        }
        writer.flush();

        return fname;
    }

}
