<script lang="ts">
import { defineComponent } from 'vue';

import MenuItemTable from './MenuItemTable.vue';
import Pagination from './Pagination.vue';

import RestClient from '../services/RestClient';

import {MENU_ENDPOINT, ORDERS_ENDPOINT, EMPOYEES_ENDPOINT, PAGE_SIZE} from '../utils/Constants';

export default defineComponent({
  name: 'Table',
  props: {
  },
  components: {
    MenuItemTable, 
    Pagination
  },
  data() {
    // Возвращает реактивный объект, который можно использовать в шаблоне и методах
    return {
      restClient: new RestClient(),
      pageResponse: [],

      activePageButton: null,
      endpoint: `/${MENU_ENDPOINT}/`
    }
  },
  computed: {
       
  },
  methods: {
    loadDataFromBackend(
      pageNumber: number, 
      pageSize: number
    ) {
      this.restClient.findAllMenuItems(
        this.endpoint,

        pageNumber, pageSize
      )
        .then(response => {
          this.pageResponse = response.data || []; 
          console.log('rest data:\n', this.pageResponse);
          response.data;  
        })
      ;
    },
    onPageNumberChanged() {
      this.loadDataFromBackend(this.$refs.pagination.pageNumber, PAGE_SIZE);
    }
  },
  mounted() {
    this.loadDataFromBackend(
      0, PAGE_SIZE
    );
    console.log('menu endpoint', MENU_ENDPOINT);
  }
})
</script>

<template>
  <div class="container container-table">
    <MenuItemTable :pageResponse="pageResponse"/>
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
.paging {
  
  margin-top: 2rem;

  justify-content: center;

  & .page-link {
    color: var(--color-header-background);

    border: 1px solid var(--color-header-background);
    padding: .25rem .75rem .25rem .75rem;

    margin-left: -1px;

    &:hover, 
    &.active {
      color: var(--color-primary);
    }

    &.active {
      color: var(--color-background);
      background-color: var(--color-primary) !important;  
    }
  }
}
</style>