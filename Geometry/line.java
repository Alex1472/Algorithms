import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class NimGame {

    public static double eps = 1e-9;
    public static int seg = 8;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new FileInputStream(new File("input.txt")));
        //Scanner sc = new Scanner(System.in);
        Segment seg1 = new Segment(new Point(sc.nextDouble(), sc.nextDouble()), new Point(sc.nextDouble(), sc.nextDouble()));
        Segment seg2 = new Segment(new Point(sc.nextDouble(), sc.nextDouble()), new Point(sc.nextDouble(), sc.nextDouble()));
        crossSegments(seg1, seg2);
        sc.close();
    }
    //пересечение 2-х отрезков. Empty - не пересекаются. Координаты точки, если пересекаются в точке
    //координаты отрезка, если пересечение - отрезов.
    public static void crossSegments(Segment seg1, Segment seg2) {
        if (seg1.a.equals(seg1.b)) {
            if (isPointOnSegment(seg2, seg1.a))
                System.out.println(seg1.a.x + " " + seg1.a.y);
            else
                System.out.println("Empty");
            return;
        }
        if (seg2.a.equals(seg2.b)) {
            if (isPointOnSegment(seg1, seg2.a))
                System.out.println(seg2.a.x + " " + seg2.a.y);
            else
                System.out.println("Empty");
            return;
        }
        Line l1 = twoPointsToLine(seg1.a, seg1.b);
        Line l2 = twoPointsToLine(seg2.a, seg2.b);
        int res = isLinesCrossed1(l1, l2);
        if (res == 0) {
            System.out.println("Empty");
            return;
        }
        if (res == 1) {
            Point p = crossNonparallelLines(l1, l2);
            if (isPointOnSegment(seg1, p) && isPointOnSegment(seg2, p))
                System.out.println(p.x + " " + p.y);
            else
                System.out.println("Empty");
            return;
        }
        List<Point> pResult = new ArrayList<Point>();
        if (isPointOnSegment(seg1, seg2.a))
            pResult.add(seg2.a);
        if (isPointOnSegment(seg1, seg2.b))
            pResult.add(seg2.b);
        if (isPointOnSegment(seg2, seg1.a))
            pResult.add(seg1.a);
        if (isPointOnSegment(seg2, seg1.b))
            pResult.add(seg1.b);
        if (pResult.size() == 0) {
            System.out.println("Empty");
            return;
        }
        if (pResult.size() == 0) {
            System.out.println("Empty");
            return;
        }

        Point[] pResult1 = new Point[pResult.size()];
        pResult.toArray(pResult1);
        Arrays.sort(pResult1);
        System.out.println(pResult1[0].x + " " + pResult1[0].y);
        for (int i = 1; i < pResult.size(); ++i)
            if (!pResult1[i].equals(pResult1[i - 1]))
                System.out.println(pResult1[i].x + " " + pResult1[i].y);
    }
    //проверка принадлежности точки отрезку(отрезок может вырождаться в точку)
    public static boolean isPointOnSegment(Segment seg, Point p) {
        if (seg.a.equals(seg.b))
            return seg.a.equals(p);
        Line l = twoPointsToLine(seg.a, seg.b);
        if (!isPointOnLine(l, p))
            return false;
        return ((Math.max(seg.a.x, seg.b.x) >= p.x) && (Math.min(seg.a.x, seg.b.x) <= p.x)
                && (Math.max(seg.a.y, seg.b.y) >= p.y) && (Math.min(seg.a.y, seg.b.y) <= p.y));
    }

    //прямая параллельная данной и лежащая на растоянии r от данной
    public static Line parallelLine(Line l, double r) {
        Point p = pointOnLine(l);
        double x = p.x + r * l.A / Math.sqrt(l.A * l.A + l.B * l.B);
        double y = p.y + r * l.B / Math.sqrt(l.A * l.A + l.B * l.B);
        return new Line(l.A, l.B, -(l.A * x + l.B * y));
    }
    //возвращает некоторую точку принадлежащую данной прямой
    public static Point pointOnLine(Line l) {
        if (l.B != 0)
            return new Point(0,- l.C / l.B);
        else
            return new Point(- l.C / l.A, 0);
    }

    //находит прямую перпендикулярную данной и проходящую через заданную точку
    public static Line perpendicularLineAcrossPoint(Line l, Point p) {
        double A = -l.B;
        double B = l.A;
        return new Line(A, B, -(A * p.x + B * p.y));
    }
    //проверка пересечения 2-х прямых
    public static int isLinesCrossed1(Line l1, Line l2) {
        if (equaqivalent(l1, l2))
            return 2;
        if (parallel(l1, l2))
            return 0;
        return 1;
    }

    public static double det(double a, double b, double c, double d) {
        return a * d - b * c;
    }
    //проверка параллельности 2-х прямых(при этом они могут совпадать))
    public static boolean parallel(Line l1, Line l2) {
        return Math.abs(det(l1.A, l1.B, l2.A, l2.B)) < eps;
    }
    //проверка, что 2-е прямые совпадают
    public static boolean equaqivalent(Line l1, Line l2) {
        return (Math.abs(det(l1.A, l1.B, l2.A, l2.B)) < eps)
                && (Math.abs(det(l1.B, l1.C, l2.B, l2.C)) < eps)
                && (Math.abs(det(l1.A, l1.C, l2.A, l2.C)) < eps);
    }
    //пересечение 2-х непараллельных прямых
    public static Point crossNonparallelLines(Line l1, Line l2) {
        double y0 = (-l1.A * l2.C + l2.A * l1.C) / (l1.A * l2.B - l2.A * l1.B);
        double x0 = (-l1.C*l2.B + l1.B * l2.C) / (l1.A * l2.B - l2.A * l1.B);
        return new Point(x0, y0);
    }
    //растояние от точки до прямой
    public static double distanceFromLineToPoint(Line l, Point p) {
        return Math.abs(l.A * p.x + l.B * p.y + l.C) / Math.sqrt(l.A * l.A + l.B * l.B);
    }
    //проверка принадлежности точки прямой
    public static boolean isPointOnLine(Line l, Point p) {
        if (Math.abs(l.A * p.x + l.B * p.y + l.C) < eps)
            return true;
        else
            return false;
    }
    
    public static Line twoPointsToLine(Point p1, Point p2) {
        double A = p2.y - p1.y;
        double B = -(p2.x - p1.x);
        double C = -(A*p1.x + B*p1.y);
        return new Line(A, B, C);
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
}

class Segment {
    public Point a;
    public Point b;

    public Segment(Point a, Point b) {
        this.a = a;
        this.b = b;
    }
}










