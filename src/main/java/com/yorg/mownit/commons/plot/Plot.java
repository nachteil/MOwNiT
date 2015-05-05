package com.yorg.mownit.commons.plot;

import com.yorg.mownit.commons.Function;
import com.yorg.mownit.commons.Point2D;
import com.yorg.mownit.commons.RandomFileName;
import com.yorg.mownit.commons.Range;
import com.yorg.mownit.commons.datasources.PointSource;
import com.yorg.mownit.commons.datasources.RegularSource;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Plot {

    private static final int NUM_POINTS_PER_FUNCTION_PLOT = 500;

    @Getter private Range xrange;
    @Getter private Range functionPlotRange = null;
    @Getter private Range yrange;
    @Getter private String xlabel;
    @Getter private String ylabel;
    @Getter private String title;
    @Getter private String outFileName = null;

    @Getter private boolean logscalex;
    @Getter private boolean logscaley;

    List<DataSeries> dataSeries;

    protected Plot(boolean logscalex, boolean logscaley) {
        this.logscalex = logscalex;
        this.logscaley = logscaley;
        dataSeries = new ArrayList<>();
    }

    protected Plot() {
        this(false, false);
    }

    @SneakyThrows
    public void plot() {

        String gnuplotFileContent = new GnuplotHelper(this, outFileName).getGnuplotInput(1000, 800);

        FileWriter writer = new FileWriter("plot.plt");
        writer.append(gnuplotFileContent);
        writer.flush();
        writer.close();

        Process p = Runtime.getRuntime().exec("gnuplot ".concat("plot.plt"));
        System.out.println("Plot status: " + p.waitFor());
        InputStream stream = p.getErrorStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        stream = p.getInputStream();
        reader = new BufferedReader(new InputStreamReader(stream));
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        new PlotWindow(outFileName);
    }

    public DataSeries newDataSeries(String description, PlotType type) {
        DataSeries newSeries = new DataSeries(description, type);
        dataSeries.add(newSeries);
        return newSeries;
    }

    public void addFunctionPlot(Function function, String description) {

        DataSeries functionSeries = this.newDataSeries(description, PlotType.LINES);
        PointSource source = new RegularSource(functionPlotRange);
        Point2D [] plotPoints = source.getFunctionValues(function, NUM_POINTS_PER_FUNCTION_PLOT);

        functionSeries.addData(plotPoints);
    }

    public void addPointsPlot(Point2D [] points, String description) {

        DataSeries pointsSeries = this.newDataSeries(description, PlotType.POINTS);
        pointsSeries.addData(points);

    }

    public static PlotBuilder newPlot() {
        return new PlotBuilder(false, false);
    }
    public static PlotBuilder newPlot(boolean logscalex, boolean logscaley) {
        return new PlotBuilder(logscalex, logscaley);
    }

    public static class PlotBuilder {

        private Plot plotInstance;

        private PlotBuilder(boolean logscalex, boolean logscaley) {
            plotInstance = new Plot(logscalex, logscaley);
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

        public PlotBuilder withPlotFileName(String name) {
            plotInstance.outFileName = name;
            return this;
        }

        public PlotBuilder withFunctionPlotRange(Range range) {
            plotInstance.functionPlotRange = range;
            return this;
        }

        public Plot build() {
            if(plotInstance.outFileName == null ) {
                plotInstance.outFileName = RandomFileName.getRandomFileName("plot", "png");
            }
            if(plotInstance.functionPlotRange == null) {
                plotInstance.functionPlotRange = plotInstance.xrange;
            }
            return plotInstance;
        }

    }
}
