-- Должности сотрудников
INSERT INTO roles(
    role_key,
    role_name
)
VALUES (
    'barista',
    'role-entity.name.barista'
), (
    'admin',
    'role-entity.name.admin'
), (
    'owner',
    'role-entity.name.owner'
);

-- employess, Сотрудники, базовая таблица
INSERT INTO employees(
    role_id,
    employee_last_name,
    employee_first_name,
    employee_middle_name,
    employee_username,
    employee_password,
    employee_email,
    employee_phone_number,

    employee_photo_uri
)
VALUES (
    (
        SELECT
            role_id
        FROM
            roles
        WHERE
            role_key = 'barista'
    ), 
    'Мананников', 'Антон', 'Олегович',
    -- хеш bcypt ф-ции соотв. "raw" паролю: 12345
    'senioravanti', '$2a$10$iPOYNe2H41BLNEzBFbB7YegRb9FZG0iAvA3cKbzYFEMuDaRItrr9O', 'senioravanti@vk.com', '+7 900 600 21-14',
    DEFAULT
), (
    (
        SELECT
            role_id
        FROM roles
        WHERE role_key = 'barista'
    ), 
    'Абрамов', 'Максим', 'Алексеевич',
-- хеш bcypt ф-ции соотв. "raw" паролю: l&u0-ks
    'lurks', 
    '$2a$10$uGJknegCRoJ118loCdVCH.Rvs0/IeerXYayBcKH4ufQeRn1FGbeY.', 'lurks@gmail.com', '+996 960 450 24-64',
    DEFAULT
), (
    (
        SELECT
            role_id
        FROM roles
        WHERE role_key = 'owner'
    ), 
    'Соколов', 'Роман', 'Добрыниевич',
-- хеш bcypt ф-ции соотв. "raw" паролю: s0kol
    's0kol228', '$2a$10$QLMYZbZrte1coSA3DZuHguQJ4GXfB9Hn6AIdDSGwUPNKlvQrgxmmO', 'sokol228@gmail.com', '+996 666 450 55-35',
    DEFAULT
), (
    (
        SELECT
            role_id
        FROM roles
        WHERE
        role_key = 'admin'
    ), 
    'Шевкун', 'Роман', 'Иванович',
-- хеш bcypt ф-ции соотв. "raw" паролю: ШевкунРИ
    'sollen', '$2a$10$k6MhUfaYHwIRC/N0UFvHbucEx3AhxMoamTuonwby8mL3UV3iAx/ai', 
    's$llen@gmail.com', '+1 900 700 12-21',
    DEFAULT
), (
    (
        SELECT
            role_id
        FROM
            roles
        WHERE
            role_key = 'barista'
    ), 
    'Асадов', 'Борис', 'Измаилович',
-- хеш bcypt ф-ции соотв. "raw" паролю: sys0eye1
    'asad_syr_36', 
    '$2a$10$KBxmbWrzh8Rmc21lGRPlUO3pfKVirWKsmdSes963v8vdOZYccRpMS', 'asad0bar@vk.com', '+963 890 666 22-88',
    DEFAULT
);

-- clients, Клиенты, базовая таблица, 4 записи
INSERT INTO clients(
    client_chat_id,

    client_name,
    client_birthday,
    client_email,
    client_phone_number,
    client_registration_state
)
VALUES (
    DEFAULT,

    'Пётр',
    '2004-10-21',
    'pertus0sa@gmail.com',
    '+7 800 543 34-54',
    'REGISTERED'
), (
    DEFAULT,

    'Александр',
    '1999-12-31',
    'alexa_a@vk.com',
    '+7 999 303 23-84',
    'REGISTERED'
), (
    DEFAULT,

    'Святослав',
    '2006-09-24',
    'svyatoslav_v@gmail.com',
    '+7 999 333 55-44',
    'REGISTERED'
), (
    DEFAULT,

    'Иван',
    '2003-11-11',
    'vanyok666v@mail.ru',
    '+7 935 654 34-43',
    'REGISTERED'
);

-- Их бонусные карты
INSERT INTO bonus_cards(
    bonus_card_amount,
    bonus_card_discount_percent)
VALUES (
    65.46,
    12),
(
    100.00,
    21),
(
    34.0,
    DEFAULT),
(
    12768.00,
    DEFAULT);

-- Таблица связей: клиенты и их бонусные карты
INSERT INTO client_bonus_cards(
    bonus_card_id,
    client_id)
VALUES ((
        SELECT
            bonus_card_id
        FROM
            bonus_cards
        WHERE
            bonus_card_amount = 65.46
            AND bonus_card_discount_percent = 12),
(
            SELECT
                client_id
            FROM
                clients
            WHERE
                client_name = 'Пётр')),
((
        SELECT
            bonus_card_id
        FROM bonus_cards
        WHERE
            bonus_card_amount = 100.00
            AND bonus_card_discount_percent = 21),(
        SELECT
            client_id
        FROM clients
    WHERE
        client_name = 'Александр')),
((
    SELECT
        bonus_card_id
    FROM bonus_cards
WHERE
    bonus_card_amount = 34.0
    AND bonus_card_discount_percent = 5),(
    SELECT
        client_id
    FROM clients
WHERE
    client_name = 'Святослав')),
((
    SELECT
        bonus_card_id
    FROM bonus_cards
WHERE
    bonus_card_amount = 12768.00
    AND bonus_card_discount_percent = 5),(
    SELECT
        client_id
    FROM clients
WHERE
    client_name = 'Иван'));

