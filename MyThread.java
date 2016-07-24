package ru.geekbrains.java2.dz.dz5.ЮрийНиконоров;

import static ru.geekbrains.java2.dz.dz5.ЮрийНиконоров.Main.h;

/**
 * Created by YurokVRS on 21.06.2016.
 */
public class MyThread implements Runnable{
    private float[] arr = new float[h];

    public void setArr(float[] arr) {
        this.arr = arr;
    }

    @Override
    public void run() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) *
                    Math.cos(0.2f + i/5) * Math.cos(0.4f + i/2));
        }
    }
}
