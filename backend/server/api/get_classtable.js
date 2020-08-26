/*
* function:根据userid获取用户课表
*/
'use strict'
const express = require('express');
const return_obj = require('../tool/return_obj.js');
const verify_login = require('../middleware/verify_login.js')
const router = express.Router();
const DBHelp = require('../config/DBHelp.js');

//中间件验证登录状态
router.post("/",verify_login);

//获取课程表
router.get("/",function(req,res,next){
    let sql=`
        select 
            Course.classid,
            name,
            time,
            place,
            number
        from Course,Possess
        where Possess.userid=? and Possess.classid=Course.classid

    `
    DBHelp(sql,[req.session.userid],(err,result,field)=>{
        if(err){
            console.log(err);
            res.send(return_obj.fail("200","调用数据库接口错误"))
            return ;
        }
        //console.log("aqaq"+result);
        if(result.length==0){
            res.send(return_obj.fail("127","用户未选课"))
            return ;
        }
        console.log(result);
        res.send(return_obj.success({
            msg: "获取数据成功",
            user_classtable: result
        }));
    })
})
module.exports = router;