/*
 * function:multer上传文件中间件的配置文件
 */
'use strict'
const multer = require("multer");
module.exports = {
    picture: {
        storage: multer.diskStorage({
            
            destination: function (req, file, cb) {
                //console.log("aaaa")
                cb(null, '../Data/Picture/');
                
            },
            filename: function (req, file, cb) {
                //console.log("aaasssa")
                let extName = file.originalname.slice(file.originalname.lastIndexOf('.'))
                cb(null,Date.now() + extName);
                
            }
        }),
        fileFilter: function (req, file, cb) {
            var acceptableMime = ['image/jpeg', 'image/png', 'image/jpg']
            
            //console.log(file)
            if (acceptableMime.indexOf(file.mimetype) != -1) {
                //console.log("aaaa")
                return cb(null, true);
                 
            }
            return cb(new Error("upload file mimetype error"), false);
        },
        limits: {
            files: 2
        }
    },
    video: {
        storage: multer.diskStorage({
            destination: function (req, file, cb) {
                cb(null, '../Data/Video/');
                
            },
            filename: function (req, file, cb) {
                let extName = file.originalname.slice(file.originalname.lastIndexOf('.'))
                cb(null,Date.now() + extName);
                
            }
        }),
        fileFilter: function (req, file, cb) {
            var acceptableMime = ['application/octet-stream', 'application/octet-stream', 'video/mp4','video/ogg','video/webm','video/x-msvideo']
           
            //console.log(file)
            if (acceptableMime.indexOf(file.mimetype) != -1) {
                //console.log("aaaa")
                return cb(null, true);
                 
            }
            return cb(new Error("upload file mimetype error"), false);
        },
        limits: {
            files: 2
        }
    },
    audio: {
        storage: multer.diskStorage({
            destination: function (req, file, cb) {
                cb(null, '../Data/Audio/');
                
            },
            filename: function (req, file, cb) {
                let extName = file.originalname.slice(file.originalname.lastIndexOf('.'))
                cb(null,Date.now() + extName);
                
            }
        }),
        fileFilter: function (req, file, cb) {
            var acceptableMime = ['audio/x-aiff','audio/basic','audio/x-mpegurl','audio/midi','audio/mpeg','audio/x-pn-realaudio','audio/x-wav']
           
            //console.log(file)
            if (acceptableMime.indexOf(file.mimetype) != -1) {
                //console.log("aaaa")
                return cb(null, true);
                 
            }
            return cb(new Error("upload file mimetype error"), false);
        },
        limits: {
            files: 2
        }
    }
};