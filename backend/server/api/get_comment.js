/*
* function:根据dataid获取此条资料的所有评论
*/
'use strict'
const express = require('express');
const return_obj = require('../tool/return_obj.js');
const verify_login = require('../middleware/verify_login.js')
const router = express.Router();
const DBHelp = require('../config/DBHelp.js');
const async = require('async');

//进行参数检查
router.post("/",function(req,res,next){
    

    if("dataid" in req.query) ;
    else {
        res.send(return_obj.fail("100", "缺少必要的参数"));
        return ;
    }
    
    next();
})

//使用瀑布流获取评论数据
router.get("/",function(req,res,next){
    async.waterfall([
        function(done){
            //sql语句
            let sql=`
            select  
                Comment.userid as userid,
                name,
                picture,
                commentid,
                comment,
                time
            from User,Comment
            where dataid=? and Comment.userid=User.userid
            order by time desc
            `
            DBHelp(sql,[req.query.dataid],(error, comment_list, fields) =>{
                if(error){
                    console.error(error);
                    res.send(return_obj.fail("200", "调用数据库接口错误"));
                    return;
                }
                if(comment_list.length==0){
                    res.send(return_obj.fail("125", "找不到对应id的评论"));
                    return;
                }
                //瀑布流1的回调函数
                done(null, comment_list);
            })
        },
        function(comment_list,done){
            done(null, comment_list);
        }
    ],function(err,result){
        if (err) {
            console.log(err);
            return ;
        }
        res.send(return_obj.success({
            msg: "获取数据成功",
            comment_list: result
        }));
    })
})
module.exports = router;