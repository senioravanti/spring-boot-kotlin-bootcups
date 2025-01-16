<script lang="ts">
import RestClient from '../services/RestClient';
import type { AxiosResponse } from 'axios';
import { defineComponent } from 'vue';

export default defineComponent({
  name: '',
  props: {
  },
  data() {
    // Возвращает реактивный объект, который можно использовать в шаблоне и методах
    return {
      restClient: new RestClient(),
      restData: []
    }
  },
  computed: {
       
  },
  methods: {
    loadDataFromBackend() {
      this.restClient.findAllMenuItems(0, 10)
        .then(response => {
          this.restData = response.data || []; 
          console.log('rest data:\n', this.restData);
          response.data;  
        })
      ;
    },

    unitText(menuItemMakes: string, unitLabel: string): string {
      return menuItemMakes + ' ' + unitLabel + '.'
    },

    firstLetterToLowercase(str: string) {
      return str.charAt(0).toLowerCase() + str.slice(1);
    },

    productNameWithTopping(productName: string, topping: string | null): string {
      return productName + ' ' + (topping == null ? '' : this.firstLetterToLowercase(topping));
    }
  },
  mounted() {
    this.loadDataFromBackend();
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
    <a  class="page-link" href="#">&larr;</a>
    <a  v-for="i in restData.totalPages" class="page-link" href="#">{{ i }}</a>
    <a class="page-link active" href="#">&rarr;</a>
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
  }
}
</style>