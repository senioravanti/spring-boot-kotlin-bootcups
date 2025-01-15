import axios from 'axios'

const API_BASE_URL = "http://localhost:8001";

export default class RestClient {
    async findAllMenuItems(pageNumber, pageSize) {
        const url = API_BASE_URL + '/api/v1/menu/';
        try {
            const response = await axios.get(
                `${url}?page_number=${pageNumber}&page_size=${pageSize}`,
                {
                    responseType: 'json'
                }
            );
            console.log('Ответ сервера:', response);
            return response;
        } catch (error) {
            console.log('Произошла ошибка при отправке запроса:\n' + error);
            return null;
        }
    }
}