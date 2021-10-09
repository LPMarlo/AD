import java.nio.charset.StandardCharsets;

public class asd {

    public static void main(String[] args) {
        byte[] by_original = {0,1,-2,3,-4,-5,6};
        String str1 = new String(by_original, StandardCharsets.UTF_8);
        System.out.println("str1 >> "+str1);
    }
}
