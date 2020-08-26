/**
 * 登录功能，账号密码不为空情况下返回正确状态
 * @type {Pool}
 */
"use strict"

const DBHelp = require('../config/DBHelp.js');
const express = require("express");
//const crypto = require("crypto");
const verify_login = require('../middleware/verify_login.js')
const verify_no_login = require('../middleware/verify_no_login.js');
const return_obj = require('../tool/return_obj.js');
const { success } = require("../tool/return_obj");
const router = express.Router();

//使用登录状态验证中间件
router.post("/", verify_no_login);

//进行参数检查
router.post("/", function (req, res) {
    
    //console.log(req.body.userid);
    console.log(req.body);
    if("userid" in req.body && "password" in req.body){
        //传参正确
        // SQL 语句
        var SQL = `
        select  password,
            name,
            sex,
            school,
            collage,
            class,
            wechat,
            phone,
            picture,
            year
        from User
        where userid=? `;

        const resultData = {
            code: null,
            data: null,
            codeMessage: null
        }
        
        DBHelp(SQL,[req.body.userid],(error, result, fields) =>{
            
            if(error){
                
                //resultData.error = error.message
                console.error(error);
                res.send(return_obj.fail("200", "调用数据库接口错误"));
                return;
            }
            console.log(result);
            if(result.length==0){
                res.send(return_obj.fail("104", "用户不存在"));
                return;
            }
            //如果用户密码正确则设置session，否则提示密码错误
            if(result[0].password==req.body.password){
                //设置session
                req.session.is_login = true;
                req.session.userid = req.body.userid;
                req.session.name = result[0].name;
                req.session.email = result[0].email;
                req.session.sex = result[0].sex;
                req.session.school = result[0].school;
                req.session.collage = result[0].collage;
                req.session.class = result[0].class;
                req.session.wechat = result[0].wechat;
                req.session.phone = result[0].phone;
                req.session.picture = result[0].picture;
                req.session.year = result[0].year;
                res.send(return_obj.success({
                    "msg": "登录成功"
                }));
                //console.log(result[0]);
                
                return;
                
            }
            else{
                res.send(return_obj.fail("105", "用户账号密码不匹配"))
                return;
            }
        })
    }else{
        res.send(return_obj.fail("100", "缺少必要的参数"));
        return;
    }
    
})

module.exports = router;