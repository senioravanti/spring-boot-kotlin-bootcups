package ru.manannikov.bootcupsbackend.enums

enum class EmployeeSortFields(
    override val fieldName: String
)
    : FieldEnum
{
    LAST_NAME("employee-entity.sort-fields.last-name"),
    FIRST_NAME("employee-entity.sort-fields.first-name"),
    MIDDLE_NAME("employee-entity.sort-fields.middle-name")
    ;

    override val fieldKey: String
        get() = this.name.lowercase()
}