-- Категории продуктов
INSERT INTO categories(
    category_key,
    category_name
)
VALUES (
    'dishes',
    'category-entity.fields.name.dishes'
), (
    'first_course',
    'category-entity.fields.name.first-course'
), (
    'breakfast',
    'category-entity.fields.name.breakfast'
), (
    'second_course',
    'category-entity.fields.name.second-course'
), (
    'salad',
    'category-entity.fields.name.salad'
), (
    'dessert',
    'category-entity.fields.name.desser'
), (
    'side_dish',
    'category-entity.fields.name.side-dish'
), (
    'sandwich',
    'category-entity.fields.name.sandwich'
), (
    'sourdough_bread',
    'category-entity.fields.name.sourdough-bread'
), (
    'backing',
    'category-entity.fields.name.backing'
), (
    'healthy_nutrition',
    'category-entity.fields.name.healthy-nutrition'
), (
    'seasonal_menu',
    'category-entity.fields.name.seasonal-menu'
), (
    'drinks',
    'category-entity.fields.name.drinks'
), (
    'coffee',
    'category-entity.fields.name.coffee'
), (
    'tea',
    'category-entity.fields.name.tea'
), (
    'chicory_drinks',
    'category-entity.fields.name.chicory-drinks'
), (
    'compote',
    'category-entity.fields.name.compote'
);

-- Ассортимент, добавить 25 записей
INSERT INTO products(
    product_name,
    product_description)
VALUES (
    'Лагман классический',
    'Это сытное, богатое вкусами блюдо, которое и насыщает, и согревает'),
(
    'Куриный суп с лапшой',
    'Горячий суп с SIDE_DISHом, в котором основным ингредиентом является лапша'),
(
    'Окорочка отварные и обжаренные',
    'Куриные окорочка -- вкусное и питательное блюдо и источник белка. Окорочка отвариваются до полуготовности, а затем обжариваются до золотистой корочки.'),
(
    'Филе куриное',
    DEFAULT),
(
    'Поджарка из свинины',
    DEFAULT),
(
    'Макароны отварные',
    DEFAULT),
(
    'Овсяная каша',
    'Приготовлена из цельнозерновых овсяных хлопьев'),
(
    'Пирог с корицей',
    'Кофейный бисквит'),
(
    'Цезарь с курицей',
    'Сытное, богатое вкусами блюдо, его придумал не древнеримский правитель, а американский повар Цезарь Кардини'),
(
    'Пончик',
    'Жаренное в масле сладкое кондитерское изделие из теста, покрытое сладкой глазурью.'),
(
    'Маффин',
    'Классический десерт, похож на хлеб, содержит, в отличие от других десертов, меньше сахара, цельнозерновую муку, овсянку и сочную чернику, украшен топпингом из корицы.'),
(
    'Круассан',
    'Классический французский десерт с румяной корочкой, готовят из слоеного теста, подают со сливочным маслом.'),
(
    'Клаб-сэндвич с бужениной',
    'Закрытый бутерброд с запеченной в духовке свининой'),
(
    'Цикорий',
    'Готовят из корня растения cichorium. По вкусу напоминает кофе, но, в отличие от последнего, не содержит кофеина'),
(
    'Флэт уайт',
    'Флэт уайт приготовлен из двойной порции экспрессо и горячего молока с небольшим количеством пены.'),
(
    'Имбирный моккачино',
    'Тёплый и уютный зимний напиток. Приготовлен на основе латте с добавлением шоколада.'),
(
    'Латте',
    'Латте, от итальянского caffellatte –– кофе с молоком. Приготовлен на основе экспрессо и всбитого парного молока.'),
(
    'Капучино',
    'Капучино –– это насыщенный кофейный напиток итальянской кухни на основе экспрессо с добавлением в него подогретого, вспененного паром молока'),
(
    'Ассамский чай',
    'Бодрящий, терпкий черный чай. Выращен в Индии, производится из растения camellia sinensis'),
(
    'Зеленый чай',
    DEFAULT),
(
    'Иван-чай',
    DEFAULT),
(
    'Сырный тост',
    'Хрустящий и ароматный сырный тост на закваске'),
(
    'Чиабатта с индейкой',
    'Сэндвич из ломтиков чиабатты и грудкой индейки'),
(
    'Колд брю',
    'Кофе холодной экстракции (заваривания), заваривается от 6 до 8 часов при температуре 52 градуса.'),
(
    'Бублик',
    'Классический, мягкий и плотный бублик, подают с сливочным сыром или сливочным маслом'),
(
    'Ржано-пшеничный хлеб «Енисей»',
    'Мягкий и ароматный хлеб на закваске'),
(
    'compote',
    'Компот из ягод –– важнейший элемент правильного питания, поддерживает баланс витаминов в организме, защищает от преждевременного старения.');

-- Категории продуктов
WITH dishes_category AS (
    SELECT
        category_id
    FROM
        categories
    WHERE
        category_key = 'dishes'
), second_course_category AS (
    SELECT
        category_id
    FROM 
        categories
    WHERE
        category_key = 'second_course'
), first_course_category AS (
    SELECT
        category_id
    FROM 
        categories
    WHERE
        category_key = 'first_course'
), breakfast_category AS (
    SELECT
        category_id
    FROM 
        categories
    WHERE
        category_key = 'breakfast'
)
INSERT INTO product_categories(
    product_id,
    category_id)
