<script lang="ts">

import { defineComponent } from 'vue';
import firstLetterToLowercase from '../utils/Utils';

export default defineComponent({
  name: 'EmployeeTable',  
  props: {
    pageResponse: { type: Object, required: true }
  },
  data() {
    return {}
  },
  methods: {
    employeeName(lastName: string, firstName: string, middleName: string | null): string {
      return lastName + ' ' + firstName + (middleName !== null 
        ? ' ' + middleName 
        : ''
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
  <h3>Сотрудники</h3>  
  <table>
    <thead>
      <tr>
        <th>№</th>

        <th>ФИО</th>
        
        <th>Должность</th>

        <th>Адрес электронной почты</th>

        <th>Номер телефона</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(data, index) in pageResponse.content" v-bind:key = "data.id">
        <td>{{ index + 1 }}</td>
        
        <td class="align-left">{{ 
            employeeName(
                data.lastName, 
                data.firstName, 
                data.middleName 
            ) 
        }}</td>
        
        <td class="align-left">{{ data.role.name }}</td>

        <td class="align-left">{{ data.email }}</td>
        <td class="align-left">{{ data.phoneNumber }}</td>

      </tr>
    </tbody>
    <tfoot>
    </tfoot>
  </table>
</template>
  
<style>
</style>
