<script setup lang="ts">
import Header from './components/Header.vue'
import Footer from './components/Footer.vue'
import Table from './components/Table.vue'
</script>

<template>
  <div class="content">
    <header>
      <div class="wrapper">
        <!-- Импорт шаблона -->
        <Header msg="Виртуальная кофейня Bootcups" brandName="Bootcups" />
      </div>
    </header>

    <main class="container">
      <Table/>
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
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }
}
</style>
