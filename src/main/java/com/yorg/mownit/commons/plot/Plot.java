package com.yorg.mownit.commons.plot;

import com.yorg.mownit.commons.Range;
import lombok.SneakyThrows;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class Plot {

    private Range xrange;
    private Range yrange;
    private String xlabel;
    private String ylabel;
    private String title;
    private Type type;

    List<DataSeries> dataSeries;

    protected Plot() {
        dataSeries = new ArrayList<>();
    }

    @SneakyThrows
    public void plot() {

        String gnuplotFileContent = new GnuplotHelper(this, "out.png").getGnuplotInput(800, 600);

        FileWriter writer = new FileWriter("plot.plt");
        writer.append(gnuplotFileContent);
        writer.flush();
        writer.close();

        Process p = Runtime.getRuntime().exec("gnuplot ".concat("plot.plt"));
        System.out.println("Plot status: " + p.waitFor());

        new PlotWindow();
    }

    public Range getXrange() {
        return xrange;
    }

    public Range getYrange() {
        return yrange;
    }

    public String getXlabel() {
        return xlabel;
    }

    public String getYlabel() {
        return ylabel;
    }

    public String getTitle() {
        return title;
    }

    public Type getType() {
        return type;
    }

    public DataSeries newDataSeries(String description) {
        DataSeries newSeries = new DataSeries(description);
        dataSeries.add(newSeries);
        return newSeries;
    }

    public static enum Type {
        LINES("lines"), POINTS("points"), LINESPOINTS("linespoints");

        private final String s;

        Type(String s) {
            this.s = s;
        }

        @Override
        public String toString() {
            return s;
        }
    }

    public static PlotBuilder newPlot() {
        return new PlotBuilder();
    }

    public static class PlotBuilder {

        private Plot plotInstance;

        private PlotBuilder() {
            plotInstance = new Plot();
        }

        public PlotBuilder withXRange(double start, double end) {
            plotInstance.xrange = new Range(start, end);
            return this;
        }

        public PlotBuilder withXRange(Range range) {
            return withXRange(range.start, range.end);
        }

        public PlotBuilder withYRange(double start, double end) {
            plotInstance.yrange = new Range(start, end);
            return this;
        }

        public PlotBuilder withYLabel(String yLabel) {
            plotInstance.ylabel = yLabel;
            return this;
        }

        public PlotBuilder withXLabel(String xLabel) {
            plotInstance.xlabel = xLabel;
            return this;
        }

        public PlotBuilder withTitle(String title) {
            plotInstance.title = title;
            return this;
        }

        public PlotBuilder withType(Type type) {
            plotInstance.type = type;
            return this;
        }

        public Plot build() {
            return plotInstance;
        }

    }
}
