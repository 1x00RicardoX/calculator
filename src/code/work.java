package code;
import java.util.Stack;
/*
    参考：https://www.cnblogs.com/magisk/p/8620303.html
    使用调度场算法
    已实现：小数的含括号加减乘除
    还需：求方程根//TODO
*/

class work {
    private static Stack<Double> numStack=new Stack<Double>();
    private static Stack<Character> charStack=new Stack<Character>();

    //using Shunting-yard algorithm
    static double cal(String s){
        int[] op=new int[128];
        op['+'] = op['-'] = 0;
        op['*'] = op['/'] = 1;
        op['^'] = 2;

        //查找 入栈
        for(int i=0;i<s.length();)
            if ((s.charAt(i) >= '0' && s.charAt(i) <= '9') || (i == 0 && s.charAt(i) == '-') || (s.charAt(i) == '-' && s.charAt(i - 1) == '(')) {
                //数字或负号
                String full="";
                int f = 1;
                if (s.charAt(i) == '-') {
                    f = -1;
                    i++;
                }
                for(;i<s.length();i++){
                    if((s.charAt(i) >= '0' && s.charAt(i) <= '9') || s.charAt(i) == '.')
                        full+=s.charAt(i);
                    else
                        break;
                }

                numStack.push(f * Double.parseDouble(full));


            } else {    //不是数字或负号，则为操作符或括号
                // 如果栈为空、或操作符为'('、或栈顶为'('、或当前操作符的优先级大于栈顶操作符，则操作符入栈
                if (charStack.empty() || s.charAt(i) == '(' || charStack.peek() == '(' || (s.charAt(i) != ')' && op[s.charAt(i)] > op[charStack.peek()])) {
                    if (s.charAt(i) == ')' && charStack.peek() == '(')
                    {
                        charStack.pop();  //当前符号和栈顶是一对括号则消除它们
                        if(s.charAt(i-1)=='(')                 //3+()
                            numStack.push((double)0);
                    }
                    else
                        charStack.push(s.charAt(i));
                }
                else if (s.charAt(i) == ')') {
                    // 如果当前是')',则做运算直到栈顶是'('
                    char c = charStack.peek();
                    while (c != '(') {
                        double a = numStack.peek();
                        numStack.pop();
                        double b = numStack.peek();
                        numStack.pop();
                        charStack.pop();
                        numStack.push(calc(a, b, c));
                        c = charStack.peek();
                    }
                    charStack.pop();
                }
                else {
                    // 否则，说明当前运算符优先级等于或小于栈顶运算符，将栈顶操作符取出做一次运算，将运算结果压栈，最后再将当前操作符入栈
                    double a = numStack.peek();
                    numStack.pop();
                    double b = numStack.peek();
                    numStack.pop();
                    char c = charStack.peek();
                    charStack.pop();
                    numStack.push(calc(a, b, c));
                    charStack.push(s.charAt(i));
                }
                i++;
            }
        // 表达式处理完后，不断运算直到操作符空栈，此时数据栈剩下的一个数据就是最终结果
        while(!charStack.empty()) {
            double a = numStack.peek();
            numStack.pop();
            double b = numStack.peek();
            numStack.pop();
            char c = charStack.peek();
            charStack.pop();
            numStack.push(calc(a,b,c));
        }
        return numStack.peek();

    }

    private static double calc(double x, double y, char op){
        switch (op) {
            case '+':
                return y+x;
            case '-':
                return y-x;
            case '*':
                return y*x;
            case '/':
                return y/x;
            case '^':
                return Math.pow(y,x);
        }
        return -1;
    }
}

/*
        //分出数字
        String[] numArray=s.split("[+|\\-|*|/]");
        for(int i=0;i<numArray.length;i++){
            System.out.println(numArray[i]);
        }
        //提取x+y型
        var pattern="(\\d++(\\.\\d+)?)(.)(\\d++(\\.\\d+)?)";//a+b
        Pattern p = Pattern.compile(pattern);//装入正则匹配式
        Matcher m = p.matcher(s);//匹配
        int count=0;
        while(m.find()) {
            count++;
            System.out.println("Match number "+count);
            System.out.println("start(): "+m.start());
            System.out.println("end(): "+m.end());
        }
*/
