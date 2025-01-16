<script lang="ts">
import { defineComponent } from 'vue';

import {MENU_ENDPOINT, ORDERS_ENDPOINT, EMPOYEES_ENDPOINT, PAGE_SIZE} from '../utils/Constants';

export default defineComponent({
  name: 'Paging',
	props: {
		totalPages: { type: Number, required: true },
		hasPrevious: { type: Boolean, required: true },
		hasNext: { type: Boolean, required: true }
	},
	data() {
		return {
      activePageButton: null,
			pageNumber: 0
		}
	},
	methods: {
		onChangePage(event: Event) {
      if (this.activePageButton !== null) {
        this.activePageButton.classList.remove('active');
      }

      let pageNumber: number | null = null
      if (event.target.id == "previousPage") {

        pageNumber = this.activePageButton.innerText - 2;

        this.activePageButton = this.$refs.pageLinks[pageNumber];   

      } else if (event.target.id == "nextPage") {

        pageNumber = this.activePageButton?.innerText ?? 0;

        this.activePageButton = this.$refs.pageLinks[pageNumber];


      } else {
      
        pageNumber = event.target.innerText - 1;
        this.activePageButton = event.target;
      
      }
      
      this.activePageButton.classList.add('active');

      this.pageNumber = pageNumber;
			this.$emit('pageNumberChanged')
    }
	},
	mounted() {
		this.totalPages
		this.hasPrevious
		this.hasNext
	}	
});    
</script>
<template>

	<a 
		@click="onChangePage" 

		id="previousPage"

		v-show="hasPrevious === true" class="page-link" href="#"
	>&larr;</a>
	
	<a 
		@click="onChangePage" 
		
		v-for="i in totalPages" 
		ref="pageLinks"

		:class="['page-link', {'active': activePageButton === null && i === 1}]" href="#"
	>{{ i }}</a>
	
	<a 
		@click="onChangePage" 

		id="nextPage"

		v-show="hasNext === true" 
		class="page-link" href="#"
	>&rarr;</a>
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

    &:hover, 
    &.active {
      color: var(--color-primary);
    }

    &.active {
      color: var(--color-background);
      background-color: var(--color-primary) !important;  
    }
  }
}
</style>