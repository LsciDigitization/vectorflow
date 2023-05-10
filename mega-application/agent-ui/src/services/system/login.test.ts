import {getCaptchaImage} from "@/services/system/login";
import request from "@/utils/request";

beforeAll(() => {
  request.interceptors.request.use((options: any) => {
    return ({
      options: {
        ...options,
        prefix: 'http://localhost:8080',
        headers: {
          'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6IjVjODkxZmQxLTEzNjgtNDY2OS04OGIzLTg3NTBmMGEzMTg2YyJ9.AfIbtfd51GY14YHaFrUFAGWkqkXptt7P4W00xq9WZ5tMU8ww08cXTIV1M-GdydyQVoO228h_PcbnujxnrvQdKA'
        }
      }
    });
  });
})

describe("login", () => {

  it("getCaptchaImage", async () => {
    const data = await getCaptchaImage();
    console.log(data);
  });

});
