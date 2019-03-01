package code;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*System.out.println("Welcome to the calculator which is not simple :)");
        System.out.println("First of all,choose the language you prefer:");*/

        System.out.print("输入表达式：");
        var reader=new Scanner(System.in);
        var s=reader.nextLine();//读取一行
        System.out.println(s);
        var ans= work.cal(s);
        System.out.println("答案为："+ans);
    }
}
