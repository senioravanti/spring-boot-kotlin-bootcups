<script lang="ts">

import { defineComponent } from 'vue';
import { checkAll } from '../utils/Utils';

import icons from "../assets/icons.svg" with { type: "svg" };

export default defineComponent({
  props: {
    pageResponse: { type: Object, required: true }
  },
  emits: ['changeEditItemsButtonState'],
  data() {
    return {
      name: 'EmployeeTable',  
      
      isShowActions: false,
      checkedCheckboxesCount: 0,

      actionLinks: [
        {
          iconId: "edit",
          href: `${icons}#edit`
        },
        {
          iconId: "delete",
          href: `${icons}#delete`
        }
      ]
    }
  },
  methods: {
    employeeName(lastName: string, firstName: string, middleName: string | null): string {
      return lastName + ' ' + firstName + (middleName !== null 
        ? ' ' + middleName 
        : ''
        );
    },
    
    onCheckBox(event: Event) {
      if (event.target.checked) {
        this.checkedCheckboxesCount += 1;
        if (this.checkedCheckboxesCount === 1) {
          this.$emit('changeEditItemsButtonState');
        }
      } else {
        this.checkedCheckboxesCount -= 1;    
        if (this.checkedCheckboxesCount === 0){
          this.$emit('changeEditItemsButtonState'); 
        }
      }
    },
    onCheckAll() {
      this.checkedCheckboxesCount = checkAll(this.$refs.checkAll, this.$refs.checkboxes);

      this.$emit('changeEditItemsButtonState');
    }
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
        <th v-if="isShowActions === true">
          <input @click="onCheckAll" type="checkbox" ref="checkAll"/>
        </th>
        <th v-else>№</th>

        <th>ФИО</th>
        
        <th>Должность</th>

        <th>Адрес электронной почты</th>

        <th>Номер телефона</th>

        <th v-if="isShowActions === true">Операции</th>
      </tr>
    </thead>
    <tbody>
      <tr v-for="(data, index) in pageResponse.content" v-bind:key = "data.id">
        <td v-if="isShowActions === true">
          
          <input 

            @click="onCheckBox"

            type="checkbox" 
            ref="checkboxes" 
            :name="data.id" 
          />

        </td>
        <td v-else>{{ index + 1 }}</td>
        
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

        <td v-if="isShowActions === true">
          <div class="actions-container">
            <a
            
              v-for="action in actionLinks" 
              
              :key="action.iconId"
              :id="`${action.iconId}-action`"  
            
            >
              <svg xmlns="http://www.w3.org/2000/svg">
                <use :href="action.href"/>
              </svg>
            </a>    
          </div>  
        </td>
      </tr>
    </tbody>
    <tfoot>
    </tfoot>
  </table>
</template>
  
<style>
</style>
