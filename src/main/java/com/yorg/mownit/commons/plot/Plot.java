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

    public void plotWithWindow() {

        callGnuplotToCreatePlot(true);
        new PlotWindow(outFileName);
    }

    public void plotWithoutWindow(boolean disposeOnExit) {

        callGnuplotToCreatePlot(disposeOnExit);
    }

    @SneakyThrows
    private void callGnuplotToCreatePlot(boolean disposeOnExit) {

        String gnuplotFileContent = new GnuplotHelper(this, outFileName).getGnuplotInput(800, 600);

        FileWriter writer = new FileWriter("plot.plt");
        writer.append(gnuplotFileContent);
        writer.flush();
        writer.close();

        Process p = Runtime.getRuntime().exec("gnuplot ".concat("plot.plt"));
        p.waitFor();
        if(disposeOnExit) {
            new File(outFileName).deleteOnExit();
        }
        InputStream processErrorStrem = p.getErrorStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(processErrorStrem));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        processErrorStrem = p.getInputStream();
        reader = new BufferedReader(new InputStreamReader(processErrorStrem));
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
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
    public void addLinesPointsPlot(Point2D [] points, String description) {

        DataSeries pointsSeries = this.newDataSeries(description, PlotType.LINESPOINTS);
        pointsSeries.addData(points);

    }

    public static void plotPoints(Point2D[] points) {

        Range xrange = Range.getRangeX(points);
        Range yrange = Range.getRangeY(points);

        xrange = scaleRange(xrange);
        yrange = scaleRange(yrange);

        Plot plot = newPlot()
                .withFunctionPlotRange(xrange)
                .withPlotFileName("tmpplt.png")
                .withTitle("Points")
                .withXLabel("x")
                .withYLabel("y")
                .withXRange(xrange)
                .withYRange(yrange.start, yrange.end)
                .build();

        plot.addPointsPlot(points, "points");
        plot.plotWithWindow();
    }

    private static Range scaleRange(Range range) {
        double start = range.start;
        double end = range.end;
        double delta = (end-start) * 0.1;
        start -= delta;
        end += delta;
        return new Range(start, end);
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
