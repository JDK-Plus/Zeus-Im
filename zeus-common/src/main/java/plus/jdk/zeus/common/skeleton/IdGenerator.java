package plus.jdk.zeus.common.skeleton;

public class IdGenerator {

    public static int maxLen = 19;

    public static long getId() {
        long timestamp = System.currentTimeMillis();
        StringBuilder builder = new StringBuilder(Long.toString(timestamp));
        while (builder.length() < maxLen) {
            double random = (Math.random() * 100) % 10;
            builder.append((int) random);
        }
        return Long.parseLong(builder.toString());
    }

    public static long getId(int maxLen) {
        StringBuilder builder = new StringBuilder();
        while (builder.length() < maxLen) {
            double random = (Math.random() * 100) % 10;
            if(builder.length() == 0 && random < 1) {
                continue;
            }
            builder.append((int) random);
        }
        return Long.parseLong(builder.toString());
    }

}
