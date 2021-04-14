package monitor_blocking_sequence;

/*
Определяем порядок захвата монитора
*/

import java.util.ArrayList;

public class Solution {
    public void someMethodWithSynchronizedBlocks(Object obj1, Object obj2) {
        synchronized (obj1) {
            synchronized (obj2) {
                System.out.println(obj1 + " " + obj2);
            }
        }
    }

    public static boolean isLockOrderNormal(final Solution solution, final Object o1, final Object o2) throws Exception {
        //do something here
        final ArrayList<String> str = new ArrayList<>();

        // 1. Данная нить будет добавлять в список запись о том что метод запускается, и запускать тот самый метод для проверки someMethodWithSynchronizedBlocks() передавая параметры o1 и o2
        Runnable runnable1  = new Runnable() {
            @Override
            public void run() {
                str.add("Launching the test");
                solution.someMethodWithSynchronizedBlocks(o1,o2);
            }
        };

        // 2. Нить для блокировки объекта O1, занимается мьютекс обьекта, и блокируется нить на 250 мс
        Runnable blockO1for250mills = new Runnable() {
            @Override
            public void run() {
                str.add("Trying to grab O1 "+ System.currentTimeMillis());
                synchronized (o1){
                    try {
                        str.add("O1 is blocked " + System.currentTimeMillis());
                        Thread.sleep(250);
                        str.add("Release O1 at " + System.currentTimeMillis());

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        // 3. Нить для блокировки объекта O2, занимается мьютекс обьекта, и блокируется нить на 250 мс
        Runnable blockO2for250mills = new Runnable() {
            @Override
            public void run() {
                str.add("Trying to grab O2");

                synchronized (o2){
                    try {
                        str.add("O2 is blocked");
                        Thread.sleep(250);
                        str.add("Release O2 at " + System.currentTimeMillis());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };


        Thread testThread = new Thread(runnable1);
        Thread blockO1 = new Thread(blockO1for250mills);
        Thread blockO2 = new Thread(blockO2for250mills);
        Thread tryO1 = new Thread(blockO1for250mills);
        Thread tryO2 = new Thread(blockO2for250mills);

        //Запускаем нити в определенном порядке, и делаем так, чтобы они точно запустились в даном порядке. (Thread.sleep)
        //Что происходит: стартуем нить с блокировкой обьекта O1, затем запускаем нить с методом, который тестируем. Метод будет зависать на блоке O1.
        //Далее запускаем метод-проверку, который будет пробовать заблокировать мьютекс О2.
        //Таким образом, если порядок правильный, то последняя нить сможет сразу захватить О2. Лог в списке будет соответствующий.
        //Определяем результат по тому, какой порядок логирующих строк в списке.

        blockO1.start();
        Thread.sleep(5);

        testThread.start();
        Thread.sleep(5);

        tryO2.start();

        testThread.join();
        blockO1.join();
        tryO2.join();

        //Цикл для визуализации списка, можно раскомментировать, если надо
        //    for (String x: str) {
        //        System.out.println(x);
        //    }

        if (str.indexOf("Trying to grab O2")+1 == str.indexOf("O2 is blocked"))
            return true;
        else
            return false;
    }

    public static void main(String[] args) throws Exception {
        final Solution solution = new Solution();
        final Object o1 = new Object();
        final Object o2 = new Object();

        System.out.println(isLockOrderNormal(solution, o1, o2));
    }
}