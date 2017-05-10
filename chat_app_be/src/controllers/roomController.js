import express from 'express'
import session from 'express-session'
import User from '../models/user'
import Room from '../models/room'
import md5 from 'md5'
import mongoose from 'mongoose'
const router = express.Router();

router.post( '/createRoom' , ( req, res ) => {
	const userId 	  = req.body.userId,
		  name   	  = req.body.name,
		  description = req.body.description;

	Room.findOne( {
		name: name 
	}, ( err, foundRoom ) => {
		if ( err ) {
			res.send( {
				data: "Database Error: " + err,
				resolved: false
			} );
		} else if ( foundRoom ) {
			res.send( {
				data: 'Room name already in use!',
				resolved: false
			} );
		} else {
			const newRoom = new Room( {
				_id: mongoose.mongo.ObjectId(),
				name: name,
				description: description,
				admin: userId,
				users: [ userId ],
				createdAt: Date.now()
			} );

			newRoom.save( ( err ) => {
				if ( err ) {
					res.send( {
						data: "Error creating room: " + err,
						resolved: false 
					} );
				} else {
					res.send( {
						data: "Room successfully created!",
						resolved: true
					} );
				}
			} );
		}
	} ); 
} );

router.get( '/getRooms', ( req, res ) => {
	const userId = req.session.userId;

	Room.find( {}, ( err, rooms ) => {
		if( err ) {
			res.send( {
				data: "Error retrieving rooms: " + err,
				resolved: false
			} );
		} else {
			res.send( {
				data: rooms,
				resolved: true
			} );
		}
	} );

} );


module.exports = router;