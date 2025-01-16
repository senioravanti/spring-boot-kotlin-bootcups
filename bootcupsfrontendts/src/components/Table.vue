<script lang="ts">
import RestClient from '../services/RestClient';
import firstLetterToLowercase from '../utils/Utils';

import type { AxiosResponse } from 'axios';

import { defineComponent } from 'vue';

const PAGE_SIZE = 10;

export default defineComponent({
  name: '',
  props: {
  },
  data() {
    // Возвращает реактивный объект, который можно использовать в шаблоне и методах
    return {
      restClient: new RestClient(),
      restData: [],

      activePageButton: null
    }
  },
  computed: {
       
  },
  methods: {
    loadDataFromBackend(
      pageNumber: number, 
      pageSize: number
    ) {
      this.restClient.findAllMenuItems(pageNumber, pageSize)
        .then(response => {
          this.restData = response.data || []; 
          console.log('rest data:\n', this.restData);
          response.data;  
        })
      ;
    },

    // utiils
    unitText(menuItemMakes: string, unitLabel: string): string {
      return menuItemMakes + ' ' + unitLabel + '.'
    },

    productNameWithTopping(productName: string, topping: string | null): string {
      return productName + ' ' + (topping == null ? '' : firstLetterToLowercase(topping));
    },

    // handlers
    changePage(event: Event) {
      if (this.activePageButton != null) {
        this.activePageButton.classList.remove('active');
      }

      let pageNumber: number | null = null
      if (event.target.id == "previousPage") {

        pageNumber = this.activePageButton.innerText - 2;

        this.activePageButton = this.$refs.pageLinks[pageNumber];   

      } else if (event.target.id == "nextPage") {

        pageNumber = this.activePageButton?.innerText ?? 0;
        
        this.activePageButton = this.$refs.pageLinks[pageNumber];


      } else {
      
        pageNumber = event.target.innerText - 1;
        this.activePageButton = event.target;
      
      }
      
      this.activePageButton.classList.add('active');
      this.loadDataFromBackend(pageNumber, PAGE_SIZE)

    }
  },
  mounted() {
    this.loadDataFromBackend(0, PAGE_SIZE);
  }
})
</script>

<template>
  <div class="container container-table">
    <h3>Меню</h3>  
    <table>
      <thead>
        <tr>
          <th>№</th>
          <th>Название</th>
          <th>Объем порции</th>
          <th>Цена</th>
        </tr>
      </thead>
      <tbody>
        <!-- <tr></tr> -->
        <tr v-for="data in restData.content" v-bind:key = "data.id">
          <td>{{ data.id }}</td>
          <td class="align-left">{{ productNameWithTopping(data.product.name, data.topping) }}</td>
          <td>{{ unitText(data.makes, data.unit.label) }}</td>
          <td>{{ data.price }}</td>
          <!-- <td>{{ new Date(Date.parse(data.birth_date)).toLocaleDateString('ru-RU') }}</td> -->
        </tr>
      </tbody>
      <tfoot>
      </tfoot>
    </table>
  </div>
  <div class="container paging">
    <a 
      @click="changePage" 

      id="previousPage"

      v-show="restData.hasPrevious === true" class="page-link" href="#"
    >&larr;</a>
    
    <a 
      @click="changePage" 
      
      v-for="i in restData.totalPages" 
      ref="pageLinks"

      :class="['page-link', {'active': activePageButton === null && i === 1}]" href="#"
    >{{ i }}</a>
    
    <a 
      @click="changePage" 

      id="nextPage"

      v-show="restData.hasNext === true" 
      class="page-link" href="#"
    >&rarr;</a>
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