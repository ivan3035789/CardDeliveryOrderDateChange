package ru.netology.web;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Generator {


    private Generator() {
    }
    private static final Faker faker = new Faker(new Locale("ru"));


    public static String validCity() {

        String[] validCity = {"Майкоп", "Горно-Алтайск", "Уфа", "Улан-Удэ", "Махачкала", "Нальчик", "Элиста",
                "Черкесск", "Петрозаводск", "Симферополь", "Йошкар-Ола", "Саранск", "Якутск", "Владикавказ", "Казань",
                "Кызыл", "Ижевск", "Абакан", "Грозный", "Чебоксары", "Барнаул", "Чита", "Петропавловск-Камчатский",
                "Краснодар", "Красноярск", "Пермь", "Владивосток", "Ставрополь", "Хабаровск", "Благовещенск", "Архангельск",
                "Астрахань", "Белгород", "Брянск", "Владимир", "Волгоград", "Вологда", "Воронеж", "Иваново", "Иркутск",
                "Калининград", "Калуга", "Кемерово", "Киров", "Кострома", "Курган", "Курск", "Липецк", "Магадан", "Красногорск",
                "Мурманск", "Нижний Новгород", "Великий Новгород", "Новосибирск", "Омск", "Оренбург", "Орёл", "Пенза", "Псков",
                "Ростов-на-Дону", "Рязань", "Самара", "Саратов", "Южно-Сахалинск", "Екатеринбург", "Смоленск", "Тамбов",
                "Тверь", "Томск", "Тула", "Тюмень", "Ульяновск", "Челябинск", "Ярославль", "Москва", "Санкт-Петербург",
                "Севастополь", "Биробиджан", "Нарьян-Мар", "Ханты-Мансийск", "Анадырь", "Салехард", "Магас"};

        Random randomCity = new Random();
        return validCity[randomCity.nextInt(validCity.length)];
    }

    public static String invalidCity() {
        String city = faker.address().country();
        String[] invalidCity = {city, "Moscow", "Чехов", "1234567890", "!@#$$%%%^^^&", "Южно" + invalidCharacters() + "Сахалинск",
                "Липецк" + invalidCharacters(), "Иван Иваныч", "Берёза", "_________"};
        Random randomValidName = new Random();
        return invalidCity[randomValidName.nextInt(invalidCity.length)];
    }

    public static String name() {
        String nameLast = faker.name().lastName().replace("ё", "е");
        String nameFirst = faker.name().firstName().replace("ё", "е");
        String name1 = nameLast + " " + nameFirst;
        String name2 = nameLast + "-" + nameLast + " " + nameFirst;
        String name3 = nameLast.toLowerCase() + " " + nameFirst.toLowerCase();
        String name4 = nameLast.toUpperCase() + " " + nameFirst.toUpperCase();
        String[] validName = {name1, name2, name3, name4};

        Random randomValidName = new Random();
        return validName[randomValidName.nextInt(validName.length)];
    }

    public static String invalidName() {
        String nameFirst = faker.name().firstName();
        String nameLast = faker.name().lastName();
        String name1 = nameLast + " " + invalidCharacters() + " " + nameFirst;
        String name2 = invalidCharacters() + nameLast + " " + nameFirst;
        String name4 = nameLast + invalidCharacters() + nameLast + " " + nameFirst;
        String name3 = nameLast + nameFirst + invalidCharacters();
        String[] invalidName = {name1, name2, name3, name4, "Волкова Алёна", "Артёмов Роман", "Ёжиков Иван", "Ёлка Владимер",
                "1234567890", "Ivanov Ivan", invalidCharacters() + "Иванов иван"};

        Random randomName = new Random();
        return invalidName[randomName.nextInt(invalidName.length)];
    }

    public static String zero() {
        return "";
    }

    private static String invalidCharacters() {
        String[] invalid = {".", "@", "_", "#", "$", "%", "^", "&", "/", ".", ",", ")", "(", "*", "1", "=", "",
                ",", "?", "|", "|", "q", "w", "e", "r", "t", "y", "u", "1", "2", "3", "4", "5"};
        Random randomInvalid = new Random();
        return invalid[randomInvalid.nextInt(invalid.length)];
    }

    public static String data() {
        LocalDate startDate = LocalDate.now().plusDays(3);
        long start = startDate.getDayOfMonth();
        LocalDate endDate = LocalDate.now().plusYears(1);
        long end = endDate.lengthOfYear();
        long randomEpochDay = ThreadLocalRandom.current().longs(start, end).findAny().getAsLong();
        return startDate.plusDays(randomEpochDay).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String invalidData() {
        String invalidData1 = faker.date().toString();
        String invalidData2 = invalidData1.replace("", "33");
        Random randomInvalidData = new Random();
        String[] invalidData = {invalidData2};
        return invalidData[randomInvalidData.nextInt(invalidData.length)];
    }

    //update
    public static String invalidDataNow() {
        String startDate = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return startDate;

    }

    public static String phone() {
        return faker.phoneNumber().phoneNumber();
    }

    public static String invalidPhone() {
        String invalidPhone1 = faker.phoneNumber().phoneNumber();
        String invalidPhone2 = invalidPhone1.replace("+", invalidCharacters());
        String invalidPhone3 = invalidPhone1.replace("7", invalidCharacters());
        String invalidPhone4 = invalidPhone1.replace("7", "8");
        String invalidPhone5 = faker.phoneNumber().subscriberNumber(5);
        String invalidPhone6 = faker.phoneNumber().subscriberNumber(12);
        String[] invalidPhone = {invalidPhone1, invalidPhone2, invalidPhone3, invalidPhone4,
                invalidPhone5, invalidPhone6};
        Random randomInvalidPhone = new Random();
        return invalidPhone[randomInvalidPhone.nextInt(invalidPhone.length)];

    }
}
