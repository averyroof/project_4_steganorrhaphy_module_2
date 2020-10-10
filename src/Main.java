import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static Charset w1251 = Charset.forName("Windows-1251");

    public static String readFile(Path path) throws IOException {
        byte[] bytesOfFile = Files.readAllBytes(path);
        String fileStr = new String(bytesOfFile, w1251);
        return fileStr;
    }

    public static String recovery(String text) {
        String result = "";
        Map<Character, Character> states = new HashMap<>();
        states.put('K', 'К');
        states.put('E', 'Е');
        states.put('H', 'Н');
        states.put('X', 'Х');
        states.put('B', 'В');
        states.put('A', 'А');
        states.put('P', 'Р');
        states.put('O', 'О');
        states.put('C', 'С');
        states.put('M', 'М');
        states.put('T', 'Т');
        states.put('e', 'е');
        states.put('o', 'о');
        states.put('x', 'х');
        states.put('c', 'с');
        states.put('p', 'р');
        states.put('a', 'а');
        char[] chText = text.toCharArray();
        for (char c : chText) {
            if (states.containsKey(c)) {
                result = result + "1";
            } else if (states.containsValue(c)) {
                result = result + "0";
            }
        }
        return result;
    }

    public static String createHidden(String str) throws UnsupportedEncodingException {
        String hidden = "";
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        for (int i = 0; i < str.length(); i += 8) {
            String sub = str.substring(i, i + 8);
            if (sub.equals("00000000")) {
                return hidden;
            }
            int value = Integer.parseInt(str.substring(i, i + 8), 2);
            baos.write(value);
            hidden = baos.toString("Cp1251");
        }
        return hidden;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

        // C:\Projects\project_2_steganorrhaphy\files\result2.txt
        System.out.println("\nВведите путь к результирующему файлу, в котором находится текст со скрытой информацией: ");
        Path pathResult = Path.of(bf.readLine());
        String textString = readFile(pathResult);

        String str = recovery(textString);
        String result = createHidden(str);

        System.out.println("\nИзвлеченная сокрытая информация: " + result);
    }
}
