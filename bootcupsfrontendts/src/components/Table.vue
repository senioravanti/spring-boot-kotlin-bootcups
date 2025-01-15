<script lang="ts">
import RestClient from '../services/RestClient';
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
  methods: {
    loadDataFromBackend() {
      this.restClient.findAllMenuItems(0, 10)
        .then(response => {
          this.restData = response.data || []; 
          console.log('rest data:\n', this.restData);
          response.data;  
        })
      ;
    }
  },
  computed: {
    
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
          <th>Выход порции</th>
          <th>Цена</th>
        </tr>
      </thead>
      <tbody>
        <!-- <tr></tr> -->
        <tr v-for="data in restData.content" v-bind:key = "data.id">
          <td>{{ data.id }}</td>
          <td class="align-left">{{ 
          
          `${data.product.name.trimEnd()} ${data.topping == null ? '' : data.topping.toLowerCase()}` 
          
          }}</td>
          <td>{{ `${data.makes} ${data.unit.label}.` }}</td>
          <td>{{ data.price }}</td>
          <!-- <td>{{ new Date(Date.parse(data.birth_date)).toLocaleDateString('ru-RU') }}</td> -->
        </tr>
      </tbody>
      <tfoot>
        <tr v-if="oldestSinger">
          <th colspan="4">{{  
            
            `Самый старый певец ${oldestSinger.first_name} ${oldestSinger.last_name} родился ${
              new Date(oldestSinger.birth_date).toLocaleDateString(
                'ru-RU', 
                { day: 'numeric', month: 'long', year: 'numeric' }
              )
            }` 
            
          }}</th>
        </tr>
      </tfoot>
    </table>
  </div>
  <div class="container paging">
    <a class="page-link" href="#">1</a>
    <a class="page-link" href="#">2</a>
    <a class="page-link" href="#">3</a>
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
  }
}
</style>