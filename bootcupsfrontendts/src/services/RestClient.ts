import axios from 'axios'

const API_BASE_URL = "http://localhost:8001/api/v1";

export default class RestClient {
    async findAllMenuItems(
        endpoint: string,
        
        pageNumber: number, pageSize: number
    ) {
        const url = API_BASE_URL + endpoint;
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