package ru.manannikov.bootcupsbackend.services

import ru.manannikov.bootcupsbackend.entities.ProductEntity

interface ProductService : CrudService<ProductEntity, Short> {}