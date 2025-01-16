<script setup lang="ts">
import Header from './components/Header.vue'
import Footer from './components/Footer.vue'
import Table from './components/Table.vue'

import { ref, onMounted } from 'vue';

const header = ref(null);
const table = ref(null);

function onTableChanged() {
  const newEndpoint = `/${header.value.activeTable.id}/`; 

  // table.value.endpoint = newEndpoint;

  console.log('table changed: ', header.value.activeTable.id);
  table.value.loadDataFromBackend(0, newEndpoint);
  console.log('request was send')
}

onMounted(() => {
  onTableChanged();  
});
</script>

<template>
  <div class="content">
    <header>
        <Header @table-changed="onTableChanged" ref="header" msg="Виртуальная кофейня Bootcups" brandName="Bootcups" />
    </header>
    <main class="container">
      <Table ref="table"/>
    </main>
  </div>

  <footer>
    <Footer/>
  </footer>
</template>

<style scoped lang="scss">
header {
  line-height: 1.5;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
  }

  .logo {
    margin: 0 2rem 0 0;
  }
}
</style>
