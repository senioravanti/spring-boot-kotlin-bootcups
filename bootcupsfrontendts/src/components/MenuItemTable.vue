<script lang="ts">

import { defineComponent } from 'vue';
import firstLetterToLowercase from '../utils/Utils';

export default defineComponent({
  props: {
    pageResponse: { type: Object, required: true }
  },
  data() {
    return {}
  },
  methods: {
    productNameWithTopping(productName: string, topping: string | null): string {
      return productName + ' ' + (topping == null ? '' : firstLetterToLowercase(topping));
    },
    unitText(menuItemMakes: string, unitLabel: string): string {
      return menuItemMakes + ' ' + unitLabel + '.'
    },
  },
  mounted() {
    this.pageResponse
    console.log('page response', this.pageResponse)
  }    
});
</script>

<template>
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
      <tr v-for="(data, index) in pageResponse.content" v-bind:key = "data.id">
        <td>{{ index + 1 }}</td>
        <td class="align-left">{{ productNameWithTopping(data.product.name, data.topping) }}</td>
        <td>{{ unitText(data.makes, data.unit.label) }}</td>
        <td>{{ data.price }}</td>
      </tr>
    </tbody>
    <tfoot>
    </tfoot>
  </table>
</template>
  
<style>
</style>
