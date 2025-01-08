<script>
import RestClient from '../services/RestClient';
export default {
  name: '',
  data() {
    // Возвращает реактивный объект, который можно использовать в шаблоне и методах
    return {
      restData: []
    }
  },
  methods: {
    loadDataFromBackend() {
      new RestClient().allSingers()
        .then(response => {
          console.log('response:\n', response.data);
          this.restData = response.data || []; 
          console.log('rest data:\n', this.restData);
          response.data;  
        })
      ;
    }
  },
  computed: {
    oldestSinger() {
      if (this.restData.length === 0) {
        console.log('В массиве restData отсутствуют объекты');
        return null;
      }

      return this.restData.reduce((oldestSinger, currentSinger) => {
        const oldestSingerBirthDate = new Date(oldestSinger['birth_date']);
        const currentSingerBirthDate = new Date(currentSinger['birth_date']);

        return currentSingerBirthDate.getTime() < oldestSingerBirthDate.getTime() 
          ? currentSinger 
          : oldestSinger
        ;
      });

      
    }
  },
  created() {
    this.loadDataFromBackend();
  }
}
</script>

<template>
  <div class="container container-table">
    <h3>Данные, полученные от сервера</h3>  
    <table>
      <thead>
        <tr>
          <th>№</th>
          <th>Имя</th>
          <th>Фамилия</th>
          <th>Дата рождения</th>
        </tr>
      </thead>
      <tbody>
        <!-- <tr></tr> -->
        <tr v-for="data in restData" v-bind:key = "data.singer_id">
          <td>{{ data.singer_id }}</td>
          <td>{{ data.first_name }}</td>
          <td>{{ data.last_name }}</td>
          <td>{{ new Date(Date.parse(data.birth_date)).toLocaleDateString('ru-RU') }}</td>
        </tr>
      </tbody>
      <tfoot>
        <tr v-if="oldestSinger">
          <th colspan="4">{{  
            
            `Самый старый певец ${oldestSinger.first_name} ${oldestSinger.last_name} родился ${
              new Date(oldestSinger.birth_date).toLocaleDateString(
                'ru-RU', 
                { day: 'numeric', month: 'long', year: 'numeric' }
              )
            }` 
            
          }}</th>
        </tr>
      </tfoot>
    </table>
  </div>
</template>

<style>
  .container-table {
    margin-top: 1rem;

    flex-direction: column;
    justify-content: center;
    gap: 1.5rem;
  }

  table {
    border-collapse: collapse;
  }

  th, td {
    border: 1px solid;

    padding: .5rem 1rem;
  }

  th {
    font-weight: 700;
  }

  td {
    text-align: center;
  }
</style>