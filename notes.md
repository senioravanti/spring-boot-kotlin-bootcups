# Полезные команды для работы с git

Просмотреть текущий индекс: `git ls-files`

Найти файлы соотв. определенному шаблону: ` clear ; git ls-files | grep .DS_`

Удалить лишние файлы из индекса: `clear ; find . -name '*.DS_Store' -exec git rm --cached {} \;`