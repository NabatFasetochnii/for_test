import java.io.FileWriter;
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
                        param = random.nextInt((int) (width / divisionParam), (int) (width / 4));
                        x = random.nextInt(0, width - param - 1);
                        y = random.nextInt(0, height - param - 1);

                        for (int j = 0; j < i; j++) {
                            b = b && r[j].isNotOverlay(x, y, x + param, y + param);
                        }
                    } while (!b);

                } else {
                    param = random.nextInt((int) (width / divisionParam), (int) (width / 4));
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
            //g /= (screen.size()-1);
            if (g > p / 15.0) {
                screens1.add(screen);
            } else if (p / 15.0 > g && g > p / 18.0) {
                screens2.add(screen);
            } else if (p / 18.0 > g && g > p / 25.0) {
                screens3.add(screen);
            } else if (p / 25.0 > g && g > p / 30.0) {
                screens4.add(screen);
            } else if (p / 30.0 > g && g > p / 35.0) {
                screens5.add(screen);
            } else if (p / 35.0 > g && g > p / 40.0) {
                screens6.add(screen);
            }
        }


        System.out.println(screens1.size());
        System.out.println(screens2.size());
        System.out.println(screens3.size());
        System.out.println(screens4.size());
        System.out.println(screens5.size());
        System.out.println(screens6.size());

        FileWriter fileWriter1 = new FileWriter("3screen1.txt", true);
        FileWriter fileWriter2 = new FileWriter("3screen2.txt", true);
        FileWriter fileWriter3 = new FileWriter("3screen3.txt", true);
        FileWriter fileWriter4 = new FileWriter("3screen4.txt", true);
        FileWriter fileWriter5 = new FileWriter("3screen5.txt", true);
        FileWriter fileWriter6 = new FileWriter("3screen6.txt", true);

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

    private static void wr(ArrayList<ArrayList<double[]>> screens, FileWriter fileWriter) throws IOException {
        for (ArrayList<double[]> s: screens) {
            for(double[] d : s){
                for (double b: d) {
                    fileWriter.write((int) b + " ");
                }
            }
            fileWriter.append('\n');
        }

        fileWriter.flush();
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
