package ru.manannikov.bootcupsbackend.enums

enum class Categories(name: String) {
    DISHES("Блюда"),
    FIRST_COURSE("Блюда:Первое"),
    SECOND_COURSE("Блюда:Второе"),
    SIDE_DISH("Блюда:Гарнир"),
    BACKING("Блюда:Выпечка"),
    SANDWICH("Блюда:Выпечка:Сэндвичи"),

    DRINKS("Напитки"),
    COFFEE("Напитки:Кофе"),
    TEA("Напитки:Чай"),
    CHICORY_DRINKS("Напитки:Напитки из цикория"),
    COMPOTE("Напитки:Компот"),

    HEALTHY_NUTRITION("Здоровое питание"),
    SEASONAL_MENU("Сезонное меню")
    ;
}