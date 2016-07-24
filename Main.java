package ru.geekbrains.java2.dz.dz5.ЮрийНиконоров;


/**
 * Created by YurokVRS on 21.06.2016.
 */
public class Main {
    static final int size = 10000000;
    static final int h = size / 2;

    public static void main(String[] args) {

        firstOperation();
        secondOperation();


    }

    private static void secondOperation() {
        float[] arr = new float[size];
        for (float f:arr) {f = 1;}

        long a = System.currentTimeMillis();

        float[] a1 = new float[h];
        float[] a2 = new float[h];

        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        MyThread myThread1 = new MyThread();
        MyThread myThread2 = new MyThread();

        myThread1.setArr(a1);
        myThread1.run();

        myThread2.setArr(a2);
        myThread2.run();

        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);

        System.out.println("Второй метод (в два потока)" + (System.currentTimeMillis() - a));
    }

    private static void firstOperation() {
        float[] arr = new float[size];
        for (float f:arr) {f = 1;}

        long a = System.currentTimeMillis();

        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) *
                    Math.cos(0.2f + i/5) * Math.cos(0.4f + i/2));
        }

        System.out.println("Первый метод (один поток)" + (System.currentTimeMillis() - a));
    }
}
