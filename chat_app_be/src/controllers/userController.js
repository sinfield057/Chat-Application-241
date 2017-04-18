import express from 'express'
import session from 'express-session'
import User from '../models/user'
import md5 from 'md5'
const router = express.Router();

router.post( '/login', ( req, res ) => {
	console.log( md5 );
	const username = req.body.username,
		  password = md5( req.body.password );

	User.findOne( { 
		username: username,
		password: password
	}, ( err, foundUser ) => {
		if ( err ) {
			res.send( { data: err } );
		} else {
			if ( foundUser ) {
				req.session.username = username;
				req.session.loginDate = Date.now();
				req.session.userId = foundUser._id;
				foundUser.lastLogin = Date.now();
				foundUser.save( ( err ) => {
					if ( err ) {
						res.send( {
							data: 'Error at login: ' + err,
							resolved: false
						} );
					} else {
						res.send( {
							data: 'User and password accepted!',
							resolved: true
						} );
					}
				} );
			} else {
				res.send( {
					data: 'User and/or password invalid!',
					resolved: false
				} );
			}
		}
	});
} );

router.post( '/register', ( req, res ) => {
	const username = req.body.username,
		  password = md5( req.body.password );

	User.findOne( {
		username: username
	}, ( err, foundUser ) => {
		if ( err ) {
			res.send( {
				data: err,
				resolved: false
			} );
		} else if ( foundUser ) {
			res.send( {
				data: 'Username already in use!',
				resolved: false
			} );
		} else {
			var newUser = new User( {
				username: username,
				password: password,
				createdAt: Date.now(),
				lastLogin: Date.now()
			} );

			newUser.save( ( err ) => {
				if ( err ) {
					res.send( { 
						data: err,
						resolved: false
					} );
				} else {
					res.send( { 
						data: 'User successfully created!',
						resolved: true
					} );
				}
			} );
		}
	} );
} );

module.exports = router;