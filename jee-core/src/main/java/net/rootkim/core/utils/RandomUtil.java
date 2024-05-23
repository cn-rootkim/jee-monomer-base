package net.rootkim.core.utils;

import java.util.List;
import java.util.Random;

/**
 * @author RootKim[rootkim.net]
 * @since 2024/4/27
 */
public class RandomUtil {

    /**
     * 范围获取随机int数字（从0开始）
     *
     * @param max（最大值，不包含）
     * @return
     */
    public static int getInt(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }

    /**
     * 范围获取随机int数字（从0开始）
     *
     * @param max（最大值，不包含）
     * @return
     */
    public static String getIntStr(int max) {
        Random random = new Random();
        return new Integer(random.nextInt(max)).toString();
    }

    /**
     * 范围获取随机int数字（从0开始）
     *
     * @param max（最大值，不包含）
     * @param length 长度
     * @return
     */
    public static String getIntStr(int max, int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            Random random = new Random();
            stringBuilder.append(random.nextInt(max));
        }
        return stringBuilder.toString();
    }

    /**
     * 范围获取随机int数字（从0开始）
     *
     * @param max（最大值，不包含）
     * @return
     */
    public static Byte getIntByte(int max) {
        Random random = new Random();
        return (byte) random.nextInt(max);
    }

    /**
     * 获取List随机元素
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> T getOne(List<T> list) {
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

    /**
     * 获取随机中文
     *
     * @param length
     * @return
     */
    public static String getChinese(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int high = 0x9FA5; // 高位Unicode编码
            int low = 0x4E00; // 低位Unicode编码
            char ch = (char) (random.nextInt(high - low + 1) + low);
            sb.append(ch);
        }
        return sb.toString();
    }

    /**
     * 获取随机字母
     *
     * @param length
     * @return
     */
    public static String getAlphabet(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int num = random.nextInt(26); // 生成0到25之间的随机数
            char alphabet = (char) (num + 65);
            sb.append(alphabet);
        }
        return sb.toString();
    }

    /**
     * 获取随机字母和中文
     *
     * @param minLength
     * @param maxLength
     * @return
     */
    public static String getAlphabetAndChinese(int minLength, int maxLength) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int randomNumber = random.nextInt(maxLength - minLength + 1) + minLength;
        for (int i = 0; i < randomNumber; i++) {
            if (random.nextInt(2) == 0) {
                int high = 0x9FA5; // 高位Unicode编码
                int low = 0x4E00; // 低位Unicode编码
                char ch = (char) (random.nextInt(high - low + 1) + low);
                sb.append(ch);
            } else {
                int num = random.nextInt(26); // 生成0到25之间的随机数
                char alphabet = (char) (num + 65);
                sb.append(alphabet);
            }
        }
        return sb.toString();
    }
}
