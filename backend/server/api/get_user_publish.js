/**
 * 获取我所发布的所有资料
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
const async = require('async');
const router = express.Router();

//使用登录状态验证中间件
router.post("/", verify_login);

//进行参数检查
router.get("/", function (req, res,next) {
    
    //console.log(req.body.userid);
    console.log(req.body);
    async.waterfall([
        function(done){
            if(req.query.op=="1" && "userid" in req.query || req.query.op=="0" ){
                //传参正确
                var userid;
                if(req.query.op=="1") userid=req.query.userid;
                else if(req.query.op=="0") {
        
                    userid=req.session.userid;}
                //    console.log("userid:"+userid);} 
                /*     console.log("userid:"+userid);
                    userid=req.session.userid;*/
                // SQL 语句
                var SQL = `
                select  
                    User.name as name,
                    User.picture as imgUrl,
                    Data.dataid as dataid,
                    if_article,
                    if_video,
                    if_audio,
                    title,
                    introduction,
                    Data.picture,
                    time,
                    type
                from Publish,Data,User
                where Publish.userid=? and Publish.dataid=Data.dataid and  User.userid=?
                order by time desc`;
                
        
                DBHelp(SQL,[userid,userid],(error, result, fields) =>{
                    
                    if(error){
                        //resultData.error = error.message
                        console.error(error);
                        res.send(return_obj.fail("200", "调用数据库接口错误"));
                        return;
                    }
                    console.log(result);
                    if(result.length==0){
                        res.send(return_obj.fail("116", "用户未发布资料"));
                        return;
                    }
                    done(null,result);
                        
                        
                        
                })
            }else{
                res.send(return_obj.fail("100", "缺少必要的参数"));
                return;
            }
        },
        function(pubilsh_info,done){
            let i=0;
            console.log(pubilsh_info);
            for(i=0;i<pubilsh_info.length;i++){
                if(pubilsh_info[i].if_video==1) pubilsh_info[i].type='视频'
                else  if(pubilsh_info[i].if_audio==1) pubilsh_info[i].type='音频'
                else  pubilsh_info[i].type='文章'
            }
            done(null,pubilsh_info)
        }
    ],
    function(err,result){
        res.send(return_obj.success({
            "msg": "获取数据成功",
            "data": result
        }));
        //console.log(result[0]);
        
        return;
    }
    )
    
    
})
module.exports = router;