import java.util.Scanner;

public class Calculator {

    public static void main(String[] args) {
        Calculator calc = new Calculator();
        Scanner scanner = new Scanner(System.in);
        String inputString = new String("");

        while (true) {
            inputString = scanner.nextLine();
            System.out.println(calc.evaluateExpression(inputString));
        }

    }

    public String evaluateExpression(String inputString) {

        int result = 0;
        inputString = inputString.replaceAll("\\s", "");
        int operationType = StringRecognizer.parseString(inputString);
        if (operationType != 0) {
            char operation = StringRecognizer.getOperation(inputString);
            int[] arrNumbers = StringRecognizer.getNumbers(inputString);

            switch (operation) {
                case '+':
                    result = addition(arrNumbers[0], arrNumbers[1]);
                    break;
                case '-':
                    result = subtraction(arrNumbers[0], arrNumbers[1]);
                    break;
                case '*':
                    result = multiplication(arrNumbers[0], arrNumbers[1]);
                    break;
                case '/':
                    result = division(arrNumbers[0], arrNumbers[1]);
                    break;
            }
        } else {
            throw new NullPointerException("Некорректная строка");
        }

        if(operationType == 2 && result < 1){
            throw new NullPointerException("Римское число меньше единицы");
        }

        if (operationType == 2){
            return RomanArabicNumeralsConverter.convertArabicToRoman(result);
        }
        return Integer.toString(result);

    }




    public int addition (int a, int b){
        return a + b;
    }

    public int subtraction (int a, int b){
        return a - b;
    }

    public int multiplication (int a, int b){
        return a * b;
    }

    public int division (int a, int b){
        return a / b;
    }

}

class RomanArabicNumeralsConverter {


    public static String arabicToRomanNumbers(int n, String one, String five, String ten){

        if(n >= 1)
        {
            if(n == 1)
            {
                return one;
            }
            else if (n == 2)
            {
                return one + one;
            }
            else if (n == 3)
            {
                return one + one + one;
            }
            else if (n==4)
            {
                return one + five;
            }
            else if (n == 5)
            {
                return five;
            }
            else if (n == 6)
            {
                return five + one;
            }
            else if (n == 7)
            {
                return five + one + one;
            }
            else if (n == 8)
            {
                return five + one + one + one;
            }
            else if (n == 9)
            {
                return one + ten;
            }

        }
        return "";
    }

    public static String[] getArrayRomanNumerals(){
        String[] arrRomanNumerals = new String[102];
        for(int i =1; i < arrRomanNumerals.length; i++){
            String s = RomanArabicNumeralsConverter.convertArabicToRomanNumbers(i);
            arrRomanNumerals[i] = s;
        }

        return arrRomanNumerals;
    }

    public static String convertArabicToRomanNumbers(int number){

        String romanOnes = arabicToRomanNumbers( number%10, "I", "V", "X");
        number /=10;
        String romanTens = arabicToRomanNumbers( number%10, "X", "L", "C");
        number /=10;
        String romanHundreds = arabicToRomanNumbers(number%10, "C", "D", "M");

        String result = romanHundreds + romanTens + romanOnes;
        return result;

    }

    public static int convertRomanToArabic(String romanNumeral){
        String[] arrRomanNumerals = RomanArabicNumeralsConverter.getArrayRomanNumerals();
        int arabicNumber = -1;
        for (int i = 1; i < arrRomanNumerals.length; i++){
            if (arrRomanNumerals[i].equals(romanNumeral)){
                arabicNumber = i;
                break;
            }
        }
        return arabicNumber;
    }

    public static String convertArabicToRoman(int number){
        String[] arrRomanNumerals = RomanArabicNumeralsConverter.getArrayRomanNumerals();
        String romanNumber = arrRomanNumerals[number];
        return romanNumber;
    }
}
class StringRecognizer{

    public static int parseString(String inputString){

        if (inputString.matches("^\\d{1,2}[-+*\\/]\\d{1,2}")) {
            String[] aNumbers = inputString.split("[-*+\\/]");
            int firstNumber = Integer.parseInt(aNumbers[0]);
            int secondNumber = Integer.parseInt(aNumbers[1]);
            if (firstNumber > 10 || secondNumber > 10){
                return 0;
            }
            return 1;
        }else if((inputString.matches("^\\D{1,5}[-+*\\/]\\D{1,5}"))){
            String[] rNumbers = inputString.split("[-*+\\/]");
            int firstNumber = RomanArabicNumeralsConverter.convertRomanToArabic(rNumbers[0]);
            int secondNumber = RomanArabicNumeralsConverter.convertRomanToArabic(rNumbers[1]);
            if (firstNumber < 0 || firstNumber > 10 || secondNumber < 0 || secondNumber > 10){
                return 0;
            }
            return 2;
        }else
            return 0;
    }

    public static char getOperation(String inputString){

        int index = 0;
        String operations = "-+*/";
        for (int i = 0; i < operations.length(); i++){
            index = inputString.indexOf(operations.charAt(i));
            if (index != -1){
                break;
            }
        }

        return inputString.charAt(index);

    }

    public static int[] getNumbers(String inputString){

        int[] arrInt = new int[2];
        String[] arrString = inputString.split("[-*+\\/]");
        if (parseString(inputString) == 1){
            arrInt[0] = Integer.parseInt(arrString[0]);
            arrInt[1] = Integer.parseInt(arrString[1]);
        }

        if (parseString(inputString) == 2){
            arrInt[0] = RomanArabicNumeralsConverter.convertRomanToArabic(arrString[0]);
            arrInt[1] = RomanArabicNumeralsConverter.convertRomanToArabic(arrString[1]);
        }

        return arrInt;

    }

}