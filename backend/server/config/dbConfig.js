//文件配置，数据库信息
const config = {
    // 启动端口
    port: 8080,
    // 数据库配置
    database: {
        DATABASE: 'StudentCommunity',
        USERNAME: 'root',
        PASSWORD: 'PLhyh7j0VGSgNprRgad3',
        PORT: '3306',
        HOST: '81.70.27.208',
        insecureAuth : true,
        useConnectionPooling: true
    }
}
 
module.exports = config