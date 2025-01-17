<script lang="ts">
import { defineComponent } from 'vue';

import MenuItemTable from './MenuItemTable.vue';
import OrdersTable from './OrdersTable.vue';
import EmployeeTable from './EmployeeTable.vue';

import type { AxiosResponse } from 'axios';

import Pagination from './Pagination.vue';

import RestClient from '../services/RestClient';

import {MENU_ENDPOINT, ORDERS_ENDPOINT, EMPOYEES_ENDPOINT, PAGE_SIZE} from '../utils/Constants';

export default defineComponent({
  name: 'Table',
  components: {
    MenuItemTable,
    OrdersTable,
    EmployeeTable,
    
    Pagination,
  },
  data() {
    // Возвращает реактивный объект, который можно использовать в шаблоне и методах
    return {
      restClient: new RestClient(),
      pageResponse: [],

      
      endpoint: `/${MENU_ENDPOINT}/`,
      method: 'get',

      currentTable: null,

      isShowActions: false,

      menuItemsTable: MENU_ENDPOINT,
      ordersTable: ORDERS_ENDPOINT,
      employeeTable: EMPOYEES_ENDPOINT
    }
  },
  computed: {
       
  },
  methods: {
    loadDataFromBackend(
      pageNumber: number, 
      endpoint: string,
      method: string = 'get',
      pageSize: number = PAGE_SIZE
    ) {
      console.log('endpoint: ', endpoint);

      let response: Promise<AxiosResponse<any, any> | null> | null = null;

      if (method == 'get') {
        response = this.restClient
        .findAllMenuItems(
          endpoint,

          pageNumber, pageSize
        );  
      }

      if (response != null) {
        response.then(
          r => {
            this.pageResponse = r?.data ?? []; 
            this.endpoint = endpoint;
          
            console.log(
              'rest data:\nendpoint: ', 
            
              this.pageResponse, 
              this.endpoint
            ); 
          }
        );  
      } else {
        console.log('Передан недопустымый метод', method);
      }
    },

    onPageNumberChanged() {
      this.loadDataFromBackend(
        this.$refs.pagination.pageNumber,
        this.endpoint,
        this.method
      );
    },

    updateCurrentTable() {      
      if (this.endpoint.includes(MENU_ENDPOINT)) {
          this.currentTable = this.$refs.menuItemTable;
      } else if (this.endpoint.includes(ORDERS_ENDPOINT)) {
        this.currentTable = this.$refs.ordersTable;
      } else if (this.endpoint.includes(EMPOYEES_ENDPOINT)) {
        this.currentTable = this.$refs.employeeTable;
      } else {
        console.log('Задано некорректное название таблицы');
      }
    },

    onCancelEditItems() {
      this.updateCurrentTable();
      this.currentTable.isShowActions = false;
      this.isShowActions = false;

      this.$refs.editItemsBtn.removeAttribute('disabled');
      this.$refs.editItemsBtn.classList.remove('btn-danger');

      this.$refs.editItemsBtn.classList.add('btn-info');
      this.$refs.editItemsBtn.classList.add('btn-fixed-width');
    
      this.$refs.editItemsBtn.innerText = 'Редактировать';
    },

    onChangeEditItemsButtonState() {
      this.updateCurrentTable();
      if (this.currentTable.checkedCheckboxesCount === 0) {
        this.$refs.editItemsBtn.setAttribute('disabled', 'disabled');
      } else {
        this.$refs.editItemsBtn.removeAttribute('disabled');
      }
    },

    onEditItems(event: Event) {
      this.onChangeEditItemsButtonState();

      this.updateCurrentTable();
      this.currentTable.isShowActions = true;
      this.isShowActions = true;

      this.$refs.editItemsBtn.classList.remove('btn-info');
      this.$refs.editItemsBtn.classList.remove('btn-fixed-width');
    

      this.$refs.editItemsBtn.classList.add('btn-danger');
      
      this.$refs.editItemsBtn.innerText = 'Удалить выбранные';
    }
  },
  mounted() {
    console.log('mounted: endpoint: ', this.endpoint);
    this.loadDataFromBackend(
      0,
      this.endpoint,
      this.method
    );
    this.onCancelEditItems();
  }
})
</script>

<template>
  <div class="container menu-container">
    <button @click="onCancelEditItems" v-show="isShowActions === true" class="btn-fixed-width btn-cancel">Отменить</button>
    <button ref="editItemsBtn" @click="onEditItems">Редактировать</button>
  </div>

  <div 
    v-if="endpoint.includes(menuItemsTable)" 
    
    class="container container-table"
  >
    <MenuItemTable
      @change-edit-items-button-state="onChangeEditItemsButtonState"
      ref="menuItemTable"
      :pageResponse="pageResponse"
    />
  </div>

  <div 
    v-if="endpoint.includes(ordersTable)" 
    
    class="container container-table"
  >
    <OrdersTable 
      @change-edit-items-button-state="onChangeEditItemsButtonState"
      ref="ordersTable"
      :pageResponse="pageResponse"
    />
  </div>

  <div 
    v-if="endpoint.includes(employeeTable)" 
    
    class="container container-table"
  >
    <EmployeeTable 
      @change-edit-items-button-state="onChangeEditItemsButtonState"
      ref="employeeTable"
      :pageResponse="pageResponse"
    />
  </div>

  <div class="container paging">
    <Pagination
      ref="pagination" @page-number-changed="onPageNumberChanged"

      :totalPages="pageResponse.totalPages"
      :hasPrevious="pageResponse.hasPrevious"
      :hasNext="pageResponse.hasNext"
    />
  </div>

</template>

<style lang="scss">
  .menu-container {
    width: 100%;

    justify-content: end;
    gap: 1.5rem;
  }

  .btn {
    color: var(--color-background);
    &-fixed-width {
      width: 150px;
      text-align: center;
    }
    &-info {
      @extend .btn;
      background-color: rgb(var(--color-info));
    }
    &-cancel {
      @extend .btn;
      background-color: var(--color-primary);
    }
    &-danger {
      @extend .btn;
      background-color: rgb(var(--color-danger));
    }
  }
</style>