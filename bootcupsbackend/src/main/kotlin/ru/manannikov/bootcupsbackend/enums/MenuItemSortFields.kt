package ru.manannikov.bootcupsbackend.enums

enum class MenuItemSortFields(
    override val fieldName: String
)
    : FieldEnum
{
    PRICE("menu-item-entity.sort-fields.price"),
    MAKES( "menu-item-entity.sort-fields.makes")
    ;

    override val fieldKey: String
        get() = this.name.lowercase()
}