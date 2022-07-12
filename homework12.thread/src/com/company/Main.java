package com.company;

public class Main {
    private static final int size = 10000000;
    private static final int h = size / 2;
    private static float[] arr = new float[size];

       private static void SingleThread(float[] arr) {
       long start = System.currentTimeMillis();

            for (int i = 0; i < size; i++) {
                arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5f) * Math.cos(0.2f + i / 5f) * Math.cos(0.4f + i / 2f));
            }
            long singleTime = System.currentTimeMillis() - start;

            System.out.printf("Время при однопоточных вычислениях: %d%n", singleTime);
        }

        private static void MultiThread(float[] arr) {
            float[] a = new float[h];
            float[] b = new float[h];

            long start = System.currentTimeMillis();

            System.arraycopy(arr, 0, a, 0, h);
            System.arraycopy(arr, h, b, 0, h);

            MyThread t1 = new MyThread("a", a);
            MyThread t2 = new MyThread("b", b);

            t1.start();
            t2.start();

            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            a = t1.getArr();
            b = t2.getArr();

            System.arraycopy(a, 0, arr, 0, h);
            System.arraycopy(b, 0, arr, a.length, b.length);

            long multiTime = System.currentTimeMillis() - start;

            System.out.printf("Время при многопоточных вычислениях: %d%n", multiTime);
        }

        public static void main(String[] args) {

            for (int i = 0; i < size; i++) {
                arr[i] = 1;
            }

            SingleThread(arr);
            MultiThread(arr);

        }

    }