VALUES (
    (
        SELECT
            product_id
        FROM
            products
        WHERE
            product_name = 'Лагман классический'
    ),
    (SELECT category_id FROM dishes_category)
), (
    (
        SELECT
            product_id
        FROM products
        WHERE
            product_name = 'Лагман классический'
    ),
    (SELECT category_id FROM second_course_category)
), (
    (
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Куриный суп с лапшой'),
    (SELECT category_id FROM dishes_category)
), (
    (
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Куриный суп с лапшой'),
    (SELECT category_id FROM first_course_category)
), (
    (
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Окорочка отварные и обжаренные'),
    (SELECT category_id FROM dishes_category)
), ((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Окорочка отварные и обжаренные'),
    (SELECT category_id FROM first_course_category)
), ((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Макароны отварные'),
    (SELECT category_id FROM dishes_category)
), (
    (
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Макароны отварные'),
    (SELECT category_id FROM second_course_category)
), (
    (
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Овсяная каша'),
    (SELECT category_id FROM dishes_category)
), (
    (
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Овсяная каша'),
    (SELECT category_id FROM breakfast_category)
), (
    (
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Овсяная каша'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'healthy_nutrition')),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Пирог с корицей'),
    (SELECT category_id FROM dishes_category)
), ((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Пирог с корицей'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'dessert')),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Пирог с корицей'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'backing')),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Цезарь с курицей'),
    (SELECT category_id FROM dishes_category)
), (
    (
        SELECT
            product_id
        FROM
            products
        WHERE
            product_name = 'Цезарь с курицей'),(
            SELECT
                category_id
            FROM
                categories
            WHERE
                category_key = 'salad')
), ((
        SELECT
            product_id
        FROM products
        WHERE
            product_name = 'Пончик'), (SELECT category_id FROM dishes_category)
), ((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Пончик'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'dessert')),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Круассан'),(SELECT category_id FROM dishes_category)),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Круассан'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'backing')),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Клаб-сэндвич с бужениной'),(SELECT category_id FROM dishes_category)),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Клаб-сэндвич с бужениной'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'sandwich')),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Флэт уайт'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'drinks')),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Флэт уайт'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'coffee')),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Цикорий'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'drinks')),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Цикорий'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'chicory_drinks')),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Цикорий'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'healthy_nutrition')),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Имбирный моккачино'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'drinks')),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Имбирный моккачино'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'coffee')),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Латте'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'drinks')),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Латте'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'coffee')),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Капучино'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'drinks')),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Капучино'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'coffee')),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Колд брю'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'drinks')),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Колд брю'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'coffee')),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Колд брю'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'seasonal_menu')),
-- Чаи
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Ассамский чай'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'drinks')),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Ассамский чай'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'tea')),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Зеленый чай'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'drinks')),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Зеленый чай'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'tea')),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Иван-чай'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'drinks')),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Иван-чай'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'tea')),
-- Компоты
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'compote'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'drinks')),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'compote'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'compote')),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'compote'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'healthy_nutrition')),
-- Прочее
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Ржано-пшеничный хлеб «Енисей»'),(SELECT category_id FROM dishes_category)),
((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Ржано-пшеничный хлеб «Енисей»'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'backing')
), ((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Ржано-пшеничный хлеб «Енисей»'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'healthy_nutrition')
), ((
    SELECT
        product_id
    FROM products
WHERE
    product_name = 'Ржано-пшеничный хлеб «Енисей»'),(
    SELECT
        category_id
    FROM categories
WHERE
    category_key = 'sourdough_bread')
);

-- VVV
-- Таблица: Единицы измерения
INSERT INTO units(
    unit_name,
    unit_label)
VALUES (
    'Грамм',
    'г'),
(
    'Миллилитр',
    'мл');

-- Позиции меню, подчиненная таблица, добавить 25 записей
INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price,
    menu_item_topping)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'compote'), 230, 25, 'Из замороженной смородины');

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price,
    menu_item_topping)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),
        (
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'compote'), 

        360, 36, 
        'Из замороженной смородины');

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price,
    menu_item_topping)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'compote'), 230, 39, 'Из свежих яблок и клюквы');

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price,
    menu_item_topping)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'compote'), 360, 55, 'Из свежих яблок и клюквы');

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price,
    menu_item_topping)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Грамм'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Пончик'), 113, 64, 'Покрытый сладкой глазурью');

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price,
    menu_item_topping)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Грамм'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Пончик'), 113, 99, 'Сырный, на слоеном тесте');

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Грамм'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Бублик'), 108, 45);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Грамм'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Ржано-пшеничный хлеб «Енисей»'), 20, 10);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Грамм'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Ржано-пшеничный хлеб «Енисей»'), 115, 55);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Грамм'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Ржано-пшеничный хлеб «Енисей»'), 400, 250);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Грамм'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Ржано-пшеничный хлеб «Енисей»'), 800, 400);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Капучино'), 230, 308);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Капучино'), 360, 499);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Капучино'), 475, 649);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Капучино'), 592, 749);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price,
    menu_item_topping)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Имбирный моккачино'), 230, 329, 'С добавлением темного шоколада');

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price,
    menu_item_topping)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Имбирный моккачино'), 230, 329, 'С добавлением белого шоколада');

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price,
    menu_item_topping)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Имбирный моккачино'), 360, 479, 'С добавлением темного шоколада');

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price,
    menu_item_topping)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Имбирный моккачино'), 360, 499, 'С добавлением белого шоколада');

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price,
    menu_item_topping)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Имбирный моккачино'), 475, 579, 'С добавлением темного шоколада');

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price,
    menu_item_topping)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Имбирный моккачино'), 475, 599, 'С добавлением белого шоколада');

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Флэт уайт'), 230, 300);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Флэт уайт'), 360, 468);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Колд брю'), 230, 339);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Колд брю'), 360, 540);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Колд брю'), 475, 699);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Колд брю'), 592, 859);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Грамм'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Цезарь с курицей'), 170, 150);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Грамм'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Клаб-сэндвич с бужениной'), 200, 260);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Грамм'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Лагман классический'), 350, 290);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Грамм'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Маффин'), 113, 75);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Латте'), 230, 180);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Латте'), 360, 280);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Латте'), 475, 365);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Латте'), 592, 456);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Грамм'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Круассан'), 62, 50);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price,
    menu_item_topping)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Грамм'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Овсяная каша'), 89, 250, 'С сухофруктами');

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price,
    menu_item_topping)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Грамм'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Овсяная каша'), 119, 250, 'С грецкими орехами');

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price,
    menu_item_topping)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Грамм'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Пирог с корицей'), 99, 120, 'С хрустящей посыпкой из  	штрейзеля');

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Грамм'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Куриный суп с лапшой'), 129, 89);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Грамм'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Окорочка отварные и обжаренные'), 150, 175);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Грамм'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Поджарка из свинины'), 100, 149);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price,
    menu_item_topping)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Грамм'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Макароны отварные'), 150, 59, 'С томатным соусом');

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price,
    menu_item_topping)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Грамм'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Макароны отварные'), 150, 69, 'С сливочно-грибным соусом');

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price,
    menu_item_topping)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Грамм'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Макароны отварные'), 150, 84, 'С соусом карбонара');

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price,
    menu_item_topping)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Грамм'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Филе куриное'), 100, 199, 'В кунжуте');

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Грамм'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Сырный тост'), 143, 149);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price,
    menu_item_topping)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Зеленый чай'), 500, 345, 'С сушеной морковью');

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price,
    menu_item_topping)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Зеленый чай'), 900, 650, 'С сушеной морковью');

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price,
    menu_item_topping)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Зеленый чай'), 900, 750, 'С черной смородиной');

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price,
    menu_item_topping)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Иван-чай'), 900, 750, 'С сушеными листьями орешника');

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price,
    menu_item_topping)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Грамм'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Чиабатта с индейкой'), 200, 199, 'С сыром проволоне и соусом песто');

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price,
    menu_item_topping)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Грамм'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Чиабатта с индейкой'), 200, 249, 'В сыром моцарелла и красным перцем');

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Цикорий'), 360, 210);

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price,
    menu_item_topping)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Цикорий'), 360, 210, 'С молоком');

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price,
    menu_item_topping)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Цикорий'), 360, 230, 'С сгущенным молоком');

