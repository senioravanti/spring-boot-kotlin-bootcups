import axios from 'axios'

const API_BASE_URL = "http://127.0.0.1:8012";

export default class RestClient {
    async allSingers() {
        const url = API_BASE_URL + '/api/v1/singer/';
        try {
            const response = await axios.get(
                url,
                {
                    responseType: 'json'
                }
            );
            console.log('Ответ сервера:\n');
            console.log(response);
            return response;
        } catch (error) {
            console.log('Произошла ошибка при отправке запроса:\n' + error);
            return null;
        }
    }
}