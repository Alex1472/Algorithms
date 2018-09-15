import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class NimGame {

    public static double eps = 1e-9;
    public static int seg = 8;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new FileInputStream(new File("input.txt")));
        //Scanner sc = new Scanner(System.in);
        Point a = new Point(sc.nextDouble(), sc.nextDouble());
        Point b = new Point(sc.nextDouble(), sc.nextDouble());
        Point c = new Point(sc.nextDouble(), sc.nextDouble());
        Triangle triangle = new Triangle(a, b, c);
        Point o = crossMiddlePerpendicular(triangle);
        double r = distanceBetweenPoints(triangle.a, o);
        System.out.println(o.x + " " + o.y + " " + r);
        sc.close();
    }
    //точка пересечения серединных перпендикуляров
    public static Point crossMiddlePerpendicular(Triangle triangle) {
        Line n1 = middlePerpendicular(triangle.a, triangle.b);
        Line n2 = middlePerpendicular(triangle.a, triangle.c);
        return crossNonparallelLines(n1, n2);
    }
    //серединный перпендикуляр
    public static Line middlePerpendicular(Point p1, Point p2) {
        Vector2d v = new Vector2d(p1.x - p2.x,p1.y - p2.y);
        return normalAndPointToLine(v, new Point((p1.x + p2.x) / 2, (p1.y + p2.y) / 2));
    }
    //точка пересечения биссектрис
    public static Point crossBisector(Triangle triangle) {
        Line b1 = bisectorAngle(triangle.b, triangle.a, triangle.c);
        Line b2 = bisectorAngle(triangle.a, triangle.b, triangle.c);
        return crossNonparallelLines(b1, b2);
    }
    //биссектриса угла bac
    public static Line bisectorAngle(Point b, Point a, Point c) {
        Vector2d v1 = new Vector2d(b.x - a.x,b.y - a.y);
        Vector2d v2 = new Vector2d(c.x - a.x,c.y - a.y);
        v1 = v1.normalize();
        v2 = v2.normalize();
        return vectorAndPointToLine(v1.sum(v2), a);
    }
    //точка пересечения высот
    public static Point crossAltitudes(Triangle triangle) {
        Line h1 = perpendicularLineAcrossPoint(twoPointsToLine(triangle.a, triangle.b), triangle.c);
        Line h2 = perpendicularLineAcrossPoint(twoPointsToLine(triangle.a, triangle.c), triangle.b);
        return crossNonparallelLines(h1, h2);
    }
    //точка пересечения медиан
    public static Point crossMedians(Triangle triangle) {
        Line m1 = twoPointsToLine(triangle.a, new Point((triangle.b.x + triangle.c.x) / 2,
                (triangle.b.y + triangle.c.y) / 2));
        Line m2 = twoPointsToLine(triangle.b, new Point((triangle.a.x + triangle.c.x) / 2,
                (triangle.a.y + triangle.c.y) / 2));
        return crossNonparallelLines(m1, m2);
    }

    //находит прямую перпендикулярную данной и проходящую через заданную точку
    public static Line perpendicularLineAcrossPoint(Line l, Point p) {
        double A = -l.B;
        double B = l.A;
        return new Line(A, B, -(A * p.x + B * p.y));
    }


    public static double det(double a, double b, double c, double d) {
        return a * d - b * c;
    }

    //пересечение 2-х непараллельных прямых
    public static Point crossNonparallelLines(Line l1, Line l2) {
        double y0 = (-l1.A * l2.C + l2.A * l1.C) / (l1.A * l2.B - l2.A * l1.B);
        double x0 = (-l1.C*l2.B + l1.B * l2.C) / (l1.A * l2.B - l2.A * l1.B);
        return new Point(x0, y0);
    }

    public static Line twoPointsToLine(Point p1, Point p2) {
        double A = p2.y - p1.y;
        double B = -(p2.x - p1.x);
        double C = -(A*p1.x + B*p1.y);
        return new Line(A, B, C);
    }

    public static Line vectorAndPointToLine(Vector2d v, Point p) {
        double a = v.y;
        double b = -v.x;
        return new Line(a, b, -(a * p.x + b * p.y));
    }

    public static Line normalAndPointToLine(Vector2d normal, Point p) {
        return new Line(normal.x, normal.y, - (normal.x * p.x + normal.y * p.y));
    }

    public static double distanceBetweenPoints(Point a, Point b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }
}

class Triangle {
    public Point a;
    public Point b;
    public Point c;

    public Triangle(Point a, Point b, Point c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double square() {
        return Math.abs(((b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y)) / 2);
    }

    public double perimeter() {
        return distenceBetweenPoints(a, b) +
                distenceBetweenPoints(a, c) +
                distenceBetweenPoints(b, c);
    }

    double distenceBetweenPoints(Point a, Point b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
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












