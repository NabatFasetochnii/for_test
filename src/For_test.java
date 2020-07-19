import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.pow;

public class For_test {

    public static void main(String[] args) throws IOException {

        RectZone[] r;
        final int LEVELS = 1_000_000;
        final int arr_size = 3;
        final double divisionParam = 30.0;
        final double division2 = 4;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int x; //координаты прямоугольничков
        int y;
        int param; //размер прямоугольничков (пока будут квадратики) 1080*1900
        int width = 1080;
        int height = 1920;
        int p = width * height;

        r = new RectZone[arr_size];

        ArrayList<ArrayList<double[]>> screens1 = new ArrayList<>();
        ArrayList<ArrayList<double[]>> screens2 = new ArrayList<>();
        ArrayList<ArrayList<double[]>> screens3 = new ArrayList<>();
        ArrayList<ArrayList<double[]>> screens4 = new ArrayList<>();
        ArrayList<ArrayList<double[]>> screens5 = new ArrayList<>();
        ArrayList<ArrayList<double[]>> screens6 = new ArrayList<>();

        for (int k = 0; k < LEVELS; k++) {
            ArrayList<double[]> screen = new ArrayList<>();
            for (int i = 0; i < arr_size; i++) {

                if (i > 0) {
                    boolean b;
                    do {
                        b = true;
                        param = random.nextInt((int) (width / divisionParam), (int) (width / division2));
                        x = random.nextInt(0, width - param - 1);
                        y = random.nextInt(0, height - param - 1);

                        for (int j = 0; j < i; j++) {
                            b = b && r[j].isNotOverlay(x, y, x + param, y + param);
                        }
                    } while (!b);

                } else {
                    param = random.nextInt((int) (width / divisionParam), (int) (width / division2));
                    x = random.nextInt(0, width - param - 1);
                    y = random.nextInt(0, height - param - 1);
                }
                r[i] = new RectZone(x, y, x + param, y + param);

                screen.add(new double[]{x, y, param});
            }

            double g = 0;
            for (double[] s : screen) {

                g += pow(s[2], 2);
            }

            if (arr_size>2) {
                //g /= (screen.size()-1);
                if (g > p / 15.0) {
                    screens1.add(screen);
                } else if (p / 15.0 > g && g > p / 18.0) {
                    screens2.add(screen);
                } else if (p / 18.0 > g && g > p / 25.0) {
                    screens3.add(screen);
                } else if (p / 25.0 > g && g > p / 30.0) {
                    screens4.add(screen);
                } else if (p / 30.0 > g && g > p / 45.0) {
                    screens5.add(screen);
                } else if (p / 45.0 > g && g > p / 50.0) {
                    screens6.add(screen);
                }
            } else {

                if (g > p / 5.0) {
                    screens1.add(screen);
                } else if (p / 5.0 > g && g > p / 8.0) {
                    screens2.add(screen);
                } else if (p / 8.0 > g && g > p / 15.0) {
                    screens3.add(screen);
                } else if (p / 15.0 > g && g > p / 20.0) {
                    screens4.add(screen);
                } else if (p / 20.0 > g && g > p / 25.0) {
                    screens5.add(screen);
                } else if (p / 25.0 > g && g > p / 30.0) {
                    screens6.add(screen);
                }
            }
        }


        System.out.println(screens1.size());
        System.out.println(screens2.size());
        System.out.println(screens3.size());
        System.out.println(screens4.size());
        System.out.println(screens5.size());
        System.out.println(screens6.size());

        DataOutputStream fileWriter1 = new DataOutputStream(new FileOutputStream("1", true));
        DataOutputStream fileWriter2 = new DataOutputStream(new FileOutputStream("2", true));
        DataOutputStream fileWriter3 = new DataOutputStream(new FileOutputStream("3", true));
        DataOutputStream fileWriter4 = new DataOutputStream(new FileOutputStream("4", true));
        DataOutputStream fileWriter5 = new DataOutputStream(new FileOutputStream("5", true));
        DataOutputStream fileWriter6 = new DataOutputStream(new FileOutputStream("6", true));

        try {
            wr(screens1, fileWriter1);
        }catch (IOException ignored){
            System.out.println("проблема в screens1");
        }

        try {
            wr(screens2, fileWriter2);
        }catch (IOException ignored){
            System.out.println("проблема в screens2");
        }

        try {
            wr(screens3, fileWriter3);
        }catch (IOException ignored){
            System.out.println("проблема в screens3");
        }

        try {
            wr(screens4, fileWriter4);
        }catch (IOException ignored){
            System.out.println("проблема в screens4");
        }

        try {
            wr(screens5, fileWriter5);
        }catch (IOException ignored){
            System.out.println("проблема в screens5");
        }

        try {
            wr(screens6, fileWriter6);
        }catch (IOException ignored){
            System.out.println("проблема в screens6");
        }
    }

    private static void wr(ArrayList<ArrayList<double[]>> screens, DataOutputStream fileWriter) throws IOException {
        for (ArrayList<double[]> s: screens) {
            for(double[] d : s){
                for (double b: d) {
                    fileWriter.writeInt((int) b);
                }
            }

        }

        fileWriter.close();
    }
}

class RectZone {

    int x;
    int y;
    int right;
    int bottom;

    public RectZone(int X, int Y, int right, int bottom) {

        this.x = X;
        this.y = Y;
        this.right = right;
        this.bottom = bottom;
    }

    boolean isNotOverlay(int X, int Y, int RIGHT, int BOTTOM) {
        return ((right < X || RIGHT < x) && (bottom < Y || BOTTOM < y));//
    }
}
