<script lang="ts">
import { defineComponent } from 'vue';

import {MENU_ENDPOINT, ORDERS_ENDPOINT, EMPOYEES_ENDPOINT} from '../utils/Constants';

export default defineComponent({
  props: {
    msg: { type: String, required: true },
    brandName: { type: String, required: true }
  },
  data() {
    return {
      menuId: MENU_ENDPOINT,
      ordersId: ORDERS_ENDPOINT,
      employeeId: EMPOYEES_ENDPOINT,

      activeTable: null
    };
  },
  methods: {
    onChangeTable(event: Event) {
      if (this.activeTable === null) {
        this.activeTable = this.$refs.initTable
      } 
      this.activeTable.classList.remove('active');  

      this.activeTable = event.target
      this.activeTable.classList.add('active')

      this.$emit('tableChanged');
    }
  },
  mounted() {
    this.brandName
    this.msg
  }
});
</script>

<template>
  <div class="container navbar">
    <h1 class="green">{{ msg }}</h1>
    <a class="container container-logo" href="#">
      <svg
        width="48"
        height="48"
        class="logo"
        xmlns="http://www.w3.org/2000/svg"
      >
        <use href="../assets/logo.svg#logo" />
      </svg>
      <h3>
        {{ brandName }}
      </h3>
    </a>
    <div class="container nav-links">
        <ul class="nav">
          <li class="nav-item">
            <a 
              @click="onChangeTable" 
              :id="menuId"
              ref="initTable"
              
              class="nav-link active" href="#"
            >
              Меню</a>
          </li>
          <li class="nav-item">
            <a 
              @click="onChangeTable"
              :id="ordersId"

              class="nav-link" href="#"
            >
              Очередь заказов</a>
          </li>
          <li class="nav-item">
            <a 
              @click="onChangeTable"
              :id="employeeId"

              class="nav-link" href="#"
            >
              Сотрудники</a>
          </li>
        </ul>
    </div>
  </div>
</template>

<style scoped lang="scss">
h1 {
  display: none;

  font-weight: 500;
  font-size: 2.5rem;
  position: relative;
  top: -10px;
}

$font-size: 1.2rem;
h3 {
  font-size: $font-size;
  color: var(--color-background);
}

.logo {
  --stroke-color: var(--color-background);
}

.container-logo {
  gap: 1rem;

  &:hover {
    & h3 {
      color: var(--color-primary);
    }

    & .logo {
      --stroke-color: var(--color-primary);
    }
  }
}

.nav {
  padding: 0;
  list-style: none; 
  display: inline-flex;
  gap: 1rem; 
}

@media (min-width: 1024px) {
  .navbar h1,
  .navbar h3 {
    text-align: left;
  }
}
</style>
