package com.yorg.mownit.commons.plot;

import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

public class PlotWindow extends JFrame{

    @SneakyThrows
    PlotWindow(String plotFileName) {

        super("Chart");

        setSize(1000, 900);

        JPanel panel = new JPanel() {

            BufferedImage image = ImageIO.read(new FileInputStream(plotFileName));

            @Override
            public void paintComponent(Graphics g) {
                g.drawImage(image, 0, 0, null);
            }
        };
        panel.setSize(800, 600);

        this.setContentPane(panel);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setVisible(true);
    }

}
