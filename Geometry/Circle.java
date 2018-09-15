import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.*;
import java.util.*;

public class NimGame {

    public static double eps = 1e-9;
    public static int seg = 8;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new FileInputStream(new File("input.txt")));
        //Scanner sc = new Scanner(System.in);
        Circle c1 = new Circle(new Point(sc.nextDouble(), sc.nextDouble()), sc.nextDouble());
        Circle c2 = new Circle(new Point(sc.nextDouble(), sc.nextDouble()), sc.nextDouble());
        Point[] result = crossTwoCircles(c1, c2);
        if (result == null) {
            System.out.println("3");
            return;
        }
        System.out.println(result.length);
        for (int i = 0; i < result.length; ++i)
            System.out.println(result[i].x + " "  + result[i].y);
        sc.close();
    }

    public static Point[] crossTwoCircles(Circle c1, Circle c2) {
        if (c1.center.equals(c2.center)) {
            return (c1.r == c2.r) ? null : new Point[0];
        }
        Vector2d shift = new Vector2d(c1.center.x, c1.center.y);
        c2 = new Circle(new Point(c2.center.x - c1.center.x, c2.center.y - c1.center.y), c2.r);
        c1 = new Circle(new Point(0, 0), c1.r);
        Line l = new Line(-2 * c2.center.x, -2 * c2.center.y, c2.center.x * c2.center.x +
                c2.center.y * c2.center.y + c1.r * c1.r - c2.r * c2.r);
        Point[] result = crossCircleAndLine(c1, l);
        for (int i = 0; i < result.length; ++i)
            result[i] = shift(result[i], shift);
        return result;
    }

    public static Point[] crossCircleAndLine(Circle c, Line l) {
        Point p = pointOnLine(l);
        p = shift(p, new Vector2d(-c.center.x, -c.center.y));
        l = normalAndPointToLine(new Vector2d(l.A, l.B), p);
        Point base = new Point(-l.C * l.A / (l.A * l.A + l.B * l.B), -l.C * l.B / (l.A * l.A + l.B * l.B));
        double d = distance(base, new Point(0, 0));
        if (c.r + eps < d)
            return new Point[0];
        if (Math.abs(c.r - d) < eps)
            return new Point[] { shift(base, new Vector2d(c.center.x, c.center.y))};
        double dShift = Math.sqrt(c.r * c.r - d * d);
        Vector2d colNormV = (new Vector2d(l.A, l.B)).normal().normalize();
        Point p1 = shift(base, new Vector2d(colNormV.x * dShift, colNormV.y * dShift));
        Point p2 = shift(base, new Vector2d(-colNormV.x * dShift, -colNormV.y * dShift));
        return new Point[] { shift(p1, new Vector2d(c.center.x, c.center.y)),
                shift(p2, new Vector2d(c.center.x, c.center.y))};
    }

    public static Point shift(Point p, Vector2d shift) {
        return new Point(p.x + shift.x, p.y + shift.y);
    }

    public static Point[] tangents(Circle circle, Point p) {
        double d = distance(p, circle.center);
        if (d < circle.r - eps)
            return new Point[0];
        if (Math.abs(d - circle.r) < eps)
            return new Point[] { p };
        double alpha = Math.asin(circle.r / d);
        double a = Math.sqrt(d * d - circle.r * circle.r);
        double fiTurn = Math.atan2(circle.center.y - p.y, circle.center.x - p.x);
        Point p1 = new Point(a*Math.cos(alpha), a * Math.sin(alpha));
        Point p2 = new Point(a*Math.cos(alpha), -a * Math.sin(alpha));
        return new Point[] { shift(turn(p1, fiTurn), new Vector2d(p.x, p.y)),
                shift(turn(p2, fiTurn), new Vector2d(p.x, p.y))};
    }

    public static double distance(Point a, Point b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    public static Point turn(Point p, double fi) {
        return new Point(p.x * Math.cos(fi) - p.y * Math.sin(fi),
                p.x * Math.sin(fi) + p.y * Math.cos(fi));
    }

    public static Point pointOnLine(Line l) {
        if (l.B != 0)
            return new Point(0,- l.C / l.B);
        else
            return new Point(- l.C / l.A, 0);
    }

    public static Line normalAndPointToLine(Vector2d normal, Point p) {
        return new Line(normal.x, normal.y, - (normal.x * p.x + normal.y * p.y));
    }
}

class Point implements Comparable {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Point that) {
        return ((Math.abs(this.x - that.x) < NimGame.eps) && (Math.abs(this.y - that.y) < NimGame.eps));
    }

    @Override
    public int compareTo(Object o) {
        Point that = (Point)o;
        if (this.x < that.x)
            return -1;
        if (this.x > that.x)
            return 1;
        if (this.y < that.y)
            return -1;
        return 1;
    }
}

class Line {
    public double A;
    public double B;
    public double C;

    public Line(double a, double b, double c) {
        A = a;
        B = b;
        C = c;
    }
}

class Vector2d {
    public double x;
    public double y;

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d(Point s, Point f) {
        this.x = f.x - s.x;
        this.y = f.y - s.y;
    }

    public Vector2d normalize() {
        double koeff = Math.sqrt(this.x * this.x + this.y * this.y);
        return new Vector2d(this.x / koeff, this.y / koeff);
    }

    public Vector2d sum(Vector2d v) {
        return new Vector2d(this.x + v.x, this.y + v.y);
    }

    public Vector2d normal() {
        return new Vector2d(-y, x);
    }
}

class Segment {
    public Point a;
    public Point b;

    public Segment(Point a, Point b) {
        this.a = a;
        this.b = b;
    }
}

class Circle {
    public Point center;
    public double r;

    public Circle(Point center, double r) {
        this.center = center;
        this.r = r;
    }
}








