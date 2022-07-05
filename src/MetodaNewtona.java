import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MetodaNewtona {

    double h = 0.0000001;
    double epsilon = 0.01;
    static double xk = 1, yk = 1 ;

    private double f(double x, double y) {
        return Math.pow(x, 3) + 5 * Math.pow(y, 2) - 6 * x * y - y + 7;
//        return 10* Math.pow(x,2) + 12*x*y + 10* Math.pow(y,2);
    }


    private double pochodnaX(double x, double y) {
        return (f(x + h, y) - f(x, y)) / h;
    }

    private double pochodnaY(double x, double y) {
        return (f(x, y + h) - f(x, y)) / h;
    }

    private double pochodnaXX(double x, double y) {
        return (f(x + 2 * h, y) - 2 * f(x + h, y) + f(x, y)) / Math.pow(h, 2);
    }

    private double pochodnaYY(double x, double y) {
        return (f(x, y + 2 * h) - 2 * f(x, y + h) + f(x, y)) / Math.pow(h, 2);
    }

    private double pochodnaXY(double x, double y) {
        return (f(x + h, y + h) - f(x + h, y) - f(x, y + h) + f(x, y)) / Math.pow(h, 2);
    }

     public double[] gradient(double x, double y) {
        var resultArray = new double[2];
        resultArray[0] = (pochodnaX(x, y));
        resultArray[1] = (pochodnaY(x, y));
        return resultArray;
    }

     public double[][] gradientKwadrat(double x, double y) {
        var resultArray = new double[2][2];
        resultArray[0][0] = (pochodnaXX(x, y));
        resultArray[0][1] = (pochodnaXY(x, y));
        resultArray[1][0] = (pochodnaXY(x, y));
        resultArray[1][1] = (pochodnaYY(x, y));
        return resultArray;
    }

    public static double[][] gradientKwadratInverse(double[][] gradientKwadrat){
        var wynik = new double[2][2];
        var a = gradientKwadrat[0][0];
        var b = gradientKwadrat[0][1];
        var c = gradientKwadrat[1][0];
        var d = gradientKwadrat[1][1];
        var skalar = 1/(a*d-b*c);

        wynik[0][0] = skalar * (d);
        wynik[0][1] = skalar * (-b);
        wynik[1][0] = skalar * (-c);
        wynik[1][1] = skalar * a;

        return wynik;
    }

    public static double[] multiplicaton2x1(double[][] m1, double[] m2){
        var wynik = new double[2];
        wynik[0] = m1[0][0] * m2[0] + m1[0][1] * m2[1];
        wynik[1] = m1[1][0] * m2[0] + m1[1][1] * m2[1];
        return wynik;
    }

    public static double[] subtraction2x2(double[] m1, double[] m2){
        double[] wynik = new double[2];
        for(int i=0;i<2;i++){
            wynik[i] = m1[i] - m2[i];
        }
        return wynik;
    }

    public  void calculate() {

        double[] XkYkPrevious = {xk,yk};
        double[] XkYkCurrent = new double[2];
        int iter=0;

        while(true){
            System.out.println("Iteracja " + ++iter);
            XkYkCurrent = subtraction2x2(XkYkPrevious,
                    multiplicaton2x1(
                            gradientKwadratInverse(gradientKwadrat(XkYkPrevious[0],XkYkPrevious[1])),
                                    gradient(XkYkPrevious[0],XkYkPrevious[1])
                            )
                    );

            if((Math.abs(gradient(XkYkCurrent[0],XkYkCurrent[1])[0])<=epsilon &&
                    Math.abs(gradient(XkYkCurrent[0],XkYkCurrent[1])[1])<=epsilon)
            || (Math.abs(XkYkCurrent[0] - XkYkPrevious[0])<=epsilon &&
                    Math.abs(XkYkCurrent[1] - XkYkPrevious[1])<=epsilon)){
                System.out.println("Wynik = ( " + XkYkCurrent[0] + " , " + XkYkCurrent[1] +" )");
                break;
            }

                XkYkPrevious = XkYkCurrent;
        }

    }
}
