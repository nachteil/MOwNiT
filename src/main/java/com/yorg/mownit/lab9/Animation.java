package com.yorg.mownit.lab9;

import com.yorg.mownit.commons.Point2D;
import com.yorg.mownit.commons.Range;
import com.yorg.mownit.commons.plot.Plot;
import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class Animation {

    private static int animationCounter = 0;

    private final ProblemParameters parameters;
    private final int animationID;

    double maxY, minY;

    private List<Point2D []> frameArrays;

    public Animation(ProblemParameters parameters) {
        this.parameters = parameters;
        this.animationID = ++animationCounter;
        frameArrays = new ArrayList<>();
    }

    public void addFrame(Point2D [] points) {

        if(frameArrays.size() == 0) {
            maxY = findMaxY(points);
            minY = findMinY(points);
        } else {
            updateMaxAndMinY(points);
        }

        frameArrays.add(points);

    }

    private void updateMaxAndMinY(Point2D [] points) {
        double newMaxCandidate = findMaxY(points);
        double newMinCandidate = findMinY(points);
        maxY = newMaxCandidate > maxY ? newMaxCandidate : maxY;
        minY = newMinCandidate < minY ? newMinCandidate : minY;
    }

    private double findMaxY(Point2D [] points) {
        double result = points[0].getY();
        for(Point2D point : points) {
            result = point.getY() > result ? point.getY() : result;
        }
        return result;
    }

    private double findMinY(Point2D [] points) {
        double result = points[0].getY();
        for(Point2D point : points) {
            result = point.getY() < result ? point.getY() : result;
        }
        return result;
    }

    public void display() {

        BufferedImage [] images = getFrameImages();
        AnimationWindow window = new AnimationWindow(images);
    }

    @SneakyThrows
    private BufferedImage [] getFrameImages() {

        Range plotRange = new Range(0, parameters.getL());

        BufferedImage [] images = new BufferedImage[frameArrays.size()];

        for(int i = 0; i < images.length; ++i) {

            String plotFileName = String.format("animation-%d-frame%d.png", animationID, i);
            double t = parameters.getK() * i;
            Plot plot = Plot.newPlot()
                    .withFunctionPlotRange(plotRange)
                    .withPlotFileName(plotFileName)
                    .withTitle(String.format("t=%2.4f", t))
                    .withXLabel("x")
                    .withXRange(plotRange)
                    .withYLabel("u(x)")
                    .withYRange(minY, maxY)
                    .build();

            plot.addPointsPlot(frameArrays.get(i), "MES result");
            plot.plotWithoutWindow(true);

            images[i] = ImageIO.read(new FileInputStream(plotFileName));
        }
        return images;
    }

}
