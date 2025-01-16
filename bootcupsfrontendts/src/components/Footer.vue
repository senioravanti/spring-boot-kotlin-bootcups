<script lang="ts">
import icons from "../assets/icons.svg";
import { defineComponent } from 'vue';

export default defineComponent({
  setup() {},
  data() {
    return {
      repoLinks: [
        {
          iconId: "github",
          href: `${icons}#github`,
          url: "https://github.com/senioravanti/spring-boot-kotlin-bootcups",
        },
        {
          iconId: "gitlab",
          href: `${icons}#gitlab`,
          url: "https://gitlab.com/senioravanti-projects/spring-boot-kotlin-bootcups",
        },
        {
          iconId: "gitverse",
          href: `${icons}#gitverse`,
          url: "https://gitverse.ru/senioravanti/spring-boot-kotlin-bootcups",
        },
      ],
    };
  },
});
</script>
<template>
  <div class="container footer">
    <div class="container repo-icons">
      <a v-for="link in repoLinks" :key="link.iconId" :href="link.url">
        <svg xmlns="http://www.w3.org/2000/svg">
          <use :href="`${link.href}`" />
        </svg>
      </a>
    </div>
    <div class="copyright">
      <p>&copy; 2025 Ремесленная Кофейня Bootcups. Мананников Антон ПИ-22-2.</p>
    </div>
  </div>
</template>

<style lang="scss">

.copyright {
  width: 100%;

  font-size: 0.875rem;

  text-align: center;
  color: var(--color-background);
}
.repo-icons {
  gap: 2rem;

  a {
    padding: 0;

    --icon-fill-color: var(--color-background);
    & svg {
      width: 24px;
      height: 24px;
    }

    &:nth-of-type(2) {
      & svg {
        width: 36px;
        height: 36px;
      }
    }

    &:hover {
      --icon-fill-color: var(--color-primary);
    }
  }
}
</style>
