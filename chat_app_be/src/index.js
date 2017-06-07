import express from 'express';
import session from 'express-session';
import bodyParser from 'body-parser';
import multer from 'multer';
import morgan from 'morgan';
import mongoose from 'mongoose';
import http from 'http';
import io from 'socket.io';
const mongoStore = require('connect-mongo')(session);


const upload = multer();
const app 	 = express();
const server = http.createServer( app );
const socket = io.listen( server );

app.use( morgan( 'dev' ) );
app.use( bodyParser.json() );
app.use( bodyParser.urlencoded( {
	extended: true
} ) );

mongoose.Promise = global.Promise;
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
	name: 'Chat App',
	resave: true,
	saveUninitialized: true
} ) );
console.log( 'Sessions loaded...' );

app.use( express.static( __dirname + '/../public') );
app.use( '/api/', require( './controllers' ) );
app.get( '*', ( req, res ) => {
	res.redirect( '/' ); 
} );
console.log( 'Modules loaded...' );

global.socket = socket;

socket.on( 'connect', client => {
	client.on( "disconnect", () => {
		console.log( "disconnected" );
	});
	client.on( 'sendMessage', ( payload ) => {
		console.log( payload );
		socket.emit( 'emitMessage', payload );
	} );
} );

server.listen( 8000, () => {
	console.log( 'Listening on port 8000...' );
} );
