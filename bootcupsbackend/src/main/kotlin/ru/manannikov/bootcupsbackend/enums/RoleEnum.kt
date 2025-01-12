package ru.manannikov.bootcupsbackend.enums

enum class RoleEnum(
    override val fieldName: String
) : FieldEnum {
    BARISTA("role-entity.name.barista"),
    ADMIN("role-entity.name.admin"),
    OWNER("role-entity.name.owner")
    ;

    override val fieldKey: String
        get() = this.name.lowercase()
}