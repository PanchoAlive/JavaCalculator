package com.company;

import java.util.Scanner;

public class Main {

    private static char[] RomanSigns = new char[]{'I', 'V', 'X', 'L', 'C'};

    public static void main(String[] args) {
        // for (int i = 0; i < 101; i++)
        //   System.out.println(ArabicToRoman(i));

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        if (input.length() < 3)
            throw new UnsupportedOperationException("Wrong operation");
        System.out.println(calc(input));


    }

    private static int RomanSignToArabic(char n) {
        switch (n) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            default:
                throw new UnsupportedOperationException("Roman sign is missing");
        }
    }

    private static int GetSignIndex(char element, char[] array) {
        for (int i = 0; i < array.length; i++)
            if (array[i] == element)
                return i;

        throw new UnsupportedOperationException("Roman sign is missing");
    }


    private static int RomanNumberToArabic(String s) {
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            if ((i + 1 < s.length()) && (RomanSignToArabic(s.charAt(i)) < RomanSignToArabic(s.charAt(i + 1)))) {
                int signIndex = GetSignIndex(s.charAt(i), RomanSigns);
                int nextSignIndex = GetSignIndex(s.charAt(i + 1), RomanSigns);


                sum += RomanSignToArabic(RomanSigns[nextSignIndex]) - RomanSignToArabic(RomanSigns[signIndex]);
                i++;
            } else
                sum += RomanSignToArabic(s.charAt(i));

        }
        return sum;
    }

    private static String[] GetExpressionValues(String input) {
        String sign = "";
        String num1 = "", num2 = "";
        int part = 1;

        for (int i = 0; i < input.length(); i++) {


            if (input.charAt(i) == '+' || input.charAt(i) == '-' || input.charAt(i) == '*' || input.charAt(i) == '/') {
                sign += input.charAt(i);
                part += 1;
                continue;
            }

            if (part == 1)
                num1 += input.charAt(i);

            if (part == 2)
                num2 += input.charAt(i);


            if (part >= 3) {
                throw new UnsupportedOperationException("Invalid number of operations");
            }
        }
        return new String[]{num1, sign, num2};
    }

    private static int CountRomanNumbers(String num1, String num2) {
        int romanAmount = 0;

        try {
            Integer.parseInt(num1);
        } catch (NumberFormatException e) {
            romanAmount += 1;
        }
        try {
            Integer.parseInt(num2);
        } catch (NumberFormatException e) {
            romanAmount += 1;
        }

        return romanAmount;
    }

    public static String calc(String input) throws IndexOutOfBoundsException {


        String[] expressionValues = GetExpressionValues(input);
        String sign = expressionValues[1];
        String num1 = expressionValues[0], num2 = expressionValues[2];

        int romanAmount = CountRomanNumbers(num1, num2);
        int result = 0;

        if (romanAmount == 1) {
            throw new UnsupportedOperationException("Wrong operation");
        }

        if (romanAmount == 0) {
            if (Integer.parseInt(num1) > 10 || Integer.parseInt(num1) < 1)
                throw new IndexOutOfBoundsException("Wrong number");


            if (Integer.parseInt(num2) > 10 || Integer.parseInt(num2) < 1)
                throw new IndexOutOfBoundsException("Wrong number");


            result = switch (sign) {
                case "+" -> Integer.parseInt(num1) + Integer.parseInt(num2);
                case "-" -> Integer.parseInt(num1) - Integer.parseInt(num2);
                case "*" -> Integer.parseInt(num1) * Integer.parseInt(num2);
                case "/" -> Integer.parseInt(num1) / Integer.parseInt(num2);
                default -> 0;
            };
        }
        if (romanAmount == 2) {
            if (RomanNumberToArabic(num1) > 10 || RomanNumberToArabic(num1) < 1)
                throw new IndexOutOfBoundsException("Wrong number");
            if (RomanNumberToArabic(num2) > 10 || RomanNumberToArabic(num2) < 1)
                throw new IndexOutOfBoundsException("Wrong number");
            result = switch (sign) {
                case "+" -> RomanNumberToArabic(num1) + RomanNumberToArabic(num2);
                case "-" -> RomanNumberToArabic(num1) - RomanNumberToArabic(num2);
                case "*" -> RomanNumberToArabic(num1) * RomanNumberToArabic(num2);
                case "/" -> RomanNumberToArabic(num1) / RomanNumberToArabic(num2);
                default -> 0;
            };
            return ArabicToRoman(result);
        }

        return String.valueOf(result);
    }

    public static String ArabicToRoman(int arabicNum) {
        int d;
        int difference;
        String romanResult = "";


        for (int i = 4; i >= 0; i--) {

            if (arabicNum == 0)
                break;
            d = arabicNum / RomanSignToArabic(RomanSigns[i]);
            if (d == 0)
                continue;

            if (i != 0 && i < 4) {

                int a = arabicNum / RomanSignToArabic(RomanSigns[i - 1]) * RomanSignToArabic(RomanSigns[i - 1]);
                difference = RomanSignToArabic(RomanSigns[i + 1]) - a;


                if (difference == RomanSignToArabic(RomanSigns[i - 1]) && (RomanSignToArabic(RomanSigns[i]) / difference > 2)) {
                    romanResult += String.valueOf(RomanSigns[i - 1]) + String.valueOf(RomanSigns[i + 1]);
                    arabicNum %= (RomanSignToArabic(RomanSigns[i + 1]) - difference);
                    continue;
                }
            }

            if (d == 4)
                romanResult += String.valueOf(RomanSigns[i]) + String.valueOf(RomanSigns[i + 1]);
            else
                romanResult += String.valueOf(RomanSigns[i]).repeat(d);


            arabicNum %= RomanSignToArabic(RomanSigns[i]) * d;

        }
        return romanResult;
    }
}