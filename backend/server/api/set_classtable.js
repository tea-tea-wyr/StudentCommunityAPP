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
    
    if(req.body.name == undefined || req.body.time == undefined ||req.body.place == undefined ||req.body.number == undefined)
    {
        res.send(return_obj.fail("100", "缺少必要的参数"));
        return ;
    }
    next();
})

//插入课程及关系表
router.post("/",function(req,res,next){
    //sql语句
    async.waterfall([
        function(done){
            let sql=`
                select courseid from Course where name=?
            `
            DBHelp(sql,[req.body.name],(err,course_info,field)=>{
                console.log(course_info)
                if(err){
                    console.log(err);
                    res.send(return_obj.fail("200","调用数据库接口错误"))
                    return ;
                }
                
                done(null,course_info)
            })
        },
        function(course_info,done){
            let courseid;
            if(course_info.length==0) courseid=Date.now();
            else courseid=course_info[0].courseid
            let sql=`
                insert into Course(
                    classid,
                    courseid,
                    name,
                    time,
                    place,
                    number
                ) value(?,?,?,?,?,?)
            `
            let classid=Date.now()
            let course_list =[
                classid,
                courseid,
                req.body.name,
                req.body.time,
                req.body.place,
                req.body.number
            ];
            DBHelp(sql,course_list,(err,result,field)=>{
                if(err){
                    console.log(err);
                    res.send(return_obj.fail("200","调用数据库接口错误"))
                    return ;
                }
                done(null,course_list[0])
            })
        },
        function(classid,done){
            let sql=`
                insert into Possess(
                    userid,
                    classid
                ) value(?,?)
            `
            let possess_list=[req.session.userid,classid]
            DBHelp(sql,possess_list,(err,result,field)=>{
                if(err){
                    console.log(err);
                    res.send(return_obj.fail("200","调用数据库接口错误"))
                    return ;
                }
                done(null,classid)
            })
        }
    ],
    function(err,result){
        if (err) {
            console.log(err);
            return ;
        }
        res.send(return_obj.success({
            msg: "设置课表成功"
        }));
    }
    )
    
})
module.exports = router;