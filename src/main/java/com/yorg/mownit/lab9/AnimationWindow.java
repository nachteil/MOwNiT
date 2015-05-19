package com.yorg.mownit.lab9;

import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class AnimationWindow extends JFrame {

    private static final int FPS = 30;

    private final BufferedImage [] frames;
    private int frameCounter = 0;
    private JPanel panel;

    private AnimationFPSAdjuster adjuster = new AnimationFPSAdjuster(FPS);

    @SneakyThrows
    public AnimationWindow(BufferedImage [] images) {

        super("Chart");

        frames = images;
        setSize(1000, 900);

        panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                g.drawImage(frames[frameCounter], 0, 0, null);
                adjuster.notifyUpdate();
            }
        };
        panel.setSize(800, 600);

        this.setContentPane(panel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        startRefreshingLoop();

        this.setVisible(true);
    }

    private void startRefreshingLoop() {
        SwingUtilities.invokeLater(getLoopRefreshingRunnable());
    }

    private Runnable getLoopRefreshingRunnable() {
        return () -> {
            adjuster.pauseIfNeeded();
            ++frameCounter;
            frameCounter %= frames.length;
            panel.repaint();
            SwingUtilities.invokeLater(getLoopRefreshingRunnable());
        };
    }
}
