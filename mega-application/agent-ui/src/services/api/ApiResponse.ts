const getResponseData = <T>(res: API.ApiResponse): T => {
  return res.data;
}

export {
  getResponseData
}
