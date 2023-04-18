package com.mega.component.bioflow.flow;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangzhengdong
 * @version 1.0
 * @date 2023/4/4 17:02
 */
public class GraphNodeGenerator {

    public static List<Point> generateHorizontalLayout(int num, int x, int y, int paddingX) {
        List<Point> points = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            int positionX = x + i * paddingX;
            int positionY = y;
            Point position = new Point(positionX, positionY);
            points.add(position);
        }

        return points;
    }

    public static List<Point> generateVerticalLayout(int num, int x, int y, int paddingY) {
        List<Point> points = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            int positionX = x;
            int positionY = y + i * paddingY;
            Point position = new Point(positionX, positionY);
            points.add(position);
        }

        return points;
    }

}