INSERT INTO menu_items(
    unit_id,
    product_id,
    menu_item_makes,
    menu_item_price,
    menu_item_topping)
VALUES ((
        SELECT
            unit_id
        FROM
            units
        WHERE
            unit_name = 'Миллилитр'),(
            SELECT
                product_id
            FROM
                products
            WHERE
                product_name = 'Цикорий'), 360, 259, 'С медом и пряностями');

-- Заказы, таблица связей, добавить 25 записей
INSERT INTO orders(
    client_id,
    employee_id,
    order_discount_amount,
    order_total_amount,
    order_created_at,
    order_status)
VALUES ((
        SELECT
            client_id
        FROM
            clients
        WHERE
            client_email = 'pertus0sa@gmail.com'),(
            SELECT
                employee_id
            FROM
                employees
            WHERE
                format('%s %s %s', employee_last_name, employee_first_name, employee_middle_name) = 'Мананников Антон Олегович'), 150.00, 465.00, CURRENT_TIMESTAMP - interval '2 weeks 1.5 hours 15 minutes', 'COMPLETED'),((
        SELECT
            client_id
        FROM clients
    WHERE
        client_email = 'alexa_a@vk.com'),(
    SELECT
        employee_id
    FROM employees
WHERE
    format('%s %s %s', employee_last_name, employee_first_name, employee_middle_name) = 'Мананников Антон Олегович'), 266.4, 867.00, CURRENT_TIMESTAMP - interval '1 year 1 month 25 minutes', 'COMPLETED'),((
        SELECT
            client_id
        FROM clients
    WHERE
        client_email = 'svyatoslav_v@gmail.com'),(
    SELECT
        employee_id
    FROM employees
WHERE
    format('%s %s %s', employee_last_name, employee_first_name, employee_middle_name) = 'Абрамов Максим Алексеевич'), 451.13, 3168.00, CURRENT_TIMESTAMP - interval '1 week 45 minutes', 'COMPLETED'),(NULL,(
            SELECT
                employee_id
            FROM
                employees
            WHERE
                format('%s %s %s', employee_last_name, employee_first_name, employee_middle_name) = 'Абрамов Максим Алексеевич'), 0.0, 3500.00, CURRENT_TIMESTAMP, 'RAISED'),(NULL,(
            SELECT
                employee_id
            FROM
                employees
            WHERE
                format('%s %s %s', employee_last_name, employee_first_name, employee_middle_name) = 'Соколов Роман Добрыниевич'), 0.0, 1848.00, CURRENT_TIMESTAMP - interval '15 minutes', 'RAISED'),(NULL,(
            SELECT
                employee_id
            FROM
                employees
            WHERE
                format('%s %s %s', employee_last_name, employee_first_name, employee_middle_name) = 'Соколов Роман Добрыниевич'), 0.0, 1916.00, CURRENT_TIMESTAMP - interval '5 minutes', 'RAISED'),((
        SELECT
            client_id
        FROM clients
    WHERE
        client_email = 'alexa_a@vk.com'),(
    SELECT
        employee_id
    FROM employees
WHERE
    format('%s %s %s', employee_last_name, employee_first_name, employee_middle_name) = 'Соколов Роман Добрыниевич'), 54.05, 843.00, CURRENT_TIMESTAMP - interval '5 minutes', 'RAISED'),((
        SELECT
            client_id
        FROM clients
    WHERE
        client_email = 'vanyok666v@mail.ru'),(
    SELECT
        employee_id
    FROM employees
WHERE
    format('%s %s %s', employee_last_name, employee_first_name, employee_middle_name) = 'Соколов Роман Добрыниевич'), 34.75, 665.00, CURRENT_TIMESTAMP - interval '1 year 1 month 45 minutes 15 seconds', 'COMPLETED'),(NULL,(
            SELECT
                employee_id
            FROM
                employees
            WHERE
                format('%s %s %s', employee_last_name, employee_first_name, employee_middle_name) = 'Соколов Роман Добрыниевич'), 0.0, 293.00, CURRENT_TIMESTAMP - interval '3 minutes 25 seconds', 'RAISED');

INSERT INTO orders(
    client_id,
    employee_id,
    order_discount_amount,
    order_total_amount,
    order_created_at)
VALUES ((
        SELECT
            client_id
        FROM
            clients
        WHERE
            client_email = 'alexa_a@vk.com'),(
            SELECT
                employee_id
            FROM
                employees
            WHERE
                format('%s %s %s', employee_last_name, employee_first_name, employee_middle_name) = 'Шевкун Роман Иванович'), 321.29, 1033.00, CURRENT_TIMESTAMP);

INSERT INTO orders(
    employee_id,
    order_discount_amount,
    order_total_amount,
    order_created_at)
VALUES ((
        SELECT
            employee_id
        FROM
            employees
        WHERE
            format('%s %s %s', employee_last_name, employee_first_name, employee_middle_name) = 'Шевкун Роман Иванович'), 0.0, 1248.00, CURRENT_TIMESTAMP);

