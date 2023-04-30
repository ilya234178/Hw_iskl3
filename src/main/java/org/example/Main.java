package org.example;


import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Scanner;

    public class Main {
        public static void main(String[] args) {
            while (true) {
                try {
                    String[] Strings = (checkLengthData(inputData()));
                    checkFIO(Strings);
                    checkDate(Strings);
                    checkPhone(Strings);
                    checkGender(Strings);
                    writeData(Strings);
                    System.out.println(Arrays.toString(Strings));
                    break;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
        }

        public static String inputData() {
            System.out.println(
                    "Введите данные через ПРОБЕЛ в следующем формате:\n<Фамилия> <Имя> <Отчество>(В ФИО должны быть только буквы латинского алфавита не более 20 букв в каждом слове!) <Дата рождения в формате dd.mm.yyyy> <Номер телефона(только цифры до 15 символов)> <Пол: f - жен. или m - мужской>");
            String input = "";
            Scanner scanner = new Scanner(System.in);
            try {
                input = scanner.nextLine();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                scanner.close();
            }
            return input;
        }

        public static String[] checkLengthData(String input) {
            int dataLenght = 6;
            String[] result = input.split(" ");
            if (result.length > dataLenght) {
                throw new RuntimeException("Вы ввели больше 6 параметров: " + input);
            }
            if (result.length < dataLenght) {
                throw new RuntimeException("Вы ввели меньше 6 параметров: " + input);
            }
            return result;
        }

        public static boolean isAlpha(String text) {
            if (text.length() < 21) {
                return text != null && text.matches("^[a-zA-Z]*$");
            }
            return false;
        }

        public static String[] checkFIO(String[] data) {
            for (int i = 0; i < 3; i++) {
                if (!isAlpha(data[i].toString())) {
                    throw new RuntimeException(
                            "В ФИО должны быть только буквы латинского алфавита (не более 20 букв в каждом слове)! Ошибка в слове: "
                                    + data[i]);
                }
            }
            return data;
        }

        public static void checkDate(String[] data) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            try {
                LocalDate.parse(data[3], formatter);
            } catch (Exception e) {
                System.out.println("Введен некорректный формат даты: '" + data[3] + "'. Корректный: dd.mm.yyyy");
            }

        }

        public static void checkPhone(String[] data) {
            try {
                Double.parseDouble(data[4]);
            } catch (RuntimeException e) {
                System.out.println(
                        "В номере телефона должны быть только цифры, при вводе была допущена ошибка: "
                                + data[4]);
            }
            if (data[4].length() > 15) {
                throw new RuntimeException(
                        "В номере телефона должно быть не более 14 цифр, при вводе была допущена ошибка: " + data[4]);
            }
        }

        public static boolean isGender(String text) {

            if (text.equals("f") || text.equals("m")) {
                return true;
            }
            return false;
        }

        public static String[] checkGender(String[] data) {
            if (!isGender(data[5].toString())) {
                throw new RuntimeException(
                        "Пол обозначается буквами f или m! При вводе была допущена ошибка: " + data[5]);
            }
            return data;
        }

        public static void writeData(String[] data) {
            String fileName = data[0];
            StringBuilder sb = new StringBuilder();
            for (String s : data) {
                sb.append(s + " ");
            }
            String forWrite = sb.toString();
            try (FileWriter writer = new FileWriter(String.format("Java\\Exceptions\\homework3\\%s.txt", fileName),
                    true)) {
                writer.append(forWrite);
                writer.append("\n");
                System.out.println("\nВведенные данные записаны в файл: " + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}