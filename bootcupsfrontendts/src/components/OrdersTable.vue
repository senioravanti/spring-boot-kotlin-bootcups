<script lang="ts">

import { defineComponent } from 'vue';
import firstLetterToLowercase from '../utils/Utils';

export default defineComponent({
  name: 'OrdersTable',  
  props: {
    pageResponse: { type: Object, required: true }
  },
  data() {
    return {}
  },
  methods: {
    employeeName(lastName: string, firstName: string, middleName: string | null): string {
      return lastName + ' ' + firstName.charAt(0) + '.' + (middleName !== null 
        ? middleName.charAt(0) + '.' 
        : ''
        );
    },
    epochSecondsToLocaleString(epochSeconds: number): string {
        return new Date(epochSeconds * 1000).toLocaleString(
            'ru-RU',
            { 
                day: 'numeric', 
                month: 'long', 
                year: 'numeric', 
                
                hour: 'numeric',
                minute: 'numeric',

                timeZone: 'Europe/Moscow' 
            }
        );
    },
  },
  mounted() {
    this.pageResponse
    console.log('page response', this.pageResponse)
  }    
});
</script>

<template>
  <h3>Заказы</h3>  
  <table>
    <thead>
      <tr>
        <th>№</th>

        <th>Имя клиента</th>
        <th>ФИО сотрудника</th>

        <th>Момент оформления</th>

        <th>Общая стоимость</th>
        <th>Размер скидки</th>
        <th>Стоимость с учетом скидки</th>

        <th>Статус заказа</th>

        <th>Количество пунктов</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(data, index) in pageResponse.content" v-bind:key = "data.id">
        <td>{{ index + 1 }}</td>
        
        <td class="align-left">{{ 
            data.client !== null ? data.client.name : 'Анонимный клиент'
        }}</td>

        <td class="align-left">{{ 
            employeeName(
                data.employee.lastName, 
                data.employee.firstName, 
                data.employee.middleName, 
            ) 
        }}</td>

        <td class="align-left">{{ epochSecondsToLocaleString(data.createdAt) }}</td>

        <td class="align-right">{{ data.totalAmount }}</td>
        <td class="align-right">{{ data.discountAmount }}</td>
        <td class="align-right">{{ data.totalAmount - data.discountAmount }}</td>

        <td>{{ data.statusDto.name }}</td>

        <td class="align-right">{{ data.totalAmount }}</td>
      </tr>
    </tbody>
    <tfoot>
    </tfoot>
  </table>
</template>
  
<style>
</style>