INSERT INTO orders(
    employee_id,
    order_discount_amount,
    order_total_amount,
    order_created_at)
VALUES ((
        SELECT
            employee_id
        FROM
            employees
        WHERE
            format('%s %s %s', employee_last_name, employee_first_name, employee_middle_name) = 'Шевкун Роман Иванович'), 0.0, 1893.00, CURRENT_TIMESTAMP);

INSERT INTO orders(
    client_id,
    employee_id,
    order_discount_amount,
    order_total_amount,
    order_created_at,
    order_status)
VALUES (
    NULL,
(
        SELECT
            employee_id
        FROM
            employees
        WHERE
            format('%s %s %s', employee_last_name, employee_first_name, employee_middle_name) = 'Мананников Антон Олегович'), 0.0, 747.00, CURRENT_TIMESTAMP, 'RAISED'),((
    SELECT
        client_id
    FROM clients
    WHERE
        client_email = 'alexa_a@vk.com'),(
    SELECT
        employee_id
    FROM employees
WHERE
    format('%s %s %s', employee_last_name, employee_first_name, employee_middle_name) = 'Асадов Борис Измаилович'), 100.50, 957.00, CURRENT_TIMESTAMP - interval '1 hours', 'COMPLETED');

INSERT INTO orders(
    client_id,
    employee_id,
    order_discount_amount,
    order_total_amount,
    order_created_at,
    order_status)
VALUES ((
        SELECT
            client_id
        FROM
            clients
        WHERE
            client_email = 'vanyok666v@mail.ru'),(
            SELECT
                employee_id
            FROM
                employees
            WHERE
                format('%s %s %s', employee_last_name, employee_first_name, employee_middle_name) = 'Асадов Борис Измаилович'), 10.00, 223.00, CURRENT_TIMESTAMP - interval '6 days', 'COMPLETED');

-- Пункты заказа, подчиненная таблица, добавить 25 записей
-- содержимое 1-го заказа VVV
INSERT INTO order_items(
    order_id,
    menu_item_id,
    order_item_quantity,
    order_item_purchase)
VALUES (
    (
        SELECT
            order_id
        FROM
            orders
        WHERE
            order_discount_amount = 150.00
            AND order_total_amount = 465.00
    ),
    (
        SELECT
            menu_item_id
        FROM
            menu_items
        WHERE
                product_id =(
                    SELECT
                        product_id
                    FROM
                        products
                    WHERE
                        product_name = 'compote')
                    AND menu_item_topping = 'Из замороженной смородины'
                    AND menu_item_makes = 230),
                3,(
                    SELECT
                        menu_item_price
                    FROM menu_items
                WHERE
                    product_id =(
                        SELECT
                            product_id
                        FROM products
                    WHERE
                        product_name = 'compote')
                    AND menu_item_topping = 'Из замороженной смородины'
                    AND menu_item_makes = 230) * 3),
