/**
 * 获取列表
 * @type {Connection}
 */

const DBHelp = require('../config/DBHelp.js');

let get_userinfo = (data, success) =>{
    // SQL 语句
    let SQL = `
    SELECT  password,
            name,
            sex,
            school,
            collage,
            class,
            wechat,
            phone,
            picture
    from User
    where userid=? `;

    /*let SQL_ = SQL+' WHERE ';
    // 多个条件时循环data去除key, val
    _.forEach(data, (key, val) =>{
        if (counter > 0){
            SQL = SQL+ ' AND ' + val + ' = ' + key;
        } else {
            SQL = SQL_+ val + '= ' + key + ' OR name LIKE %' + key +'%';
        }
        counter++
    });*/
    /**
     * resultData
     * @code: 状态码
     * @data：data
     * @codeMessage: 状态消息
     */
    const resultData = {
        code: null,
        data: null,
        codeMessage: null
    }
    DBHelp(SQL,[data.body.userid],(error, result, fields) =>{
        if(error){
            //resultData.error = error.message
            console.error(error);
            resultData.code=200;
            resultData.codeMessage="调用数据库接口错误";
            success(resultData);
            //res.send(return_obj.fail("200", "调用数据库接口错误"));
            return;
        }
        if(result.length==0){
            resultData.code=104;
            resultData.codeMessage="用户不存在";
            success(resultData);
            //res.send(return_obj.fail("104", "用户不存在"));
            return;
        }
        //如果用户密码正确则设置session，否则提示密码错误
        if(result[0].password==data.body.password){
            resultData.code=001;
            resultData.data = result;
            resultData.codeMessage="登陆成功";
            success(resultData);
            return;
            //设置session
        }
        else{
            resultData.code=105;
            resultData.codeMessage="用户账号或密码不正确";
            success(resultData);
            return;
        }
        /* resultData.code = 200;
        resultData.data = result;
        resultData.codeMessage = 'success'
        success(resultData); */
    })
 
 
}
module.exports = get_userinfo