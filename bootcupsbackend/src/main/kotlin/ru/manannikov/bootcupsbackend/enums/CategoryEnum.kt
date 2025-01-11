package ru.manannikov.bootcupsbackend.enums

import kotlin.enums.EnumEntries

enum class CategoryEnum(
    override val fieldName: String
)
    : FieldEnum
{
    DISHES("category-entity.fields.name.dishes"),
    BREAKFAST("category-entity.fields.name.breakfast"),

    FIRST_COURSE("category-entity.fields.name.first-course"),
    SECOND_COURSE("category-entity.fields.name.second-course"),

    SALAD("category-entity.fields.name.salad"),

    SIDE_DISH("category-entity.fields.name.side-dish"),

    BACKING("category-entity.fields.name.backing"),
    SANDWICH("category-entity.fields.name.sandwich"),
    SOURDOUGH_BREAD("category-entity.fields.name.sourdough-bread"),

    DESSERT("category-entity.fields.name.dessert"),

    DRINKS("category-entity.fields.name.drinks"),
    COFFEE("category-entity.fields.name.coffee"),
    TEA("category-entity.fields.name.tea"),
    CHICORY_DRINKS("category-entity.fields.name.chicory-drinks"),
    COMPOTE("category-entity.fields.name.compote"),

    HEALTHY_NUTRITION("category-entity.fields.name.healthy-nutrition"),
    SEASONAL_MENU("category-entity.fields.name.seasonal-menu")
    ;

    override val fieldKey: String
        get() = this.name.lowercase()
}