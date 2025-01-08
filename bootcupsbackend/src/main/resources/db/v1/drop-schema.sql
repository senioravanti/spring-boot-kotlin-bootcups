DROP INDEX IF EXISTS client_bonus_cards_pk;
DROP TABLE IF EXISTS client_bonus_cards;

DROP INDEX IF EXISTS bonus_card_pk;
DROP TABLE IF EXISTS bonus_cards;

DROP INDEX IF EXISTS product_categories_pk;
DROP TABLE IF EXISTS product_categories;

DROP INDEX IF EXISTS category_pk;
DROP TABLE IF EXISTS categories;

DROP INDEX IF EXISTS includes_fk;
DROP INDEX IF EXISTS consist_fk;
DROP INDEX IF EXISTS order_item_pk;
DROP TABLE IF EXISTS order_items;

DROP INDEX IF EXISTS menu_item_price_index;
DROP INDEX IF EXISTS contains_fk;
DROP INDEX IF EXISTS stated_in_fk;
DROP INDEX IF EXISTS menu_item_pk;
DROP TABLE IF EXISTS menu_items;

DROP INDEX IF EXISTS unit_pk;
DROP TABLE IF EXISTS units;

DROP INDEX IF EXISTS product_name_index;
DROP INDEX IF EXISTS product_pk;
DROP TABLE IF EXISTS products;

DROP INDEX IF EXISTS order_total_amount_index;
DROP INDEX IF EXISTS executes_fk;
DROP INDEX IF EXISTS raise_fk;
DROP INDEX IF EXISTS order_pk;
DROP TABLE IF EXISTS orders CASCADE;

DROP INDEX IF EXISTS client_pk;
DROP TABLE IF EXISTS clients;

DROP INDEX IF EXISTS employee_fullname_index;
DROP INDEX IF EXISTS hold_fk;
DROP INDEX IF EXISTS employee_pk;
DROP TABLE IF EXISTS employees;

DROP INDEX IF EXISTS role_pk;
DROP TABLE IF EXISTS roles;