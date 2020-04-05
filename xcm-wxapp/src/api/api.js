//api接口
import wxRequest from '../utils/wxRequest';

const host = "http://localhost:8090";

const testGet = (params) => wxRequest(params, host + '/api/test/get/', "GET");
const testPost = (params) => wxRequest(params, host + '/api/test/post/', "POST");
const rsaDecrypt = (params) => wxRequest(params, host + '/api/test/rsa_decrypt/', 'GET');

const getPublicKey = (params) => wxRequest(params, host + '/api/common/get_public_key/', 'GET');

const wxLogin = (params) => wxRequest(params, host + '/api/weixin/app/login/', 'POST');
const login = (params) => wxRequest(params, host + '/api/login/submit/', 'POST');

export default {
  testGet,
  testPost,
  rsaDecrypt,

  getPublicKey,
  wxLogin,
  login
}
