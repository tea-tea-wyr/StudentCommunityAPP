/*
* author:谢奇
* create_day:2020-04-24
* modified_day:2020-04-24
* function:验证登录态中间件，登录成功则通过。
*/
'use strict'
const return_obj = require("../tool/return_obj.js")

module.exports = function(req,res,next){
    if(req.session.is_login == true||req.session.userid !=undefined){
        next();
    }else{
        res.send(return_obj.fail('102','用户未登录'));
    }
}