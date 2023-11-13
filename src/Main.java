import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static AtomicInteger count3 = new AtomicInteger(0);
    public static AtomicInteger count4 = new AtomicInteger(0);
    public static AtomicInteger count5 = new AtomicInteger(0);


    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
            //System.out.println(texts[i]);
        }


        // Палиндром
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                StringBuffer buffer = new StringBuffer(texts[i]);
                buffer.reverse();
                String textConv = buffer.toString();
                if (texts[i].equals(textConv)) {

                    if (texts[i].length() == 3) {
                        count3.getAndIncrement();
                    }
                    if (texts[i].length() == 4) {
                        count4.getAndIncrement();
                    }
                    if (texts[i].length() == 5) {
                        count5.getAndIncrement();
                    }
                }
            }
        });
        // состоит из одной и той же буквы
        Thread t2 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                boolean b = true;
                char ch = texts[i].charAt(0);
                for (int n = 1; n < texts[i].length(); n++) {
                    if (texts[i].charAt(n) != ch) {
                        b = false;
                    }
                }
                if (b) {

                    if (texts[i].length() == 3) {
                        count3.getAndIncrement();
                    }
                    if (texts[i].length() == 4) {
                        count4.getAndIncrement();
                    }
                    if (texts[i].length() == 5) {
                        count5.getAndIncrement();
                    }
                }
            }
        });
        // буквы в слове идут по возрастанию
        Thread t3 = new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                char[] chArr = texts[i].toCharArray();
                Arrays.sort(chArr);
                String textOrder = String.valueOf(chArr);
                if (texts[i].equals(textOrder)) {

                    if (texts[i].length() == 3) {
                        count3.getAndIncrement();
                    }
                    if (texts[i].length() == 4) {
                        count4.getAndIncrement();
                    }
                    if (texts[i].length() == 5) {
                        count5.getAndIncrement();
                    }
                }
            }
        });
        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("Красивых слов с длиной 3: " + count3.get() + " шт.");
        System.out.println("Красивых слов с длиной 4: " + count4.get() + " шт.");
        System.out.println("Красивых слов с длиной 5: " + count5.get() + " шт.");
    }
}

