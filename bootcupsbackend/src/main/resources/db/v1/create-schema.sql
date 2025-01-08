/*==============================================================*/
/* table: units                                                  */
/*==============================================================*/
CREATE TABLE units (
    unit_id smallserial PRIMARY KEY,

    unit_name varchar(32) NOT NULL UNIQUE,
    unit_label varchar(6) NOT NULL UNIQUE
);

-- индекс первичного ключа таблицы units
CREATE UNIQUE INDEX unit_pk ON units(unit_id);

/*==============================================================*/
/* table: clients                                                */
/*==============================================================*/
CREATE TABLE clients(
    client_id bigserial PRIMARY KEY,

    client_name varchar(128) NOT NULL,
    client_birthday date DEFAULT NULL,
    client_email varchar(100) DEFAULT NULL UNIQUE,
    client_phone_number varchar(18) DEFAULT NULL UNIQUE,
    client_registration_state varchar(12) NOT NULL DEFAULT 'ASK_REGISTRATION'
);

-- индекс первичного ключа таблицы clients
CREATE UNIQUE INDEX client_pk ON clients(client_id);


/*==============================================================*/
/* table: bonus_cards                                            */
/*==============================================================*/
CREATE TABLE bonus_cards(
    bonus_card_id bigserial PRIMARY KEY,
    bonus_card_amount decimal(8, 2) NOT NULL DEFAULT 150.00,
    bonus_card_discount_percent smallint NOT NULL DEFAULT 5
);

-- индекс первичного ключа таблицы bonus_cards
CREATE UNIQUE INDEX bonus_card_pk ON bonus_cards(bonus_card_id);

/*==============================================================*/
/* table: client_bonus_card                                     */
/*==============================================================*/
CREATE TABLE client_bonus_cards (
    bonus_card_id bigint NOT NULL,
    client_id bigint NOT NULL,
    
    CONSTRAINT pk_bonus_card PRIMARY KEY (client_id, bonus_card_id)
);

CREATE UNIQUE INDEX client_bonus_cards_pk ON client_bonus_cards(bonus_card_id, client_id);

