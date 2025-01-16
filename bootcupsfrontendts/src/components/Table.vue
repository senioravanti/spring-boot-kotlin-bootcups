<script lang="ts">
import { defineComponent } from 'vue';

import MenuItemTable from './MenuItemTable.vue';
import OrdersTable from './OrdersTable.vue';
import EmployeeTable from './EmployeeTable.vue';


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

      activePageButton: null,
      endpoint: `/${MENU_ENDPOINT}/`,

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
      pageSize: number = PAGE_SIZE
    ) {
      console.log('endpoint: ', endpoint);
      this.restClient
        .findAllMenuItems(
          endpoint,

          pageNumber, pageSize
        )
        .then(response => {
          this.pageResponse = response.data || []; 
          this.endpoint = endpoint;
          
          console.log(
            'rest data:\nendpoint: ', 
            
            this.pageResponse, 
            this.endpoint
          );

          response.data; 
        })
      ;
    },
    onPageNumberChanged() {
      this.loadDataFromBackend(
        this.$refs.pagination.pageNumber,
        this.endpoint
      );
    }
  },
  mounted() {
    console.log('mounted: endpoint: ', this.endpoint);
    this.loadDataFromBackend(
      0,
      this.endpoint
    );
  }
})
</script>

<template>
  <div 
    v-if="endpoint.includes(menuItemsTable)" 
    
    class="container container-table"
  >
    <MenuItemTable :pageResponse="pageResponse"/>
  </div>

  <div 
    v-if="endpoint.includes(ordersTable)" 
    
    class="container container-table"
  >
    <OrdersTable :pageResponse="pageResponse"/>
  </div>

  <div 
    v-if="endpoint.includes(employeeTable)" 
    
    class="container container-table"
  >
    <EmployeeTable :pageResponse="pageResponse"/>
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
</style>