((
            SELECT
                order_id
            FROM orders
        WHERE
            order_discount_amount = 150.00
            AND order_total_amount = 465.00),(
        SELECT
            menu_item_id
        FROM menu_items
    WHERE
        product_id =(
            SELECT
                product_id
            FROM products
        WHERE
            product_name = 'Лагман классический')), 1,(
    SELECT
        menu_item_price
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Лагман классический')) * 1),
((
        SELECT
            order_id
        FROM orders
    WHERE
        order_discount_amount = 150.00
        AND order_total_amount = 465.00),(
    SELECT
        menu_item_id
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Круассан')), 2,(
    SELECT
        menu_item_price
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Круассан')) * 2);

-- содержимое 2-го заказа VVV
INSERT INTO order_items(
    order_id,
    menu_item_id,
    order_item_quantity,
    order_item_purchase)
VALUES ((
        SELECT
            order_id
        FROM
            orders
        WHERE
            order_discount_amount = 266.4
            AND order_total_amount = 867.00),
(
            SELECT
                menu_item_id
            FROM
                menu_items
            WHERE
                product_id =(
                    SELECT
                        product_id
                    FROM
                        products
                    WHERE
                        product_name = 'compote')
                    AND menu_item_topping = 'Из свежих яблок и клюквы'
                    AND menu_item_makes = 360),
                1,(
                    SELECT
                        menu_item_price
                    FROM menu_items
                WHERE
                    product_id =(
                        SELECT
                            product_id
                        FROM products
                    WHERE
                        product_name = 'compote')
                    AND menu_item_topping = 'Из свежих яблок и клюквы'
                    AND menu_item_makes = 360) * 1),
((
            SELECT
                order_id
            FROM orders
        WHERE
            order_discount_amount = 266.4
            AND order_total_amount = 867.00),(
        SELECT
            menu_item_id
        FROM menu_items
    WHERE
        product_id =(
            SELECT
                product_id
            FROM products
        WHERE
            product_name = 'Куриный суп с лапшой')), 2,(
    SELECT
        menu_item_price
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Куриный суп с лапшой')) * 2),
((
        SELECT
            order_id
        FROM orders
    WHERE
        order_discount_amount = 266.4
        AND order_total_amount = 867.00),(
    SELECT
        menu_item_id
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Пончик')
AND menu_item_topping = 'Покрытый сладкой глазурью'
AND menu_item_makes = 113), 3,(
    SELECT
        menu_item_price
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Пончик')
AND menu_item_topping = 'Покрытый сладкой глазурью'
AND menu_item_makes = 113) * 3),
((
    SELECT
        order_id
    FROM orders
WHERE
    order_discount_amount = 266.4
    AND order_total_amount = 867.00),(
    SELECT
        menu_item_id
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Пирог с корицей')), 1,(
    SELECT
        menu_item_price
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Пирог с корицей')) * 1);

-- содержимое 3-го заказа VVV
INSERT INTO order_items(
    order_id,
    menu_item_id,
    order_item_quantity,
    order_item_purchase)
VALUES ((
        SELECT
            order_id
        FROM
            orders
        WHERE
            order_discount_amount = 451.13
            AND order_total_amount = 3168.00),
(
            SELECT
                menu_item_id
            FROM
                menu_items
            WHERE
                product_id =(
                    SELECT
                        product_id
                    FROM
                        products
                    WHERE
                        product_name = 'Латте')
                    AND menu_item_makes = 592),
                3,(
                    SELECT
                        menu_item_price
                    FROM menu_items
                WHERE
                    product_id =(
                        SELECT
                            product_id
                        FROM products
                    WHERE
                        product_name = 'Латте')
                    AND menu_item_makes = 592) * 3),
((
            SELECT
                order_id
            FROM orders
        WHERE
            order_discount_amount = 451.13
            AND order_total_amount = 3168.00),(
        SELECT
            menu_item_id
        FROM menu_items
    WHERE
        product_id =(
            SELECT
                product_id
            FROM products
        WHERE
            product_name = 'Пирог с корицей')), 15,(
    SELECT
        menu_item_price
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Пирог с корицей')) * 15);

-- содержимое 4-го заказа VVV
INSERT INTO order_items(
    order_id,
    menu_item_id,
    order_item_quantity,
    order_item_purchase)
VALUES ((
        SELECT
            order_id
        FROM
            orders
        WHERE
            order_discount_amount = 0.0
            AND order_total_amount = 3500.00),
(
            SELECT
                menu_item_id
            FROM
                menu_items
            WHERE
                product_id =(
                    SELECT
                        product_id
                    FROM
                        products
                    WHERE
                        product_name = 'Зеленый чай')
                    AND menu_item_topping = 'С сушеной морковью'
                    AND menu_item_makes = 500),
                7,(
                    SELECT
                        menu_item_price
                    FROM menu_items
                WHERE
                    product_id =(
                        SELECT
                            product_id
                        FROM products
                    WHERE
                        product_name = 'Зеленый чай')
                    AND menu_item_topping = 'С сушеной морковью'
                    AND menu_item_makes = 500) * 7);

-- содержимое 5-го заказа VVV
INSERT INTO order_items(
    order_id,
    menu_item_id,
    order_item_quantity,
    order_item_purchase)
VALUES ((
        SELECT
            order_id
        FROM
            orders
        WHERE
            order_discount_amount = 0.0
            AND order_total_amount = 1848.00),
(
            SELECT
                menu_item_id
            FROM
                menu_items
            WHERE
                product_id =(
                    SELECT
                        product_id
                    FROM
                        products
                    WHERE
                        product_name = 'Колд брю')
                    AND menu_item_makes = 360),
                1,(
                    SELECT
                        menu_item_price
                    FROM menu_items
                WHERE
                    product_id =(
                        SELECT
                            product_id
                        FROM products
                    WHERE
                        product_name = 'Колд брю')
                    AND menu_item_makes = 360) * 1),
((
            SELECT
                order_id
            FROM orders
        WHERE
            order_discount_amount = 0.0
            AND order_total_amount = 1848.00),(
        SELECT
            menu_item_id
        FROM menu_items
    WHERE
        product_id =(
            SELECT
                product_id
            FROM products
        WHERE
            product_name = 'Куриный суп с лапшой')), 1,(
    SELECT
        menu_item_price
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Куриный суп с лапшой')) * 1),
((
        SELECT
            order_id
        FROM orders
    WHERE
        order_discount_amount = 0.0
        AND order_total_amount = 1848.00),(
    SELECT
        menu_item_id
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Клаб-сэндвич с бужениной')), 3,(
    SELECT
        menu_item_price
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Клаб-сэндвич с бужениной')) * 3),
((
    SELECT
        order_id
    FROM orders
WHERE
    order_discount_amount = 0.0
    AND order_total_amount = 1848.00),(
    SELECT
        menu_item_id
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Лагман классический')), 1,(
    SELECT
        menu_item_price
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Лагман классический')) * 1),
((
    SELECT
        order_id
    FROM orders
WHERE
    order_discount_amount = 0.0
    AND order_total_amount = 1848.00),(
    SELECT
        menu_item_id
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Поджарка из свинины')), 1,(
    SELECT
        menu_item_price
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Поджарка из свинины')) * 1);

-- содержимое 6-го заказа VVV
INSERT INTO order_items(
    order_id,
    menu_item_id,
    order_item_quantity,
    order_item_purchase)
VALUES ((
        SELECT
            order_id
        FROM
            orders
        WHERE
            order_discount_amount = 321.29
            AND order_total_amount = 1033.00),
(
            SELECT
                menu_item_id
            FROM
                menu_items
            WHERE
                product_id =(
                    SELECT
                        product_id
                    FROM
                        products
                    WHERE
                        product_name = 'Макароны отварные')
                    AND menu_item_topping = 'С соусом карбонара'),
                1,(
                    SELECT
                        menu_item_price
                    FROM menu_items
                WHERE
                    product_id =(
                        SELECT
                            product_id
                        FROM products
                    WHERE
                        product_name = 'Макароны отварные')
                    AND menu_item_topping = 'С соусом карбонара') * 1),
((
            SELECT
                order_id
            FROM orders
        WHERE
            order_discount_amount = 321.29
            AND order_total_amount = 1033.00),(
        SELECT
            menu_item_id
        FROM menu_items
    WHERE
        product_id =(
            SELECT
                product_id
            FROM products
        WHERE
            product_name = 'Филе куриное')
    AND menu_item_topping = 'В кунжуте'), 1,(
    SELECT
        menu_item_price
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Филе куриное')
AND menu_item_topping = 'В кунжуте') * 1),
((
        SELECT
            order_id
        FROM orders
    WHERE
        order_discount_amount = 321.29
        AND order_total_amount = 1033.00),(
    SELECT
        menu_item_id
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Иван-чай')
AND menu_item_topping = 'С сушеными листьями орешника'
AND menu_item_makes = 900), 1,(
    SELECT
        menu_item_price
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Иван-чай')
AND menu_item_topping = 'С сушеными листьями орешника'
AND menu_item_makes = 900) * 1);

-- содержимое 7-го заказа VVV
INSERT INTO order_items(
    order_id,
    menu_item_id,
    order_item_quantity,
    order_item_purchase)
VALUES ((
        SELECT
            order_id
        FROM
            orders
        WHERE
            order_discount_amount = 0.0
            AND order_total_amount = 1893.00),
(
            SELECT
                menu_item_id
            FROM
                menu_items
            WHERE
                product_id =(
                    SELECT
                        product_id
                    FROM
                        products
                    WHERE
                        product_name = 'Зеленый чай')
                    AND menu_item_topping = 'С черной смородиной'
                    AND menu_item_makes = 900),
                1,(
                    SELECT
                        menu_item_price
                    FROM menu_items
                WHERE
                    product_id =(
                        SELECT
                            product_id
                        FROM products
                    WHERE
                        product_name = 'Зеленый чай')
                    AND menu_item_topping = 'С черной смородиной'
                    AND menu_item_makes = 900) * 1),
((
            SELECT
                order_id
            FROM orders
        WHERE
            order_discount_amount = 0.0
            AND order_total_amount = 1893.00),(
        SELECT
            menu_item_id
        FROM menu_items
    WHERE
        product_id =(
            SELECT
                product_id
            FROM products
        WHERE
            product_name = 'Чиабатта с индейкой')
    AND menu_item_topping = 'С сыром проволоне и соусом песто'), 2,(
    SELECT
        menu_item_price
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Чиабатта с индейкой')
AND menu_item_topping = 'С сыром проволоне и соусом песто') * 2),
((
        SELECT
            order_id
        FROM orders
    WHERE
        order_discount_amount = 0.0
        AND order_total_amount = 1893.00),(
    SELECT
        menu_item_id
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Сырный тост')), 5,(
    SELECT
        menu_item_price
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Сырный тост')) * 5);

-- содержимое 8-го заказа VVV
INSERT INTO order_items(
    order_id,
    menu_item_id,
    order_item_quantity,
    order_item_purchase)
VALUES ((
        SELECT
            order_id
        FROM
            orders
        WHERE
            order_discount_amount = 0.0
            AND order_total_amount = 1248.00),
(
            SELECT
                menu_item_id
            FROM
                menu_items
            WHERE
                product_id =(
                    SELECT
                        product_id
                    FROM
                        products
                    WHERE
                        product_name = 'Цикорий')
                    AND menu_item_topping = 'С медом и пряностями'
                    AND menu_item_makes = 360),
                2,(
                    SELECT
                        menu_item_price
                    FROM menu_items
                WHERE
                    product_id =(
                        SELECT
                            product_id
                        FROM products
                    WHERE
                        product_name = 'Цикорий')
                    AND menu_item_topping = 'С медом и пряностями'
                    AND menu_item_makes = 360) * 2),
((
            SELECT
                order_id
            FROM orders
        WHERE
            order_discount_amount = 0.0
            AND order_total_amount = 1248.00),(
        SELECT
            menu_item_id
        FROM menu_items
    WHERE
        product_id =(
            SELECT
                product_id
            FROM products
        WHERE
            product_name = 'Цикорий')
    AND menu_item_topping = 'С сгущенным молоком'
    AND menu_item_makes = 360), 1,(
    SELECT
        menu_item_price
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Цикорий')
AND menu_item_topping = 'С сгущенным молоком'
AND menu_item_makes = 360) * 1),
((
        SELECT
            order_id
        FROM orders
    WHERE
        order_discount_amount = 0.0
        AND order_total_amount = 1248.00),(
    SELECT
        menu_item_id
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Овсяная каша')
AND menu_item_topping = 'С сухофруктами'), 2,(
    SELECT
        menu_item_price
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Овсяная каша')
AND menu_item_topping = 'С сухофруктами') * 2);

-- Содержимое 9-го заказа VVV
INSERT INTO order_items(
    order_id,
    menu_item_id,
    order_item_quantity,
    order_item_purchase)
VALUES ((
        SELECT
            order_id
        FROM
            orders
        WHERE
            order_discount_amount = 0.0
            AND order_total_amount = 1916.00),
(
            SELECT
                menu_item_id
            FROM
                menu_items
            WHERE
                product_id =(
                    SELECT
                        product_id
                    FROM
                        products
                    WHERE
                        product_name = 'Имбирный моккачино')
                    AND menu_item_makes = 360
                    AND menu_item_topping = 'С добавлением темного шоколада'),
                4,(
                    SELECT
                        menu_item_price
                    FROM menu_items
                WHERE
                    product_id =(
                        SELECT
                            product_id
                        FROM products
                    WHERE
                        product_name = 'Имбирный моккачино')
                    AND menu_item_makes = 360
                    AND menu_item_topping = 'С добавлением темного шоколада') * 4);

-- Содержимое 10-го заказа VVV
INSERT INTO order_items(
    order_id,
    menu_item_id,
    order_item_quantity,
    order_item_purchase)
VALUES ((
        SELECT
            order_id
        FROM
            orders
        WHERE
            order_discount_amount = 54.05
            AND order_total_amount = 843.00),
(
            SELECT
                menu_item_id
            FROM
                menu_items
            WHERE
                product_id =(
                    SELECT
                        product_id
                    FROM
                        products
                    WHERE
                        product_name = 'Флэт уайт')
                    AND menu_item_makes = 360),
                1,(
                    SELECT
                        menu_item_price
                    FROM menu_items
                WHERE
                    product_id =(
                        SELECT
                            product_id
                        FROM products
                    WHERE
                        product_name = 'Флэт уайт')
                    AND menu_item_makes = 360) * 1),
((
            SELECT
                order_id
            FROM orders
        WHERE
            order_discount_amount = 54.05
            AND order_total_amount = 843.00),(
        SELECT
            menu_item_id
        FROM menu_items
    WHERE
        product_id =(
            SELECT
                product_id
            FROM products
        WHERE
            product_name = 'Маффин')), 5,(
    SELECT
        menu_item_price
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Маффин')) * 5);

-- Содержимое 11-го заказа VVV
INSERT INTO order_items(
    order_id,
    menu_item_id,
    order_item_quantity,
    order_item_purchase)
VALUES ((
        SELECT
            order_id
        FROM
            orders
        WHERE
            order_discount_amount = 34.75
            AND order_total_amount = 665.00),
(
            SELECT
                menu_item_id
            FROM
                menu_items
            WHERE
                product_id =(
                    SELECT
                        product_id
                    FROM
                        products
                    WHERE
                        product_name = 'Цезарь с курицей')),
                2,(
                    SELECT
                        menu_item_price
                    FROM menu_items
                WHERE
                    product_id =(
                        SELECT
                            product_id
                        FROM products
                    WHERE
                        product_name = 'Цезарь с курицей')) * 2),
((
            SELECT
                order_id
            FROM orders
        WHERE
            order_discount_amount = 34.75
            AND order_total_amount = 665.00),(
        SELECT
            menu_item_id
        FROM menu_items
    WHERE
        product_id =(
            SELECT
                product_id
            FROM products
        WHERE
            product_name = 'Латте')
    AND menu_item_makes = 475), 1,(
    SELECT
        menu_item_price
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Латте')
AND menu_item_makes = 475) * 1),
((
        SELECT
            order_id
        FROM orders
    WHERE
        order_discount_amount = 34.75
        AND order_total_amount = 665.00),(
    SELECT
        menu_item_id
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Макароны отварные')
AND menu_item_topping = 'С томатным соусом'), 2,(
    SELECT
        menu_item_price
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Макароны отварные')
AND menu_item_topping = 'С томатным соусом') * 2),
((
    SELECT
        order_id
    FROM orders
WHERE
    order_discount_amount = 34.75
    AND order_total_amount = 665.00),(
    SELECT
        menu_item_id
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Окорочка отварные и обжаренные')), 1,(
    SELECT
        menu_item_price
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Окорочка отварные и обжаренные')) * 1);

-- Содержимое 12-го заказа VVV
INSERT INTO order_items(
    order_id,
    menu_item_id,
    order_item_quantity,
    order_item_purchase)
VALUES ((
        SELECT
            order_id
        FROM
            orders
        WHERE
            order_discount_amount = 0.0
            AND order_total_amount = 747.00),
(
            SELECT
                menu_item_id
            FROM
                menu_items
            WHERE
                product_id =(
                    SELECT
                        product_id
                    FROM
                        products
                    WHERE
                        product_name = 'Чиабатта с индейкой')
                    AND menu_item_topping = 'В сыром моцарелла и красным перцем'),
                3,(
                    SELECT
                        menu_item_price
                    FROM menu_items
                WHERE
                    product_id =(
                        SELECT
                            product_id
                        FROM products
                    WHERE
                        product_name = 'Чиабатта с индейкой')
                    AND menu_item_topping = 'В сыром моцарелла и красным перцем') * 3);

-- Содержимое 13-го заказа VVV
INSERT INTO order_items(
    order_id,
    menu_item_id,
    order_item_quantity,
    order_item_purchase)
VALUES ((
        SELECT
            order_id
        FROM
            orders
        WHERE
            order_discount_amount = 100.50
            AND order_total_amount = 957.00),
(
            SELECT
                menu_item_id
            FROM
                menu_items
            WHERE
                product_id =(
                    SELECT
                        product_id
                    FROM
                        products
                    WHERE
                        product_name = 'Зеленый чай')
                    AND menu_item_topping = 'С черной смородиной'
                    AND menu_item_makes = 900),
                1,(
                    SELECT
                        menu_item_price
                    FROM menu_items
                WHERE
                    product_id =(
                        SELECT
                            product_id
                        FROM products
                    WHERE
                        product_name = 'Зеленый чай')
                    AND menu_item_topping = 'С черной смородиной'
                    AND menu_item_makes = 900) * 1),
((
            SELECT
                order_id
            FROM orders
        WHERE
            order_discount_amount = 100.50
            AND order_total_amount = 957.00),(
        SELECT
            menu_item_id
        FROM menu_items
    WHERE
        product_id =(
            SELECT
                product_id
            FROM products
        WHERE
            product_name = 'Макароны отварные')
    AND menu_item_topping = 'С сливочно-грибным соусом'), 3,(
    SELECT
        menu_item_price
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Макароны отварные')
AND menu_item_topping = 'С сливочно-грибным соусом') * 3);

-- Содержимое 14-го заказа VVV
INSERT INTO order_items(
    order_id,
    menu_item_id,
    order_item_quantity,
    order_item_purchase)
VALUES ((
        SELECT
            order_id
        FROM
            orders
        WHERE
            order_discount_amount = 10.00
            AND order_total_amount = 223.00),
(
            SELECT
                menu_item_id
            FROM
                menu_items
            WHERE
                product_id =(
                    SELECT
                        product_id
                    FROM
                        products
                    WHERE
                        product_name = 'compote')
                    AND menu_item_topping = 'Из свежих яблок и клюквы'
                    AND menu_item_makes = 360),
                2,(
                    SELECT
                        menu_item_price
                    FROM menu_items
                WHERE
                    product_id =(
                        SELECT
                            product_id
                        FROM products
                    WHERE
                        product_name = 'compote')
                    AND menu_item_topping = 'Из свежих яблок и клюквы'
                    AND menu_item_makes = 360) * 2),
((
            SELECT
                order_id
            FROM orders
        WHERE
            order_discount_amount = 10.00
            AND order_total_amount = 223.00),(
        SELECT
            menu_item_id
        FROM menu_items
    WHERE
        product_id =(
            SELECT
                product_id
            FROM products
        WHERE
            product_name = 'Пончик')
    AND menu_item_topping = 'Сырный, на слоеном тесте'), 1,(
    SELECT
        menu_item_price
    FROM menu_items
WHERE
    product_id =(
        SELECT
            product_id
        FROM products
    WHERE
        product_name = 'Пончик')
AND menu_item_topping = 'Сырный, на слоеном тесте') * 1);

