/*
* function:用户对某条资料发布评论
*/
'use strict'
const express = require('express');
const return_obj = require('../tool/return_obj.js');
const verify_login = require('../middleware/verify_login.js')
const router = express.Router();
const DBHelp = require('../config/DBHelp.js');
const async = require('async');
const moment = require('moment');

//使用登录状态验证中间件
router.post("/", verify_login);

//进行参数检查
router.post("/",function(req,res,next){
    /* let date = new Date();          // 获取一个Date对象
    let timeStamp = new Timestamp(date.getTime());     //   讲日期时间转换为数据库中的timestamp类型 */
    
    if(req.body.dataid == undefined || req.body.comment == undefined)
    {
        res.send(return_obj.fail("100", "缺少必要的参数"));
        return ;
    }
    next();
})

//发布评论
router.post("/",function(req,res,next){
    //sql语句
    let sql=`
    insert into Comment(
        commentid,
        userid,
        dataid,
        comment,
        time
    ) value(?,?,?,?,?)
    `
    /* let now = new Date();
    let time = now.getFullYear() + "-"
    time += now.getMonth() + 1 + "-";
    time += now.getDate() + "-";
    time += now.getHours() + "-";
    time += now.getMinutes(); 
    let date = new Date();          // 获取一个Date对象
    let timeStamp = new Timestamp(date.getTime());     //   讲日期时间转换为数据库中的timestamp类型*/
    let timeStamp=moment(new Date()).format('YYYY-MM-DD HH:mm:ss')
    /* console.log(moment(new Date()).format('YYYY-MM-DD HH:mm:ss'))
    console.log("aaa"+timeStamp) 
    console.log("bababa"+Date.now()) */
    
    let comment_list =[
        Date.now(),
        req.session.userid,
        req.body.dataid,
        req.body.comment,
        timeStamp
    ];
    DBHelp(sql,comment_list,(err,result,field)=>{
        if(err){
            console.log(err);
            res.send(return_obj.fail("200","调用数据库接口错误"))
            return ;
        }
        res.send(return_obj.success({
            msg: "发布评论成功"
        }));
    })
    
})
module.exports = router;