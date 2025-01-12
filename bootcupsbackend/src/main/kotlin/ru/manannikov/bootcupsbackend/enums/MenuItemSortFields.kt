package ru.manannikov.bootcupsbackend.enums

enum class MenuItemSortFields(
    override val fieldName: String
)
    : FieldEnum
{
    PRICE("menu-item-entity.fields.price"),
    MAKES( "menu-item-entity.fields.makes")
    ;

    override val fieldKey: String
        get() = this.name.lowercase()
}