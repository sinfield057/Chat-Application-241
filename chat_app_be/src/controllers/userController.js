import express from 'express'
import User from '../models/user'
const router = express.Router();

router.post( '/login', ( req, res ) => {
	const username = req.body.username,
		  password = req.body.password;

	User.findOne( { 
		username: username,
		password: password
	}, ( err, foundUser ) => {
		if ( err ) {
			res.send( { data: err } );
		} else {
			if ( foundUser ) {
				res.send( {
					data: 'User and password accepted!'
				} );
			} else {
				res.send( {
					data: 'User and/or password invalid!'
				} );
			}
		}
	});
} );

router.post( '/register', ( req, res ) => {
	const username = req.body.username,
		  password = req.body.password;

	var newUser = new User( {
		username: username,
		password: password
	} );

	newUser.save( ( err ) => {
		if ( err ) {
			res.send( { 
				data: err
			} );
		} else {
			res.send( { 
				data: 'User successfully created!'
			} );
		}
	} );
} );

module.exports = router;