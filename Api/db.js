// get the client
const mysql = require('mysql2');
 
// create the connection to database
const con = mysql.createConnection({
  host: 'localhost',
  user: 'root',
  password: '123456',
  database: 'bida',
  waitForConnections: true,
  connectionLimit: 10,
});
con.connect(function(err) {
    if (err) throw err;
    console.log("Connected MYSQL!");
});
module.exports = con;   