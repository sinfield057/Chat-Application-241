import express from 'express';
import bodyParser from 'body-parser';
import multer from 'multer';
import morgan from 'morgan';
import mongoose from 'mongoose';

const app 	 = express();
const upload = multer();

app.use( express.static( __dirname + '/../public') );
app.use( morgan( 'dev' ) );
app.use( bodyParser.json() );
app.use( bodyParser.urlencoded( {
	extended: true
} ) );
app.use( '/api/', require( './controllers' ) );
console.log( 'Modules loaded...' );


mongoose.connect( 'mongodb://localhost', ( error ) => {
	if ( error ) {
		throw error;
	};
} );
console.log( 'Connected to DB...' );

app.listen( 8000, () => {
	console.log( 'Listening on port 3000...' );
} );