ALTER TABLE client_bonus_cards
    ADD CONSTRAINT FK_BK_ATTACHED_CLIENT FOREIGN KEY (bonus_card_id) REFERENCES bonus_cards(bonus_card_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE client_bonus_cards
    ADD CONSTRAINT FK_BK_ISSUED_CLIENT FOREIGN KEY (client_id) REFERENCES clients(client_id) ON DELETE CASCADE ON UPDATE RESTRICT;


/*==============================================================*/
/* table: roles                                                  */
/*==============================================================*/
CREATE TABLE roles(
    role_id smallserial NOT NULL,
    role_name varchar(255) NOT NULL UNIQUE,
    CONSTRAINT pk_role PRIMARY KEY (role_id)
);

-- индекс первичного ключа таблицы roles
CREATE UNIQUE INDEX role_pk ON roles(role_id);


/*==============================================================*/
/* table: employees                                              */
/*==============================================================*/
CREATE TABLE employees(
    employee_id serial PRIMARY KEY,
    role_id int2 NOT NULL,
    employee_last_name varchar(64) NOT NULL,
    employee_first_name varchar(32) NOT NULL,
    employee_middle_name varchar(36) DEFAULT NULL,
    employee_username varchar(128) NOT NULL,
    employee_password varchar(100) NOT NULL,
    employee_email varchar(100) NOT NULL UNIQUE,
    employee_phone_number varchar(18) NOT NULL UNIQUE
);

-- индекс первичного ключа таблицы employees
CREATE UNIQUE INDEX employee_pk ON employees(employee_id);

-- индекс столбца role_id таблицы employees, сод. внешние ключи, соотв. первичным ключам таблицы roles
CREATE INDEX hold_fk ON employees(role_id);

-- multicolumn index. индекс, сост. из столбцов employee_first_name, employee_middle_name и employee_last_name таблицы employees
CREATE INDEX employee_fullname_index ON employees(employee_last_name, employee_first_name, employee_middle_name);

ALTER TABLE employees
    ADD CONSTRAINT fk_employee_role FOREIGN KEY (role_id) REFERENCES roles(role_id) ON DELETE RESTRICT ON UPDATE RESTRICT;


/*==============================================================*/
/* table: categories                                              */
/*==============================================================*/
CREATE TABLE categories(
    category_id smallserial PRIMARY KEY,
    category_name varchar(255) NOT NULL UNIQUE
);

-- индекс первичного ключа таблицы categories
CREATE UNIQUE INDEX category_pk ON categories(category_id);


/*==============================================================*/
/* table: products (ассортимент кофейни)                        */
/*==============================================================*/
CREATE TABLE products(
    product_id smallserial PRIMARY KEY,
    product_name varchar(512) NOT NULL UNIQUE,
    product_description text DEFAULT NULL
);

-- индекс первичного ключа таблицы products
CREATE UNIQUE INDEX product_pk ON products(product_id);

-- индекс столбца product_name таблицы products
CREATE UNIQUE INDEX product_name_index ON products(product_name);


/*==============================================================*/
/* Table: product_categories                                    */
/*==============================================================*/
CREATE TABLE product_categories (
    product_id int2 NOT NULL,
    category_id int2 NOT NULL,

    CONSTRAINT PK_PRODUCT_CATEGORIES PRIMARY KEY (product_id, category_id)
);

CREATE UNIQUE INDEX product_categories_pk ON product_categories(product_id, category_id);

ALTER TABLE product_categories
    ADD CONSTRAINT FK_PRODUCT_BELONGS_CATEGORY FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

ALTER TABLE product_categories
    ADD CONSTRAINT FK_CATEGORY_CLASSIFIES_PRODUCT FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE SET NULL ON UPDATE RESTRICT;


/*==============================================================*/
/* table: menu_items                                             */
/*==============================================================*/
CREATE TABLE menu_items(
    menu_item_id serial PRIMARY KEY,

    unit_id int2 NOT NULL,
    product_id int2 NOT NULL,

    menu_item_topping varchar(128) DEFAULT NULL,
    menu_item_makes int2 NOT NULL,
    menu_item_price decimal(8, 2) NOT NULL,

    menu_item_image_uri varchar(1024) UNIQUE DEFAULT NULL
);

-- индекс первичного ключа таблицы menu_items
CREATE UNIQUE INDEX menu_item_pk ON menu_items(menu_item_id);

-- индекс столбца unit_id таблицы menu_items, сод. внешние ключи, соотв. первичным ключам таблицы units
CREATE INDEX stated_in_fk ON menu_items(unit_id);

-- индекс столбца product_id таблицы menu_items, сод. внешние ключи, соотв. первичным ключам таблицы products
CREATE INDEX contains_fk ON menu_items(product_id);

-- индекс столбца menu_item_price таблицы menu_items
CREATE INDEX menu_item_price_index ON menu_items(menu_item_price);

ALTER TABLE menu_items
    ADD CONSTRAINT fk_menu_item_product FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE menu_items
    ADD CONSTRAINT fk_menu_item_unit FOREIGN KEY (unit_id) REFERENCES units(unit_id) ON DELETE RESTRICT ON UPDATE RESTRICT;


/*==============================================================*/
/* table: orders                                               */
/*==============================================================*/
CREATE TABLE orders(
    order_id serial PRIMARY KEY,
    client_id bigint DEFAULT NULL,
    employee_id int4 NOT NULL,
    -- Сумма скидки
    order_discount_amount decimal(8, 2) NOT NULL,
    -- Общая стоимость заказа
    order_total_amount decimal(10, 2) NOT NULL,
    -- Instant в Java
    order_created_at timestamp with time zone NOT NULL,
    order_status varchar(16) NOT NULL DEFAULT 'RAISED'
);

-- индекс первичного ключа таблицы orders
CREATE UNIQUE INDEX order_pk ON orders(order_id);

-- индекс столбца client_id таблицы orders, сод. внешние ключи, соотв. первичным ключам таблицы clients
CREATE INDEX raise_fk ON orders(client_id);

-- индекс столбца employee_id таблицы orders, сод. внешние ключи, соотв. первичным ключам таблицы employees
CREATE INDEX executes_fk ON orders(employee_id);

-- индекс столбца order_total_amount таблицы orders
CREATE INDEX order_total_amount_index ON orders(order_total_amount);

ALTER TABLE orders
    ADD CONSTRAINT fk_order_client FOREIGN KEY (client_id) REFERENCES clients(client_id) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE orders
    ADD CONSTRAINT fk_order_employee FOREIGN KEY (employee_id) REFERENCES employees(employee_id) ON DELETE RESTRICT ON UPDATE RESTRICT;

    
/*==============================================================*/
/* table: order_items                                            */
/*==============================================================*/
CREATE TABLE order_items(
    order_item_id bigserial PRIMARY KEY,

    order_id int4 NOT NULL,
    menu_item_id int4 NOT NULL,
    
    order_item_quantity int2 NOT NULL,
    order_item_purchase decimal(10, 2) NOT NULL
);

-- индекс первичного ключа таблицы order_items
CREATE UNIQUE INDEX order_item_pk ON order_items(order_item_id);

-- индекс столбца order_id таблицы order_items, сод. внешние ключи, соотв. первичным ключам таблицы orders
CREATE INDEX consist_fk ON order_items(order_id);

-- индекс столбца menu_item_id таблицы order_items, сод. внешние ключи, соотв. первичным ключам таблицы menu_items
CREATE INDEX includes_fk ON order_items(menu_item_id);

ALTER TABLE order_items
    ADD CONSTRAINT fk_order_order_item FOREIGN KEY (order_id) REFERENCES orders(order_id) ON DELETE CASCADE ON UPDATE RESTRICT;

ALTER TABLE order_items
    ADD CONSTRAINT fk_order_item_menu_item FOREIGN KEY (menu_item_id) REFERENCES menu_items(menu_item_id) ON DELETE RESTRICT ON UPDATE RESTRICT;