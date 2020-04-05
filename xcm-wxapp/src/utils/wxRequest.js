//请求封装
const wxRequest = async(params = {}, url, method) => {
  return new Promise(resolve => {
    //头处理
    let header = params.header || {};
    if(!header['content-type']){
      header['content-type'] = 'application/x-www-form-urlencoded';
    }
    if(!header['X-Requested-With']){
      header['X-Requested-With'] = 'XMLHttpRequest';
    }
    //cookie处理
    header['cookie'] = wx.getStorageSync('cookie');
    let data = params.data || {};
    wx.request({
      url : url,
      header : header,
      method : method || 'GET',
      data : data,
      success : function(data){
        cookieSave(data);
        resolve(data);
      }
    });
  });
};

function cookieSave(data){
  let cookie = data.header['Set-Cookie'];
  if(!cookie){
    return;
  }
  let newCookie = '';
  let session = cookie.match(/SESSION=(\S*)\;/);//会话sessionid
  if(session){
    newCookie += session[0];
  }
  wx.setStorageSync("cookie", newCookie);
}

module.exports = wxRequest;
