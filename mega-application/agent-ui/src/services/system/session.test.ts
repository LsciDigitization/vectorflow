import request, {printJsonString} from "@/utils/request-test";
import {getResponseData} from "@/services/api/ApiResponse";
import {getLocalRouterPath} from "@/services/system/remoteComponent";

describe("session", () => {

  it("getRouters", async () => {
    const data = await request('/getRouters');
    printJsonString(data)

    const responseData: API.ResRouterMenuItem[] = getResponseData(data);
    console.log(responseData)
    if (responseData == null) {
      return ;
    }

    const resRouterMenuItem: API.ResRouterMenuItem | undefined = responseData.shift();
    if (resRouterMenuItem !== undefined) {
      const children = resRouterMenuItem.children;
      children.forEach(item => {
        console.log(item)
        console.log(getLocalRouterPath(item.path))
      })
    }
  });

});
