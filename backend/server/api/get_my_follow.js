/*
* function:获取我关注的用户所发布的资料，按时间降序展示
*/
'use strict'
const express = require('express');
const return_obj = require('../tool/return_obj.js');
const verify_login = require('../middleware/verify_login.js')
const router = express.Router();
const DBHelp = require('../config/DBHelp.js');
const async = require('async');

 //验证用户登录状态
router.get("/", verify_login);

//使用瀑布流获取数据
router.get("/",function(req,res,next){
    async.waterfall([
        function(done){
            let sql=`
            select 
                User.userid as userid,
                picture as user_pic,
                name
            from User,Follow
            where Follow.userid=? and Follow.followid=User.userid
            `
            DBHelp(sql,[req.session.userid],(err,Follow_user,field)=>{
                if(err){
                    console.log(err);
                    res.send(return_obj.fail("200","调用数据库接口错误"))
                    return ;
                }
                if(Follow_user.length==0){
                    res.send(return_obj.fail("126","无关注的用户"))
                    return ;
                }
                let follow_use = JSON.stringify(Follow_user);
                let follow_user = JSON.parse(follow_use);
                done(null,follow_user);
            })
        },
        function(follow_user,done){
            //关注的用户的userid集合
            let follow_user_id =new Array();
            let i;
            //最终返回的数组（用户信息+发布的资料）
            let follow_user_data=new Array();
            for(i=0;i<follow_user.length;i++){
                follow_user_id.push(follow_user[i].userid);
            }
            //console.log(follow_user_id);
            let sql=`
            select 
                Publish.userid as publishid,
                Publish.dataid as dataid,
                title,
                Data.picture as data_pic,
                introduction,
                time,
                if_article,
                if_video,
                if_audio
            from Publish,Data
            where Publish.dataid=Data.dataid and Publish.userid in (?)
            order by time desc
            `
            DBHelp(sql,[follow_user_id],(err,Follow_data,field)=>{
                if(err){
                    console.log(err);
                    res.send(return_obj.fail("200","调用数据库接口错误"))
                    return ;
                }
                if(Follow_data.length==0){
                    res.send(return_obj.fail("124","关注用户未发布资料"));
                    return ;
                }
                //
                //转换格式
                let follow_dat = JSON.stringify(Follow_data);
                let follow_data = JSON.parse(follow_dat);
                //console.log(follow_data)
                
                for(let j=0;j<follow_data.length;j++){
                    for(let i=0;i<follow_user.length;i++){
                        if(follow_user[i].userid==follow_data[j].publishid){
                            follow_user_data.push({
                                'userid':follow_user[i].userid,
                                'user_pic':follow_user[i].user_pic,
                                'name':follow_user[i].name,
                                'title':follow_data[j].title,
                                'picture':follow_data[j].data_pic,
                                'introduction':follow_data[j].introduction,
                                'dataid':follow_data[j].dataid,
                                'time':follow_data[j].time,
                                'if_article':follow_data[j].if_article,
                                'if_video':follow_data[j].if_video,
                                'if_audio':follow_data[j].if_audio,
                            })
                            /* console.log("sqaa")
                            console.log(follow_user_data) */
                            break;
                        }
                    }
                }
                //console.log(follow_user_data)
                done(null,follow_user_data)
            })
            
        }
    ],function(err,result){
        if (err) {
            console.log(err);
            return ;
        }
        res.send(return_obj.success({
            msg: "获取数据成功",
            follow_user_data: result
        }));
    })
})
module.exports = router;