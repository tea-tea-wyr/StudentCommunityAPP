/*
* function:根据userid获取用户信息
*/
'use strict'
const express = require('express');
const return_obj = require('../tool/return_obj.js');
const verify_login = require('../middleware/verify_login.js')
const router = express.Router();
const DBHelp = require('../config/DBHelp.js');



router.get("/", function (req, res, next) {
    //使用登录状态验证中间件
    if(req.query.op=="0")
    router.post("/", verify_login);
    //op为1时，代表获取其他用户信息；为0时代表获取自身信息
    if(req.query.op=="1" && "userid" in req.query || req.query.op=="0" ){
        var userid;
        if(req.query.op=="1") {
            userid=req.query.userid;
            // SQL 语句
            var SQL = `
            select  
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
            DBHelp(SQL,[userid],(error, result, fields) =>{
            
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

                    
                    res.send(return_obj.success({
                        "msg": "登录成功",
                        user_info:[
                            {
                                name: result[0].name,
                                email :result[0].email,
                                sex : result[0].sex,
                                school : result[0].school,
                                collage : result[0].collage,
                                class : result[0].class,
                                wechat :result[0].wechat,
                                phone :result[0].phone,
                                picture :result[0].picture,
                                yaer :result[0].year
                            }
                        ]
                    }));
                    //console.log(result[0]);
                    
                    return;
                    
                
            })

        }
        else if(req.query.op=="0") {
            userid=req.session.userid;
            res.send(return_obj.success({
                msg: "获取用户信息成功",
                user_info:[
                    {
                        userid: userid,
                        name: req.session.name,
                        email: req.session.email,
                        sex: req.session.sex,
                        school: req.session.school,
                        collage: req.session.collage,
                        class: req.session.class,
                        wechat:req.session.wechat,
                        phone: req.session.phone,
                        picture: req.session.picture,
                        year:req.session.year
                    }
                ]
            }))
        } 
    
    }else{
        res.send(return_obj.fail("100", "缺少必要的参数"));
        return;
    }
})

module.exports = router;