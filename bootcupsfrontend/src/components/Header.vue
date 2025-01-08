<script setup>
defineProps({
  msg: {
    type: String,
    required: true,
  },
  brandName: {
    type: String,
    required: true
  }
})
</script>

<template>
  <div class="container navbar">
    <h1 class="green">{{ msg }}</h1>
    <a class="container container-logo" href="#">
      <svg 
        width="48" height="48" class="logo" 
        xmlns="http://www.w3.org/2000/svg"
      >
        <use href="../assets/logo.svg#logo"/>
      </svg>
      <h3>
        {{ brandName }}  
      </h3>
    </a>
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

@media (min-width: 1024px) {
  .navbar h1,
  .navbar h3 {
    text-align: left;
  }
}
</style>
