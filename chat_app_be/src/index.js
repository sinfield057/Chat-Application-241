import express from 'express';
import session from 'express-session';
import bodyParser from 'body-parser';
import multer from 'multer';
import morgan from 'morgan';
import mongoose from 'mongoose';
const mongoStore = require('connect-mongo')(session);

const app 	 = express();
const upload = multer();

app.use( morgan( 'dev' ) );
app.use( bodyParser.json() );
app.use( bodyParser.urlencoded( {
	extended: true
} ) );

mongoose.connect( 'mongodb://localhost', ( error ) => {
	if ( error ) {
		throw error;
	};
} );
console.log( 'Connected to DB...' );

const db = mongoose.connection;
db.on( 'error', console.error.bind( console, 'Database connection error: ' ) );

app.use( session( {
	secret: 'wewlad',
	store: new mongoStore( { mongooseConnection: db } ),
	cookie: {
		maxAge: 60000 * 20,
		secure: false
	},
	resave: true,
	saveUninitialized: true
} ) );
console.log( 'Sessions loaded...' );

app.use( express.static( __dirname + '/../public') );
app.use( '/api/', require( './controllers' ) );
console.log( 'Modules loaded...' );

app.listen( 8000, () => {
	console.log( 'Listening on port 8000...' );
} );
