package ru.manannikov.bootcupsbackend.enums

enum class Category(val categoryName: String) {
    DISHES("Блюда"),
    BREAKFAST("Блюда:Завтраки"),

    FIRST_COURSE("Блюда:Первое"),
    SECOND_COURSE("Блюда:Второе"),

    SALAD("Блюда:Салаты"),

    SIDE_DISH("Блюда:Гарнир"),

    BACKING("Блюда:Выпечка"),
    SANDWICH("Блюда:Выпечка:Сэндвичи"),
    SOURDOUGH_BREAD("Блюда:Выпечка:Хлеб на закваске"),

    DESSERT("Блюда:Десерты"),

    DRINKS("Напитки"),
    COFFEE("Напитки:Кофе"),
    TEA("Напитки:Чай"),
    CHICORY_DRINKS("Напитки:Напитки из цикория"),
    COMPOTE("Напитки:Компот"),

    HEALTHY_NUTRITION("Здоровое питание"),
    SEASONAL_MENU("Сезонное меню")
    ;
}