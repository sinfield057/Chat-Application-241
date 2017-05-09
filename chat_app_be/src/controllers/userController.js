import express from 'express'
import session from 'express-session'
import User from '../models/user'
import md5 from 'md5'
import mongoose from 'mongoose'
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
				console.log( foundUser );
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

router.get( '/logout', ( req, res ) => {
	req.session.destroy( ( err ) => {
		if( err ) {
			res.send( {
				data: 'Failed to logout properly: ' + err,
				resolved: false
			} )
		} else {
			res.send( {
				resolved: true
			} )
		}
	} );
} );

router.post( '/register', ( req, res ) => {
	const username = req.body.username,
		  password = md5( req.body.password );

	User.findOne( {
		username: username
	}, ( err, foundUser ) => {
		if ( err ) {
			res.send( {
				data: "Database Error: " + err,
				resolved: false
			} );
		} else if ( foundUser ) {
			res.send( {
				data: 'Username already in use!',
				resolved: false
			} );
		} else {
			const newUser = new User( {
				_id: mongoose.mongo.ObjectId(),
				username: username,
				password: password,
				createdAt: Date.now(),
				lastLogin: Date.now()
			} );

			newUser.save( ( err ) => {
				if ( err ) {
					res.send( { 
						data: "Database error while saving: " + err,
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

router.get( '/validate', ( req, res ) => {
	const username = req.session.username,
		  userId   = req.session.userId;

	User.findOne( { 
		_id: userId
	}, ( err, foundUser ) => {
		if ( err ) {
			res.send( {
				resolved: false
			} );
		} else {
			if ( foundUser && foundUser.username == username) {
				res.send( {
					resolved: true,
					username: username,
					userId: userId
				} );
			} else {
				res.send( {
					resolved: false,
					username: username,
					userId: userId
				} );
			}
		}
	}) 
} );

module.exports = router;