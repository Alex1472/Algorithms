import com.sun.org.apache.xpath.internal.SourceTree;

import java.io.*;
import java.util.*;

public class NimGame {

    public static double eps = 1e-9;
    public static int seg = 8;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new FileInputStream(new File("input.txt")));
        //Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        Point[] polygon = new Point[n];
        for (int i = 0; i < n; ++i)
            polygon[i] = new Point(sc.nextDouble(), sc.nextDouble());
        Point[] hull = convexHull(polygon);


        System.out.println(perimeter(hull));
        System.out.println(square(hull));
        //System.out.println("YES");
        sc.close();
    }

    public static double perimeter(Point[] points) {
        double p = 0;
        for (int i = 0; i < points.length - 1; ++i)
            p += distance(points[i], points[i + 1]);
        p += distance(points[points.length - 1], points[0]);
        return p;
    }

    public static Point[] convexHull(Point[] points) {
        Point tmp = points[0];
        int pos = 0;
        for (int i = 1; i < points.length; ++i) {
            if (points[i].y < tmp.y) {
                tmp = points[i];
                pos = i;
                continue;
            }
            if ((points[i].y == tmp.y) && (points[i].x > tmp.x)) {
                tmp = points[i];
                pos = i;
            }
        }
        final Point p0 = tmp;
        swap(points, 0, pos);
        Arrays.sort(points, 1, points.length, new Comparator<Point>() {
            @Override
            public int compare(Point point, Point t1) {
                double angle = angleBetweenVectors(new Vector2d(p0, point), new Vector2d(p0, t1));
                if (angle > 0) return -1;
                if (angle < 0) return 1;
                return (distance(p0, point) < distance(p0, t1)) ? -1 : 1;
            }
        });
        int top = 1;
        int i = 2;
        while (i < points.length) {
            if (top == 0) {
                ++top;
                swap(points, i, top);
                ++i;
                continue;
            }
            double angle = angleBetweenVectors(new Vector2d(points[top - 1], points[top]),
                    new Vector2d(points[top], points[i]));
            if (angle > 0) {
                ++top;
                swap(points, i, top);
                ++i;
                continue;
            }
            if (angle < 0) {
                --top;
                continue;
            }
            swap(points, top, i);
            ++i;
        }
        return Arrays.copyOf(points, top + 1);

    }

    public static double distance(Point a, Point b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    static void swap(Point[] points, int i, int j) {
        Point q = points[i];
        points[i] = points[j];
        points[j] = q;
    }
    //площадь простого многоугольника для корректной работы в массив нужно добавить ещё одну первую точку
    public static double square(Point[] poly) {
        double s = 0;
        for (int i = 0; i < poly.length - 1; ++i)
            s += vectorProduct(new Vector2d(poly[i].x, poly[i].y),
                    new Vector2d(poly[i + 1].x, poly[i + 1].y));
        s += vectorProduct(new Vector2d(poly[poly.length - 1].x, poly[poly.length - 1].y),
                new Vector2d(poly[0].x, poly[0].y));
        return Math.abs(s / 2);
    }


    public static double angleBetweenVectors(Vector2d a, Vector2d b) {
        return Math.atan2(vectorProduct(a, b), scalarProduct(a, b));
    }
    //проверка выпуклости многоугольника. Для корректной работы в конец нужно добавить 2-е первые точки
    public static boolean isPolygonConvex(Point[] pol) {
        double sign = (pol[1].x - pol[0].x) * (pol[2].y - pol[1].y) - (pol[2].x - pol[1].x) * (pol[1].y - pol[0].y);
        for (int i = 1; i < pol.length - 2; ++i)
            if (sign * ((pol[i + 1].x - pol[i].x) * (pol[i + 2].y - pol[i + 1].y) -
                    (pol[i + 2].x - pol[i + 1].x) * (pol[i + 1].y - pol[i].y)) <= 0)
                return false;
        return true;
    }
    //принадлежность точки многоугольнику. В конец нужно добавить первую точку
    public static boolean isPointBelongsPolygon(Point[] poly, Point p) {
        double res = 0;
        for (int i = 0; i < poly.length - 1; ++i) {
            if (isPointOnSegment(new Segment(poly[i], poly[i + 1]), p))
                return true;
            Vector2d v1 = new Vector2d(poly[i].x - p.x,poly[i].y - p.y);
            Vector2d v2 = new Vector2d(poly[i + 1].x - p.x, poly[i + 1].y - p.y);
            res += Math.atan2(vectorProduct(v2, v1), scalarProduct(v2, v1));
        }
        return !(Math.abs(res) < eps);
    }

    public static boolean isPointOnSegment(Segment seg, Point p) {
        if (seg.a.equals(seg.b))
            return seg.a.equals(p);
        Line l = twoPointsToLine(seg.a, seg.b);
        if (!isPointOnLine(l, p))
            return false;
        return ((Math.max(seg.a.x, seg.b.x) >= p.x) && (Math.min(seg.a.x, seg.b.x) <= p.x)
                && (Math.max(seg.a.y, seg.b.y) >= p.y) && (Math.min(seg.a.y, seg.b.y) <= p.y));
    }

    public static Line twoPointsToLine(Point p1, Point p2) {
        double A = p2.y - p1.y;
        double B = -(p2.x - p1.x);
        double C = -(A*p1.x + B*p1.y);
        return new Line(A, B, C);
    }

    public static boolean isPointOnLine(Line l, Point p) {
        if (Math.abs(l.A * p.x + l.B * p.y + l.C) < eps)
            return true;
        else
            return false;
    }

    public static double scalarProduct(Vector2d a, Vector2d b) {
        return a.x * b.x + a.y * b.y;
    }

    public static double vectorProduct(Vector2d a, Vector2d b) {
        return a.x * b.y - b.x * a.y;
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










